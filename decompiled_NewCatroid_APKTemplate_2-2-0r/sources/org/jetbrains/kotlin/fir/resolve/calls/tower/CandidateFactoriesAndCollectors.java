package org.jetbrains.kotlin.fir.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;

/* JADX INFO: compiled from: TowerLevelHandler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/CandidateFactoriesAndCollectors;", "", "candidateFactory", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "resultCollector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;)V", "getCandidateFactory", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "getResultCollector", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CandidateFactoriesAndCollectors {
    private final CandidateFactory candidateFactory;
    private final CandidateCollector resultCollector;

    public CandidateFactoriesAndCollectors(CandidateFactory candidateFactory, CandidateCollector resultCollector) {
        Intrinsics.checkNotNullParameter(candidateFactory, "candidateFactory");
        Intrinsics.checkNotNullParameter(resultCollector, "resultCollector");
        this.candidateFactory = candidateFactory;
        this.resultCollector = resultCollector;
    }

    public final CandidateFactory getCandidateFactory() {
        return this.candidateFactory;
    }

    public final CandidateCollector getResultCollector() {
        return this.resultCollector;
    }
}
