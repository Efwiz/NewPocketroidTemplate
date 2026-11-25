package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: StateMachineBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005H\u0016R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/DispatchPointTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "action", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lkotlin/jvm/functions/Function1;)V", "getAction", "()Lkotlin/jvm/functions/Function1;", "visitExpression", "expression", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DispatchPointTransformer extends IrElementTransformerVoid {
    private final Function1<SuspendState, IrExpression> action;

    /* JADX WARN: Multi-variable type inference failed */
    public DispatchPointTransformer(Function1<? super SuspendState, ? extends IrExpression> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        this.action = action;
    }

    public final Function1<SuspendState, IrExpression> getAction() {
        return this.action;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitExpression(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrDispatchPoint irDispatchPoint = expression instanceof IrDispatchPoint ? (IrDispatchPoint) expression : null;
        if (irDispatchPoint == null) {
            return super.visitExpression(expression);
        }
        return this.action.invoke(irDispatchPoint.getTarget());
    }
}
