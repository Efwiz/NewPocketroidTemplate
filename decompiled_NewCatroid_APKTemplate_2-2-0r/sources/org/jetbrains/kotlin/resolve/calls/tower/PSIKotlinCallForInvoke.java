package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.CallTransformer;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallKind;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyForInvoke;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: PSIKotlinCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\b\u001a\u0004\u0018\u00010\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u001e\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b!\u0010#R\u0014\u0010$\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\u00020-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0014\u00100\u001a\u00020-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010/R\u0014\u00102\u001a\u000203X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u001a\u00106\u001a\b\u0012\u0004\u0012\u0002070\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallForInvoke;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "baseCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "variableCall", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "dispatchReceiverForInvokeExtension", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;)V", "argumentsInParenthesis", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getArgumentsInParenthesis", "()Ljava/util/List;", "getBaseCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "callKind", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "getCallKind", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "dataFlowInfoForArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "getDataFlowInfoForArguments", "()Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "getDispatchReceiverForInvokeExtension", "()Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "getExplicitReceiver", "()Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "externalArgument", "getExternalArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "isForImplicitInvoke", "", "()Z", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "psiCall", "Lorg/jetbrains/kotlin/psi/Call;", "getPsiCall", "()Lorg/jetbrains/kotlin/psi/Call;", "resultDataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getResultDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "startingDataFlowInfo", "getStartingDataFlowInfo", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "getTracingStrategy", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "typeArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "getTypeArguments", "getVariableCall", "()Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PSIKotlinCallForInvoke extends PSIKotlinCall {
    private final PSIKotlinCallImpl baseCall;
    private final SimpleKotlinCallArgument dispatchReceiverForInvokeExtension;
    private final ReceiverKotlinCallArgument explicitReceiver;
    private final boolean isForImplicitInvoke;
    private final Call psiCall;
    private final TracingStrategy tracingStrategy;
    private final ResolutionCandidate variableCall;

    public final PSIKotlinCallImpl getBaseCall() {
        return this.baseCall;
    }

    public final ResolutionCandidate getVariableCall() {
        return this.variableCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public ReceiverKotlinCallArgument getExplicitReceiver() {
        return this.explicitReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public SimpleKotlinCallArgument getDispatchReceiverForInvokeExtension() {
        return this.dispatchReceiverForInvokeExtension;
    }

    public PSIKotlinCallForInvoke(PSIKotlinCallImpl baseCall, ResolutionCandidate variableCall, ReceiverKotlinCallArgument explicitReceiver, SimpleKotlinCallArgument simpleKotlinCallArgument) {
        Intrinsics.checkNotNullParameter(baseCall, "baseCall");
        Intrinsics.checkNotNullParameter(variableCall, "variableCall");
        Intrinsics.checkNotNullParameter(explicitReceiver, "explicitReceiver");
        this.baseCall = baseCall;
        this.variableCall = variableCall;
        this.explicitReceiver = explicitReceiver;
        this.dispatchReceiverForInvokeExtension = simpleKotlinCallArgument;
        this.isForImplicitInvoke = true;
        SimpleKotlinCallArgument dispatchReceiverForInvokeExtension = getDispatchReceiverForInvokeExtension();
        SimpleKotlinCallArgument explicitReceiver2 = dispatchReceiverForInvokeExtension != null ? dispatchReceiverForInvokeExtension : getExplicitReceiver();
        ReceiverKotlinCallArgument explicitReceiver3 = getDispatchReceiverForInvokeExtension() == null ? null : getExplicitReceiver();
        KtExpression calleeExpression = baseCall.getPsiCall().getCalleeExpression();
        Intrinsics.checkNotNull(calleeExpression);
        ReceiverValue receiverValue = explicitReceiver3 != null ? PSIKotlinCallsKt.getReceiverValue(explicitReceiver3) : null;
        ReceiverValue receiverValue2 = PSIKotlinCallsKt.getReceiverValue(explicitReceiver2);
        Intrinsics.checkNotNull(receiverValue2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver");
        this.psiCall = new CallTransformer.CallForImplicitInvoke(receiverValue, (ExpressionReceiver) receiverValue2, baseCall.getPsiCall(), true);
        Call psiCall = getPsiCall();
        ReceiverValue receiverValue3 = PSIKotlinCallsKt.getReceiverValue(explicitReceiver2);
        Intrinsics.checkNotNull(receiverValue3);
        this.tracingStrategy = new TracingStrategyForInvoke(calleeExpression, psiCall, receiverValue3.getType());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public KotlinCallKind getCallKind() {
        return KotlinCallKind.FUNCTION;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public Name getName() {
        return OperatorNameConventions.INVOKE;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public List<TypeArgument> getTypeArguments() {
        return this.baseCall.getTypeArguments();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public List<KotlinCallArgument> getArgumentsInParenthesis() {
        return this.baseCall.getArgumentsInParenthesis();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public KotlinCallArgument getExternalArgument() {
        return this.baseCall.getExternalArgument();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfo getStartingDataFlowInfo() {
        return this.baseCall.getStartingDataFlowInfo();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfo getResultDataFlowInfo() {
        return this.baseCall.getResultDataFlowInfo();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfoForArguments getDataFlowInfoForArguments() {
        return this.baseCall.getDataFlowInfoForArguments();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public Call getPsiCall() {
        return this.psiCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public TracingStrategy getTracingStrategy() {
        return this.tracingStrategy;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    /* JADX INFO: renamed from: isForImplicitInvoke, reason: from getter */
    public boolean getIsForImplicitInvoke() {
        return this.isForImplicitInvoke;
    }
}
