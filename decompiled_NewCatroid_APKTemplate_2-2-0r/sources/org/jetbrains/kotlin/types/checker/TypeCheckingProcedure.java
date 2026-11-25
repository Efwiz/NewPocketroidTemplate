package org.jetbrains.kotlin.types.checker;

import java.util.List;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.EnrichedProjectionKind;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeCapabilitiesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: loaded from: classes6.dex */
public class TypeCheckingProcedure {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final TypeCheckingProcedureCallbacks constraints;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 18:
            case 20:
                objArr[0] = "supertype";
                break;
            case 2:
            case 17:
            case 19:
            default:
                objArr[0] = "subtype";
                break;
            case 4:
                objArr[0] = "typeCheckingProcedureCallbacks";
                break;
            case 5:
            case 8:
            case 23:
                objArr[0] = "parameter";
                break;
            case 6:
            case 9:
                objArr[0] = "argument";
                break;
            case 7:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/types/checker/TypeCheckingProcedure";
                break;
            case 11:
                objArr[0] = "type1";
                break;
            case 12:
                objArr[0] = "type2";
                break;
            case 13:
                objArr[0] = "typeParameter";
                break;
            case 14:
                objArr[0] = "typeArgument";
                break;
            case 15:
                objArr[0] = "typeParameterVariance";
                break;
            case 16:
                objArr[0] = "typeArgumentVariance";
                break;
            case 21:
                objArr[0] = "subtypeArgumentProjection";
                break;
            case 22:
                objArr[0] = "supertypeArgumentProjection";
                break;
        }
        if (i == 7) {
            objArr[1] = "getOutType";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/types/checker/TypeCheckingProcedure";
        } else {
            objArr[1] = "getInType";
        }
        switch (i) {
            case 5:
            case 6:
                objArr[2] = "getOutType";
                break;
            case 7:
            case 10:
                break;
            case 8:
            case 9:
                objArr[2] = "getInType";
                break;
            case 11:
            case 12:
                objArr[2] = "equalTypes";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "getEffectiveProjectionKind";
                break;
            case 17:
            case 18:
                objArr[2] = "isSubtypeOf";
                break;
            case 19:
            case 20:
                objArr[2] = "checkSubtypeForTheSameConstructor";
                break;
            case 21:
            case 22:
            case 23:
                objArr[2] = "capture";
                break;
            default:
                objArr[2] = "findCorrespondingSupertype";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static KotlinType findCorrespondingSupertype(KotlinType kotlinType, KotlinType kotlinType2) {
        if (kotlinType == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(1);
        }
        return findCorrespondingSupertype(kotlinType, kotlinType2, new TypeCheckerProcedureCallbacksImpl());
    }

    public static KotlinType findCorrespondingSupertype(KotlinType kotlinType, KotlinType kotlinType2, TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks) {
        if (kotlinType == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(3);
        }
        if (typeCheckingProcedureCallbacks == null) {
            $$$reportNull$$$0(4);
        }
        return UtilsKt.findCorrespondingSupertype(kotlinType, kotlinType2, typeCheckingProcedureCallbacks);
    }

    private static KotlinType getOutType(TypeParameterDescriptor typeParameterDescriptor, TypeProjection typeProjection) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (typeProjection == null) {
            $$$reportNull$$$0(6);
        }
        KotlinType nullableAnyType = (typeProjection.getProjectionKind() == Variance.IN_VARIANCE || typeParameterDescriptor.getVariance() == Variance.IN_VARIANCE) ? DescriptorUtilsKt.getBuiltIns(typeParameterDescriptor).getNullableAnyType() : typeProjection.getType();
        if (nullableAnyType == null) {
            $$$reportNull$$$0(7);
        }
        return nullableAnyType;
    }

    private static KotlinType getInType(TypeParameterDescriptor typeParameterDescriptor, TypeProjection typeProjection) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (typeProjection == null) {
            $$$reportNull$$$0(9);
        }
        KotlinType nothingType = (typeProjection.getProjectionKind() == Variance.OUT_VARIANCE || typeParameterDescriptor.getVariance() == Variance.OUT_VARIANCE) ? DescriptorUtilsKt.getBuiltIns(typeParameterDescriptor).getNothingType() : typeProjection.getType();
        if (nothingType == null) {
            $$$reportNull$$$0(10);
        }
        return nothingType;
    }

    public TypeCheckingProcedure(TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks) {
        this.constraints = typeCheckingProcedureCallbacks;
    }

    public boolean equalTypes(KotlinType kotlinType, KotlinType kotlinType2) {
        if (kotlinType == null) {
            $$$reportNull$$$0(11);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(12);
        }
        if (kotlinType == kotlinType2) {
            return true;
        }
        if (FlexibleTypesKt.isFlexible(kotlinType)) {
            if (FlexibleTypesKt.isFlexible(kotlinType2)) {
                return !KotlinTypeKt.isError(kotlinType) && !KotlinTypeKt.isError(kotlinType2) && isSubtypeOf(kotlinType, kotlinType2) && isSubtypeOf(kotlinType2, kotlinType);
            }
            return heterogeneousEquivalence(kotlinType2, kotlinType);
        }
        if (FlexibleTypesKt.isFlexible(kotlinType2)) {
            return heterogeneousEquivalence(kotlinType, kotlinType2);
        }
        if (kotlinType.getIsMarkedNullable() != kotlinType2.getIsMarkedNullable()) {
            return false;
        }
        if (kotlinType.getIsMarkedNullable()) {
            return this.constraints.assertEqualTypes(TypeUtils.makeNotNullable(kotlinType), TypeUtils.makeNotNullable(kotlinType2), this);
        }
        TypeConstructor constructor = kotlinType.getConstructor();
        TypeConstructor constructor2 = kotlinType2.getConstructor();
        if (!this.constraints.assertEqualTypeConstructors(constructor, constructor2)) {
            return false;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        List<TypeProjection> arguments2 = kotlinType2.getArguments();
        if (arguments.size() != arguments2.size()) {
            return false;
        }
        for (int i = 0; i < arguments.size(); i++) {
            TypeProjection typeProjection = arguments.get(i);
            TypeProjection typeProjection2 = arguments2.get(i);
            if (!typeProjection.isStarProjection() || !typeProjection2.isStarProjection()) {
                TypeParameterDescriptor typeParameterDescriptor = constructor.getParameters().get(i);
                TypeParameterDescriptor typeParameterDescriptor2 = constructor2.getParameters().get(i);
                if (!capture(typeProjection, typeProjection2, typeParameterDescriptor) && (getEffectiveProjectionKind(typeParameterDescriptor, typeProjection) != getEffectiveProjectionKind(typeParameterDescriptor2, typeProjection2) || !this.constraints.assertEqualTypes(typeProjection.getType(), typeProjection2.getType(), this))) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean heterogeneousEquivalence(KotlinType kotlinType, KotlinType kotlinType2) {
        return isSubtypeOf(FlexibleTypesKt.asFlexibleType(kotlinType2).getLowerBound(), kotlinType) && isSubtypeOf(kotlinType, FlexibleTypesKt.asFlexibleType(kotlinType2).getUpperBound());
    }

    public static EnrichedProjectionKind getEffectiveProjectionKind(TypeParameterDescriptor typeParameterDescriptor, TypeProjection typeProjection) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        if (typeProjection == null) {
            $$$reportNull$$$0(14);
        }
        return getEffectiveProjectionKind(typeParameterDescriptor.getVariance(), typeProjection.getProjectionKind());
    }

    public static EnrichedProjectionKind getEffectiveProjectionKind(Variance variance, Variance variance2) {
        if (variance == null) {
            $$$reportNull$$$0(15);
        }
        if (variance2 == null) {
            $$$reportNull$$$0(16);
        }
        return EnrichedProjectionKind.INSTANCE.getEffectiveProjectionKind(variance, variance2);
    }

    public boolean isSubtypeOf(KotlinType kotlinType, KotlinType kotlinType2) {
        if (kotlinType == null) {
            $$$reportNull$$$0(17);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(18);
        }
        if (TypeCapabilitiesKt.sameTypeConstructors(kotlinType, kotlinType2)) {
            return !kotlinType.getIsMarkedNullable() || kotlinType2.getIsMarkedNullable();
        }
        KotlinType subtypeRepresentative = TypeCapabilitiesKt.getSubtypeRepresentative(kotlinType);
        KotlinType supertypeRepresentative = TypeCapabilitiesKt.getSupertypeRepresentative(kotlinType2);
        if (subtypeRepresentative != kotlinType || supertypeRepresentative != kotlinType2) {
            return isSubtypeOf(subtypeRepresentative, supertypeRepresentative);
        }
        return isSubtypeOfForRepresentatives(kotlinType, kotlinType2);
    }

    private boolean isSubtypeOfForRepresentatives(KotlinType kotlinType, KotlinType kotlinType2) {
        if (KotlinTypeKt.isError(kotlinType) || KotlinTypeKt.isError(kotlinType2)) {
            return true;
        }
        if (!kotlinType2.getIsMarkedNullable() && kotlinType.getIsMarkedNullable()) {
            return false;
        }
        if (KotlinBuiltIns.isNothingOrNullableNothing(kotlinType)) {
            return true;
        }
        KotlinType kotlinTypeFindCorrespondingSupertype = findCorrespondingSupertype(kotlinType, kotlinType2, this.constraints);
        if (kotlinTypeFindCorrespondingSupertype == null) {
            return this.constraints.noCorrespondingSupertype(kotlinType, kotlinType2);
        }
        if (kotlinType2.getIsMarkedNullable() || !kotlinTypeFindCorrespondingSupertype.getIsMarkedNullable()) {
            return checkSubtypeForTheSameConstructor(kotlinTypeFindCorrespondingSupertype, kotlinType2);
        }
        return false;
    }

    private boolean checkSubtypeForTheSameConstructor(KotlinType kotlinType, KotlinType kotlinType2) {
        if (kotlinType == null) {
            $$$reportNull$$$0(19);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(20);
        }
        TypeConstructor constructor = kotlinType.getConstructor();
        List<TypeProjection> arguments = kotlinType.getArguments();
        List<TypeProjection> arguments2 = kotlinType2.getArguments();
        if (arguments.size() != arguments2.size()) {
            return false;
        }
        List<TypeParameterDescriptor> parameters = constructor.getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            TypeParameterDescriptor typeParameterDescriptor = parameters.get(i);
            TypeProjection typeProjection = arguments2.get(i);
            TypeProjection typeProjection2 = arguments.get(i);
            if (!typeProjection.isStarProjection() && !capture(typeProjection2, typeProjection, typeParameterDescriptor)) {
                if (!KotlinTypeKt.isError(typeProjection2.getType()) && !KotlinTypeKt.isError(typeProjection.getType()) && typeParameterDescriptor.getVariance() == Variance.INVARIANT && typeProjection2.getProjectionKind() == Variance.INVARIANT && typeProjection.getProjectionKind() == Variance.INVARIANT) {
                    if (!this.constraints.assertEqualTypes(typeProjection2.getType(), typeProjection.getType(), this)) {
                        return false;
                    }
                } else {
                    if (!this.constraints.assertSubtype(getOutType(typeParameterDescriptor, typeProjection2), getOutType(typeParameterDescriptor, typeProjection), this)) {
                        return false;
                    }
                    KotlinType inType = getInType(typeParameterDescriptor, typeProjection);
                    KotlinType inType2 = getInType(typeParameterDescriptor, typeProjection2);
                    if (typeProjection.getProjectionKind() != Variance.OUT_VARIANCE && !this.constraints.assertSubtype(inType, inType2, this)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean capture(TypeProjection typeProjection, TypeProjection typeProjection2, TypeParameterDescriptor typeParameterDescriptor) {
        if (typeProjection == null) {
            $$$reportNull$$$0(21);
        }
        if (typeProjection2 == null) {
            $$$reportNull$$$0(22);
        }
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(23);
        }
        if (typeParameterDescriptor.getVariance() == Variance.INVARIANT && typeProjection.getProjectionKind() != Variance.INVARIANT && typeProjection2.getProjectionKind() == Variance.INVARIANT) {
            return this.constraints.capture(typeProjection2.getType(), typeProjection);
        }
        return false;
    }
}
