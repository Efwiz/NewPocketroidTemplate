package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrDelegatingSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b&\u0018\u0000*\u0014\b\u0000\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0002*\b\b\u0001\u0010\u0004*\u00020\u0005*\b\b\u0002\u0010\u0003*\u00020\u00062\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0002B\r\u0012\u0006\u0010\u0007\u001a\u00028\u0000¢\u0006\u0002\u0010\bJ\u0015\u0010$\u001a\u00020%2\u0006\u0010\u0018\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010&J\u0013\u0010'\u001a\u00020\u00132\b\u0010(\u001a\u0004\u0018\u00010)H\u0096\u0002J\b\u0010*\u001a\u00020+H\u0016R\u001c\u0010\u0007\u001a\u00028\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\bR\u001a\u0010\r\u001a\u00028\u00028VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u00138VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0016R\u0014\u0010\u0018\u001a\u00028\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR(\u0010\u001d\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0016\u0010\"\u001a\u0004\u0018\u00010\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u001f¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrDelegatingSymbol;", "DelegateSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "Descriptor", "Owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;)V", "getDelegate", "()Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "setDelegate", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "descriptor", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasDescriptor", "", "getHasDescriptor$annotations", "getHasDescriptor", "()Z", "isBound", "owner", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "value", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "privateSignature", "getPrivateSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "setPrivateSignature", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "signature", "getSignature", "bind", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)V", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrDelegatingSymbol<DelegateSymbol extends IrBindableSymbol<? extends Descriptor, Owner>, Owner extends IrSymbolOwner, Descriptor extends DeclarationDescriptor> implements IrBindableSymbol<Descriptor, Owner> {
    private DelegateSymbol delegate;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getHasDescriptor$annotations() {
    }

    public IrDelegatingSymbol(DelegateSymbol delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    public final DelegateSymbol getDelegate() {
        return this.delegate;
    }

    public final void setDelegate(DelegateSymbol delegatesymbol) {
        Intrinsics.checkNotNullParameter(delegatesymbol, "<set-?>");
        this.delegate = delegatesymbol;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public Owner getOwner() {
        return (Owner) this.delegate.getOwner();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public Descriptor getDescriptor() {
        return (Descriptor) this.delegate.getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public boolean getHasDescriptor() {
        return this.delegate.getHasDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public boolean isBound() {
        return this.delegate.isBound();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public IdSignature getSignature() {
        return this.delegate.getSignature();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrBindableSymbol
    public void bind(Owner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.delegate.bind(owner);
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    public boolean equals(Object other) {
        return this == other || this.delegate == other;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public IdSignature getPrivateSignature() {
        return this.delegate.getPrivateSignature();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public void setPrivateSignature(IdSignature idSignature) {
        this.delegate.setPrivateSignature(idSignature);
    }
}
