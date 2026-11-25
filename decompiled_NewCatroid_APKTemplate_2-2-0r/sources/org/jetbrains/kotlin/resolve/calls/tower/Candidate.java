package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;

/* JADX INFO: compiled from: TowerResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0000H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "", "isSuccessful", "", "()Z", "resultingApplicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "getResultingApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "addCompatibilityWarning", "", "other", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Candidate {
    void addCompatibilityWarning(Candidate other);

    CandidateApplicability getResultingApplicability();

    boolean isSuccessful();
}
