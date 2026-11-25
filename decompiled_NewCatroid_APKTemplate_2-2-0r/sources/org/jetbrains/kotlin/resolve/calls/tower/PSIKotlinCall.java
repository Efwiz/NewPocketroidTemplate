package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;

/* JADX INFO: compiled from: PSIKotlinCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "()V", "dataFlowInfoForArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "getDataFlowInfoForArguments", "()Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "psiCall", "Lorg/jetbrains/kotlin/psi/Call;", "getPsiCall", "()Lorg/jetbrains/kotlin/psi/Call;", "resultDataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getResultDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "startingDataFlowInfo", "getStartingDataFlowInfo", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "getTracingStrategy", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PSIKotlinCall implements KotlinCall {
    public abstract DataFlowInfoForArguments getDataFlowInfoForArguments();

    public abstract Call getPsiCall();

    public abstract DataFlowInfo getResultDataFlowInfo();

    public abstract DataFlowInfo getStartingDataFlowInfo();

    public abstract TracingStrategy getTracingStrategy();

    public String toString() {
        return String.valueOf(getPsiCall());
    }
}
