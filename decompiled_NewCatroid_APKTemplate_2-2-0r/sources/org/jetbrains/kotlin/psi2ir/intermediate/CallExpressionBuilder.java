package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\bà\u0080\u0001\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/CallExpressionBuilder;", "", "withReceivers", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "dispatchReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "extensionReceiver", "contextReceivers", "", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CallExpressionBuilder {
    IrExpression withReceivers(IntermediateValue dispatchReceiver, IntermediateValue extensionReceiver, List<? extends IntermediateValue> contextReceivers);
}
