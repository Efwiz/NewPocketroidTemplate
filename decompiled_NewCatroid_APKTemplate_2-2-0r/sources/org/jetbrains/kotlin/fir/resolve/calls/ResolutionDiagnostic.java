package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)V", "getApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolutionDiagnostic {
    private final CandidateApplicability applicability;

    public ResolutionDiagnostic(CandidateApplicability applicability) {
        Intrinsics.checkNotNullParameter(applicability, "applicability");
        this.applicability = applicability;
    }

    public final CandidateApplicability getApplicability() {
        return this.applicability;
    }
}
