package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Ljava/util/List;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getDiagnostics", "()Ljava/util/List;", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CandidateWithBoundDispatchReceiver {
    private final CallableDescriptor descriptor;
    private final List<ResolutionDiagnostic> diagnostics;
    private final ReceiverValueWithSmartCastInfo dispatchReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    public CandidateWithBoundDispatchReceiver(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, CallableDescriptor descriptor, List<? extends ResolutionDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        this.dispatchReceiver = receiverValueWithSmartCastInfo;
        this.descriptor = descriptor;
        this.diagnostics = diagnostics;
    }

    public final ReceiverValueWithSmartCastInfo getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public final CallableDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final List<ResolutionDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }
}
