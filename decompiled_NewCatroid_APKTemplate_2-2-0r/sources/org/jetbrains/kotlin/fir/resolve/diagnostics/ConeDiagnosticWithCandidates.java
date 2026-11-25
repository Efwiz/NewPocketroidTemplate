package org.jetbrains.kotlin.fir.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithCandidates;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "candidateSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getCandidateSymbols", "()Ljava/util/Collection;", "candidates", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "getCandidates", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConeDiagnosticWithCandidates extends ConeDiagnostic {
    Collection<AbstractCandidate> getCandidates();

    default Collection<FirBasedSymbol<?>> getCandidateSymbols() {
        Collection<AbstractCandidate> candidates = getCandidates();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates, 10));
        Iterator<T> it = candidates.iterator();
        while (it.hasNext()) {
            arrayList.add(((AbstractCandidate) it.next()).getSymbol());
        }
        return arrayList;
    }
}
