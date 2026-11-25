package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: WasmVarargExpressionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u0007"}, d2 = {"irIntPlus", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "rhs", "lhs", "wasmContext", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WasmVarargExpressionLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression irIntPlus(IrBlockBuilder irBlockBuilder, IrExpression irExpression, IrExpression irExpression2, WasmBackendContext wasmBackendContext) {
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder, wasmBackendContext.getWasmSymbols().getBinaryOperator(OperatorNameConventions.PLUS, irBlockBuilder.getContext().getIrBuiltIns().getIntType(), irBlockBuilder.getContext().getIrBuiltIns().getIntType()).getOwner());
        irFunctionAccessExpressionIrCall.setDispatchReceiver(irExpression);
        irFunctionAccessExpressionIrCall.putValueArgument(0, irExpression2);
        return irFunctionAccessExpressionIrCall;
    }
}
