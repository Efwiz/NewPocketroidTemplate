package org.jetbrains.kotlin.fir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: Fir2IrBindableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010 J\b\u0010!\u001a\u00020\"H\u0016R\u0012\u0010\t\u001a\u0004\u0018\u00018\u0001X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\nR\u001a\u0010\u000b\u001a\u00028\u00008VX\u0097\u0004¢\u0006\f\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u00118VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0014R\u0014\u0010\u0016\u001a\u00028\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001b¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/Fir2IrBindableSymbol;", "D", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "B", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "_owner", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "descriptor", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasDescriptor", "", "getHasDescriptor$annotations", "getHasDescriptor", "()Z", "isBound", "owner", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "privateSignature", "getPrivateSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "setPrivateSignature", "getSignature", "bind", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "toString", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Fir2IrBindableSymbol<D extends DeclarationDescriptor, B extends IrDeclaration> implements IrBindableSymbol<D, B> {
    private B _owner;
    private IdSignature privateSignature;
    private final IdSignature signature;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getHasDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public boolean getHasDescriptor() {
        return false;
    }

    public Fir2IrBindableSymbol(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.signature = signature;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public IdSignature getSignature() {
        return this.signature;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public B getOwner() {
        B b = this._owner;
        if (b != null) {
            return b;
        }
        throw new IllegalStateException("Symbol is unbound");
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrBindableSymbol
    public void bind(B owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (this._owner == null) {
            this._owner = owner;
            return;
        }
        throw new IllegalStateException(getClass().getSimpleName() + " for " + getSignature() + " is already bound");
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public boolean isBound() {
        return this._owner != null;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public D getDescriptor() {
        D d = (D) IrBasedDescriptorsKt.toIrBasedDescriptor(getOwner());
        Intrinsics.checkNotNull(d, "null cannot be cast to non-null type D of org.jetbrains.kotlin.fir.symbols.Fir2IrBindableSymbol");
        return d;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public IdSignature getPrivateSignature() {
        return this.privateSignature;
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    public void setPrivateSignature(IdSignature idSignature) {
        this.privateSignature = idSignature;
    }

    public String toString() {
        return isBound() ? RenderIrElementKt.render$default(getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) : "Unbound public symbol for " + getSignature();
    }
}
