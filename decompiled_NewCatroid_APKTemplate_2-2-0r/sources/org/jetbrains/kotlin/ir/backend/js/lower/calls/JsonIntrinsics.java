package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JsonIntrinsics.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/JsonIntrinsics;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/NativeGetterSetterTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "transformFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "doNotIntrinsify", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsonIntrinsics extends NativeGetterSetterTransformer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsonIntrinsics(JsIrBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.NativeGetterSetterTransformer, org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(call.getSymbol().getOwner());
        if (Intrinsics.areEqual(fqNameWhenAvailable, new FqName("kotlin.js.Json.get"))) {
            return transformToIndexedRead(call);
        }
        return Intrinsics.areEqual(fqNameWhenAvailable, new FqName("kotlin.js.Json.set")) ? transformToIndexedWrite(call) : call;
    }
}
