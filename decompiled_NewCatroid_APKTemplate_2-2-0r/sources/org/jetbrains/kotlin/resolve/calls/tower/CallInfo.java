package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.SingleCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;

/* JADX INFO: compiled from: StubTypesBasedInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/CallInfo;", "", "callResolutionResult", "Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "(Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;)V", "getCallResolutionResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;", "getContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "getTracingStrategy", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallInfo {
    private final SingleCallResolutionResult callResolutionResult;
    private final BasicCallResolutionContext context;
    private final TracingStrategy tracingStrategy;

    public CallInfo(SingleCallResolutionResult callResolutionResult, BasicCallResolutionContext context, TracingStrategy tracingStrategy) {
        Intrinsics.checkNotNullParameter(callResolutionResult, "callResolutionResult");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        this.callResolutionResult = callResolutionResult;
        this.context = context;
        this.tracingStrategy = tracingStrategy;
    }

    public SingleCallResolutionResult getCallResolutionResult() {
        return this.callResolutionResult;
    }

    public final BasicCallResolutionContext getContext() {
        return this.context;
    }

    public final TracingStrategy getTracingStrategy() {
        return this.tracingStrategy;
    }
}
