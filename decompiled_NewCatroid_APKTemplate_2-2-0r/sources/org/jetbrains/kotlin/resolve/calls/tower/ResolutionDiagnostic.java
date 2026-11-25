package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "candidateApplicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)V", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolutionDiagnostic extends KotlinCallDiagnostic {
    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResolutionDiagnostic(CandidateApplicability candidateApplicability) {
        super(candidateApplicability);
        Intrinsics.checkNotNullParameter(candidateApplicability, "candidateApplicability");
    }
}
