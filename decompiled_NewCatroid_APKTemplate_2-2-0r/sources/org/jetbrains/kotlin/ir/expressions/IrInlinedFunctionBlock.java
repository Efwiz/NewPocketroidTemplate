package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: IrInlinedFunctionBlock.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0018\u0010\u0003\u001a\u00020\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\u00020\nX¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "()V", "inlineCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "getInlineCall", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "setInlineCall", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)V", "inlinedElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "getInlinedElement", "()Lorg/jetbrains/kotlin/ir/IrElement;", "setInlinedElement", "(Lorg/jetbrains/kotlin/ir/IrElement;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrInlinedFunctionBlock extends IrBlock {
    public abstract IrFunctionAccessExpression getInlineCall();

    public abstract IrElement getInlinedElement();

    public abstract void setInlineCall(IrFunctionAccessExpression irFunctionAccessExpression);

    public abstract void setInlinedElement(IrElement irElement);
}
