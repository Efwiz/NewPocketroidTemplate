package org.jetbrains.kotlin.fir.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: ConeBuiltinTypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001a\u0010%\u001a\u00020\u0001*\u00020\u00022\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0002\u001a#\u0010)\u001a\u00020\u0001*\u00020\u00022\u0006\u0010*\u001a\u00020(2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u0002¢\u0006\u0002\u0010,\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003\"\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0003\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0003\"\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0003\"\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0003\"\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0003\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0003\"\u0015\u0010\f\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0003\"\u0015\u0010\r\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0003\"\u0015\u0010\u000e\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0003\"\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0003\"\u0015\u0010\u0010\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0003\"\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0003\"\u0015\u0010\u0012\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0003\"\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0003\"\u0015\u0010\u0014\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0003\"\u0015\u0010\u0015\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0003\"\u0015\u0010\u0016\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0003\"\u0015\u0010\u0017\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0003\"\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0003\"\u0015\u0010\u0019\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0003\"\u0015\u0010\u001a\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0003\"\u0015\u0010\u001b\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0003\"\u0015\u0010\u001c\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0003\"\u0015\u0010\u001d\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0003\"\u0015\u0010\u001e\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0003\"\u0015\u0010\u001f\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0003\"\u0015\u0010 \u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b \u0010\u0003\"\u0015\u0010!\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b!\u0010\u0003\"\u0015\u0010\"\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\"\u0010\u0003\"\u0015\u0010#\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b#\u0010\u0003\"\u0015\u0010$\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b$\u0010\u0003¨\u0006-"}, d2 = {"isAny", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "isArrayType", "isBoolean", "isBooleanOrNullableBoolean", "isByte", Namer.IS_CHAR, "isCharOrNullableChar", "isDouble", "isEnum", "isFloat", "isInt", "isLong", "isNonPrimitiveArray", "isNothing", "isNothingOrNullableNothing", "isNullableAny", "isNullableBoolean", "isNullableNothing", "isPrimitive", "isPrimitiveArray", "isPrimitiveNumberOrNullableType", "isPrimitiveOrNullablePrimitive", "isPrimitiveOrUnsignedArray", "isShort", "isString", "isThrowableOrNullableThrowable", "isUByte", "isUInt", "isULong", "isUShort", "isUnit", "isUnsignedArray", "isUnsignedType", "isUnsignedTypeOrNullableUnsignedType", "isAnyOfBuiltinType", "classIds", "", "Lorg/jetbrains/kotlin/name/ClassId;", "isBuiltinType", "classId", "isNullable", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/name/ClassId;Ljava/lang/Boolean;)Z", "cones"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConeBuiltinTypeUtilsKt {
    public static final boolean isByte(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getByte(), false);
    }

    public static final boolean isShort(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getShort(), false);
    }

    public static final boolean isInt(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getInt(), false);
    }

    public static final boolean isLong(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getLong(), false);
    }

    public static final boolean isFloat(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getFloat(), false);
    }

    public static final boolean isDouble(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getDouble(), false);
    }

    public static final boolean isAny(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getAny(), false);
    }

    public static final boolean isNullableAny(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getAny(), true);
    }

    public static final boolean isNothing(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getNothing(), false);
    }

    public static final boolean isNullableNothing(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getNothing(), true);
    }

    public static final boolean isNothingOrNullableNothing(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getNothing(), null);
    }

    public static final boolean isUnit(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getUnit(), false);
    }

    public static final boolean isBoolean(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getBoolean(), false);
    }

    public static final boolean isNullableBoolean(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getBoolean(), true);
    }

    public static final boolean isBooleanOrNullableBoolean(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getBoolean(), null);
    }

    public static final boolean isThrowableOrNullableThrowable(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isAnyOfBuiltinType(coneKotlinType, SetsKt.setOf(StandardClassIds.INSTANCE.getThrowable()));
    }

    public static final boolean isChar(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getChar(), false);
    }

    public static final boolean isCharOrNullableChar(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isAnyOfBuiltinType(coneKotlinType, SetsKt.setOf(StandardClassIds.INSTANCE.getChar()));
    }

    public static final boolean isString(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getString(), false);
    }

    public static final boolean isEnum(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getEnum(), false);
    }

    public static final boolean isUByte(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getUByte(), false);
    }

    public static final boolean isUShort(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getUShort(), false);
    }

    public static final boolean isUInt(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getUInt(), false);
    }

    public static final boolean isULong(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getULong(), false);
    }

    public static final boolean isPrimitiveOrNullablePrimitive(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isAnyOfBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getPrimitiveTypes());
    }

    public static final boolean isPrimitive(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isPrimitiveOrNullablePrimitive(coneKotlinType) && coneKotlinType.getNullability() == ConeNullability.NOT_NULL;
    }

    public static final boolean isPrimitiveNumberOrNullableType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return (!isPrimitiveOrNullablePrimitive(coneKotlinType) || isBooleanOrNullableBoolean(coneKotlinType) || isCharOrNullableChar(coneKotlinType)) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x006b  */
    /* JADX WARN: Code duplicated, block: B:28:0x0077 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:29:? A[LOOP:0: B:21:0x0065->B:29:?, LOOP_END, SYNTHETIC] */
    public static final boolean isArrayType(ConeKotlinType coneKotlinType) {
        Collection<ClassId> collectionValues;
        Iterator<T> it;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        if (!isBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getArray(), false)) {
            Collection<ClassId> collectionValues2 = StandardClassIds.INSTANCE.getPrimitiveArrayTypeByElementType().values();
            if ((collectionValues2 instanceof Collection) && collectionValues2.isEmpty()) {
                collectionValues = StandardClassIds.INSTANCE.getUnsignedArrayTypeByElementType().values();
                if (!(collectionValues instanceof Collection)) {
                }
                it = collectionValues.iterator();
                while (it.hasNext()) {
                    if (isBuiltinType(coneKotlinType, (ClassId) it.next(), false)) {
                    }
                }
                return false;
            }
            Iterator<T> it2 = collectionValues2.iterator();
            while (it2.hasNext()) {
                if (isBuiltinType(coneKotlinType, (ClassId) it2.next(), false)) {
                }
            }
            collectionValues = StandardClassIds.INSTANCE.getUnsignedArrayTypeByElementType().values();
            if (!(collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                return false;
            }
            it = collectionValues.iterator();
            while (it.hasNext()) {
                if (isBuiltinType(coneKotlinType, (ClassId) it.next(), false)) {
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean isNonPrimitiveArray(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return (coneKotlinType instanceof ConeClassLikeType) && Intrinsics.areEqual(((ConeClassLikeType) coneKotlinType).getLookupTag().getClassId(), StandardClassIds.INSTANCE.getArray());
    }

    public static final boolean isPrimitiveArray(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return (coneKotlinType instanceof ConeClassLikeType) && StandardClassIds.INSTANCE.getPrimitiveArrayTypeByElementType().values().contains(((ConeClassLikeType) coneKotlinType).getLookupTag().getClassId());
    }

    public static final boolean isUnsignedArray(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return (coneKotlinType instanceof ConeClassLikeType) && StandardClassIds.INSTANCE.getUnsignedArrayTypeByElementType().values().contains(((ConeClassLikeType) coneKotlinType).getLookupTag().getClassId());
    }

    public static final boolean isPrimitiveOrUnsignedArray(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isPrimitiveArray(coneKotlinType) || isUnsignedArray(coneKotlinType);
    }

    public static final boolean isUnsignedTypeOrNullableUnsignedType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isAnyOfBuiltinType(coneKotlinType, StandardClassIds.INSTANCE.getUnsignedTypes());
    }

    public static final boolean isUnsignedType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return isUnsignedTypeOrNullableUnsignedType(coneKotlinType) && coneKotlinType.getNullability() == ConeNullability.NOT_NULL;
    }

    private static final boolean isBuiltinType(ConeKotlinType coneKotlinType, ClassId classId, Boolean bool) {
        if ((coneKotlinType instanceof ConeClassLikeType) && Intrinsics.areEqual(((ConeClassLikeType) coneKotlinType).getLookupTag().getClassId(), classId)) {
            return bool == null || Intrinsics.areEqual(Boolean.valueOf(ConeTypeUtilsKt.isNullable(coneKotlinType.getType())), bool);
        }
        return false;
    }

    private static final boolean isAnyOfBuiltinType(ConeKotlinType coneKotlinType, Set<ClassId> set) {
        if (coneKotlinType instanceof ConeClassLikeType) {
            return set.contains(((ConeClassLikeType) coneKotlinType).getLookupTag().getClassId());
        }
        return false;
    }
}
