package org.jetbrains.kotlin.fir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: Fir2IrSymbols.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/Fir2IrPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/Fir2IrBindableSymbol;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrPropertySymbol extends Fir2IrBindableSymbol<PropertyDescriptor, IrProperty> implements IrPropertySymbol {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Fir2IrPropertySymbol(IdSignature signature) {
        super(signature);
        Intrinsics.checkNotNullParameter(signature, "signature");
    }
}
