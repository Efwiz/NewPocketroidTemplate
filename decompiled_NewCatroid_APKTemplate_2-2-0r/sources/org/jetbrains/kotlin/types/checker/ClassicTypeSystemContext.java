package org.jetbrains.kotlin.types.checker;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.BuiltInAnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedType;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.SubstitutingScope;
import org.jetbrains.kotlin.types.AbstractStubType;
import org.jetbrains.kotlin.types.AnnotationsTypeAttribute;
import org.jetbrains.kotlin.types.AnnotationsTypeAttributeKt;
import org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor;
import org.jetbrains.kotlin.types.DefaultTypeAttributeTranslator;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.DynamicType;
import org.jetbrains.kotlin.types.ErasureProjectionComputer;
import org.jetbrains.kotlin.types.ErasureTypeAttributes;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.NotNullTypeParameter;
import org.jetbrains.kotlin.types.RawType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SimpleTypeWithEnhancement;
import org.jetbrains.kotlin.types.StarProjectionImpl;
import org.jetbrains.kotlin.types.TypeAttribute;
import org.jetbrains.kotlin.types.TypeAttributeTranslator;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeParameterErasureOptions;
import org.jetbrains.kotlin.types.TypeParameterUpperBoundEraser;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.TypeUsage;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextKt;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ClassicTypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0090\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b<\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\bf\u0018\u00002\u00020\u00012\u00020\u0002:\u0002æ\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0019\u001a\u00020\u0017H\u0016J0\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00170\"2\b\u0010#\u001a\u0004\u0018\u00010\u00172\u0006\u0010$\u001a\u00020\u001bH\u0016J\b\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u00102\u0006\u0010(\u001a\u00020)H\u0016J\u0018\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\u00102\u0006\u0010,\u001a\u00020\u0010H\u0016J>\u0010-\u001a\u00020\u00102\u0006\u0010.\u001a\u00020\u00132\f\u0010/\u001a\b\u0012\u0004\u0012\u00020 0\"2\u0006\u00100\u001a\u00020\b2\u0006\u00101\u001a\u00020\b2\u000e\u00102\u001a\n\u0012\u0004\u0012\u000203\u0018\u00010\"H\u0016J\u0010\u00104\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u0016J\u0010\u00109\u001a\u0002062\u0006\u00107\u001a\u000208H\u0016J\u0012\u0010:\u001a\u0004\u0018\u00010&2\u0006\u0010;\u001a\u00020\u0017H\u0016J\u0018\u0010<\u001a\u00020 2\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010=\u001a\u00020>H\u0016J\u0018\u0010?\u001a\u00020\u00172\u0006\u0010@\u001a\u00020\u00172\u0006\u0010A\u001a\u00020\u0017H\u0016J\u0010\u0010B\u001a\u00020\u00172\u0006\u0010.\u001a\u00020\u0013H\u0016J\u0018\u0010C\u001a\u0004\u0018\u00010\u00102\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00100\"H\u0016J\u0018\u0010E\u001a\u00020\u00132\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020IH\u0016J\u0018\u0010J\u001a\u00020\u00132\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020IH\u0016J\u0018\u0010K\u001a\u00020\b2\u0006\u0010L\u001a\u00020\u00102\u0006\u0010M\u001a\u00020\u0010H\u0016J\u0016\u0010N\u001a\u00020\u00172\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00170\"H\u0016J\u0016\u0010N\u001a\u00020\u00102\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00100\"H\u0016J\u0018\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\b2\u0006\u0010S\u001a\u00020\bH\u0016J\b\u0010T\u001a\u00020\u0010H\u0016J\b\u0010U\u001a\u00020\u0010H\u0016J\b\u0010V\u001a\u00020\u0010H\u0016J\u0010\u0010W\u001a\u00020X2\u0006\u0010\u0019\u001a\u00020\u0010H\u0016J\u001c\u0010Y\u001a\u00020&2\u0012\u0010Z\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00170[H\u0016J\u001c\u0010\\\u001a\b\u0012\u0004\u0012\u0002030\"2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00170\"H\u0016J\b\u0010]\u001a\u00020\bH\u0016J\f\u0010^\u001a\u00020G*\u00020\u0017H\u0016J\f\u0010_\u001a\u00020`*\u00020\u0010H\u0016J\u000e\u0010a\u001a\u0004\u0018\u00010\u001e*\u00020\u0010H\u0016J\u000e\u0010b\u001a\u0004\u0018\u00010c*\u00020\u0010H\u0016J\u000e\u0010d\u001a\u0004\u0018\u00010e*\u00020fH\u0016J\u000e\u0010g\u001a\u0004\u0018\u00010f*\u00020\u0017H\u0016J\u000e\u0010h\u001a\u0004\u0018\u00010\u0010*\u00020\u0017H\u0016J\f\u0010i\u001a\u00020 *\u00020\u0017H\u0016J\f\u0010j\u001a\u00020\b*\u00020\u0017H\u0016J\f\u0010$\u001a\u00020\u001b*\u00020\u001eH\u0016J \u0010k\u001a\u00020\b*\u00020\u00172\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\b0mH\u0016J\f\u0010n\u001a\u00020\u0017*\u00020\u0017H\u0016J\f\u0010o\u001a\u00020\u0010*\u000208H\u0016J\f\u0010p\u001a\u00020\u0017*\u00020\u0017H\u0016J\u0012\u0010q\u001a\b\u0012\u0004\u0012\u00020\u00170\"*\u00020\u0017H\u0016J\f\u0010r\u001a\u00020\u0013*\u000208H\u0016J\u000e\u0010s\u001a\u0004\u0018\u00010I*\u00020\u0017H\u0016J\u0016\u0010t\u001a\u0004\u0018\u00010u*\u00020\u00172\u0006\u0010v\u001a\u00020wH\u0016J\f\u0010x\u001a\u00020\u0017*\u00020\u0013H\u0016J\u0014\u0010y\u001a\u00020 *\u00020\u00172\u0006\u0010z\u001a\u00020GH\u0016J\u0012\u0010{\u001a\b\u0012\u0004\u0012\u00020 0\"*\u00020\u0017H\u0016J\u0012\u0010|\u001a\b\u0012\u0004\u0012\u0002030\"*\u00020\u0017H\u0016J\f\u0010}\u001a\u00020~*\u00020\u0013H\u0016J\u0012\u0010\u007f\u001a\b\u0012\u0004\u0012\u0002030\"*\u00020\u0017H\u0016J\r\u0010\u0080\u0001\u001a\u00020\u0017*\u00020\u0017H\u0016J\u000e\u0010\u0081\u0001\u001a\u00030\u0082\u0001*\u00020\u000bH\u0016J\r\u0010\u0083\u0001\u001a\u00020\f*\u000206H\u0016J\u0015\u0010\u0084\u0001\u001a\u00020\u000b*\u00020\u00132\u0006\u0010z\u001a\u00020GH\u0016J\u0013\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\"*\u00020\u0013H\u0016J\u0010\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0087\u0001*\u00020\u0013H\u0016J\u0010\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0087\u0001*\u00020\u0013H\u0016J\r\u0010\u0089\u0001\u001a\u00020\u0017*\u00020\u000bH\u0016J\u000f\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u0017*\u00020\u0017H\u0016J\r\u0010\u008b\u0001\u001a\u00020\u0017*\u00020 H\u0016J\r\u0010\u008c\u0001\u001a\u00020\u0013*\u00020\u000bH\u0016J\u000f\u0010\u008d\u0001\u001a\u0004\u0018\u00010\u000b*\u00020\u0013H\u0016J\u000f\u0010\u008e\u0001\u001a\u0004\u0018\u00010\u0017*\u00020\u0017H\u0016J\u0015\u0010\u008f\u0001\u001a\u00020\u0017*\u00020\u000b2\u0006\u0010z\u001a\u00020GH\u0016J\u0013\u0010\u0090\u0001\u001a\b\u0012\u0004\u0012\u00020\u00170\"*\u00020\u000bH\u0016J#\u0010\u0091\u0001\u001a\u0018\u0012\u0012\u0012\u0010\u0012\u0005\u0012\u00030\u0082\u0001\u0012\u0004\u0012\u00020\u00100\u0092\u0001\u0018\u00010\"*\u00020\u0013H\u0016J\r\u0010\u0093\u0001\u001a\u00020>*\u00020 H\u0016J\r\u0010\u0093\u0001\u001a\u00020>*\u00020\u000bH\u0016J\u0015\u0010\u0094\u0001\u001a\u00020\b*\u00020\u00172\u0006\u0010v\u001a\u00020wH\u0016J\r\u0010\u0095\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010\u0096\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010\u0097\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\u0018\u0010\u0098\u0001\u001a\u00020\b*\u00020\u000b2\t\u0010\u0099\u0001\u001a\u0004\u0018\u00010\u0013H\u0016J\r\u0010\u009a\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010\u009b\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010\u009c\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010\u009d\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010\u009e\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010\u009f\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010 \u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010¡\u0001\u001a\u00020\b*\u00020\fH\u0016J\r\u0010¢\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010£\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010£\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\f\u00101\u001a\u00020\b*\u00020\u0010H\u0016J\r\u0010¤\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010¥\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010¦\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010§\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010¨\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010©\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010ª\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010«\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010¬\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010\u00ad\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010®\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010¯\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010°\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010±\u0001\u001a\u00020\b*\u00020\u0010H\u0016J\r\u0010²\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010³\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010´\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010µ\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010¶\u0001\u001a\u00020\b*\u00020\u001eH\u0016J\r\u0010·\u0001\u001a\u00020\b*\u00020\u0010H\u0016J\r\u0010¸\u0001\u001a\u00020\b*\u00020\u001eH\u0016J\r\u0010¹\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010º\u0001\u001a\u00020\b*\u00020\u000bH\u0016J\r\u0010»\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010¼\u0001\u001a\u00020\b*\u00020\u0010H\u0016J\r\u0010½\u0001\u001a\u00020\b*\u00020\u0010H\u0002J\r\u0010¾\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010¿\u0001\u001a\u00020\b*\u00020 H\u0016J\r\u0010À\u0001\u001a\u00020\b*\u00020\u0010H\u0016J\r\u0010Á\u0001\u001a\u00020\b*\u00020\u0010H\u0016J\r\u0010Â\u0001\u001a\u00020\b*\u00020\u0010H\u0016J\r\u0010Ã\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010Ä\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010Å\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010Æ\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\r\u0010Ç\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010È\u0001\u001a\u00020\b*\u00020\u0017H\u0016J\r\u0010É\u0001\u001a\u00020\b*\u00020\u0013H\u0016J\f\u0010+\u001a\u00020\u0010*\u00020fH\u0016J\u000e\u0010#\u001a\u0004\u0018\u00010\u0017*\u00020\u001eH\u0016J\r\u0010Ê\u0001\u001a\u00020\u0017*\u00020\u0017H\u0016J\r\u0010Ë\u0001\u001a\u00020\u0010*\u00020\u0010H\u0016J\r\u0010Ì\u0001\u001a\u00020\u0010*\u00020cH\u0016J\r\u0010Í\u0001\u001a\u00020G*\u00020\u0013H\u0016J\u0014\u0010Î\u0001\u001a\t\u0012\u0004\u0012\u00020\u00170Ï\u0001*\u00020\u0010H\u0016J\u000e\u0010Ð\u0001\u001a\u00020 *\u00030Ñ\u0001H\u0016J\r\u0010Ò\u0001\u001a\u00020\u0017*\u00020\u0017H\u0016J\r\u0010Ó\u0001\u001a\u00020\u0017*\u00020\u0017H\u0016J\"\u0010Ô\u0001\u001a\u00020\u0010*\u00020\u00102\u0013\u0010Õ\u0001\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0mH\u0016J\u001c\u0010Ô\u0001\u001a\u00020\u0010*\u00020\u00102\r\u0010Ö\u0001\u001a\b\u0012\u0004\u0012\u00020 0\"H\u0016J\u001c\u0010×\u0001\u001a\u00020\u0017*\u00020\u00172\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u0002030\"H\u0016J\u0016\u0010Ù\u0001\u001a\u00020 *\u00020 2\u0007\u0010Ú\u0001\u001a\u00020\u0017H\u0016J\u0015\u0010Û\u0001\u001a\u00020\u0017*\u00020&2\u0006\u0010\u0019\u001a\u00020\u0017H\u0016J\u0016\u0010Ü\u0001\u001a\u0004\u0018\u00010\u0017*\t\u0012\u0004\u0012\u00020\u00170Ï\u0001H\u0016J\u0014\u0010Ý\u0001\u001a\t\u0012\u0004\u0012\u00020\u00170Ï\u0001*\u00020\u0013H\u0016J\r\u0010Þ\u0001\u001a\u00020\u0010*\u00020\u0013H\u0016J\u000e\u0010ß\u0001\u001a\u00030Ñ\u0001*\u00020\u001eH\u0016J\r\u0010ß\u0001\u001a\u00020\u0013*\u00020\u0010H\u0016J\r\u0010à\u0001\u001a\u00020 *\u00020\u001eH\u0016J\r\u0010á\u0001\u001a\u00020G*\u00020\u0010H\u0016J\u000e\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u001eH\u0016J\r\u0010â\u0001\u001a\u00020\u0013*\u00020\u0013H\u0016J\f\u0010,\u001a\u00020\u0010*\u00020fH\u0016J\r\u0010ã\u0001\u001a\u00020G*\u00020\u000bH\u0016J\r\u0010ä\u0001\u001a\u00020\u0017*\u00020\u001eH\u0016J\u0015\u0010å\u0001\u001a\u00020\u0017*\u00020\u00172\u0006\u00100\u001a\u00020\bH\u0016J\u0015\u0010å\u0001\u001a\u00020\u0010*\u00020\u00102\u0006\u00100\u001a\u00020\bH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006ç\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/ClassicTypeSystemContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "isK2", "", "()Z", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "areEqualTypeConstructors", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "arrayType", "componentType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "captureFromArguments", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "createCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "constructorProjection", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "constructorSupertypes", "", "lowerType", "captureStatus", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createErrorType", "debugName", "", "createFlexibleType", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "arguments", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "createStubTypeForBuilderInference", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "createStubTypeForTypeVariablesInSubtyping", "createSubstitutorForSuperTypes", "baseType", "createTypeArgument", "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createTypeWithAlternativeForIntersectionResult", "firstCandidate", "secondCandidate", "createUninferredType", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "getNonReflectFunctionTypeConstructor", "parametersNumber", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getReflectFunctionTypeConstructor", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", SpecTypePair.NAME_types, "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "unionTypeAttributes", "useRefinedBoundsForTypeVariableInFlexiblePosition", "argumentsCount", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "canHaveUndefinedNullability", "contains", "predicate", "Lkotlin/Function1;", "convertToNonRaw", "defaultType", "eraseContainingTypeParameters", "extractArgumentsForFunctionTypeOrSubtype", "freshTypeConstructor", "functionTypeKind", "getAnnotationFirstArgumentValue", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getApproximatedIntegerLiteralType", "getArgument", "index", "getArguments", "getAttributes", "getClassFqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getCustomAttributes", "getFunctionTypeFromSupertypes", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginalTypeVariable", "getParameter", "getParameters", "getPrimitiveArrayType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveType", "getRepresentativeUpperBound", "getSubstitutedUnderlyingType", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUnsubstitutedUnderlyingType", "getUpperBound", "getUpperBounds", "getValueClassProperties", "Lkotlin/Pair;", "getVariance", "hasAnnotation", "hasCustomAttributes", "hasExactAnnotation", "hasNoInferAnnotation", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isArrayOrNullableArray", "isBuiltinFunctionTypeOrSubtype", "isCapturedTypeConstructor", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isContainedInInvariantOrContravariantPositions", "isDenotable", "isError", "isExtensionFunctionType", "isFinalClassConstructor", "isFinalClassOrEnumEntryOrAnnotationClassConstructor", "isFunctionOrKFunctionWithAnySuspendability", "isInlineClass", "isInnerClass", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralTypeConstructor", "isInterface", "isInterfaceOrAnnotationClass", "isIntersection", "isLocalType", "isMarkedNullable", "isMultiFieldValueClass", "isNotNullTypeParameter", "isNothingConstructor", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isReified", "isSignedOrUnsignedNumberType", "isSingleClassifierType", "isSingleClassifierTypeWithEnhancement", "isSpecial", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariable", "isTypeVariableType", "isUnderKotlinPackage", "isUninferredParameter", "isUnit", "isUnitTypeConstructor", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "parametersCount", "possibleIntegerTypes", "", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "removeAnnotations", "removeExactAnnotation", "replaceArguments", "replacement", "newArguments", "replaceCustomAttributes", "newAttributes", "replaceType", "newType", "safeSubstitute", "singleBestRepresentative", "supertypes", "toErrorType", "typeConstructor", "typeConstructorProjection", "typeDepth", "unwrapStubTypeVariableConstructor", "upperBoundCount", "withNotNullProjection", "withNullability", "WA", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ClassicTypeSystemContext extends TypeSystemCommonBackendContext, TypeSystemInferenceExtensionContext {

    /* JADX INFO: compiled from: ClassicTypeSystemContext.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/ClassicTypeSystemContext$WA;", "", "()V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WA {
    }

    SimpleTypeMarker anyType();

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    int argumentsCount(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    SimpleTypeMarker arrayType(KotlinTypeMarker componentType);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker);

    boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker captureFromExpression(KotlinTypeMarker type);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker);

    boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> function1);

    KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker);

    CapturedTypeMarker createCapturedType(TypeArgumentMarker constructorProjection, List<? extends KotlinTypeMarker> constructorSupertypes, KotlinTypeMarker lowerType, CaptureStatus captureStatus);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeSubstitutorMarker createEmptySubstitutor();

    SimpleTypeMarker createErrorType(String debugName);

    KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound);

    SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes);

    TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameter);

    StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariable);

    StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariable);

    TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker baseType);

    TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance);

    KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(KotlinTypeMarker firstCandidate, KotlinTypeMarker secondCandidate);

    KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor);

    SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker);

    KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker);

    List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes);

    TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker);

    FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName);

    KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker);

    KotlinBuiltIns getBuiltIns();

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    Name getName(TypeParameterMarker typeParameterMarker);

    TypeConstructorMarker getNonReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind);

    TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker);

    TypeConstructorMarker getReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeVariance getVariance(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker);

    boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker);

    boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isAnonymous(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker);

    boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker);

    boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isDenotable(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isError(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isError(TypeConstructorMarker typeConstructorMarker);

    boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker);

    boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker);

    boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isInlineClass(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isInnerClass(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isInterface(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isIntersection(TypeConstructorMarker typeConstructorMarker);

    boolean isK2();

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isLocalType(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isNullableType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isRawType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isReified(TypeParameterMarker typeParameterMarker);

    boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker);

    boolean isSpecial(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isStarProjection(TypeArgumentMarker typeArgumentMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isStubType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker);

    boolean isUnit(KotlinTypeMarker kotlinTypeMarker);

    boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker);

    TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything);

    SimpleTypeMarker nothingType();

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    SimpleTypeMarker nullableAnyType();

    SimpleTypeMarker nullableNothingType();

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    int parametersCount(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker);

    KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> list);

    SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> function1);

    KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> list);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker);

    SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker);

    TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker);

    int typeDepth(SimpleTypeMarker simpleTypeMarker);

    TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map);

    List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    int upperBoundCount(TypeParameterMarker typeParameterMarker);

    boolean useRefinedBoundsForTypeVariableInFlexiblePosition();

    KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z);

    /* JADX INFO: compiled from: ClassicTypeSystemContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean isIntegerConstantOperatorTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isK2(ClassicTypeSystemContext classicTypeSystemContext) {
            return false;
        }

        public static TypeConstructorMarker unwrapStubTypeVariableConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver;
        }

        public static boolean useRefinedBoundsForTypeVariableInFlexiblePosition(ClassicTypeSystemContext classicTypeSystemContext) {
            return false;
        }

        public static boolean isDenotable(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((TypeConstructor) receiver).isDenotable();
        }

        public static boolean isIntegerLiteralTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return receiver instanceof IntegerLiteralTypeConstructor;
        }

        public static boolean isIntegerLiteralConstantTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return classicTypeSystemContext.isIntegerLiteralTypeConstructor(receiver);
        }

        public static boolean isLocalType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            ClassId classId;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            return (this$0 == null || (classId = DescriptorUtilsKt.getClassId(this$0)) == null || !classId.isLocal()) ? false : true;
        }

        public static boolean isAnonymous(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            ClassId classId;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            return Intrinsics.areEqual((this$0 == null || (classId = DescriptorUtilsKt.getClassId(this$0)) == null) ? null : classId.getShortClassName(), SpecialNames.ANONYMOUS);
        }

        public static TypeParameterMarker getTypeParameter(ClassicTypeSystemContext classicTypeSystemContext, TypeVariableTypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof NewTypeVariableConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((NewTypeVariableConstructor) receiver).getOriginalTypeParameter();
        }

        public static Collection<KotlinTypeMarker> possibleIntegerTypes(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            TypeConstructorMarker typeConstructorMarkerTypeConstructor = classicTypeSystemContext.typeConstructor(receiver);
            if (!(typeConstructorMarkerTypeConstructor instanceof IntegerLiteralTypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((IntegerLiteralTypeConstructor) typeConstructorMarkerTypeConstructor).getPossibleTypes();
        }

        public static SimpleTypeMarker withNullability(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver, boolean z) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((SimpleType) receiver).makeNullableAsSpecified(z);
        }

        public static boolean isError(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return KotlinTypeKt.isError((KotlinType) receiver);
        }

        public static SimpleTypeMarker toErrorType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if ((receiver instanceof TypeConstructor) && ErrorUtils.isError(((TypeConstructor) receiver).getThis$0())) {
                return ErrorUtils.createErrorType(ErrorTypeKind.RESOLUTION_ERROR_TYPE, "from type constructor " + receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isUninferredParameter(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ErrorUtils.isUninferredTypeVariable((KotlinType) receiver);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static boolean isStubType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeUtilsKt.isStubType((KotlinType) receiver);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static boolean isStubTypeForVariableInSubtyping(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeUtilsKt.isStubTypeForVariableInSubtyping((KotlinType) receiver);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static boolean isStubTypeForBuilderInference(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeUtilsKt.isStubTypeForBuilderInference((KotlinType) receiver);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static TypeVariableTypeConstructorMarker getOriginalTypeVariable(ClassicTypeSystemContext classicTypeSystemContext, StubTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof AbstractStubType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            NewTypeVariableConstructor originalTypeVariable = ((AbstractStubType) receiver).getOriginalTypeVariable();
            Intrinsics.checkNotNull(originalTypeVariable, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker");
            return (TypeVariableTypeConstructorMarker) originalTypeVariable;
        }

        public static KotlinTypeMarker lowerType(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof NewCapturedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((NewCapturedType) receiver).getLowerType();
        }

        public static boolean isIntersection(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return receiver instanceof IntersectionTypeConstructor;
        }

        public static boolean identicalArguments(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker a, SimpleTypeMarker b) {
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            if (!(a instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + a + ", " + Reflection.getOrCreateKotlinClass(a.getClass())).toString());
            }
            if (b instanceof SimpleType) {
                return ((SimpleType) a).getArguments() == ((SimpleType) b).getArguments();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + b + ", " + Reflection.getOrCreateKotlinClass(b.getClass())).toString());
        }

        public static SimpleTypeMarker asSimpleType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            UnwrappedType unwrappedTypeUnwrap = ((KotlinType) receiver).unwrap();
            return unwrappedTypeUnwrap instanceof SimpleType ? (SimpleType) unwrappedTypeUnwrap : null;
        }

        public static FlexibleTypeMarker asFlexibleType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            UnwrappedType unwrappedTypeUnwrap = ((KotlinType) receiver).unwrap();
            return unwrappedTypeUnwrap instanceof FlexibleType ? (FlexibleType) unwrappedTypeUnwrap : null;
        }

        public static DynamicTypeMarker asDynamicType(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof FlexibleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return receiver instanceof DynamicType ? (DynamicType) receiver : null;
        }

        public static boolean isRawType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return receiver instanceof RawType;
        }

        public static KotlinTypeMarker convertToNonRaw(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            throw new IllegalStateException("Is not expected to be called in K1".toString());
        }

        public static SimpleTypeMarker upperBound(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof FlexibleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((FlexibleType) receiver).getUpperBound();
        }

        public static SimpleTypeMarker lowerBound(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof FlexibleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((FlexibleType) receiver).getLowerBound();
        }

        public static CapturedTypeMarker asCapturedType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            if (receiver instanceof SimpleTypeWithEnhancement) {
                return classicTypeSystemContext.asCapturedType(((SimpleTypeWithEnhancement) receiver).getOrigin());
            }
            return receiver instanceof NewCapturedType ? (NewCapturedType) receiver : null;
        }

        public static DefinitelyNotNullTypeMarker asDefinitelyNotNullType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return receiver instanceof DefinitelyNotNullType ? (DefinitelyNotNullType) receiver : null;
        }

        public static boolean isNotNullTypeParameter(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof NotNullTypeParameter;
        }

        public static boolean isMarkedNullable(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((SimpleType) receiver).getIsMarkedNullable();
        }

        public static TypeConstructorMarker typeConstructor(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((SimpleType) receiver).getConstructor();
        }

        public static CapturedTypeConstructorMarker typeConstructor(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof NewCapturedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((NewCapturedType) receiver).getConstructor();
        }

        public static TypeArgumentMarker projection(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof NewCapturedTypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((NewCapturedTypeConstructor) receiver).getProjection();
        }

        public static int argumentsCount(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((KotlinType) receiver).getArguments().size();
        }

        public static TypeArgumentMarker getArgument(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((KotlinType) receiver).getArguments().get(i);
        }

        public static List<TypeArgumentMarker> getArguments(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((KotlinType) receiver).getArguments();
        }

        public static boolean isStarProjection(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeProjection)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((TypeProjection) receiver).isStarProjection();
        }

        public static TypeVariance getVariance(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeProjection)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            Variance projectionKind = ((TypeProjection) receiver).getProjectionKind();
            Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
            return TypeSystemContextKt.convertVariance(projectionKind);
        }

        public static TypeArgumentMarker replaceType(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker receiver, KotlinTypeMarker newType) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(newType, "newType");
            if (!(receiver instanceof TypeProjection)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            if (!(newType instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            TypeProjection typeProjectionReplaceType = ((TypeProjection) receiver).replaceType((KotlinType) newType);
            Intrinsics.checkNotNullExpressionValue(typeProjectionReplaceType, "replaceType(...)");
            return typeProjectionReplaceType;
        }

        public static KotlinTypeMarker getType(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeProjection)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((TypeProjection) receiver).getNullableAnyType().unwrap();
        }

        public static int parametersCount(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((TypeConstructor) receiver).getParameters().size();
        }

        public static TypeParameterMarker getParameter(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            TypeParameterDescriptor typeParameterDescriptor = ((TypeConstructor) receiver).getParameters().get(i);
            Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "get(...)");
            return typeParameterDescriptor;
        }

        public static List<TypeParameterMarker> getParameters(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            List<TypeParameterDescriptor> parameters = ((TypeConstructor) receiver).getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            return parameters;
        }

        public static Collection<KotlinTypeMarker> supertypes(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            Collection<KotlinType> collectionMo7438getSupertypes = ((TypeConstructor) receiver).mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            return collectionMo7438getSupertypes;
        }

        public static TypeVariance getVariance(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            Variance variance = ((TypeParameterDescriptor) receiver).getVariance();
            Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
            return TypeSystemContextKt.convertVariance(variance);
        }

        public static int upperBoundCount(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((TypeParameterDescriptor) receiver).getUpperBounds().size();
        }

        public static KotlinTypeMarker getUpperBound(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            KotlinType kotlinType = ((TypeParameterDescriptor) receiver).getUpperBounds().get(i);
            Intrinsics.checkNotNullExpressionValue(kotlinType, "get(...)");
            return kotlinType;
        }

        public static List<KotlinTypeMarker> getUpperBounds(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            List<KotlinType> upperBounds = ((TypeParameterDescriptor) receiver).getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            return upperBounds;
        }

        public static TypeConstructorMarker getTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            TypeConstructor typeConstructor = ((TypeParameterDescriptor) receiver).getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
            return typeConstructor;
        }

        public static boolean hasRecursiveBounds(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            if (!(typeConstructorMarker == null ? true : typeConstructorMarker instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeUtilsKt.hasTypeParameterRecursiveBounds$default((TypeParameterDescriptor) receiver, (TypeConstructor) typeConstructorMarker, null, 4, null);
        }

        public static boolean areEqualTypeConstructors(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker c1, TypeConstructorMarker c2) {
            Intrinsics.checkNotNullParameter(c1, "c1");
            Intrinsics.checkNotNullParameter(c2, "c2");
            if (!(c1 instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + c1 + ", " + Reflection.getOrCreateKotlinClass(c1.getClass())).toString());
            }
            if (!(c2 instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + c2 + ", " + Reflection.getOrCreateKotlinClass(c2.getClass())).toString());
            }
            return Intrinsics.areEqual(c1, c2);
        }

        public static boolean isClassTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((TypeConstructor) receiver).getThis$0() instanceof ClassDescriptor;
        }

        public static boolean isInterface(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return DescriptorUtils.isInterface(((TypeConstructor) receiver).getThis$0());
        }

        public static boolean isFinalClassConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor == null) {
                return false;
            }
            return ModalityUtilsKt.isFinalClass(classDescriptor);
        }

        public static boolean isCommonFinalClassConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            return (classDescriptor == null || !ModalityUtilsKt.isFinalClass(classDescriptor) || classDescriptor.getKind() == ClassKind.ENUM_ENTRY || classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) ? false : true;
        }

        public static boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            return (this$0 instanceof ClassDescriptor) && ModalityUtilsKt.isFinalClass((ClassDescriptor) this$0);
        }

        public static TypeArgumentListMarker asArgumentList(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return (TypeArgumentListMarker) receiver;
        }

        public static SimpleTypeMarker captureFromArguments(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker type, CaptureStatus status) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(status, "status");
            if (!(type instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + type + ", " + Reflection.getOrCreateKotlinClass(type.getClass())).toString());
            }
            return NewCapturedTypeKt.captureFromArguments((SimpleType) type, status);
        }

        public static boolean isAnyConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor) receiver, StandardNames.FqNames.any);
        }

        public static boolean isNothingConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor) receiver, StandardNames.FqNames.nothing);
        }

        public static TypeArgumentMarker asTypeArgument(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeUtilsKt.asTypeProjection((KotlinType) receiver);
        }

        public static boolean isUnitTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor) receiver, StandardNames.FqNames.unit);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static boolean isSingleClassifierType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            if (!KotlinTypeKt.isError((KotlinType) receiver)) {
                SimpleType simpleType = (SimpleType) receiver;
                if (!(simpleType.getConstructor().getThis$0() instanceof TypeAliasDescriptor) && (simpleType.getConstructor().getThis$0() != null || (receiver instanceof CapturedType) || (receiver instanceof NewCapturedType) || (receiver instanceof DefinitelyNotNullType) || (simpleType.getConstructor() instanceof IntegerLiteralTypeConstructor) || isSingleClassifierTypeWithEnhancement(classicTypeSystemContext, receiver))) {
                    return true;
                }
            }
            return false;
        }

        private static boolean isSingleClassifierTypeWithEnhancement(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            return (simpleTypeMarker instanceof SimpleTypeWithEnhancement) && classicTypeSystemContext.isSingleClassifierType(((SimpleTypeWithEnhancement) simpleTypeMarker).getOrigin());
        }

        public static boolean contains(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, Function1<? super KotlinTypeMarker, Boolean> predicate) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            if (receiver instanceof KotlinType) {
                return ClassicTypeSystemContextKt.containsInternal((KotlinType) receiver, predicate);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static int typeDepth(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Integer num;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            if (receiver instanceof TypeUtils.SpecialType) {
                return 0;
            }
            Iterator<T> it = ((SimpleType) receiver).getArguments().iterator();
            if (it.hasNext()) {
                TypeProjection typeProjection = (TypeProjection) it.next();
                Integer numValueOf = Integer.valueOf(typeProjection.isStarProjection() ? 1 : classicTypeSystemContext.typeDepth(typeProjection.getNullableAnyType().unwrap()));
                while (it.hasNext()) {
                    TypeProjection typeProjection2 = (TypeProjection) it.next();
                    Integer numValueOf2 = Integer.valueOf(typeProjection2.isStarProjection() ? 1 : classicTypeSystemContext.typeDepth(typeProjection2.getNullableAnyType().unwrap()));
                    if (numValueOf.compareTo(numValueOf2) < 0) {
                        numValueOf = numValueOf2;
                    }
                }
                num = numValueOf;
            } else {
                num = null;
            }
            Integer num2 = num;
            return (num2 != null ? num2.intValue() : 0) + 1;
        }

        public static KotlinTypeMarker intersectTypes(ClassicTypeSystemContext classicTypeSystemContext, List<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return IntersectionTypeKt.m7477intersectTypes((List<? extends UnwrappedType>) types);
        }

        /* JADX INFO: renamed from: intersectTypes, reason: collision with other method in class */
        public static SimpleTypeMarker m7476intersectTypes(ClassicTypeSystemContext classicTypeSystemContext, List<? extends SimpleTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return IntersectionTypeKt.intersectTypes((List<? extends SimpleType>) types);
        }

        public static KotlinTypeMarker singleBestRepresentative(ClassicTypeSystemContext classicTypeSystemContext, Collection<? extends KotlinTypeMarker> receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return ClassicTypeSystemContextKt.singleBestRepresentative(receiver);
        }

        public static boolean isUnit(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof UnwrappedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return KotlinBuiltIns.isUnit((KotlinType) receiver);
        }

        public static boolean isBuiltinFunctionTypeOrSubtype(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof UnwrappedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return FunctionTypesKt.isBuiltinFunctionalTypeOrSubtype((KotlinType) receiver);
        }

        public static KotlinTypeMarker createFlexibleType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
            Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
            Intrinsics.checkNotNullParameter(upperBound, "upperBound");
            if (!(lowerBound instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
            }
            if (!(upperBound instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
            }
            return KotlinTypeFactory.flexibleType((SimpleType) lowerBound, (SimpleType) upperBound);
        }

        public static KotlinTypeMarker withNullability(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, boolean z) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof SimpleTypeMarker) {
                return classicTypeSystemContext.withNullability((SimpleTypeMarker) receiver, z);
            }
            if (!(receiver instanceof FlexibleTypeMarker)) {
                throw new IllegalStateException("sealed".toString());
            }
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) receiver;
            return classicTypeSystemContext.createFlexibleType(classicTypeSystemContext.withNullability(classicTypeSystemContext.lowerBound(flexibleTypeMarker), z), classicTypeSystemContext.withNullability(classicTypeSystemContext.upperBound(flexibleTypeMarker), z));
        }

        public static TypeCheckerState newTypeCheckerState(ClassicTypeSystemContext classicTypeSystemContext, boolean z, boolean z2) {
            return ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(z, z2, classicTypeSystemContext, null, null, 24, null);
        }

        public static SimpleTypeMarker nullableNothingType(ClassicTypeSystemContext classicTypeSystemContext) {
            SimpleType nullableNothingType = classicTypeSystemContext.getBuiltIns().getNullableNothingType();
            Intrinsics.checkNotNullExpressionValue(nullableNothingType, "getNullableNothingType(...)");
            return nullableNothingType;
        }

        public static SimpleTypeMarker nullableAnyType(ClassicTypeSystemContext classicTypeSystemContext) {
            SimpleType nullableAnyType = classicTypeSystemContext.getBuiltIns().getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
            return nullableAnyType;
        }

        public static SimpleTypeMarker nothingType(ClassicTypeSystemContext classicTypeSystemContext) {
            SimpleType nothingType = classicTypeSystemContext.getBuiltIns().getNothingType();
            Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
            return nothingType;
        }

        public static SimpleTypeMarker anyType(ClassicTypeSystemContext classicTypeSystemContext) {
            SimpleType anyType = classicTypeSystemContext.getBuiltIns().getAnyType();
            Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
            return anyType;
        }

        public static KotlinBuiltIns getBuiltIns(ClassicTypeSystemContext classicTypeSystemContext) {
            throw new UnsupportedOperationException("Not supported");
        }

        public static KotlinTypeMarker makeDefinitelyNotNullOrNotNull(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof UnwrappedType) {
                return ClassicTypeSystemContextKt.makeDefinitelyNotNullOrNotNullInternal((UnwrappedType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof SimpleType) {
                return ClassicTypeSystemContextKt.makeSimpleTypeDefinitelyNotNullOrNotNullInternal((SimpleType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static KotlinTypeMarker removeAnnotations(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof UnwrappedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeUtilsKt.replaceAnnotations((KotlinType) receiver, Annotations.INSTANCE.getEMPTY());
        }

        public static KotlinTypeMarker removeExactAnnotation(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof UnwrappedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            Annotations annotations = ((UnwrappedType) receiver).getAnnotations();
            ArrayList arrayList = new ArrayList();
            for (AnnotationDescriptor annotationDescriptor : annotations) {
                if (!AnnotationsForResolveUtilsKt.isExactAnnotation(annotationDescriptor)) {
                    arrayList.add(annotationDescriptor);
                }
            }
            return TypeUtilsKt.replaceAnnotations((KotlinType) receiver, Annotations.INSTANCE.create(arrayList));
        }

        public static boolean hasExactAnnotation(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof UnwrappedType) {
                return ClassicTypeSystemContextKt.hasExactInternal((UnwrappedType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean hasNoInferAnnotation(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof UnwrappedType) {
                return ClassicTypeSystemContextKt.hasNoInferInternal((UnwrappedType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeConstructorMarker freshTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeVariableMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static TypeArgumentMarker typeConstructorProjection(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof NewCapturedType) {
                return ((NewCapturedType) receiver).getConstructor().getProjection();
            }
            if (receiver instanceof CapturedType) {
                return ((CapturedType) receiver).getTypeProjection();
            }
            throw new IllegalStateException("Unsupported captured type".toString());
        }

        public static KotlinTypeMarker withNotNullProjection(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof NewCapturedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            NewCapturedType newCapturedType = (NewCapturedType) receiver;
            return new NewCapturedType(newCapturedType.getCaptureStatus(), newCapturedType.getConstructor(), newCapturedType.getLowerType(), newCapturedType.getAttributes(), newCapturedType.getIsMarkedNullable(), true);
        }

        public static boolean isProjectionNotNull(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof NewCapturedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((NewCapturedType) receiver).getIsProjectionNotNull();
        }

        public static TypeParameterMarker typeParameter(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof NewCapturedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((NewCapturedType) receiver).getConstructor().getTypeParameter();
        }

        public static CaptureStatus captureStatus(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof NewCapturedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((NewCapturedType) receiver).getCaptureStatus();
        }

        public static boolean isOldCapturedType(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof CapturedType;
        }

        public static boolean isNullableType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeUtils.isNullableType((KotlinType) receiver);
        }

        public static SimpleTypeMarker createSimpleType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean z, boolean z2, List<? extends AnnotationMarker> list) {
            Annotations annotationsCreate;
            Object next;
            Annotations annotations;
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            if (!(constructor instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + constructor + ", " + Reflection.getOrCreateKotlinClass(constructor.getClass())).toString());
            }
            List list2 = null;
            if (list != null) {
                Iterator<T> it = list.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!(next instanceof AnnotationsTypeAttribute));
                AnnotationsTypeAttribute annotationsTypeAttribute = (AnnotationsTypeAttribute) next;
                if (annotationsTypeAttribute != null && (annotations = annotationsTypeAttribute.getAnnotations()) != null) {
                    list2 = CollectionsKt.toList(annotations);
                }
            }
            List list3 = list2;
            if ((list3 == null || list3.isEmpty()) && z2) {
                annotationsCreate = Annotations.INSTANCE.create(CollectionsKt.listOf(createSimpleType$createExtensionFunctionAnnotation(classicTypeSystemContext)));
            } else if (list3 == null || list3.isEmpty() || z2) {
                annotationsCreate = (list3 == null || list3.isEmpty() || !z2) ? Annotations.INSTANCE.getEMPTY() : Annotations.INSTANCE.create(CollectionsKt.plus((Collection<? extends BuiltInAnnotationDescriptor>) list3, createSimpleType$createExtensionFunctionAnnotation(classicTypeSystemContext)));
            } else {
                Annotations.Companion companion = Annotations.INSTANCE;
                ArrayList arrayList = new ArrayList();
                for (Object obj : list2) {
                    if (!Intrinsics.areEqual(((AnnotationDescriptor) obj).getFqName(), StandardNames.FqNames.extensionFunctionType)) {
                        arrayList.add(obj);
                    }
                }
                annotationsCreate = companion.create(arrayList);
            }
            return KotlinTypeFactory.simpleType$default(TypeAttributeTranslator.DefaultImpls.toAttributes$default(DefaultTypeAttributeTranslator.INSTANCE, annotationsCreate, null, null, 6, null), (TypeConstructor) constructor, arguments, z, (KotlinTypeRefiner) null, 16, (Object) null);
        }

        private static BuiltInAnnotationDescriptor createSimpleType$createExtensionFunctionAnnotation(ClassicTypeSystemContext classicTypeSystemContext) {
            return new BuiltInAnnotationDescriptor(classicTypeSystemContext.getBuiltIns(), StandardNames.FqNames.extensionFunctionType, MapsKt.emptyMap(), false, 8, null);
        }

        public static TypeArgumentMarker createTypeArgument(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker type, TypeVariance variance) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(variance, "variance");
            if (!(type instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + type + ", " + Reflection.getOrCreateKotlinClass(type.getClass())).toString());
            }
            return new TypeProjectionImpl(ClassicTypeSystemContextKt.convertVariance(variance), (KotlinType) type);
        }

        public static TypeArgumentMarker createStarProjection(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            if (!(typeParameter instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeParameter + ", " + Reflection.getOrCreateKotlinClass(typeParameter.getClass())).toString());
            }
            return new StarProjectionImpl((TypeParameterDescriptor) typeParameter);
        }

        public static boolean canHaveUndefinedNullability(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof UnwrappedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            UnwrappedType unwrappedType = (UnwrappedType) receiver;
            return (unwrappedType.getConstructor() instanceof NewTypeVariableConstructor) || (unwrappedType.getConstructor().getThis$0() instanceof TypeParameterDescriptor) || (receiver instanceof NewCapturedType);
        }

        public static boolean isExtensionFunction(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return classicTypeSystemContext.hasAnnotation(receiver, StandardNames.FqNames.extensionFunctionType);
        }

        public static SimpleTypeMarker replaceArguments(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver, List<? extends TypeArgumentMarker> newArguments) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(newArguments, "newArguments");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeSubstitutionKt.replace$default((SimpleType) receiver, newArguments, null, 2, null);
        }

        public static SimpleTypeMarker replaceArguments(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(replacement, "replacement");
            if (!(receiver instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            SimpleType simpleTypeReplace$default = (SimpleType) receiver;
            if (!simpleTypeReplace$default.getArguments().isEmpty()) {
                List<TypeProjection> arguments = simpleTypeReplace$default.getArguments();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    TypeArgumentMarker typeArgumentMarkerInvoke = replacement.invoke((TypeProjection) it.next());
                    Intrinsics.checkNotNull(typeArgumentMarkerInvoke, "null cannot be cast to non-null type org.jetbrains.kotlin.types.TypeProjection");
                    arrayList.add((TypeProjection) typeArgumentMarkerInvoke);
                }
                simpleTypeReplace$default = TypeSubstitutionKt.replace$default(simpleTypeReplace$default, arrayList, null, 2, null);
            }
            return simpleTypeReplace$default;
        }

        public static SimpleTypeMarker original(ClassicTypeSystemContext classicTypeSystemContext, DefinitelyNotNullTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof DefinitelyNotNullType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((DefinitelyNotNullType) receiver).getOriginal();
        }

        public static CapturedTypeMarker createCapturedType(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker constructorProjection, List<? extends KotlinTypeMarker> constructorSupertypes, KotlinTypeMarker kotlinTypeMarker, CaptureStatus captureStatus) {
            Intrinsics.checkNotNullParameter(constructorProjection, "constructorProjection");
            Intrinsics.checkNotNullParameter(constructorSupertypes, "constructorSupertypes");
            Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static TypeSubstitutorMarker typeSubstitutorByTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static TypeSubstitutorMarker createEmptySubstitutor(ClassicTypeSystemContext classicTypeSystemContext) {
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static KotlinTypeMarker safeSubstitute(ClassicTypeSystemContext classicTypeSystemContext, TypeSubstitutorMarker receiver, KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(type, "type");
            if (!(type instanceof UnwrappedType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + type + ", " + Reflection.getOrCreateKotlinClass(type.getClass())).toString());
            }
            if (!(receiver instanceof TypeSubstitutor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            KotlinType kotlinTypeSafeSubstitute = ((TypeSubstitutor) receiver).safeSubstitute((KotlinType) type, Variance.INVARIANT);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
            return kotlinTypeSafeSubstitute;
        }

        public static SimpleTypeMarker defaultType(ClassicTypeSystemContext classicTypeSystemContext, TypeVariableMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static StubTypeMarker createStubTypeForBuilderInference(ClassicTypeSystemContext classicTypeSystemContext, TypeVariableMarker typeVariable) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static StubTypeMarker createStubTypeForTypeVariablesInSubtyping(ClassicTypeSystemContext classicTypeSystemContext, TypeVariableMarker typeVariable) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static boolean isSpecial(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return receiver instanceof TypeUtils.SpecialType;
        }

        public static boolean isTypeVariable(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static boolean isContainedInInvariantOrContravariantPositions(ClassicTypeSystemContext classicTypeSystemContext, TypeVariableTypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            ClassicTypeSystemContextKt.errorSupportedOnlyInTypeInference();
            throw null;
        }

        public static boolean isSignedOrUnsignedNumberType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            KotlinType kotlinType = (KotlinType) receiver;
            return TypeUtilsKt.isSignedOrUnsignedNumberType(kotlinType) || (kotlinType.getConstructor() instanceof IntegerLiteralTypeConstructor);
        }

        public static SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(ClassicTypeSystemContext classicTypeSystemContext, List<? extends SimpleTypeMarker> explicitSupertypes) {
            Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
            return IntegerLiteralTypeConstructor.INSTANCE.findCommonSuperType(explicitSupertypes);
        }

        public static List<AnnotationMarker> unionTypeAttributes(ClassicTypeSystemContext classicTypeSystemContext, List<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            List<? extends KotlinTypeMarker> list = types;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((KotlinType) it.next()).unwrap().getAttributes());
            }
            Iterator it2 = arrayList.iterator();
            if (!it2.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next = it2.next();
            while (it2.hasNext()) {
                next = ((TypeAttributes) next).union((TypeAttributes) it2.next());
            }
            return CollectionsKt.toList((Iterable) next);
        }

        public static KotlinTypeMarker replaceCustomAttributes(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, List<? extends AnnotationMarker> newAttributes) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : newAttributes) {
                if (!(((TypeAttribute) obj) instanceof AnnotationsTypeAttribute)) {
                    arrayList.add(obj);
                }
            }
            List<? extends TypeAttribute<?>> mutableList = CollectionsKt.toMutableList((Collection) arrayList);
            KotlinType kotlinType = (KotlinType) receiver;
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(mutableList, AnnotationsTypeAttributeKt.getAnnotationsAttribute(kotlinType.getAttributes()));
            return kotlinType.unwrap().replaceAttributes(TypeAttributes.INSTANCE.create(mutableList));
        }

        public static boolean isError(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ErrorUtils.isError(((TypeConstructor) receiver).getThis$0());
        }

        public static KotlinTypeMarker getApproximatedIntegerLiteralType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof IntegerLiteralTypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((IntegerLiteralTypeConstructor) receiver).getApproximatedType().unwrap();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static boolean isPrimitiveType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return KotlinBuiltIns.isPrimitiveType((KotlinType) receiver);
        }

        public static List<AnnotationMarker> getAttributes(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return CollectionsKt.toList(((KotlinType) receiver).getAttributes());
        }

        public static boolean hasCustomAttributes(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof KotlinType) {
                return !((KotlinType) receiver).getAttributes().isEmpty() && classicTypeSystemContext.getCustomAttributes(receiver).size() > 0;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static List<AnnotationMarker> getCustomAttributes(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            TypeAttributes attributes = ((KotlinType) receiver).getAttributes();
            ArrayList arrayList = new ArrayList();
            for (TypeAttribute<?> typeAttribute : attributes) {
                if (!(typeAttribute instanceof AnnotationsTypeAttribute)) {
                    arrayList.add(typeAttribute);
                }
            }
            return arrayList;
        }

        public static KotlinTypeMarker captureFromExpression(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return ClassicTypeSystemContextKt.captureFromExpressionInternal((UnwrappedType) type);
        }

        public static SimpleTypeMarker createErrorType(ClassicTypeSystemContext classicTypeSystemContext, String debugName) {
            Intrinsics.checkNotNullParameter(debugName, "debugName");
            return ErrorUtils.createErrorType(ErrorTypeKind.RESOLUTION_ERROR_TYPE, debugName);
        }

        public static KotlinTypeMarker createUninferredType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker constructor) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return ErrorUtils.INSTANCE.createErrorType(ErrorTypeKind.UNINFERRED_TYPE_VARIABLE, (TypeConstructor) constructor, constructor.toString());
        }

        public static boolean isCapturedTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof NewCapturedTypeConstructor;
        }

        public static KotlinTypeMarker eraseContainingTypeParameters(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            TypeParameterUpperBoundEraser typeParameterUpperBoundEraser = new TypeParameterUpperBoundEraser(new ErasureProjectionComputer(), new TypeParameterErasureOptions(true, true));
            Set<TypeParameterMarker> setExtractTypeParameters = classicTypeSystemContext.extractTypeParameters(receiver);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setExtractTypeParameters, 10));
            for (TypeParameterMarker typeParameterMarker : setExtractTypeParameters) {
                Intrinsics.checkNotNull(typeParameterMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
                arrayList.add((TypeParameterDescriptor) typeParameterMarker);
            }
            ArrayList arrayList2 = arrayList;
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
            for (Object obj : arrayList2) {
                linkedHashMap.put(obj, new TypeProjectionImpl(Variance.OUT_VARIANCE, typeParameterUpperBoundEraser.getErasedUpperBound((TypeParameterDescriptor) obj, new ErasureTypeAttributes(TypeUsage.COMMON, null, null, 6, null))));
            }
            return classicTypeSystemContext.safeSubstitute(TypeConstructorSubstitution.INSTANCE.createByParametersMap(linkedHashMap).buildSubstitutor(), receiver);
        }

        public static boolean isTypeParameterTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof ClassifierBasedTypeConstructor) && (((ClassifierBasedTypeConstructor) receiver).getThis$0() instanceof AbstractTypeParameterDescriptor);
        }

        public static SimpleTypeMarker arrayType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker componentType) {
            Intrinsics.checkNotNullParameter(componentType, "componentType");
            if (!(componentType instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
            }
            SimpleType arrayType = classicTypeSystemContext.getBuiltIns().getArrayType(Variance.INVARIANT, (KotlinType) componentType);
            Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
            return arrayType;
        }

        public static boolean isArrayOrNullableArray(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return KotlinBuiltIns.isArray((KotlinType) receiver);
        }

        public static boolean hasAnnotation(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, FqName fqName) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((KotlinType) receiver).getAnnotations().hasAnnotation(fqName);
        }

        public static Object getAnnotationFirstArgumentValue(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, FqName fqName) {
            Map<Name, ConstantValue<?>> allValueArguments;
            Collection<ConstantValue<?>> collectionValues;
            ConstantValue constantValue;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            AnnotationDescriptor annotationDescriptorMo6752findAnnotation = ((KotlinType) receiver).getAnnotations().mo6752findAnnotation(fqName);
            if (annotationDescriptorMo6752findAnnotation == null || (allValueArguments = annotationDescriptorMo6752findAnnotation.getAllValueArguments()) == null || (collectionValues = allValueArguments.values()) == null || (constantValue = (ConstantValue) CollectionsKt.firstOrNull(collectionValues)) == null) {
                return null;
            }
            return constantValue.getValue();
        }

        public static TypeParameterMarker getTypeParameterClassifier(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            return this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
        }

        public static boolean isInlineClass(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            return (classDescriptor != null ? classDescriptor.getValueClassRepresentation() : null) instanceof InlineClassRepresentation;
        }

        public static boolean isMultiFieldValueClass(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            return (classDescriptor != null ? classDescriptor.getValueClassRepresentation() : null) instanceof MultiFieldValueClassRepresentation;
        }

        public static List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            ValueClassRepresentation<SimpleType> valueClassRepresentation;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor == null || (valueClassRepresentation = classDescriptor.getValueClassRepresentation()) == null) {
                return null;
            }
            return valueClassRepresentation.getUnderlyingPropertyNamesToTypes();
        }

        public static boolean isInnerClass(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            return classDescriptor != null && classDescriptor.getIsInner();
        }

        public static KotlinTypeMarker getRepresentativeUpperBound(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) receiver);
        }

        public static KotlinTypeMarker getUnsubstitutedUnderlyingType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return InlineClassesUtilsKt.unsubstitutedUnderlyingType((KotlinType) receiver);
        }

        public static KotlinTypeMarker getSubstitutedUnderlyingType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return InlineClassesUtilsKt.substitutedUnderlyingType((KotlinType) receiver);
        }

        public static PrimitiveType getPrimitiveType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            return KotlinBuiltIns.getPrimitiveType((ClassDescriptor) this$0);
        }

        public static PrimitiveType getPrimitiveArrayType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            return KotlinBuiltIns.getPrimitiveArrayType((ClassDescriptor) this$0);
        }

        public static boolean isUnderKotlinPackage(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            return this$0 != null && KotlinBuiltIns.isUnderKotlinPackage(this$0);
        }

        public static FqNameUnsafe getClassFqNameUnsafe(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((TypeConstructor) receiver).getThis$0();
            Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            return DescriptorUtilsKt.getFqNameUnsafe((ClassDescriptor) this$0);
        }

        public static Name getName(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            Name name2 = ((TypeParameterDescriptor) receiver).getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            return name2;
        }

        public static boolean isReified(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof TypeParameterDescriptor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return ((TypeParameterDescriptor) receiver).isReified();
        }

        public static boolean isInterfaceOrAnnotationClass(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            ClassifierDescriptor this$0 = ((KotlinType) receiver).getConstructor().getThis$0();
            if (this$0 instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) this$0;
                if (classDescriptor.getKind() == ClassKind.INTERFACE || classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) {
                    return true;
                }
            }
            return false;
        }

        public static KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker firstCandidate, KotlinTypeMarker secondCandidate) {
            Intrinsics.checkNotNullParameter(firstCandidate, "firstCandidate");
            Intrinsics.checkNotNullParameter(secondCandidate, "secondCandidate");
            if (!(firstCandidate instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
            }
            if (!(secondCandidate instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
            }
            KotlinType kotlinType = (KotlinType) firstCandidate;
            TypeConstructor constructor = kotlinType.getConstructor();
            IntersectionTypeConstructor intersectionTypeConstructor = constructor instanceof IntersectionTypeConstructor ? (IntersectionTypeConstructor) constructor : null;
            if (intersectionTypeConstructor != null) {
                SimpleType simpleTypeCreateType = intersectionTypeConstructor.setAlternative((KotlinType) secondCandidate).createType();
                return kotlinType.getIsMarkedNullable() ? simpleTypeCreateType.makeNullableAsSpecified(true) : simpleTypeCreateType;
            }
            throw new IllegalStateException(("Expected intersection type, found " + firstCandidate).toString());
        }

        public static boolean isFunctionOrKFunctionWithAnySuspendability(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return FunctionTypesKt.isFunctionOrKFunctionTypeWithAnySuspendability((KotlinType) receiver);
        }

        public static boolean isExtensionFunctionType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return FunctionTypesKt.isBuiltinExtensionFunctionalType((KotlinType) receiver);
        }

        public static List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
            }
            return FunctionTypesKt.getPureArgumentsForFunctionalTypeOrSubtype((KotlinType) receiver);
        }

        public static KotlinTypeMarker getFunctionTypeFromSupertypes(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return FunctionTypesKt.extractFunctionalTypeFromSupertypes((KotlinType) receiver);
        }

        public static FunctionTypeKind functionTypeKind(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof KotlinType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return FunctionTypesKt.getFunctionTypeKind((KotlinType) receiver);
        }

        public static TypeConstructorMarker getNonReflectFunctionTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, int i, FunctionTypeKind kind) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            TypeConstructor typeConstructor = FunctionTypesKt.getFunctionDescriptor(classicTypeSystemContext.getBuiltIns(), i, Intrinsics.areEqual(kind.nonReflectKind(), FunctionTypeKind.SuspendFunction.INSTANCE)).getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
            return typeConstructor;
        }

        public static TypeConstructorMarker getReflectFunctionTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, int i, FunctionTypeKind kind) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            TypeConstructor typeConstructor = FunctionTypesKt.getKFunctionDescriptor(classicTypeSystemContext.getBuiltIns(), i, Intrinsics.areEqual(kind.reflectKind(), FunctionTypeKind.KSuspendFunction.INSTANCE)).getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
            return typeConstructor;
        }

        public static TypeSubstitutorMarker createSubstitutorForSuperTypes(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker baseType) {
            Intrinsics.checkNotNullParameter(baseType, "baseType");
            if (!(baseType instanceof KotlinType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + baseType + ", " + Reflection.getOrCreateKotlinClass(baseType.getClass())).toString());
            }
            MemberScope memberScope = ((KotlinType) baseType).getMemberScope();
            SubstitutingScope substitutingScope = memberScope instanceof SubstitutingScope ? (SubstitutingScope) memberScope : null;
            return substitutingScope != null ? substitutingScope.getSubstitutor() : null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(final ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (!(type instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + type + ", " + Reflection.getOrCreateKotlinClass(type.getClass())).toString());
            }
            final TypeSubstitutor typeSubstitutorBuildSubstitutor = TypeConstructorSubstitution.INSTANCE.create((KotlinType) type).buildSubstitutor();
            return new TypeCheckerState.SupertypesPolicy.DoCustomTransform() { // from class: org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext.substitutionSupertypePolicy.2
                @Override // org.jetbrains.kotlin.types.TypeCheckerState.SupertypesPolicy
                /* JADX INFO: renamed from: transformType */
                public SimpleTypeMarker mo7474transformType(TypeCheckerState state, KotlinTypeMarker type2) {
                    Intrinsics.checkNotNullParameter(state, "state");
                    Intrinsics.checkNotNullParameter(type2, "type");
                    ClassicTypeSystemContext classicTypeSystemContext2 = ClassicTypeSystemContext.this;
                    TypeSubstitutor typeSubstitutor = typeSubstitutorBuildSubstitutor;
                    Object objLowerBoundIfFlexible = classicTypeSystemContext2.lowerBoundIfFlexible(type2);
                    Intrinsics.checkNotNull(objLowerBoundIfFlexible, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
                    KotlinType kotlinTypeSafeSubstitute = typeSubstitutor.safeSubstitute((KotlinType) objLowerBoundIfFlexible, Variance.INVARIANT);
                    Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
                    SimpleTypeMarker simpleTypeMarkerAsSimpleType = classicTypeSystemContext2.asSimpleType(kotlinTypeSafeSubstitute);
                    Intrinsics.checkNotNull(simpleTypeMarkerAsSimpleType);
                    return simpleTypeMarkerAsSimpleType;
                }
            };
        }

        public static boolean isTypeVariableType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof UnwrappedType) && (((UnwrappedType) receiver).getConstructor() instanceof NewTypeVariableConstructor);
        }
    }
}
