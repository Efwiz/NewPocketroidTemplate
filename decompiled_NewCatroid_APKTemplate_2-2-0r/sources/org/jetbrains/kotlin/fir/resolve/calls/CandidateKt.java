package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Candidate.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"fullyAnalyzed", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getFullyAnalyzed", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)Z", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CandidateKt {
    public static final boolean getFullyAnalyzed(Candidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        return candidate.getPassedStages() == candidate.getCallInfo().getCallKind().getResolutionSequence().size();
    }
}
