package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: IrWhenImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\b\u0016\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B%\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\b\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrBranchImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "condition", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "result", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "startOffset", "", "endOffset", "(IILorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getCondition", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setCondition", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndOffset", "()I", "getResult", "setResult", "getStartOffset", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBranchImpl extends IrBranch {
    private IrExpression condition;
    private final int endOffset;
    private IrExpression result;
    private final int startOffset;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrBranch
    public IrExpression getCondition() {
        return this.condition;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrBranch
    public void setCondition(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.condition = irExpression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrBranch
    public IrExpression getResult() {
        return this.result;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrBranch
    public void setResult(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.result = irExpression;
    }

    public IrBranchImpl(int i, int i2, IrExpression condition, IrExpression result) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(result, "result");
        this.startOffset = i;
        this.endOffset = i2;
        this.condition = condition;
        this.result = result;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrBranchImpl(IrExpression condition, IrExpression result) {
        this(condition.getStartOffset(), result.getEndOffset(), condition, result);
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(result, "result");
    }
}
