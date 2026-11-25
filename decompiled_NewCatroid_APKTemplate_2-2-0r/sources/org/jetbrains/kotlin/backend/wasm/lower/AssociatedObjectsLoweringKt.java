package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.Unit;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrSingleStatementBuilder;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;

/* JADX INFO: compiled from: AssociatedObjectsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a4\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0002¨\u0006\u000b"}, d2 = {"createAssociatedObjectAdd", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "wasmSymbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "targetClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "keyAnnotation", "associatedObject", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AssociatedObjectsLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrCall createAssociatedObjectAdd(IrBuilderWithScope irBuilderWithScope, WasmSymbols wasmSymbols, IrBuiltIns irBuiltIns, IrClassSymbol irClassSymbol, IrClassSymbol irClassSymbol2, IrClassSymbol irClassSymbol3) {
        IrBuilderWithScope irBuilderWithScope2 = irBuilderWithScope;
        IrSingleStatementBuilder irSingleStatementBuilder = new IrSingleStatementBuilder(irBuilderWithScope2.getContext(), irBuilderWithScope2.getScope(), -1, -1, null, 16, null);
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irSingleStatementBuilder, wasmSymbols.getAddAssociatedObject(), irBuiltIns.getUnitType(), 0, 0, null, 28, null);
        irCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irGet(irSingleStatementBuilder, wasmSymbols.getInitAssociatedObjects().getOwner().getValueParameters().get(0)));
        IrCall irCallIrCall$default2 = ExpressionHelpersKt.irCall$default(irSingleStatementBuilder, wasmSymbols.getWasmTypeId(), irBuiltIns.getIntType(), 0, 0, null, 28, null);
        irCallIrCall$default2.putTypeArgument(0, IrTypesKt.getDefaultType(irClassSymbol));
        Unit unit = Unit.INSTANCE;
        irCallIrCall$default.putValueArgument(1, irCallIrCall$default2);
        IrCall irCallIrCall$default3 = ExpressionHelpersKt.irCall$default(irSingleStatementBuilder, wasmSymbols.getWasmTypeId(), irBuiltIns.getIntType(), 0, 0, null, 28, null);
        irCallIrCall$default3.putTypeArgument(0, IrTypesKt.getDefaultType(irClassSymbol2));
        Unit unit2 = Unit.INSTANCE;
        irCallIrCall$default.putValueArgument(2, irCallIrCall$default3);
        irCallIrCall$default.putValueArgument(3, ExpressionHelpersKt.irGetObjectValue(irSingleStatementBuilder, irBuiltIns.getAnyType(), irClassSymbol3));
        return irCallIrCall$default;
    }
}
