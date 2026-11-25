package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.mpp.FunctionSymbolMarker;

/* JADX INFO: compiled from: IrSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u00012\u00020\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrFunctionSymbol extends IrReturnTargetSymbol, FunctionSymbolMarker {
    @Override // org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    IrFunction getOwner();
}
