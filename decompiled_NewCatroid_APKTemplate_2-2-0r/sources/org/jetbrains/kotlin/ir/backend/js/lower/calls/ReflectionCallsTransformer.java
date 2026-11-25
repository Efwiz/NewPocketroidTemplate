package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ReflectionCallsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0002J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0005\u001a\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/ReflectionCallsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "nameToTransformer", "", "Lorg/jetbrains/kotlin/name/Name;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "buildDynamicCall", "name", "", "call", "transformFunctionAccess", "doNotIntrinsify", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectionCallsTransformer implements CallsTransformer {
    private final JsIrBackendContext context;
    private final Map<Name, Function1<IrFunctionAccessExpression, IrExpression>> nameToTransformer;

    public ReflectionCallsTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        HashMap map = new HashMap();
        this.nameToTransformer = map;
        HashMap map2 = map;
        Name nameSpecial = Name.special(Namer.INSTANCE.getKCALLABLE_GET_NAME());
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        CallsLoweringUtilsKt.addWithPredicate(map2, nameSpecial, new Function1<IrFunctionAccessExpression, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.ReflectionCallsTransformer$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrFunctionAccessExpression call) {
                boolean zIsSubtypeOfClass;
                Intrinsics.checkNotNullParameter(call, "call");
                boolean z = false;
                if (!Intrinsics.areEqual(call.getOrigin(), JsStatementOrigins.CALLABLE_REFERENCE_INVOKE.INSTANCE)) {
                    IrValueParameter dispatchReceiverParameter = call.getSymbol().getOwner().getDispatchReceiverParameter();
                    if (dispatchReceiverParameter != null) {
                        zIsSubtypeOfClass = IrTypeUtilsKt.isSubtypeOfClass(dispatchReceiverParameter.getType(), this.this$0.context.getIrBuiltIns().getKCallableClass());
                    } else {
                        zIsSubtypeOfClass = false;
                    }
                    if (zIsSubtypeOfClass) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }, new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.ReflectionCallsTransformer$1$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                int startOffset = call.getStartOffset();
                int endOffset = call.getEndOffset();
                IrType stringType = this.this$0.context.getIrBuiltIns().getStringType();
                String kcallable_name = Namer.INSTANCE.getKCALLABLE_NAME();
                IrExpression dispatchReceiver = call.getDispatchReceiver();
                Intrinsics.checkNotNull(dispatchReceiver);
                return new IrDynamicMemberExpressionImpl(startOffset, endOffset, stringType, kcallable_name, dispatchReceiver);
            }
        });
        Name nameIdentifier = Name.identifier(Namer.INSTANCE.getKPROPERTY_GET());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        CallsLoweringUtilsKt.addWithPredicate(map2, nameIdentifier, new Function1<IrFunctionAccessExpression, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.ReflectionCallsTransformer$1$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrFunctionAccessExpression call) {
                boolean zIsSubtypeOfClass;
                Intrinsics.checkNotNullParameter(call, "call");
                IrValueParameter dispatchReceiverParameter = call.getSymbol().getOwner().getDispatchReceiverParameter();
                if (dispatchReceiverParameter != null) {
                    zIsSubtypeOfClass = IrTypeUtilsKt.isSubtypeOfClass(dispatchReceiverParameter.getType(), this.this$0.context.getIrBuiltIns().getKPropertyClass());
                } else {
                    zIsSubtypeOfClass = false;
                }
                return Boolean.valueOf(zIsSubtypeOfClass);
            }
        }, new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.ReflectionCallsTransformer$1$4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                return this.this$0.buildDynamicCall(Namer.INSTANCE.getKPROPERTY_GET(), call);
            }
        });
        Name nameIdentifier2 = Name.identifier(Namer.INSTANCE.getKPROPERTY_SET());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        CallsLoweringUtilsKt.addWithPredicate(map2, nameIdentifier2, new Function1<IrFunctionAccessExpression, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.ReflectionCallsTransformer$1$5
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrFunctionAccessExpression call) {
                boolean zIsSubtypeOfClass;
                Intrinsics.checkNotNullParameter(call, "call");
                IrValueParameter dispatchReceiverParameter = call.getSymbol().getOwner().getDispatchReceiverParameter();
                if (dispatchReceiverParameter != null) {
                    zIsSubtypeOfClass = IrTypeUtilsKt.isSubtypeOfClass(dispatchReceiverParameter.getType(), this.this$0.context.getIrBuiltIns().getKPropertyClass());
                } else {
                    zIsSubtypeOfClass = false;
                }
                return Boolean.valueOf(zIsSubtypeOfClass);
            }
        }, new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.ReflectionCallsTransformer$1$6
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                return this.this$0.buildDynamicCall(Namer.INSTANCE.getKPROPERTY_SET(), call);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression buildDynamicCall(String name2, IrFunctionAccessExpression call) {
        int startOffset = call.getStartOffset();
        int endOffset = call.getEndOffset();
        IrDynamicType dynamicType = this.context.getDynamicType();
        IrExpression dispatchReceiver = call.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        IrDynamicMemberExpressionImpl irDynamicMemberExpressionImpl = new IrDynamicMemberExpressionImpl(startOffset, endOffset, dynamicType, name2, dispatchReceiver);
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(call.getStartOffset(), call.getEndOffset(), call.getType(), IrDynamicOperator.INVOKE);
        irDynamicOperatorExpressionImpl.setReceiver(irDynamicMemberExpressionImpl);
        int valueArgumentsCount = call.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            List<IrExpression> arguments = irDynamicOperatorExpressionImpl.getArguments();
            IrExpression valueArgument = call.getValueArgument(i);
            Intrinsics.checkNotNull(valueArgument);
            arguments.add(valueArgument);
        }
        return irDynamicOperatorExpressionImpl;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        Function1<IrFunctionAccessExpression, IrExpression> function1 = this.nameToTransformer.get(call.getSymbol().getOwner().getName());
        return function1 != null ? function1.invoke(call) : call;
    }
}
