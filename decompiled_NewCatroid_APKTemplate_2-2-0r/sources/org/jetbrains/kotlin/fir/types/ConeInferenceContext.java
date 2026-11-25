package org.jetbrains.kotlin.fir.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.PrimitivesKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeIntermediateDiagnostic;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.resolve.substitution.NoSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.AbstractTypeRefiner;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: ConeInferenceContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ü\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\b\u0007\n\u0002\u0018\u0002\bf\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J0\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eH\u0016J>\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00112\u0006\u0010$\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u00042\u000e\u0010&\u001a\n\u0012\u0004\u0012\u00020'\u0018\u00010\u0011H\u0016J\u0010\u0010(\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J\u0012\u00100\u001a\u0004\u0018\u0001012\u0006\u00102\u001a\u00020\u0012H\u0016J\u0018\u00103\u001a\u00020\u000f2\u0006\u00104\u001a\u00020\u00122\u0006\u00105\u001a\u000206H\u0016J\u0018\u00107\u001a\u00020\u00122\u0006\u00108\u001a\u00020\u00122\u0006\u00109\u001a\u00020\u0012H\u0016J\u0010\u0010:\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\"H\u0016J\u0018\u0010;\u001a\u0004\u0018\u00010\u001e2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0011H\u0016J\u0018\u0010=\u001a\u00020\"2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0016J\u0018\u0010B\u001a\u00020\"2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0016J\u0018\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u00042\u0006\u0010F\u001a\u00020\u0004H\u0016J\b\u0010G\u001a\u00020\u000bH\u0016J\b\u0010H\u001a\u00020\u000bH\u0016J\b\u0010I\u001a\u00020\u000bH\u0016J\u001c\u0010J\u001a\u00020\u00172\u0012\u0010K\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00120LH\u0016J\u001c\u0010M\u001a\b\u0012\u0004\u0012\u00020'0\u00112\f\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016J\b\u0010O\u001a\u00020\u0004H\u0016J\f\u0010P\u001a\u00020\u0004*\u00020\u0012H\u0016J\f\u0010\u0014\u001a\u00020\u0015*\u00020\rH\u0016J \u0010Q\u001a\u00020\u0004*\u00020\u00122\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00040SH\u0016J\"\u0010T\u001a\u00020\u0004*\u0004\u0018\u00010\u00122\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00040SH\u0002J\f\u0010U\u001a\u00020\u0012*\u00020\u0012H\u0016J\f\u0010V\u001a\u00020\u001e*\u00020.H\u0016J\f\u0010W\u001a\u00020\u0012*\u00020\u0012H\u0016J\u0012\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011*\u00020\u0012H\u0016J\f\u0010Y\u001a\u00020\"*\u00020.H\u0016J\u000e\u0010Z\u001a\u0004\u0018\u00010A*\u00020\u0012H\u0016J\f\u0010[\u001a\u00020\u0012*\u00020\"H\u0016J\f\u0010\\\u001a\u00020\u0012*\u00020\u0012H\u0016J\f\u0010]\u001a\u00020^*\u00020,H\u0016J\f\u0010_\u001a\u00020\u0004*\u00020\u0012H\u0016J\f\u0010`\u001a\u00020\u0004*\u00020\u0012H\u0016J\f\u0010a\u001a\u00020\u0004*\u00020\u0012H\u0016J\f\u0010b\u001a\u00020\u0004*\u00020\"H\u0016J\f\u0010c\u001a\u00020\u0004*\u00020^H\u0016J\f\u0010d\u001a\u00020\u0004*\u00020'H\u0002J\f\u0010%\u001a\u00020\u0004*\u00020\u001eH\u0016J\f\u0010e\u001a\u00020\u0004*\u00020\u0012H\u0016J\f\u0010f\u001a\u00020\u0004*\u00020\"H\u0016J\f\u0010g\u001a\u00020\u0004*\u00020\u0012H\u0016J\f\u0010h\u001a\u00020\u0004*\u00020\rH\u0016J\f\u0010i\u001a\u00020\u0004*\u00020\rH\u0016J\f\u0010j\u001a\u00020\u0004*\u00020\u0012H\u0016J\f\u0010k\u001a\u00020\u0004*\u00020\u0012H\u0016J \u0010l\u001a\u00020\u0004*\u00020m2\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020m\u0012\u0004\u0012\u00020\u00040SH\u0016J\f\u0010n\u001a\u00020\u0004*\u00020\"H\u0016J\f\u0010o\u001a\u00020\u0004*\u00020\"H\u0016J\f\u0010p\u001a\u00020\u0004*\u00020\u0012H\u0016J\f\u0010q\u001a\u00020\u0004*\u00020\"H\u0016J\f\u0010r\u001a\u00020\u0012*\u00020\u0012H\u0016J\f\u0010s\u001a\u00020\u001e*\u00020\u001eH\u0016J\f\u0010t\u001a\u00020\u001e*\u00020uH\u0016J\f\u0010v\u001a\u00020\u0012*\u00020\u0012H\u0016J\f\u0010w\u001a\u00020\u0012*\u00020\u0012H\u0016J \u0010x\u001a\u00020\u001e*\u00020\u001e2\u0012\u0010y\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0SH\u0016J\u001a\u0010x\u001a\u00020\u001e*\u00020\u001e2\f\u0010z\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011H\u0016J\u001a\u0010{\u001a\u00020\u0012*\u00020\u00122\f\u0010|\u001a\b\u0012\u0004\u0012\u00020'0\u0011H\u0016J\u0014\u0010}\u001a\u00020\u0012*\u0002012\u0006\u00104\u001a\u00020\u0012H\u0016J\u0014\u0010~\u001a\u0004\u0018\u00010\u0012*\b\u0012\u0004\u0012\u00020\u00120\u007fH\u0016J\r\u0010\u0080\u0001\u001a\u00020\u001e*\u00020\"H\u0016J\r\u0010\u0081\u0001\u001a\u00020\u000f*\u00020\rH\u0016J\r\u0010\u0082\u0001\u001a\u00020?*\u00020\u0012H\u0016J\r\u0010\u0082\u0001\u001a\u00020?*\u00020\u001eH\u0016J\u000e\u0010)\u001a\u0004\u0018\u00010**\u00020\rH\u0016J\r\u0010\u0083\u0001\u001a\u00020\u0012*\u00020\rH\u0016J\u0015\u0010\u0084\u0001\u001a\u00020\u0012*\u00020\u00122\u0006\u0010$\u001a\u00020\u0004H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0085\u0001²\u0006\f\u0010\u0086\u0001\u001a\u00030\u0087\u0001X\u008a\u0084\u0002À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "isK2", "", "()Z", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "createCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "constructorProjection", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "constructorSupertypes", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "lowerType", "captureStatus", "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "createErrorType", "Lorg/jetbrains/kotlin/fir/types/ConeErrorType;", "debugName", "", "createFlexibleType", "lowerBound", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "arguments", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "createStubTypeForBuilderInference", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "createStubTypeForTypeVariablesInSubtyping", "createSubstitutorForSuperTypes", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "baseType", "createTypeArgument", ModuleXmlParser.TYPE, "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createTypeWithAlternativeForIntersectionResult", "firstCandidate", "secondCandidate", "createUninferredType", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "getNonReflectFunctionTypeConstructor", "parametersNumber", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getReflectFunctionTypeConstructor", "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "typeSubstitutorByTypeConstructor", "map", "", "unionTypeAttributes", SpecTypePair.NAME_types, "useRefinedBoundsForTypeVariableInFlexiblePosition", "canHaveUndefinedNullability", "contains", "predicate", "Lkotlin/Function1;", "containsInternal", "convertToNonRaw", "defaultType", "eraseContainingTypeParameters", "extractArgumentsForFunctionTypeOrSubtype", "freshTypeConstructor", "functionTypeKind", "getApproximatedIntegerLiteralType", "getFunctionTypeFromSupertypes", "getOriginalTypeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "hasExactAnnotation", "hasNoInferAnnotation", "isBuiltinFunctionTypeOrSubtype", "isCapturedTypeConstructor", "isContainedInInvariantOrContravariantPositions", "isCustomAttribute", "isExtensionFunctionType", "isFinalClassConstructor", "isFunctionOrKFunctionWithAnySuspendability", "isOldCapturedType", "isProjectionNotNull", "isSignedOrUnsignedNumberType", "isSpecial", "isTypeOrSubtypeOf", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isTypeParameterTypeConstructor", "isTypeVariable", "isUnit", "isUnitTypeConstructor", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "removeAnnotations", "removeExactAnnotation", "replaceArguments", "replacement", "newArguments", "replaceCustomAttributes", "newAttributes", "safeSubstitute", "singleBestRepresentative", "", "toErrorType", "typeConstructorProjection", "typeDepth", "withNotNullProjection", "withNullability", "providers", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutorByMap;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConeInferenceContext extends ConeTypeContext, TypeSystemInferenceExtensionContext {

    /* JADX INFO: compiled from: ConeInferenceContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.INV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isK2() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeTypeContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isSpecial(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean useRefinedBoundsForTypeVariableInFlexiblePosition() {
        return true;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* bridge */ /* synthetic */ default TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map map) {
        return typeSubstitutorByTypeConstructor((Map<TypeConstructorMarker, ? extends KotlinTypeMarker>) map);
    }

    default FirSymbolProvider getSymbolProvider() {
        return FirSymbolProviderKt.getSymbolProvider(get$session());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    default ConeClassLikeType nullableNothingType() {
        return get$session().getBuiltinTypes().getNullableNothingType().getType();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeTypeContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    default ConeClassLikeType nullableAnyType() {
        return get$session().getBuiltinTypes().getNullableAnyType().getType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    default ConeClassLikeType nothingType() {
        return get$session().getBuiltinTypes().getNothingType().getType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    default ConeClassLikeType anyType() {
        return get$session().getBuiltinTypes().getAnyType().getType();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    default KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        if (!(lowerBound instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(upperBound instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeUtilsKt.coneFlexibleOrSimpleType(this, (ConeKotlinType) lowerBound, (ConeKotlinType) upperBound);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    default SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes) {
        ArrayList arrayList;
        ConeAttributes empty;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (attributes != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : attributes) {
                if (obj instanceof ConeAttribute) {
                    arrayList2.add(obj);
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        if (isExtensionFunction) {
            if (!(constructor instanceof ConeClassLikeLookupTag) || !FunctionalTypeUtilsKt.isSomeFunctionType((ConeClassLikeLookupTag) constructor, get$session())) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (arrayList != null) {
                arrayList.add(CompilerConeAttributes.ExtensionFunctionType.INSTANCE);
                empty = ConeAttributes.INSTANCE.create(arrayList);
            } else {
                empty = ConeAttributes.INSTANCE.getWithExtensionFunctionType();
            }
        } else if (arrayList == null || (empty = ConeAttributes.INSTANCE.create(arrayList)) == null) {
            empty = ConeAttributes.INSTANCE.getEmpty();
        }
        if (constructor instanceof ConeClassLikeLookupTag) {
            return new ConeClassLikeTypeImpl((ConeClassLikeLookupTag) constructor, (ConeTypeProjection[]) arguments.toArray(new ConeTypeProjection[0]), nullable, empty);
        }
        if (constructor instanceof ConeTypeParameterLookupTag) {
            return new ConeTypeParameterTypeImpl((ConeTypeParameterLookupTag) constructor, nullable, empty);
        }
        throw new IllegalStateException("!".toString());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    default TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        if (!(type instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
        if (i == 1) {
            return (TypeArgumentMarker) type;
        }
        if (i == 2) {
            return new ConeKotlinTypeProjectionIn((ConeKotlinType) type);
        }
        if (i == 3) {
            return new ConeKotlinTypeProjectionOut((ConeKotlinType) type);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    default TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return ConeStarProjection.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
    default TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
        return new TypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything, true, this, new ConeTypePreparator(get$session()), AbstractTypeRefiner.Default.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    default boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (kotlinTypeMarker instanceof ConeKotlinType) {
            return (kotlinTypeMarker instanceof ConeCapturedType) || (kotlinTypeMarker instanceof ConeTypeVariableType) || (kotlinTypeMarker instanceof ConeTypeParameterType);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    default boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return CompilerConeAttributesKt.isExtensionFunctionType((ConeKotlinType) simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker) {
        Intrinsics.checkNotNullParameter(stubTypeMarker, "<this>");
        if (!(stubTypeMarker instanceof ConeStubType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeStubType) stubTypeMarker).getConstructor().getVariable().getTypeConstructor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    default int typeDepth(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (kotlinTypeMarker instanceof ConeSimpleKotlinType) {
            return typeDepth((SimpleTypeMarker) kotlinTypeMarker);
        }
        if (kotlinTypeMarker instanceof ConeFlexibleType) {
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
            return Math.max(typeDepth(lowerBound(flexibleTypeMarker)), typeDepth(upperBound(flexibleTypeMarker)));
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Type should be simple or flexible: " + kotlinTypeMarker.getClass(), null);
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments2 = kotlinIllegalArgumentExceptionWithAttachments;
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, kotlinTypeMarker instanceof ConeKotlinType ? (ConeKotlinType) kotlinTypeMarker : null);
        kotlinIllegalArgumentExceptionWithAttachments2.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    default int typeDepth(SimpleTypeMarker simpleTypeMarker) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if ((simpleTypeMarker instanceof ConeClassLikeType) && simpleTypeMarker != (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) simpleTypeMarker, get$session(), null, 2, null))) {
            return typeDepth((SimpleTypeMarker) coneClassLikeTypeFullyExpandedType$default);
        }
        ConeTypeProjection[] typeArguments = ((ConeKotlinType) simpleTypeMarker).getTypeArguments();
        int length = typeArguments.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            int iTypeDepth = 1;
            if (i >= length) {
                return i2 + 1;
            }
            ConeTypeProjection coneTypeProjection = typeArguments[i];
            if (!(coneTypeProjection instanceof ConeStarProjection)) {
                Intrinsics.checkNotNull(coneTypeProjection, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection");
                iTypeDepth = typeDepth(((ConeKotlinTypeProjection) coneTypeProjection).getType());
            }
            if (iTypeDepth > i2) {
                i2 = iTypeDepth;
            }
            i++;
        }
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return containsInternal(kotlinTypeMarker, predicate);
    }

    private default boolean containsInternal(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> function1) {
        if (kotlinTypeMarker == null) {
            return false;
        }
        if (function1.invoke(kotlinTypeMarker).booleanValue()) {
            return true;
        }
        ConeFlexibleType coneFlexibleType = kotlinTypeMarker instanceof ConeFlexibleType ? (ConeFlexibleType) kotlinTypeMarker : null;
        if (coneFlexibleType != null && (containsInternal(coneFlexibleType.getLowerBound(), function1) || containsInternal(coneFlexibleType.getUpperBound(), function1))) {
            return true;
        }
        if ((kotlinTypeMarker instanceof ConeDefinitelyNotNullType) && containsInternal(((ConeDefinitelyNotNullType) kotlinTypeMarker).getOriginal(), function1)) {
            return true;
        }
        if (kotlinTypeMarker instanceof ConeIntersectionType) {
            Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) kotlinTypeMarker).getIntersectedTypes();
            if ((intersectedTypes instanceof Collection) && intersectedTypes.isEmpty()) {
                return false;
            }
            Iterator<T> it = intersectedTypes.iterator();
            while (it.hasNext()) {
                if (containsInternal((ConeKotlinType) it.next(), function1)) {
                    return true;
                }
            }
            return false;
        }
        int iArgumentsCount = argumentsCount(kotlinTypeMarker);
        for (int i = 0; i < iArgumentsCount; i++) {
            TypeArgumentMarker argument = getArgument(kotlinTypeMarker, i);
            if (!isStarProjection(argument) && containsInternal(getType(argument), function1)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return (typeConstructorMarker instanceof ConeClassLikeLookupTag) && Intrinsics.areEqual(((ConeClassLikeLookupTag) typeConstructorMarker).getClassId(), StandardClassIds.INSTANCE.getUnit());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection) {
        Object next;
        Intrinsics.checkNotNullParameter(collection, "<this>");
        if (collection.size() == 1) {
            return (KotlinTypeMarker) CollectionsKt.first(collection);
        }
        TypeCheckerState typeCheckerStateNewTypeCheckerState = newTypeCheckerState(true, true);
        Collection<? extends KotlinTypeMarker> collection2 = collection;
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            next = it.next();
            KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) next;
            if (!collection2.isEmpty()) {
                Iterator<T> it2 = collection2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        KotlinTypeMarker kotlinTypeMarker2 = (KotlinTypeMarker) it2.next();
                        if (Intrinsics.areEqual(kotlinTypeMarker, kotlinTypeMarker2) || AbstractTypeChecker.INSTANCE.equalTypes(typeCheckerStateNewTypeCheckerState, kotlinTypeMarker, kotlinTypeMarker2)) {
                        }
                    }
                }
            }
            return (KotlinTypeMarker) next;
        }
        next = null;
        return (KotlinTypeMarker) next;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isUnit(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (kotlinTypeMarker instanceof ConeKotlinType) {
            return isUnitTypeConstructor(typeConstructor(kotlinTypeMarker)) && !ConeTypeUtilsKt.isNullable((ConeKotlinType) kotlinTypeMarker);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return isTypeOrSubtypeOf((ConeKotlinType) kotlinTypeMarker, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.types.ConeInferenceContext.isBuiltinFunctionTypeOrSubtype.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Object objLowerBoundIfFlexible = ConeInferenceContext.this.lowerBoundIfFlexible(it);
                Intrinsics.checkNotNull(objLowerBoundIfFlexible, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                return Boolean.valueOf(FunctionalTypeUtilsKt.isSomeFunctionType((ConeKotlinType) objLowerBoundIfFlexible, ConeInferenceContext.this.get$session()));
            }
        });
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeUtilsKt.withNullability$default((ConeKotlinType) kotlinTypeMarker, ConeNullability.INSTANCE.create(z), this, null, false, 12, null);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeUtilsKt.makeConeTypeDefinitelyNotNullOrNotNull$default((ConeKotlinType) kotlinTypeMarker, this, false, 2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        TypeArgumentListMarker typeArgumentListMarkerMakeConeTypeDefinitelyNotNullOrNotNull$default = TypeUtilsKt.makeConeTypeDefinitelyNotNullOrNotNull$default((ConeKotlinType) simpleTypeMarker, this, false, 2, null);
        Intrinsics.checkNotNull(typeArgumentListMarkerMakeConeTypeDefinitelyNotNullOrNotNull$default, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.SimpleTypeMarker");
        return (SimpleTypeMarker) typeArgumentListMarkerMakeConeTypeDefinitelyNotNullOrNotNull$default;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default CapturedTypeMarker createCapturedType(TypeArgumentMarker constructorProjection, List<? extends KotlinTypeMarker> constructorSupertypes, KotlinTypeMarker lowerType, CaptureStatus captureStatus) {
        Intrinsics.checkNotNullParameter(constructorProjection, "constructorProjection");
        Intrinsics.checkNotNullParameter(constructorSupertypes, "constructorSupertypes");
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        if (!(lowerType == null ? true : lowerType instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(constructorProjection instanceof ConeTypeProjection)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return new ConeCapturedType(captureStatus, (ConeKotlinType) lowerType, null, new ConeCapturedTypeConstructor((ConeTypeProjection) constructorProjection, constructorSupertypes, null, 4, null), null, false, 52, null);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        if (!(typeVariable instanceof ConeTypeVariable)) {
            throw new IllegalArgumentException((typeVariable + " should subtype of " + Reflection.getOrCreateKotlinClass(ConeTypeVariable.class).getQualifiedName()).toString());
        }
        return new ConeStubTypeForChainInference((ConeTypeVariable) typeVariable, ConeNullability.INSTANCE.create(isMarkedNullable(defaultType(typeVariable))));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        if (!(typeVariable instanceof ConeTypeVariable)) {
            throw new IllegalArgumentException((typeVariable + " should subtype of " + Reflection.getOrCreateKotlinClass(ConeTypeVariable.class).getQualifiedName()).toString());
        }
        return new ConeStubTypeForTypeVariableInSubtyping((ConeTypeVariable) typeVariable, ConeNullability.INSTANCE.create(isMarkedNullable(defaultType(typeVariable))));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeUtilsKt.withAttributes((ConeKotlinType) kotlinTypeMarker, ConeAttributes.INSTANCE.getEmpty());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> newArguments) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (SimpleTypeMarker) TypeUtilsKt.withArguments((ConeKotlinType) simpleTypeMarker, (ConeTypeProjection[]) newArguments.toArray(new ConeTypeProjection[0]));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, final Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (SimpleTypeMarker) TypeUtilsKt.withArguments((ConeKotlinType) simpleTypeMarker, new Function1<ConeTypeProjection, ConeTypeProjection>() { // from class: org.jetbrains.kotlin.fir.types.ConeInferenceContext.replaceArguments.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeTypeProjection invoke(ConeTypeProjection it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TypeArgumentMarker typeArgumentMarkerInvoke = replacement.invoke(it);
                Intrinsics.checkNotNull(typeArgumentMarkerInvoke, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeTypeProjection");
                return (ConeTypeProjection) typeArgumentMarkerInvoke;
            }
        });
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (kotlinTypeMarker instanceof ConeKotlinType) {
            return CompilerConeAttributesKt.getExact(((ConeKotlinType) kotlinTypeMarker).getAttributes()) != null;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (kotlinTypeMarker instanceof ConeKotlinType) {
            return CompilerConeAttributesKt.getNoInfer(((ConeKotlinType) kotlinTypeMarker).getAttributes()) != null;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FirClassLikeSymbol<?> classLikeSymbol = toClassLikeSymbol(typeConstructorMarker);
        if (classLikeSymbol == null) {
            return false;
        }
        if (classLikeSymbol instanceof FirAnonymousObjectSymbol) {
            return true;
        }
        FirRegularClassSymbol firRegularClassSymbol = classLikeSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbol : null;
        return firRegularClassSymbol != null && firRegularClassSymbol.getResolvedStatus().getModality() == Modality.FINAL;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        if (!(typeVariableMarker instanceof ConeTypeVariable)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeTypeVariable) typeVariableMarker).getTypeConstructor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        if (!(capturedTypeMarker instanceof ConeCapturedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeCapturedType) capturedTypeMarker).getConstructor().getProjection();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        if (!(capturedTypeMarker instanceof ConeCapturedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeCapturedType) capturedTypeMarker).getConstructor().getTypeParameterMarker();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        if (!(capturedTypeMarker instanceof ConeCapturedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ConeCapturedType coneCapturedType = (ConeCapturedType) capturedTypeMarker;
        return new ConeCapturedType(coneCapturedType.getCaptureStatus(), coneCapturedType.getLowerType(), coneCapturedType.getNullability(), coneCapturedType.getConstructor(), coneCapturedType.getAttributes(), true);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        if (!(capturedTypeMarker instanceof ConeCapturedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeCapturedType) capturedTypeMarker).isProjectionNotNull();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        if (!(definitelyNotNullTypeMarker instanceof ConeDefinitelyNotNullType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeDefinitelyNotNullType) definitelyNotNullTypeMarker).getOriginal();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default ConeSubstitutor typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return SubstitutorsKt.createTypeSubstitutorByTypeConstructor(map, this, false);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default ConeSubstitutor createEmptySubstitutor() {
        return ConeSubstitutor.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        if (typeSubstitutorMarker == NoSubstitutor.INSTANCE) {
            return type;
        }
        if (!(typeSubstitutorMarker instanceof ConeSubstitutor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(type instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeSubstitutor) typeSubstitutorMarker).substituteOrSelf((ConeKotlinType) type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        if (!(typeVariableMarker instanceof ConeTypeVariable)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeTypeVariable) typeVariableMarker).getDefaultType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeTypeVariableTypeConstructor;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        if (!(typeVariableTypeConstructorMarker instanceof ConeTypeVariableTypeConstructor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeTypeVariableTypeConstructor) typeVariableTypeConstructorMarker).getIsContainedInInvariantOrContravariantPositions();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    default ConeErrorType createErrorType(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return new ConeErrorType(new ConeIntermediateDiagnostic(debugName), false, null, null, 14, null);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    default KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return new ConeErrorType(new ConeIntermediateDiagnostic("Uninferred type c: " + constructor), false, null, null, 14, null);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeTypeContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    default CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        if (!(capturedTypeMarker instanceof ConeCapturedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeCapturedType) capturedTypeMarker).getCaptureStatus();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeCapturedTypeConstructor;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Set<TypeParameterMarker> setExtractTypeParameters = extractTypeParameters(kotlinTypeMarker);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setExtractTypeParameters, 10));
        for (TypeParameterMarker typeParameterMarker : setExtractTypeParameters) {
            Intrinsics.checkNotNull(typeParameterMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag");
            arrayList.add(((ConeTypeParameterLookupTag) typeParameterMarker).getTypeParameterSymbol());
        }
        final Map<FirTypeParameterSymbol, ConeKotlinType> mapEraseToUpperBoundsAssociated = TypeUtilsKt.eraseToUpperBoundsAssociated(arrayList, get$session());
        final Lazy lazy = LazyKt.lazy(new Function0<ConeSubstitutorByMap>() { // from class: org.jetbrains.kotlin.fir.types.ConeInferenceContext$eraseContainingTypeParameters$substitutor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ConeSubstitutorByMap invoke() {
                return new ConeSubstitutorByMap(mapEraseToUpperBoundsAssociated, this.get$session());
            }
        });
        if (argumentsCount(kotlinTypeMarker) != 0) {
            return replaceArgumentsDeeply(kotlinTypeMarker, new Function1<TypeArgumentMarker, TypeArgumentMarker>() { // from class: org.jetbrains.kotlin.fir.types.ConeInferenceContext$eraseContainingTypeParameters$typeWithErasedTypeParameters$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final TypeArgumentMarker invoke(TypeArgumentMarker it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    KotlinTypeMarker type = this.this$0.getType(it);
                    ConeInferenceContext coneInferenceContext = this.this$0;
                    TypeParameterMarker typeParameterClassifier = coneInferenceContext.getTypeParameterClassifier(coneInferenceContext.typeConstructor(type));
                    ConeTypeParameterLookupTag coneTypeParameterLookupTag = typeParameterClassifier instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) typeParameterClassifier : null;
                    if ((coneTypeParameterLookupTag != null ? coneTypeParameterLookupTag.getTypeParameterSymbol() : null) == null) {
                        return it;
                    }
                    ConeInferenceContext coneInferenceContext2 = this.this$0;
                    return coneInferenceContext2.createTypeArgument(coneInferenceContext2.safeSubstitute(ConeInferenceContext.eraseContainingTypeParameters$lambda$9(lazy), type), TypeVariance.OUT);
                }
            });
        }
        return isTypeParameterTypeConstructor(typeConstructor(kotlinTypeMarker)) ? safeSubstitute(eraseContainingTypeParameters$lambda$9(lazy), kotlinTypeMarker) : kotlinTypeMarker;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static ConeSubstitutorByMap eraseContainingTypeParameters$lambda$9(Lazy<ConeSubstitutorByMap> lazy) {
        return lazy.getValue();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return getTypeParameterClassifier(typeConstructorMarker) != null;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ConeKotlinType coneKotlinType = (ConeKotlinType) kotlinTypeMarker;
        return TypeUtilsKt.withAttributes(coneKotlinType, coneKotlinType.getAttributes().remove(CompilerConeAttributes.Exact.INSTANCE));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    default SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (typeConstructorMarker instanceof ErrorTypeConstructor) {
            return createErrorType(((ErrorTypeConstructor) typeConstructorMarker).getReason());
        }
        return typeConstructorMarker instanceof ConeClassLikeLookupTag ? createErrorType("Not found classifier: " + ((ConeClassLikeLookupTag) typeConstructorMarker).getClassId()) : createErrorType("Unknown reason");
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    default SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes) {
        Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
        return ConeIntegerLiteralTypeImplKt.findCommonSuperType(ConeIntegerLiteralType.INSTANCE, explicitSupertypes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    default List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        List<? extends KotlinTypeMarker> list = types;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ConeKotlinType) it.next()).getAttributes());
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = ((ConeAttributes) next).union((ConeAttributes) it2.next());
        }
        return CollectionsKt.toList((Iterable) next);
    }

    private default boolean isCustomAttribute(AnnotationMarker annotationMarker) {
        Map<KClass<? extends ConeAttribute<?>>, ClassId> classIdByCompilerAttributeKey = CompilerConeAttributes.INSTANCE.getClassIdByCompilerAttributeKey();
        ConeAttribute coneAttribute = annotationMarker instanceof ConeAttribute ? (ConeAttribute) annotationMarker : null;
        return (classIdByCompilerAttributeKey.containsKey(coneAttribute != null ? coneAttribute.getKey() : null) || (annotationMarker instanceof CustomAnnotationTypeAttribute)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    default KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> newAttributes) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : newAttributes) {
            if (isCustomAttribute((ConeAttribute) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ConeKotlinType coneKotlinType = (ConeKotlinType) kotlinTypeMarker;
        ConeAttributes attributes = coneKotlinType.getAttributes();
        ArrayList arrayList3 = new ArrayList();
        for (ConeAttribute<?> coneAttribute : attributes) {
            if (!isCustomAttribute(coneAttribute)) {
                arrayList3.add(coneAttribute);
            }
        }
        return TypeUtilsKt.withAttributes(coneKotlinType, ConeAttributes.INSTANCE.create(CollectionsKt.plus((Collection) arrayList2, (Iterable) arrayList3)));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (!(typeConstructorMarker instanceof ConeIntegerLiteralType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ConeIntegerLiteralType.getApproximatedType$default((ConeIntegerLiteralType) typeConstructorMarker, null, 1, null);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (kotlinTypeMarker instanceof ConeIntegerLiteralType) {
            return true;
        }
        if (kotlinTypeMarker instanceof ConeClassLikeType) {
            return PrimitivesKt.isPrimitiveNumberOrUnsignedNumberType((ConeClassLikeType) kotlinTypeMarker);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return FunctionalTypeUtilsKt.isSomeFunctionType((ConeKotlinType) kotlinTypeMarker, get$session());
    }

    default boolean isTypeOrSubtypeOf(ConeKotlinType coneKotlinType, final Function1<? super ConeKotlinType, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if (!predicate.invoke(coneKotlinType).booleanValue()) {
            Object objDfsFromNode = DFS.dfsFromNode(coneKotlinType, new DFS.Neighbors() { // from class: org.jetbrains.kotlin.fir.types.ConeInferenceContext$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
                public final Iterable getNeighbors(Object obj) {
                    return ConeInferenceContext.isTypeOrSubtypeOf$lambda$14(this.f$0, (ConeKotlinType) obj);
                }
            }, new DFS.VisitedWithSet(), new DFS.AbstractNodeHandler<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.types.ConeInferenceContext.isTypeOrSubtypeOf.2
                private boolean result;

                @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
                public boolean beforeChildren(ConeKotlinType current) {
                    Intrinsics.checkNotNullParameter(current, "current");
                    if (predicate.invoke(current).booleanValue()) {
                        this.result = true;
                    }
                    return !this.result;
                }

                @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
                public Boolean result() {
                    return Boolean.valueOf(this.result);
                }
            });
            Intrinsics.checkNotNullExpressionValue(objDfsFromNode, "dfsFromNode(...)");
            if (!((Boolean) objDfsFromNode).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Iterable isTypeOrSubtypeOf$lambda$14(ConeInferenceContext this$0, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(coneKotlinType);
        return this$0.supertypes(this$0.typeConstructor(coneKotlinType));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Object objLowerBoundIfFlexible = lowerBoundIfFlexible(kotlinTypeMarker);
        ConeKotlinType coneKotlinType = objLowerBoundIfFlexible instanceof ConeKotlinType ? (ConeKotlinType) objLowerBoundIfFlexible : null;
        return coneKotlinType != null && TypeUtilsKt.isExtensionFunctionType(coneKotlinType, get$session());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        KotlinTypeMarker functionTypeFromSupertypes = getFunctionTypeFromSupertypes(kotlinTypeMarker);
        Intrinsics.checkNotNull(functionTypeFromSupertypes, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        ConeKotlinType coneKotlinType = (ConeKotlinType) functionTypeFromSupertypes;
        int iArgumentsCount = argumentsCount(coneKotlinType) - 1;
        for (int i = 0; i < iArgumentsCount; i++) {
            listCreateListBuilder.add(getType(getArgument(coneKotlinType, i)));
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        isBuiltinFunctionTypeOrSubtype(kotlinTypeMarker);
        final SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = lowerBoundIfFlexible(TypeExpansionUtilsKt.fullyExpandedType((ConeKotlinType) kotlinTypeMarker, get$session()));
        Intrinsics.checkNotNull(simpleTypeMarkerLowerBoundIfFlexible, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        ConeKotlinType coneKotlinType = (ConeKotlinType) simpleTypeMarkerLowerBoundIfFlexible;
        if (!FunctionalTypeUtilsKt.isSomeFunctionType(coneKotlinType, get$session())) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            anySuperTypeConstructor(simpleTypeMarkerLowerBoundIfFlexible, new Function1<SimpleTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.fir.types.ConeInferenceContext$getFunctionTypeFromSupertypes$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Type inference failed for: r3v3, types: [T, java.lang.Object, org.jetbrains.kotlin.types.model.SimpleTypeMarker] */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(SimpleTypeMarker type) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    ConeInferenceContext coneInferenceContext = this.this$0;
                    List<SimpleTypeMarker> listFastCorrespondingSupertypes = coneInferenceContext.fastCorrespondingSupertypes(simpleTypeMarkerLowerBoundIfFlexible, coneInferenceContext.typeConstructor(type));
                    boolean z = false;
                    if (listFastCorrespondingSupertypes != null) {
                        List<SimpleTypeMarker> list = listFastCorrespondingSupertypes;
                        ConeInferenceContext coneInferenceContext2 = this.this$0;
                        Ref.ObjectRef<KotlinTypeMarker> objectRef2 = objectRef;
                        if (!(list instanceof Collection) || !list.isEmpty()) {
                            Iterator<T> it = list.iterator();
                            while (it.hasNext()) {
                                ?? r3 = (SimpleTypeMarker) it.next();
                                Intrinsics.checkNotNull(r3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                                boolean zIsSomeFunctionType = FunctionalTypeUtilsKt.isSomeFunctionType((ConeKotlinType) r3, coneInferenceContext2.get$session());
                                if (zIsSomeFunctionType) {
                                    objectRef2.element = r3;
                                }
                                if (zIsSomeFunctionType) {
                                    z = true;
                                    break;
                                }
                            }
                        }
                    }
                    return Boolean.valueOf(z);
                }
            });
            kotlinTypeMarker = (KotlinTypeMarker) objectRef.element;
            if (kotlinTypeMarker == null) {
                KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Failed to find functional supertype for " + simpleTypeMarkerLowerBoundIfFlexible.getClass(), null);
                ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, coneKotlinType);
                kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                throw kotlinIllegalArgumentExceptionWithAttachments;
            }
        }
        return kotlinTypeMarker;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return FunctionalTypeUtilsKt.functionTypeKind((ConeKotlinType) kotlinTypeMarker, get$session());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default TypeConstructorMarker getNonReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return TypeConstructionUtilsKt.toLookupTag(kind.nonReflectKind().numberedClassId(parametersNumber));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default TypeConstructorMarker getReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return TypeConstructionUtilsKt.toLookupTag(kind.reflectKind().numberedClassId(parametersNumber));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(final KotlinTypeMarker firstCandidate, KotlinTypeMarker secondCandidate) {
        Intrinsics.checkNotNullParameter(firstCandidate, "firstCandidate");
        Intrinsics.checkNotNullParameter(secondCandidate, "secondCandidate");
        if (!(firstCandidate instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(secondCandidate instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = lowerBoundIfFlexible(firstCandidate);
        ConeIntersectionType coneIntersectionType = simpleTypeMarkerLowerBoundIfFlexible instanceof ConeIntersectionType ? (ConeIntersectionType) simpleTypeMarkerLowerBoundIfFlexible : null;
        if (coneIntersectionType == null) {
            throw new IllegalStateException(new Function0<String>() { // from class: org.jetbrains.kotlin.fir.types.ConeInferenceContext$createTypeWithAlternativeForIntersectionResult$intersectionType$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Expected type is intersection, found " + firstCandidate;
                }
            }.toString());
        }
        return ConeTypeUtilsKt.withAlternative(coneIntersectionType, (ConeKotlinType) secondCandidate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeUtilsKt.convertToNonRawVersion((ConeKotlinType) kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    default TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker baseType) {
        Intrinsics.checkNotNullParameter(baseType, "baseType");
        if (baseType instanceof ConeLookupTagBasedType) {
            return SupertypeUtilsKt.createSubstitutionForSupertype((ConeLookupTagBasedType) baseType, get$session());
        }
        return null;
    }
}
