package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeInapplicableCandidateError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithSingleCandidate;", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;)V", "getApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "getCandidate", "()Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeInapplicableCandidateError implements ConeDiagnosticWithSingleCandidate {
    private final CandidateApplicability applicability;
    private final AbstractCandidate candidate;

    public ConeInapplicableCandidateError(CandidateApplicability applicability, AbstractCandidate candidate) {
        Intrinsics.checkNotNullParameter(applicability, "applicability");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        this.applicability = applicability;
        this.candidate = candidate;
    }

    public final CandidateApplicability getApplicability() {
        return this.applicability;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSingleCandidate
    public AbstractCandidate getCandidate() {
        return this.candidate;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Inapplicable(" + this.applicability + "): " + ConeDiagnosticsKt.describeSymbol(getCandidateSymbol());
    }
}
