package org.jetbrains.kotlin.backend.common.actualizer;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.AbstractIrTypeSubstitutor;
import org.jetbrains.kotlin.ir.types.IrChainedSubstitutor;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeSubstitutor;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBase;
import org.jetbrains.kotlin.ir.types.impl.IrTypeProjectionImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.mpp.ClassLikeSymbolMarker;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.mpp.FunctionSymbolMarker;
import org.jetbrains.kotlin.mpp.PropertySymbolMarker;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeAliasSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeParameterSymbolMarker;
import org.jetbrains.kotlin.mpp.ValueParameterSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualCollectionArgumentsCompatibilityCheckStrategy;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.Variance;
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
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: IrExpectActualMatchingContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009a\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b0\n\u0002\u0010(\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0014\b \u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0004È\u0002É\u0002B!\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u001b\u0010\u008a\u0001\u001a\u00020\u000e2\u0007\u0010\u008b\u0001\u001a\u00020&2\u0007\u0010\u008c\u0001\u001a\u00020&H\u0016J%\u0010\u008d\u0001\u001a\u00020\u000e2\u0007\u0010\u008e\u0001\u001a\u00020!2\u0007\u0010\u008f\u0001\u001a\u00020!2\b\u0010\u0090\u0001\u001a\u00030\u0091\u0001H\u0016J\u001f\u0010\u0092\u0001\u001a\u00020\u000e2\t\u0010\u008b\u0001\u001a\u0004\u0018\u00010&2\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010&H\u0016J\u001e\u0010\u0093\u0001\u001a\u00020\u000e2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u0001H\u0096\u0001J!\u0010\u0097\u0001\u001a\u0005\u0018\u00010\u0098\u00012\b\u0010\u0099\u0001\u001a\u00030\u0098\u00012\b\u0010\u009a\u0001\u001a\u00030\u009b\u0001H\u0096\u0001J\u0015\u0010\u009c\u0001\u001a\u0004\u0018\u00010&2\u0007\u0010\u0099\u0001\u001a\u00020&H\u0096\u0001J\u000b\u0010\u009d\u0001\u001a\u00030\u009e\u0001H\u0096\u0001J4\u0010\u009f\u0001\u001a\u00030\u009e\u00012\r\u0010 \u0001\u001a\b\u0012\u0004\u0012\u00020'0 2\r\u0010¡\u0001\u001a\b\u0012\u0004\u0012\u00020'0 2\n\u0010¢\u0001\u001a\u0005\u0018\u00010\u009e\u0001H\u0016J\n\u0010£\u0001\u001a\u00030¤\u0001H\u0002J\u0017\u0010¥\u0001\u001a\u00020\b2\u0006\u0010/\u001a\u00020\bH\u0000¢\u0006\u0003\b¦\u0001J\u001e\u0010§\u0001\u001a\u00020\u000e2\b\u0010¨\u0001\u001a\u00030\u0098\u00012\b\u0010©\u0001\u001a\u00030\u0098\u0001H\u0096\u0001J\u0019\u0010ª\u0001\u001a\u00020&2\r\u0010«\u0001\u001a\b\u0012\u0004\u0012\u00020&0 H\u0096\u0001J\u001b\u0010ª\u0001\u001a\u00030\u0098\u00012\u000e\u0010«\u0001\u001a\t\u0012\u0005\u0012\u00030\u0098\u00010 H\u0096\u0001J\u001c\u0010¬\u0001\u001a\u00030\u00ad\u00012\u0007\u0010®\u0001\u001a\u00020\u00022\u0007\u0010¯\u0001\u001a\u00020\u0002H&J\u001c\u0010°\u0001\u001a\u00030\u00ad\u00012\u0007\u0010±\u0001\u001a\u00020\t2\u0007\u0010²\u0001\u001a\u00020\tH&J\u001c\u0010³\u0001\u001a\u00030\u00ad\u00012\u0007\u0010®\u0001\u001a\u00020\"2\u0007\u0010¯\u0001\u001a\u00020\"H\u0016J\u0015\u0010´\u0001\u001a\u00030µ\u00012\b\u0010\u0099\u0001\u001a\u00030\u0098\u0001H\u0096\u0001J!\u0010¶\u0001\u001a\u00030\u009e\u00012\u0014\u0010·\u0001\u001a\u000f\u0012\u0005\u0012\u00030\u0095\u0001\u0012\u0004\u0012\u00020&0\u0007H\u0096\u0001J\u000e\u0010¸\u0001\u001a\u00030¹\u0001*\u00020&H\u0002J\u0014\u0010º\u0001\u001a\t\u0012\u0004\u0012\u00020,0»\u0001*\u00020oH\u0016J\u000f\u0010¼\u0001\u001a\u00030½\u0001*\u00020&H\u0096\u0001J\u0010\u0010¾\u0001\u001a\u00030¿\u0001*\u00030\u0098\u0001H\u0096\u0001J\u0012\u0010À\u0001\u001a\u0005\u0018\u00010Á\u0001*\u00030\u0098\u0001H\u0096\u0001J\u0012\u0010Â\u0001\u001a\u0005\u0018\u00010Ã\u0001*\u00030\u0098\u0001H\u0096\u0001J\u0012\u0010Ä\u0001\u001a\u0005\u0018\u00010Å\u0001*\u00030Æ\u0001H\u0096\u0001J\u0011\u0010Ç\u0001\u001a\u0005\u0018\u00010Æ\u0001*\u00020&H\u0096\u0001J\u000e\u0010È\u0001\u001a\u00030É\u0001*\u00020\"H\u0002J\u000e\u0010È\u0001\u001a\u00030Ê\u0001*\u00020oH\u0002J\u000e\u0010È\u0001\u001a\u00030Ë\u0001*\u00020NH\u0002J\u000e\u0010È\u0001\u001a\u00030Ì\u0001*\u000200H\u0002J\u000e\u0010È\u0001\u001a\u00030Í\u0001*\u000203H\u0002J\u000e\u0010È\u0001\u001a\u00030Î\u0001*\u00020'H\u0002J\u000e\u0010È\u0001\u001a\u00030Ï\u0001*\u00020BH\u0002J\u0011\u0010Ð\u0001\u001a\u0005\u0018\u00010\u0098\u0001*\u00020&H\u0096\u0001J\u000f\u0010Ñ\u0001\u001a\u00030Ò\u0001*\u00020&H\u0096\u0001J\u0010\u0010Ó\u0001\u001a\u00030\u009b\u0001*\u00030Á\u0001H\u0096\u0001J\u001c\u0010Ô\u0001\u001a\b\u0012\u0004\u0012\u00020\"0 *\u0002002\u0007\u0010Õ\u0001\u001a\u00020\u000eH\u0016J\u0013\u0010Ö\u0001\u001a\b\u0012\u0004\u0012\u00020h0 *\u000200H\u0016J\u000f\u0010×\u0001\u001a\u0004\u0018\u000100*\u000203H\u0016J\"\u0010Ø\u0001\u001a\u000b\u0012\u0005\u0012\u00030\u0098\u0001\u0018\u00010 *\u00030\u0098\u00012\b\u0010Ù\u0001\u001a\u00030\u0095\u0001H\u0096\u0001J\u001a\u0010Ú\u0001\u001a\u00030Ò\u0001*\u00030¿\u00012\b\u0010Û\u0001\u001a\u00030½\u0001H\u0096\u0003J\u0019\u0010Ü\u0001\u001a\u00030Ò\u0001*\u00020&2\b\u0010Û\u0001\u001a\u00030½\u0001H\u0096\u0001J\u001c\u0010Ý\u0001\u001a\u0005\u0018\u00010Ò\u0001*\u00030\u0098\u00012\b\u0010Û\u0001\u001a\u00030½\u0001H\u0096\u0001J\u0015\u0010Þ\u0001\u001a\t\u0012\u0005\u0012\u00030Ò\u00010 *\u00020&H\u0096\u0001J\u0015\u0010ß\u0001\u001a\t\u0012\u0005\u0012\u00030à\u00010 *\u00020&H\u0096\u0001J\u0015\u0010á\u0001\u001a\t\u0012\u0005\u0012\u00030à\u00010 *\u00020&H\u0096\u0001J\u001c\u0010â\u0001\u001a\b\u0012\u0004\u0012\u00020\"0 *\u0002002\u0007\u0010ã\u0001\u001a\u00020hH\u0016J\u0019\u0010ä\u0001\u001a\u00020v*\u00030\u0095\u00012\b\u0010Û\u0001\u001a\u00030½\u0001H\u0096\u0001J\u0015\u0010å\u0001\u001a\b\u0012\u0004\u0012\u00020v0 *\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010æ\u0001\u001a\u00020&*\u00030Ò\u0001H\u0096\u0001J\u000f\u0010ç\u0001\u001a\u00030\u0095\u0001*\u00020vH\u0096\u0001J\u0011\u0010è\u0001\u001a\u0004\u0018\u00010v*\u00030\u0095\u0001H\u0096\u0001J\u0018\u0010é\u0001\u001a\u00020&*\u00020v2\b\u0010Û\u0001\u001a\u00030½\u0001H\u0096\u0001J\u0014\u0010ê\u0001\u001a\b\u0012\u0004\u0012\u00020&0 *\u00020vH\u0096\u0001J\u0010\u0010\u0083\u0001\u001a\u00030ë\u0001*\u00030Ò\u0001H\u0096\u0001J\u000f\u0010\u0083\u0001\u001a\u00030ë\u0001*\u00020vH\u0096\u0001J\u000e\u0010ì\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000e\u0010í\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u001c\u0010î\u0001\u001a\u00020\u000e*\u00020v2\f\b\u0002\u0010ï\u0001\u001a\u0005\u0018\u00010\u0095\u0001H\u0096\u0001J\r\u0010ð\u0001\u001a\u00020\u000e*\u00020,H\u0016J\u000f\u0010ñ\u0001\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010ò\u0001\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010ó\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000e\u0010ô\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000f\u0010õ\u0001\u001a\u00020\u000e*\u00030\u0098\u0001H\u0096\u0001J\u000f\u0010ö\u0001\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010÷\u0001\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010ø\u0001\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010ù\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000f\u0010ú\u0001\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010û\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000e\u0010ü\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000f\u0010ü\u0001\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010ý\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000e\u0010þ\u0001\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000f\u0010ÿ\u0001\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010\u0080\u0002\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010\u0081\u0002\u001a\u00020\u000e*\u00030\u0098\u0001H\u0096\u0001J\u000f\u0010\u0082\u0002\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010\u0083\u0002\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010\u0084\u0002\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010\u0085\u0002\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010\u0086\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000f\u0010\u0086\u0002\u001a\u00020\u000e*\u00030\u0098\u0001H\u0096\u0001J\u000e\u0010\u0087\u0002\u001a\u00020\u000e*\u00020&H\u0097\u0001J\r\u0010\u0088\u0002\u001a\u00020\u000e*\u000200H\u0016J\u000e\u0010\u0089\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000f\u0010\u008a\u0002\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010\u008b\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000e\u0010\u008c\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000e\u0010\u008d\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000f\u0010\u008e\u0002\u001a\u00020\u000e*\u00030Á\u0001H\u0096\u0001J\u000f\u0010\u008f\u0002\u001a\u00020\u000e*\u00030\u0098\u0001H\u0096\u0001J\u000f\u0010\u0090\u0002\u001a\u00020\u000e*\u00030Á\u0001H\u0096\u0001J\u000e\u0010\u0091\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000e\u0010\u0092\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000f\u0010\u0093\u0002\u001a\u00020\u000e*\u00030\u0098\u0001H\u0096\u0001J\u000f\u0010\u0094\u0002\u001a\u00020\u000e*\u00030Ò\u0001H\u0096\u0001J\u000f\u0010\u0095\u0002\u001a\u00020\u000e*\u00030\u0098\u0001H\u0096\u0001J\u000f\u0010\u0096\u0002\u001a\u00020\u000e*\u00030\u0098\u0001H\u0096\u0001J\u000f\u0010\u0097\u0002\u001a\u00020\u000e*\u00030\u0098\u0001H\u0096\u0001J\u000f\u0010\u0098\u0002\u001a\u00020\u000e*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010\u0099\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u000e\u0010\u009a\u0002\u001a\u00020\u000e*\u00020&H\u0096\u0001J\u0017\u0010\u009b\u0002\u001a\n\u0012\u0005\u0012\u00030Ò\u00010\u009c\u0002*\u00030¿\u0001H\u0096\u0003J\u0010\u0010\u009d\u0002\u001a\u00030\u0098\u0001*\u00030Æ\u0001H\u0096\u0001J\u000f\u0010\u009e\u0002\u001a\u00030\u0098\u0001*\u00020&H\u0096\u0001J\u0011\u0010\u009f\u0002\u001a\u0004\u0018\u00010&*\u00030Á\u0001H\u0096\u0001J\u000e\u0010 \u0002\u001a\u00020&*\u00020&H\u0096\u0001J\u0010\u0010¡\u0002\u001a\u00030\u0098\u0001*\u00030\u0098\u0001H\u0096\u0001J\u0010\u0010¢\u0002\u001a\u00030\u0098\u0001*\u00030Ã\u0001H\u0096\u0001J\u0010\u0010£\u0002\u001a\u00030\u0098\u0001*\u00030\u0098\u0001H\u0096\u0001J\u0010\u0010¤\u0002\u001a\u00030½\u0001*\u00030\u0095\u0001H\u0096\u0001J\u0016\u0010¥\u0002\u001a\t\u0012\u0004\u0012\u00020&0¦\u0002*\u00030\u0098\u0001H\u0096\u0001J\u0094\u0001\u0010§\u0002\u001a\u0003H¨\u0002\"\u0005\b\u0000\u0010¨\u0002*\u00020,2\u0016\u0010©\u0002\u001a\u0011\u0012\u0005\u0012\u00030Ê\u0001\u0012\u0005\u0012\u0003H¨\u00020ª\u00022\u0016\u0010«\u0002\u001a\u0011\u0012\u0005\u0012\u00030Ë\u0001\u0012\u0005\u0012\u0003H¨\u00020ª\u00022\u0016\u0010¬\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u00ad\u0002\u0012\u0005\u0012\u0003H¨\u00020ª\u00022\u0016\u0010®\u0002\u001a\u0011\u0012\u0005\u0012\u00030Ï\u0001\u0012\u0005\u0012\u0003H¨\u00020ª\u00022\u0016\u0010¯\u0002\u001a\u0011\u0012\u0005\u0012\u00030°\u0002\u0012\u0005\u0012\u0003H¨\u00020ª\u0002H\u0082\b¢\u0006\u0003\u0010±\u0002JL\u0010§\u0002\u001a\u0003H¨\u0002\"\u0005\b\u0000\u0010¨\u0002*\u00020e2\u0016\u0010²\u0002\u001a\u0011\u0012\u0005\u0012\u00030Ì\u0001\u0012\u0005\u0012\u0003H¨\u00020ª\u00022\u0016\u0010³\u0002\u001a\u0011\u0012\u0005\u0012\u00030Í\u0001\u0012\u0005\u0012\u0003H¨\u00020ª\u0002H\u0082\b¢\u0006\u0003\u0010´\u0002J\u0010\u0010µ\u0002\u001a\u00030Ò\u0001*\u00030¶\u0002H\u0096\u0001J\u0019\u0010·\u0002\u001a\u00030Ò\u0001*\u00030Ò\u00012\u0007\u0010¸\u0002\u001a\u00020&H\u0096\u0001J%\u0010¹\u0002\u001a\u0005\u0018\u0001Hº\u0002\"\f\b\u0000\u0010º\u0002\u0018\u0001*\u00030É\u0001*\u00020\"H\u0082\b¢\u0006\u0003\u0010»\u0002J\u0018\u0010¼\u0002\u001a\u00020&*\u00030\u009e\u00012\u0007\u0010\u0099\u0001\u001a\u00020&H\u0096\u0001J\u0016\u0010½\u0002\u001a\u00020\u000e*\u00020,2\u0007\u0010¾\u0002\u001a\u000200H\u0016J\u0010\u0010¿\u0002\u001a\u00030½\u0001*\u00030¿\u0001H\u0096\u0001J\u0016\u0010À\u0002\u001a\t\u0012\u0004\u0012\u00020&0¦\u0002*\u00030\u0095\u0001H\u0096\u0001J\u0010\u0010Á\u0002\u001a\u00030¶\u0002*\u00030Á\u0001H\u0096\u0001J\u000f\u0010Á\u0002\u001a\u00030\u0095\u0001*\u00020&H\u0096\u0001J\u0010\u0010Á\u0002\u001a\u00030\u0095\u0001*\u00030\u0098\u0001H\u0096\u0001J\u0010\u0010Â\u0002\u001a\u00030\u0095\u0001*\u00030\u0095\u0001H\u0096\u0001J\u0010\u0010Ã\u0002\u001a\u00030\u0098\u0001*\u00030Æ\u0001H\u0096\u0001J\u000f\u0010Ä\u0002\u001a\u00030½\u0001*\u00020vH\u0096\u0001J\u000f\u0010Å\u0002\u001a\u00030\u0098\u0001*\u00020&H\u0096\u0001J\u0017\u0010Æ\u0002\u001a\u00020&*\u00020&2\u0007\u0010Ç\u0002\u001a\u00020\u000eH\u0096\u0001J\u0019\u0010Æ\u0002\u001a\u00030\u0098\u0001*\u00030\u0098\u00012\u0007\u0010Ç\u0002\u001a\u00020\u000eH\u0096\u0001R\u0012\u0010\u000b\u001a\u00060\fR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0014\u0010\u0013\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0010R\u0014\u0010\u0019\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0010R\u0014\u0010\u001b\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 *\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020&0 *\u00020'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0018\u0010*\u001a\u00020+*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0018\u0010/\u001a\u00020\b*\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00102R\u0018\u0010/\u001a\u00020\b*\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00104R\u0018\u00105\u001a\u000206*\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0018\u00109\u001a\u00020&*\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u001a\u0010<\u001a\u0004\u0018\u00010&*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>R\u001a\u0010?\u001a\u0004\u0018\u00010&*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u0010>R\u0018\u0010A\u001a\u00020\u000e*\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bC\u0010DR\u0018\u0010E\u001a\u00020\u000e*\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0018\u0010H\u001a\u00020\u000e*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0018\u0010K\u001a\u00020\u000e*\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0018\u0010M\u001a\u00020\u000e*\u00020N8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010OR\u0018\u0010P\u001a\u00020\u000e*\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bP\u0010DR\u0018\u0010Q\u001a\u00020\u000e*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010JR\u0018\u0010R\u001a\u00020\u000e*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bR\u0010JR\u0018\u0010S\u001a\u00020\u000e*\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bS\u0010LR\u0018\u0010T\u001a\u00020\u000e*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bT\u0010JR\u0018\u0010U\u001a\u00020\u000e*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bU\u0010JR\u0018\u0010U\u001a\u00020\u000e*\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bU\u0010LR\u0018\u0010V\u001a\u00020\u000e*\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bV\u0010LR\u0018\u0010W\u001a\u00020\u000e*\u00020N8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bW\u0010OR\u0018\u0010X\u001a\u00020\u000e*\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bX\u0010DR\u0018\u0010Y\u001a\u00020\u000e*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bY\u0010JR\u0018\u0010Z\u001a\u00020\u000e*\u00020'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010[R\u0018\u0010\\\u001a\u00020\u000e*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\\\u0010JR\u0018\u0010]\u001a\u00020\u000e*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b]\u0010JR\u0018\u0010^\u001a\u00020\u000e*\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b^\u0010LR\u0018\u0010_\u001a\u00020\u000e*\u00020N8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b_\u0010OR\u0018\u0010`\u001a\u00020\u000e*\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b`\u0010DR\u001a\u0010a\u001a\u0004\u0018\u00010b*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bc\u0010dR\u0018\u0010a\u001a\u00020b*\u00020e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bc\u0010fR\u0018\u0010g\u001a\u00020h*\u00020'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bi\u0010jR\u0018\u0010g\u001a\u00020h*\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bi\u0010kR\u0018\u0010l\u001a\u00020&*\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bm\u0010>R\u001a\u0010n\u001a\u0004\u0018\u00010o*\u00020N8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bp\u0010qR\u001e\u0010r\u001a\b\u0012\u0004\u0012\u00020&0 *\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bs\u0010tR\u0018\u0010u\u001a\u0004\u0018\u00010v*\u00020wX\u0096\u0005¢\u0006\u0006\u001a\u0004\bx\u0010yR\u001e\u0010z\u001a\b\u0012\u0004\u0012\u00020'0 *\u00020,8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b{\u0010|R\u001e\u0010z\u001a\b\u0012\u0004\u0012\u00020'0 *\u00020e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b{\u0010}R\u001f\u0010~\u001a\b\u0012\u0004\u0012\u00020B0 *\u00020o8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u007f\u0010\u0080\u0001R\u001c\u0010\u0081\u0001\u001a\u00030\u0082\u0001*\u00020'8VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0083\u0001\u0010\u0084\u0001R\u001c\u0010\u0085\u0001\u001a\u00030\u0086\u0001*\u00020,8VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0087\u0001\u0010\u0088\u0001R\u001c\u0010\u0085\u0001\u001a\u00030\u0086\u0001*\u00020e8VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0087\u0001\u0010\u0089\u0001¨\u0006Ê\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualMatchingContext;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "typeContext", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "expectToActualClassMap", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Ljava/util/Map;)V", "actualizingSubstitutor", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualMatchingContext$ActualizingSubstitutor;", "allowClassActualizationWithWiderVisibility", "", "getAllowClassActualizationWithWiderVisibility", "()Z", "allowTransitiveSupertypesActualization", "getAllowTransitiveSupertypesActualization", "enumConstructorsAreAlwaysCompatible", "getEnumConstructorsAreAlwaysCompatible", "getExpectToActualClassMap", "()Ljava/util/Map;", "innerClassesCapturesOuterTypeParameters", "getInnerClassesCapturesOuterTypeParameters", "shouldCheckAbsenceOfDefaultParamsInActual", "getShouldCheckAbsenceOfDefaultParamsInActual", "shouldCheckReturnTypesOfCallables", "getShouldCheckReturnTypesOfCallables", "getTypeContext", "()Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "annotations", "", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "getAnnotations", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Ljava/util/List;", "bounds", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;", "getBounds", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Ljava/util/List;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;", "getCallableId", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/name/CallableId;", "classId", "Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;", "getClassId", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;)Lorg/jetbrains/kotlin/name/ClassId;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/ClassKind;", "defaultType", "getDefaultType", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "dispatchReceiverType", "getDispatchReceiverType", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "extensionReceiverType", "getExtensionReceiverType", "hasDefaultValue", "Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;", "getHasDefaultValue", "(Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;)Z", "hasSourceAnnotationsErased", "getHasSourceAnnotationsErased", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Z", "hasStableParameterNames", "getHasStableParameterNames", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Z", "isCompanion", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Z", "isConst", "Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Z", "isCrossinline", "isExpect", "isExternal", "isFun", "isInfix", "isInline", "isInner", "isLateinit", "isNoinline", "isOperator", "isReified", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Z", "isSuspend", "isTailrec", "isValue", "isVar", "isVararg", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Modality;", "Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Modality;", "parameterName", "Lorg/jetbrains/kotlin/name/Name;", "getParameterName", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "returnType", "getReturnType", "setter", "Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "getSetter", "(Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "superTypes", "getSuperTypes", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Ljava/util/List;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "typeParameters", "getTypeParameters", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Ljava/util/List;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Ljava/util/List;", "valueParameters", "getValueParameters", "(Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;)Ljava/util/List;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Lorg/jetbrains/kotlin/types/Variance;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "actualTypeIsSubtypeOfExpectType", "expectType", "actualType", "areAnnotationArgumentsEqual", "expectAnnotation", "actualAnnotation", "collectionArgumentsCompatibilityCheckStrategy", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "areCompatibleExpectActualTypes", "areEqualTypeConstructors", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "captureFromArguments", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createExpectActualTypeParameterSubstitutor", "expectTypeParameters", "actualTypeParameters", "parentSubstitutor", "createTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "getClassIdAfterActualization", "getClassIdAfterActualization$ir_backend_common", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", SpecTypePair.NAME_types, "onMatchedCallables", "", "expectSymbol", "actualSymbol", "onMatchedClasses", "expectClassSymbol", "actualClassSymbol", "onMatchedMembers", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "actualize", "Lorg/jetbrains/kotlin/ir/types/IrType;", "allOverriddenDeclarationsRecursive", "Lkotlin/sequences/Sequence;", "argumentsCount", "", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asIr", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "asSimpleType", "asTypeArgument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "captureStatus", "collectAllMembers", "isActualDeclaration", "collectEnumEntryNames", "expandToRegularClass", "fastCorrespondingSupertypes", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "get", "index", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "getCustomAttributes", "getMembersForExpectClass", "name", "getParameter", "getParameters", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUpperBound", "getUpperBounds", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "hasCustomAttributes", "hasFlexibleNullability", "hasRecursiveBounds", "selfConstructor", "isAnnotationConstructor", "isAnonymous", "isAnyConstructor", "isCapturedDynamic", "isCapturedType", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isFlexible", "isFlexibleNothing", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isIntersection", "isLocalType", "isMarkedNullable", "isNotNullTypeParameter", "isNotSamInterface", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isSimpleType", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUninferredParameter", "iterator", "", "lowerBound", "lowerBoundIfFlexible", "lowerType", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "", "processIr", "R", "onFunction", "Lkotlin/Function1;", "onProperty", "onField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "onValueParameter", "onEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "onClass", "onTypeAlias", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceType", "newType", "safeAsIr", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "safeSubstitute", "shouldSkipMatching", "containingExpectClass", "size", "supertypes", "typeConstructor", "unwrapStubTypeVariableConstructor", "upperBound", "upperBoundCount", "upperBoundIfFlexible", "withNullability", "nullable", "ActualizingSubstitutor", "AnnotationCallInfoImpl", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrExpectActualMatchingContext implements ExpectActualMatchingContext<IrSymbol>, TypeSystemContext {
    private final ActualizingSubstitutor actualizingSubstitutor;
    private final Map<ClassId, IrClassSymbol> expectToActualClassMap;
    private final IrTypeSystemContext typeContext;

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return this.typeContext.areEqualTypeConstructors(c1, c2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.typeContext.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.typeContext.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeContext.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeContext.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.typeContext.createEmptySubstitutor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.typeContext.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.typeContext.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getAllowClassActualizationWithWiderVisibility() {
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getAllowTransitiveSupertypesActualization() {
        return true;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getEnumConstructorsAreAlwaysCompatible() {
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getInnerClassesCapturesOuterTypeParameters() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getShouldCheckAbsenceOfDefaultParamsInActual() {
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getShouldCheckReturnTypesOfCallables() {
        return true;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.typeContext.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeContext.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.typeContext.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeContext.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeContext.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.typeContext.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeContext.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeContext.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return this.typeContext.identicalArguments(a, b);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.typeContext.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes, reason: collision with other method in class */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.typeContext.mo6107intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isCapturedDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isCapturedType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isDefinitelyClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isFlexibleNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.isMarkedNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    @ObsoleteTypeKind
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isNotNullTypeParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isNothingConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isNullableAny(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isNullableNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeContext.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeContext.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isRawType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.typeContext.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.typeContext.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.typeContext.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeContext.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    public abstract void onMatchedCallables(IrSymbol expectSymbol, IrSymbol actualSymbol);

    public abstract void onMatchedClasses(IrClassSymbol expectClassSymbol, IrClassSymbol actualClassSymbol);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.typeContext.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.typeContext.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.typeContext.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeContext.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean shouldSkipMatching(CallableSymbolMarker callableSymbolMarker, RegularClassSymbolMarker containingExpectClass) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        Intrinsics.checkNotNullParameter(containingExpectClass, "containingExpectClass");
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.typeContext.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeContext.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeContext.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.typeContext.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeContext.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.typeContext.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeContext.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeContext.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeContext.withNullability(simpleTypeMarker, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IrExpectActualMatchingContext(IrTypeSystemContext typeContext, Map<ClassId, ? extends IrClassSymbol> expectToActualClassMap) {
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        Intrinsics.checkNotNullParameter(expectToActualClassMap, "expectToActualClassMap");
        this.typeContext = typeContext;
        this.expectToActualClassMap = expectToActualClassMap;
        this.actualizingSubstitutor = new ActualizingSubstitutor();
    }

    public final IrTypeSystemContext getTypeContext() {
        return this.typeContext;
    }

    public final Map<ClassId, IrClassSymbol> getExpectToActualClassMap() {
        return this.expectToActualClassMap;
    }

    private final IrDeclaration asIr(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNull(declarationSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSymbol");
        IrSymbolOwner owner = ((IrSymbol) declarationSymbolMarker).getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        return (IrDeclaration) owner;
    }

    private final IrFunction asIr(FunctionSymbolMarker functionSymbolMarker) {
        Intrinsics.checkNotNull(functionSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol");
        return ((IrFunctionSymbol) functionSymbolMarker).getOwner();
    }

    private final IrProperty asIr(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNull(propertySymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
        return ((IrPropertySymbol) propertySymbolMarker).getOwner();
    }

    private final IrValueParameter asIr(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNull(valueParameterSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol");
        return ((IrValueParameterSymbol) valueParameterSymbolMarker).getOwner();
    }

    private final IrTypeParameter asIr(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNull(typeParameterSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
        return ((IrTypeParameterSymbol) typeParameterSymbolMarker).getOwner();
    }

    private final IrClass asIr(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNull(regularClassSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
        return ((IrClassSymbol) regularClassSymbolMarker).getOwner();
    }

    private final IrTypeAlias asIr(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNull(typeAliasSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol");
        return ((IrTypeAliasSymbol) typeAliasSymbolMarker).getOwner();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassId getClassId(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return AdditionalIrUtilsKt.getClassIdOrFail(asIr(regularClassSymbolMarker));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassId getClassId(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeAliasSymbolMarker, "<this>");
        return AdditionalIrUtilsKt.getClassIdOrFail(asIr(typeAliasSymbolMarker));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Name getParameterName(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        return asIr(typeParameterSymbolMarker).getName();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Name getParameterName(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asIr(valueParameterSymbolMarker).getName();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public RegularClassSymbolMarker expandToRegularClass(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeAliasSymbolMarker, "<this>");
        IrClass irClass = IrTypesKt.getClass(asIr(typeAliasSymbolMarker).getExpandedType());
        return irClass != null ? irClass.getSymbol() : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassKind getClassKind(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asIr(regularClassSymbolMarker).getKind();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isCompanion(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asIr(regularClassSymbolMarker).getIsCompanion();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInner(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asIr(regularClassSymbolMarker).getIsInner();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInline(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asIr(regularClassSymbolMarker).getIsValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isValue(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asIr(regularClassSymbolMarker).getIsValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isFun(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asIr(regularClassSymbolMarker).getIsFun();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Visibility getVisibility(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        IrSymbolOwner owner = ((IrSymbol) classLikeSymbolMarker).getOwner();
        if (!(owner instanceof IrDeclarationWithVisibility)) {
            owner = null;
        }
        IrDeclarationWithVisibility irDeclarationWithVisibility = (IrDeclarationWithVisibility) owner;
        Intrinsics.checkNotNull(irDeclarationWithVisibility);
        return irDeclarationWithVisibility.getVisibility().getDelegate();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Modality getModality(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof IrConstructorSymbol) {
            return Modality.FINAL;
        }
        if (!(callableSymbolMarker instanceof IrSymbol)) {
            AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
            throw null;
        }
        IrSymbolOwner owner = ((IrSymbol) callableSymbolMarker).getOwner();
        IrOverridableMember irOverridableMember = owner instanceof IrOverridableMember ? (IrOverridableMember) owner : null;
        if (irOverridableMember != null) {
            return irOverridableMember.getModality();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Visibility getVisibility(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof IrEnumEntrySymbol) {
            return Visibilities.Public.INSTANCE;
        }
        if (!(callableSymbolMarker instanceof IrSymbol)) {
            AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
            throw null;
        }
        IrSymbolOwner owner = ((IrSymbol) callableSymbolMarker).getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility");
        return ((IrDeclarationWithVisibility) owner).getVisibility().getDelegate();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<KotlinTypeMarker> getSuperTypes(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asIr(regularClassSymbolMarker).getSuperTypes();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getDefaultType(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return IrUtilsKt.getDefaultType(asIr(regularClassSymbolMarker));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isExternal(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        IrSymbolOwner owner = ((IrSymbol) callableSymbolMarker).getOwner();
        if (!(owner instanceof IrPossiblyExternalDeclaration)) {
            owner = null;
        }
        IrPossiblyExternalDeclaration irPossiblyExternalDeclaration = (IrPossiblyExternalDeclaration) owner;
        if (irPossiblyExternalDeclaration != null) {
            return irPossiblyExternalDeclaration.getIsExternal();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInfix(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        IrSymbolOwner owner = ((IrSymbol) callableSymbolMarker).getOwner();
        if (!(owner instanceof IrSimpleFunction)) {
            owner = null;
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) owner;
        if (irSimpleFunction != null) {
            return irSimpleFunction.getIsInfix();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isOperator(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        IrSymbolOwner owner = ((IrSymbol) callableSymbolMarker).getOwner();
        if (!(owner instanceof IrSimpleFunction)) {
            owner = null;
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) owner;
        if (irSimpleFunction != null) {
            return irSimpleFunction.getIsOperator();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isTailrec(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        IrSymbolOwner owner = ((IrSymbol) callableSymbolMarker).getOwner();
        if (!(owner instanceof IrSimpleFunction)) {
            owner = null;
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) owner;
        if (irSimpleFunction != null) {
            return irSimpleFunction.getIsTailrec();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isVar(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asIr(propertySymbolMarker).getIsVar();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isLateinit(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asIr(propertySymbolMarker).getIsLateinit();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isConst(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asIr(propertySymbolMarker).getIsConst();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public FunctionSymbolMarker getSetter(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        IrSimpleFunction setter = asIr(propertySymbolMarker).getSetter();
        return setter != null ? setter.getSymbol() : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<DeclarationSymbolMarker> collectAllMembers(RegularClassSymbolMarker regularClassSymbolMarker, boolean z) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        List<IrDeclaration> declarations = asIr(regularClassSymbolMarker).getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (!(((IrDeclaration) obj) instanceof IrAnonymousInitializer)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((IrDeclaration) it.next()).getSymbol());
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<DeclarationSymbolMarker> getMembersForExpectClass(RegularClassSymbolMarker regularClassSymbolMarker, Name name2) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        List<IrDeclaration> declarations = asIr(regularClassSymbolMarker).getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (Intrinsics.areEqual(AdditionalIrUtilsKt.getNameWithAssert((IrDeclaration) obj), name2)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((IrDeclaration) it.next()).getSymbol());
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<Name> collectEnumEntryNames(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        List<IrDeclaration> declarations = asIr(regularClassSymbolMarker).getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrEnumEntry) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((IrEnumEntry) it.next()).getName());
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getDispatchReceiverType(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        IrDeclarationParent parent = asIr(callableSymbolMarker).getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        return irClass != null ? IrUtilsKt.getDefaultType(irClass) : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getExtensionReceiverType(CallableSymbolMarker callableSymbolMarker) {
        IrValueParameter extensionReceiverParameter;
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        IrSymbolOwner owner = ((IrSymbol) callableSymbolMarker).getOwner();
        IrType type = null;
        if (!(owner instanceof IrFunction)) {
            owner = null;
        }
        IrFunction irFunction = (IrFunction) owner;
        if (irFunction != null && (extensionReceiverParameter = irFunction.getExtensionReceiverParameter()) != null) {
            type = extensionReceiverParameter.getType();
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Sequence<CallableSymbolMarker> allOverriddenDeclarationsRecursive(FunctionSymbolMarker functionSymbolMarker) {
        Intrinsics.checkNotNullParameter(functionSymbolMarker, "<this>");
        throw new NotImplementedError("Not implemented because it's unused");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<ValueParameterSymbolMarker> getValueParameters(FunctionSymbolMarker functionSymbolMarker) {
        Intrinsics.checkNotNullParameter(functionSymbolMarker, "<this>");
        List<IrValueParameter> valueParameters = asIr(functionSymbolMarker).getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrValueParameter) it.next()).getSymbol());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isVararg(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return AdditionalIrUtilsKt.isVararg(asIr(valueParameterSymbolMarker));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isNoinline(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asIr(valueParameterSymbolMarker).getIsNoinline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isCrossinline(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asIr(valueParameterSymbolMarker).getIsCrossinline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasDefaultValue(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return IrUtilsKt.hasDefaultValue(asIr(valueParameterSymbolMarker));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isAnnotationConstructor(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        IrSymbolOwner owner = ((IrSymbol) callableSymbolMarker).getOwner();
        if (!(owner instanceof IrConstructor)) {
            owner = null;
        }
        IrConstructor irConstructor = (IrConstructor) owner;
        if (irConstructor == null) {
            return false;
        }
        return IrUtilsKt.isAnnotationClass(AdditionalIrUtilsKt.getConstructedClass(irConstructor));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<KotlinTypeMarker> getBounds(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        return asIr(typeParameterSymbolMarker).getSuperTypes();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Variance getVariance(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        return asIr(typeParameterSymbolMarker).getVariance();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isReified(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        return asIr(typeParameterSymbolMarker).getIsReified();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean areCompatibleExpectActualTypes(KotlinTypeMarker expectType, KotlinTypeMarker actualType) {
        if (expectType == null) {
            return actualType == null;
        }
        if (actualType == null) {
            return false;
        }
        return AbstractTypeChecker.INSTANCE.equalTypes(createTypeCheckerState(), actualize(expectType), actualize(actualType));
    }

    private final TypeCheckerState createTypeCheckerState() {
        return this.typeContext.newTypeCheckerState(true, false);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean actualTypeIsSubtypeOfExpectType(KotlinTypeMarker expectType, KotlinTypeMarker actualType) {
        Intrinsics.checkNotNullParameter(expectType, "expectType");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, createTypeCheckerState(), (KotlinTypeMarker) actualize(actualType), (KotlinTypeMarker) actualize(expectType), false, 8, (Object) null);
    }

    private final IrType actualize(KotlinTypeMarker kotlinTypeMarker) {
        ActualizingSubstitutor actualizingSubstitutor = this.actualizingSubstitutor;
        Intrinsics.checkNotNull(kotlinTypeMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrType");
        return actualizingSubstitutor.substitute((IrType) kotlinTypeMarker);
    }

    /* JADX INFO: compiled from: IrExpectActualMatchingContext.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualMatchingContext$ActualizingSubstitutor;", "Lorg/jetbrains/kotlin/ir/types/AbstractIrTypeSubstitutor;", "(Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualMatchingContext;)V", "substitute", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "substituteArgumentOrNull", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "argument", "substituteOrNull", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ActualizingSubstitutor extends AbstractIrTypeSubstitutor {
        public ActualizingSubstitutor() {
        }

        @Override // org.jetbrains.kotlin.ir.types.AbstractIrTypeSubstitutor
        public IrType substitute(IrType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            IrType irTypeSubstituteOrNull = substituteOrNull(type);
            return irTypeSubstituteOrNull == null ? type : irTypeSubstituteOrNull;
        }

        private final IrType substituteOrNull(IrType type) {
            if (!(type instanceof IrSimpleTypeImpl)) {
                return null;
            }
            IrSimpleTypeImpl irSimpleTypeImpl = (IrSimpleTypeImpl) type;
            IrSymbolOwner owner = irSimpleTypeImpl.getClassifier().getOwner();
            IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
            IrClassSymbol irClassSymbol = irClass != null ? IrExpectActualMatchingContext.this.getExpectToActualClassMap().get(AdditionalIrUtilsKt.getClassIdOrFail(irClass)) : null;
            ArrayList arrayList = new ArrayList(irSimpleTypeImpl.getArguments().size());
            boolean z = false;
            for (IrTypeArgument irTypeArgument : irSimpleTypeImpl.getArguments()) {
                IrTypeArgument irTypeArgumentSubstituteArgumentOrNull = substituteArgumentOrNull(irTypeArgument);
                if (irTypeArgumentSubstituteArgumentOrNull != null) {
                    arrayList.add(irTypeArgumentSubstituteArgumentOrNull);
                    z = true;
                } else {
                    arrayList.add(irTypeArgument);
                }
            }
            if (irClassSymbol != null || z) {
                return new IrSimpleTypeImpl(irClassSymbol != null ? irClassSymbol : irSimpleTypeImpl.getClassifier(), irSimpleTypeImpl.getNullability(), arrayList, type.getAnnotations(), irSimpleTypeImpl.getAbbreviation());
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final IrTypeArgument substituteArgumentOrNull(IrTypeArgument argument) {
            IrTypeBase irTypeBaseMakeTypeProjection;
            if (argument instanceof IrStarProjection) {
                return null;
            }
            if (!(argument instanceof IrTypeProjection)) {
                throw new NoWhenBranchMatchedException();
            }
            if (argument instanceof IrTypeProjectionImpl) {
                IrTypeProjection irTypeProjection = (IrTypeProjection) argument;
                IrType irTypeSubstituteOrNull = substituteOrNull(irTypeProjection.getType());
                if (irTypeSubstituteOrNull == null) {
                    return null;
                }
                irTypeBaseMakeTypeProjection = IrSimpleTypeImplKt.makeTypeProjection(irTypeSubstituteOrNull, irTypeProjection.getVariance());
            } else {
                if (!(argument instanceof IrTypeBase)) {
                    AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                    throw null;
                }
                irTypeBaseMakeTypeProjection = (IrTypeBase) substituteOrNull((IrType) argument);
            }
            return irTypeBaseMakeTypeProjection;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isNotSamInterface(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return !asIr(regularClassSymbolMarker).getIsFun();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasStableParameterNames(CallableSymbolMarker callableSymbolMarker) {
        IrOverridableDeclaration irOverridableDeclarationResolveFakeOverrideOrNull$default;
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        IrDeclaration irDeclarationAsIr = asIr(callableSymbolMarker);
        if (IrFakeOverrideUtilsKt.isFakeOverride(irDeclarationAsIr) && (irDeclarationAsIr instanceof IrOverridableDeclaration) && (irOverridableDeclarationResolveFakeOverrideOrNull$default = IrFakeOverrideUtilsKt.resolveFakeOverrideOrNull$default((IrOverridableDeclaration) irDeclarationAsIr, false, null, 3, null)) != null) {
            irDeclarationAsIr = irOverridableDeclarationResolveFakeOverrideOrNull$default;
        }
        IrDeclarationOrigin origin = irDeclarationAsIr.getOrigin();
        return !(Intrinsics.areEqual(origin, IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE) ? true : Intrinsics.areEqual(origin, IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public void onMatchedMembers(DeclarationSymbolMarker expectSymbol, DeclarationSymbolMarker actualSymbol) {
        IrClassSymbol symbol;
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
        if (!(expectSymbol instanceof IrSymbol)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(actualSymbol instanceof IrSymbol)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (expectSymbol instanceof IrClassSymbol) {
            if (actualSymbol instanceof IrClassSymbol) {
                symbol = (IrClassSymbol) actualSymbol;
            } else {
                if (!(actualSymbol instanceof IrTypeAliasSymbol)) {
                    throw new IllegalArgumentException("Unexpected kind of " + IrClassifierSymbol.class.getTypeName() + ": " + ((IrSymbol) actualSymbol));
                }
                IrClass irClass = IrTypesKt.getClass(((IrTypeAliasSymbol) actualSymbol).getOwner().getExpandedType());
                Intrinsics.checkNotNull(irClass);
                symbol = irClass.getSymbol();
            }
            onMatchedClasses((IrClassSymbol) expectSymbol, symbol);
            return;
        }
        onMatchedCallables((IrSymbol) expectSymbol, (IrSymbol) actualSymbol);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<ExpectActualMatchingContext.AnnotationCallInfo> getAnnotations(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNullParameter(declarationSymbolMarker, "<this>");
        List<IrConstructorCall> annotations = asIr(declarationSymbolMarker).getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(new AnnotationCallInfoImpl(this, (IrConstructorCall) it.next()));
        }
        return arrayList;
    }

    private static final IrConstructorCall areAnnotationArgumentsEqual$getIrElement(ExpectActualMatchingContext.AnnotationCallInfo annotationCallInfo) {
        Intrinsics.checkNotNull(annotationCallInfo, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualMatchingContext.AnnotationCallInfoImpl");
        return ((AnnotationCallInfoImpl) annotationCallInfo).getIrElement();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean areAnnotationArgumentsEqual(ExpectActualMatchingContext.AnnotationCallInfo expectAnnotation, ExpectActualMatchingContext.AnnotationCallInfo actualAnnotation, ExpectActualCollectionArgumentsCompatibilityCheckStrategy collectionArgumentsCompatibilityCheckStrategy) {
        Intrinsics.checkNotNullParameter(expectAnnotation, "expectAnnotation");
        Intrinsics.checkNotNullParameter(actualAnnotation, "actualAnnotation");
        Intrinsics.checkNotNullParameter(collectionArgumentsCompatibilityCheckStrategy, "collectionArgumentsCompatibilityCheckStrategy");
        return IrConstExpressionValuesEqualityCheckerKt.areIrExpressionConstValuesEqual(this, areAnnotationArgumentsEqual$getIrElement(expectAnnotation), areAnnotationArgumentsEqual$getIrElement(actualAnnotation), collectionArgumentsCompatibilityCheckStrategy);
    }

    public final ClassId getClassIdAfterActualization$ir_backend_common(ClassId classId) {
        ClassId classId2;
        Intrinsics.checkNotNullParameter(classId, "classId");
        IrClassSymbol irClassSymbol = this.expectToActualClassMap.get(classId);
        return (irClassSymbol == null || (classId2 = getClassId(irClassSymbol)) == null) ? classId : classId2;
    }

    /* JADX INFO: compiled from: IrExpectActualMatchingContext.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002R\u0014\u0010\u0005\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualMatchingContext$AnnotationCallInfoImpl;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "irElement", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "(Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualMatchingContext;Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;)V", "annotationSymbol", "getAnnotationSymbol", "()Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getIrElement", "isOptIn", "", "()Z", "isRetentionSource", "getAnnotationClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class AnnotationCallInfoImpl implements ExpectActualMatchingContext.AnnotationCallInfo {
        private final IrConstructorCall annotationSymbol;
        private final IrConstructorCall irElement;
        final /* synthetic */ IrExpectActualMatchingContext this$0;

        public AnnotationCallInfoImpl(IrExpectActualMatchingContext irExpectActualMatchingContext, IrConstructorCall irElement) {
            Intrinsics.checkNotNullParameter(irElement, "irElement");
            this.this$0 = irExpectActualMatchingContext;
            this.irElement = irElement;
            this.annotationSymbol = irElement;
        }

        public final IrConstructorCall getIrElement() {
            return this.irElement;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public IrConstructorCall getAnnotationSymbol() {
            return this.annotationSymbol;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public ClassId getClassId() {
            IrClass annotationClass = getAnnotationClass();
            if (annotationClass != null) {
                return AdditionalIrUtilsKt.getClassId(annotationClass);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public boolean isRetentionSource() {
            IrClass annotationClass = getAnnotationClass();
            return (annotationClass != null ? IrUtilsKt.getAnnotationRetention(annotationClass) : null) == KotlinRetention.SOURCE;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public boolean isOptIn() {
            IrClass annotationClass = getAnnotationClass();
            if (annotationClass != null) {
                return IrUtilsKt.hasAnnotation(annotationClass, OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME());
            }
            return false;
        }

        private final IrClass getAnnotationClass() {
            IrClass owner;
            IrClass irClass = IrTypesKt.getClass(this.irElement.getType());
            if (irClass == null) {
                return null;
            }
            IrClassSymbol irClassSymbol = this.this$0.getExpectToActualClassMap().get(AdditionalIrUtilsKt.getClassId(irClass));
            return (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null) ? irClass : owner;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasSourceAnnotationsErased(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNullParameter(declarationSymbolMarker, "<this>");
        IrDeclaration irDeclarationAsIr = asIr(declarationSymbolMarker);
        return UtilsKt.sourceElement(irDeclarationAsIr) == null && !(irDeclarationAsIr.getOrigin() instanceof IrDeclarationOrigin.GeneratedByPlugin);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public CallableId getCallableId(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof IrFunctionSymbol) {
            return AdditionalIrUtilsKt.getCallableId(((IrFunctionSymbol) callableSymbolMarker).getOwner());
        }
        if (callableSymbolMarker instanceof IrPropertySymbol) {
            return AdditionalIrUtilsKt.getCallableId(((IrPropertySymbol) callableSymbolMarker).getOwner());
        }
        if (callableSymbolMarker instanceof IrFieldSymbol) {
            return AdditionalIrUtilsKt.getCallableId(((IrFieldSymbol) callableSymbolMarker).getOwner());
        }
        if (callableSymbolMarker instanceof IrValueParameterSymbol) {
            ((IrValueParameterSymbol) callableSymbolMarker).getOwner();
            AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
            throw null;
        }
        if (callableSymbolMarker instanceof IrEnumEntrySymbol) {
            return AdditionalIrUtilsKt.getCallableId(((IrEnumEntrySymbol) callableSymbolMarker).getOwner());
        }
        throw new IllegalStateException(("Unsupported declaration: " + callableSymbolMarker).toString());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<TypeParameterSymbolMarker> getTypeParameters(ClassLikeSymbolMarker classLikeSymbolMarker) {
        List<IrTypeParameter> typeParameters;
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        if (classLikeSymbolMarker instanceof IrClassSymbol) {
            typeParameters = ((IrClassSymbol) classLikeSymbolMarker).getOwner().getTypeParameters();
        } else if (classLikeSymbolMarker instanceof IrTypeAliasSymbol) {
            typeParameters = ((IrTypeAliasSymbol) classLikeSymbolMarker).getOwner().getTypeParameters();
        } else {
            throw new IllegalStateException(("Unsupported declaration: " + classLikeSymbolMarker).toString());
        }
        List<IrTypeParameter> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrTypeParameter) it.next()).getSymbol());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Modality getModality(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        if (classLikeSymbolMarker instanceof IrClassSymbol) {
            IrClass owner = ((IrClassSymbol) classLikeSymbolMarker).getOwner();
            return IrUtilsKt.isAnnotationClass(owner) ? Modality.OPEN : owner.getModality();
        }
        if (classLikeSymbolMarker instanceof IrTypeAliasSymbol) {
            ((IrTypeAliasSymbol) classLikeSymbolMarker).getOwner();
            return Modality.FINAL;
        }
        throw new IllegalStateException(("Unsupported declaration: " + classLikeSymbolMarker).toString());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isExpect(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof IrFunctionSymbol) {
            return ((IrFunctionSymbol) callableSymbolMarker).getOwner().getIsExpect();
        }
        if (callableSymbolMarker instanceof IrPropertySymbol) {
            return ((IrPropertySymbol) callableSymbolMarker).getOwner().getIsExpect();
        }
        if (callableSymbolMarker instanceof IrFieldSymbol) {
            ((IrFieldSymbol) callableSymbolMarker).getOwner();
        } else if (callableSymbolMarker instanceof IrValueParameterSymbol) {
            ((IrValueParameterSymbol) callableSymbolMarker).getOwner();
        } else {
            if (!(callableSymbolMarker instanceof IrEnumEntrySymbol)) {
                throw new IllegalStateException(("Unsupported declaration: " + callableSymbolMarker).toString());
            }
            ((IrEnumEntrySymbol) callableSymbolMarker).getOwner();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInline(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof IrFunctionSymbol) {
            return ((IrFunctionSymbol) callableSymbolMarker).getOwner().getIsInline();
        }
        if (callableSymbolMarker instanceof IrPropertySymbol) {
            ((IrPropertySymbol) callableSymbolMarker).getOwner();
        } else if (callableSymbolMarker instanceof IrFieldSymbol) {
            ((IrFieldSymbol) callableSymbolMarker).getOwner();
        } else if (callableSymbolMarker instanceof IrValueParameterSymbol) {
            ((IrValueParameterSymbol) callableSymbolMarker).getOwner();
        } else {
            if (!(callableSymbolMarker instanceof IrEnumEntrySymbol)) {
                throw new IllegalStateException(("Unsupported declaration: " + callableSymbolMarker).toString());
            }
            ((IrEnumEntrySymbol) callableSymbolMarker).getOwner();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isSuspend(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof IrFunctionSymbol) {
            return AdditionalIrUtilsKt.isSuspend(((IrFunctionSymbol) callableSymbolMarker).getOwner());
        }
        if (callableSymbolMarker instanceof IrPropertySymbol) {
            ((IrPropertySymbol) callableSymbolMarker).getOwner();
        } else if (callableSymbolMarker instanceof IrFieldSymbol) {
            ((IrFieldSymbol) callableSymbolMarker).getOwner();
        } else if (callableSymbolMarker instanceof IrValueParameterSymbol) {
            ((IrValueParameterSymbol) callableSymbolMarker).getOwner();
        } else {
            if (!(callableSymbolMarker instanceof IrEnumEntrySymbol)) {
                throw new IllegalStateException(("Unsupported declaration: " + callableSymbolMarker).toString());
            }
            ((IrEnumEntrySymbol) callableSymbolMarker).getOwner();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public TypeSubstitutorMarker createExpectActualTypeParameterSubstitutor(List<? extends TypeParameterSymbolMarker> expectTypeParameters, List<? extends TypeParameterSymbolMarker> actualTypeParameters, TypeSubstitutorMarker parentSubstitutor) {
        Intrinsics.checkNotNullParameter(expectTypeParameters, "expectTypeParameters");
        Intrinsics.checkNotNullParameter(actualTypeParameters, "actualTypeParameters");
        for (TypeParameterSymbolMarker typeParameterSymbolMarker : expectTypeParameters) {
            if (typeParameterSymbolMarker == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
            }
        }
        for (TypeParameterSymbolMarker typeParameterSymbolMarker2 : actualTypeParameters) {
            if (typeParameterSymbolMarker2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
            }
        }
        List<? extends TypeParameterSymbolMarker> list = actualTypeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(IrTypesKt.getDefaultType(((IrTypeParameterSymbol) it.next()).getOwner()));
        }
        IrTypeSubstitutor irTypeSubstitutor = new IrTypeSubstitutor(expectTypeParameters, arrayList, this.typeContext.getIrBuiltIns(), true);
        if (parentSubstitutor == null) {
            return irTypeSubstitutor;
        }
        if (parentSubstitutor instanceof AbstractIrTypeSubstitutor) {
            return new IrChainedSubstitutor((AbstractIrTypeSubstitutor) parentSubstitutor, irTypeSubstitutor);
        }
        AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
        throw null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getReturnType(CallableSymbolMarker callableSymbolMarker) {
        IrSimpleType defaultType;
        IrType returnType;
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof IrFunctionSymbol) {
            defaultType = ((IrFunctionSymbol) callableSymbolMarker).getOwner().getReturnType();
        } else if (callableSymbolMarker instanceof IrPropertySymbol) {
            IrProperty owner = ((IrPropertySymbol) callableSymbolMarker).getOwner();
            IrSimpleFunction getter = owner.getGetter();
            if (getter == null || (returnType = getter.getReturnType()) == null) {
                IrField backingField = owner.getBackingField();
                if (backingField == null) {
                    throw new IllegalStateException(("No type for property: " + owner).toString());
                }
                defaultType = backingField.getType();
            } else {
                defaultType = returnType;
            }
        } else if (callableSymbolMarker instanceof IrFieldSymbol) {
            defaultType = ((IrFieldSymbol) callableSymbolMarker).getOwner().getType();
        } else if (callableSymbolMarker instanceof IrValueParameterSymbol) {
            defaultType = ((IrValueParameterSymbol) callableSymbolMarker).getOwner().getType();
        } else if (callableSymbolMarker instanceof IrEnumEntrySymbol) {
            defaultType = IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(((IrEnumEntrySymbol) callableSymbolMarker).getOwner()));
        } else {
            throw new IllegalStateException(("Unsupported declaration: " + callableSymbolMarker).toString());
        }
        return defaultType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<TypeParameterSymbolMarker> getTypeParameters(CallableSymbolMarker callableSymbolMarker) {
        IrSimpleFunctionSymbol symbol;
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        if (callableSymbolMarker instanceof IrFunctionSymbol) {
            List<IrTypeParameter> typeParameters = ((IrFunctionSymbol) callableSymbolMarker).getOwner().getTypeParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(((IrTypeParameter) it.next()).getSymbol());
            }
            return arrayList;
        }
        if (callableSymbolMarker instanceof IrPropertySymbol) {
            IrSimpleFunction getter = ((IrPropertySymbol) callableSymbolMarker).getOwner().getGetter();
            List<TypeParameterSymbolMarker> typeParameters2 = (getter == null || (symbol = getter.getSymbol()) == null) ? null : getTypeParameters(symbol);
            if (typeParameters2 == null) {
                typeParameters2 = CollectionsKt.emptyList();
            }
            return typeParameters2;
        }
        if (callableSymbolMarker instanceof IrFieldSymbol) {
            ((IrFieldSymbol) callableSymbolMarker).getOwner();
            return CollectionsKt.emptyList();
        }
        if (callableSymbolMarker instanceof IrValueParameterSymbol) {
            ((IrValueParameterSymbol) callableSymbolMarker).getOwner();
            return CollectionsKt.emptyList();
        }
        if (callableSymbolMarker instanceof IrEnumEntrySymbol) {
            ((IrEnumEntrySymbol) callableSymbolMarker).getOwner();
            return CollectionsKt.emptyList();
        }
        throw new IllegalStateException(("Unsupported declaration: " + callableSymbolMarker).toString());
    }
}
