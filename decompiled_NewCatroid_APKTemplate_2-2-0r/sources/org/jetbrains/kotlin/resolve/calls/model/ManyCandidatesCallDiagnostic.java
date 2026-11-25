package org.jetbrains.kotlin.resolve.calls.model;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ManyCandidatesCallDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "candidates", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "(Ljava/util/Collection;)V", "getCandidates", "()Ljava/util/Collection;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ManyCandidatesCallDiagnostic extends KotlinCallDiagnostic {
    private final Collection<ResolutionCandidate> candidates;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ManyCandidatesCallDiagnostic(Collection<? extends ResolutionCandidate> candidates) {
        super(CandidateApplicability.INAPPLICABLE);
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        this.candidates = candidates;
    }

    public final Collection<ResolutionCandidate> getCandidates() {
        return this.candidates;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCall(this);
    }
}
