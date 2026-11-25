package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;

/* JADX INFO: compiled from: NativeGetterSetterTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\f\u0010\r\u001a\u00020\b*\u00020\nH\u0004J\f\u0010\u000e\u001a\u00020\b*\u00020\nH\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/NativeGetterSetterTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "transformFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "doNotIntrinsify", "", "transformToIndexedRead", "transformToIndexedWrite", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class NativeGetterSetterTransformer implements CallsTransformer {
    private final JsIrBackendContext context;

    public NativeGetterSetterTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        IrFunction owner = call.getSymbol().getOwner();
        if (AnnotationUtilsKt.isJsNativeGetter(owner)) {
            return transformToIndexedRead(call);
        }
        return AnnotationUtilsKt.isJsNativeSetter(owner) ? transformToIndexedWrite(call) : call;
    }

    protected final IrExpression transformToIndexedRead(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        IrExpression dispatchReceiver = irFunctionAccessExpression.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = irFunctionAccessExpression.getExtensionReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
        }
        IrExpression valueArgument = irFunctionAccessExpression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(irFunctionAccessExpression.getStartOffset(), irFunctionAccessExpression.getEndOffset(), this.context.getIrBuiltIns().getAnyNType(), IrDynamicOperator.ARRAY_ACCESS);
        irDynamicOperatorExpressionImpl.setReceiver(dispatchReceiver);
        irDynamicOperatorExpressionImpl.getArguments().add(valueArgument);
        return irDynamicOperatorExpressionImpl;
    }

    protected final IrExpression transformToIndexedWrite(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        IrExpression valueArgument = irFunctionAccessExpression.getValueArgument(1);
        Intrinsics.checkNotNull(valueArgument);
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(irFunctionAccessExpression.getStartOffset(), irFunctionAccessExpression.getEndOffset(), irFunctionAccessExpression.getType(), IrDynamicOperator.EQ);
        irDynamicOperatorExpressionImpl.setReceiver(transformToIndexedRead(irFunctionAccessExpression));
        irDynamicOperatorExpressionImpl.getArguments().add(valueArgument);
        return irDynamicOperatorExpressionImpl;
    }
}
