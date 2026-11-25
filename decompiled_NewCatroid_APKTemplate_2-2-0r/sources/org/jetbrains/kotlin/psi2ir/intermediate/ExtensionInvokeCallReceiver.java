package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: ExtensionInvokeCallReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/ExtensionInvokeCallReceiver;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "callBuilder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "functionReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "extensionInvokeReceiver", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;)V", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallExpressionBuilder;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExtensionInvokeCallReceiver implements CallReceiver {
    private final CallBuilder callBuilder;
    private final IntermediateValue extensionInvokeReceiver;
    private final IntermediateValue functionReceiver;

    public ExtensionInvokeCallReceiver(CallBuilder callBuilder, IntermediateValue functionReceiver, IntermediateValue extensionInvokeReceiver) {
        Intrinsics.checkNotNullParameter(callBuilder, "callBuilder");
        Intrinsics.checkNotNullParameter(functionReceiver, "functionReceiver");
        Intrinsics.checkNotNullParameter(extensionInvokeReceiver, "extensionInvokeReceiver");
        this.callBuilder = callBuilder;
        this.functionReceiver = functionReceiver;
        this.extensionInvokeReceiver = extensionInvokeReceiver;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver
    public IrExpression call(CallExpressionBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrExpression irExpression = this.callBuilder.getIrValueArgumentsByIndex()[0];
        this.callBuilder.getIrValueArgumentsByIndex()[0] = this.extensionInvokeReceiver.load();
        return builder.withReceivers(this.functionReceiver, null, CollectionsKt.emptyList());
    }
}
