package org.jetbrains.kotlin.resolve.calls.results;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: NewOverloadResolutionResults.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007\u0018\u00010\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u0006H\u0016J\b\u0010\r\u001a\u00020\u000bH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/NameNotFoundResolutionResult;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/results/AbstractOverloadResolutionResults;", "()V", "getAllCandidates", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getResultCode", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults$Code;", "getResultingCall", "", "getResultingCalls", "getResultingDescriptor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class NameNotFoundResolutionResult<D extends CallableDescriptor> extends AbstractOverloadResolutionResults<D> {
    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public Collection<ResolvedCall<D>> getAllCandidates() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    /* JADX INFO: renamed from: getResultingCall */
    public /* bridge */ /* synthetic */ ResolvedCall mo7436getResultingCall() {
        return (ResolvedCall) getResultingCall();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    /* JADX INFO: renamed from: getResultingDescriptor */
    public /* bridge */ /* synthetic */ CallableDescriptor mo7437getResultingDescriptor() {
        return (CallableDescriptor) getResultingDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public Collection<ResolvedCall<D>> getResultingCalls() {
        return CollectionsKt.emptyList();
    }

    public Void getResultingCall() {
        throw new IllegalStateException("No candidates".toString());
    }

    public Void getResultingDescriptor() {
        throw new IllegalStateException("No candidates".toString());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public OverloadResolutionResults.Code getResultCode() {
        return OverloadResolutionResults.Code.NAME_NOT_FOUND;
    }
}
