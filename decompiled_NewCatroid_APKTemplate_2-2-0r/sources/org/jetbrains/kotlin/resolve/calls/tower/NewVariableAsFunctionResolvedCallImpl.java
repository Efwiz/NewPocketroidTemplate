package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatchImpl;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;

/* JADX INFO: compiled from: NewVariableAsFunctionResolvedCallImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B!\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002¢\u0006\u0002\u0010\u0007J0\u00102\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u0002040\t2\u0006\u00105\u001a\u0002062\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002080\tH\u0016J\b\u00109\u001a\u00020:H\u0016J\b\u0010;\u001a\u00020\u0003H\u0016J*\u0010<\u001a$\u0012\f\u0012\n ?*\u0004\u0018\u00010>0> ?*\u0010\u0012\f\u0012\n ?*\u0004\u0018\u00010>0>0@0=H\u0016J\n\u0010A\u001a\u0004\u0018\u00010>H\u0016J\b\u0010B\u001a\u00020CH\u0016J\n\u0010D\u001a\u0004\u0018\u00010>H\u0016J\b\u0010E\u001a\u00020\u0003H\u0016J\n\u0010F\u001a\u0004\u0018\u00010GH\u0016J\b\u0010H\u001a\u00020IH\u0016JF\u0010J\u001a@\u0012\f\u0012\n ?*\u0004\u0018\u00010L0L\u0012\f\u0012\n ?*\u0004\u0018\u00010G0G ?*\u001e\u0012\f\u0012\n ?*\u0004\u0018\u00010L0L\u0012\f\u0012\n ?*\u0004\u0018\u00010G0G0\t0KH\u0016J\u0012\u0010M\u001a\u00020N2\b\u0010O\u001a\u0004\u0018\u00010PH\u0016J\u0016\u0010Q\u001a\u00020N2\f\u0010R\u001a\b\u0012\u0004\u0012\u00020G0@H\u0016J\u0010\u0010S\u001a\u00020N2\u0006\u0010T\u001a\u00020GH\u0016J\u0010\u0010U\u001a\u00020N2\u0006\u0010T\u001a\u00020GH\u0016R \u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u00020&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0016\u0010)\u001a\u0004\u0018\u00010*X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020.X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001c¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewVariableAsFunctionResolvedCallImpl;", "Lorg/jetbrains/kotlin/resolve/calls/model/VariableAsFunctionResolvedCall;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "variableCall", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "functionCall", "(Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;)V", "argumentMappingByOriginal", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "getArgumentMappingByOriginal", "()Ljava/util/Map;", "baseCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "getBaseCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallImpl;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "getDiagnostics", "()Ljava/util/Collection;", "freshSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "getFreshSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "getFunctionCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "psiKotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "getPsiKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "getResolvedCallAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/TypeApproximator;", "getVariableCall", "argumentToParameterMap", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchImpl;", "resultingDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "valueArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "containsOnlyOnlyInputTypesErrors", "", "getCandidateDescriptor", "getContextReceivers", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "getDispatchReceiver", "getExplicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExtensionReceiver", "getResultingDescriptor", "getSmartCastDispatchReceiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getStatus", "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "getTypeArguments", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "setResultingSubstitutor", "", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "updateContextReceiverTypes", "newTypes", "updateDispatchReceiverType", "newType", "updateExtensionReceiverType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewVariableAsFunctionResolvedCallImpl extends NewAbstractResolvedCall<FunctionDescriptor> implements VariableAsFunctionResolvedCall {
    private final Map<ValueParameterDescriptor, ResolvedCallArgument> argumentMappingByOriginal;
    private final PSIKotlinCallImpl baseCall;
    private final Collection<KotlinCallDiagnostic> diagnostics;
    private final FreshVariableNewTypeSubstitutor freshSubstitutor;
    private final NewAbstractResolvedCall<FunctionDescriptor> functionCall;
    private final KotlinCall kotlinCall;
    private final LanguageVersionSettings languageVersionSettings;
    private final PSIKotlinCall psiKotlinCall;
    private final ResolvedCallAtom resolvedCallAtom;
    private final TypeApproximator typeApproximator;
    private final NewAbstractResolvedCall<VariableDescriptor> variableCall;

    @Override // org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall
    public NewAbstractResolvedCall<VariableDescriptor> getVariableCall() {
        return this.variableCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall
    public NewAbstractResolvedCall<FunctionDescriptor> getFunctionCall() {
        return this.functionCall;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewVariableAsFunctionResolvedCallImpl(NewAbstractResolvedCall<VariableDescriptor> variableCall, NewAbstractResolvedCall<FunctionDescriptor> functionCall) {
        super(null);
        Intrinsics.checkNotNullParameter(variableCall, "variableCall");
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        this.variableCall = variableCall;
        this.functionCall = functionCall;
        PSIKotlinCall psiKotlinCall = getFunctionCall().getPsiKotlinCall();
        Intrinsics.checkNotNull(psiKotlinCall, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallForInvoke");
        this.baseCall = ((PSIKotlinCallForInvoke) psiKotlinCall).getBaseCall();
        this.resolvedCallAtom = getFunctionCall().getResolvedCallAtom();
        this.psiKotlinCall = getFunctionCall().getPsiKotlinCall();
        this.typeApproximator = getFunctionCall().getTypeApproximator();
        this.freshSubstitutor = getFunctionCall().getFreshSubstitutor();
        this.argumentMappingByOriginal = getFunctionCall().getArgumentMappingByOriginal();
        this.kotlinCall = getFunctionCall().getKotlinCall();
        this.languageVersionSettings = getFunctionCall().getLanguageVersionSettings();
        this.diagnostics = getFunctionCall().getDiagnostics();
    }

    public final PSIKotlinCallImpl getBaseCall() {
        return this.baseCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public ResolvedCallAtom getResolvedCallAtom() {
        return this.resolvedCallAtom;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public PSIKotlinCall getPsiKotlinCall() {
        return this.psiKotlinCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public TypeApproximator getTypeApproximator() {
        return this.typeApproximator;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public FreshVariableNewTypeSubstitutor getFreshSubstitutor() {
        return this.freshSubstitutor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Map<ValueParameterDescriptor, ResolvedCallArgument> getArgumentMappingByOriginal() {
        return this.argumentMappingByOriginal;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public KotlinCall getKotlinCall() {
        return this.kotlinCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Collection<KotlinCallDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ResolutionStatus getStatus() {
        ResolutionStatus status = getFunctionCall().getStatus();
        Intrinsics.checkNotNullExpressionValue(status, "getStatus(...)");
        return status;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    /* JADX INFO: renamed from: getCandidateDescriptor */
    public FunctionDescriptor get$suspendPropertyDescriptor() {
        FunctionDescriptor candidateDescriptor = getFunctionCall().get$suspendPropertyDescriptor();
        Intrinsics.checkNotNullExpressionValue(candidateDescriptor, "getCandidateDescriptor(...)");
        return candidateDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public FunctionDescriptor getResultingDescriptor() {
        FunctionDescriptor resultingDescriptor = getFunctionCall().getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        return resultingDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ReceiverValue getExtensionReceiver() {
        return getFunctionCall().getExtensionReceiver();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ReceiverValue> getContextReceivers() {
        List<ReceiverValue> contextReceivers = getFunctionCall().getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        return contextReceivers;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ReceiverValue getDispatchReceiver() {
        return getFunctionCall().getDispatchReceiver();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ExplicitReceiverKind getExplicitReceiverKind() {
        ExplicitReceiverKind explicitReceiverKind = getFunctionCall().getExplicitReceiverKind();
        Intrinsics.checkNotNullExpressionValue(explicitReceiverKind, "getExplicitReceiverKind(...)");
        return explicitReceiverKind;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<TypeParameterDescriptor, KotlinType> getTypeArguments() {
        Map<TypeParameterDescriptor, KotlinType> typeArguments = getFunctionCall().getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        return typeArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public KotlinType getSmartCastDispatchReceiverType() {
        return getFunctionCall().getSmartCastDispatchReceiverType();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public boolean containsOnlyOnlyInputTypesErrors() {
        return getFunctionCall().containsOnlyOnlyInputTypesErrors();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateDispatchReceiverType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        getFunctionCall().updateDispatchReceiverType(newType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateExtensionReceiverType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        getFunctionCall().updateExtensionReceiverType(newType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateContextReceiverTypes(List<? extends KotlinType> newTypes) {
        Intrinsics.checkNotNullParameter(newTypes, "newTypes");
        getFunctionCall().updateContextReceiverTypes(newTypes);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Map<ValueArgument, ArgumentMatchImpl> argumentToParameterMap(CallableDescriptor resultingDescriptor, Map<ValueParameterDescriptor, ? extends ResolvedValueArgument> valueArguments) {
        Intrinsics.checkNotNullParameter(resultingDescriptor, "resultingDescriptor");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        return getFunctionCall().argumentToParameterMap(resultingDescriptor, valueArguments);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void setResultingSubstitutor(NewTypeSubstitutor substitutor) {
        getFunctionCall().setResultingSubstitutor(substitutor);
        getVariableCall().setResultingSubstitutor(substitutor);
    }
}
