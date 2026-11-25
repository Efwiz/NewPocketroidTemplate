package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.CompletedCallInfo;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.CompletedCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;

/* JADX INFO: compiled from: StubTypesBasedInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0015\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSICompletedCallInfo;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CallInfo;", "Lorg/jetbrains/kotlin/resolve/calls/components/CompletedCallInfo;", "callResolutionResult", "Lorg/jetbrains/kotlin/resolve/calls/model/CompletedCallResolutionResult;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CompletedCallResolutionResult;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;)V", "getCallResolutionResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/CompletedCallResolutionResult;", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PSICompletedCallInfo extends CallInfo implements CompletedCallInfo {
    private final CompletedCallResolutionResult callResolutionResult;
    private final NewAbstractResolvedCall<?> resolvedCall;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CallInfo, org.jetbrains.kotlin.resolve.calls.components.CompletedCallInfo
    public CompletedCallResolutionResult getCallResolutionResult() {
        return this.callResolutionResult;
    }

    public final NewAbstractResolvedCall<?> getResolvedCall() {
        return this.resolvedCall;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PSICompletedCallInfo(CompletedCallResolutionResult callResolutionResult, BasicCallResolutionContext context, NewAbstractResolvedCall<?> resolvedCall, TracingStrategy tracingStrategy) {
        super(callResolutionResult, context, tracingStrategy);
        Intrinsics.checkNotNullParameter(callResolutionResult, "callResolutionResult");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        this.callResolutionResult = callResolutionResult;
        this.resolvedCall = resolvedCall;
    }
}
