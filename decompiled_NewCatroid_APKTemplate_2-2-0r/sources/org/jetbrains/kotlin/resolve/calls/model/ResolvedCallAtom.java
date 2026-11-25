package org.jetbrains.kotlin.resolve.calls.model;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.TypeArgumentsToParametersMapper;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020\u001aH&R\u001e\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u001e\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000e0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\bR\u001e\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00110\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u001e\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00110\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\bR\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX¦\u000e¢\u0006\f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0014\u0010$\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0012\u0010'\u001a\u00020(X¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u001a\u0010+\u001a\u0004\u0018\u00010\u001fX¦\u000e¢\u0006\f\u001a\u0004\b,\u0010&\"\u0004\b-\u0010.R\u001a\u0010/\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b0\u0010!R\u0012\u00101\u001a\u000202X¦\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u0012\u00105\u001a\u000206X¦\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0012\u00109\u001a\u00020:X¦\u0004¢\u0006\u0006\u001a\u0004\b;\u0010<¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "()V", "argumentMappingByOriginal", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "getArgumentMappingByOriginal", "()Ljava/util/Map;", "argumentsWithConstantConversion", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstant;", "getArgumentsWithConstantConversion", "argumentsWithConversion", "Lorg/jetbrains/kotlin/resolve/calls/model/SamConversionDescription;", "getArgumentsWithConversion", "argumentsWithSuspendConversion", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "getArgumentsWithSuspendConversion", "argumentsWithUnitConversion", "getArgumentsWithUnitConversion", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "candidateDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getCandidateDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "contextReceiversArguments", "", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "getContextReceiversArguments", "()Ljava/util/List;", "setContextReceiversArguments", "(Ljava/util/List;)V", "dispatchReceiverArgument", "getDispatchReceiverArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExplicitReceiverKind", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "extensionReceiverArgument", "getExtensionReceiverArgument", "setExtensionReceiverArgument", "(Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;)V", "extensionReceiverArgumentCandidates", "getExtensionReceiverArgumentCandidates", "freshVariablesSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "getFreshVariablesSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "knownParametersSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "getKnownParametersSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "typeArgumentMappingByOriginal", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;", "getTypeArgumentMappingByOriginal", "()Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;", "setCandidateDescriptor", "", "newCandidateDescriptor", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolvedCallAtom extends ResolvedAtom {
    public abstract Map<ValueParameterDescriptor, ResolvedCallArgument> getArgumentMappingByOriginal();

    public abstract Map<KotlinCallArgument, IntegerValueTypeConstant> getArgumentsWithConstantConversion();

    public abstract Map<KotlinCallArgument, SamConversionDescription> getArgumentsWithConversion();

    public abstract Map<KotlinCallArgument, UnwrappedType> getArgumentsWithSuspendConversion();

    public abstract Map<KotlinCallArgument, UnwrappedType> getArgumentsWithUnitConversion();

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public abstract KotlinCall getAtom();

    /* JADX INFO: renamed from: getCandidateDescriptor */
    public abstract CallableDescriptor get_candidateDescriptor();

    public abstract List<SimpleKotlinCallArgument> getContextReceiversArguments();

    public abstract SimpleKotlinCallArgument getDispatchReceiverArgument();

    public abstract ExplicitReceiverKind getExplicitReceiverKind();

    public abstract SimpleKotlinCallArgument getExtensionReceiverArgument();

    public abstract List<SimpleKotlinCallArgument> getExtensionReceiverArgumentCandidates();

    public abstract FreshVariableNewTypeSubstitutor getFreshVariablesSubstitutor();

    public abstract NewTypeSubstitutor getKnownParametersSubstitutor();

    public abstract TypeArgumentsToParametersMapper.TypeArgumentsMapping getTypeArgumentMappingByOriginal();

    public abstract void setCandidateDescriptor(CallableDescriptor newCandidateDescriptor);

    public abstract void setContextReceiversArguments(List<? extends SimpleKotlinCallArgument> list);

    public abstract void setExtensionReceiverArgument(SimpleKotlinCallArgument simpleKotlinCallArgument);

    public ResolvedCallAtom() {
        super(null);
    }
}
