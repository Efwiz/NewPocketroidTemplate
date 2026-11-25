package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.scopes.impl.FirIntegerConstantOperatorScopeKt;

/* JADX INFO: compiled from: ConeIntegerOperatorConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ConeIntegerOperatorConflictResolver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "()V", "chooseMaximallySpecificCandidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "candidates", "discriminateAbstracts", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeIntegerOperatorConflictResolver extends ConeCallConflictResolver {
    public static final ConeIntegerOperatorConflictResolver INSTANCE = new ConeIntegerOperatorConflictResolver();

    private ConeIntegerOperatorConflictResolver() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolver
    public Set<Candidate> chooseMaximallySpecificCandidates(Set<Candidate> candidates, boolean discriminateAbstracts) {
        Object next;
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        if (candidates.size() <= 1) {
            return candidates;
        }
        Iterator<T> it = candidates.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!FirIntegerConstantOperatorScopeKt.isWrappedIntegerOperator(((Candidate) next).getSymbol()));
        Candidate candidate = (Candidate) next;
        return candidate != null ? SetsKt.setOf(candidate) : candidates;
    }
}
