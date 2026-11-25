package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirCallResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/OverloadCandidate;", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "isInBestCandidates", "", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Z)V", "getCandidate", "()Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class OverloadCandidate {
    private final Candidate candidate;
    private final boolean isInBestCandidates;

    public static /* synthetic */ OverloadCandidate copy$default(OverloadCandidate overloadCandidate, Candidate candidate, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            candidate = overloadCandidate.candidate;
        }
        if ((i & 2) != 0) {
            z = overloadCandidate.isInBestCandidates;
        }
        return overloadCandidate.copy(candidate, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Candidate getCandidate() {
        return this.candidate;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsInBestCandidates() {
        return this.isInBestCandidates;
    }

    public final OverloadCandidate copy(Candidate candidate, boolean isInBestCandidates) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return new OverloadCandidate(candidate, isInBestCandidates);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OverloadCandidate)) {
            return false;
        }
        OverloadCandidate overloadCandidate = (OverloadCandidate) other;
        return Intrinsics.areEqual(this.candidate, overloadCandidate.candidate) && this.isInBestCandidates == overloadCandidate.isInBestCandidates;
    }

    public int hashCode() {
        return (this.candidate.hashCode() * 31) + Boolean.hashCode(this.isInBestCandidates);
    }

    public String toString() {
        return "OverloadCandidate(candidate=" + this.candidate + ", isInBestCandidates=" + this.isInBestCandidates + ')';
    }

    public OverloadCandidate(Candidate candidate, boolean z) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        this.candidate = candidate;
        this.isInBestCandidates = z;
    }

    public final Candidate getCandidate() {
        return this.candidate;
    }

    public final boolean isInBestCandidates() {
        return this.isInBestCandidates;
    }
}
