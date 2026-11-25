package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.model.CallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;

/* JADX INFO: compiled from: StubTypesBasedInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bJ\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007HÆ\u0003J)\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionResultCallInfo;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "", "resolutionResult", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "overloadResolutionResults", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;)V", "getOverloadResolutionResults", "()Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "getResolutionResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ResolutionResultCallInfo<D extends CallableDescriptor> {
    private final OverloadResolutionResults<D> overloadResolutionResults;
    private final CallResolutionResult resolutionResult;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ResolutionResultCallInfo copy$default(ResolutionResultCallInfo resolutionResultCallInfo, CallResolutionResult callResolutionResult, OverloadResolutionResults overloadResolutionResults, int i, Object obj) {
        if ((i & 1) != 0) {
            callResolutionResult = resolutionResultCallInfo.resolutionResult;
        }
        if ((i & 2) != 0) {
            overloadResolutionResults = resolutionResultCallInfo.overloadResolutionResults;
        }
        return resolutionResultCallInfo.copy(callResolutionResult, overloadResolutionResults);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CallResolutionResult getResolutionResult() {
        return this.resolutionResult;
    }

    public final OverloadResolutionResults<D> component2() {
        return this.overloadResolutionResults;
    }

    public final ResolutionResultCallInfo<D> copy(CallResolutionResult resolutionResult, OverloadResolutionResults<D> overloadResolutionResults) {
        Intrinsics.checkNotNullParameter(resolutionResult, "resolutionResult");
        Intrinsics.checkNotNullParameter(overloadResolutionResults, "overloadResolutionResults");
        return new ResolutionResultCallInfo<>(resolutionResult, overloadResolutionResults);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResolutionResultCallInfo)) {
            return false;
        }
        ResolutionResultCallInfo resolutionResultCallInfo = (ResolutionResultCallInfo) other;
        return Intrinsics.areEqual(this.resolutionResult, resolutionResultCallInfo.resolutionResult) && Intrinsics.areEqual(this.overloadResolutionResults, resolutionResultCallInfo.overloadResolutionResults);
    }

    public int hashCode() {
        return (this.resolutionResult.hashCode() * 31) + this.overloadResolutionResults.hashCode();
    }

    public String toString() {
        return "ResolutionResultCallInfo(resolutionResult=" + this.resolutionResult + ", overloadResolutionResults=" + this.overloadResolutionResults + ')';
    }

    public ResolutionResultCallInfo(CallResolutionResult resolutionResult, OverloadResolutionResults<D> overloadResolutionResults) {
        Intrinsics.checkNotNullParameter(resolutionResult, "resolutionResult");
        Intrinsics.checkNotNullParameter(overloadResolutionResults, "overloadResolutionResults");
        this.resolutionResult = resolutionResult;
        this.overloadResolutionResults = overloadResolutionResults;
    }

    public final CallResolutionResult getResolutionResult() {
        return this.resolutionResult;
    }

    public final OverloadResolutionResults<D> getOverloadResolutionResults() {
        return this.overloadResolutionResults;
    }
}
