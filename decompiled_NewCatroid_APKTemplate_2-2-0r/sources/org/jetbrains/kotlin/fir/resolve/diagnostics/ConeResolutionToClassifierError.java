package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeResolutionToClassifierError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithSingleCandidate;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "candidateSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;)V", "getCandidate", "()Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "getCandidateSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeResolutionToClassifierError implements ConeDiagnosticWithSingleCandidate {
    private final AbstractCandidate candidate;
    private final FirRegularClassSymbol candidateSymbol;

    public ConeResolutionToClassifierError(AbstractCandidate candidate, FirRegularClassSymbol candidateSymbol) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(candidateSymbol, "candidateSymbol");
        this.candidate = candidate;
        this.candidateSymbol = candidateSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSingleCandidate
    public AbstractCandidate getCandidate() {
        return this.candidate;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSingleCandidate
    public FirRegularClassSymbol getCandidateSymbol() {
        return this.candidateSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Resolution to classifier";
    }
}
