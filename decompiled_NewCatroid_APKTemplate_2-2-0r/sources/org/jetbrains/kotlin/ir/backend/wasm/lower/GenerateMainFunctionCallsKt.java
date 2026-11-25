package org.jetbrains.kotlin.ir.backend.wasm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.utils.JsMainFunctionDetector;
import org.jetbrains.kotlin.ir.backend.js.utils.JsMainFunctionDetectorKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;

/* JADX INFO: compiled from: generateMainFunctionCalls.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"generateMainFunctionCalls", "", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GenerateMainFunctionCallsKt {
    public static final void generateMainFunctionCalls(WasmBackendContext context, IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(module, "module");
        WasmBackendContext wasmBackendContext = context;
        IrSimpleFunction mainFunctionOrNull = new JsMainFunctionDetector(wasmBackendContext).getMainFunctionOrNull(module);
        if (mainFunctionOrNull == null) {
            return;
        }
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.firstOrNull((List) mainFunctionOrNull.getValueParameters());
        int i = 0;
        boolean zIsStringArrayParameter = irValueParameter != null ? JsMainFunctionDetectorKt.isStringArrayParameter(irValueParameter) : false;
        IrSimpleFunction irSimpleFunction = mainFunctionOrNull;
        boolean zIsLoweredSuspendFunction = JsMainFunctionDetectorKt.isLoweredSuspendFunction(irSimpleFunction, wasmBackendContext);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(context, context.getMainCallsWrapperFunction().getSymbol(), 0, 0, 6, (Object) null);
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = null;
        IrExpression irExpressionCreateArrayOfExpression = zIsStringArrayParameter ? IrUtilsKt.createArrayOfExpression(context, -1, -1, context.getIrBuiltIns().getStringType(), CollectionsKt.emptyList()) : null;
        if (zIsLoweredSuspendFunction) {
            IrSimpleFunction getter = context.getWasmSymbols().getCoroutineEmptyContinuation().getOwner().getGetter();
            Intrinsics.checkNotNull(getter);
            irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, getter);
        }
        IrBody body = context.getMainCallsWrapperFunction().getBody();
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        List<IrStatement> statements = ((IrBlockBody) body).getStatements();
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, irSimpleFunction);
        for (Object obj : CollectionsKt.listOfNotNull((Object[]) new IrExpression[]{irExpressionCreateArrayOfExpression, irFunctionAccessExpressionIrCall})) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irFunctionAccessExpressionIrCall2.putValueArgument(i, (IrExpression) obj);
            i = i2;
        }
        statements.add(irFunctionAccessExpressionIrCall2);
    }
}
