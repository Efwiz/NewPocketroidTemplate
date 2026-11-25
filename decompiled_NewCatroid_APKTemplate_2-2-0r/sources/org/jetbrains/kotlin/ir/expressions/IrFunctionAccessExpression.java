package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;

/* JADX INFO: compiled from: IrFunctionAccessExpression.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003R\u0018\u0010\u0004\u001a\u00020\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "()V", "contextReceiversCount", "", "getContextReceiversCount", "()I", "setContextReceiversCount", "(I)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrFunctionAccessExpression extends IrMemberAccessExpression<IrFunctionSymbol> {
    public abstract int getContextReceiversCount();

    public abstract void setContextReceiversCount(int i);
}
