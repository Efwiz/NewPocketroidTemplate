package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;

/* JADX INFO: compiled from: PrimitiveComparison.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/PrimitiveConeNumericComparisonInfo;", "", "comparisonType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "leftType", "rightType", "(Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;)V", "getComparisonType", "()Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getLeftType", "getRightType", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveConeNumericComparisonInfo {
    private final ConeClassLikeType comparisonType;
    private final ConeClassLikeType leftType;
    private final ConeClassLikeType rightType;

    public PrimitiveConeNumericComparisonInfo(ConeClassLikeType comparisonType, ConeClassLikeType leftType, ConeClassLikeType rightType) {
        Intrinsics.checkNotNullParameter(comparisonType, "comparisonType");
        Intrinsics.checkNotNullParameter(leftType, "leftType");
        Intrinsics.checkNotNullParameter(rightType, "rightType");
        this.comparisonType = comparisonType;
        this.leftType = leftType;
        this.rightType = rightType;
    }

    public final ConeClassLikeType getComparisonType() {
        return this.comparisonType;
    }

    public final ConeClassLikeType getLeftType() {
        return this.leftType;
    }

    public final ConeClassLikeType getRightType() {
        return this.rightType;
    }
}
