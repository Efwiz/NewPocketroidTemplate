package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConeCompositeConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0003\"\u00020\u0001¢\u0006\u0002\u0010\u0004J$\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0018\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0005¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCompositeConflictResolver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "conflictResolvers", "", "([Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;)V", "[Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "chooseMaximallySpecificCandidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "candidates", "discriminateAbstracts", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeCompositeConflictResolver extends ConeCallConflictResolver {
    private final ConeCallConflictResolver[] conflictResolvers;

    public ConeCompositeConflictResolver(ConeCallConflictResolver... conflictResolvers) {
        Intrinsics.checkNotNullParameter(conflictResolvers, "conflictResolvers");
        this.conflictResolvers = conflictResolvers;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolver
    public Set<Candidate> chooseMaximallySpecificCandidates(Set<Candidate> candidates, boolean discriminateAbstracts) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        if (candidates.size() <= 1) {
            return candidates;
        }
        int i = 0;
        while (candidates.size() > 1) {
            ConeCallConflictResolver[] coneCallConflictResolverArr = this.conflictResolvers;
            if (i >= coneCallConflictResolverArr.length) {
                break;
            }
            candidates = coneCallConflictResolverArr[i].chooseMaximallySpecificCandidates(candidates, discriminateAbstracts);
            i++;
        }
        return candidates;
    }
}
