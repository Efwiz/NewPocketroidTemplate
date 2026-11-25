package org.jetbrains.kotlin.resolve.calls.results;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: NewOverloadResolutionResults.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010\n\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0006\u0018\u00010\u0005H\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005H\u0016J\b\u0010\u0010\u001a\u00020\u000eH\u0016R\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/ManyCandidates;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/results/AbstractOverloadResolutionResults;", "candidates", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "(Ljava/util/Collection;)V", "getCandidates", "()Ljava/util/Collection;", "getAllCandidates", "getResultCode", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults$Code;", "getResultingCall", "", "getResultingCalls", "getResultingDescriptor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ManyCandidates<D extends CallableDescriptor> extends AbstractOverloadResolutionResults<D> {
    private final Collection<ResolvedCall<D>> candidates;

    /* JADX INFO: compiled from: NewOverloadResolutionResults.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ResolutionStatus.values().length];
            try {
                iArr[ResolutionStatus.RECEIVER_TYPE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ResolutionStatus.SUCCESS.ordinal()] = 2;
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

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    /* JADX INFO: renamed from: getResultingCall, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ResolvedCall mo7436getResultingCall() {
        return (ResolvedCall) getResultingCall();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    /* JADX INFO: renamed from: getResultingDescriptor, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ CallableDescriptor mo7437getResultingDescriptor() {
        return (CallableDescriptor) getResultingDescriptor();
    }

    public final Collection<ResolvedCall<D>> getCandidates() {
        return this.candidates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ManyCandidates(Collection<? extends ResolvedCall<D>> candidates) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        this.candidates = candidates;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public Collection<ResolvedCall<D>> getResultingCalls() {
        return this.candidates;
    }

    public Void getResultingCall() {
        throw new IllegalStateException("Many candidates".toString());
    }

    public Void getResultingDescriptor() {
        throw new IllegalStateException("Many candidates".toString());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults
    public OverloadResolutionResults.Code getResultCode() {
        int i = WhenMappings.$EnumSwitchMapping$0[((ResolvedCall) CollectionsKt.first(this.candidates)).getStatus().ordinal()];
        if (i == 1) {
            return OverloadResolutionResults.Code.CANDIDATES_WITH_WRONG_RECEIVER;
        }
        if (i == 2) {
            return OverloadResolutionResults.Code.AMBIGUITY;
        }
        if (i == 3) {
            return OverloadResolutionResults.Code.INCOMPLETE_TYPE_INFERENCE;
        }
        return OverloadResolutionResults.Code.MANY_FAILED_CANDIDATES;
    }
}
