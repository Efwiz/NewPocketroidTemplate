package org.jetbrains.kotlin.fir.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeOperatorAmbiguityError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithCandidates;", "candidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "(Ljava/util/Collection;)V", "getCandidates", "()Ljava/util/Collection;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeOperatorAmbiguityError implements ConeDiagnosticWithCandidates {
    private final Collection<AbstractCandidate> candidates;

    /* JADX WARN: Multi-variable type inference failed */
    public ConeOperatorAmbiguityError(Collection<? extends AbstractCandidate> candidates) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        this.candidates = candidates;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithCandidates
    public Collection<AbstractCandidate> getCandidates() {
        return this.candidates;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        StringBuilder sb = new StringBuilder("Operator overload ambiguity. Compatible candidates: ");
        Collection<FirBasedSymbol<?>> candidateSymbols = getCandidateSymbols();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidateSymbols, 10));
        Iterator<T> it = candidateSymbols.iterator();
        while (it.hasNext()) {
            arrayList.add(ConeDiagnosticsKt.describeSymbol((FirBasedSymbol) it.next()));
        }
        return sb.append(arrayList).toString();
    }
}
