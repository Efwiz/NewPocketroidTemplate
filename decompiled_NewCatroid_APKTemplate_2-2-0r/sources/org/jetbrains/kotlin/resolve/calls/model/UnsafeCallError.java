package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/UnsafeCallError;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "receiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "isForImplicitInvoke", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;Z)V", "()Z", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsafeCallError extends KotlinCallDiagnostic {
    private final boolean isForImplicitInvoke;
    private final SimpleKotlinCallArgument receiver;

    public /* synthetic */ UnsafeCallError(SimpleKotlinCallArgument simpleKotlinCallArgument, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(simpleKotlinCallArgument, (i & 2) != 0 ? false : z);
    }

    public final SimpleKotlinCallArgument getReceiver() {
        return this.receiver;
    }

    /* JADX INFO: renamed from: isForImplicitInvoke, reason: from getter */
    public final boolean getIsForImplicitInvoke() {
        return this.isForImplicitInvoke;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnsafeCallError(SimpleKotlinCallArgument receiver, boolean z) {
        super(CandidateApplicability.UNSAFE_CALL);
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        this.receiver = receiver;
        this.isForImplicitInvoke = z;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallReceiver(this.receiver, this);
    }
}
