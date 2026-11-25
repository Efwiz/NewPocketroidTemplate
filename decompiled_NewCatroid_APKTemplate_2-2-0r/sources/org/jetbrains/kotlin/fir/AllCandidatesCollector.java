package org.jetbrains.kotlin.fir;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner;
import org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: FirCallResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\t0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/AllCandidatesCollector;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "resolutionStageRunner", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;)V", "allCandidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getAllCandidates", "()Ljava/util/Collection;", "allCandidatesMap", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "consumeCandidate", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "group", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "candidate", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "shouldStopAtTheGroup", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AllCandidatesCollector extends CandidateCollector {
    private final Map<FirBasedSymbol<?>, Candidate> allCandidatesMap;

    @Override // org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector
    public boolean shouldStopAtTheGroup(TowerGroup group) {
        Intrinsics.checkNotNullParameter(group, "group");
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AllCandidatesCollector(BodyResolveComponents components, ResolutionStageRunner resolutionStageRunner) {
        super(components, resolutionStageRunner);
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(resolutionStageRunner, "resolutionStageRunner");
        this.allCandidatesMap = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector
    public CandidateApplicability consumeCandidate(TowerGroup group, Candidate candidate, ResolutionContext context) {
        FirConstructor originalConstructorIfTypeAlias;
        FirConstructorSymbol symbol;
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(context, "context");
        FirDeclaration fir = candidate.getSymbol().getFir();
        FirConstructor firConstructor = fir instanceof FirConstructor ? (FirConstructor) fir : null;
        FirConstructorSymbol symbol2 = (firConstructor == null || (originalConstructorIfTypeAlias = DeclarationUtilsKt.getOriginalConstructorIfTypeAlias(firConstructor)) == null || (symbol = originalConstructorIfTypeAlias.getSymbol()) == null) ? candidate.getSymbol() : symbol;
        Map<FirBasedSymbol<?>, Candidate> map = this.allCandidatesMap;
        if (map.get(symbol2) == null) {
            map.put(symbol2, candidate);
        }
        return super.consumeCandidate(group, candidate, context);
    }

    public final Collection<Candidate> getAllCandidates() {
        return this.allCandidatesMap.values();
    }
}
