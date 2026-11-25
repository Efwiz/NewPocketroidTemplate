package org.jetbrains.kotlin.resolve.calls.model;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/AllCandidatesResolutionResult;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "allCandidates", "", "Lorg/jetbrains/kotlin/resolve/calls/model/CandidateWithDiagnostics;", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;)V", "getAllCandidates", "()Ljava/util/Collection;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AllCandidatesResolutionResult extends CallResolutionResult {
    private final Collection<CandidateWithDiagnostics> allCandidates;

    public final Collection<CandidateWithDiagnostics> getAllCandidates() {
        return this.allCandidates;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AllCandidatesResolutionResult(Collection<CandidateWithDiagnostics> allCandidates, NewConstraintSystem constraintSystem) {
        super(null, CollectionsKt.emptyList(), constraintSystem, null);
        Intrinsics.checkNotNullParameter(allCandidates, "allCandidates");
        Intrinsics.checkNotNullParameter(constraintSystem, "constraintSystem");
        this.allCandidates = allCandidates;
    }
}
