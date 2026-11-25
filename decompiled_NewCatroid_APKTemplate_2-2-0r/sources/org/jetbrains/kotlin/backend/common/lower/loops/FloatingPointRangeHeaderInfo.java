package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\n\u0010\t\u001a\u0004\u0018\u00010\u0001H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/FloatingPointRangeHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "start", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "endInclusive", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndInclusive", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getStart", "asReversed", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FloatingPointRangeHeaderInfo extends HeaderInfo {
    private final IrExpression endInclusive;
    private final IrExpression start;

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo
    public HeaderInfo asReversed() {
        return null;
    }

    public final IrExpression getStart() {
        return this.start;
    }

    public final IrExpression getEndInclusive() {
        return this.endInclusive;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloatingPointRangeHeaderInfo(IrExpression start, IrExpression endInclusive) {
        super(null);
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(endInclusive, "endInclusive");
        this.start = start;
        this.endInclusive = endInclusive;
    }
}
