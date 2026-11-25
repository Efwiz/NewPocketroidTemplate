package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;

/* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionCallsLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInteropFunctionCallsLowering implements BodyLoweringPass {
    private final WasmBackendContext context;

    public JsInteropFunctionCallsLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, final IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (this.context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.WASI) {
            return;
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionCallsLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                transformChildrenVoid(expression);
                IrSimpleFunction irSimpleFunction = JsInteropFunctionCallsLowering.this.getContext().getMapping().getWasmJsInteropFunctionToWrapper().get(expression.getSymbol().getOwner());
                if (irSimpleFunction != null && !Intrinsics.areEqual(container, irSimpleFunction)) {
                    return IrUtilsKt.irCall(expression, irSimpleFunction, (56 & 4) != 0 ? false : false, (56 & 8) != 0 ? false : false, (56 & 16) != 0 ? null : null, (56 & 32) != 0 ? null : null);
                }
                return expression;
            }
        });
    }
}
