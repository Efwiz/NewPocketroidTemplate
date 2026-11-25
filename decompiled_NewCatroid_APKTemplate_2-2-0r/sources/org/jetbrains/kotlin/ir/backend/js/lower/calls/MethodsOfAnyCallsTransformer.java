package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MethodsOfAnyCallsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000bH\u0002J\u0018\u0010\u0010\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0007\u001a\u001a\u0012\u0004\u0012\u00020\t\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/MethodsOfAnyCallsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "intrinsics", "Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "nameToTransformer", "", "Lorg/jetbrains/kotlin/name/Name;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "shouldReplaceToStringWithRuntimeCall", "", "call", "transformFunctionAccess", "doNotIntrinsify", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MethodsOfAnyCallsTransformer implements CallsTransformer {
    private final JsIntrinsics intrinsics;
    private final Map<Name, Function1<IrFunctionAccessExpression, IrExpression>> nameToTransformer;

    public MethodsOfAnyCallsTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.intrinsics = context.getIntrinsics();
        HashMap map = new HashMap();
        this.nameToTransformer = map;
        HashMap map2 = map;
        map2.put(Name.identifier("toString"), new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.MethodsOfAnyCallsTransformer$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                if (!this.this$0.shouldReplaceToStringWithRuntimeCall(call)) {
                    return call;
                }
                return IrUtilsKt.isSuperToAny((IrCall) call) ? IrUtilsKt.irCall(call, this.this$0.intrinsics.getJsAnyToString(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null) : IrUtilsKt.irCall(call, this.this$0.intrinsics.getJsToString(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        Function1<IrFunctionAccessExpression, IrExpression> function1 = this.nameToTransformer.get(call.getSymbol().getOwner().getName());
        return function1 != null ? function1.invoke(call) : call;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldReplaceToStringWithRuntimeCall(IrFunctionAccessExpression call) {
        IrValueParameter dispatchReceiverParameter;
        IrType type;
        IrClassSymbol superQualifierSymbol;
        IrFunction owner = call.getSymbol().getOwner();
        if ((!owner.getValueParameters().isEmpty() && !Intrinsics.areEqual(owner.getName().asString(), "toString")) || owner.getExtensionReceiverParameter() != null) {
            return false;
        }
        if (((call instanceof IrCall) && (superQualifierSymbol = ((IrCall) call).getSuperQualifierSymbol()) != null && !IrUtilsKt.isInterface(superQualifierSymbol.getOwner()) && !Intrinsics.areEqual(superQualifierSymbol, this.intrinsics.getAnyClassSymbol())) || (dispatchReceiverParameter = owner.getDispatchReceiverParameter()) == null || (type = dispatchReceiverParameter.getType()) == null) {
            return false;
        }
        return IrTypePredicatesKt.isArray(type) || IrTypePredicatesKt.isAny(type) || (type instanceof IrDynamicType) || IrTypePredicatesKt.isString(type);
    }
}
