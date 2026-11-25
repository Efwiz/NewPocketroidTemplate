package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/UnsupportedContextualDeclarationCall;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "()V", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsupportedContextualDeclarationCall extends ResolutionDiagnostic {
    public UnsupportedContextualDeclarationCall() {
        super(CandidateApplicability.RESOLVED_WITH_ERROR);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ResolutionDiagnostic, org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCall(this);
    }
}
