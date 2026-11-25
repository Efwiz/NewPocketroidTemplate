package org.jetbrains.kotlin.resolve.calls.components.candidate;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.CallableReceiver;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceAdaptation;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceCallAtom;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: CallableReferenceResolutionCandidate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001Bo\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017¢\u0006\u0002\u0010\u0018J\u0010\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020HH\u0016J\u000e\u0010I\u001a\b\u0012\u0004\u0012\u00020H0JH\u0016R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\"R(\u0010*\u001a\u0004\u0018\u00010)2\b\u0010(\u001a\u0004\u0018\u00010)@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0016\u0010/\u001a\u0004\u0018\u000100X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u00105\u001a\u0002068F¢\u0006\u0006\u001a\u0004\b7\u00108R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b9\u0010$R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0014\u0010<\u001a\u00020=X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u0016\u0010B\u001a\u0004\u0018\u00010\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010D¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "candidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "extensionReceiver", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "reflectionCandidateType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "callableReferenceAdaptation", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceAdaptation;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "expectedType", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceAdaptation;Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;)V", "getBaseSystem", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getCallComponents", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "getCallableReferenceAdaptation", "()Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceAdaptation;", "getCandidate", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getExplicitReceiverKind", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExtensionReceiver", "<set-?>", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "freshVariablesSubstitutor", "getFreshVariablesSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "setFreshVariablesSubstitutor$resolution", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;)V", "knownTypeParametersResultingSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "getKnownTypeParametersResultingSubstitutor", "()Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "numDefaults", "", "getNumDefaults", "()I", "getReflectionCandidateType", "getResolutionCallbacks", "()Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceCallAtom;", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceCallAtom;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "variableCandidateIfInvoke", "getVariableCandidateIfInvoke", "()Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "addResolvedKtPrimitive", "", "resolvedAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "getSubResolvedAtoms", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceResolutionCandidate extends ResolutionCandidate {
    private final ConstraintStorage baseSystem;
    private final KotlinCallComponents callComponents;
    private final CallableReferenceAdaptation callableReferenceAdaptation;
    private final CallableDescriptor candidate;
    private final CallableReceiver dispatchReceiver;
    private final UnwrappedType expectedType;
    private final ExplicitReceiverKind explicitReceiverKind;
    private final CallableReceiver extensionReceiver;
    private FreshVariableNewTypeSubstitutor freshVariablesSubstitutor;
    private final TypeSubstitutor knownTypeParametersResultingSubstitutor;
    private final CallableReferenceResolutionAtom kotlinCall;
    private final UnwrappedType reflectionCandidateType;
    private final KotlinResolutionCallbacks resolutionCallbacks;
    private final ResolvedCallableReferenceCallAtom resolvedCall;
    private final ImplicitScopeTower scopeTower;
    private final ResolutionCandidate variableCandidateIfInvoke;

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public void addResolvedKtPrimitive(ResolvedAtom resolvedAtom) {
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
    }

    public final CallableDescriptor getCandidate() {
        return this.candidate;
    }

    public final CallableReceiver getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public final CallableReceiver getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public final ExplicitReceiverKind getExplicitReceiverKind() {
        return this.explicitReceiverKind;
    }

    public final UnwrappedType getReflectionCandidateType() {
        return this.reflectionCandidateType;
    }

    public final CallableReferenceAdaptation getCallableReferenceAdaptation() {
        return this.callableReferenceAdaptation;
    }

    public final CallableReferenceResolutionAtom getKotlinCall() {
        return this.kotlinCall;
    }

    public final UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public KotlinCallComponents getCallComponents() {
        return this.callComponents;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public ImplicitScopeTower getScopeTower() {
        return this.scopeTower;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public KotlinResolutionCallbacks getResolutionCallbacks() {
        return this.resolutionCallbacks;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    protected ConstraintStorage getBaseSystem() {
        return this.baseSystem;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CallableReferenceResolutionCandidate(CallableDescriptor candidate, CallableReceiver callableReceiver, CallableReceiver callableReceiver2, ExplicitReceiverKind explicitReceiverKind, UnwrappedType reflectionCandidateType, CallableReferenceAdaptation callableReferenceAdaptation, CallableReferenceResolutionAtom kotlinCall, UnwrappedType unwrappedType, KotlinCallComponents callComponents, ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, ConstraintStorage constraintStorage) {
        super(null);
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        Intrinsics.checkNotNullParameter(reflectionCandidateType, "reflectionCandidateType");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        this.candidate = candidate;
        this.dispatchReceiver = callableReceiver;
        this.extensionReceiver = callableReceiver2;
        this.explicitReceiverKind = explicitReceiverKind;
        this.reflectionCandidateType = reflectionCandidateType;
        this.callableReferenceAdaptation = callableReferenceAdaptation;
        this.kotlinCall = kotlinCall;
        this.expectedType = unwrappedType;
        this.callComponents = callComponents;
        this.scopeTower = scopeTower;
        this.resolutionCallbacks = resolutionCallbacks;
        this.baseSystem = constraintStorage;
        this.resolvedCall = new ResolvedCallableReferenceCallAtom(kotlinCall.getCall(), candidate, explicitReceiverKind, callableReceiver != null ? ReceiverExpressionKotlinCallArgument.Companion.invoke$default(ReceiverExpressionKotlinCallArgument.INSTANCE, callableReceiver.getReceiver(), false, false, 6, null) : null, callableReceiver2 != null ? ReceiverExpressionKotlinCallArgument.Companion.invoke$default(ReceiverExpressionKotlinCallArgument.INSTANCE, callableReceiver2.getReceiver(), false, false, 6, null) : null, reflectionCandidateType, this);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public ResolutionCandidate getVariableCandidateIfInvoke() {
        return this.variableCandidateIfInvoke;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public TypeSubstitutor getKnownTypeParametersResultingSubstitutor() {
        return this.knownTypeParametersResultingSubstitutor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public ResolvedCallableReferenceCallAtom getResolvedCall() {
        return this.resolvedCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public List<ResolvedAtom> getSubResolvedAtoms() {
        return CollectionsKt.emptyList();
    }

    public final FreshVariableNewTypeSubstitutor getFreshVariablesSubstitutor() {
        return this.freshVariablesSubstitutor;
    }

    public final void setFreshVariablesSubstitutor$resolution(FreshVariableNewTypeSubstitutor freshVariableNewTypeSubstitutor) {
        this.freshVariablesSubstitutor = freshVariableNewTypeSubstitutor;
    }

    public final int getNumDefaults() {
        CallableReferenceAdaptation callableReferenceAdaptation = this.callableReferenceAdaptation;
        if (callableReferenceAdaptation != null) {
            return callableReferenceAdaptation.getDefaults();
        }
        return 0;
    }
}
