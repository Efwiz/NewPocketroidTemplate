package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;

/* JADX INFO: compiled from: IrExpressionBodyImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\bR\u0014\u0010\u0007\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0004R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrExpressionBodyImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "startOffset", "", "endOffset", "(IILorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndOffset", "()I", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setExpression", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getStartOffset", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrExpressionBodyImpl extends IrExpressionBody {
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpressionBody
    public IrExpression getExpression() {
        return this.expression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpressionBody
    public void setExpression(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.expression = irExpression;
    }

    public IrExpressionBodyImpl(int i, int i2, IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.startOffset = i;
        this.endOffset = i2;
        this.expression = expression;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrExpressionBodyImpl(IrExpression expression) {
        this(expression.getStartOffset(), expression.getEndOffset(), expression);
        Intrinsics.checkNotNullParameter(expression, "expression");
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpressionBody
    public IrFactory getFactory() {
        return IrFactoryImpl.INSTANCE;
    }
}
