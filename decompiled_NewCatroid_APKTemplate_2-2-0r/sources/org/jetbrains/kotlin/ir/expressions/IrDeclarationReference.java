package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrDeclarationReference.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrDeclarationReference extends IrExpression {
    public abstract IrSymbol getSymbol();
}
