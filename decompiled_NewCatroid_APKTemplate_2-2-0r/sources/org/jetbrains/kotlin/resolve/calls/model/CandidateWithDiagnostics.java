package org.jetbrains.kotlin.resolve.calls.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CandidateWithDiagnostics;", "", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "(Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;Ljava/util/List;)V", "getCandidate", "()Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "getDiagnostics", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CandidateWithDiagnostics {
    private final ResolutionCandidate candidate;
    private final List<KotlinCallDiagnostic> diagnostics;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CandidateWithDiagnostics copy$default(CandidateWithDiagnostics candidateWithDiagnostics, ResolutionCandidate resolutionCandidate, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            resolutionCandidate = candidateWithDiagnostics.candidate;
        }
        if ((i & 2) != 0) {
            list = candidateWithDiagnostics.diagnostics;
        }
        return candidateWithDiagnostics.copy(resolutionCandidate, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ResolutionCandidate getCandidate() {
        return this.candidate;
    }

    public final List<KotlinCallDiagnostic> component2() {
        return this.diagnostics;
    }

    public final CandidateWithDiagnostics copy(ResolutionCandidate candidate, List<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        return new CandidateWithDiagnostics(candidate, diagnostics);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CandidateWithDiagnostics)) {
            return false;
        }
        CandidateWithDiagnostics candidateWithDiagnostics = (CandidateWithDiagnostics) other;
        return Intrinsics.areEqual(this.candidate, candidateWithDiagnostics.candidate) && Intrinsics.areEqual(this.diagnostics, candidateWithDiagnostics.diagnostics);
    }

    public int hashCode() {
        return (this.candidate.hashCode() * 31) + this.diagnostics.hashCode();
    }

    public String toString() {
        return "CandidateWithDiagnostics(candidate=" + this.candidate + ", diagnostics=" + this.diagnostics + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CandidateWithDiagnostics(ResolutionCandidate candidate, List<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        this.candidate = candidate;
        this.diagnostics = diagnostics;
    }

    public final ResolutionCandidate getCandidate() {
        return this.candidate;
    }

    public final List<KotlinCallDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }
}
