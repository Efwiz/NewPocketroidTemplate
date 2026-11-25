package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b`\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005H&J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/AssignmentReceiver;", "", "assign", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "withLValue", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValue;", "value", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AssignmentReceiver {
    IrExpression assign(Function1<? super LValue, ? extends IrExpression> withLValue);

    default IrExpression assign(final IrExpression value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return assign(new Function1<LValue, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver.assign.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(LValue it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.store(value);
            }
        });
    }
}
