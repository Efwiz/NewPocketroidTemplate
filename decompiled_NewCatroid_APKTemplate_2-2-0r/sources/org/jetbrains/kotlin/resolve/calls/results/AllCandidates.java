package org.jetbrains.kotlin.resolve.calls.results;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: NewOverloadResolutionResults.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0014\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005H\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/AllCandidates;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/results/NameNotFoundResolutionResult;", "allCandidates", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "(Ljava/util/Collection;)V", "getAllCandidates", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AllCandidates<D extends CallableDescriptor> extends NameNotFoundResolutionResult<D> {
    private final Collection<ResolvedCall<D>> allCandidates;

    /* JADX WARN: Multi-variable type inference failed */
    public AllCandidates(Collection<? extends ResolvedCall<D>> allCandidates) {
        Intrinsics.checkNotNullParameter(allCandidates, "allCandidates");
        this.allCandidates = allCandidates;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.NameNotFoundResolutionResult, org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public Collection<ResolvedCall<D>> getAllCandidates() {
        return this.allCandidates;
    }
}
