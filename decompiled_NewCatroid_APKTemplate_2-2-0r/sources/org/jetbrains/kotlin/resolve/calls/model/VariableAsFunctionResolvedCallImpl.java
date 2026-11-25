package org.jetbrains.kotlin.resolve.calls.model;

import androidx.core.app.NotificationCompat;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: VariableAsFunctionResolvedCall.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B!\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0002¢\u0006\u0002\u0010\u0007J5\u0010\u000b\u001a\u00020\f2*\u0010\r\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\f0\f \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\f0\f\u0018\u00010\u000e0\u000eH\u0096\u0001J\u0013\u0010\u0010\u001a\u00020\f2\b\b\u0001\u0010\u0011\u001a\u00020\u0012H\u0096\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u0010\u0015\u001a\u00020\u0016H\u0097\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u0097\u0001J\t\u0010\u0019\u001a\u00020\u0003H\u0097\u0001J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0097\u0001J+\u0010\u001c\u001a$\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u001e0\u001e \u000f*\u0010\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u001e0\u001e0\u001f0\u001dH\u0097\u0001J\t\u0010 \u001a\u00020!H\u0097\u0001J\u000b\u0010\"\u001a\u0004\u0018\u00010\u001eH\u0097\u0001J\t\u0010#\u001a\u00020$H\u0097\u0001J\u000b\u0010%\u001a\u0004\u0018\u00010\u001eH\u0097\u0001J\u000b\u0010&\u001a\u0004\u0018\u00010'H\u0097\u0001J\t\u0010(\u001a\u00020\u0003H\u0097\u0001J\u000b\u0010)\u001a\u0004\u0018\u00010*H\u0097\u0001J\b\u0010+\u001a\u00020\u0012H\u0016J\b\u0010,\u001a\u00020-H\u0016J\t\u0010.\u001a\u00020/H\u0097\u0001JG\u00100\u001a@\u0012\f\u0012\n \u000f*\u0004\u0018\u00010202\u0012\f\u0012\n \u000f*\u0004\u0018\u00010*0* \u000f*\u001e\u0012\f\u0012\n \u000f*\u0004\u0018\u00010202\u0012\f\u0012\n \u000f*\u0004\u0018\u00010*0*0301H\u0097\u0001JG\u00104\u001a@\u0012\f\u0012\n \u000f*\u0004\u0018\u00010505\u0012\f\u0012\n \u000f*\u0004\u0018\u00010606 \u000f*\u001e\u0012\f\u0012\n \u000f*\u0004\u0018\u00010505\u0012\f\u0012\n \u000f*\u0004\u0018\u000106060301H\u0097\u0001J/\u00107\u001a(\u0012\f\u0012\n \u000f*\u0004\u0018\u00010606\u0018\u0001 \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u00010606\u0018\u00010\u001f0\u001dH\u0097\u0001J\t\u00108\u001a\u000209H\u0096\u0001J\b\u0010:\u001a\u000209H\u0016J\b\u0010;\u001a\u00020\fH\u0016J\t\u0010<\u001a\u00020\fH\u0096\u0001J\u001d\u0010=\u001a\u00020\f2\b\b\u0001\u0010\u0015\u001a\u00020\u00162\b\b\u0001\u0010>\u001a\u00020?H\u0096\u0001J\u001d\u0010@\u001a\u00020\f2\b\b\u0001\u0010A\u001a\u0002052\b\b\u0001\u0010\u0015\u001a\u000206H\u0096\u0001J\u0013\u0010B\u001a\u00020\f2\b\b\u0001\u0010C\u001a\u00020\u001bH\u0096\u0001J\u0013\u0010D\u001a\u00020\f2\b\b\u0001\u0010E\u001a\u00020*H\u0096\u0001J\t\u0010F\u001a\u00020\fH\u0096\u0001J\u0013\u0010G\u001a\u00020\f2\b\b\u0001\u0010H\u001a\u00020'H\u0096\u0001J\u0013\u0010I\u001a\u00020\f2\b\b\u0001\u0010J\u001a\u00020*H\u0096\u0001R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/VariableAsFunctionResolvedCallImpl;", "Lorg/jetbrains/kotlin/resolve/calls/model/VariableAsFunctionMutableResolvedCall;", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "functionCall", "variableCall", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;)V", "getFunctionCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;", "getVariableCall", "addRemainingTasks", "", "task", "Lkotlin/Function0;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "addStatus", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "getArgumentMapping", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMapping;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "getCall", "Lorg/jetbrains/kotlin/psi/Call;", "getCandidateDescriptor", "getConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem;", "getContextReceivers", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "", "getDataFlowInfoForArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableDataFlowInfoForArguments;", "getDispatchReceiver", "getExplicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExtensionReceiver", "getKnownTypeParametersSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "getResultingDescriptor", "getSmartCastDispatchReceiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getStatus", "getTrace", "Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;", "getTracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "getTypeArguments", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "", "getValueArguments", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "getValueArgumentsByIndex", "hasInferredReturnType", "", "isCompleted", "markCallAsCompleted", "performRemainingTasks", "recordArgumentMatchStatus", "matchStatus", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchStatus;", "recordValueArgument", "valueParameter", "setConstraintSystem", "constraintSystem", "setSmartCastDispatchReceiverType", "smartCastDispatchReceiverType", "setStatusToSuccess", "setSubstitutor", "substitutor", "updateExtensionReceiverWithSmartCastIfNeeded", "smartCastExtensionReceiverType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableAsFunctionResolvedCallImpl implements MutableResolvedCall<FunctionDescriptor>, VariableAsFunctionMutableResolvedCall {
    private final MutableResolvedCall<FunctionDescriptor> functionCall;
    private final MutableResolvedCall<VariableDescriptor> variableCall;

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void addRemainingTasks(Function0<Unit> task) {
        this.functionCall.addRemainingTasks(task);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void addStatus(ResolutionStatus status) {
        Intrinsics.checkNotNullParameter(status, "status");
        this.functionCall.addStatus(status);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ArgumentMapping getArgumentMapping(ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        return this.functionCall.getArgumentMapping(valueArgument);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Call getCall() {
        return this.functionCall.getCall();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    /* JADX INFO: renamed from: getCandidateDescriptor */
    public FunctionDescriptor get$suspendPropertyDescriptor() {
        return this.functionCall.get$suspendPropertyDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public ConstraintSystem getConstraintSystem() {
        return this.functionCall.getConstraintSystem();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ReceiverValue> getContextReceivers() {
        return this.functionCall.getContextReceivers();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public MutableDataFlowInfoForArguments getDataFlowInfoForArguments() {
        return this.functionCall.getDataFlowInfoForArguments();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ReceiverValue getDispatchReceiver() {
        return this.functionCall.getDispatchReceiver();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ExplicitReceiverKind getExplicitReceiverKind() {
        return this.functionCall.getExplicitReceiverKind();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ReceiverValue getExtensionReceiver() {
        return this.functionCall.getExtensionReceiver();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public TypeSubstitutor getKnownTypeParametersSubstitutor() {
        return this.functionCall.getKnownTypeParametersSubstitutor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public FunctionDescriptor getResultingDescriptor() {
        return this.functionCall.getResultingDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public KotlinType getSmartCastDispatchReceiverType() {
        return this.functionCall.getSmartCastDispatchReceiverType();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public TracingStrategy getTracingStrategy() {
        return this.functionCall.getTracingStrategy();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<TypeParameterDescriptor, KotlinType> getTypeArguments() {
        return this.functionCall.getTypeArguments();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArguments() {
        return this.functionCall.getValueArguments();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ResolvedValueArgument> getValueArgumentsByIndex() {
        return this.functionCall.getValueArgumentsByIndex();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public boolean hasInferredReturnType() {
        return this.functionCall.hasInferredReturnType();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void performRemainingTasks() {
        this.functionCall.performRemainingTasks();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void recordArgumentMatchStatus(ValueArgument valueArgument, ArgumentMatchStatus matchStatus) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(matchStatus, "matchStatus");
        this.functionCall.recordArgumentMatchStatus(valueArgument, matchStatus);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void recordValueArgument(ValueParameterDescriptor valueParameter, ResolvedValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        this.functionCall.recordValueArgument(valueParameter, valueArgument);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void setConstraintSystem(ConstraintSystem constraintSystem) {
        Intrinsics.checkNotNullParameter(constraintSystem, "constraintSystem");
        this.functionCall.setConstraintSystem(constraintSystem);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void setSmartCastDispatchReceiverType(KotlinType smartCastDispatchReceiverType) {
        Intrinsics.checkNotNullParameter(smartCastDispatchReceiverType, "smartCastDispatchReceiverType");
        this.functionCall.setSmartCastDispatchReceiverType(smartCastDispatchReceiverType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void setStatusToSuccess() {
        this.functionCall.setStatusToSuccess();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void setSubstitutor(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        this.functionCall.setSubstitutor(substitutor);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void updateExtensionReceiverWithSmartCastIfNeeded(KotlinType smartCastExtensionReceiverType) {
        Intrinsics.checkNotNullParameter(smartCastExtensionReceiverType, "smartCastExtensionReceiverType");
        this.functionCall.updateExtensionReceiverWithSmartCastIfNeeded(smartCastExtensionReceiverType);
    }

    public VariableAsFunctionResolvedCallImpl(MutableResolvedCall<FunctionDescriptor> functionCall, MutableResolvedCall<VariableDescriptor> variableCall) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(variableCall, "variableCall");
        this.functionCall = functionCall;
        this.variableCall = variableCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall
    public MutableResolvedCall<FunctionDescriptor> getFunctionCall() {
        return this.functionCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall
    public MutableResolvedCall<VariableDescriptor> getVariableCall() {
        return this.variableCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void markCallAsCompleted() {
        getFunctionCall().markCallAsCompleted();
        getVariableCall().markCallAsCompleted();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public boolean isCompleted() {
        return getFunctionCall().isCompleted() && getVariableCall().isCompleted();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ResolutionStatus getStatus() {
        ResolutionStatus resolutionStatusCombine = getVariableCall().getStatus().combine(getFunctionCall().getStatus());
        Intrinsics.checkNotNullExpressionValue(resolutionStatusCombine, "combine(...)");
        return resolutionStatusCombine;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public DelegatingBindingTrace getTrace() {
        DelegatingBindingTrace trace = getFunctionCall().getTrace();
        Intrinsics.checkNotNullExpressionValue(trace, "getTrace(...)");
        return trace;
    }
}
