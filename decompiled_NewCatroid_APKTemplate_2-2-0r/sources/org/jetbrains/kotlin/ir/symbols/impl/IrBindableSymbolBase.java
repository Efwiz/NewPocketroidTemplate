package org.jetbrains.kotlin.ir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: IrPrivateSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0006B\u000f\u0012\b\u0010\u0007\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\bJ\u0015\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001bJ\u0010\u0010\u001c\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u0002H\u0002R\u0012\u0010\t\u001a\u0004\u0018\u00018\u0001X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0014\u0010\u000e\u001a\u00028\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0014¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrBindableSymbolBase;", "Descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/symbols/impl/IrSymbolBase;", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "_owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "isBound", "", "()Z", "owner", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "privateSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getPrivateSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "setPrivateSignature", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "signature", "getSignature", "bind", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)V", "isOriginalDescriptor", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBindableSymbolBase<Descriptor extends DeclarationDescriptor, Owner extends IrSymbolOwner> extends IrSymbolBase<Descriptor> implements IrBindableSymbol<Descriptor, Owner> {
    private Owner _owner;
    private IdSignature privateSignature;

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public Owner getOwner() {
        Owner owner = this._owner;
        if (owner != null) {
            return owner;
        }
        throw new IllegalStateException((getClass().getSimpleName() + " is unbound. Signature: " + getSignature()).toString());
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public IdSignature getSignature() {
        return null;
    }

    public IrBindableSymbolBase(Descriptor descriptor) {
        DeclarationDescriptor moduleDescriptor;
        super(descriptor);
        if (descriptor != null) {
            isOriginalDescriptor(descriptor);
        }
        if (descriptor == null || (moduleDescriptor = descriptor.getModuleDescriptor()) == null) {
            return;
        }
        isOriginalDescriptor(moduleDescriptor);
    }

    private final boolean isOriginalDescriptor(DeclarationDescriptor descriptor) {
        return ((descriptor instanceof ValueParameterDescriptor) && isOriginalDescriptor(((ValueParameterDescriptor) descriptor).getModuleDescriptor())) || Intrinsics.areEqual(descriptor, descriptor.getOriginal());
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrBindableSymbol
    public void bind(Owner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (this._owner == null) {
            this._owner = owner;
        } else {
            StringBuilder sbAppend = new StringBuilder().append(getClass().getSimpleName()).append(" is already bound. Signature: ").append(getSignature()).append(". Owner: ");
            Owner owner2 = this._owner;
            throw new IllegalStateException(sbAppend.append(owner2 != null ? RenderIrElementKt.render$default(owner2, (DumpIrTreeOptions) null, 1, (Object) null) : null).toString().toString());
        }
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public boolean isBound() {
        return this._owner != null;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public IdSignature getPrivateSignature() {
        return this.privateSignature;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public void setPrivateSignature(IdSignature idSignature) {
        this.privateSignature = idSignature;
    }
}
