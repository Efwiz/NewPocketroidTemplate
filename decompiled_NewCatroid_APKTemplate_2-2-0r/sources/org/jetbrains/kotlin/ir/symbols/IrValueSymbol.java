package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;

/* JADX INFO: compiled from: IrSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u00020\u00038&X§\u0004¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u0082\u0001\u0002\f\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrValueSymbol extends IrSymbol {

    /* JADX INFO: compiled from: IrSymbol.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @ObsoleteDescriptorBasedAPI
        public static /* synthetic */ void getDescriptor$annotations() {
        }
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    ValueDescriptor getDescriptor();

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    IrValueDeclaration getOwner();
}
