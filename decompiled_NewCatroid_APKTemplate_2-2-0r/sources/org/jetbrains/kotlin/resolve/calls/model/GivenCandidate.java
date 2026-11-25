package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: KotlinResolverContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/GivenCandidate;", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "knownTypeParametersResultingSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;Lorg/jetbrains/kotlin/types/TypeSubstitutor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getKnownTypeParametersResultingSubstitutor", "()Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GivenCandidate {
    private final FunctionDescriptor descriptor;
    private final ReceiverValueWithSmartCastInfo dispatchReceiver;
    private final TypeSubstitutor knownTypeParametersResultingSubstitutor;

    public GivenCandidate(FunctionDescriptor descriptor, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, TypeSubstitutor typeSubstitutor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.descriptor = descriptor;
        this.dispatchReceiver = receiverValueWithSmartCastInfo;
        this.knownTypeParametersResultingSubstitutor = typeSubstitutor;
    }

    public final FunctionDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final ReceiverValueWithSmartCastInfo getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public final TypeSubstitutor getKnownTypeParametersResultingSubstitutor() {
        return this.knownTypeParametersResultingSubstitutor;
    }
}
