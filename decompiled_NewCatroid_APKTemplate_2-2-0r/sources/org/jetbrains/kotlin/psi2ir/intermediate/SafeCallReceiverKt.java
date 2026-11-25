package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorWithScope;

/* JADX INFO: compiled from: SafeCallReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a8\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\bH\u0000¨\u0006\t"}, d2 = {"safeCallOnDispatchReceiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "generator", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;", "startOffset", "", "endOffset", "ifNotNull", "Lkotlin/Function1;", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SafeCallReceiverKt {
    public static final IrExpression safeCallOnDispatchReceiver(IrExpression irExpression, GeneratorWithScope generator, int i, int i2, final Function1<? super IrExpression, ? extends IrExpression> ifNotNull) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(generator, "generator");
        Intrinsics.checkNotNullParameter(ifNotNull, "ifNotNull");
        return new SafeCallReceiver(generator, i, i2, null, CollectionsKt.emptyList(), new OnceExpressionValue(irExpression), false).call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.SafeCallReceiverKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return SafeCallReceiverKt.safeCallOnDispatchReceiver$lambda$1(ifNotNull, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression safeCallOnDispatchReceiver$lambda$1(Function1 ifNotNull, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        Intrinsics.checkNotNullParameter(ifNotNull, "$ifNotNull");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        contextReceiverValues.isEmpty();
        Intrinsics.checkNotNull(intermediateValue);
        return (IrExpression) ifNotNull.invoke(intermediateValue.load());
    }
}
