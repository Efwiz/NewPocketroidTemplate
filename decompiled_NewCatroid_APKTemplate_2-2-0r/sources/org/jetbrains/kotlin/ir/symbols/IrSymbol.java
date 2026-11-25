package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;

/* JADX INFO: compiled from: IrSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u00020\u00038&X§\u0004¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\t8&X§\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u0005\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u0013X¦\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0015¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasDescriptor", "", "getHasDescriptor$annotations", "getHasDescriptor", "()Z", "isBound", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "privateSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getPrivateSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "setPrivateSignature", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "signature", "getSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrSymbol extends DeclarationSymbolMarker {

    /* JADX INFO: compiled from: IrSymbol.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @ObsoleteDescriptorBasedAPI
        public static /* synthetic */ void getDescriptor$annotations() {
        }

        @ObsoleteDescriptorBasedAPI
        public static /* synthetic */ void getHasDescriptor$annotations() {
        }
    }

    DeclarationDescriptor getDescriptor();

    boolean getHasDescriptor();

    IrSymbolOwner getOwner();

    IdSignature getPrivateSignature();

    IdSignature getSignature();

    boolean isBound();

    void setPrivateSignature(IdSignature idSignature);
}
