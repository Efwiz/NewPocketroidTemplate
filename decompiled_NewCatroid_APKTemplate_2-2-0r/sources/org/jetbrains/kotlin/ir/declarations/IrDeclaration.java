package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;

/* JADX INFO: compiled from: IrDeclaration.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003R\u001a\u0010\u0004\u001a\u00020\u00058&X§\u0004¢\u0006\f\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\u00020\u0015X¦\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrDeclaration extends IrStatement, IrMutableAnnotationContainer, IrSymbolOwner {

    /* JADX INFO: compiled from: IrDeclaration.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @ObsoleteDescriptorBasedAPI
        public static /* synthetic */ void getDescriptor$annotations() {
        }
    }

    DeclarationDescriptor getDescriptor();

    IrFactory getFactory();

    IrDeclarationOrigin getOrigin();

    IrDeclarationParent getParent();

    void setOrigin(IrDeclarationOrigin irDeclarationOrigin);

    void setParent(IrDeclarationParent irDeclarationParent);
}
