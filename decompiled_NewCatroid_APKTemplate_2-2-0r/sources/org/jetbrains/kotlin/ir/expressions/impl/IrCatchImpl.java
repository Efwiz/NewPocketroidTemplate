package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: IrTryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\nR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\bX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrCatchImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "startOffset", "", "endOffset", "catchParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "result", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(IILorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "(IILorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "getCatchParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "setCatchParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "getEndOffset", "()I", "getResult", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setResult", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getStartOffset", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrCatchImpl extends IrCatch {
    private IrVariable catchParameter;
    private final int endOffset;
    public IrExpression result;
    private final int startOffset;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCatch
    public IrVariable getCatchParameter() {
        return this.catchParameter;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCatch
    public void setCatchParameter(IrVariable irVariable) {
        Intrinsics.checkNotNullParameter(irVariable, "<set-?>");
        this.catchParameter = irVariable;
    }

    public IrCatchImpl(int i, int i2, IrVariable catchParameter) {
        Intrinsics.checkNotNullParameter(catchParameter, "catchParameter");
        this.startOffset = i;
        this.endOffset = i2;
        this.catchParameter = catchParameter;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrCatchImpl(int i, int i2, IrVariable catchParameter, IrExpression result) {
        this(i, i2, catchParameter);
        Intrinsics.checkNotNullParameter(catchParameter, "catchParameter");
        Intrinsics.checkNotNullParameter(result, "result");
        setResult(result);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCatch
    public IrExpression getResult() {
        IrExpression irExpression = this.result;
        if (irExpression != null) {
            return irExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("result");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCatch
    public void setResult(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.result = irExpression;
    }
}
