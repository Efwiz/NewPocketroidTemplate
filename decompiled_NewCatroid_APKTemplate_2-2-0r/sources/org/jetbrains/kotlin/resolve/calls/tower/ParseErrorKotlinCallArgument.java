package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ParseErrorKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/tower/SimplePSIKotlinCallArgument;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "dataFlowInfoAfterThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "(Lorg/jetbrains/kotlin/psi/ValueArgument;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;)V", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "getDataFlowInfoAfterThisArgument", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "dataFlowInfoBeforeThisArgument", "getDataFlowInfoBeforeThisArgument", "isSafeCall", "", "()Z", "isSpread", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getValueArgument", "()Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParseErrorKotlinCallArgument extends SimplePSIKotlinCallArgument implements ExpressionKotlinCallArgument {
    private final DataFlowInfo dataFlowInfoAfterThisArgument;
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
    public DataFlowInfo getDataFlowInfoAfterThisArgument() {
        return this.dataFlowInfoAfterThisArgument;
    }

    public ParseErrorKotlinCallArgument(ValueArgument valueArgument, DataFlowInfo dataFlowInfoAfterThisArgument) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoAfterThisArgument, "dataFlowInfoAfterThisArgument");
        this.valueArgument = valueArgument;
        this.dataFlowInfoAfterThisArgument = dataFlowInfoAfterThisArgument;
        this.receiver = new ReceiverValueWithSmartCastInfo(new TransientReceiver(ErrorUtils.createErrorType(ErrorTypeKind.PARSE_ERROR_ARGUMENT, getValueArgument().toString())), SetsKt.emptySet(), true, null, 8, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    public ReceiverValueWithSmartCastInfo getReceiver() {
        return this.receiver;
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

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoBeforeThisArgument() {
        return getDataFlowInfoAfterThisArgument();
    }
}
