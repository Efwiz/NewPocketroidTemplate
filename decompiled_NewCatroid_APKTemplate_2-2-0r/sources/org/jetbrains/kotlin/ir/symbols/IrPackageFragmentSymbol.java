package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;

/* JADX INFO: compiled from: IrSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u00020\u00038&X§\u0004¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrPackageFragmentSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrPackageFragmentSymbol extends IrSymbol {

    /* JADX INFO: compiled from: IrSymbol.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @ObsoleteDescriptorBasedAPI
        public static /* synthetic */ void getDescriptor$annotations() {
        }
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrSymbol
    PackageFragmentDescriptor getDescriptor();
}
