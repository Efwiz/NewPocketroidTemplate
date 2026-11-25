package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.ErrorCallInfo;
import org.jetbrains.kotlin.resolve.calls.model.CallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;

/* JADX INFO: compiled from: StubTypesBasedInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSIErrorCallInfo;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/components/ErrorCallInfo;", "callResolutionResult", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "result", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;)V", "getCallResolutionResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "getResult", "()Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PSIErrorCallInfo<D extends CallableDescriptor> implements ErrorCallInfo {
    private final CallResolutionResult callResolutionResult;
    private final OverloadResolutionResults<D> result;

    public PSIErrorCallInfo(CallResolutionResult callResolutionResult, OverloadResolutionResults<D> result) {
        Intrinsics.checkNotNullParameter(callResolutionResult, "callResolutionResult");
        Intrinsics.checkNotNullParameter(result, "result");
        this.callResolutionResult = callResolutionResult;
        this.result = result;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ErrorCallInfo
    public CallResolutionResult getCallResolutionResult() {
        return this.callResolutionResult;
    }

    public final OverloadResolutionResults<D> getResult() {
        return this.result;
    }
}
