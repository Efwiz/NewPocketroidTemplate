package org.jetbrains.kotlin.resolve.calls.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.TypeArgumentsToParametersMapper;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolvedCallAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001BY\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011J\u0016\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020\u001c2\u0006\u0010`\u001a\u00020 J\u0016\u0010a\u001a\u00020^2\u0006\u0010_\u001a\u00020\u001c2\u0006\u0010b\u001a\u00020#J\u0016\u0010c\u001a\u00020^2\u0006\u0010_\u001a\u00020\u001c2\u0006\u0010d\u001a\u00020\u000eJ\u0016\u0010e\u001a\u00020^2\u0006\u0010_\u001a\u00020\u001c2\u0006\u0010d\u001a\u00020\u000eJ\u0016\u0010f\u001a\u00020^2\f\u0010g\u001a\b\u0012\u0004\u0012\u00020h0\fH\u0016J\u0010\u0010i\u001a\u00020^2\u0006\u0010j\u001a\u00020\u0005H\u0016J\b\u0010k\u001a\u00020lH\u0016R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR&\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00150\u0014X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0018\"\u0004\b\u001e\u0010\u001aR \u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020 0\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0018R \u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020#0\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\u0018R \u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u000e0\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u0018R \u0010'\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u000e0\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\u0018R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R \u00100\u001a\b\u0012\u0004\u0012\u00020\t0\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u0016\u0010\b\u001a\u0004\u0018\u00010\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u001c\u0010\n\u001a\u0004\u0018\u00010\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u00106\"\u0004\b:\u0010;R\u001c\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u00102R\u001a\u0010=\u001a\u00020>X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u0011\u0010C\u001a\u00020D8F¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0011\u0010G\u001a\u00020D8F¢\u0006\u0006\u001a\u0004\bH\u0010FR\u001a\u0010I\u001a\u00020JX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bO\u0010PR.\u0010Q\u001a\"\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020#\u0018\u00010Rj\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020#\u0018\u0001`SX\u0082\u000e¢\u0006\u0002\n\u0000R.\u0010T\u001a\"\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020 \u0018\u00010Rj\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020 \u0018\u0001`SX\u0082\u000e¢\u0006\u0002\n\u0000R.\u0010U\u001a\"\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u000e\u0018\u00010Rj\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u000e\u0018\u0001`SX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010V\u001a\u00020WX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R.\u0010\\\u001a\"\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u000e\u0018\u00010Rj\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u000e\u0018\u0001`SX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006m"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "originalCandidateDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "dispatchReceiverArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "extensionReceiverArgument", "extensionReceiverArgumentCandidates", "", "reflectionCandidateType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;Ljava/util/List;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;)V", "_candidateDescriptor", "argumentMappingByOriginal", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "getArgumentMappingByOriginal", "()Ljava/util/Map;", "setArgumentMappingByOriginal", "(Ljava/util/Map;)V", "argumentToCandidateParameter", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getArgumentToCandidateParameter", "setArgumentToCandidateParameter", "argumentsWithConstantConversion", "Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstant;", "getArgumentsWithConstantConversion", "argumentsWithConversion", "Lorg/jetbrains/kotlin/resolve/calls/model/SamConversionDescription;", "getArgumentsWithConversion", "argumentsWithSuspendConversion", "getArgumentsWithSuspendConversion", "argumentsWithUnitConversion", "getArgumentsWithUnitConversion", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getCandidate", "()Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "candidateDescriptor", "getCandidateDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "contextReceiversArguments", "getContextReceiversArguments", "()Ljava/util/List;", "setContextReceiversArguments", "(Ljava/util/List;)V", "getDispatchReceiverArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "getExplicitReceiverKind", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExtensionReceiverArgument", "setExtensionReceiverArgument", "(Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;)V", "getExtensionReceiverArgumentCandidates", "freshVariablesSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "getFreshVariablesSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "setFreshVariablesSubstitutor", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;)V", "hasSamConversion", "", "getHasSamConversion", "()Z", "hasSuspendConversion", "getHasSuspendConversion", "knownParametersSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "getKnownParametersSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "setKnownParametersSubstitutor", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;)V", "getReflectionCandidateType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "samAdapterMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "signedUnsignedConstantConversions", "suspendAdapterMap", "typeArgumentMappingByOriginal", "Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;", "getTypeArgumentMappingByOriginal", "()Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;", "setTypeArgumentMappingByOriginal", "(Lorg/jetbrains/kotlin/resolve/calls/components/TypeArgumentsToParametersMapper$TypeArgumentsMapping;)V", "unitAdapterMap", "registerArgumentWithConstantConversion", "", "argument", "convertedConstant", "registerArgumentWithSamConversion", "samConversionDescription", "registerArgumentWithSuspendConversion", "convertedType", "registerArgumentWithUnitConversion", "setAnalyzedResults", "subResolvedAtoms", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "setCandidateDescriptor", "newCandidateDescriptor", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class MutableResolvedCallAtom extends ResolvedCallAtom {
    private CallableDescriptor _candidateDescriptor;
    public Map<ValueParameterDescriptor, ? extends ResolvedCallArgument> argumentMappingByOriginal;
    public Map<KotlinCallArgument, ? extends ValueParameterDescriptor> argumentToCandidateParameter;
    private final KotlinCall atom;
    private final CallableReferenceResolutionCandidate candidate;
    private List<? extends SimpleKotlinCallArgument> contextReceiversArguments;
    private final SimpleKotlinCallArgument dispatchReceiverArgument;
    private final ExplicitReceiverKind explicitReceiverKind;
    private SimpleKotlinCallArgument extensionReceiverArgument;
    private final List<SimpleKotlinCallArgument> extensionReceiverArgumentCandidates;
    public FreshVariableNewTypeSubstitutor freshVariablesSubstitutor;
    public NewTypeSubstitutor knownParametersSubstitutor;
    private final UnwrappedType reflectionCandidateType;
    private HashMap<KotlinCallArgument, SamConversionDescription> samAdapterMap;
    private HashMap<KotlinCallArgument, IntegerValueTypeConstant> signedUnsignedConstantConversions;
    private HashMap<KotlinCallArgument, UnwrappedType> suspendAdapterMap;
    public TypeArgumentsToParametersMapper.TypeArgumentsMapping typeArgumentMappingByOriginal;
    private HashMap<KotlinCallArgument, UnwrappedType> unitAdapterMap;

    public /* synthetic */ MutableResolvedCallAtom(KotlinCall kotlinCall, CallableDescriptor callableDescriptor, ExplicitReceiverKind explicitReceiverKind, SimpleKotlinCallArgument simpleKotlinCallArgument, SimpleKotlinCallArgument simpleKotlinCallArgument2, List list, UnwrappedType unwrappedType, CallableReferenceResolutionCandidate callableReferenceResolutionCandidate, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinCall, callableDescriptor, explicitReceiverKind, simpleKotlinCallArgument, simpleKotlinCallArgument2, list, (i & 64) != 0 ? null : unwrappedType, (i & 128) != 0 ? null : callableReferenceResolutionCandidate);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public KotlinCall getAtom() {
        return this.atom;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public ExplicitReceiverKind getExplicitReceiverKind() {
        return this.explicitReceiverKind;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public SimpleKotlinCallArgument getDispatchReceiverArgument() {
        return this.dispatchReceiverArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public SimpleKotlinCallArgument getExtensionReceiverArgument() {
        return this.extensionReceiverArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public void setExtensionReceiverArgument(SimpleKotlinCallArgument simpleKotlinCallArgument) {
        this.extensionReceiverArgument = simpleKotlinCallArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public List<SimpleKotlinCallArgument> getExtensionReceiverArgumentCandidates() {
        return this.extensionReceiverArgumentCandidates;
    }

    public UnwrappedType getReflectionCandidateType() {
        return this.reflectionCandidateType;
    }

    public CallableReferenceResolutionCandidate getCandidate() {
        return this.candidate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MutableResolvedCallAtom(KotlinCall atom, CallableDescriptor originalCandidateDescriptor, ExplicitReceiverKind explicitReceiverKind, SimpleKotlinCallArgument simpleKotlinCallArgument, SimpleKotlinCallArgument simpleKotlinCallArgument2, List<? extends SimpleKotlinCallArgument> list, UnwrappedType unwrappedType, CallableReferenceResolutionCandidate callableReferenceResolutionCandidate) {
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(originalCandidateDescriptor, "originalCandidateDescriptor");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        this.atom = atom;
        this.explicitReceiverKind = explicitReceiverKind;
        this.dispatchReceiverArgument = simpleKotlinCallArgument;
        this.extensionReceiverArgument = simpleKotlinCallArgument2;
        this.extensionReceiverArgumentCandidates = list;
        this.reflectionCandidateType = unwrappedType;
        this.candidate = callableReferenceResolutionCandidate;
        this.contextReceiversArguments = CollectionsKt.emptyList();
        this._candidateDescriptor = originalCandidateDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public List<SimpleKotlinCallArgument> getContextReceiversArguments() {
        return this.contextReceiversArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public void setContextReceiversArguments(List<? extends SimpleKotlinCallArgument> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.contextReceiversArguments = list;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public TypeArgumentsToParametersMapper.TypeArgumentsMapping getTypeArgumentMappingByOriginal() {
        TypeArgumentsToParametersMapper.TypeArgumentsMapping typeArgumentsMapping = this.typeArgumentMappingByOriginal;
        if (typeArgumentsMapping != null) {
            return typeArgumentsMapping;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeArgumentMappingByOriginal");
        return null;
    }

    public void setTypeArgumentMappingByOriginal(TypeArgumentsToParametersMapper.TypeArgumentsMapping typeArgumentsMapping) {
        Intrinsics.checkNotNullParameter(typeArgumentsMapping, "<set-?>");
        this.typeArgumentMappingByOriginal = typeArgumentsMapping;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public Map<ValueParameterDescriptor, ResolvedCallArgument> getArgumentMappingByOriginal() {
        Map map = this.argumentMappingByOriginal;
        if (map != null) {
            return map;
        }
        Intrinsics.throwUninitializedPropertyAccessException("argumentMappingByOriginal");
        return null;
    }

    public void setArgumentMappingByOriginal(Map<ValueParameterDescriptor, ? extends ResolvedCallArgument> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.argumentMappingByOriginal = map;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public FreshVariableNewTypeSubstitutor getFreshVariablesSubstitutor() {
        FreshVariableNewTypeSubstitutor freshVariableNewTypeSubstitutor = this.freshVariablesSubstitutor;
        if (freshVariableNewTypeSubstitutor != null) {
            return freshVariableNewTypeSubstitutor;
        }
        Intrinsics.throwUninitializedPropertyAccessException("freshVariablesSubstitutor");
        return null;
    }

    public void setFreshVariablesSubstitutor(FreshVariableNewTypeSubstitutor freshVariableNewTypeSubstitutor) {
        Intrinsics.checkNotNullParameter(freshVariableNewTypeSubstitutor, "<set-?>");
        this.freshVariablesSubstitutor = freshVariableNewTypeSubstitutor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public NewTypeSubstitutor getKnownParametersSubstitutor() {
        NewTypeSubstitutor newTypeSubstitutor = this.knownParametersSubstitutor;
        if (newTypeSubstitutor != null) {
            return newTypeSubstitutor;
        }
        Intrinsics.throwUninitializedPropertyAccessException("knownParametersSubstitutor");
        return null;
    }

    public void setKnownParametersSubstitutor(NewTypeSubstitutor newTypeSubstitutor) {
        Intrinsics.checkNotNullParameter(newTypeSubstitutor, "<set-?>");
        this.knownParametersSubstitutor = newTypeSubstitutor;
    }

    public final Map<KotlinCallArgument, ValueParameterDescriptor> getArgumentToCandidateParameter() {
        Map map = this.argumentToCandidateParameter;
        if (map != null) {
            return map;
        }
        Intrinsics.throwUninitializedPropertyAccessException("argumentToCandidateParameter");
        return null;
    }

    public final void setArgumentToCandidateParameter(Map<KotlinCallArgument, ? extends ValueParameterDescriptor> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.argumentToCandidateParameter = map;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    /* JADX INFO: renamed from: getCandidateDescriptor, reason: from getter */
    public CallableDescriptor get_candidateDescriptor() {
        return this._candidateDescriptor;
    }

    public final boolean getHasSamConversion() {
        return this.samAdapterMap != null;
    }

    public final boolean getHasSuspendConversion() {
        return this.suspendAdapterMap != null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public Map<KotlinCallArgument, SamConversionDescription> getArgumentsWithConversion() {
        HashMap<KotlinCallArgument, SamConversionDescription> map = this.samAdapterMap;
        return map != null ? map : MapsKt.emptyMap();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public Map<KotlinCallArgument, UnwrappedType> getArgumentsWithSuspendConversion() {
        HashMap<KotlinCallArgument, UnwrappedType> map = this.suspendAdapterMap;
        return map != null ? map : MapsKt.emptyMap();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public Map<KotlinCallArgument, UnwrappedType> getArgumentsWithUnitConversion() {
        HashMap<KotlinCallArgument, UnwrappedType> map = this.unitAdapterMap;
        return map != null ? map : MapsKt.emptyMap();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public Map<KotlinCallArgument, IntegerValueTypeConstant> getArgumentsWithConstantConversion() {
        HashMap<KotlinCallArgument, IntegerValueTypeConstant> map = this.signedUnsignedConstantConversions;
        return map != null ? map : MapsKt.emptyMap();
    }

    public final void registerArgumentWithSamConversion(KotlinCallArgument argument, SamConversionDescription samConversionDescription) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(samConversionDescription, "samConversionDescription");
        if (this.samAdapterMap == null) {
            this.samAdapterMap = new HashMap<>();
        }
        HashMap<KotlinCallArgument, SamConversionDescription> map = this.samAdapterMap;
        Intrinsics.checkNotNull(map);
        map.put(argument, samConversionDescription);
    }

    public final void registerArgumentWithSuspendConversion(KotlinCallArgument argument, UnwrappedType convertedType) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(convertedType, "convertedType");
        if (this.suspendAdapterMap == null) {
            this.suspendAdapterMap = new HashMap<>();
        }
        HashMap<KotlinCallArgument, UnwrappedType> map = this.suspendAdapterMap;
        Intrinsics.checkNotNull(map);
        map.put(argument, convertedType);
    }

    public final void registerArgumentWithUnitConversion(KotlinCallArgument argument, UnwrappedType convertedType) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(convertedType, "convertedType");
        if (this.unitAdapterMap == null) {
            this.unitAdapterMap = new HashMap<>();
        }
        HashMap<KotlinCallArgument, UnwrappedType> map = this.unitAdapterMap;
        Intrinsics.checkNotNull(map);
        map.put(argument, convertedType);
    }

    public final void registerArgumentWithConstantConversion(KotlinCallArgument argument, IntegerValueTypeConstant convertedConstant) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(convertedConstant, "convertedConstant");
        if (this.signedUnsignedConstantConversions == null) {
            this.signedUnsignedConstantConversions = new HashMap<>();
        }
        HashMap<KotlinCallArgument, IntegerValueTypeConstant> map = this.signedUnsignedConstantConversions;
        Intrinsics.checkNotNull(map);
        map.put(argument, convertedConstant);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public void setAnalyzedResults(List<? extends ResolvedAtom> subResolvedAtoms) {
        Intrinsics.checkNotNullParameter(subResolvedAtoms, "subResolvedAtoms");
        super.setAnalyzedResults(subResolvedAtoms);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom
    public void setCandidateDescriptor(CallableDescriptor newCandidateDescriptor) {
        Intrinsics.checkNotNullParameter(newCandidateDescriptor, "newCandidateDescriptor");
        if (Intrinsics.areEqual(newCandidateDescriptor, get_candidateDescriptor())) {
            return;
        }
        this._candidateDescriptor = newCandidateDescriptor;
    }

    public String toString() {
        return getAtom() + ", candidate = " + get_candidateDescriptor();
    }
}
