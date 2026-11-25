package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConeCallConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\b\b\u0002\u0010\b\u001a\u00020\tJ$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "", "()V", "chooseMaximallySpecificCandidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "candidates", "", "discriminateAbstracts", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeCallConflictResolver {
    public abstract Set<Candidate> chooseMaximallySpecificCandidates(Set<Candidate> candidates, boolean discriminateAbstracts);

    public static /* synthetic */ Set chooseMaximallySpecificCandidates$default(ConeCallConflictResolver coneCallConflictResolver, Collection collection, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: chooseMaximallySpecificCandidates");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return coneCallConflictResolver.chooseMaximallySpecificCandidates((Collection<Candidate>) collection, z);
    }

    public final Set<Candidate> chooseMaximallySpecificCandidates(Collection<Candidate> candidates, boolean discriminateAbstracts) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        return chooseMaximallySpecificCandidates(CollectionsKt.toSet(candidates), discriminateAbstracts);
    }
}
