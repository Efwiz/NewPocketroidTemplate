package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: SimpleCallReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/SimpleCallReceiver;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "dispatchReceiverValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "extensionReceiverValue", "contextReceiverValues", "", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;Ljava/util/List;)V", "getContextReceiverValues", "()Ljava/util/List;", "getDispatchReceiverValue", "()Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "getExtensionReceiverValue", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallExpressionBuilder;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleCallReceiver implements CallReceiver {
    private final List<IntermediateValue> contextReceiverValues;
    private final IntermediateValue dispatchReceiverValue;
    private final IntermediateValue extensionReceiverValue;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleCallReceiver(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List<? extends IntermediateValue> contextReceiverValues) {
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        this.dispatchReceiverValue = intermediateValue;
        this.extensionReceiverValue = intermediateValue2;
        this.contextReceiverValues = contextReceiverValues;
    }

    public final IntermediateValue getDispatchReceiverValue() {
        return this.dispatchReceiverValue;
    }

    public final IntermediateValue getExtensionReceiverValue() {
        return this.extensionReceiverValue;
    }

    public final List<IntermediateValue> getContextReceiverValues() {
        return this.contextReceiverValues;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver
    public IrExpression call(CallExpressionBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        return builder.withReceivers(this.dispatchReceiverValue, this.extensionReceiverValue, this.contextReceiverValues);
    }
}
