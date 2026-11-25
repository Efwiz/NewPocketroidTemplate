package org.jetbrains.kotlin.types.model;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeChecker;
import org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeInfo;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u001e\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\r\u001a\u0004\u0018\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016J \u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017H\u0016J0\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00110\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u001d\u001a\u00020\u001eH&J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0014\u001a\u00020!H&J\u0010\u0010\"\u001a\u00020 2\u0006\u0010\u0014\u001a\u00020!H&J\u0012\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020\u0011H&J\u0018\u0010&\u001a\u00020\u00112\u0006\u0010'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u0011H&J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H&J\u0018\u0010/\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H&J\b\u00100\u001a\u00020\u0005H&J \u00101\u001a\u00020\u0005*\u00020\u00112\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000503H&J\f\u00104\u001a\u00020\u0011*\u00020\u0011H&J\f\u00105\u001a\u00020\u0013*\u00020!H&J\f\u00106\u001a\u00020\u0011*\u00020\u0011H&J\u0012\u00107\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017*\u00020\u0011H&J6\u00108\u001a\u000209\"\u0004\b\u0000\u0010:*\u00020\u00112\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H:0<2\u0014\u0010=\u001a\u0010\u0012\u0004\u0012\u00020*\u0012\u0006\u0012\u0004\u0018\u0001H:03H\u0002J\u0012\u0010>\u001a\b\u0012\u0004\u0012\u00020\n0?*\u00020\u0011H\u0016J\u0012\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00150?*\u00020\u0011H\u0016J\f\u0010A\u001a\u00020**\u00020!H&J\u000e\u0010B\u001a\u0004\u0018\u00010.*\u00020\u0011H&J\f\u0010C\u001a\u00020\u0011*\u00020*H&J\f\u0010D\u001a\u00020\u0011*\u00020\u0011H&J\f\u0010E\u001a\u00020\u0015*\u00020 H&J\f\u0010F\u001a\u00020\u0005*\u00020\u0011H&J\f\u0010G\u001a\u00020\u0005*\u00020\u0011H&J\f\u0010H\u001a\u00020\u0005*\u00020\u0011H&J\f\u0010I\u001a\u00020\u0005*\u00020*H&J\f\u0010J\u001a\u00020\u0005*\u00020\u0015H&J\f\u0010K\u001a\u00020\u0005*\u00020\u0011H&J\f\u0010L\u001a\u00020\u0005*\u00020*H&J\f\u0010M\u001a\u00020\u0005*\u00020\u0011H&J\f\u0010N\u001a\u00020\u0005*\u00020\u0011H&J\f\u0010O\u001a\u00020\u0005*\u00020\u0011H&J\f\u0010P\u001a\u00020\u0005*\u00020*H&J\f\u0010Q\u001a\u00020\u0005*\u00020\u0011H&J\f\u0010R\u001a\u00020\u0005*\u00020*H&J\f\u0010S\u001a\u00020\u0011*\u00020\u0011H&J\f\u0010T\u001a\u00020\u0011*\u00020\u0011H&J \u0010U\u001a\u00020\u0011*\u00020\u00112\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f03H\u0016J \u0010U\u001a\u00020\u0013*\u00020\u00132\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f03H&J\u001a\u0010U\u001a\u00020\u0013*\u00020\u00132\f\u0010W\u001a\b\u0012\u0004\u0012\u00020\f0\u0017H&J \u0010X\u001a\u00020\u0011*\u00020\u00112\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f03H\u0016J \u0010X\u001a\u00020\u0013*\u00020\u00132\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f03H\u0016J\u0014\u0010Y\u001a\u0004\u0018\u00010\u0011*\b\u0012\u0004\u0012\u00020\u00110\u0010H&J\f\u0010Z\u001a\u00020\f*\u00020\u0019H&J\u000e\u0010[\u001a\u0004\u0018\u00010\n*\u00020\u0019H&J\f\u0010\\\u001a\u00020\u0011*\u00020\u0019H&R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006]À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemBuiltInsContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemCommonSuperTypesContext;", "isK2", "", "()Z", "computeEffectiveVariance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "parameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "argument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "computeEmptyIntersectionTypeKind", "Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeInfo;", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "createCapturedStarProjectionForSelfType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "typesForRecursiveTypeParameters", "", "createCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "constructorProjection", "constructorSupertypes", "lowerType", "captureStatus", "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "createStubTypeForBuilderInference", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "createStubTypeForTypeVariablesInSubtyping", "createSubstitutorForSuperTypes", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "baseType", "createTypeWithAlternativeForIntersectionResult", "firstCandidate", "secondCandidate", "getNonReflectFunctionTypeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "parametersNumber", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getReflectFunctionTypeConstructor", "useRefinedBoundsForTypeVariableInFlexiblePosition", "contains", "predicate", "Lkotlin/Function1;", "convertToNonRaw", "defaultType", "eraseContainingTypeParameters", "extractArgumentsForFunctionTypeOrSubtype", "extractTypeOf", "", ExifInterface.GPS_DIRECTION_TRUE, "to", "", "getIfApplicable", "extractTypeParameters", "", "extractTypeVariables", "freshTypeConstructor", "functionTypeKind", "getApproximatedIntegerLiteralType", "getFunctionTypeFromSupertypes", "getOriginalTypeVariable", "hasExactAnnotation", "hasNoInferAnnotation", "isBuiltinFunctionTypeOrSubtype", "isCapturedTypeConstructor", "isContainedInInvariantOrContravariantPositions", "isExtensionFunctionType", "isFinalClassConstructor", "isFunctionOrKFunctionWithAnySuspendability", "isSignedOrUnsignedNumberType", "isSpecial", "isTypeVariable", "isUnit", "isUnitTypeConstructor", "removeAnnotations", "removeExactAnnotation", "replaceArguments", "replacement", "newArguments", "replaceArgumentsDeeply", "singleBestRepresentative", "typeConstructorProjection", "typeParameter", "withNotNullProjection", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeSystemInferenceExtensionContext extends TypeSystemBuiltInsContext, TypeSystemCommonSuperTypesContext, TypeSystemContext {
    boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> function1);

    KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker);

    CapturedTypeMarker createCapturedType(TypeArgumentMarker constructorProjection, List<? extends KotlinTypeMarker> constructorSupertypes, KotlinTypeMarker lowerType, CaptureStatus captureStatus);

    StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariable);

    StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariable);

    TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker baseType);

    KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(KotlinTypeMarker firstCandidate, KotlinTypeMarker secondCandidate);

    SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker);

    KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker);

    List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker);

    TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker);

    FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker);

    KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker);

    TypeConstructorMarker getNonReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind);

    TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker);

    TypeConstructorMarker getReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind);

    boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker);

    boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker);

    boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker);

    boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker);

    boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker);

    boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker);

    boolean isK2();

    boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker);

    boolean isSpecial(KotlinTypeMarker kotlinTypeMarker);

    boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker);

    boolean isUnit(KotlinTypeMarker kotlinTypeMarker);

    boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> list);

    SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> function1);

    KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection);

    TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker);

    TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker);

    boolean useRefinedBoundsForTypeVariableInFlexiblePosition();

    KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker);

    default KotlinTypeMarker replaceArguments(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        if (kotlinTypeMarker instanceof SimpleTypeMarker) {
            return replaceArguments((SimpleTypeMarker) kotlinTypeMarker, replacement);
        }
        if (kotlinTypeMarker instanceof FlexibleTypeMarker) {
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
            return createFlexibleType(replaceArguments(lowerBound(flexibleTypeMarker), replacement), replaceArguments(upperBound(flexibleTypeMarker), replacement));
        }
        throw new IllegalStateException("sealed".toString());
    }

    default SimpleTypeMarker replaceArgumentsDeeply(SimpleTypeMarker simpleTypeMarker, final Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return replaceArguments(simpleTypeMarker, (Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker>) new Function1<TypeArgumentMarker, TypeArgumentMarker>() { // from class: org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext.replaceArgumentsDeeply.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final TypeArgumentMarker invoke(TypeArgumentMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (TypeSystemInferenceExtensionContext.this.isStarProjection(it)) {
                    return it;
                }
                KotlinTypeMarker type = TypeSystemInferenceExtensionContext.this.getType(it);
                if (TypeSystemInferenceExtensionContext.this.argumentsCount(type) > 0) {
                    TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = TypeSystemInferenceExtensionContext.this;
                    it = typeSystemInferenceExtensionContext.replaceType(it, typeSystemInferenceExtensionContext.replaceArgumentsDeeply(type, replacement));
                }
                return replacement.invoke(it);
            }
        });
    }

    default KotlinTypeMarker replaceArgumentsDeeply(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        if (kotlinTypeMarker instanceof SimpleTypeMarker) {
            return replaceArgumentsDeeply((SimpleTypeMarker) kotlinTypeMarker, replacement);
        }
        if (kotlinTypeMarker instanceof FlexibleTypeMarker) {
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
            return createFlexibleType(replaceArgumentsDeeply(lowerBound(flexibleTypeMarker), replacement), replaceArgumentsDeeply(upperBound(flexibleTypeMarker), replacement));
        }
        throw new IllegalStateException("sealed".toString());
    }

    private default <T> void extractTypeOf(KotlinTypeMarker kotlinTypeMarker, Set<T> set, Function1<? super TypeConstructorMarker, ? extends T> function1) {
        int iArgumentsCount = argumentsCount(kotlinTypeMarker);
        for (int i = 0; i < iArgumentsCount; i++) {
            TypeArgumentMarker argument = getArgument(kotlinTypeMarker, i);
            if (!isStarProjection(argument)) {
                KotlinTypeMarker type = getType(argument);
                T tInvoke = function1.invoke(typeConstructor(type));
                if (tInvoke != null) {
                    set.add(tInvoke);
                } else if (argumentsCount(type) != 0) {
                    extractTypeOf(type, set, function1);
                }
            }
        }
    }

    default Set<TypeVariableTypeConstructorMarker> extractTypeVariables(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        extractTypeOf(kotlinTypeMarker, setCreateSetBuilder, new Function1<TypeConstructorMarker, TypeVariableTypeConstructorMarker>() { // from class: org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext$extractTypeVariables$1$1
            @Override // kotlin.jvm.functions.Function1
            public final TypeVariableTypeConstructorMarker invoke(TypeConstructorMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof TypeVariableTypeConstructorMarker) {
                    return (TypeVariableTypeConstructorMarker) it;
                }
                return null;
            }
        });
        return SetsKt.build(setCreateSetBuilder);
    }

    default Set<TypeParameterMarker> extractTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        TypeParameterMarker typeParameterClassifier = getTypeParameterClassifier(typeConstructor(kotlinTypeMarker));
        if (typeParameterClassifier != null) {
            setCreateSetBuilder.add(typeParameterClassifier);
        }
        extractTypeOf(kotlinTypeMarker, setCreateSetBuilder, new Function1<TypeConstructorMarker, TypeParameterMarker>() { // from class: org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext$extractTypeParameters$1$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final TypeParameterMarker invoke(TypeConstructorMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getTypeParameterClassifier(it);
            }
        });
        return SetsKt.build(setCreateSetBuilder);
    }

    default SimpleTypeMarker createCapturedStarProjectionForSelfType(final TypeVariableTypeConstructorMarker typeVariable, List<? extends KotlinTypeMarker> typesForRecursiveTypeParameters) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(typesForRecursiveTypeParameters, "typesForRecursiveTypeParameters");
        TypeParameterMarker typeParameter = getTypeParameter(typeVariable);
        if (typeParameter == null) {
            return null;
        }
        final TypeArgumentMarker typeArgumentMarkerCreateStarProjection = createStarProjection(typeParameter);
        List<? extends KotlinTypeMarker> list = typesForRecursiveTypeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(replaceArgumentsDeeply((KotlinTypeMarker) it.next(), new Function1<TypeArgumentMarker, TypeArgumentMarker>() { // from class: org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext$createCapturedStarProjectionForSelfType$superType$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final TypeArgumentMarker invoke(TypeArgumentMarker it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.this$0;
                    TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemInferenceExtensionContext.typeConstructor(typeSystemInferenceExtensionContext.getType(it2));
                    return ((typeConstructorMarkerTypeConstructor instanceof TypeVariableTypeConstructorMarker) && Intrinsics.areEqual(typeConstructorMarkerTypeConstructor, typeVariable)) ? typeArgumentMarkerCreateStarProjection : it2;
                }
            }));
        }
        return createCapturedType(typeArgumentMarkerCreateStarProjection, CollectionsKt.listOf(intersectTypes((List<? extends KotlinTypeMarker>) arrayList)), null, CaptureStatus.FROM_EXPRESSION);
    }

    default EmptyIntersectionTypeInfo computeEmptyIntersectionTypeKind(Collection<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return EmptyIntersectionTypeChecker.INSTANCE.computeEmptyIntersectionEmptiness(this, types);
    }
}
