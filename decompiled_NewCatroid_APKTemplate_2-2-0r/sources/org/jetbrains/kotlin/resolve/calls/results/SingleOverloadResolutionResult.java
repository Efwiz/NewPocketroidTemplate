package org.jetbrains.kotlin.resolve.calls.results;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: NewOverloadResolutionResults.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0018\u00010\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0016J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\nH\u0016J\r\u0010\u000f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/SingleOverloadResolutionResult;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/results/AbstractOverloadResolutionResults;", "result", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "getResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getAllCandidates", "", "getResultCode", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults$Code;", "getResultingCall", "getResultingCalls", "getResultingDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SingleOverloadResolutionResult<D extends CallableDescriptor> extends AbstractOverloadResolutionResults<D> {
    private final ResolvedCall<D> result;

    /* JADX INFO: compiled from: NewOverloadResolutionResults.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ResolutionStatus.values().length];
            try {
                iArr[ResolutionStatus.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ResolutionStatus.RECEIVER_TYPE_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ResolutionStatus.INCOMPLETE_TYPE_INFERENCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public Collection<ResolvedCall<D>> getAllCandidates() {
        return null;
    }

    public SingleOverloadResolutionResult(ResolvedCall<D> result) {
        Intrinsics.checkNotNullParameter(result, "result");
        this.result = result;
    }

    public final ResolvedCall<D> getResult() {
        return this.result;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public Collection<ResolvedCall<D>> getResultingCalls() {
        return CollectionsKt.listOf(this.result);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    /* JADX INFO: renamed from: getResultingCall */
    public ResolvedCall<D> mo7436getResultingCall() {
        return this.result;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    /* JADX INFO: renamed from: getResultingDescriptor */
    public D mo7437getResultingDescriptor() {
        D d = (D) this.result.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(d, "getResultingDescriptor(...)");
        return d;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public OverloadResolutionResults.Code getResultCode() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.result.getStatus().ordinal()];
        if (i == 1) {
            return OverloadResolutionResults.Code.SUCCESS;
        }
        if (i == 2) {
            return OverloadResolutionResults.Code.CANDIDATES_WITH_WRONG_RECEIVER;
        }
        if (i == 3) {
            return OverloadResolutionResults.Code.INCOMPLETE_TYPE_INFERENCE;
        }
        return OverloadResolutionResults.Code.SINGLE_CANDIDATE_ARGUMENT_MISMATCH;
    }
}
