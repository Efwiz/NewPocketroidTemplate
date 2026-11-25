package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.PartialCallInfo;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;

/* JADX INFO: compiled from: StubTypesBasedInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSIPartialCallInfo;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CallInfo;", "Lorg/jetbrains/kotlin/resolve/calls/components/PartialCallInfo;", "callResolutionResult", "Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "(Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;)V", "getCallResolutionResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PSIPartialCallInfo extends CallInfo implements PartialCallInfo {
    private final PartialCallResolutionResult callResolutionResult;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CallInfo, org.jetbrains.kotlin.resolve.calls.components.CompletedCallInfo
    public PartialCallResolutionResult getCallResolutionResult() {
        return this.callResolutionResult;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PSIPartialCallInfo(PartialCallResolutionResult callResolutionResult, BasicCallResolutionContext context, TracingStrategy tracingStrategy) {
        super(callResolutionResult, context, tracingStrategy);
        Intrinsics.checkNotNullParameter(callResolutionResult, "callResolutionResult");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        this.callResolutionResult = callResolutionResult;
    }
}
