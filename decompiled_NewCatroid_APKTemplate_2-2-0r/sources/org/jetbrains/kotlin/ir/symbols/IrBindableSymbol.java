package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;

/* JADX INFO: compiled from: IrSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0005J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u0010R\u001a\u0010\u0006\u001a\u00028\u00008&X§\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "Descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Owner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "descriptor", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "owner", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "bind", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrBindableSymbol<Descriptor extends DeclarationDescriptor, Owner extends IrSymbolOwner> extends IrSymbol {

    /* JADX INFO: compiled from: IrSymbol.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @ObsoleteDescriptorBasedAPI
        public static /* synthetic */ void getDescriptor$annotations() {
        }
    }

    void bind(Owner owner);

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    Descriptor getDescriptor();

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    Owner getOwner();
}
