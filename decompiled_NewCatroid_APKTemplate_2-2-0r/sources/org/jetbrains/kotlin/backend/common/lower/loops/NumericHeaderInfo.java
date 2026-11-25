package org.jetbrains.kotlin.backend.common.lower.loops;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001BG\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0010R\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014\u0082\u0001\u0002\u0019\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "progressionType", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "first", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "last", "step", "isLastInclusive", "", "canCacheLast", "isReversed", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionDirection;", "(Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;ZZZLorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionDirection;)V", "getCanCacheLast", "()Z", "getDirection", "()Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionDirection;", "getFirst", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getLast", "getProgressionType", "()Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "getStep", "Lorg/jetbrains/kotlin/backend/common/lower/loops/IndexedGetHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionHeaderInfo;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NumericHeaderInfo extends HeaderInfo {
    private final boolean canCacheLast;
    private final ProgressionDirection direction;
    private final IrExpression first;
    private final boolean isLastInclusive;
    private final boolean isReversed;
    private final IrExpression last;
    private final ProgressionType progressionType;
    private final IrExpression step;

    public /* synthetic */ NumericHeaderInfo(ProgressionType progressionType, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3, boolean z, boolean z2, boolean z3, ProgressionDirection progressionDirection, DefaultConstructorMarker defaultConstructorMarker) {
        this(progressionType, irExpression, irExpression2, irExpression3, z, z2, z3, progressionDirection);
    }

    public final ProgressionType getProgressionType() {
        return this.progressionType;
    }

    public final IrExpression getFirst() {
        return this.first;
    }

    public final IrExpression getLast() {
        return this.last;
    }

    public final IrExpression getStep() {
        return this.step;
    }

    /* JADX INFO: renamed from: isLastInclusive, reason: from getter */
    public final boolean getIsLastInclusive() {
        return this.isLastInclusive;
    }

    public final boolean getCanCacheLast() {
        return this.canCacheLast;
    }

    /* JADX INFO: renamed from: isReversed, reason: from getter */
    public final boolean getIsReversed() {
        return this.isReversed;
    }

    public final ProgressionDirection getDirection() {
        return this.direction;
    }

    private NumericHeaderInfo(ProgressionType progressionType, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3, boolean z, boolean z2, boolean z3, ProgressionDirection progressionDirection) {
        super(null);
        this.progressionType = progressionType;
        this.first = irExpression;
        this.last = irExpression2;
        this.step = irExpression3;
        this.isLastInclusive = z;
        this.canCacheLast = z2;
        this.isReversed = z3;
        this.direction = progressionDirection;
    }
}
