package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrConstructor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017R\u001a\u0010\u0003\u001a\u00020\u00048&X§\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\n\"\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "()V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "isPrimary", "", "()Z", "setPrimary", "(Z)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConstructor extends IrFunction {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction, org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public abstract ClassConstructorDescriptor getDescriptor();

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction, org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrConstructorSymbol getSymbol();

    public abstract boolean isPrimary();

    public abstract void setPrimary(boolean z);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitConstructor2(this, data);
    }
}
