package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;

/* JADX INFO: compiled from: CandidateCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001aJ \u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0006\u0010 \u001a\u00020!J\u0010\u0010\"\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\bH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "resolutionStageRunner", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;)V", "bestGroup", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "candidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "<set-?>", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "currentApplicability", "getCurrentApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "groupNumbers", "isSuccess", "", "()Z", "shouldStopResolve", "getShouldStopResolve", "bestCandidates", "", "consumeCandidate", "group", "candidate", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "newDataSet", "", "shouldStopAtTheGroup", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CandidateCollector {
    private TowerGroup bestGroup;
    private final List<Candidate> candidates;
    private final BodyResolveComponents components;
    private CandidateApplicability currentApplicability;
    private final List<TowerGroup> groupNumbers;
    private final ResolutionStageRunner resolutionStageRunner;

    public CandidateCollector(BodyResolveComponents components, ResolutionStageRunner resolutionStageRunner) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(resolutionStageRunner, "resolutionStageRunner");
        this.components = components;
        this.resolutionStageRunner = resolutionStageRunner;
        this.groupNumbers = new ArrayList();
        this.candidates = new ArrayList();
        this.currentApplicability = CandidateApplicability.HIDDEN;
        this.bestGroup = TowerGroup.INSTANCE.getLast();
    }

    public final BodyResolveComponents getComponents() {
        return this.components;
    }

    public final CandidateApplicability getCurrentApplicability() {
        return this.currentApplicability;
    }

    public final void newDataSet() {
        this.groupNumbers.clear();
        this.candidates.clear();
        this.currentApplicability = CandidateApplicability.HIDDEN;
        this.bestGroup = TowerGroup.INSTANCE.getLast();
    }

    public CandidateApplicability consumeCandidate(TowerGroup group, Candidate candidate, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(context, "context");
        CandidateApplicability candidateApplicabilityProcessCandidate$default = ResolutionStageRunner.processCandidate$default(this.resolutionStageRunner, candidate, context, false, 4, null);
        if (candidateApplicabilityProcessCandidate$default.compareTo(this.currentApplicability) > 0 || (candidateApplicabilityProcessCandidate$default == this.currentApplicability && group.compareTo(this.bestGroup) < 0)) {
            if (candidateApplicabilityProcessCandidate$default.compareTo(CandidateApplicability.RESOLVED_LOW_PRIORITY) >= 0) {
                this.candidates.clear();
            }
            this.currentApplicability = candidateApplicabilityProcessCandidate$default;
            this.bestGroup = group;
        }
        if (candidateApplicabilityProcessCandidate$default == this.currentApplicability && Intrinsics.areEqual(group, this.bestGroup)) {
            this.candidates.add(candidate);
        }
        return candidateApplicabilityProcessCandidate$default;
    }

    public final List<Candidate> bestCandidates() {
        return this.candidates;
    }

    public boolean shouldStopAtTheGroup(TowerGroup group) {
        Intrinsics.checkNotNullParameter(group, "group");
        return getShouldStopResolve() && this.bestGroup.compareTo(group) < 0;
    }

    public final boolean getShouldStopResolve() {
        return CandidateApplicabilityKt.getShouldStopResolve(this.currentApplicability);
    }

    public final boolean isSuccess() {
        return CandidateApplicabilityKt.isSuccess(this.currentApplicability);
    }
}
