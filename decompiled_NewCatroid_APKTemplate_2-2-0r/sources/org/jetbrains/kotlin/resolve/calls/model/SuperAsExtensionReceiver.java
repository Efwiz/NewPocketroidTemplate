package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/SuperAsExtensionReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "receiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "(Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;)V", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuperAsExtensionReceiver extends KotlinCallDiagnostic {
    private final SimpleKotlinCallArgument receiver;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuperAsExtensionReceiver(SimpleKotlinCallArgument receiver) {
        super(CandidateApplicability.K1_RUNTIME_ERROR);
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        this.receiver = receiver;
    }

    public final SimpleKotlinCallArgument getReceiver() {
        return this.receiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallReceiver(this.receiver, this);
    }
}
