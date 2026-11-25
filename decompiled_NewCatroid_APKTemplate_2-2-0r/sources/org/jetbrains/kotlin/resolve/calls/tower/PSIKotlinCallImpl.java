package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallKind;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;

/* JADX INFO: compiled from: PSIKotlinCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\u0018\u00002\u00020\u0001B\u007f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019¢\u0006\u0002\u0010\u001aR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u0016\u001a\u00020\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0016\u0010\n\u001a\u0004\u0018\u00010\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0016\u0010\b\u001a\u0004\u0018\u00010\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\"R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010\u0018\u001a\u00020\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010&R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0014\u0010\u0015\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010,R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001c¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "callKind", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "psiCall", "Lorg/jetbrains/kotlin/psi/Call;", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "dispatchReceiverForInvokeExtension", "name", "Lorg/jetbrains/kotlin/name/Name;", "typeArguments", "", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "argumentsInParenthesis", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "externalArgument", "startingDataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "resultDataFlowInfo", "dataFlowInfoForArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "isForImplicitInvoke", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;Lorg/jetbrains/kotlin/psi/Call;Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;Lorg/jetbrains/kotlin/name/Name;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;Z)V", "getArgumentsInParenthesis", "()Ljava/util/List;", "getCallKind", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "getDataFlowInfoForArguments", "()Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "getDispatchReceiverForInvokeExtension", "()Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "getExplicitReceiver", "getExternalArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "()Z", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getPsiCall", "()Lorg/jetbrains/kotlin/psi/Call;", "getResultDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getStartingDataFlowInfo", "getTracingStrategy", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "getTypeArguments", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PSIKotlinCallImpl extends PSIKotlinCall {
    private final List<KotlinCallArgument> argumentsInParenthesis;
    private final KotlinCallKind callKind;
    private final DataFlowInfoForArguments dataFlowInfoForArguments;
    private final ReceiverKotlinCallArgument dispatchReceiverForInvokeExtension;
    private final ReceiverKotlinCallArgument explicitReceiver;
    private final KotlinCallArgument externalArgument;
    private final boolean isForImplicitInvoke;
    private final Name name;
    private final Call psiCall;
    private final DataFlowInfo resultDataFlowInfo;
    private final DataFlowInfo startingDataFlowInfo;
    private final TracingStrategy tracingStrategy;
    private final List<TypeArgument> typeArguments;

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public KotlinCallKind getCallKind() {
        return this.callKind;
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
    public ReceiverKotlinCallArgument getExplicitReceiver() {
        return this.explicitReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public ReceiverKotlinCallArgument getDispatchReceiverForInvokeExtension() {
        return this.dispatchReceiverForInvokeExtension;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public List<TypeArgument> getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public List<KotlinCallArgument> getArgumentsInParenthesis() {
        return this.argumentsInParenthesis;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    public KotlinCallArgument getExternalArgument() {
        return this.externalArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfo getStartingDataFlowInfo() {
        return this.startingDataFlowInfo;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfo getResultDataFlowInfo() {
        return this.resultDataFlowInfo;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall
    public DataFlowInfoForArguments getDataFlowInfoForArguments() {
        return this.dataFlowInfoForArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCall
    /* JADX INFO: renamed from: isForImplicitInvoke, reason: from getter */
    public boolean getIsForImplicitInvoke() {
        return this.isForImplicitInvoke;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PSIKotlinCallImpl(KotlinCallKind callKind, Call psiCall, TracingStrategy tracingStrategy, ReceiverKotlinCallArgument receiverKotlinCallArgument, ReceiverKotlinCallArgument receiverKotlinCallArgument2, Name name2, List<? extends TypeArgument> typeArguments, List<? extends KotlinCallArgument> argumentsInParenthesis, KotlinCallArgument kotlinCallArgument, DataFlowInfo startingDataFlowInfo, DataFlowInfo resultDataFlowInfo, DataFlowInfoForArguments dataFlowInfoForArguments, boolean z) {
        Intrinsics.checkNotNullParameter(callKind, "callKind");
        Intrinsics.checkNotNullParameter(psiCall, "psiCall");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(argumentsInParenthesis, "argumentsInParenthesis");
        Intrinsics.checkNotNullParameter(startingDataFlowInfo, "startingDataFlowInfo");
        Intrinsics.checkNotNullParameter(resultDataFlowInfo, "resultDataFlowInfo");
        Intrinsics.checkNotNullParameter(dataFlowInfoForArguments, "dataFlowInfoForArguments");
        this.callKind = callKind;
        this.psiCall = psiCall;
        this.tracingStrategy = tracingStrategy;
        this.explicitReceiver = receiverKotlinCallArgument;
        this.dispatchReceiverForInvokeExtension = receiverKotlinCallArgument2;
        this.name = name2;
        this.typeArguments = typeArguments;
        this.argumentsInParenthesis = argumentsInParenthesis;
        this.externalArgument = kotlinCallArgument;
        this.startingDataFlowInfo = startingDataFlowInfo;
        this.resultDataFlowInfo = resultDataFlowInfo;
        this.dataFlowInfoForArguments = dataFlowInfoForArguments;
        this.isForImplicitInvoke = z;
    }
}
