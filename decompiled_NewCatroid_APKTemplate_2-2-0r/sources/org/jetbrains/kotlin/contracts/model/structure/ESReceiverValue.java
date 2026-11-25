package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESReceiverValue;", "Lorg/jetbrains/kotlin/contracts/model/structure/AbstractESValue;", "Lorg/jetbrains/kotlin/contracts/model/structure/ESReceiver;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;)V", "getReceiverValue", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ESReceiverValue extends AbstractESValue implements ESReceiver {
    private final ReceiverValue receiverValue;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ESReceiverValue(ReceiverValue receiverValue) {
        super(null);
        Intrinsics.checkNotNullParameter(receiverValue, "receiverValue");
        this.receiverValue = receiverValue;
    }

    @Override // org.jetbrains.kotlin.contracts.model.structure.ESReceiver
    public ReceiverValue getReceiverValue() {
        return this.receiverValue;
    }
}
