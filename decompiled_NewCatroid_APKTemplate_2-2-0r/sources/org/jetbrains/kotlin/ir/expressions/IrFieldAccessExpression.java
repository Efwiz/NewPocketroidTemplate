package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFieldAccessExpression.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u0010X¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\u00020\u0016X¦\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "()V", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getReceiver", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setReceiver", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getSuperQualifierSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "setSuperQualifierSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrFieldAccessExpression extends IrDeclarationReference {
    private IrExpression receiver;

    public abstract IrStatementOrigin getOrigin();

    public abstract IrClassSymbol getSuperQualifierSymbol();

    @Override // org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public abstract IrFieldSymbol getSymbol();

    public abstract void setOrigin(IrStatementOrigin irStatementOrigin);

    public abstract void setSuperQualifierSymbol(IrClassSymbol irClassSymbol);

    public abstract void setSymbol(IrFieldSymbol irFieldSymbol);

    public final IrExpression getReceiver() {
        return this.receiver;
    }

    public final void setReceiver(IrExpression irExpression) {
        this.receiver = irExpression;
    }
}
