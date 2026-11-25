package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeConstraintSystemHasContradiction;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithSingleCandidate;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;)V", "getCandidate", "()Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "candidateSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getCandidateSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeConstraintSystemHasContradiction implements ConeDiagnosticWithSingleCandidate {
    private final AbstractCandidate candidate;

    public ConeConstraintSystemHasContradiction(AbstractCandidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        this.candidate = candidate;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSingleCandidate
    public AbstractCandidate getCandidate() {
        return this.candidate;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "CS errors: " + ConeDiagnosticsKt.describeSymbol(getCandidateSymbol());
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSingleCandidate
    public FirBasedSymbol<?> getCandidateSymbol() {
        return getCandidate().getSymbol();
    }
}
