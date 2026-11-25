package org.jetbrains.kotlin.fir.types;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeRecursiveTypeParameterDuringErasureError;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator;
import org.jetbrains.kotlin.types.AbstractNullabilityChecker;
import org.jetbrains.kotlin.types.AbstractStrictEqualityTypeChecker;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: TypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008a\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001\u001a \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0018\u0010\r\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0007\u001a\u0012\u0010\u0011\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013\u001a'\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0015*\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018¢\u0006\u0002\u0010\u0019\u001a\u001c\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b*\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0001H\u0002\u001a\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0001H\u0000\u001a\u001a\u0010\u001f\u001a\u0004\u0018\u00010\u0001*\u00020\t2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u001b\u001a\n\u0010!\u001a\u00020\u0001*\u00020\u0001\u001a&\u0010\"\u001a\u0004\u0018\u00010#*\u00020$2\u0006\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010&\u001a\u00020\u0007\u001a\u0012\u0010\"\u001a\u00020'*\u00020(2\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u001a\u0010)\u001a\u00020\u0007*\u00020*2\u0006\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u0001\u001a0\u0010-\u001a\u00020\u0001*\u00020.2\u0006\u0010\u0002\u001a\u00020\t2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u0001002\u0006\u00102\u001a\u000203H\u0002\u001a0\u00104\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u0001002\u0006\u00102\u001a\u000203H\u0002\u001a0\u00105\u001a\u00020\u0001*\u0002012\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u0001002\u0006\u00102\u001a\u000203H\u0002\u001a$\u00106\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000107*\b\u0012\u0004\u0012\u00020\f0\u001b2\u0006\u0010\u0012\u001a\u00020\u0013\u001a#\u00108\u001a\b\u0012\u0004\u0012\u0002090\u0015*\b\u0012\u0004\u0012\u00020\f0\u001b2\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010:\u001a\n\u0010;\u001a\u00020\u0007*\u00020<\u001a\n\u0010=\u001a\u00020\u0001*\u00020\u0001\u001a\f\u0010>\u001a\u00020\u0001*\u00020\u0001H\u0002\u001a\f\u0010?\u001a\u00020\u0001*\u00020\u0001H\u0002\u001a\u001a\u0010@\u001a\u0004\u0018\u00010\u0001*\u00020\t2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u001b\u001a\u0012\u0010A\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0012\u0010A\u001a\u00020\u0007*\u00020<2\u0006\u0010\u0012\u001a\u00020\u0013\u001a\n\u0010B\u001a\u00020\u0007*\u00020\u0001\u001a\u001a\u0010C\u001a\u00020\u0007*\u00020D2\u0006\u0010E\u001a\u00020D2\u0006\u0010F\u001a\u00020G\u001a$\u0010C\u001a\u00020\u0007*\u00020\u00012\u0006\u0010H\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010I\u001a\u00020\u0007\u001a\u001c\u0010C\u001a\u00020\u0007*\u00020J2\u0006\u0010\b\u001a\u00020*2\b\u0010\u0016\u001a\u0004\u0018\u00010J\u001a\u001c\u0010K\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010&\u001a\u00020\u0007\u001a\u001c\u0010L\u001a\u00020\u0007*\u00020\u00032\u0006\u0010\u0016\u001a\u00020M2\u0006\u0010&\u001a\u00020\u0007H\u0002\u001a\u0018\u0010N\u001a\b\u0012\u0002\b\u0003\u0018\u00010O*\u00020P2\u0006\u0010\u0012\u001a\u00020\u0013\u001a\"\u0010Q\u001a\u00020R*\u00020\u00012\n\b\u0002\u0010S\u001a\u0004\u0018\u00010T2\n\b\u0002\u0010U\u001a\u0004\u0018\u00010<\u001a\u0014\u0010V\u001a\u0004\u0018\u00010W*\u00020P2\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0014\u0010V\u001a\u0004\u0018\u00010W*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0018\u0010X\u001a\b\u0012\u0002\b\u0003\u0018\u00010Y*\u00020P2\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0018\u0010X\u001a\b\u0012\u0002\b\u0003\u0018\u00010Z*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0014\u0010[\u001a\u00020\u000f*\u00020\\2\b\u0010]\u001a\u0004\u0018\u00010\\\u001a-\u0010^\u001a\u0002H_\"\b\b\u0000\u0010_*\u00020\u0001*\u0002H_2\u0012\u0010`\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u0002090a¢\u0006\u0002\u0010b\u001a)\u0010^\u001a\u0002H_\"\b\b\u0000\u0010_*\u00020\u0001*\u0002H_2\u000e\u0010c\u001a\n\u0012\u0006\b\u0001\u0012\u0002090\u0015¢\u0006\u0002\u0010d\u001a!\u0010e\u001a\u0002H_\"\b\b\u0000\u0010_*\u00020\u0001*\u0002H_2\u0006\u0010f\u001a\u00020g¢\u0006\u0002\u0010h\u001a=\u0010i\u001a\u0002H_\"\b\b\u0000\u0010_*\u00020\u0001*\u0002H_2\u0006\u0010j\u001a\u00020k2\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010f\u001a\u00020g2\b\b\u0002\u0010l\u001a\u00020\u0007¢\u0006\u0002\u0010m\u001a \u0010n\u001a\u00020R*\u00020<2\b\u0010o\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010p\u001a\u0004\u0018\u00010q\u001a\u0014\u0010r\u001a\u00020<*\u00020<2\b\u0010o\u001a\u0004\u0018\u00010\u0001\u001a\n\u0010s\u001a\u00020<*\u00020<¨\u0006t"}, d2 = {"coneFlexibleOrSimpleType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "lowerBound", "upperBound", "lowerThanBound", "", "context", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "argument", "typeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "shouldApproximateAnonymousTypesOfNonLocalDeclaration", "containingCallableVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "isInlineFunction", "canHaveSubtypes", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "captureArguments", "", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/types/model/CaptureStatus;)[Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "captureArgumentsForIntersectionType", "", "Lorg/jetbrains/kotlin/fir/types/CapturedArguments;", "captureFromArgumentsInternal", "captureFromExpressionInternal", "commonSuperTypeOrNull", SpecTypePair.NAME_types, "convertToNonRawVersion", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/types/ConeDefinitelyNotNullType;", "Lorg/jetbrains/kotlin/fir/types/ConeDefinitelyNotNullType$Companion;", "original", "avoidComprehensiveCheck", "Lorg/jetbrains/kotlin/fir/types/ConeDynamicType;", "Lorg/jetbrains/kotlin/fir/types/ConeDynamicType$Companion;", "equalTypes", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "eraseArgumentsDeeply", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", ResponseCacheMiddleware.CACHE, "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "mode", "Lorg/jetbrains/kotlin/fir/types/EraseUpperBoundMode;", "eraseAsUpperBound", "eraseToUpperBound", "eraseToUpperBoundsAssociated", "", "getProjectionsForRawType", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/FirSession;)[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "hasEnhancedNullability", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "independentInstance", "instanceWithIndependentAnnotations", "instanceWithIndependentArguments", "intersectTypesOrNull", "isExtensionFunctionType", "isRaw", "isSubtypeOf", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "other", "typeCheckerContext", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "superType", "errorTypesEqualToAnything", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "makeConeTypeDefinitelyNotNullOrNotNull", "makesSenseToBeDefinitelyNotNull", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "toClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "toFirResolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "delegatedTypeRef", "toRegularClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "toSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "visibilityForApproximation", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "container", "withArguments", ExifInterface.GPS_DIRECTION_TRUE, "replacement", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "arguments", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "withAttributes", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "withNullability", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "preserveEnhancedNullability", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeNullability;Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;Lorg/jetbrains/kotlin/fir/types/ConeAttributes;Z)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "withReplacedConeType", "newType", "firFakeSourceElementKind", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "withReplacedReturnType", "withoutEnhancedNullability", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeUtilsKt {

    /* JADX INFO: compiled from: TypeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[ConeNullability.values().length];
            try {
                iArr[ConeNullability.NULLABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConeNullability.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConeNullability.NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProjectionKind.values().length];
            try {
                iArr2[ProjectionKind.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ProjectionKind.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ProjectionKind.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ProjectionKind.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[Variance.values().length];
            try {
                iArr3[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr3[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr3[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    public static final ConeKotlinType commonSuperTypeOrNull(ConeInferenceContext coneInferenceContext, List<? extends ConeKotlinType> types) {
        Intrinsics.checkNotNullParameter(coneInferenceContext, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        int size = types.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return (ConeKotlinType) CollectionsKt.first((List) types);
        }
        KotlinTypeMarker kotlinTypeMarkerCommonSuperType = NewCommonSuperTypeCalculator.INSTANCE.commonSuperType(coneInferenceContext, types);
        Intrinsics.checkNotNull(kotlinTypeMarkerCommonSuperType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        return (ConeKotlinType) kotlinTypeMarkerCommonSuperType;
    }

    public static final ConeKotlinType intersectTypesOrNull(ConeInferenceContext coneInferenceContext, List<? extends ConeKotlinType> types) {
        Intrinsics.checkNotNullParameter(coneInferenceContext, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        int size = types.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return (ConeKotlinType) CollectionsKt.first((List) types);
        }
        return ConeTypeIntersector.INSTANCE.intersectTypes(coneInferenceContext, types);
    }

    public static final boolean equalTypes(TypeCheckerProviderContext typeCheckerProviderContext, ConeKotlinType a, ConeKotlinType b) {
        Intrinsics.checkNotNullParameter(typeCheckerProviderContext, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, typeCheckerProviderContext, a, b, false, 8, null);
    }

    private static final boolean makesSenseToBeDefinitelyNotNull(ConeTypeContext coneTypeContext, ConeSimpleKotlinType coneSimpleKotlinType, boolean z) {
        if (!(coneSimpleKotlinType instanceof ConeTypeParameterType)) {
            if ((coneSimpleKotlinType instanceof ConeTypeVariableType ? true : coneSimpleKotlinType instanceof ConeCapturedType) && (z || !AbstractNullabilityChecker.INSTANCE.isSubtypeOfAny(coneTypeContext.newTypeCheckerState(false, false), coneSimpleKotlinType))) {
                return true;
            }
        } else if (z || coneTypeContext.isNullableType(coneSimpleKotlinType)) {
            return true;
        }
        return false;
    }

    public static /* synthetic */ ConeDefinitelyNotNullType create$default(ConeDefinitelyNotNullType.Companion companion, ConeKotlinType coneKotlinType, ConeTypeContext coneTypeContext, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return create(companion, coneKotlinType, coneTypeContext, z);
    }

    public static final ConeDefinitelyNotNullType create(ConeDefinitelyNotNullType.Companion companion, ConeKotlinType original, ConeTypeContext typeContext, boolean z) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        if (original instanceof ConeDefinitelyNotNullType) {
            return (ConeDefinitelyNotNullType) original;
        }
        if (original instanceof ConeFlexibleType) {
            return create(companion, ((ConeFlexibleType) original).getLowerBound(), typeContext, z);
        }
        if (!(original instanceof ConeSimpleKotlinType)) {
            throw new NoWhenBranchMatchedException();
        }
        if (makesSenseToBeDefinitelyNotNull(typeContext, (ConeSimpleKotlinType) original, z)) {
            return new ConeDefinitelyNotNullType(ConeTypeUtilsKt.lowerBoundIfFlexible(original));
        }
        return null;
    }

    public static final ConeDynamicType create(ConeDynamicType.Companion companion, FirSession session) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return new ConeDynamicType(session.getBuiltinTypes().getNothingType().getType(), session.getBuiltinTypes().getNullableAnyType().getType());
    }

    public static final ConeKotlinType independentInstance(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeDynamicType coneDynamicType = (ConeFlexibleType) coneKotlinType;
            ConeKotlinType coneKotlinTypeIndependentInstance = independentInstance(coneDynamicType.getLowerBound());
            Intrinsics.checkNotNull(coneKotlinTypeIndependentInstance, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType");
            ConeSimpleKotlinType coneSimpleKotlinType = (ConeSimpleKotlinType) coneKotlinTypeIndependentInstance;
            ConeKotlinType coneKotlinTypeIndependentInstance2 = independentInstance(coneDynamicType.getUpperBound());
            Intrinsics.checkNotNull(coneKotlinTypeIndependentInstance2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType");
            ConeSimpleKotlinType coneSimpleKotlinType2 = (ConeSimpleKotlinType) coneKotlinTypeIndependentInstance2;
            if (coneSimpleKotlinType != coneDynamicType.getLowerBound() || coneSimpleKotlinType2 != coneDynamicType.getUpperBound()) {
                if (coneKotlinType instanceof ConeRawType) {
                    coneDynamicType = ConeRawType.INSTANCE.create(coneSimpleKotlinType, coneSimpleKotlinType2);
                } else {
                    coneDynamicType = coneKotlinType instanceof ConeDynamicType ? new ConeDynamicType(coneSimpleKotlinType, coneSimpleKotlinType2) : new ConeFlexibleType(coneSimpleKotlinType, coneSimpleKotlinType2);
                }
            }
            return coneDynamicType;
        }
        return instanceWithIndependentAnnotations(instanceWithIndependentArguments(coneKotlinType));
    }

    private static final ConeKotlinType instanceWithIndependentArguments(ConeKotlinType coneKotlinType) {
        if (coneKotlinType.getTypeArguments().length == 0) {
            return coneKotlinType;
        }
        ConeTypeProjection[] typeArguments = coneKotlinType.getType().getTypeArguments();
        ArrayList arrayList = new ArrayList(typeArguments.length);
        boolean z = false;
        for (ConeKotlinType coneKotlinType2 : typeArguments) {
            if (coneKotlinType2 instanceof ConeKotlinType) {
                ConeKotlinType coneKotlinTypeIndependentInstance = independentInstance((ConeKotlinType) coneKotlinType2);
                if (coneKotlinTypeIndependentInstance != coneKotlinType2) {
                    z = true;
                }
                coneKotlinType2 = coneKotlinTypeIndependentInstance;
            }
            arrayList.add(coneKotlinType2);
        }
        return z ? withArguments(coneKotlinType, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0])) : coneKotlinType;
    }

    private static final ConeKotlinType instanceWithIndependentAnnotations(ConeKotlinType coneKotlinType) {
        CustomAnnotationTypeAttribute customAnnotationTypeAttributeIndependentInstance;
        CustomAnnotationTypeAttribute custom = CustomAnnotationTypeAttributeKt.getCustom(coneKotlinType.getAttributes());
        return (custom == null || (customAnnotationTypeAttributeIndependentInstance = custom.independentInstance()) == custom) ? coneKotlinType : withAttributes(coneKotlinType, coneKotlinType.getAttributes().remove(custom).plus(customAnnotationTypeAttributeIndependentInstance));
    }

    public static /* synthetic */ ConeKotlinType makeConeTypeDefinitelyNotNullOrNotNull$default(ConeKotlinType coneKotlinType, ConeTypeContext coneTypeContext, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return makeConeTypeDefinitelyNotNullOrNotNull(coneKotlinType, coneTypeContext, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeKotlinType makeConeTypeDefinitelyNotNullOrNotNull(ConeKotlinType coneKotlinType, ConeTypeContext typeContext, boolean z) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        if (coneKotlinType instanceof ConeIntersectionType) {
            Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intersectedTypes, 10));
            Iterator<T> it = intersectedTypes.iterator();
            while (it.hasNext()) {
                arrayList.add(makeConeTypeDefinitelyNotNullOrNotNull((ConeKotlinType) it.next(), typeContext, z));
            }
            return new ConeIntersectionType(arrayList, null, 2, 0 == true ? 1 : 0);
        }
        ConeDefinitelyNotNullType coneDefinitelyNotNullTypeCreate = create(ConeDefinitelyNotNullType.INSTANCE, coneKotlinType, typeContext, z);
        return coneDefinitelyNotNullTypeCreate != null ? coneDefinitelyNotNullTypeCreate : withNullability$default(coneKotlinType, ConeNullability.NOT_NULL, typeContext, null, false, 12, null);
    }

    public static final <T extends ConeKotlinType> T withArguments(T t, ConeTypeProjection[] arguments) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (t.getTypeArguments() == arguments) {
            return t;
        }
        if (t instanceof ConeErrorType) {
            ConeErrorType coneErrorType = (ConeErrorType) t;
            return new ConeErrorType(coneErrorType.getDiagnostic(), coneErrorType.getIsUninferredParameter(), arguments, t.getAttributes());
        }
        if (t instanceof ConeClassLikeTypeImpl) {
            return new ConeClassLikeTypeImpl(((ConeClassLikeTypeImpl) t).getLookupTag(), arguments, t.getNullability().isNullable(), t.getAttributes());
        }
        if (t instanceof ConeDefinitelyNotNullType) {
            return new ConeDefinitelyNotNullType((ConeSimpleKotlinType) withArguments(((ConeDefinitelyNotNullType) t).getOriginal(), arguments));
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Not supported: " + Reflection.getOrCreateKotlinClass(t.getClass()), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, t);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final <T extends ConeKotlinType> T withArguments(T t, Function1<? super ConeTypeProjection, ? extends ConeTypeProjection> replacement) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        ConeTypeProjection[] typeArguments = t.getTypeArguments();
        ArrayList arrayList = new ArrayList(typeArguments.length);
        for (ConeTypeProjection coneTypeProjection : typeArguments) {
            arrayList.add(replacement.invoke(coneTypeProjection));
        }
        return (T) withArguments(t, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]));
    }

    public static final <T extends ConeKotlinType> T withAttributes(T t, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (Intrinsics.areEqual(t.getAttributes(), attributes)) {
            return t;
        }
        if (!(t instanceof ConeErrorType)) {
            if (t instanceof ConeClassLikeTypeImpl) {
                t = new ConeClassLikeTypeImpl(((ConeClassLikeTypeImpl) t).getLookupTag(), t.getTypeArguments(), t.getNullability().isNullable(), attributes);
            } else if (t instanceof ConeDefinitelyNotNullType) {
                t = new ConeDefinitelyNotNullType((ConeSimpleKotlinType) withAttributes(((ConeDefinitelyNotNullType) t).getOriginal(), attributes));
            } else if (t instanceof ConeTypeParameterTypeImpl) {
                t = new ConeTypeParameterTypeImpl(((ConeTypeParameterTypeImpl) t).getLookupTag(), t.getNullability().isNullable(), attributes);
            } else if (t instanceof ConeRawType) {
                ConeRawType coneRawType = (ConeRawType) t;
                t = ConeRawType.INSTANCE.create((ConeSimpleKotlinType) withAttributes(coneRawType.getLowerBound(), attributes), (ConeSimpleKotlinType) withAttributes(coneRawType.getUpperBound(), attributes));
            } else if (t instanceof ConeDynamicType) {
                ConeDynamicType coneDynamicType = (ConeDynamicType) t;
                t = new ConeDynamicType((ConeSimpleKotlinType) withAttributes(coneDynamicType.getLowerBound(), attributes), (ConeSimpleKotlinType) withAttributes(coneDynamicType.getUpperBound(), attributes));
            } else if (t instanceof ConeFlexibleType) {
                ConeFlexibleType coneFlexibleType = (ConeFlexibleType) t;
                t = new ConeFlexibleType((ConeSimpleKotlinType) withAttributes(coneFlexibleType.getLowerBound(), attributes), (ConeSimpleKotlinType) withAttributes(coneFlexibleType.getUpperBound(), attributes));
            } else if (t instanceof ConeTypeVariableType) {
                t = new ConeTypeVariableType(t.getNullability(), ((ConeTypeVariableType) t).getLookupTag(), attributes);
            } else if (t instanceof ConeCapturedType) {
                ConeCapturedType coneCapturedType = (ConeCapturedType) t;
                t = new ConeCapturedType(coneCapturedType.getCaptureStatus(), coneCapturedType.getLowerType(), t.getNullability(), coneCapturedType.getConstructor(), attributes, coneCapturedType.isProjectionNotNull());
            } else if (!(t instanceof ConeIntersectionType) && !(t instanceof ConeStubType) && !(t instanceof ConeIntegerLiteralType)) {
                KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Not supported: " + Reflection.getOrCreateKotlinClass(t.getClass()), null);
                ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, t);
                kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                throw kotlinIllegalArgumentExceptionWithAttachments;
            }
        }
        Intrinsics.checkNotNull(t, "null cannot be cast to non-null type T of org.jetbrains.kotlin.fir.types.TypeUtilsKt.withAttributes");
        return t;
    }

    public static /* synthetic */ ConeKotlinType withNullability$default(ConeKotlinType coneKotlinType, ConeNullability coneNullability, ConeTypeContext coneTypeContext, ConeAttributes coneAttributes, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            coneAttributes = coneKotlinType.getAttributes();
        }
        if ((i & 8) != 0) {
            z = false;
        }
        return withNullability(coneKotlinType, coneNullability, coneTypeContext, coneAttributes, z);
    }

    public static final <T extends ConeKotlinType> T withNullability(T t, final ConeNullability nullability, final ConeTypeContext typeContext, ConeAttributes attributes, final boolean z) {
        ConeSimpleKotlinType coneSimpleKotlinType;
        ConeIntersectionType coneIntersectionTypeMapTypes;
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (!z) {
            attributes = attributes.remove(CompilerConeAttributes.EnhancedNullability.INSTANCE);
        }
        ConeAttributes coneAttributes = attributes;
        if (t.getNullability() == nullability && Intrinsics.areEqual(t.getAttributes(), coneAttributes)) {
            return t;
        }
        if (!(t instanceof ConeErrorType)) {
            if (t instanceof ConeClassLikeTypeImpl) {
                t = new ConeClassLikeTypeImpl(((ConeClassLikeTypeImpl) t).getLookupTag(), t.getTypeArguments(), nullability.isNullable(), coneAttributes);
            } else if (t instanceof ConeTypeParameterTypeImpl) {
                t = new ConeTypeParameterTypeImpl(((ConeTypeParameterTypeImpl) t).getLookupTag(), nullability.isNullable(), coneAttributes);
            } else if (!(t instanceof ConeDynamicType)) {
                if (t instanceof ConeFlexibleType) {
                    if (nullability == ConeNullability.UNKNOWN) {
                        ConeFlexibleType coneFlexibleType = (ConeFlexibleType) t;
                        if (coneFlexibleType.getLowerBound().getNullability() != coneFlexibleType.getUpperBound().getNullability() || coneFlexibleType.getLowerBound().getNullability() == ConeNullability.UNKNOWN) {
                            return t;
                        }
                    }
                    ConeFlexibleType coneFlexibleType2 = (ConeFlexibleType) t;
                    t = (T) coneFlexibleOrSimpleType(typeContext, withNullability$default(coneFlexibleType2.getLowerBound(), nullability, typeContext, null, z, 4, null), withNullability$default(coneFlexibleType2.getUpperBound(), nullability, typeContext, null, z, 4, null));
                } else if (t instanceof ConeTypeVariableType) {
                    t = new ConeTypeVariableType(nullability, ((ConeTypeVariableType) t).getLookupTag(), coneAttributes);
                } else if (t instanceof ConeCapturedType) {
                    ConeCapturedType coneCapturedType = (ConeCapturedType) t;
                    t = new ConeCapturedType(coneCapturedType.getCaptureStatus(), coneCapturedType.getLowerType(), nullability, coneCapturedType.getConstructor(), coneAttributes, false, 32, null);
                } else if (t instanceof ConeIntersectionType) {
                    int i = WhenMappings.$EnumSwitchMapping$0[nullability.ordinal()];
                    if (i == 1) {
                        coneIntersectionTypeMapTypes = ConeTypeUtilsKt.mapTypes((ConeIntersectionType) t, new Function1<ConeKotlinType, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.types.TypeUtilsKt.withNullability.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final ConeKotlinType invoke(ConeKotlinType it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return TypeUtilsKt.withNullability$default(it, nullability, typeContext, null, z, 4, null);
                            }
                        });
                    } else {
                        if (i != 2 && i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        coneIntersectionTypeMapTypes = (ConeIntersectionType) t;
                    }
                    t = coneIntersectionTypeMapTypes;
                } else if (t instanceof ConeStubTypeForSyntheticFixation) {
                    t = new ConeStubTypeForSyntheticFixation(((ConeStubTypeForSyntheticFixation) t).getConstructor(), nullability);
                } else if (t instanceof ConeStubTypeForChainInference) {
                    t = new ConeStubTypeForChainInference(((ConeStubTypeForChainInference) t).getConstructor(), nullability);
                } else if (t instanceof ConeStubTypeForTypeVariableInSubtyping) {
                    t = new ConeStubTypeForTypeVariableInSubtyping(((ConeStubTypeForTypeVariableInSubtyping) t).getConstructor(), nullability);
                } else if (t instanceof ConeDefinitelyNotNullType) {
                    int i2 = WhenMappings.$EnumSwitchMapping$0[nullability.ordinal()];
                    if (i2 == 1 || i2 == 2) {
                        coneSimpleKotlinType = (ConeSimpleKotlinType) withNullability$default(((ConeDefinitelyNotNullType) t).getOriginal(), nullability, typeContext, null, z, 4, null);
                    } else if (i2 == 3) {
                        coneSimpleKotlinType = (ConeSimpleKotlinType) t;
                    } else {
                        throw new NoWhenBranchMatchedException();
                    }
                    t = coneSimpleKotlinType;
                } else if (t instanceof ConeIntegerLiteralConstantType) {
                    ConeIntegerLiteralConstantType coneIntegerLiteralConstantType = (ConeIntegerLiteralConstantType) t;
                    t = new ConeIntegerLiteralConstantTypeImpl(coneIntegerLiteralConstantType.getValue(), coneIntegerLiteralConstantType.getPossibleTypes(), coneIntegerLiteralConstantType.getIsUnsigned(), nullability);
                } else {
                    if (!(t instanceof ConeIntegerConstantOperatorType)) {
                        throw new IllegalStateException(("sealed: " + Reflection.getOrCreateKotlinClass(t.getClass())).toString());
                    }
                    t = new ConeIntegerConstantOperatorTypeImpl(((ConeIntegerConstantOperatorType) t).getIsUnsigned(), nullability);
                }
            }
        }
        Intrinsics.checkNotNull(t, "null cannot be cast to non-null type T of org.jetbrains.kotlin.fir.types.TypeUtilsKt.withNullability");
        return t;
    }

    public static final ConeKotlinType coneFlexibleOrSimpleType(ConeTypeContext typeContext, ConeKotlinType lowerBound, ConeKotlinType upperBound) {
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        if (lowerBound instanceof ConeFlexibleType) {
            return coneFlexibleOrSimpleType(typeContext, ((ConeFlexibleType) lowerBound).getLowerBound(), upperBound);
        }
        if (!(lowerBound instanceof ConeSimpleKotlinType)) {
            throw new NoWhenBranchMatchedException();
        }
        if (upperBound instanceof ConeFlexibleType) {
            return coneFlexibleOrSimpleType(typeContext, lowerBound, ((ConeFlexibleType) upperBound).getUpperBound());
        }
        if (upperBound instanceof ConeSimpleKotlinType) {
            return AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes(typeContext, lowerBound, upperBound) ? lowerBound : new ConeFlexibleType((ConeSimpleKotlinType) lowerBound, (ConeSimpleKotlinType) upperBound);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final boolean isExtensionFunctionType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return CompilerConeAttributesKt.getExtensionFunctionType(TypeExpansionUtilsKt.fullyExpandedType(ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType), session).getAttributes()) != null;
    }

    public static final FirClassifierSymbol<?> toSymbol(ConeKotlinType coneKotlinType, FirSession session) {
        ConeClassifierLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeLookupTagBasedType coneLookupTagBasedType = coneKotlinType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneKotlinType : null;
        if (coneLookupTagBasedType == null || (lookupTag = coneLookupTagBasedType.getLookupTag()) == null) {
            return null;
        }
        return LookupTagUtilsKt.toSymbol(lookupTag, session);
    }

    public static final FirClassLikeSymbol<?> toSymbol(ConeClassLikeType coneClassLikeType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), session);
    }

    public static /* synthetic */ FirResolvedTypeRef toFirResolvedTypeRef$default(ConeKotlinType coneKotlinType, KtSourceElement ktSourceElement, FirTypeRef firTypeRef, int i, Object obj) {
        if ((i & 1) != 0) {
            ktSourceElement = null;
        }
        if ((i & 2) != 0) {
            firTypeRef = null;
        }
        return toFirResolvedTypeRef(coneKotlinType, ktSourceElement, firTypeRef);
    }

    public static final FirResolvedTypeRef toFirResolvedTypeRef(ConeKotlinType coneKotlinType, KtSourceElement ktSourceElement, FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        if (coneKotlinType instanceof ConeErrorType) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setSource(ktSourceElement);
            firErrorTypeRefBuilder.setDiagnostic(((ConeErrorType) coneKotlinType).getDiagnostic());
            firErrorTypeRefBuilder.setType(coneKotlinType);
            firErrorTypeRefBuilder.setDelegatedTypeRef(firTypeRef);
            return firErrorTypeRefBuilder.mo6809build();
        }
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(ktSourceElement);
        firResolvedTypeRefBuilder.setType(coneKotlinType);
        firResolvedTypeRefBuilder.setDelegatedTypeRef(firTypeRef);
        return firResolvedTypeRefBuilder.mo6809build();
    }

    public static final FirTypeRef withoutEnhancedNullability(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        if (!(firTypeRef instanceof FirResolvedTypeRef)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!hasEnhancedNullability(firTypeRef)) {
            return firTypeRef;
        }
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(firTypeRef.getSource());
        FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) firTypeRef;
        ConeKotlinType type = firResolvedTypeRef.getType();
        ConeAttributes.Companion companion = ConeAttributes.INSTANCE;
        ConeAttributes attributes = firResolvedTypeRef.getType().getAttributes();
        ArrayList arrayList = new ArrayList();
        for (ConeAttribute<?> coneAttribute : attributes) {
            if (!Intrinsics.areEqual(coneAttribute, CompilerConeAttributes.EnhancedNullability.INSTANCE)) {
                arrayList.add(coneAttribute);
            }
        }
        firResolvedTypeRefBuilder.setType(withAttributes(type, companion.create(arrayList)));
        CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), firTypeRef.getAnnotations());
        return firResolvedTypeRefBuilder.mo6809build();
    }

    public static final FirTypeRef withReplacedReturnType(FirTypeRef firTypeRef, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        if ((firTypeRef instanceof FirResolvedTypeRef) || coneKotlinType == null) {
            return coneKotlinType == null ? firTypeRef : CopyUtilsKt.resolvedTypeFromPrototype$default(firTypeRef, coneKotlinType, null, 2, null);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    public static /* synthetic */ FirResolvedTypeRef withReplacedConeType$default(FirTypeRef firTypeRef, ConeKotlinType coneKotlinType, KtFakeSourceElementKind ktFakeSourceElementKind, int i, Object obj) {
        if ((i & 2) != 0) {
            ktFakeSourceElementKind = null;
        }
        return withReplacedConeType(firTypeRef, coneKotlinType, ktFakeSourceElementKind);
    }

    public static final FirResolvedTypeRef withReplacedConeType(FirTypeRef firTypeRef, ConeKotlinType coneKotlinType, KtFakeSourceElementKind ktFakeSourceElementKind) {
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        if (!(firTypeRef instanceof FirResolvedTypeRef)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (coneKotlinType == null) {
            return (FirResolvedTypeRef) firTypeRef;
        }
        if (ktFakeSourceElementKind != null) {
            KtSourceElement source2 = firTypeRef.getSource();
            source = source2 != null ? KtSourceElementKt.fakeElement$default(source2, ktFakeSourceElementKind, 0, 0, 6, null) : null;
        } else {
            source = firTypeRef.getSource();
        }
        if (coneKotlinType instanceof ConeErrorType) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setSource(source);
            firErrorTypeRefBuilder.setType(coneKotlinType);
            firErrorTypeRefBuilder.setDiagnostic(((ConeErrorType) coneKotlinType).getDiagnostic());
            return firErrorTypeRefBuilder.mo6809build();
        }
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(source);
        firResolvedTypeRefBuilder.setType(coneKotlinType);
        CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), firTypeRef.getAnnotations());
        FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) firTypeRef;
        firResolvedTypeRefBuilder.setDelegatedTypeRef(firResolvedTypeRef.getDelegatedTypeRef());
        firResolvedTypeRefBuilder.setFromStubType(firResolvedTypeRef.getType() instanceof ConeStubType);
        return firResolvedTypeRefBuilder.mo6809build();
    }

    public static final boolean shouldApproximateAnonymousTypesOfNonLocalDeclaration(Visibility visibility, boolean z) {
        if (Intrinsics.areEqual(visibility, Visibilities.Public.INSTANCE) ? true : Intrinsics.areEqual(visibility, Visibilities.Protected.INSTANCE) ? true : Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE)) {
            return true;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE)) {
            return z;
        }
        return false;
    }

    public static final Visibility visibilityForApproximation(FirDeclaration firDeclaration, FirDeclaration firDeclaration2) {
        Visibilities.Public visibility;
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        if (!(firDeclaration instanceof FirMemberDeclaration)) {
            return Visibilities.Local.INSTANCE;
        }
        if (firDeclaration2 == null || (firDeclaration2 instanceof FirFile)) {
            visibility = Visibilities.Public.INSTANCE;
        } else {
            FirRegularClass firRegularClass = firDeclaration2 instanceof FirRegularClass ? (FirRegularClass) firDeclaration2 : null;
            if (firRegularClass == null || (visibility = firRegularClass.getStatus().getVisibility()) == null) {
                visibility = Visibilities.Local.INSTANCE;
            }
        }
        if (!Intrinsics.areEqual(visibility, Visibilities.Local.INSTANCE)) {
            FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) firDeclaration;
            if (!Intrinsics.areEqual(firMemberDeclaration.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
                return Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE) ? Visibilities.Private.INSTANCE : firMemberDeclaration.getStatus().getVisibility();
            }
        }
        return Visibilities.Local.INSTANCE;
    }

    public static final ConeKotlinType captureFromArgumentsInternal(ConeTypeContext coneTypeContext, ConeKotlinType type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(coneTypeContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        ConeKotlinType[] coneKotlinTypeArrCaptureArguments = captureArguments(coneTypeContext, type, status);
        if (coneKotlinTypeArrCaptureArguments == null) {
            return null;
        }
        if (type instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) type;
            ConeKotlinType[] coneKotlinTypeArr = coneKotlinTypeArrCaptureArguments;
            return new ConeFlexibleType((ConeSimpleKotlinType) withArguments(coneFlexibleType.getLowerBound(), coneKotlinTypeArr), (ConeSimpleKotlinType) withArguments(coneFlexibleType.getUpperBound(), coneKotlinTypeArr));
        }
        return withArguments(type, coneKotlinTypeArrCaptureArguments);
    }

    public static final ConeKotlinType[] captureArguments(ConeTypeContext coneTypeContext, ConeKotlinType type, CaptureStatus status) {
        FirTypeParameterSymbol typeParameterSymbol;
        ConeKotlinType type2;
        ConeCapturedType coneCapturedType;
        Intrinsics.checkNotNullParameter(coneTypeContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        int length = type.getTypeArguments().length;
        if (length == 0) {
            return null;
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = coneTypeContext.typeConstructor(type);
        if (length != coneTypeContext.parametersCount(typeConstructorMarkerTypeConstructor)) {
            return null;
        }
        int i = 0;
        for (ConeTypeProjection coneTypeProjection : type.getTypeArguments()) {
            if (coneTypeProjection.getKind() != ProjectionKind.INVARIANT) {
                ConeKotlinType[] coneKotlinTypeArr = new ConeKotlinType[length];
                for (int i2 = 0; i2 < length; i2++) {
                    ConeTypeProjection coneTypeProjection2 = type.getTypeArguments()[i2];
                    if (coneTypeProjection2.getKind() == ProjectionKind.INVARIANT) {
                        coneCapturedType = ConeTypeProjectionKt.getType(coneTypeProjection2);
                        Intrinsics.checkNotNull(coneCapturedType);
                    } else {
                        if (coneTypeProjection2.getKind() == ProjectionKind.IN) {
                            Intrinsics.checkNotNull(coneTypeProjection2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection");
                            type2 = ((ConeKotlinTypeProjection) coneTypeProjection2).getType();
                        } else {
                            type2 = null;
                        }
                        coneCapturedType = new ConeCapturedType(status, type2, coneTypeProjection2, coneTypeContext.getParameter(typeConstructorMarkerTypeConstructor, i2));
                    }
                    coneKotlinTypeArr[i2] = coneCapturedType;
                }
                IntRange intRangeUntil = RangesKt.until(0, length);
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10)), 16));
                Iterator<Integer> it = intRangeUntil.iterator();
                while (it.hasNext()) {
                    int iNextInt = ((IntIterator) it).nextInt();
                    TypeParameterMarker parameter = coneTypeContext.getParameter(typeConstructorMarkerTypeConstructor, iNextInt);
                    Intrinsics.checkNotNull(parameter, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag");
                    Pair pair = TuplesKt.to(((ConeTypeParameterLookupTag) parameter).getSymbol(), coneKotlinTypeArr[iNextInt]);
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                ConeSubstitutor coneSubstitutorSubstitutorByMap = SubstitutorsKt.substitutorByMap(linkedHashMap, coneTypeContext.get$session());
                int i3 = 0;
                while (i3 < length) {
                    ConeTypeProjection coneTypeProjection3 = type.getTypeArguments()[i3];
                    ConeKotlinType coneKotlinType = coneKotlinTypeArr[i3];
                    if (coneTypeProjection3.getKind() != ProjectionKind.INVARIANT) {
                        TypeParameterMarker parameter2 = coneTypeContext.getParameter(typeConstructorMarkerTypeConstructor, i3);
                        ConeTypeParameterLookupTag coneTypeParameterLookupTag = parameter2 instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) parameter2 : null;
                        if (coneTypeParameterLookupTag != null && (typeParameterSymbol = coneTypeParameterLookupTag.getTypeParameterSymbol()) != null) {
                            FirLazyDeclarationResolverKt.lazyResolveToPhase(typeParameterSymbol, FirResolvePhase.TYPES);
                        }
                        IntRange intRangeUntil2 = RangesKt.until(i, coneTypeContext.upperBoundCount(parameter2));
                        ArrayList arrayList = new ArrayList();
                        Iterator<Integer> it2 = intRangeUntil2.iterator();
                        while (it2.hasNext()) {
                            arrayList.add(MarkerExtensionsKt.safeSubstitute(coneSubstitutorSubstitutorByMap, (TypeSystemInferenceExtensionContext) coneTypeContext, coneTypeContext.getUpperBound(parameter2, ((IntIterator) it2).nextInt())));
                        }
                        ArrayList arrayList2 = arrayList;
                        if (coneTypeProjection3.getKind() == ProjectionKind.OUT) {
                            arrayList2.add(coneTypeContext.getType(coneTypeProjection3));
                        }
                        if (!(coneKotlinType instanceof ConeCapturedType)) {
                            throw new IllegalArgumentException("Failed requirement.".toString());
                        }
                        ((ConeCapturedType) coneKotlinType).getConstructor().setSupertypes(arrayList2);
                    }
                    i3++;
                    i = 0;
                }
                return coneKotlinTypeArr;
            }
        }
        return null;
    }

    public static final ConeKotlinType captureFromExpressionInternal(ConeTypeContext coneTypeContext, ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(coneTypeContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof ConeIntersectionType) && !(type instanceof ConeFlexibleType)) {
            return captureFromArgumentsInternal(coneTypeContext, type, CaptureStatus.FROM_EXPRESSION);
        }
        List<CapturedArguments> listCaptureArgumentsForIntersectionType = captureArgumentsForIntersectionType(coneTypeContext, type);
        if (listCaptureArgumentsForIntersectionType == null) {
            return null;
        }
        if (type instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) type;
            return new ConeFlexibleType(ConeTypeUtilsKt.lowerBoundIfFlexible(withNullability$default(coneTypeContext.intersectTypes((List<? extends KotlinTypeMarker>) captureFromExpressionInternal$replaceArgumentsWithCapturedArgumentsByIntersectionComponents(listCaptureArgumentsForIntersectionType, coneTypeContext, coneFlexibleType.getLowerBound())), ConeNullability.INSTANCE.create(ConeTypeUtilsKt.isMarkedNullable(coneFlexibleType.getLowerBound())), coneTypeContext, null, false, 12, null)), ConeTypeUtilsKt.upperBoundIfFlexible(withNullability$default(coneTypeContext.intersectTypes((List<? extends KotlinTypeMarker>) captureFromExpressionInternal$replaceArgumentsWithCapturedArgumentsByIntersectionComponents(listCaptureArgumentsForIntersectionType, coneTypeContext, coneFlexibleType.getUpperBound())), ConeNullability.INSTANCE.create(ConeTypeUtilsKt.isMarkedNullable(coneFlexibleType.getUpperBound())), coneTypeContext, null, false, 12, null)));
        }
        if (!(type instanceof ConeSimpleKotlinType)) {
            throw new NoWhenBranchMatchedException();
        }
        KotlinTypeMarker kotlinTypeMarkerWithNullability = coneTypeContext.withNullability(coneTypeContext.intersectTypes((List<? extends KotlinTypeMarker>) captureFromExpressionInternal$replaceArgumentsWithCapturedArgumentsByIntersectionComponents(listCaptureArgumentsForIntersectionType, coneTypeContext, (ConeSimpleKotlinType) type)), ConeTypeUtilsKt.isMarkedNullable(type));
        Intrinsics.checkNotNull(kotlinTypeMarkerWithNullability, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        return (ConeKotlinType) kotlinTypeMarkerWithNullability;
    }

    private static final ConeTypeProjection[] captureFromExpressionInternal$findCorrespondingCapturedArgumentsForType(List<CapturedArguments> list, ConeTypeContext coneTypeContext, ConeKotlinType coneKotlinType) {
        Object next;
        Iterator<T> it = list.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((CapturedArguments) next).isSuitableForType(coneKotlinType, coneTypeContext));
        CapturedArguments capturedArguments = (CapturedArguments) next;
        if (capturedArguments != null) {
            return capturedArguments.getCapturedArguments();
        }
        return null;
    }

    private static final List<ConeKotlinType> captureFromExpressionInternal$replaceArgumentsWithCapturedArgumentsByIntersectionComponents(List<CapturedArguments> list, ConeTypeContext coneTypeContext, ConeSimpleKotlinType coneSimpleKotlinType) {
        if (coneSimpleKotlinType instanceof ConeIntersectionType) {
            Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneSimpleKotlinType).getIntersectedTypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intersectedTypes, 10));
            for (ConeKotlinType coneKotlinTypeWithArguments : intersectedTypes) {
                ConeTypeProjection[] coneTypeProjectionArrCaptureFromExpressionInternal$findCorrespondingCapturedArgumentsForType = captureFromExpressionInternal$findCorrespondingCapturedArgumentsForType(list, coneTypeContext, coneKotlinTypeWithArguments);
                if (coneTypeProjectionArrCaptureFromExpressionInternal$findCorrespondingCapturedArgumentsForType != null) {
                    coneKotlinTypeWithArguments = withArguments(coneKotlinTypeWithArguments, coneTypeProjectionArrCaptureFromExpressionInternal$findCorrespondingCapturedArgumentsForType);
                }
                arrayList.add(coneKotlinTypeWithArguments);
            }
            return arrayList;
        }
        ConeSimpleKotlinType coneSimpleKotlinType2 = coneSimpleKotlinType;
        ConeTypeProjection[] coneTypeProjectionArrCaptureFromExpressionInternal$findCorrespondingCapturedArgumentsForType2 = captureFromExpressionInternal$findCorrespondingCapturedArgumentsForType(list, coneTypeContext, coneSimpleKotlinType2);
        if (coneTypeProjectionArrCaptureFromExpressionInternal$findCorrespondingCapturedArgumentsForType2 == null) {
            return CollectionsKt.listOf(coneSimpleKotlinType);
        }
        return CollectionsKt.listOf(withArguments(coneSimpleKotlinType2, coneTypeProjectionArrCaptureFromExpressionInternal$findCorrespondingCapturedArgumentsForType2));
    }

    private static final Collection<ConeKotlinType> captureArgumentsForIntersectionType$getTypesToCapture(ConeKotlinType coneKotlinType) {
        return coneKotlinType instanceof ConeIntersectionType ? ((ConeIntersectionType) coneKotlinType).getIntersectedTypes() : CollectionsKt.listOf(coneKotlinType);
    }

    private static final List<CapturedArguments> captureArgumentsForIntersectionType(ConeTypeContext coneTypeContext, ConeKotlinType coneKotlinType) {
        ArrayList intersectedTypes;
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            List listPlus = CollectionsKt.plus((Collection) captureArgumentsForIntersectionType$getTypesToCapture(coneFlexibleType.getLowerBound()), (Iterable) captureArgumentsForIntersectionType$getTypesToCapture(coneFlexibleType.getUpperBound()));
            HashSet hashSet = new HashSet();
            ArrayList arrayList = new ArrayList();
            for (Object obj : listPlus) {
                ConeKotlinType coneKotlinType2 = (ConeKotlinType) obj;
                Object baseBoundFqNameByMutability = ConeFlexibleTypeBoundsChecker.INSTANCE.getBaseBoundFqNameByMutability(coneKotlinType2);
                if (baseBoundFqNameByMutability == null) {
                    baseBoundFqNameByMutability = TypeSystemContextHelpersKt.typeConstructor(coneKotlinType2, coneTypeContext);
                }
                if (hashSet.add(TuplesKt.to(baseBoundFqNameByMutability, coneKotlinType2.getTypeArguments()))) {
                    arrayList.add(obj);
                }
            }
            intersectedTypes = arrayList;
        } else {
            if (!(coneKotlinType instanceof ConeIntersectionType)) {
                throw new IllegalStateException("Should not be here".toString());
            }
            intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it = intersectedTypes.iterator();
        boolean z = false;
        while (true) {
            CapturedArguments capturedArguments = null;
            if (!it.hasNext()) {
                break;
            }
            ConeKotlinType coneKotlinType3 = (ConeKotlinType) it.next();
            ConeKotlinType[] coneKotlinTypeArrCaptureArguments = captureArguments(coneTypeContext, coneKotlinType3, CaptureStatus.FROM_EXPRESSION);
            if (coneKotlinTypeArrCaptureArguments != null) {
                capturedArguments = new CapturedArguments(coneKotlinTypeArrCaptureArguments, coneKotlinType3);
                z = true;
            }
            if (capturedArguments != null) {
                arrayList2.add(capturedArguments);
            }
        }
        ArrayList arrayList3 = arrayList2;
        if (z) {
            return arrayList3;
        }
        return null;
    }

    public static /* synthetic */ boolean isSubtypeOf$default(ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, FirSession firSession, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return isSubtypeOf(coneKotlinType, coneKotlinType2, firSession, z);
    }

    public static final boolean isSubtypeOf(ConeKotlinType coneKotlinType, ConeKotlinType superType, FirSession session, boolean z) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(superType, "superType");
        Intrinsics.checkNotNullParameter(session, "session");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, TypeComponentsKt.getTypeContext(session).newTypeCheckerState(z, false), (KotlinTypeMarker) coneKotlinType, (KotlinTypeMarker) superType, false, 8, (Object) null);
    }

    public static final boolean isSubtypeOf(FirCallableDeclaration firCallableDeclaration, FirCallableDeclaration other, TypeCheckerState typeCheckerContext) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(typeCheckerContext, "typeCheckerContext");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerContext, (KotlinTypeMarker) FirTypeUtilsKt.getConeType(firCallableDeclaration.getReturnTypeRef()), (KotlinTypeMarker) FirTypeUtilsKt.getConeType(other.getReturnTypeRef()), false, 8, (Object) null);
    }

    public static final boolean canHaveSubtypes(ConeKotlinType coneKotlinType, FirSession session) {
        FirRegularClassSymbol regularClassSymbol;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!ConeTypeUtilsKt.isMarkedNullable(coneKotlinType) && (regularClassSymbol = toRegularClassSymbol(coneKotlinType, session)) != null && regularClassSymbol.getClassKind() != ClassKind.ENUM_CLASS) {
            FirRegularClassSymbol firRegularClassSymbol = regularClassSymbol;
            if (!firRegularClassSymbol.getRawStatus().isExpect() && firRegularClassSymbol.getResolvedStatus().getModality() == Modality.FINAL) {
                int i = 0;
                for (Object obj : regularClassSymbol.getTypeParameterSymbols()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    FirTypeParameterSymbol firTypeParameterSymbol = (FirTypeParameterSymbol) obj;
                    ConeTypeProjection coneTypeProjection = coneKotlinType.getTypeArguments()[i];
                    if (ConeTypeProjectionKt.isStarProjection(coneTypeProjection)) {
                        return true;
                    }
                    ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
                    Intrinsics.checkNotNull(type);
                    int i3 = WhenMappings.$EnumSwitchMapping$2[firTypeParameterSymbol.getVariance().ordinal()];
                    if (i3 == 1) {
                        int i4 = WhenMappings.$EnumSwitchMapping$1[coneTypeProjection.getKind().ordinal()];
                        if (i4 == 1) {
                            if (lowerThanBound(TypeComponentsKt.getTypeContext(session), type, firTypeParameterSymbol) || canHaveSubtypes(type, session)) {
                                return true;
                            }
                        } else if (i4 == 2) {
                            if (lowerThanBound(TypeComponentsKt.getTypeContext(session), type, firTypeParameterSymbol)) {
                                return true;
                            }
                        } else if (i4 != 3) {
                            if (i4 == 4) {
                                return true;
                            }
                        } else if (canHaveSubtypes(type, session)) {
                            return true;
                        }
                    } else if (i3 != 2) {
                        if (i3 != 3) {
                            continue;
                        } else if (coneTypeProjection.getKind() != ProjectionKind.IN) {
                            if (canHaveSubtypes(type, session)) {
                                return true;
                            }
                        } else if (lowerThanBound(TypeComponentsKt.getTypeContext(session), type, firTypeParameterSymbol)) {
                            return true;
                        }
                    } else if (coneTypeProjection.getKind() != ProjectionKind.OUT) {
                        if (lowerThanBound(TypeComponentsKt.getTypeContext(session), type, firTypeParameterSymbol)) {
                            return true;
                        }
                    } else if (canHaveSubtypes(type, session)) {
                        return true;
                    }
                    i = i2;
                }
                return false;
            }
        }
        return true;
    }

    public static final FirRegularClassSymbol toRegularClassSymbol(ConeClassLikeType coneClassLikeType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassLikeSymbol<?> symbol = toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, session, null, 2, null), session);
        if (symbol instanceof FirRegularClassSymbol) {
            return (FirRegularClassSymbol) symbol;
        }
        return null;
    }

    public static final FirRegularClassSymbol toRegularClassSymbol(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeClassLikeType coneClassLikeType = coneKotlinType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinType : null;
        if (coneClassLikeType != null) {
            return toRegularClassSymbol(coneClassLikeType, session);
        }
        return null;
    }

    public static final FirClassSymbol<?> toClassSymbol(ConeClassLikeType coneClassLikeType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassLikeSymbol<?> symbol = toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, session, null, 2, null), session);
        if (symbol instanceof FirClassSymbol) {
            return (FirClassSymbol) symbol;
        }
        return null;
    }

    private static final boolean lowerThanBound(ConeInferenceContext coneInferenceContext, ConeKotlinType coneKotlinType, FirTypeParameterSymbol firTypeParameterSymbol) {
        for (FirResolvedTypeRef firResolvedTypeRef : firTypeParameterSymbol.getResolvedBounds()) {
            if (!Intrinsics.areEqual(coneKotlinType, FirTypeUtilsKt.getConeType(firResolvedTypeRef)) && isSubtypeOf(coneKotlinType, coneInferenceContext, FirTypeUtilsKt.getConeType(firResolvedTypeRef))) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isSubtypeOf(KotlinTypeMarker kotlinTypeMarker, TypeCheckerProviderContext context, KotlinTypeMarker kotlinTypeMarker2) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return kotlinTypeMarker2 != null && AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, context, kotlinTypeMarker, kotlinTypeMarker2, false, 8, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final Map<FirTypeParameterSymbol, ConeKotlinType> eraseToUpperBoundsAssociated(List<FirTypeParameterSymbol> list, FirSession session) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<FirTypeParameterSymbol> list2 = list;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj : list2) {
            linkedHashMap2.put(obj, eraseToUpperBound((FirTypeParameter) ((FirTypeParameterSymbol) obj).getFir(), session, linkedHashMap, EraseUpperBoundMode.FOR_EMPTY_INTERSECTION_CHECK));
        }
        return linkedHashMap2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeTypeProjection[] getProjectionsForRawType(List<FirTypeParameterSymbol> list, FirSession session) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size = list.size();
        ConeTypeProjection[] coneTypeProjectionArr = new ConeTypeProjection[size];
        for (int i = 0; i < size; i++) {
            coneTypeProjectionArr[i] = eraseToUpperBound((FirTypeParameter) list.get(i).getFir(), session, linkedHashMap, EraseUpperBoundMode.FOR_RAW_TYPE_ERASURE);
        }
        return coneTypeProjectionArr;
    }

    private static final ConeKotlinType eraseToUpperBound$eraseAsUpperBound(FirSession firSession, Map<FirTypeParameter, ConeKotlinType> map, EraseUpperBoundMode eraseUpperBoundMode, FirResolvedTypeRef firResolvedTypeRef) {
        return eraseAsUpperBound(FirTypeUtilsKt.getConeType(firResolvedTypeRef), firSession, map, eraseUpperBoundMode);
    }

    private static final ConeKotlinType eraseArgumentsDeeply(SimpleTypeMarker simpleTypeMarker, final ConeInferenceContext coneInferenceContext, final Map<FirTypeParameter, ConeKotlinType> map, final EraseUpperBoundMode eraseUpperBoundMode) {
        Object objReplaceArgumentsDeeply = coneInferenceContext.replaceArgumentsDeeply(simpleTypeMarker, (Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker>) new Function1<TypeArgumentMarker, TypeArgumentMarker>() { // from class: org.jetbrains.kotlin.fir.types.TypeUtilsKt$eraseArgumentsDeeply$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final TypeArgumentMarker invoke(TypeArgumentMarker typeArgument) {
                Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
                if (coneInferenceContext.isStarProjection(typeArgument)) {
                    return typeArgument;
                }
                ConeInferenceContext coneInferenceContext2 = coneInferenceContext;
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = coneInferenceContext2.typeConstructor(coneInferenceContext2.getType(typeArgument));
                if (!coneInferenceContext.isTypeParameterTypeConstructor(typeConstructorMarkerTypeConstructor)) {
                    typeConstructorMarkerTypeConstructor = null;
                }
                if (typeConstructorMarkerTypeConstructor == null) {
                    return typeArgument;
                }
                ConeKotlinType coneKotlinTypeEraseToUpperBound = TypeUtilsKt.eraseToUpperBound((FirTypeParameter) ((ConeTypeParameterLookupTag) typeConstructorMarkerTypeConstructor).getTypeParameterSymbol().getFir(), coneInferenceContext.get$session(), map, eraseUpperBoundMode);
                ConeErrorType coneErrorType = coneKotlinTypeEraseToUpperBound instanceof ConeErrorType ? (ConeErrorType) coneKotlinTypeEraseToUpperBound : null;
                if ((coneErrorType != null ? coneErrorType.getDiagnostic() : null) instanceof ConeRecursiveTypeParameterDuringErasureError) {
                    return ConeStarProjection.INSTANCE;
                }
                if (eraseUpperBoundMode == EraseUpperBoundMode.FOR_RAW_TYPE_ERASURE) {
                    return coneKotlinTypeEraseToUpperBound;
                }
                return ConeTypeUtilsKt.toTypeProjection(coneKotlinTypeEraseToUpperBound, ProjectionKind.OUT);
            }
        });
        Intrinsics.checkNotNull(objReplaceArgumentsDeeply, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        return (ConeKotlinType) objReplaceArgumentsDeeply;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final ConeKotlinType eraseAsUpperBound(ConeKotlinType coneKotlinType, FirSession firSession, Map<FirTypeParameter, ConeKotlinType> map, EraseUpperBoundMode eraseUpperBoundMode) {
        if (coneKotlinType instanceof ConeClassLikeType) {
            return eraseArgumentsDeeply((SimpleTypeMarker) coneKotlinType, TypeComponentsKt.getTypeContext(firSession), map, eraseUpperBoundMode);
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            return coneFlexibleOrSimpleType(TypeComponentsKt.getTypeContext(firSession), eraseAsUpperBound(coneFlexibleType.getLowerBound(), firSession, map, eraseUpperBoundMode), eraseAsUpperBound(coneFlexibleType.getUpperBound(), firSession, map, eraseUpperBoundMode));
        }
        if (coneKotlinType instanceof ConeTypeParameterType) {
            ConeKotlinType coneKotlinTypeEraseToUpperBound = eraseToUpperBound((FirTypeParameter) ((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().getFir(), firSession, map, eraseUpperBoundMode);
            return ConeTypeUtilsKt.isNullable(coneKotlinType) ? withNullability$default(coneKotlinTypeEraseToUpperBound, coneKotlinType.getNullability(), TypeComponentsKt.getTypeContext(firSession), null, false, 12, null) : coneKotlinTypeEraseToUpperBound;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return makeConeTypeDefinitelyNotNullOrNotNull$default(eraseAsUpperBound(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), firSession, map, eraseUpperBoundMode), TypeComponentsKt.getTypeContext(firSession), false, 2, null);
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("unexpected Java type parameter upper bound kind: " + Reflection.getOrCreateKotlinClass(coneKotlinType.getClass()), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, coneKotlinType);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final boolean isRaw(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType).getAttributes().contains(CompilerConeAttributes.RawType.INSTANCE);
    }

    public static final ConeKotlinType convertToNonRawVersion(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        if (!isRaw(coneKotlinType)) {
            return coneKotlinType;
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            return new ConeFlexibleType((ConeSimpleKotlinType) withAttributes(coneFlexibleType.getLowerBound(), coneKotlinType.getAttributes().remove(CompilerConeAttributes.RawType.INSTANCE)), coneFlexibleType.getUpperBound());
        }
        return withAttributes(coneKotlinType, coneKotlinType.getAttributes().remove(CompilerConeAttributes.RawType.INSTANCE));
    }

    public static final boolean isExtensionFunctionType(FirTypeRef firTypeRef, FirSession session) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
        return coneKotlinType != null && isExtensionFunctionType(coneKotlinType, session);
    }

    public static final boolean hasEnhancedNullability(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
        return coneKotlinType != null && CompilerConeAttributesKt.getHasEnhancedNullability(coneKotlinType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConeKotlinType eraseToUpperBound(FirTypeParameter firTypeParameter, FirSession firSession, Map<FirTypeParameter, ConeKotlinType> map, EraseUpperBoundMode eraseUpperBoundMode) {
        ConeClassLikeType coneClassLikeTypeEraseToUpperBound$eraseAsUpperBound;
        ConeKotlinType coneKotlinType = map.get(firTypeParameter);
        if (coneKotlinType == null) {
            map.put(firTypeParameter, new ConeErrorType(new ConeRecursiveTypeParameterDuringErasureError(firTypeParameter.getName()), false, null, null, 14, null));
            if (eraseUpperBoundMode == EraseUpperBoundMode.FOR_EMPTY_INTERSECTION_CHECK) {
                ConeTypeIntersector coneTypeIntersector = ConeTypeIntersector.INSTANCE;
                ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(firSession);
                List<FirResolvedTypeRef> resolvedBounds = firTypeParameter.getSymbol().getResolvedBounds();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
                Iterator<T> it = resolvedBounds.iterator();
                while (it.hasNext()) {
                    arrayList.add(eraseToUpperBound$eraseAsUpperBound(firSession, map, eraseUpperBoundMode, (FirResolvedTypeRef) it.next()));
                }
                coneClassLikeTypeEraseToUpperBound$eraseAsUpperBound = coneTypeIntersector.intersectTypes(typeContext, arrayList);
            } else {
                FirTypeRef firTypeRef = (FirTypeRef) CollectionsKt.first((List) firTypeParameter.getBounds());
                coneClassLikeTypeEraseToUpperBound$eraseAsUpperBound = firTypeRef instanceof FirResolvedTypeRef ? eraseToUpperBound$eraseAsUpperBound(firSession, map, eraseUpperBoundMode, (FirResolvedTypeRef) firTypeRef) : firSession.getBuiltinTypes().getAnyType().getType();
            }
            coneKotlinType = coneClassLikeTypeEraseToUpperBound$eraseAsUpperBound;
            map.put(firTypeParameter, coneKotlinType);
        }
        return coneKotlinType;
    }
}
