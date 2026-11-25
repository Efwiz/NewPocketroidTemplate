package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;

/* JADX INFO: compiled from: IrSpreadElementImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrSpreadElementImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "startOffset", "", "endOffset", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(IILorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndOffset", "()I", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getStartOffset", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSpreadElementImpl extends IrSpreadElement {
    private final int endOffset;
    private IrExpression expression;
    private final int startOffset;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSpreadElement
    public IrExpression getExpression() {
        return this.expression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSpreadElement
    public void setExpression(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.expression = irExpression;
    }

    public IrSpreadElementImpl(int i, int i2, IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.startOffset = i;
        this.endOffset = i2;
        this.expression = expression;
    }
}
