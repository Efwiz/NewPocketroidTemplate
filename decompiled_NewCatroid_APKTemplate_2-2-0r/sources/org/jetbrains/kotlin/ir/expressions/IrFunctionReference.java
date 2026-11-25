package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrFunctionReference.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000fR\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u0002X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "()V", "reflectionTarget", "getReflectionTarget", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "setReflectionTarget", "(Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrFunctionReference extends IrCallableReference<IrFunctionSymbol> {
    public abstract IrFunctionSymbol getReflectionTarget();

    public abstract void setReflectionTarget(IrFunctionSymbol irFunctionSymbol);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFunctionReference(this, data);
    }
}
