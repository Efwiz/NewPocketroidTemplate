package org.jetbrains.kotlin.resolve.calls.results;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: loaded from: classes6.dex */
public class OverloadResolutionResultsImpl<D extends CallableDescriptor> implements OverloadResolutionResults<D> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Collection<ResolvedCall<D>> allCandidates;
    private final OverloadResolutionResults.Code resultCode;
    private final Collection<MutableResolvedCall<D>> results;
    private DelegatingBindingTrace trace;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "resultCode";
                break;
            case 2:
                objArr[0] = "results";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl";
                break;
            default:
                objArr[0] = "candidate";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getResultingCalls";
                break;
            case 4:
                objArr[1] = "getResultingCall";
                break;
            case 5:
                objArr[1] = "getResultingDescriptor";
                break;
            case 6:
                objArr[1] = "getResultCode";
                break;
            case 7:
            case 8:
                objArr[1] = "changeStatusToSuccess";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "<init>";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                break;
            default:
                objArr[2] = FirebaseAnalytics.Param.SUCCESS;
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> success(MutableResolvedCall<D> mutableResolvedCall) {
        if (mutableResolvedCall == null) {
            $$$reportNull$$$0(0);
        }
        return new OverloadResolutionResultsImpl<>(OverloadResolutionResults.Code.SUCCESS, Collections.singleton(mutableResolvedCall));
    }

    public static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> nameNotFound() {
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImpl = new OverloadResolutionResultsImpl<>(OverloadResolutionResults.Code.NAME_NOT_FOUND, Collections.emptyList());
        overloadResolutionResultsImpl.setAllCandidates(Collections.emptyList());
        return overloadResolutionResultsImpl;
    }

    public static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> singleFailedCandidate(MutableResolvedCall<D> mutableResolvedCall) {
        return new OverloadResolutionResultsImpl<>(OverloadResolutionResults.Code.SINGLE_CANDIDATE_ARGUMENT_MISMATCH, Collections.singleton(mutableResolvedCall));
    }

    public static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> manyFailedCandidates(Collection<MutableResolvedCall<D>> collection) {
        return new OverloadResolutionResultsImpl<>(OverloadResolutionResults.Code.MANY_FAILED_CANDIDATES, collection);
    }

    public static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> candidatesWithWrongReceiver(Collection<MutableResolvedCall<D>> collection) {
        return new OverloadResolutionResultsImpl<>(OverloadResolutionResults.Code.CANDIDATES_WITH_WRONG_RECEIVER, collection);
    }

    public static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> ambiguity(Collection<MutableResolvedCall<D>> collection) {
        return new OverloadResolutionResultsImpl<>(OverloadResolutionResults.Code.AMBIGUITY, collection);
    }

    public static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> incompleteTypeInference(Collection<MutableResolvedCall<D>> collection) {
        return new OverloadResolutionResultsImpl<>(OverloadResolutionResults.Code.INCOMPLETE_TYPE_INFERENCE, collection);
    }

    public static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> incompleteTypeInference(MutableResolvedCall<D> mutableResolvedCall) {
        return incompleteTypeInference(Collections.singleton(mutableResolvedCall));
    }

    private OverloadResolutionResultsImpl(OverloadResolutionResults.Code code, Collection<MutableResolvedCall<D>> collection) {
        if (code == null) {
            $$$reportNull$$$0(1);
        }
        if (collection == null) {
            $$$reportNull$$$0(2);
        }
        this.results = collection;
        this.resultCode = code;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public Collection<MutableResolvedCall<D>> getResultingCalls() {
        Collection<MutableResolvedCall<D>> collection = this.results;
        if (collection == null) {
            $$$reportNull$$$0(3);
        }
        return collection;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    /* JADX INFO: renamed from: getResultingCall */
    public MutableResolvedCall<D> mo7436getResultingCall() {
        MutableResolvedCall<D> next = this.results.iterator().next();
        if (next == null) {
            $$$reportNull$$$0(4);
        }
        return next;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    /* JADX INFO: renamed from: getResultingDescriptor */
    public D mo7437getResultingDescriptor() {
        D resultingDescriptor = mo7436getResultingCall().getResultingDescriptor();
        if (resultingDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        return resultingDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public OverloadResolutionResults.Code getResultCode() {
        OverloadResolutionResults.Code code = this.resultCode;
        if (code == null) {
            $$$reportNull$$$0(6);
        }
        return code;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isSuccess() {
        return this.resultCode.isSuccess();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isSingleResult() {
        return this.results.size() == 1 && getResultCode() != OverloadResolutionResults.Code.CANDIDATES_WITH_WRONG_RECEIVER;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isNothing() {
        return this.resultCode == OverloadResolutionResults.Code.NAME_NOT_FOUND;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isAmbiguity() {
        return this.resultCode == OverloadResolutionResults.Code.AMBIGUITY;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public boolean isIncomplete() {
        return this.resultCode == OverloadResolutionResults.Code.INCOMPLETE_TYPE_INFERENCE;
    }

    public DelegatingBindingTrace getTrace() {
        return this.trace;
    }

    public OverloadResolutionResultsImpl<D> setTrace(DelegatingBindingTrace delegatingBindingTrace) {
        this.trace = delegatingBindingTrace;
        return this;
    }

    public void setAllCandidates(Collection<ResolvedCall<D>> collection) {
        this.allCandidates = collection;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public Collection<ResolvedCall<D>> getAllCandidates() {
        return this.allCandidates;
    }

    public OverloadResolutionResultsImpl<D> changeStatusToSuccess() {
        if (getResultCode() == OverloadResolutionResults.Code.SUCCESS) {
            return this;
        }
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImpl = new OverloadResolutionResultsImpl<>(OverloadResolutionResults.Code.SUCCESS, getResultingCalls());
        overloadResolutionResultsImpl.setAllCandidates(getAllCandidates());
        return overloadResolutionResultsImpl;
    }
}
