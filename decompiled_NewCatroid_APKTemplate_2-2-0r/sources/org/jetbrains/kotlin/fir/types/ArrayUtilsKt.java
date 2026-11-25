package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: ArrayUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\f\u0010\u0004\u001a\u0004\u0018\u00010\u0002*\u00020\u0002\u001a\u001e\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\u0001\u001a\u001e\u0010\n\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\u0001\u001a\f\u0010\f\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\n\u0010\r\u001a\u00020\u0002*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u000e"}, d2 = {"isArrayOrPrimitiveArray", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "arrayElementType", "createArrayType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "nullable", "createPrimitiveArrayTypeIfPossible", "createOutArrayType", "createPrimitiveArrayType", "isPotentiallyArray", "varargElementType", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArrayUtilsKt {
    public static final boolean isArrayOrPrimitiveArray(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return arrayElementType(coneKotlinType) != null;
    }

    public static /* synthetic */ ConeKotlinType createOutArrayType$default(ConeKotlinType coneKotlinType, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return createOutArrayType(coneKotlinType, z, z2);
    }

    public static final ConeKotlinType createOutArrayType(ConeKotlinType coneKotlinType, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return createArrayType(new ConeKotlinTypeProjectionOut(coneKotlinType), z, z2);
    }

    public static /* synthetic */ ConeClassLikeType createArrayType$default(ConeTypeProjection coneTypeProjection, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return createArrayType(coneTypeProjection, z, z2);
    }

    public static final ConeClassLikeType createArrayType(ConeTypeProjection coneTypeProjection, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(coneTypeProjection, "<this>");
        if ((coneTypeProjection instanceof ConeKotlinTypeProjection) && z2) {
            ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(((ConeKotlinTypeProjection) coneTypeProjection).getType());
            if ((coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType) && coneSimpleKotlinTypeLowerBoundIfFlexible.getNullability() != ConeNullability.NULLABLE) {
                ClassId classId = ((ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible).getLookupTag().getClassId();
                ClassId classId2 = StandardClassIds.INSTANCE.getPrimitiveArrayTypeByElementType().get(classId);
                if (classId2 == null) {
                    classId2 = StandardClassIds.INSTANCE.getUnsignedArrayTypeByElementType().get(classId);
                }
                ClassId classId3 = classId2;
                if (classId3 != null) {
                    return TypeConstructionUtilsKt.constructClassLikeType$default(classId3, new ConeTypeProjection[0], z, null, 4, null);
                }
            }
        }
        return TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getArray(), new ConeTypeProjection[]{coneTypeProjection}, z, null, 4, null);
    }

    public static final ConeKotlinType arrayElementType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType);
        if (!(coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType)) {
            return null;
        }
        ClassId classId = ((ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible).getLookupTag().getClassId();
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getArray())) {
            Object objFirst = ArraysKt.first(coneSimpleKotlinTypeLowerBoundIfFlexible.getTypeArguments());
            Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection");
            return ((ConeKotlinTypeProjection) objFirst).getType();
        }
        ClassId classId2 = StandardClassIds.INSTANCE.getElementTypeByPrimitiveArrayType().get(classId);
        if (classId2 == null) {
            classId2 = StandardClassIds.INSTANCE.getElementTypeByUnsignedArrayType().get(classId);
        }
        ClassId classId3 = classId2;
        if (classId3 != null) {
            return TypeConstructionUtilsKt.constructClassLikeType$default(classId3, new ConeTypeProjection[0], false, null, 4, null);
        }
        return null;
    }

    public static final ConeKotlinType varargElementType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        ConeKotlinType coneKotlinTypeArrayElementType = arrayElementType(coneKotlinType);
        return coneKotlinTypeArrayElementType == null ? coneKotlinType : coneKotlinTypeArrayElementType;
    }

    public static final boolean isPotentiallyArray(ConeKotlinType coneKotlinType) {
        return coneKotlinType != null && (arrayElementType(coneKotlinType) != null || (coneKotlinType instanceof ConeTypeVariableType));
    }
}
