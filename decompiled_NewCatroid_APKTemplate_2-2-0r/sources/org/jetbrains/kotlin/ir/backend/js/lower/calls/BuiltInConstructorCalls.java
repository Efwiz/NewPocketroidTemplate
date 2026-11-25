package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: BuiltInConstructorCalls.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/BuiltInConstructorCalls;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "intrinsics", "Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "getIntrinsics", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "transformFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "doNotIntrinsify", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltInConstructorCalls implements CallsTransformer {
    private final JsIrBackendContext context;
    private final JsIntrinsics intrinsics;

    public BuiltInConstructorCalls(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.intrinsics = context.getIntrinsics();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final JsIntrinsics getIntrinsics() {
        return this.intrinsics;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        if (call instanceof IrConstructorCall) {
            IrConstructorSymbol symbol = ((IrConstructorCall) call).getSymbol();
            if (Intrinsics.areEqual(symbol, this.intrinsics.getStringConstructorSymbol())) {
                return JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), "");
            }
            return Intrinsics.areEqual(symbol, this.intrinsics.getAnyConstructorSymbol()) ? IrUtilsKt.irConstructorCall$default(call, this.intrinsics.getJsObjectConstructorSymbol(), false, false, 12, null) : call;
        }
        return call;
    }
}
