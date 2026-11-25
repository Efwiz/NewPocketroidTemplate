package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0007\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0014R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ExpressionKotlinCallArgumentImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/SimplePSIKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "dataFlowInfoBeforeThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "dataFlowInfoAfterThisArgument", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/psi/ValueArgument;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "getDataFlowInfoAfterThisArgument", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfoBeforeThisArgument", "isSafeCall", "", "()Z", "isSpread", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getValueArgument", "()Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpressionKotlinCallArgumentImpl extends SimplePSIKotlinCallArgument implements ExpressionKotlinCallArgument {
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

    public ExpressionKotlinCallArgumentImpl(ValueArgument valueArgument, DataFlowInfo dataFlowInfoBeforeThisArgument, DataFlowInfo dataFlowInfoAfterThisArgument, ReceiverValueWithSmartCastInfo receiver) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoBeforeThisArgument, "dataFlowInfoBeforeThisArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoAfterThisArgument, "dataFlowInfoAfterThisArgument");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        this.valueArgument = valueArgument;
        this.dataFlowInfoBeforeThisArgument = dataFlowInfoBeforeThisArgument;
        this.dataFlowInfoAfterThisArgument = dataFlowInfoAfterThisArgument;
        this.receiver = receiver;
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
