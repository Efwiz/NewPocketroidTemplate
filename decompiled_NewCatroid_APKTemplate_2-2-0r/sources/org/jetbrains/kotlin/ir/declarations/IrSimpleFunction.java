package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrSimpleFunction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J5\u0010\u001c\u001a\u0002H\u001d\"\u0004\b\u0000\u0010\u001d\"\u0004\b\u0001\u0010\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\u001e0 2\u0006\u0010!\u001a\u0002H\u001eH\u0016¢\u0006\u0002\u0010\"R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\rX¦\u000e¢\u0006\f\u001a\u0004\b\f\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00020\rX¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u0018\u0010\u0013\u001a\u00020\rX¦\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u0018\u0010\u0015\u001a\u00020\rX¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R\u0018\u0010\u0017\u001a\u00020\rX¦\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\u000e\"\u0004\b\u0018\u0010\u0010R\u0012\u0010\u0019\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "()V", "correspondingPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getCorrespondingPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setCorrespondingPropertySymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "isFakeOverride", "", "()Z", "setFakeOverride", "(Z)V", "isInfix", "setInfix", "isOperator", "setOperator", "isSuspend", "setSuspend", "isTailrec", "setTailrec", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrSimpleFunction extends IrFunction implements IrAttributeContainer, IrOverridableDeclaration<IrSimpleFunctionSymbol> {
    public abstract IrPropertySymbol getCorrespondingPropertySymbol();

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction, org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrSimpleFunctionSymbol getSymbol();

    public abstract boolean isFakeOverride();

    public abstract boolean isInfix();

    public abstract boolean isOperator();

    public abstract boolean isSuspend();

    public abstract boolean isTailrec();

    public abstract void setCorrespondingPropertySymbol(IrPropertySymbol irPropertySymbol);

    public abstract void setFakeOverride(boolean z);

    public abstract void setInfix(boolean z);

    public abstract void setOperator(boolean z);

    public abstract void setSuspend(boolean z);

    public abstract void setTailrec(boolean z);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSimpleFunction(this, data);
    }
}
