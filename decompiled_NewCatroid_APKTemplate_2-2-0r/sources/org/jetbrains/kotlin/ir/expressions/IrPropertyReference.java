package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrPropertyReference.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00150\u00172\u0006\u0010\u0018\u001a\u0002H\u0015H\u0016¢\u0006\u0002\u0010\u0019R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u0004\u0018\u00010\u000bX¦\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u000bX¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "()V", "field", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getField", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "setField", "(Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;)V", "getter", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getGetter", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setGetter", "(Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "setter", "getSetter", "setSetter", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrPropertyReference extends IrCallableReference<IrPropertySymbol> {
    public abstract IrFieldSymbol getField();

    public abstract IrSimpleFunctionSymbol getGetter();

    public abstract IrSimpleFunctionSymbol getSetter();

    public abstract void setField(IrFieldSymbol irFieldSymbol);

    public abstract void setGetter(IrSimpleFunctionSymbol irSimpleFunctionSymbol);

    public abstract void setSetter(IrSimpleFunctionSymbol irSimpleFunctionSymbol);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitPropertyReference2(this, data);
    }
}
