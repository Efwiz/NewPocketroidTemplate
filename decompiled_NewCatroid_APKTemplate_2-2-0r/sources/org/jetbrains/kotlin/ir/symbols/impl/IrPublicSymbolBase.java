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
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: IrPublicSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0007J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0012\u0010\u0006\u001a\u0004\u0018\u00018\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u001a\u0010\t\u001a\u00028\u00008VX\u0097\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000f8VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrPublicSymbolBase;", "Descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "_descriptor", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptor", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasDescriptor", "", "getHasDescriptor$annotations", "getHasDescriptor", "()Z", "getSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrPublicSymbolBase<Descriptor extends DeclarationDescriptor> implements IrSymbol {
    private final Descriptor _descriptor;
    private final IdSignature signature;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getHasDescriptor$annotations() {
    }

    public IrPublicSymbolBase(IdSignature signature, Descriptor descriptor) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.signature = signature;
        this._descriptor = descriptor;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public IdSignature getSignature() {
        return this.signature;
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
        Intrinsics.checkNotNull(descriptor2, "null cannot be cast to non-null type Descriptor of org.jetbrains.kotlin.ir.symbols.impl.IrPublicSymbolBase");
        return descriptor2;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public boolean getHasDescriptor() {
        return this._descriptor != null;
    }

    public String toString() {
        return isBound() ? RenderIrElementKt.render$default(getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) : "Unbound public symbol " + getClass().getSimpleName() + ": " + getSignature();
    }
}
