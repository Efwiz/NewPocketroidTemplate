package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.calls.CallTransformer;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallKind;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;

/* JADX INFO: compiled from: PSIKotlinCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020#X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010)R\u0014\u0010,\u001a\u00020-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u001a\u00100\u001a\b\u0012\u0004\u0012\u0002010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\r¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallForVariable;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "baseCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;Lorg/jetbrains/kotlin/name/Name;)V", "argumentsInParenthesis", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getArgumentsInParenthesis", "()Ljava/util/List;", "getBaseCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "callKind", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "getCallKind", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "dataFlowInfoForArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "getDataFlowInfoForArguments", "()Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "getExplicitReceiver", "()Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "externalArgument", "getExternalArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "isForImplicitInvoke", "", "()Z", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "psiCall", "Lorg/jetbrains/kotlin/psi/Call;", "getPsiCall", "()Lorg/jetbrains/kotlin/psi/Call;", "resultDataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getResultDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "startingDataFlowInfo", "getStartingDataFlowInfo", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "getTracingStrategy", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "typeArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "getTypeArguments", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PSIKotlinCallForVariable extends PSIKotlinCall {
    private final PSIKotlinCallImpl baseCall;
    private final ReceiverKotlinCallArgument explicitReceiver;
    private final Name name;
    private final Call psiCall;

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public KotlinCallArgument getExternalArgument() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    /* JADX INFO: renamed from: isForImplicitInvoke */
    public boolean getIsForImplicitInvoke() {
        return false;
    }

    public final PSIKotlinCallImpl getBaseCall() {
        return this.baseCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public ReceiverKotlinCallArgument getExplicitReceiver() {
        return this.explicitReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public Name getName() {
        return this.name;
    }

    public PSIKotlinCallForVariable(PSIKotlinCallImpl baseCall, ReceiverKotlinCallArgument receiverKotlinCallArgument, Name name2) {
        Intrinsics.checkNotNullParameter(baseCall, "baseCall");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.baseCall = baseCall;
        this.explicitReceiver = receiverKotlinCallArgument;
        this.name = name2;
        Call callStripCallArguments = CallTransformer.stripCallArguments(baseCall.getPsiCall());
        callStripCallArguments = getExplicitReceiver() == null ? CallTransformer.stripReceiver(callStripCallArguments) : callStripCallArguments;
        Intrinsics.checkNotNullExpressionValue(callStripCallArguments, "let(...)");
        this.psiCall = callStripCallArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public KotlinCallKind getCallKind() {
        return KotlinCallKind.VARIABLE;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public List<TypeArgument> getTypeArguments() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public List<KotlinCallArgument> getArgumentsInParenthesis() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfo getStartingDataFlowInfo() {
        return this.baseCall.getStartingDataFlowInfo();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfo getResultDataFlowInfo() {
        return this.baseCall.getStartingDataFlowInfo();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfoForArguments getDataFlowInfoForArguments() {
        return this.baseCall.getDataFlowInfoForArguments();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public TracingStrategy getTracingStrategy() {
        return this.baseCall.getTracingStrategy();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public Call getPsiCall() {
        return this.psiCall;
    }
}
