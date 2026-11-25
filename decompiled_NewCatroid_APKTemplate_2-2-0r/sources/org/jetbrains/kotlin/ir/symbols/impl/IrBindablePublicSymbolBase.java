package org.jetbrains.kotlin.ir.symbols.impl;

import com.reandroid.apkeditor.Options;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: IrPublicSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0006B\u0017\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\nJ\u0015\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u0002H\u0002R\u0012\u0010\u000b\u001a\u0004\u0018\u00018\u0001X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00028\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrBindablePublicSymbolBase;", "Descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/symbols/impl/IrPublicSymbolBase;", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", Options.TYPE_SIG, "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "descriptor", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "_owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "isBound", "", "()Z", "owner", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "privateSignature", "getPrivateSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "setPrivateSignature", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "bind", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)V", "isOriginalDescriptor", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBindablePublicSymbolBase<Descriptor extends DeclarationDescriptor, Owner extends IrSymbolOwner> extends IrPublicSymbolBase<Descriptor> implements IrBindableSymbol<Descriptor, Owner> {
    private Owner _owner;
    private IdSignature privateSignature;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBindablePublicSymbolBase(IdSignature sig, Descriptor descriptor) {
        super(sig, descriptor);
        Intrinsics.checkNotNullParameter(sig, "sig");
        if (descriptor != null) {
            isOriginalDescriptor(descriptor);
        }
    }

    private final boolean isOriginalDescriptor(DeclarationDescriptor descriptor) {
        return ((descriptor instanceof ValueParameterDescriptor) && isOriginalDescriptor(((ValueParameterDescriptor) descriptor).getModuleDescriptor())) || Intrinsics.areEqual(descriptor, descriptor.getOriginal());
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public Owner getOwner() {
        Owner owner = this._owner;
        if (owner != null) {
            return owner;
        }
        throw new IllegalStateException("Symbol for " + getSignature() + " is unbound");
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrBindableSymbol
    public void bind(Owner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (this._owner == null) {
            this._owner = owner;
        } else {
            StringBuilder sbAppend = new StringBuilder().append(getClass().getSimpleName()).append(" for ").append(getSignature()).append(" is already bound: ");
            Owner owner2 = this._owner;
            throw new IllegalStateException(sbAppend.append(owner2 != null ? RenderIrElementKt.render$default(owner2, (DumpIrTreeOptions) null, 1, (Object) null) : null).toString());
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
