package org.jetbrains.kotlin.ir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: IrPrivateSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0005J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0012\u0010\u0004\u001a\u0004\u0018\u00018\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u001a\u0010\u0007\u001a\u00028\u00008VX\u0097\u0004¢\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\r8VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\t\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrSymbolBase;", "Descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "_descriptor", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptor", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasDescriptor", "", "getHasDescriptor$annotations", "getHasDescriptor", "()Z", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrSymbolBase<Descriptor extends DeclarationDescriptor> implements IrSymbol {
    private final Descriptor _descriptor;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getHasDescriptor$annotations() {
    }

    public IrSymbolBase(Descriptor descriptor) {
        this._descriptor = descriptor;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public Descriptor getDescriptor() {
        Descriptor descriptor = this._descriptor;
        if (descriptor != null) {
            return descriptor;
        }
        IrSymbolOwner owner = getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        Descriptor descriptor2 = (Descriptor) IrBasedDescriptorsKt.toIrBasedDescriptor((IrDeclaration) owner);
        Intrinsics.checkNotNull(descriptor2, "null cannot be cast to non-null type Descriptor of org.jetbrains.kotlin.ir.symbols.impl.IrSymbolBase");
        return descriptor2;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public boolean getHasDescriptor() {
        return this._descriptor != null;
    }

    public String toString() {
        if (isBound()) {
            return RenderIrElementKt.render$default(getOwner(), (DumpIrTreeOptions) null, 1, (Object) null);
        }
        return "Unbound private symbol " + (this._descriptor != null ? getClass().getSimpleName() + ": " + this._descriptor : super.toString());
    }
}
