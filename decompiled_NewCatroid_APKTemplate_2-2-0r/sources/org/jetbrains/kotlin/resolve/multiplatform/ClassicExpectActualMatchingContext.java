package org.jetbrains.kotlin.resolve.multiplatform;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.mpp.ClassLikeSymbolMarker;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.mpp.FunctionSymbolMarker;
import org.jetbrains.kotlin.mpp.K1SyntheticClassifierSymbolMarker;
import org.jetbrains.kotlin.mpp.PropertySymbolMarker;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeAliasSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeParameterSymbolMarker;
import org.jetbrains.kotlin.mpp.ValueParameterSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCS;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualCollectionArgumentsCompatibilityCheckStrategy;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeRefinerImpl;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.ClassicTypeCheckerStateKt;
import org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeCheckerImpl;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.error.ErrorClassDescriptor;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.ObsoleteTypeKind;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: ClassicExpectActualMatchingContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008e\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b0\n\u0002\u0010(\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0002´\u0002B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010{\u001a\u00020\u00072\u0006\u0010|\u001a\u00020\u00162\u0006\u0010}\u001a\u00020\u0016H\u0016J#\u0010~\u001a\u00020\u00072\u0006\u0010\u007f\u001a\u00020\u00112\u0007\u0010\u0080\u0001\u001a\u00020\u00112\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001H\u0016J\u001d\u0010\u0083\u0001\u001a\u00020\u00072\b\u0010|\u001a\u0004\u0018\u00010\u00162\b\u0010}\u001a\u0004\u0018\u00010\u0016H\u0016J1\u0010\u0084\u0001\u001a\u00020\u00072\b\u0010\u0085\u0001\u001a\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0086\u00012\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\b\u0010\u008a\u0001\u001a\u00030\u008b\u0001H\u0002J\u001d\u0010\u008c\u0001\u001a\u00020\u00072\b\u0010\u0085\u0001\u001a\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0086\u0001H\u0002J\u001d\u0010\u008d\u0001\u001a\u00020\u00072\b\u0010\u0085\u0001\u001a\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0086\u0001H\u0002J\u001e\u0010\u008e\u0001\u001a\u00020\u00072\b\u0010\u008f\u0001\u001a\u00030\u0090\u00012\b\u0010\u0091\u0001\u001a\u00030\u0090\u0001H\u0096\u0001J!\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u00012\b\u0010\u0094\u0001\u001a\u00030\u0093\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0096\u0001J\u0015\u0010\u0097\u0001\u001a\u0004\u0018\u00010\u00162\u0007\u0010\u0094\u0001\u001a\u00020\u0016H\u0096\u0001J\u000b\u0010\u0098\u0001\u001a\u00030\u0099\u0001H\u0096\u0001J4\u0010\u009a\u0001\u001a\u00030\u0099\u00012\r\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u00020\u00170\u00102\r\u0010\u009c\u0001\u001a\b\u0012\u0004\u0012\u00020\u00170\u00102\n\u0010\u009d\u0001\u001a\u0005\u0018\u00010\u0099\u0001H\u0016J8\u0010\u009e\u0001\u001a\n\u0012\u0005\u0012\u00030 \u00010\u009f\u00012\b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0007\u0010¡\u0001\u001a\u00020\u00052\u0014\u0010¢\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00070£\u0001J\u001b\u0010¤\u0001\u001a\u0005\u0018\u00010¥\u00012\u0007\u0010¦\u0001\u001a\u00020 H\u0000¢\u0006\u0003\b§\u0001J\u001e\u0010¨\u0001\u001a\u00020\u00072\b\u0010\u0085\u0001\u001a\u00030\u0093\u00012\b\u0010\u0087\u0001\u001a\u00030\u0093\u0001H\u0096\u0001J\u0019\u0010©\u0001\u001a\u00020\u00162\r\u0010ª\u0001\u001a\b\u0012\u0004\u0012\u00020\u00160\u0010H\u0096\u0001J\u001b\u0010©\u0001\u001a\u00030\u0093\u00012\u000e\u0010ª\u0001\u001a\t\u0012\u0005\u0012\u00030\u0093\u00010\u0010H\u0096\u0001J%\u0010«\u0001\u001a\u00020\u00072\b\u0010¬\u0001\u001a\u00030\u00ad\u00012\b\u0010®\u0001\u001a\u00030\u00ad\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0015\u0010¯\u0001\u001a\u00030°\u00012\b\u0010\u0094\u0001\u001a\u00030\u0093\u0001H\u0096\u0001J\"\u0010±\u0001\u001a\u00030\u0099\u00012\u0015\u0010²\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0090\u0001\u0012\u0004\u0012\u00020\u00160³\u0001H\u0096\u0001J\u0014\u0010´\u0001\u001a\t\u0012\u0004\u0012\u00020\u001c0µ\u0001*\u00020`H\u0016J\u000f\u0010¶\u0001\u001a\u00030·\u0001*\u00020\u0016H\u0096\u0001J\u0010\u0010¸\u0001\u001a\u00030¹\u0001*\u00030\u0093\u0001H\u0096\u0001J\u0012\u0010º\u0001\u001a\u0005\u0018\u00010»\u0001*\u00030\u0093\u0001H\u0096\u0001J\u0012\u0010¼\u0001\u001a\u0005\u0018\u00010½\u0001*\u00030\u0093\u0001H\u0096\u0001J\u000e\u0010¾\u0001\u001a\u00030¿\u0001*\u00020\u001cH\u0002J\u000e\u0010¾\u0001\u001a\u00030 \u0001*\u00020VH\u0002J\u000e\u0010¾\u0001\u001a\u00030À\u0001*\u00020\u0012H\u0002J\u000e\u0010¾\u0001\u001a\u00030Á\u0001*\u00020`H\u0002J\u000e\u0010¾\u0001\u001a\u00030Â\u0001*\u00020?H\u0002J\u000e\u0010¾\u0001\u001a\u00030¥\u0001*\u00020!H\u0002J\u000e\u0010¾\u0001\u001a\u00030Ã\u0001*\u00020$H\u0002J\u000e\u0010¾\u0001\u001a\u00030Ä\u0001*\u00020\u0017H\u0002J\u000e\u0010¾\u0001\u001a\u00030Å\u0001*\u000203H\u0002J\u0012\u0010Æ\u0001\u001a\u0005\u0018\u00010Ç\u0001*\u00030È\u0001H\u0096\u0001J\u0011\u0010É\u0001\u001a\u0005\u0018\u00010È\u0001*\u00020\u0016H\u0096\u0001J\u0011\u0010Ê\u0001\u001a\u0005\u0018\u00010\u0093\u0001*\u00020\u0016H\u0096\u0001J\u000f\u0010Ë\u0001\u001a\u00030Ì\u0001*\u00020\u0016H\u0096\u0001J\u0010\u0010Í\u0001\u001a\u00030\u0096\u0001*\u00030»\u0001H\u0096\u0001J\u001c\u0010Î\u0001\u001a\b\u0012\u0004\u0012\u00020\u00120\u0010*\u00020!2\u0007\u0010Ï\u0001\u001a\u00020\u0007H\u0016J\u0013\u0010Ð\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u0010*\u00020!H\u0016J\u000f\u0010Ñ\u0001\u001a\u0004\u0018\u00010!*\u00020$H\u0016J\"\u0010Ò\u0001\u001a\u000b\u0012\u0005\u0012\u00030\u0093\u0001\u0018\u00010\u0010*\u00030\u0093\u00012\b\u0010Ó\u0001\u001a\u00030\u0090\u0001H\u0096\u0001J\u001a\u0010Ô\u0001\u001a\u00030Ì\u0001*\u00030¹\u00012\b\u0010Õ\u0001\u001a\u00030·\u0001H\u0096\u0003J\u0019\u0010Ö\u0001\u001a\u00030Ì\u0001*\u00020\u00162\b\u0010Õ\u0001\u001a\u00030·\u0001H\u0096\u0001J\u001c\u0010×\u0001\u001a\u0005\u0018\u00010Ì\u0001*\u00030\u0093\u00012\b\u0010Õ\u0001\u001a\u00030·\u0001H\u0096\u0001J\u0015\u0010Ø\u0001\u001a\t\u0012\u0005\u0012\u00030Ì\u00010\u0010*\u00020\u0016H\u0096\u0001J\u0015\u0010Ù\u0001\u001a\t\u0012\u0005\u0012\u00030Ú\u00010\u0010*\u00020\u0016H\u0096\u0001J\u0015\u0010Û\u0001\u001a\t\u0012\u0005\u0012\u00030Ú\u00010\u0010*\u00020\u0016H\u0096\u0001J!\u0010Ü\u0001\u001a\b\u0012\u0004\u0012\u00020\u00020\u0010*\u00030¥\u00012\u000b\b\u0002\u0010Ý\u0001\u001a\u0004\u0018\u00010YH\u0002J\u001c\u0010Þ\u0001\u001a\b\u0012\u0004\u0012\u00020\u00120\u0010*\u00020!2\u0007\u0010Ý\u0001\u001a\u00020YH\u0016J\u0019\u0010ß\u0001\u001a\u00020g*\u00030\u0090\u00012\b\u0010Õ\u0001\u001a\u00030·\u0001H\u0096\u0001J\u0015\u0010à\u0001\u001a\b\u0012\u0004\u0012\u00020g0\u0010*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010á\u0001\u001a\u00020\u0016*\u00030Ì\u0001H\u0096\u0001J\u000f\u0010â\u0001\u001a\u00030\u0090\u0001*\u00020gH\u0096\u0001J\u0011\u0010ã\u0001\u001a\u0004\u0018\u00010g*\u00030\u0090\u0001H\u0096\u0001J\u0018\u0010ä\u0001\u001a\u00020\u0016*\u00020g2\b\u0010Õ\u0001\u001a\u00030·\u0001H\u0096\u0001J\u0014\u0010å\u0001\u001a\b\u0012\u0004\u0012\u00020\u00160\u0010*\u00020gH\u0096\u0001J\u000f\u0010t\u001a\u00030æ\u0001*\u00030Ì\u0001H\u0096\u0001J\u000e\u0010t\u001a\u00030æ\u0001*\u00020gH\u0096\u0001J\u000e\u0010ç\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000e\u0010è\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u001c\u0010é\u0001\u001a\u00020\u0007*\u00020g2\f\b\u0002\u0010ê\u0001\u001a\u0005\u0018\u00010\u0090\u0001H\u0096\u0001J\r\u0010ë\u0001\u001a\u00020\u0007*\u00020\u001cH\u0016J\u000f\u0010ì\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010í\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010î\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000e\u0010ï\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000f\u0010ð\u0001\u001a\u00020\u0007*\u00030\u0093\u0001H\u0096\u0001J\u000f\u0010ñ\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010ò\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010ó\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010ô\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000f\u0010õ\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010ö\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000e\u0010÷\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000f\u0010÷\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010ø\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000e\u0010ù\u0001\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000f\u0010ú\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010û\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010ü\u0001\u001a\u00020\u0007*\u00030\u0093\u0001H\u0096\u0001J\u000f\u0010ý\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010þ\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010ÿ\u0001\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010\u0080\u0002\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010\u0081\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000f\u0010\u0081\u0002\u001a\u00020\u0007*\u00030\u0093\u0001H\u0096\u0001J\u000e\u0010\u0082\u0002\u001a\u00020\u0007*\u00020\u0016H\u0097\u0001J\r\u0010\u0083\u0002\u001a\u00020\u0007*\u00020!H\u0016J\u000e\u0010\u0084\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000f\u0010\u0085\u0002\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010\u0086\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0087\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0088\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000f\u0010\u0089\u0002\u001a\u00020\u0007*\u00030»\u0001H\u0096\u0001J\u000f\u0010\u008a\u0002\u001a\u00020\u0007*\u00030\u0093\u0001H\u0096\u0001J\u000f\u0010\u008b\u0002\u001a\u00020\u0007*\u00030»\u0001H\u0096\u0001J\u000e\u0010\u008c\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000e\u0010\u008d\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000f\u0010\u008e\u0002\u001a\u00020\u0007*\u00030\u0093\u0001H\u0096\u0001J\u000f\u0010\u008f\u0002\u001a\u00020\u0007*\u00030Ì\u0001H\u0096\u0001J\u000f\u0010\u0090\u0002\u001a\u00020\u0007*\u00030\u0093\u0001H\u0096\u0001J\u000f\u0010\u0091\u0002\u001a\u00020\u0007*\u00030\u0093\u0001H\u0096\u0001J\u000f\u0010\u0092\u0002\u001a\u00020\u0007*\u00030\u0093\u0001H\u0096\u0001J\u000f\u0010\u0093\u0002\u001a\u00020\u0007*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010\u0094\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u000e\u0010\u0095\u0002\u001a\u00020\u0007*\u00020\u0016H\u0096\u0001J\u0017\u0010\u0096\u0002\u001a\n\u0012\u0005\u0012\u00030Ì\u00010\u0097\u0002*\u00030¹\u0001H\u0096\u0003J\u0010\u0010\u0098\u0002\u001a\u00030\u0093\u0001*\u00030È\u0001H\u0096\u0001J\u000f\u0010\u0099\u0002\u001a\u00030\u0093\u0001*\u00020\u0016H\u0096\u0001J\u0011\u0010\u009a\u0002\u001a\u0004\u0018\u00010\u0016*\u00030»\u0001H\u0096\u0001J\u000e\u0010\u009b\u0002\u001a\u00020\u0016*\u00020\u0016H\u0096\u0001J\u0010\u0010\u009c\u0002\u001a\u00030\u0093\u0001*\u00030\u0093\u0001H\u0096\u0001J\u0010\u0010\u009d\u0002\u001a\u00030\u0093\u0001*\u00030½\u0001H\u0096\u0001J\u0010\u0010\u009e\u0002\u001a\u00030\u0093\u0001*\u00030\u0093\u0001H\u0096\u0001J\u0010\u0010\u009f\u0002\u001a\u00030·\u0001*\u00030\u0090\u0001H\u0096\u0001J\u0016\u0010 \u0002\u001a\t\u0012\u0004\u0012\u00020\u00160\u009f\u0001*\u00030\u0093\u0001H\u0096\u0001J\u0010\u0010¡\u0002\u001a\u00030Ì\u0001*\u00030¢\u0002H\u0096\u0001J\u0019\u0010£\u0002\u001a\u00030Ì\u0001*\u00030Ì\u00012\u0007\u0010¤\u0002\u001a\u00020\u0016H\u0096\u0001J%\u0010¥\u0002\u001a\u0005\u0018\u0001H¦\u0002\"\f\b\u0000\u0010¦\u0002\u0018\u0001*\u00030À\u0001*\u00020\u0012H\u0082\b¢\u0006\u0003\u0010§\u0002J\u0018\u0010¨\u0002\u001a\u00020\u0016*\u00030\u0099\u00012\u0007\u0010\u0094\u0001\u001a\u00020\u0016H\u0096\u0001J\u0016\u0010©\u0002\u001a\u00020\u0007*\u00020\u001c2\u0007\u0010ª\u0002\u001a\u00020!H\u0016J\u0010\u0010«\u0002\u001a\u00030·\u0001*\u00030¹\u0001H\u0096\u0001J\u0016\u0010¬\u0002\u001a\t\u0012\u0004\u0012\u00020\u00160\u009f\u0001*\u00030\u0090\u0001H\u0096\u0001J\u0010\u0010\u00ad\u0002\u001a\u00030¢\u0002*\u00030»\u0001H\u0096\u0001J\u000f\u0010\u00ad\u0002\u001a\u00030\u0090\u0001*\u00020\u0016H\u0096\u0001J\u0010\u0010\u00ad\u0002\u001a\u00030\u0090\u0001*\u00030\u0093\u0001H\u0096\u0001J\u0010\u0010®\u0002\u001a\u00030\u0090\u0001*\u00030\u0090\u0001H\u0096\u0001J\u0010\u0010¯\u0002\u001a\u00030\u0093\u0001*\u00030È\u0001H\u0096\u0001J\u000f\u0010°\u0002\u001a\u00030·\u0001*\u00020gH\u0096\u0001J\u000f\u0010±\u0002\u001a\u00030\u0093\u0001*\u00020\u0016H\u0096\u0001J\u0017\u0010²\u0002\u001a\u00020\u0016*\u00020\u00162\u0007\u0010³\u0002\u001a\u00020\u0007H\u0096\u0001J\u0019\u0010²\u0002\u001a\u00030\u0093\u0001*\u00030\u0093\u00012\u0007\u0010³\u0002\u001a\u00020\u0007H\u0096\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010*\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0010*\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\u00020\u001b*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\u00020 *\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0018\u0010\u001f\u001a\u00020 *\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010%R\u0018\u0010&\u001a\u00020'*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0018\u0010*\u001a\u00020\u0016*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u001a\u0010-\u001a\u0004\u0018\u00010\u0016*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u001a\u00100\u001a\u0004\u0018\u00010\u0016*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010/R\u0018\u00102\u001a\u00020\u0007*\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0018\u00106\u001a\u00020\u0007*\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0018\u00109\u001a\u00020\u0007*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0018\u0010<\u001a\u00020\u0007*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0018\u0010>\u001a\u00020\u0007*\u00020?8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010@R\u0018\u0010A\u001a\u00020\u0007*\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u00105R\u0018\u0010B\u001a\u00020\u0007*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010;R\u0018\u0010C\u001a\u00020\u0007*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bC\u0010;R\u0018\u0010D\u001a\u00020\u0007*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010=R\u0018\u0010E\u001a\u00020\u0007*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bE\u0010;R\u0018\u0010F\u001a\u00020\u0007*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010;R\u0018\u0010F\u001a\u00020\u0007*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010=R\u0018\u0010G\u001a\u00020\u0007*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bG\u0010=R\u0018\u0010H\u001a\u00020\u0007*\u00020?8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010@R\u0018\u0010I\u001a\u00020\u0007*\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u00105R\u0018\u0010J\u001a\u00020\u0007*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010;R\u0018\u0010K\u001a\u00020\u0007*\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0018\u0010M\u001a\u00020\u0007*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010;R\u0018\u0010N\u001a\u00020\u0007*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bN\u0010;R\u0018\u0010O\u001a\u00020\u0007*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bO\u0010=R\u0018\u0010P\u001a\u00020\u0007*\u00020?8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bP\u0010@R\u0018\u0010Q\u001a\u00020\u0007*\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u00105R\u001a\u0010R\u001a\u0004\u0018\u00010S*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bT\u0010UR\u0018\u0010R\u001a\u00020S*\u00020V8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bT\u0010WR\u0018\u0010X\u001a\u00020Y*\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010[R\u0018\u0010X\u001a\u00020Y*\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010\\R\u0018\u0010]\u001a\u00020\u0016*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b^\u0010/R\u001a\u0010_\u001a\u0004\u0018\u00010`*\u00020?8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\ba\u0010bR\u001e\u0010c\u001a\b\u0012\u0004\u0012\u00020\u00160\u0010*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bd\u0010eR\u0018\u0010f\u001a\u0004\u0018\u00010g*\u00020hX\u0096\u0005¢\u0006\u0006\u001a\u0004\bi\u0010jR\u001e\u0010k\u001a\b\u0012\u0004\u0012\u00020\u00170\u0010*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bl\u0010mR\u001e\u0010k\u001a\b\u0012\u0004\u0012\u00020\u00170\u0010*\u00020V8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bl\u0010nR\u001e\u0010o\u001a\b\u0012\u0004\u0012\u0002030\u0010*\u00020`8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bp\u0010qR\u0018\u0010r\u001a\u00020s*\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bt\u0010uR\u0018\u0010v\u001a\u00020w*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bx\u0010yR\u0018\u0010v\u001a\u00020w*\u00020V8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bx\u0010z¨\u0006µ\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ClassicExpectActualMatchingContext;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "platformModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "shouldCheckAbsenceOfDefaultParamsInActual", "", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Z)V", "getPlatformModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getShouldCheckAbsenceOfDefaultParamsInActual", "()Z", "shouldCheckReturnTypesOfCallables", "getShouldCheckReturnTypesOfCallables", "annotations", "", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "getAnnotations", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Ljava/util/List;", "bounds", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;", "getBounds", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Ljava/util/List;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;", "getCallableId", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/name/CallableId;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;", "getClassId", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;)Lorg/jetbrains/kotlin/name/ClassId;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/ClassKind;", "defaultType", "getDefaultType", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "dispatchReceiverType", "getDispatchReceiverType", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "extensionReceiverType", "getExtensionReceiverType", "hasDefaultValue", "Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;", "getHasDefaultValue", "(Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;)Z", "hasSourceAnnotationsErased", "getHasSourceAnnotationsErased", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Z", "hasStableParameterNames", "getHasStableParameterNames", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Z", "isCompanion", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Z", "isConst", "Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Z", "isCrossinline", "isExpect", "isExternal", "isFun", "isInfix", "isInline", "isInner", "isLateinit", "isNoinline", "isOperator", "isReified", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Z", "isSuspend", "isTailrec", "isValue", "isVar", "isVararg", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Modality;", "Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Modality;", "parameterName", "Lorg/jetbrains/kotlin/name/Name;", "getParameterName", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "returnType", "getReturnType", "setter", "Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "getSetter", "(Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "superTypes", "getSuperTypes", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Ljava/util/List;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "typeParameters", "getTypeParameters", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Ljava/util/List;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Ljava/util/List;", "valueParameters", "getValueParameters", "(Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;)Ljava/util/List;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Lorg/jetbrains/kotlin/types/Variance;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "actualTypeIsSubtypeOfExpectType", "expectType", "actualType", "areAnnotationArgumentsEqual", "expectAnnotation", "actualAnnotation", "collectionArgumentsCompatibilityCheckStrategy", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "areCompatibleExpectActualTypes", "areCompatibleTypes", "a", "Lorg/jetbrains/kotlin/types/KotlinType;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "typeSystemContext", "Lorg/jetbrains/kotlin/types/checker/ClassicTypeSystemContext;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "areCompatibleTypesViaTypeContext", "areCompatibleTypesViaTypeRefinement", "areEqualTypeConstructors", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "captureFromArguments", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createExpectActualTypeParameterSubstitutor", "expectTypeParameters", "actualTypeParameters", "parentSubstitutor", "findClassifiersFromModule", "", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "module", "moduleFilter", "Lkotlin/Function1;", "findExpandedExpectClassInPlatformModule", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "originalClassId", "findExpandedExpectClassInPlatformModule$resolution", "identicalArguments", "intersectTypes", SpecTypePair.NAME_types, "isExpectedClassAndActualTypeAlias", "expectedTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "actualTypeConstructor", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "allOverriddenDeclarationsRecursive", "Lkotlin/sequences/Sequence;", "argumentsCount", "", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "captureStatus", "collectAllMembers", "isActualDeclaration", "collectEnumEntryNames", "expandToRegularClass", "fastCorrespondingSupertypes", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "get", "index", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "getCustomAttributes", "getMembers", "name", "getMembersForExpectClass", "getParameter", "getParameters", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUpperBound", "getUpperBounds", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "hasCustomAttributes", "hasFlexibleNullability", "hasRecursiveBounds", "selfConstructor", "isAnnotationConstructor", "isAnonymous", "isAnyConstructor", "isCapturedDynamic", "isCapturedType", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isFlexible", "isFlexibleNothing", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isIntersection", "isLocalType", "isMarkedNullable", "isNotNullTypeParameter", "isNotSamInterface", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isSimpleType", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUninferredParameter", "iterator", "", "lowerBound", "lowerBoundIfFlexible", "lowerType", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceType", "newType", "safeAsDescriptor", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "safeSubstitute", "shouldSkipMatching", "containingExpectClass", "size", "supertypes", "typeConstructor", "unwrapStubTypeVariableConstructor", "upperBound", "upperBoundCount", "upperBoundIfFlexible", "withNullability", "nullable", "AnnotationCallInfoImpl", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassicExpectActualMatchingContext implements ExpectActualMatchingContext<MemberDescriptor>, TypeSystemContext {
    private final /* synthetic */ ClassicTypeSystemContextForCS $$delegate_0;
    private final ModuleDescriptor platformModule;
    private final boolean shouldCheckAbsenceOfDefaultParamsInActual;

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return this.$$delegate_0.areEqualTypeConstructors(c1, c2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.$$delegate_0.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.$$delegate_0.createEmptySubstitutor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.$$delegate_0.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getShouldCheckReturnTypesOfCallables() {
        return true;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.$$delegate_0.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return this.$$delegate_0.identicalArguments(a, b);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.mo6107intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isCapturedDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isCapturedType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isDefinitelyClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isFlexibleNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isMarkedNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    @ObsoleteTypeKind
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNotNullTypeParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isNothingConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableAny(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isRawType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.$$delegate_0.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.$$delegate_0.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.$$delegate_0.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.$$delegate_0.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.withNullability(simpleTypeMarker, z);
    }

    public ClassicExpectActualMatchingContext(ModuleDescriptor platformModule, boolean z) {
        Intrinsics.checkNotNullParameter(platformModule, "platformModule");
        this.platformModule = platformModule;
        this.shouldCheckAbsenceOfDefaultParamsInActual = z;
        this.$$delegate_0 = new ClassicTypeSystemContextForCS(platformModule.getBuiltIns(), KotlinTypeRefiner.Default.INSTANCE);
    }

    public /* synthetic */ ClassicExpectActualMatchingContext(ModuleDescriptor moduleDescriptor, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, (i & 2) != 0 ? false : z);
    }

    public final ModuleDescriptor getPlatformModule() {
        return this.platformModule;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getShouldCheckAbsenceOfDefaultParamsInActual() {
        return this.shouldCheckAbsenceOfDefaultParamsInActual;
    }

    private final DeclarationDescriptor asDescriptor(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNull(declarationSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.DeclarationDescriptor");
        return (DeclarationDescriptor) declarationSymbolMarker;
    }

    private final CallableDescriptor asDescriptor(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNull(callableSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        return (CallableDescriptor) callableSymbolMarker;
    }

    private final FunctionDescriptor asDescriptor(FunctionSymbolMarker functionSymbolMarker) {
        Intrinsics.checkNotNull(functionSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
        return (FunctionDescriptor) functionSymbolMarker;
    }

    private final PropertyDescriptor asDescriptor(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNull(propertySymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        return (PropertyDescriptor) propertySymbolMarker;
    }

    private final ValueParameterDescriptor asDescriptor(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNull(valueParameterSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ValueParameterDescriptor");
        return (ValueParameterDescriptor) valueParameterSymbolMarker;
    }

    private final TypeParameterDescriptor asDescriptor(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNull(typeParameterSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
        return (TypeParameterDescriptor) typeParameterSymbolMarker;
    }

    private final ClassifierDescriptorWithTypeParameters asDescriptor(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNull(classLikeSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters");
        return (ClassifierDescriptorWithTypeParameters) classLikeSymbolMarker;
    }

    private final ClassDescriptor asDescriptor(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNull(regularClassSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return (ClassDescriptor) regularClassSymbolMarker;
    }

    private final TypeAliasDescriptor asDescriptor(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNull(typeAliasSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeAliasDescriptor");
        return (TypeAliasDescriptor) typeAliasSymbolMarker;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassId getClassId(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) regularClassSymbolMarker);
        Intrinsics.checkNotNull(classId);
        return classId;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassId getClassId(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeAliasSymbolMarker, "<this>");
        ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) typeAliasSymbolMarker);
        Intrinsics.checkNotNull(classId);
        return classId;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public CallableId getCallableId(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        CallableDescriptor callableDescriptorAsDescriptor = asDescriptor(callableSymbolMarker);
        DeclarationDescriptor containingDeclaration = callableDescriptorAsDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            FqName fqName = ((PackageFragmentDescriptor) containingDeclaration).getFqName();
            Name name2 = callableDescriptorAsDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            return new CallableId(fqName, name2);
        }
        if (!(containingDeclaration instanceof ClassifierDescriptor)) {
            throw new IllegalStateException(("Callable descriptor without callableId: " + callableDescriptorAsDescriptor).toString());
        }
        ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) containingDeclaration);
        Intrinsics.checkNotNull(classId);
        Name name3 = callableDescriptorAsDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        return new CallableId(classId, name3);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Name getParameterName(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        Name name2 = asDescriptor(typeParameterSymbolMarker).getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Name getParameterName(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        Name name2 = asDescriptor(valueParameterSymbolMarker).getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public RegularClassSymbolMarker expandToRegularClass(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeAliasSymbolMarker, "<this>");
        return asDescriptor(typeAliasSymbolMarker).getClassDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassKind getClassKind(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        ClassKind kind = asDescriptor(regularClassSymbolMarker).getKind();
        Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
        return kind;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isCompanion(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        RegularClassSymbolMarker regularClassSymbolMarker2 = regularClassSymbolMarker;
        if (!(regularClassSymbolMarker2 instanceof ClassDescriptor)) {
            regularClassSymbolMarker2 = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) regularClassSymbolMarker2;
        return classDescriptor != null && classDescriptor.getIsCompanionObject();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInner(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asDescriptor(regularClassSymbolMarker).getIsInner();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInline(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        RegularClassSymbolMarker regularClassSymbolMarker2 = regularClassSymbolMarker;
        if (!(regularClassSymbolMarker2 instanceof ClassDescriptor)) {
            regularClassSymbolMarker2 = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) regularClassSymbolMarker2;
        return classDescriptor != null && classDescriptor.isInline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isValue(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        RegularClassSymbolMarker regularClassSymbolMarker2 = regularClassSymbolMarker;
        if (!(regularClassSymbolMarker2 instanceof ClassDescriptor)) {
            regularClassSymbolMarker2 = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) regularClassSymbolMarker2;
        return classDescriptor != null && classDescriptor.isValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isFun(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        RegularClassSymbolMarker regularClassSymbolMarker2 = regularClassSymbolMarker;
        if (!(regularClassSymbolMarker2 instanceof ClassDescriptor)) {
            regularClassSymbolMarker2 = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) regularClassSymbolMarker2;
        return classDescriptor != null && classDescriptor.isFun();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<TypeParameterSymbolMarker> getTypeParameters(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        List<TypeParameterDescriptor> declaredTypeParameters = asDescriptor(classLikeSymbolMarker).getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        return declaredTypeParameters;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Modality getModality(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        Modality modality = asDescriptor(classLikeSymbolMarker).getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        return modality;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Visibility getVisibility(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        return asDescriptor(classLikeSymbolMarker).getVisibilityImpl().getDelegate();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Modality getModality(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        CallableSymbolMarker callableSymbolMarker2 = callableSymbolMarker;
        if (!(callableSymbolMarker2 instanceof CallableMemberDescriptor)) {
            callableSymbolMarker2 = null;
        }
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) callableSymbolMarker2;
        if (callableMemberDescriptor != null) {
            return callableMemberDescriptor.getModality();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Visibility getVisibility(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asDescriptor(callableSymbolMarker).getVisibilityImpl().getDelegate();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<KotlinTypeMarker> getSuperTypes(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        Collection<KotlinType> collectionMo7438getSupertypes = asDescriptor(regularClassSymbolMarker).getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        return CollectionsKt.toList(collectionMo7438getSupertypes);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isExpect(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        CallableSymbolMarker callableSymbolMarker2 = callableSymbolMarker;
        if (!(callableSymbolMarker2 instanceof MemberDescriptor)) {
            callableSymbolMarker2 = null;
        }
        MemberDescriptor memberDescriptor = (MemberDescriptor) callableSymbolMarker2;
        return memberDescriptor != null && memberDescriptor.get$isExpect();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInline(CallableSymbolMarker callableSymbolMarker) {
        PropertyGetterDescriptor getter;
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof FunctionDescriptor) {
            return ((FunctionDescriptor) callableSymbolMarker).isInline();
        }
        return (callableSymbolMarker instanceof PropertyDescriptor) && (getter = ((PropertyDescriptor) callableSymbolMarker).getGetter()) != null && getter.isInline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isSuspend(CallableSymbolMarker callableSymbolMarker) {
        PropertyGetterDescriptor getter;
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof FunctionDescriptor) {
            return ((FunctionDescriptor) callableSymbolMarker).isSuspend();
        }
        return (callableSymbolMarker instanceof PropertyDescriptor) && (getter = ((PropertyDescriptor) callableSymbolMarker).getGetter()) != null && getter.isSuspend();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isExternal(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        CallableSymbolMarker callableSymbolMarker2 = callableSymbolMarker;
        if (!(callableSymbolMarker2 instanceof MemberDescriptor)) {
            callableSymbolMarker2 = null;
        }
        MemberDescriptor memberDescriptor = (MemberDescriptor) callableSymbolMarker2;
        return memberDescriptor != null && memberDescriptor.isExternal();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInfix(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        CallableSymbolMarker callableSymbolMarker2 = callableSymbolMarker;
        if (!(callableSymbolMarker2 instanceof FunctionDescriptor)) {
            callableSymbolMarker2 = null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableSymbolMarker2;
        return functionDescriptor != null && functionDescriptor.isInfix();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isOperator(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        CallableSymbolMarker callableSymbolMarker2 = callableSymbolMarker;
        if (!(callableSymbolMarker2 instanceof FunctionDescriptor)) {
            callableSymbolMarker2 = null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableSymbolMarker2;
        return functionDescriptor != null && functionDescriptor.isOperator();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isTailrec(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        CallableSymbolMarker callableSymbolMarker2 = callableSymbolMarker;
        if (!(callableSymbolMarker2 instanceof FunctionDescriptor)) {
            callableSymbolMarker2 = null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableSymbolMarker2;
        return functionDescriptor != null && functionDescriptor.isTailrec();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isVar(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asDescriptor(propertySymbolMarker).isVar();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isLateinit(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asDescriptor(propertySymbolMarker).isLateInit();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isConst(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asDescriptor(propertySymbolMarker).isConst();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public FunctionSymbolMarker getSetter(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asDescriptor(propertySymbolMarker).getSetter();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<DeclarationSymbolMarker> collectAllMembers(RegularClassSymbolMarker regularClassSymbolMarker, boolean z) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return getMembers(asDescriptor(regularClassSymbolMarker), null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<DeclarationSymbolMarker> getMembersForExpectClass(RegularClassSymbolMarker regularClassSymbolMarker, Name name2) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        return getMembers(asDescriptor(regularClassSymbolMarker), name2);
    }

    private final List<MemberDescriptor> getMembers(ClassDescriptor classDescriptor, final Name name2) {
        Function1<Name, Boolean> all_name_filter = name2 != null ? new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ClassicExpectActualMatchingContext$getMembers$nameFilter$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it, name2));
            }
        } : MemberScope.INSTANCE.getALL_NAME_FILTER();
        Collection descriptorsFiltered$default = MemberScopeKt.getDescriptorsFiltered$default(classDescriptor.getDefaultType().getMemberScope(), null, all_name_filter, 1, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : descriptorsFiltered$default) {
            if (obj instanceof MemberDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (!DescriptorUtils.isEnumEntry((DeclarationDescriptor) obj2)) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = arrayList2;
        Collection<ClassConstructorDescriptor> constructors = classDescriptor.getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : constructors) {
            Name name3 = ((ClassConstructorDescriptor) obj3).getShortName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            if (all_name_filter.invoke(name3).booleanValue()) {
                arrayList4.add(obj3);
            }
        }
        return CollectionsKt.plus((Collection) arrayList3, (Iterable) arrayList4);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<Name> collectEnumEntryNames(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        MemberScope unsubstitutedMemberScope = asDescriptor(regularClassSymbolMarker).getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Collection descriptorsFiltered$default = MemberScopeKt.getDescriptorsFiltered$default(unsubstitutedMemberScope, null, null, 3, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : descriptorsFiltered$default) {
            if (DescriptorUtils.isEnumEntry((DeclarationDescriptor) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((DeclarationDescriptor) it.next()).getShortName());
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getDispatchReceiverType(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = asDescriptor(callableSymbolMarker).mo6753getDispatchReceiverParameter();
        return receiverParameterDescriptorMo6753getDispatchReceiverParameter != null ? receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType() : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getExtensionReceiverType(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        ReceiverParameterDescriptor extensionReceiverParameter = asDescriptor(callableSymbolMarker).getExtensionReceiverParameter();
        return extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getReturnType(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        KotlinType returnType = asDescriptor(callableSymbolMarker).getReturnType();
        Intrinsics.checkNotNull(returnType);
        return returnType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<TypeParameterSymbolMarker> getTypeParameters(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        List<TypeParameterDescriptor> typeParameters = asDescriptor(callableSymbolMarker).getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<ValueParameterSymbolMarker> getValueParameters(FunctionSymbolMarker functionSymbolMarker) {
        Intrinsics.checkNotNullParameter(functionSymbolMarker, "<this>");
        List<ValueParameterDescriptor> valueParameters = asDescriptor(functionSymbolMarker).getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        return valueParameters;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isVararg(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asDescriptor(valueParameterSymbolMarker).getVarargElementType() != null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isNoinline(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asDescriptor(valueParameterSymbolMarker).getIsNoinline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isCrossinline(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asDescriptor(valueParameterSymbolMarker).getIsCrossinline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasDefaultValue(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asDescriptor(valueParameterSymbolMarker).declaresDefaultValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Sequence<CallableSymbolMarker> allOverriddenDeclarationsRecursive(FunctionSymbolMarker functionSymbolMarker) {
        Intrinsics.checkNotNullParameter(functionSymbolMarker, "<this>");
        return SequencesKt.filter(SequencesKt.plus(SequencesKt.sequenceOf(asDescriptor(functionSymbolMarker)), (Sequence) DescriptorUtilsKt.overriddenTreeAsSequence(asDescriptor(functionSymbolMarker), true)), new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ClassicExpectActualMatchingContext.allOverriddenDeclarationsRecursive.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE || it.getKind() == CallableMemberDescriptor.Kind.DELEGATION) ? false : true);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isAnnotationConstructor(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        CallableSymbolMarker callableSymbolMarker2 = callableSymbolMarker;
        if (!(callableSymbolMarker2 instanceof ConstructorDescriptor)) {
            callableSymbolMarker2 = null;
        }
        ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) callableSymbolMarker2;
        if (constructorDescriptor == null) {
            return false;
        }
        return DescriptorUtils.isAnnotationClass(constructorDescriptor.getConstructedClass());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<KotlinTypeMarker> getBounds(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        List<KotlinType> upperBounds = asDescriptor(typeParameterSymbolMarker).getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        return upperBounds;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Variance getVariance(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        Variance variance = asDescriptor(typeParameterSymbolMarker).getVariance();
        Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
        return variance;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isReified(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        return asDescriptor(typeParameterSymbolMarker).isReified();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean areCompatibleExpectActualTypes(KotlinTypeMarker expectType, KotlinTypeMarker actualType) {
        if (expectType == null) {
            return actualType == null;
        }
        if (actualType == null) {
            return false;
        }
        if (!(expectType instanceof KotlinType) || !(actualType instanceof KotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (DescriptorUtilsKt.isTypeRefinementEnabled(this.platformModule)) {
            return areCompatibleTypesViaTypeRefinement((KotlinType) expectType, (KotlinType) actualType);
        }
        return areCompatibleTypesViaTypeContext((KotlinType) expectType, (KotlinType) actualType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getDefaultType(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        SimpleType defaultType = asDescriptor(regularClassSymbolMarker).getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return defaultType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean actualTypeIsSubtypeOfExpectType(KotlinTypeMarker expectType, KotlinTypeMarker actualType) {
        Intrinsics.checkNotNullParameter(expectType, "expectType");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        AddToStdlibKt.shouldNotBeCalled("Checking for subtyping is used only in FIR and IR implementations");
        throw null;
    }

    private final boolean areCompatibleTypesViaTypeRefinement(KotlinType a, KotlinType b) {
        KotlinTypeRefinerImpl kotlinTypeRefiner = DescriptorUtilsKt.getKotlinTypeRefiner(this.platformModule);
        if (kotlinTypeRefiner instanceof KotlinTypeRefiner.Default) {
            kotlinTypeRefiner = KotlinTypeRefinerImpl.INSTANCE.createStandaloneInstanceFor(this.platformModule);
        }
        return areCompatibleTypes(a, b, SimpleClassicTypeSystemContext.INSTANCE, kotlinTypeRefiner);
    }

    private final boolean areCompatibleTypesViaTypeContext(KotlinType a, KotlinType b) {
        return areCompatibleTypes(a, b, new ClassicTypeSystemContext() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ClassicExpectActualMatchingContext$areCompatibleTypesViaTypeContext$typeSystemContext$1
            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
            public SimpleTypeMarker anyType() {
                return ClassicTypeSystemContext.DefaultImpls.anyType(this);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.argumentsCount(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public SimpleTypeMarker arrayType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.arrayType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.asArgumentList(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.asCapturedType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.asDefinitelyNotNullType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.asDynamicType(this, flexibleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.asFlexibleType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.asSimpleType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.asTypeArgument(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.canHaveUndefinedNullability(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker captureFromArguments(SimpleTypeMarker simpleTypeMarker, CaptureStatus captureStatus) {
                return ClassicTypeSystemContext.DefaultImpls.captureFromArguments(this, simpleTypeMarker, captureStatus);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker captureFromExpression(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.captureFromExpression(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.captureStatus(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> function1) {
                return ClassicTypeSystemContext.DefaultImpls.contains(this, kotlinTypeMarker, function1);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.convertToNonRaw(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public CapturedTypeMarker createCapturedType(TypeArgumentMarker typeArgumentMarker, List<? extends KotlinTypeMarker> list, KotlinTypeMarker kotlinTypeMarker, CaptureStatus captureStatus) {
                return ClassicTypeSystemContext.DefaultImpls.createCapturedType(this, typeArgumentMarker, list, kotlinTypeMarker, captureStatus);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeSubstitutorMarker createEmptySubstitutor() {
                return ClassicTypeSystemContext.DefaultImpls.createEmptySubstitutor(this);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public SimpleTypeMarker createErrorType(String str) {
                return ClassicTypeSystemContext.DefaultImpls.createErrorType(this, str);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public KotlinTypeMarker createFlexibleType(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
                return ClassicTypeSystemContext.DefaultImpls.createFlexibleType(this, simpleTypeMarker, simpleTypeMarker2);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public SimpleTypeMarker createSimpleType(TypeConstructorMarker typeConstructorMarker, List<? extends TypeArgumentMarker> list, boolean z, boolean z2, List<? extends AnnotationMarker> list2) {
                return ClassicTypeSystemContext.DefaultImpls.createSimpleType(this, typeConstructorMarker, list, z, z2, list2);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameterMarker) {
                return ClassicTypeSystemContext.DefaultImpls.createStarProjection(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariableMarker) {
                return ClassicTypeSystemContext.DefaultImpls.createStubTypeForBuilderInference(this, typeVariableMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariableMarker) {
                return ClassicTypeSystemContext.DefaultImpls.createStubTypeForTypeVariablesInSubtyping(this, typeVariableMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.createSubstitutorForSuperTypes(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public TypeArgumentMarker createTypeArgument(KotlinTypeMarker kotlinTypeMarker, TypeVariance typeVariance) {
                return ClassicTypeSystemContext.DefaultImpls.createTypeArgument(this, kotlinTypeMarker, typeVariance);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
                return ClassicTypeSystemContext.DefaultImpls.createTypeWithAlternativeForIntersectionResult(this, kotlinTypeMarker, kotlinTypeMarker2);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public KotlinTypeMarker createUninferredType(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.createUninferredType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker) {
                return ClassicTypeSystemContext.DefaultImpls.defaultType(this, typeVariableMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.eraseContainingTypeParameters(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.extractArgumentsForFunctionTypeOrSubtype(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> list) {
                return ClassicTypeSystemContext.DefaultImpls.findCommonIntegerLiteralTypesSuperType(this, list);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker) {
                return ClassicTypeSystemContext.DefaultImpls.freshTypeConstructor(this, typeVariableMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.functionTypeKind(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
                return ClassicTypeSystemContext.DefaultImpls.getAnnotationFirstArgumentValue(this, kotlinTypeMarker, fqName);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getApproximatedIntegerLiteralType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
                return ClassicTypeSystemContext.DefaultImpls.getArgument(this, kotlinTypeMarker, i);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getArguments(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getAttributes(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext
            public KotlinBuiltIns getBuiltIns() {
                return ClassicTypeSystemContext.DefaultImpls.getBuiltIns(this);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getClassFqNameUnsafe(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getCustomAttributes(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getFunctionTypeFromSupertypes(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public Name getName(TypeParameterMarker typeParameterMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getName(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public TypeConstructorMarker getNonReflectFunctionTypeConstructor(int i, FunctionTypeKind functionTypeKind) {
                return ClassicTypeSystemContext.DefaultImpls.getNonReflectFunctionTypeConstructor(this, i, functionTypeKind);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getOriginalTypeVariable(this, stubTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
                return ClassicTypeSystemContext.DefaultImpls.getParameter(this, typeConstructorMarker, i);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getParameters(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getPrimitiveArrayType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getPrimitiveType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public TypeConstructorMarker getReflectFunctionTypeConstructor(int i, FunctionTypeKind functionTypeKind) {
                return ClassicTypeSystemContext.DefaultImpls.getReflectFunctionTypeConstructor(this, i, functionTypeKind);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getRepresentativeUpperBound(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getSubstitutedUnderlyingType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getType(this, typeArgumentMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getTypeConstructor(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getTypeParameter(this, typeVariableTypeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getTypeParameterClassifier(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getUnsubstitutedUnderlyingType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
                return ClassicTypeSystemContext.DefaultImpls.getUpperBound(this, typeParameterMarker, i);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getUpperBounds(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getValueClassProperties(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getVariance(this, typeArgumentMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
                return ClassicTypeSystemContext.DefaultImpls.getVariance(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
                return ClassicTypeSystemContext.DefaultImpls.hasAnnotation(this, kotlinTypeMarker, fqName);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.hasCustomAttributes(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.hasExactAnnotation(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.hasNoInferAnnotation(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.hasRecursiveBounds(this, typeParameterMarker, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
            public boolean identicalArguments(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
                return ClassicTypeSystemContext.DefaultImpls.identicalArguments(this, simpleTypeMarker, simpleTypeMarker2);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> list) {
                return ClassicTypeSystemContext.DefaultImpls.intersectTypes(this, list);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            /* JADX INFO: renamed from: intersectTypes */
            public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> list) {
                return ClassicTypeSystemContext.DefaultImpls.m7476intersectTypes((ClassicTypeSystemContext) this, list);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isAnonymous(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isAnyConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isArrayOrNullableArray(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isBuiltinFunctionTypeOrSubtype(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isCapturedTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isClassTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isCommonFinalClassConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isContainedInInvariantOrContravariantPositions(this, typeVariableTypeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isDenotable(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isError(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isError(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isError(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isExtensionFunction(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isExtensionFunctionType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isFinalClassConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isFinalClassOrEnumEntryOrAnnotationClassConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isFunctionOrKFunctionWithAnySuspendability(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isInlineClass(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isInnerClass(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isInnerClass(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isIntegerConstantOperatorTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralConstantTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isInterface(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isInterfaceOrAnnotationClass(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isIntersection(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isK2() {
                return ClassicTypeSystemContext.DefaultImpls.isK2(this);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isLocalType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isMultiFieldValueClass(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isNotNullTypeParameter(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isNothingConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isNullableType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isOldCapturedType(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isPrimitiveType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isProjectionNotNull(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isRawType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isReified(TypeParameterMarker typeParameterMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isReified(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isSignedOrUnsignedNumberType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isSingleClassifierType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isSpecial(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isSpecial(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isStarProjection(this, typeArgumentMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isStubType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isStubTypeForBuilderInference(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isStubTypeForVariableInSubtyping(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isTypeParameterTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isTypeVariable(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isTypeVariableType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isUnderKotlinPackage(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isUninferredParameter(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isUnit(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isUnit(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.isUnitTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.lowerBound(this, flexibleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.lowerType(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.makeDefinitelyNotNullOrNotNull(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.makeSimpleTypeDefinitelyNotNullOrNotNull(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
            public TypeCheckerState newTypeCheckerState(boolean z, boolean z2) {
                return ClassicTypeSystemContext.DefaultImpls.newTypeCheckerState(this, z, z2);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
            public SimpleTypeMarker nothingType() {
                return ClassicTypeSystemContext.DefaultImpls.nothingType(this);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
            public SimpleTypeMarker nullableAnyType() {
                return ClassicTypeSystemContext.DefaultImpls.nullableAnyType(this);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
            public SimpleTypeMarker nullableNothingType() {
                return ClassicTypeSystemContext.DefaultImpls.nullableNothingType(this);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.original(this, definitelyNotNullTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.parametersCount(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.possibleIntegerTypes(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.projection(this, capturedTypeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.removeAnnotations(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.removeExactAnnotation(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> list) {
                return ClassicTypeSystemContext.DefaultImpls.replaceArguments(this, simpleTypeMarker, list);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> function1) {
                return ClassicTypeSystemContext.DefaultImpls.replaceArguments(this, simpleTypeMarker, function1);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> list) {
                return ClassicTypeSystemContext.DefaultImpls.replaceCustomAttributes(this, kotlinTypeMarker, list);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.replaceType(this, typeArgumentMarker, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker kotlinTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.safeSubstitute(this, typeSubstitutorMarker, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection) {
                return ClassicTypeSystemContext.DefaultImpls.singleBestRepresentative(this, collection);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.substitutionSupertypePolicy(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.supertypes(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.toErrorType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.typeConstructor((ClassicTypeSystemContext) this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.typeConstructor(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.typeConstructorProjection(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.typeDepth(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.typeParameter(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
                return ClassicTypeSystemContext.DefaultImpls.typeSubstitutorByTypeConstructor(this, map);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> list) {
                return ClassicTypeSystemContext.DefaultImpls.unionTypeAttributes(this, list);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
                return ClassicTypeSystemContext.DefaultImpls.unwrapStubTypeVariableConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.upperBound(this, flexibleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
                return ClassicTypeSystemContext.DefaultImpls.upperBoundCount(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public boolean useRefinedBoundsForTypeVariableInFlexiblePosition() {
                return ClassicTypeSystemContext.DefaultImpls.useRefinedBoundsForTypeVariableInFlexiblePosition(this);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
            public KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker) {
                return ClassicTypeSystemContext.DefaultImpls.withNotNullProjection(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
                return ClassicTypeSystemContext.DefaultImpls.withNullability(this, kotlinTypeMarker, z);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
                return ClassicTypeSystemContext.DefaultImpls.withNullability((ClassicTypeSystemContext) this, simpleTypeMarker, z);
            }

            @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
                Intrinsics.checkNotNullParameter(c1, "c1");
                Intrinsics.checkNotNullParameter(c2, "c2");
                if (!(c1 instanceof TypeConstructor)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (!(c2 instanceof TypeConstructor)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                ClassicExpectActualMatchingContext classicExpectActualMatchingContext = this.this$0;
                TypeConstructor typeConstructor = (TypeConstructor) c1;
                TypeConstructor typeConstructor2 = (TypeConstructor) c2;
                if (!classicExpectActualMatchingContext.isExpectedClassAndActualTypeAlias(typeConstructor, typeConstructor2, classicExpectActualMatchingContext.getPlatformModule())) {
                    ClassicExpectActualMatchingContext classicExpectActualMatchingContext2 = this.this$0;
                    if (!classicExpectActualMatchingContext2.isExpectedClassAndActualTypeAlias(typeConstructor2, typeConstructor, classicExpectActualMatchingContext2.getPlatformModule()) && !ClassicTypeSystemContext.DefaultImpls.areEqualTypeConstructors(this, c1, c2)) {
                        return false;
                    }
                }
                return true;
            }
        }, KotlinTypeRefiner.Default.INSTANCE);
    }

    private final boolean areCompatibleTypes(KotlinType a, KotlinType b, ClassicTypeSystemContext typeSystemContext, KotlinTypeRefiner kotlinTypeRefiner) {
        return new NewKotlinTypeCheckerImpl(kotlinTypeRefiner, null, 2, null).equalTypes(ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(false, false, typeSystemContext, null, kotlinTypeRefiner, 10, null), a.unwrap(), b.unwrap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isExpectedClassAndActualTypeAlias(TypeConstructor expectedTypeConstructor, TypeConstructor actualTypeConstructor, ModuleDescriptor platformModule) {
        ClassifierDescriptor this$0 = expectedTypeConstructor.getThis$0();
        ClassifierDescriptor this$1 = actualTypeConstructor.getThis$0();
        if ((this$0 instanceof ClassifierDescriptorWithTypeParameters) && ((ClassifierDescriptorWithTypeParameters) this$0).get$isExpect() && (this$1 instanceof ClassifierDescriptorWithTypeParameters)) {
            Collection<ClassifierDescriptorWithTypeParameters> collectionFindClassifiersFromModule = findClassifiersFromModule(DescriptorUtilsKt.getClassId(this$0), platformModule, ModuleFilterUtilsKt.getALL_MODULES());
            if (!(collectionFindClassifiersFromModule instanceof Collection) || !collectionFindClassifiersFromModule.isEmpty()) {
                for (ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters : collectionFindClassifiersFromModule) {
                    TypeAliasDescriptor typeAliasDescriptor = classifierDescriptorWithTypeParameters instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) classifierDescriptorWithTypeParameters : null;
                    if (Intrinsics.areEqual(typeAliasDescriptor != null ? typeAliasDescriptor.getClassDescriptor() : null, this$1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final Collection<ClassifierDescriptorWithTypeParameters> findClassifiersFromModule(ClassId classId, ModuleDescriptor module, Function1<? super ModuleDescriptor, Boolean> moduleFilter) {
        ClassifierDescriptor contributedClassifier;
        MemberScope unsubstitutedInnerClassesScope;
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(moduleFilter, "moduleFilter");
        if (classId == null) {
            return CollectionsKt.emptyList();
        }
        List<Name> listPathSegments = classId.getRelativeClassName().pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        MemberScope memberScope = module.getPackage(packageFqName).getMemberScope();
        Object objFirst = CollectionsKt.first((List<? extends Object>) listPathSegments);
        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
        ArrayList arrayListApplyFilter = ModuleFilterUtilsKt.applyFilter(findClassifiersFromModule$getAllClassifiers(memberScope, (Name) objFirst), moduleFilter);
        for (Name name2 : listPathSegments.subList(1, listPathSegments.size())) {
            ArrayList arrayList = new ArrayList();
            for (ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters : arrayListApplyFilter) {
                ClassDescriptor classDescriptor = classifierDescriptorWithTypeParameters instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorWithTypeParameters : null;
                if (classDescriptor == null || (unsubstitutedInnerClassesScope = classDescriptor.getUnsubstitutedInnerClassesScope()) == null) {
                    contributedClassifier = null;
                } else {
                    Intrinsics.checkNotNull(name2);
                    contributedClassifier = unsubstitutedInnerClassesScope.mo7465getContributedClassifier(name2, NoLookupLocation.FOR_ALREADY_TRACKED);
                }
                ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters2 = contributedClassifier instanceof ClassifierDescriptorWithTypeParameters ? (ClassifierDescriptorWithTypeParameters) contributedClassifier : null;
                if (classifierDescriptorWithTypeParameters2 != null) {
                    arrayList.add(classifierDescriptorWithTypeParameters2);
                }
            }
            arrayListApplyFilter = arrayList;
        }
        return arrayListApplyFilter;
    }

    private static final Collection<ClassifierDescriptorWithTypeParameters> findClassifiersFromModule$getAllClassifiers(MemberScope memberScope, final Name name2) {
        Collection<DeclarationDescriptor> descriptorsFiltered = MemberScopeKt.getDescriptorsFiltered(memberScope, DescriptorKindFilter.CLASSIFIERS, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ClassicExpectActualMatchingContext$findClassifiersFromModule$getAllClassifiers$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it, name2));
            }
        });
        ArrayList arrayList = new ArrayList();
        for (Object obj : descriptorsFiltered) {
            if (obj instanceof ClassifierDescriptorWithTypeParameters) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isNotSamInterface(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        ClassDescriptor classDescriptorAsDescriptor = asDescriptor(regularClassSymbolMarker);
        return classDescriptorAsDescriptor.isDefinitelyNotSamInterface() || classDescriptorAsDescriptor.getDefaultFunctionTypeForSamInterface() == null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean shouldSkipMatching(CallableSymbolMarker callableSymbolMarker, RegularClassSymbolMarker containingExpectClass) {
        CallableMemberDescriptor.Kind kind;
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        Intrinsics.checkNotNullParameter(containingExpectClass, "containingExpectClass");
        CallableSymbolMarker callableSymbolMarker2 = callableSymbolMarker;
        if (!(callableSymbolMarker2 instanceof CallableMemberDescriptor)) {
            callableSymbolMarker2 = null;
        }
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) callableSymbolMarker2;
        return (callableMemberDescriptor == null || (kind = callableMemberDescriptor.getKind()) == null || kind.isReal()) ? false : true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasStableParameterNames(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asDescriptor(callableSymbolMarker).hasStableParameterNames();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<ExpectActualMatchingContext.AnnotationCallInfo> getAnnotations(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNullParameter(declarationSymbolMarker, "<this>");
        Annotations annotations = asDescriptor(declarationSymbolMarker).getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<AnnotationDescriptor> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(new AnnotationCallInfoImpl(this, it.next()));
        }
        return arrayList;
    }

    private static final AnnotationDescriptor areAnnotationArgumentsEqual$getDescriptor(ExpectActualMatchingContext.AnnotationCallInfo annotationCallInfo) {
        Intrinsics.checkNotNull(annotationCallInfo, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.multiplatform.ClassicExpectActualMatchingContext.AnnotationCallInfoImpl");
        return ((AnnotationCallInfoImpl) annotationCallInfo).getAnnotationDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean areAnnotationArgumentsEqual(ExpectActualMatchingContext.AnnotationCallInfo expectAnnotation, ExpectActualMatchingContext.AnnotationCallInfo actualAnnotation, ExpectActualCollectionArgumentsCompatibilityCheckStrategy collectionArgumentsCompatibilityCheckStrategy) {
        Intrinsics.checkNotNullParameter(expectAnnotation, "expectAnnotation");
        Intrinsics.checkNotNullParameter(actualAnnotation, "actualAnnotation");
        Intrinsics.checkNotNullParameter(collectionArgumentsCompatibilityCheckStrategy, "collectionArgumentsCompatibilityCheckStrategy");
        return ConstExpressionValuesEqualityCheckerKt.areExpressionConstValuesEqual(this, areAnnotationArgumentsEqual$getDescriptor(expectAnnotation), areAnnotationArgumentsEqual$getDescriptor(actualAnnotation), collectionArgumentsCompatibilityCheckStrategy);
    }

    /* JADX INFO: compiled from: ClassicExpectActualMatchingContext.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ClassicExpectActualMatchingContext$AnnotationCallInfoImpl;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "annotationDescriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "(Lorg/jetbrains/kotlin/resolve/multiplatform/ClassicExpectActualMatchingContext;Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)V", "getAnnotationDescriptor", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "annotationSymbol", "getAnnotationSymbol", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "isOptIn", "", "()Z", "isRetentionSource", "getAnnotationClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class AnnotationCallInfoImpl implements ExpectActualMatchingContext.AnnotationCallInfo {
        private final AnnotationDescriptor annotationDescriptor;
        private final AnnotationDescriptor annotationSymbol;
        final /* synthetic */ ClassicExpectActualMatchingContext this$0;

        public AnnotationCallInfoImpl(ClassicExpectActualMatchingContext classicExpectActualMatchingContext, AnnotationDescriptor annotationDescriptor) {
            Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
            this.this$0 = classicExpectActualMatchingContext;
            this.annotationDescriptor = annotationDescriptor;
            this.annotationSymbol = annotationDescriptor;
        }

        public final AnnotationDescriptor getAnnotationDescriptor() {
            return this.annotationDescriptor;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public AnnotationDescriptor getAnnotationSymbol() {
            return this.annotationSymbol;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public ClassId getClassId() {
            ClassDescriptor annotationClassDescriptor = getAnnotationClassDescriptor();
            if (annotationClassDescriptor != null) {
                return this.this$0.getClassId(annotationClassDescriptor);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public boolean isRetentionSource() {
            ClassDescriptor annotationClassDescriptor = getAnnotationClassDescriptor();
            return (annotationClassDescriptor != null ? DescriptorUtilsKt.getAnnotationRetention(annotationClassDescriptor) : null) == KotlinRetention.SOURCE;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public boolean isOptIn() {
            Annotations annotations;
            ClassDescriptor annotationClassDescriptor = getAnnotationClassDescriptor();
            if (annotationClassDescriptor == null || (annotations = annotationClassDescriptor.getAnnotations()) == null) {
                return false;
            }
            return annotations.hasAnnotation(OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME());
        }

        private final ClassDescriptor getAnnotationClassDescriptor() {
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(this.annotationDescriptor);
            if (annotationClass == null || (annotationClass instanceof ErrorClassDescriptor)) {
                return null;
            }
            if (!annotationClass.get$isExpect()) {
                return annotationClass;
            }
            ClassDescriptor classDescriptorFindExpandedExpectClassInPlatformModule$resolution = this.this$0.findExpandedExpectClassInPlatformModule$resolution(this.this$0.getClassId(annotationClass));
            return classDescriptorFindExpandedExpectClassInPlatformModule$resolution == null ? annotationClass : classDescriptorFindExpandedExpectClassInPlatformModule$resolution;
        }
    }

    public final ClassDescriptor findExpandedExpectClassInPlatformModule$resolution(ClassId originalClassId) {
        Intrinsics.checkNotNullParameter(originalClassId, "originalClassId");
        ClassifierDescriptor classifierDescriptorFindClassifierAcrossModuleDependencies = FindClassInModuleKt.findClassifierAcrossModuleDependencies(this.platformModule, originalClassId);
        if (classifierDescriptorFindClassifierAcrossModuleDependencies instanceof TypeAliasDescriptor) {
            return ((TypeAliasDescriptor) classifierDescriptorFindClassifierAcrossModuleDependencies).getClassDescriptor();
        }
        if (classifierDescriptorFindClassifierAcrossModuleDependencies instanceof ClassDescriptor) {
            return (ClassDescriptor) classifierDescriptorFindClassifierAcrossModuleDependencies;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasSourceAnnotationsErased(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNullParameter(declarationSymbolMarker, "<this>");
        return (!Intrinsics.areEqual(DescriptorUtils.getContainingSourceFile(asDescriptor(declarationSymbolMarker)), SourceFile.NO_SOURCE_FILE) || (declarationSymbolMarker instanceof K1SyntheticClassifierSymbolMarker) || ((declarationSymbolMarker instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) declarationSymbolMarker).getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public TypeSubstitutorMarker createExpectActualTypeParameterSubstitutor(List<? extends TypeParameterSymbolMarker> expectTypeParameters, final List<? extends TypeParameterSymbolMarker> actualTypeParameters, TypeSubstitutorMarker parentSubstitutor) {
        Intrinsics.checkNotNullParameter(expectTypeParameters, "expectTypeParameters");
        Intrinsics.checkNotNullParameter(actualTypeParameters, "actualTypeParameters");
        for (TypeParameterSymbolMarker typeParameterSymbolMarker : expectTypeParameters) {
            if (typeParameterSymbolMarker == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
            }
        }
        for (TypeParameterSymbolMarker typeParameterSymbolMarker2 : actualTypeParameters) {
            if (typeParameterSymbolMarker2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
            }
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(TypeConstructorSubstitution.INSTANCE.createByParametersMap(org.jetbrains.kotlin.utils.CollectionsKt.keysToMap(expectTypeParameters, new Function1<TypeParameterDescriptor, TypeProjection>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ClassicExpectActualMatchingContext$createExpectActualTypeParameterSubstitutor$substitutor$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final TypeProjection invoke(TypeParameterDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SimpleType defaultType = actualTypeParameters.get(it.getIndex()).getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                return TypeUtilsKt.asTypeProjection(defaultType);
            }
        })));
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        if (parentSubstitutor == null) {
            return typeSubstitutorCreate;
        }
        if (!(parentSubstitutor instanceof TypeSubstitutor)) {
            throw new IllegalStateException(("Unsupported substitutor type: " + parentSubstitutor).toString());
        }
        TypeSubstitutor typeSubstitutorCreateChainedSubstitutor = TypeSubstitutor.createChainedSubstitutor(((TypeSubstitutor) parentSubstitutor).getSubstitution(), typeSubstitutorCreate.getSubstitution());
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreateChainedSubstitutor, "createChainedSubstitutor(...)");
        return typeSubstitutorCreateChainedSubstitutor;
    }
}
