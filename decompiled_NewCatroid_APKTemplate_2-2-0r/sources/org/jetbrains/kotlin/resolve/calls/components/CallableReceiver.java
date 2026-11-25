package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: CallableReferenceResolution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0004\u000b\f\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "BoundValueReference", "ExplicitValueReceiver", "ScopeReceiver", "UnboundReference", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver$BoundValueReference;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver$ExplicitValueReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver$ScopeReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver$UnboundReference;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallableReceiver {
    private final ReceiverValueWithSmartCastInfo receiver;

    public /* synthetic */ CallableReceiver(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, DefaultConstructorMarker defaultConstructorMarker) {
        this(receiverValueWithSmartCastInfo);
    }

    /* JADX INFO: compiled from: CallableReferenceResolution.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver$UnboundReference;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnboundReference extends CallableReceiver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UnboundReference(ReceiverValueWithSmartCastInfo receiver) {
            super(receiver, null);
            Intrinsics.checkNotNullParameter(receiver, "receiver");
        }
    }

    private CallableReceiver(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
        this.receiver = receiverValueWithSmartCastInfo;
    }

    public final ReceiverValueWithSmartCastInfo getReceiver() {
        return this.receiver;
    }

    /* JADX INFO: compiled from: CallableReferenceResolution.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver$BoundValueReference;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BoundValueReference extends CallableReceiver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BoundValueReference(ReceiverValueWithSmartCastInfo receiver) {
            super(receiver, null);
            Intrinsics.checkNotNullParameter(receiver, "receiver");
        }
    }

    /* JADX INFO: compiled from: CallableReferenceResolution.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver$ScopeReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ScopeReceiver extends CallableReceiver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ScopeReceiver(ReceiverValueWithSmartCastInfo receiver) {
            super(receiver, null);
            Intrinsics.checkNotNullParameter(receiver, "receiver");
        }
    }

    /* JADX INFO: compiled from: CallableReferenceResolution.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver$ExplicitValueReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExplicitValueReceiver extends CallableReceiver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ExplicitValueReceiver(ReceiverValueWithSmartCastInfo receiver) {
            super(receiver, null);
            Intrinsics.checkNotNullParameter(receiver, "receiver");
        }
    }
}
