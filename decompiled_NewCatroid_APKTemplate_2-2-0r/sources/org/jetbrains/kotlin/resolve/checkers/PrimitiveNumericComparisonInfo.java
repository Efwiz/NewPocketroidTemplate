package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: PrimitiveNumericComparisonCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/PrimitiveNumericComparisonInfo;", "", "comparisonType", "Lorg/jetbrains/kotlin/types/KotlinType;", "leftPrimitiveType", "rightPrimitiveType", "leftType", "rightType", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getComparisonType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getLeftPrimitiveType", "getLeftType", "getRightPrimitiveType", "getRightType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveNumericComparisonInfo {
    private final KotlinType comparisonType;
    private final KotlinType leftPrimitiveType;
    private final KotlinType leftType;
    private final KotlinType rightPrimitiveType;
    private final KotlinType rightType;

    public PrimitiveNumericComparisonInfo(KotlinType comparisonType, KotlinType leftPrimitiveType, KotlinType rightPrimitiveType, KotlinType leftType, KotlinType rightType) {
        Intrinsics.checkNotNullParameter(comparisonType, "comparisonType");
        Intrinsics.checkNotNullParameter(leftPrimitiveType, "leftPrimitiveType");
        Intrinsics.checkNotNullParameter(rightPrimitiveType, "rightPrimitiveType");
        Intrinsics.checkNotNullParameter(leftType, "leftType");
        Intrinsics.checkNotNullParameter(rightType, "rightType");
        this.comparisonType = comparisonType;
        this.leftPrimitiveType = leftPrimitiveType;
        this.rightPrimitiveType = rightPrimitiveType;
        this.leftType = leftType;
        this.rightType = rightType;
    }

    public final KotlinType getComparisonType() {
        return this.comparisonType;
    }

    public final KotlinType getLeftPrimitiveType() {
        return this.leftPrimitiveType;
    }

    public final KotlinType getRightPrimitiveType() {
        return this.rightPrimitiveType;
    }

    public final KotlinType getLeftType() {
        return this.leftType;
    }

    public final KotlinType getRightType() {
        return this.rightType;
    }
}
