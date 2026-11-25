package org.jetbrains.kotlin.resolve.calls.results;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;

/* JADX INFO: compiled from: NewOverloadResolutionResults.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/AbstractOverloadResolutionResults;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "()V", "isAmbiguity", "", "isIncomplete", "isNothing", "isSingleResult", "isSuccess", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractOverloadResolutionResults<D extends CallableDescriptor> implements OverloadResolutionResults<D> {
    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isSuccess() {
        return getResultCode().isSuccess();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isSingleResult() {
        return getResultingCalls().size() == 1 && getResultCode() != OverloadResolutionResults.Code.CANDIDATES_WITH_WRONG_RECEIVER;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isNothing() {
        return getResultCode() == OverloadResolutionResults.Code.NAME_NOT_FOUND;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isAmbiguity() {
        return getResultCode() == OverloadResolutionResults.Code.AMBIGUITY;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isIncomplete() {
        return getResultCode() == OverloadResolutionResults.Code.INCOMPLETE_TYPE_INFERENCE;
    }
}
