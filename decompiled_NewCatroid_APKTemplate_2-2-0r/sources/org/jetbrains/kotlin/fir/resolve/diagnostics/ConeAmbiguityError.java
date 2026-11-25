package org.jetbrains.kotlin.fir.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeAmbiguityError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithCandidates;", "name", "Lorg/jetbrains/kotlin/name/Name;", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "candidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;Ljava/util/Collection;)V", "getApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "candidateSymbols", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getCandidateSymbols", "()Ljava/util/Collection;", "getCandidates", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeAmbiguityError implements ConeDiagnosticWithCandidates {
    private final CandidateApplicability applicability;
    private final Collection<AbstractCandidate> candidates;
    private final Name name;

    /* JADX WARN: Multi-variable type inference failed */
    public ConeAmbiguityError(Name name2, CandidateApplicability applicability, Collection<? extends AbstractCandidate> candidates) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(applicability, "applicability");
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        this.name = name2;
        this.applicability = applicability;
        this.candidates = candidates;
    }

    public final Name getName() {
        return this.name;
    }

    public final CandidateApplicability getApplicability() {
        return this.applicability;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithCandidates
    public Collection<AbstractCandidate> getCandidates() {
        return this.candidates;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        StringBuilder sbAppend = new StringBuilder("Ambiguity: ").append(this.name).append(", ");
        Collection<FirBasedSymbol<?>> candidateSymbols = getCandidateSymbols();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidateSymbols, 10));
        Iterator<T> it = candidateSymbols.iterator();
        while (it.hasNext()) {
            arrayList.add(ConeDiagnosticsKt.describeSymbol((FirBasedSymbol) it.next()));
        }
        return sbAppend.append(arrayList).toString();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithCandidates
    public Collection<FirBasedSymbol<?>> getCandidateSymbols() {
        Collection<AbstractCandidate> candidates = getCandidates();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates, 10));
        Iterator<T> it = candidates.iterator();
        while (it.hasNext()) {
            arrayList.add(((AbstractCandidate) it.next()).getSymbol());
        }
        return arrayList;
    }
}
