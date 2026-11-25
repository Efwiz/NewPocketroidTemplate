package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrValueAccessExpression.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\u00020\nX¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "()V", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrValueAccessExpression extends IrDeclarationReference {
    public abstract IrStatementOrigin getOrigin();

    @Override // org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public abstract IrValueSymbol getSymbol();

    public abstract void setOrigin(IrStatementOrigin irStatementOrigin);

    public abstract void setSymbol(IrValueSymbol irValueSymbol);
}
