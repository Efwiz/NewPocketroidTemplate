package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NewResolutionOldInference.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PreviousResolutionError;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "candidateLevel", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PreviousResolutionError extends ResolutionDiagnostic {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreviousResolutionError(CandidateApplicability candidateLevel) {
        super(candidateLevel);
        Intrinsics.checkNotNullParameter(candidateLevel, "candidateLevel");
    }
}
