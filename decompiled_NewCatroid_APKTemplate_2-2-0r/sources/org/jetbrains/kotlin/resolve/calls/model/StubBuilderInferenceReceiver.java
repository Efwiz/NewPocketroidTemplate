package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/StubBuilderInferenceReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "receiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "extensionReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;)V", "getExtensionReceiverParameter", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StubBuilderInferenceReceiver extends KotlinCallDiagnostic {
    private final ReceiverParameterDescriptor extensionReceiverParameter;
    private final SimpleKotlinCallArgument receiver;

    public final SimpleKotlinCallArgument getReceiver() {
        return this.receiver;
    }

    public final ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return this.extensionReceiverParameter;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StubBuilderInferenceReceiver(SimpleKotlinCallArgument receiver, ReceiverParameterDescriptor extensionReceiverParameter) {
        super(CandidateApplicability.RESOLVED);
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(extensionReceiverParameter, "extensionReceiverParameter");
        this.receiver = receiver;
        this.extensionReceiverParameter = extensionReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallReceiver(this.receiver, this);
    }
}
