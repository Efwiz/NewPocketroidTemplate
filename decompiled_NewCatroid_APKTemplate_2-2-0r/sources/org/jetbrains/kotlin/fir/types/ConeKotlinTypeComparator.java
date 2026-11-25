package org.jetbrains.kotlin.fir.types;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: ConeKotlinTypeComparator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J-\u0010\t\u001a\u00020\u00062\u000e\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000b2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000bH\u0002¢\u0006\u0002\u0010\u000eJ\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0002H\u0016J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\u000fH\u0002J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lkotlin/Comparator;", "()V", "priority", "", "getPriority", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)I", "compare", "a", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "([Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;)I", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeKotlinTypeComparator implements Comparator<ConeKotlinType> {
    public static final ConeKotlinTypeComparator INSTANCE = new ConeKotlinTypeComparator();

    private ConeKotlinTypeComparator() {
    }

    private final int getPriority(ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeErrorType) {
            return 9;
        }
        if (coneKotlinType instanceof ConeLookupTagBasedType) {
            return 8;
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            return 7;
        }
        if (coneKotlinType instanceof ConeCapturedType) {
            return 6;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return 5;
        }
        if (coneKotlinType instanceof ConeIntersectionType) {
            return 4;
        }
        if (coneKotlinType instanceof ConeStubType) {
            return 3;
        }
        if (coneKotlinType instanceof ConeIntegerLiteralConstantType) {
            return 2;
        }
        return coneKotlinType instanceof ConeIntegerConstantOperatorType ? 1 : 0;
    }

    private final int compare(ConeTypeProjection a, ConeTypeProjection b) {
        int iOrdinal = a.getKind().ordinal() - b.getKind().ordinal();
        if (iOrdinal != 0) {
            return iOrdinal;
        }
        if (a instanceof ConeStarProjection) {
            return 0;
        }
        if (a instanceof ConeKotlinTypeProjectionIn) {
            if (!(b instanceof ConeKotlinTypeProjectionIn)) {
                throw new IllegalArgumentException(("ordinal is inconsistent: " + a + " v.s. " + b).toString());
            }
            return compare(((ConeKotlinTypeProjectionIn) a).getType(), ((ConeKotlinTypeProjectionIn) b).getType());
        }
        if (a instanceof ConeKotlinTypeProjectionOut) {
            if (!(b instanceof ConeKotlinTypeProjectionOut)) {
                throw new IllegalArgumentException(("ordinal is inconsistent: " + a + " v.s. " + b).toString());
            }
            return compare(((ConeKotlinTypeProjectionOut) a).getType(), ((ConeKotlinTypeProjectionOut) b).getType());
        }
        if (a instanceof ConeKotlinType) {
            boolean z = b instanceof ConeKotlinType;
        }
        Intrinsics.checkNotNull(a, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        Intrinsics.checkNotNull(b, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        return compare((ConeKotlinType) a, (ConeKotlinType) b);
    }

    private final int compare(ConeTypeProjection[] a, ConeTypeProjection[] b) {
        int length = a.length - b.length;
        if (length != 0) {
            return length;
        }
        for (Pair pair : ArraysKt.zip(a, b)) {
            int iCompare = compare((ConeTypeProjection) pair.component1(), (ConeTypeProjection) pair.component2());
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    private final int compare(ConeNullability a, ConeNullability b) {
        return a.ordinal() - b.ordinal();
    }

    @Override // java.util.Comparator
    public int compare(ConeKotlinType a, ConeKotlinType b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        int priority = getPriority(a) - getPriority(b);
        if (priority != 0) {
            return priority;
        }
        if (a instanceof ConeErrorType) {
            if (!(b instanceof ConeErrorType)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
            }
            return a.hashCode() - b.hashCode();
        }
        if (a instanceof ConeLookupTagBasedType) {
            if (!(b instanceof ConeLookupTagBasedType)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
            }
            int iCompareTo = ((ConeLookupTagBasedType) a).getLookupTag().getName().compareTo(((ConeLookupTagBasedType) b).getLookupTag().getName());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            int iCompare = compare(a.getNullability(), b.getNullability());
            return iCompare != 0 ? iCompare : compare(a.getTypeArguments(), b.getTypeArguments());
        }
        if (a instanceof ConeFlexibleType) {
            if (!(b instanceof ConeFlexibleType)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
            }
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) a;
            ConeFlexibleType coneFlexibleType2 = (ConeFlexibleType) b;
            int iCompare2 = compare((ConeKotlinType) coneFlexibleType.getLowerBound(), (ConeKotlinType) coneFlexibleType2.getLowerBound());
            return iCompare2 != 0 ? iCompare2 : compare((ConeKotlinType) coneFlexibleType.getUpperBound(), (ConeKotlinType) coneFlexibleType2.getUpperBound());
        }
        if (a instanceof ConeCapturedType) {
            if (!(b instanceof ConeCapturedType)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
            }
            ConeCapturedType coneCapturedType = (ConeCapturedType) a;
            ConeCapturedType coneCapturedType2 = (ConeCapturedType) b;
            int i = (coneCapturedType.getLowerType() != null ? 1 : 0) - (coneCapturedType2.getLowerType() == null ? 0 : 1);
            if (i != 0) {
                return i;
            }
            if (coneCapturedType.getLowerType() != null && coneCapturedType2.getLowerType() != null) {
                ConeKotlinType lowerType = coneCapturedType.getLowerType();
                Intrinsics.checkNotNull(lowerType);
                ConeKotlinType lowerType2 = coneCapturedType2.getLowerType();
                Intrinsics.checkNotNull(lowerType2);
                int iCompare3 = compare(lowerType, lowerType2);
                if (iCompare3 != 0) {
                    return iCompare3;
                }
            }
            int iCompare4 = compare(a.getNullability(), b.getNullability());
            return iCompare4 != 0 ? iCompare4 : coneCapturedType.getConstructor().hashCode() - coneCapturedType2.getConstructor().hashCode();
        }
        if (a instanceof ConeDefinitelyNotNullType) {
            if (!(b instanceof ConeDefinitelyNotNullType)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
            }
            return compare((ConeKotlinType) ((ConeDefinitelyNotNullType) a).getOriginal(), (ConeKotlinType) ((ConeDefinitelyNotNullType) b).getOriginal());
        }
        if (a instanceof ConeIntersectionType) {
            if (!(b instanceof ConeIntersectionType)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
            }
            int size = ((ConeIntersectionType) a).getIntersectedTypes().size() - ((ConeIntersectionType) b).getIntersectedTypes().size();
            return size != 0 ? size : a.hashCode() - b.hashCode();
        }
        if (a instanceof ConeStubType) {
            if (!(b instanceof ConeStubType)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
            }
            int iCompareTo2 = ((ConeStubType) a).getConstructor().getVariable().getTypeConstructor().getName().compareTo(((ConeStubType) b).getConstructor().getVariable().getTypeConstructor().getName());
            return iCompareTo2 != 0 ? iCompareTo2 : compare(a.getNullability(), b.getNullability());
        }
        if (a instanceof ConeIntegerLiteralConstantType) {
            if (!(b instanceof ConeIntegerLiteralConstantType)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
            }
            long value = ((ConeIntegerLiteralConstantType) a).getValue() - ((ConeIntegerLiteralConstantType) b).getValue();
            if (value != 0) {
                return (int) value;
            }
            int iCompare5 = compare(a.getNullability(), b.getNullability());
            return iCompare5 != 0 ? iCompare5 : a.hashCode() - b.hashCode();
        }
        if (a instanceof ConeIntegerConstantOperatorType) {
            return compare(a.getNullability(), b.getNullability());
        }
        throw new IllegalStateException(("Unsupported type comparison: " + ConeTypeUtilsKt.renderForDebugging(a) + " v.s. " + ConeTypeUtilsKt.renderForDebugging(b)).toString());
    }
}
