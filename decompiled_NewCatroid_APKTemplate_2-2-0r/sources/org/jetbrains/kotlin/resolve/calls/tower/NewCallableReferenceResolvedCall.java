package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.components.CallableReceiver;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatchImpl;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentUnmapped;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceArgumentAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewCallableReferenceResolvedCall.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ä\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B)\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ0\u0010:\u001a\u000e\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020<0\u000e2\u0006\u00103\u001a\u00020\u00022\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020>0\u000eH\u0016J\b\u0010?\u001a\u00020&H\u0016J\u0010\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020;H\u0016J\r\u0010C\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010DJ\u000e\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001908H\u0016J\b\u0010F\u001a\u00020GH\u0016J\n\u0010H\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010I\u001a\u00020JH\u0016J\n\u0010K\u001a\u0004\u0018\u00010\u0019H\u0016J\r\u0010L\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010DJ\n\u0010M\u001a\u0004\u0018\u00010NH\u0016J\b\u0010O\u001a\u00020PH\u0016J\u0014\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020R\u0012\u0004\u0012\u00020N0\u000eH\u0016J\u0012\u0010S\u001a\u00020T2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0016\u0010U\u001a\u00020T2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020N08H\u0016J\u0010\u0010W\u001a\u00020T2\u0006\u0010X\u001a\u00020NH\u0016J\u0010\u0010Y\u001a\u00020T2\u0006\u0010X\u001a\u00020NH\u0016R \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u001f\u001a\u0004\u0018\u00010 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u00020&X\u0094D¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020*X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0016\u0010/\u001a\u0004\u0018\u0001008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00102R\u0010\u00103\u001a\u00028\u0000X\u0082.¢\u0006\u0004\n\u0002\u00104R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020908X\u0082.¢\u0006\u0002\n\u0000¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewCallableReferenceResolvedCall;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "resolvedAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceAtom;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceAtom;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;)V", "argumentMappingByOriginal", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "getArgumentMappingByOriginal", "()Ljava/util/Map;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "getDiagnostics", "()Ljava/util/Collection;", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "extensionReceiver", "freshSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "getFreshSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "positionDependentApproximation", "", "getPositionDependentApproximation", "()Z", "psiKotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "getPsiKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "getResolvedAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceAtom;", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceCallAtom;", "getResolvedCallAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceCallAtom;", "resultingDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/TypeApproximator;", "typeArguments", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "argumentToParameterMap", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchImpl;", "valueArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "containsOnlyOnlyInputTypesErrors", "getArgumentMapping", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMapping;", "valueArgument", "getCandidateDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getContextReceivers", "getDataFlowInfoForArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "getDispatchReceiver", "getExplicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExtensionReceiver", "getResultingDescriptor", "getSmartCastDispatchReceiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getStatus", "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "getTypeArguments", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "setResultingSubstitutor", "", "updateContextReceiverTypes", "newTypes", "updateDispatchReceiverType", "newType", "updateExtensionReceiverType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCallableReferenceResolvedCall<D extends CallableDescriptor> extends NewAbstractResolvedCall<D> {
    private final Map<ValueParameterDescriptor, ResolvedCallArgument> argumentMappingByOriginal;
    private final Collection<KotlinCallDiagnostic> diagnostics;
    private ReceiverValue dispatchReceiver;
    private ReceiverValue extensionReceiver;
    private final LanguageVersionSettings languageVersionSettings;
    private final boolean positionDependentApproximation;
    private final PSIKotlinCall psiKotlinCall;
    private final ResolvedCallableReferenceAtom resolvedAtom;
    private D resultingDescriptor;
    private final TypeApproximator typeApproximator;
    private List<? extends UnwrappedType> typeArguments;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public boolean containsOnlyOnlyInputTypesErrors() {
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public KotlinType getSmartCastDispatchReceiverType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateContextReceiverTypes(List<? extends KotlinType> newTypes) {
        Intrinsics.checkNotNullParameter(newTypes, "newTypes");
    }

    public /* synthetic */ NewCallableReferenceResolvedCall(ResolvedCallableReferenceAtom resolvedCallableReferenceAtom, TypeApproximator typeApproximator, LanguageVersionSettings languageVersionSettings, NewTypeSubstitutor newTypeSubstitutor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(resolvedCallableReferenceAtom, typeApproximator, languageVersionSettings, (i & 8) != 0 ? null : newTypeSubstitutor);
    }

    public final ResolvedCallableReferenceAtom getResolvedAtom() {
        return this.resolvedAtom;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public TypeApproximator getTypeApproximator() {
        return this.typeApproximator;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Code duplicated, block: B:13:0x0063  */
    public NewCallableReferenceResolvedCall(ResolvedCallableReferenceAtom resolvedAtom, TypeApproximator typeApproximator, LanguageVersionSettings languageVersionSettings, NewTypeSubstitutor newTypeSubstitutor) {
        PSIKotlinCall psiKotlinCall;
        CallableReceiver extensionReceiver;
        ReceiverValueWithSmartCastInfo receiver;
        ReceiverValue receiverValue;
        CallableReceiver dispatchReceiver;
        ReceiverValueWithSmartCastInfo receiver2;
        super(null);
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        ReceiverValue receiverValue2 = null;
        this.resolvedAtom = resolvedAtom;
        this.typeApproximator = typeApproximator;
        this.languageVersionSettings = languageVersionSettings;
        this.positionDependentApproximation = true;
        this.argumentMappingByOriginal = MapsKt.emptyMap();
        this.diagnostics = CollectionsKt.emptyList();
        if (resolvedAtom instanceof ResolvedCallableReferenceCallAtom) {
            psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(((ResolvedCallableReferenceCallAtom) resolvedAtom).getAtom());
        } else {
            if (!(resolvedAtom instanceof ResolvedCallableReferenceArgumentAtom)) {
                throw new NoWhenBranchMatchedException();
            }
            psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(((ResolvedCallableReferenceArgumentAtom) resolvedAtom).getAtom().getCall());
        }
        this.psiKotlinCall = psiKotlinCall;
        if (resolvedAtom instanceof ResolvedCallableReferenceCallAtom) {
            SimpleKotlinCallArgument extensionReceiverArgument = ((ResolvedCallableReferenceCallAtom) resolvedAtom).getExtensionReceiverArgument();
            if (extensionReceiverArgument != null) {
                receiverValue = PSIKotlinCallsKt.getReceiverValue(extensionReceiverArgument);
            } else {
                receiverValue = null;
            }
        } else {
            if (!(resolvedAtom instanceof ResolvedCallableReferenceArgumentAtom)) {
                throw new NoWhenBranchMatchedException();
            }
            CallableReferenceResolutionCandidate candidate = ((ResolvedCallableReferenceArgumentAtom) resolvedAtom).getCandidate();
            if (candidate == null || (extensionReceiver = candidate.getExtensionReceiver()) == null || (receiver = extensionReceiver.getReceiver()) == null) {
                receiverValue = null;
            } else {
                receiverValue = receiver.getReceiverValue();
            }
        }
        this.extensionReceiver = receiverValue;
        if (resolvedAtom instanceof ResolvedCallableReferenceCallAtom) {
            SimpleKotlinCallArgument dispatchReceiverArgument = ((ResolvedCallableReferenceCallAtom) resolvedAtom).getDispatchReceiverArgument();
            if (dispatchReceiverArgument != null) {
                receiverValue2 = PSIKotlinCallsKt.getReceiverValue(dispatchReceiverArgument);
            }
        } else {
            if (!(resolvedAtom instanceof ResolvedCallableReferenceArgumentAtom)) {
                throw new NoWhenBranchMatchedException();
            }
            CallableReferenceResolutionCandidate candidate2 = ((ResolvedCallableReferenceArgumentAtom) resolvedAtom).getCandidate();
            if (candidate2 != null && (dispatchReceiver = candidate2.getDispatchReceiver()) != null && (receiver2 = dispatchReceiver.getReceiver()) != null) {
                receiverValue2 = receiver2.getReceiverValue();
            }
        }
        this.dispatchReceiver = receiverValue2;
        setResultingSubstitutor(newTypeSubstitutor);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    protected boolean getPositionDependentApproximation() {
        return this.positionDependentApproximation;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Map<ValueParameterDescriptor, ResolvedCallArgument> getArgumentMappingByOriginal() {
        return this.argumentMappingByOriginal;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Collection<KotlinCallDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public ResolvedCallableReferenceCallAtom getResolvedCallAtom() {
        ResolvedCallableReferenceAtom resolvedCallableReferenceAtom = this.resolvedAtom;
        if (resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceCallAtom) {
            return (ResolvedCallableReferenceCallAtom) resolvedCallableReferenceAtom;
        }
        if (!(resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceArgumentAtom)) {
            throw new NoWhenBranchMatchedException();
        }
        CallableReferenceResolutionCandidate candidate = ((ResolvedCallableReferenceArgumentAtom) resolvedCallableReferenceAtom).getCandidate();
        if (candidate != null) {
            return candidate.getResolvedCall();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public PSIKotlinCall getPsiKotlinCall() {
        return this.psiKotlinCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public FreshVariableNewTypeSubstitutor getFreshSubstitutor() {
        ResolvedCallableReferenceAtom resolvedCallableReferenceAtom = this.resolvedAtom;
        if (resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceCallAtom) {
            return ((ResolvedCallableReferenceCallAtom) resolvedCallableReferenceAtom).getFreshVariablesSubstitutor();
        }
        if (!(resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceArgumentAtom)) {
            throw new NoWhenBranchMatchedException();
        }
        CallableReferenceResolutionCandidate candidate = ((ResolvedCallableReferenceArgumentAtom) resolvedCallableReferenceAtom).getCandidate();
        if (candidate != null) {
            return candidate.getFreshVariablesSubstitutor();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public KotlinCall getKotlinCall() {
        CallableReferenceResolutionAtom kotlinCall;
        ResolvedCallableReferenceAtom resolvedCallableReferenceAtom = this.resolvedAtom;
        if (!(resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceArgumentAtom)) {
            if (resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceCallAtom) {
                return ((ResolvedCallableReferenceCallAtom) resolvedCallableReferenceAtom).getAtom();
            }
            throw new NoWhenBranchMatchedException();
        }
        CallableReferenceResolutionCandidate candidate = ((ResolvedCallableReferenceArgumentAtom) resolvedCallableReferenceAtom).getCandidate();
        if (candidate == null || (kotlinCall = candidate.getKotlinCall()) == null) {
            return null;
        }
        return kotlinCall.getCall();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ReceiverValue getExtensionReceiver() {
        return this.extensionReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ReceiverValue getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ReceiverValue> getContextReceivers() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateDispatchReceiverType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        ReceiverValue receiverValue = this.dispatchReceiver;
        if (Intrinsics.areEqual(receiverValue != null ? receiverValue.getType() : null, newType)) {
            return;
        }
        ReceiverValue receiverValue2 = this.dispatchReceiver;
        this.dispatchReceiver = receiverValue2 != null ? receiverValue2.mo7466replaceType(newType) : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateExtensionReceiverType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        ReceiverValue receiverValue = this.extensionReceiver;
        if (Intrinsics.areEqual(receiverValue != null ? receiverValue.getType() : null, newType)) {
            return;
        }
        ReceiverValue receiverValue2 = this.extensionReceiver;
        this.extensionReceiver = receiverValue2 != null ? receiverValue2.mo7466replaceType(newType) : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    /* JADX INFO: renamed from: getCandidateDescriptor */
    public D get$suspendPropertyDescriptor() {
        ResolvedCallableReferenceAtom resolvedCallableReferenceAtom = this.resolvedAtom;
        if (resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceCallAtom) {
            D d = (D) ((ResolvedCallableReferenceCallAtom) resolvedCallableReferenceAtom).get_candidateDescriptor();
            Intrinsics.checkNotNull(d, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.calls.tower.NewCallableReferenceResolvedCall");
            return d;
        }
        if (!(resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceArgumentAtom)) {
            throw new NoWhenBranchMatchedException();
        }
        CallableReferenceResolutionCandidate candidate = ((ResolvedCallableReferenceArgumentAtom) resolvedCallableReferenceAtom).getCandidate();
        D d2 = candidate != null ? (D) candidate.getCandidate() : null;
        Intrinsics.checkNotNull(d2, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.calls.tower.NewCallableReferenceResolvedCall");
        return d2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public D getResultingDescriptor() {
        D d = this.resultingDescriptor;
        if (d != null) {
            return d;
        }
        Intrinsics.throwUninitializedPropertyAccessException("resultingDescriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ArgumentMapping getArgumentMapping(ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        return ArgumentUnmapped.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<TypeParameterDescriptor, KotlinType> getTypeArguments() {
        List<TypeParameterDescriptor> typeParameters = get$suspendPropertyDescriptor().getTypeParameters();
        Intrinsics.checkNotNull(typeParameters);
        List<? extends UnwrappedType> list = null;
        if (typeParameters.isEmpty()) {
            typeParameters = null;
        }
        if (typeParameters == null) {
            return MapsKt.emptyMap();
        }
        List<TypeParameterDescriptor> list2 = typeParameters;
        List<? extends UnwrappedType> list3 = this.typeArguments;
        if (list3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("typeArguments");
        } else {
            list = list3;
        }
        return MapsKt.toMap(CollectionsKt.zip(list2, list));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ResolutionStatus getStatus() {
        return ResolvedCallUtilKt.toResolutionStatus(CandidateApplicability.RESOLVED);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ExplicitReceiverKind getExplicitReceiverKind() {
        ExplicitReceiverKind explicitReceiverKind;
        ResolvedCallableReferenceAtom resolvedCallableReferenceAtom = this.resolvedAtom;
        if (resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceArgumentAtom) {
            CallableReferenceResolutionCandidate candidate = ((ResolvedCallableReferenceArgumentAtom) resolvedCallableReferenceAtom).getCandidate();
            return (candidate == null || (explicitReceiverKind = candidate.getExplicitReceiverKind()) == null) ? ExplicitReceiverKind.NO_EXPLICIT_RECEIVER : explicitReceiverKind;
        }
        if (resolvedCallableReferenceAtom instanceof ResolvedCallableReferenceCallAtom) {
            return ((ResolvedCallableReferenceCallAtom) resolvedCallableReferenceAtom).getExplicitReceiverKind();
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public DataFlowInfoForArguments getDataFlowInfoForArguments() {
        return new MutableDataFlowInfoForArguments.WithoutArgumentsCheck(DataFlowInfo.INSTANCE.getEMPTY());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void setResultingSubstitutor(NewTypeSubstitutor substitutor) {
        substituteReceivers(substitutor);
        D d = (D) substitutedResultingDescriptor(substitutor);
        Intrinsics.checkNotNull(d, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.calls.tower.NewCallableReferenceResolvedCall");
        this.resultingDescriptor = d;
        FreshVariableNewTypeSubstitutor freshSubstitutor = getFreshSubstitutor();
        if (freshSubstitutor != null) {
            List<TypeVariableFromCallableDescriptor> freshVariables = freshSubstitutor.getFreshVariables();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(freshVariables, 10));
            Iterator<T> it = freshVariables.iterator();
            while (it.hasNext()) {
                UnwrappedType unwrappedTypeSafeSubstitute = (substitutor == null ? FreshVariableNewTypeSubstitutor.INSTANCE.getEmpty() : substitutor).safeSubstitute(((TypeVariableFromCallableDescriptor) it.next()).getDefaultType());
                UnwrappedType unwrappedTypeApproximateToSuperType = getTypeApproximator().approximateToSuperType(unwrappedTypeSafeSubstitute, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.IntegerLiteralsTypesApproximation.INSTANCE);
                if (unwrappedTypeApproximateToSuperType != null) {
                    unwrappedTypeSafeSubstitute = unwrappedTypeApproximateToSuperType;
                }
                arrayList.add(unwrappedTypeSafeSubstitute);
            }
            this.typeArguments = arrayList;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Map<ValueArgument, ArgumentMatchImpl> argumentToParameterMap(CallableDescriptor resultingDescriptor, Map<ValueParameterDescriptor, ? extends ResolvedValueArgument> valueArguments) {
        Intrinsics.checkNotNullParameter(resultingDescriptor, "resultingDescriptor");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        return MapsKt.emptyMap();
    }
}
