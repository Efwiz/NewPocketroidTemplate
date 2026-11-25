package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;

/* JADX INFO: compiled from: IrSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\u00020\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrClassSymbol extends IrBindableSymbol<ClassDescriptor, IrClass>, IrClassifierSymbol, RegularClassSymbolMarker {
}
