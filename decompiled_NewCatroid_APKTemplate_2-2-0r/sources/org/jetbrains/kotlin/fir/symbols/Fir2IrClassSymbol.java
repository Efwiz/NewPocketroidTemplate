package org.jetbrains.kotlin.fir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: Fir2IrSymbols.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/Fir2IrClassSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/Fir2IrBindableSymbol;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrClassSymbol extends Fir2IrBindableSymbol<ClassDescriptor, IrClass> implements IrClassSymbol {
    @Override // org.jetbrains.kotlin.fir.symbols.Fir2IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ ClassifierDescriptor getDescriptor() {
        return (ClassifierDescriptor) getDescriptor();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Fir2IrClassSymbol(IdSignature signature) {
        super(signature);
        Intrinsics.checkNotNullParameter(signature, "signature");
    }
}
