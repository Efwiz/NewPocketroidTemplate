package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CandidateApplicability.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003¨\u0006\u0006"}, d2 = {"isSuccess", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)Z", "shouldStopResolve", "getShouldStopResolve", "resolution.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CandidateApplicabilityKt {
    public static final boolean isSuccess(CandidateApplicability candidateApplicability) {
        Intrinsics.checkNotNullParameter(candidateApplicability, "<this>");
        return candidateApplicability.compareTo(CandidateApplicability.RESOLVED_LOW_PRIORITY) >= 0 && candidateApplicability != CandidateApplicability.RESOLVED_WITH_ERROR;
    }

    public static final boolean getShouldStopResolve(CandidateApplicability candidateApplicability) {
        Intrinsics.checkNotNullParameter(candidateApplicability, "<this>");
        return candidateApplicability.compareTo(CandidateApplicability.K2_SYNTHETIC_RESOLVED) >= 0;
    }
}
