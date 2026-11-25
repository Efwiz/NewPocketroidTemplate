package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.SubKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0007\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0018R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/SubKotlinCallArgumentImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/SimplePSIKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/SubKotlinCallArgument;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "dataFlowInfoBeforeThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "dataFlowInfoAfterThisArgument", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "callResult", "Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;", "(Lorg/jetbrains/kotlin/psi/ValueArgument;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;)V", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "getCallResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;", "getDataFlowInfoAfterThisArgument", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfoBeforeThisArgument", "isSafeCall", "", "()Z", "isSpread", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getValueArgument", "()Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubKotlinCallArgumentImpl extends SimplePSIKotlinCallArgument implements SubKotlinCallArgument {
    private final PartialCallResolutionResult callResult;
    private final DataFlowInfo dataFlowInfoAfterThisArgument;
    private final DataFlowInfo dataFlowInfoBeforeThisArgument;
    private final ReceiverValueWithSmartCastInfo receiver;
    private final ValueArgument valueArgument;

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    /* JADX INFO: renamed from: isSafeCall */
    public boolean getIsSafeCall() {
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public ValueArgument getValueArgument() {
        return this.valueArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoBeforeThisArgument() {
        return this.dataFlowInfoBeforeThisArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoAfterThisArgument() {
        return this.dataFlowInfoAfterThisArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    public ReceiverValueWithSmartCastInfo getReceiver() {
        return this.receiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.SubKotlinCallArgument
    public PartialCallResolutionResult getCallResult() {
        return this.callResult;
    }

    public SubKotlinCallArgumentImpl(ValueArgument valueArgument, DataFlowInfo dataFlowInfoBeforeThisArgument, DataFlowInfo dataFlowInfoAfterThisArgument, ReceiverValueWithSmartCastInfo receiver, PartialCallResolutionResult callResult) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoBeforeThisArgument, "dataFlowInfoBeforeThisArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoAfterThisArgument, "dataFlowInfoAfterThisArgument");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(callResult, "callResult");
        this.valueArgument = valueArgument;
        this.dataFlowInfoBeforeThisArgument = dataFlowInfoBeforeThisArgument;
        this.dataFlowInfoAfterThisArgument = dataFlowInfoAfterThisArgument;
        this.receiver = receiver;
        this.callResult = callResult;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public boolean isSpread() {
        return getValueArgument().getSpreadElement() != null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public Name getArgumentName() {
        ValueArgumentName argumentName = getValueArgument().getArgumentName();
        if (argumentName != null) {
            return argumentName.getAsName();
        }
        return null;
    }
}
