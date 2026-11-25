package org.jetbrains.kotlin.resolve.calls.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCSKt;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleErrorResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.LowerPriorityToPreserveCompatibility;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateWithBoundDispatchReceiver;
import org.jetbrains.kotlin.resolve.calls.tower.HiddenDescriptor;
import org.jetbrains.kotlin.resolve.calls.tower.HiddenExtensionRelatedToDynamicTypes;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.resolve.calls.tower.ResolutionDiagnostic;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.error.ErrorScope;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: SimpleCandidateFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJT\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010\"2\u000e\u0010$\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\b\u0010)\u001a\u0004\u0018\u00010*H\u0002J\u000e\u0010\u001c\u001a\u00020\u00022\u0006\u0010+\u001a\u00020,J&\u0010\u001c\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.2\u0006\u0010\u001f\u001a\u00020 2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002000%H\u0016J\"\u0010\u001c\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.2\u0006\u0010\u001f\u001a\u00020 2\b\u00101\u001a\u0004\u0018\u000100H\u0016J\b\u00102\u001a\u00020\u0002H\u0016J\u001e\u00103\u001a\u0004\u0018\u00010\"2\b\u00104\u001a\u0004\u0018\u0001052\b\u00106\u001a\u0004\u0018\u000100H\u0002J\u0016\u00107\u001a\u0004\u0018\u000105*\u00020\b2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0016\u00108\u001a\u0004\u0018\u000105*\u00020\b2\u0006\u0010\u001f\u001a\u00020 H\u0002R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/SimpleCandidateFactory;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleResolutionCandidate;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;)V", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getBaseSystem", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getCallComponents", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getInferenceSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getResolutionCallbacks", "()Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "createCandidate", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "dispatchArgumentReceiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "extensionArgumentReceiver", "extensionArgumentReceiverCandidates", "", "initialDiagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "knownSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "givenCandidate", "Lorg/jetbrains/kotlin/resolve/calls/model/GivenCandidate;", "towerCandidate", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "extensionReceiverCandidates", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "extensionReceiver", "createErrorCandidate", "createReceiverArgument", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "fromResolution", "getExplicitDispatchReceiver", "getExplicitExtensionReceiver", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleCandidateFactory implements CandidateFactory<SimpleResolutionCandidate> {
    private final ConstraintStorage baseSystem;
    private final KotlinCallComponents callComponents;
    private final InferenceSession inferenceSession;
    private final KotlinCall kotlinCall;
    private final KotlinResolutionCallbacks resolutionCallbacks;
    private final ImplicitScopeTower scopeTower;

    /* JADX INFO: compiled from: SimpleCandidateFactory.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ExplicitReceiverKind.values().length];
            try {
                iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public SimpleCandidateFactory(KotlinCallComponents callComponents, ImplicitScopeTower scopeTower, KotlinCall kotlinCall, KotlinResolutionCallbacks resolutionCallbacks) {
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        this.callComponents = callComponents;
        this.scopeTower = scopeTower;
        this.kotlinCall = kotlinCall;
        this.resolutionCallbacks = resolutionCallbacks;
        InferenceSession inferenceSession = resolutionCallbacks.getInferenceSession();
        this.inferenceSession = inferenceSession;
        NewConstraintSystemImpl NewConstraintSystemImpl = ClassicTypeSystemContextForCSKt.NewConstraintSystemImpl(callComponents.getConstraintInjector(), callComponents.getBuiltIns(), callComponents.getKotlinTypeRefiner(), callComponents.getLanguageVersionSettings());
        if (!inferenceSession.getResolveReceiverIndependently()) {
            NewConstraintSystemImpl newConstraintSystemImpl = NewConstraintSystemImpl;
            DescriptorRelatedInferenceUtilsKt.addSubsystemFromArgument(newConstraintSystemImpl, kotlinCall.getExplicitReceiver());
            DescriptorRelatedInferenceUtilsKt.addSubsystemFromArgument(newConstraintSystemImpl, kotlinCall.getDispatchReceiverForInvokeExtension());
        }
        Iterator<KotlinCallArgument> it = kotlinCall.getArgumentsInParenthesis().iterator();
        while (it.hasNext()) {
            DescriptorRelatedInferenceUtilsKt.addSubsystemFromArgument(NewConstraintSystemImpl, it.next());
        }
        DescriptorRelatedInferenceUtilsKt.addSubsystemFromArgument(NewConstraintSystemImpl, this.kotlinCall.getExternalArgument());
        NewConstraintSystemImpl.addOtherSystem(this.inferenceSession.currentConstraintSystem());
        this.baseSystem = NewConstraintSystemImpl.asReadOnlyStorage();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
    public /* bridge */ /* synthetic */ Candidate createCandidate(CandidateWithBoundDispatchReceiver candidateWithBoundDispatchReceiver, ExplicitReceiverKind explicitReceiverKind, List list) {
        return createCandidate(candidateWithBoundDispatchReceiver, explicitReceiverKind, (List<ReceiverValueWithSmartCastInfo>) list);
    }

    public final KotlinCallComponents getCallComponents() {
        return this.callComponents;
    }

    public final ImplicitScopeTower getScopeTower() {
        return this.scopeTower;
    }

    public final KotlinCall getKotlinCall() {
        return this.kotlinCall;
    }

    public final KotlinResolutionCallbacks getResolutionCallbacks() {
        return this.resolutionCallbacks;
    }

    public final InferenceSession getInferenceSession() {
        return this.inferenceSession;
    }

    public final ConstraintStorage getBaseSystem() {
        return this.baseSystem;
    }

    private final SimpleKotlinCallArgument createReceiverArgument(ReceiverKotlinCallArgument explicitReceiver, ReceiverValueWithSmartCastInfo fromResolution) {
        SimpleKotlinCallArgument simpleKotlinCallArgument = explicitReceiver instanceof SimpleKotlinCallArgument ? (SimpleKotlinCallArgument) explicitReceiver : null;
        if (simpleKotlinCallArgument == null) {
            return fromResolution != null ? ReceiverExpressionKotlinCallArgument.INSTANCE.invoke(fromResolution, false, this.kotlinCall.getIsForImplicitInvoke()) : null;
        }
        return simpleKotlinCallArgument;
    }

    private final ReceiverKotlinCallArgument getExplicitDispatchReceiver(KotlinCall kotlinCall, ExplicitReceiverKind explicitReceiverKind) {
        int i = WhenMappings.$EnumSwitchMapping$0[explicitReceiverKind.ordinal()];
        if (i == 1) {
            return kotlinCall.getExplicitReceiver();
        }
        if (i != 2) {
            return null;
        }
        return kotlinCall.getDispatchReceiverForInvokeExtension();
    }

    private final ReceiverKotlinCallArgument getExplicitExtensionReceiver(KotlinCall kotlinCall, ExplicitReceiverKind explicitReceiverKind) {
        int i = WhenMappings.$EnumSwitchMapping$0[explicitReceiverKind.ordinal()];
        if (i == 2 || i == 3) {
            return kotlinCall.getExplicitReceiver();
        }
        return null;
    }

    public final SimpleResolutionCandidate createCandidate(GivenCandidate givenCandidate) {
        Intrinsics.checkNotNullParameter(givenCandidate, "givenCandidate");
        ReceiverKotlinCallArgument explicitReceiver = this.kotlinCall.getExplicitReceiver();
        SimpleKotlinCallArgument simpleKotlinCallArgument = explicitReceiver instanceof SimpleKotlinCallArgument ? (SimpleKotlinCallArgument) explicitReceiver : null;
        boolean zIsSafeCall = simpleKotlinCallArgument != null ? simpleKotlinCallArgument.getIsSafeCall() : false;
        ExplicitReceiverKind explicitReceiverKind = givenCandidate.getDispatchReceiver() == null ? ExplicitReceiverKind.NO_EXPLICIT_RECEIVER : ExplicitReceiverKind.DISPATCH_RECEIVER;
        ReceiverValueWithSmartCastInfo dispatchReceiver = givenCandidate.getDispatchReceiver();
        return createCandidate(givenCandidate.getDescriptor(), explicitReceiverKind, dispatchReceiver != null ? ReceiverExpressionKotlinCallArgument.Companion.invoke$default(ReceiverExpressionKotlinCallArgument.INSTANCE, dispatchReceiver, zIsSafeCall, false, 4, null) : null, null, null, CollectionsKt.emptyList(), givenCandidate.getKnownTypeParametersResultingSubstitutor());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
    public SimpleResolutionCandidate createCandidate(CandidateWithBoundDispatchReceiver towerCandidate, ExplicitReceiverKind explicitReceiverKind, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(towerCandidate, "towerCandidate");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        SimpleKotlinCallArgument simpleKotlinCallArgumentCreateReceiverArgument = createReceiverArgument(getExplicitDispatchReceiver(this.kotlinCall, explicitReceiverKind), towerCandidate.getDispatchReceiver());
        SimpleKotlinCallArgument simpleKotlinCallArgumentCreateReceiverArgument2 = createReceiverArgument(getExplicitExtensionReceiver(this.kotlinCall, explicitReceiverKind), extensionReceiver);
        CallableDescriptor descriptor = towerCandidate.getDescriptor();
        List<ResolutionDiagnostic> diagnostics = towerCandidate.getDiagnostics();
        if ((descriptor instanceof PropertyDescriptor) && SimpleCandidateFactoryKt.isSyntheticEnumEntries((PropertyDescriptor) descriptor)) {
            diagnostics = CollectionsKt.plus((Collection<? extends KotlinConstraintSystemDiagnostic>) diagnostics, KotlinCallDiagnosticsKt.asDiagnostic(new LowerPriorityToPreserveCompatibility(false)));
        }
        return createCandidate(descriptor, explicitReceiverKind, simpleKotlinCallArgumentCreateReceiverArgument, simpleKotlinCallArgumentCreateReceiverArgument2, null, diagnostics, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
    public SimpleResolutionCandidate createCandidate(CandidateWithBoundDispatchReceiver towerCandidate, ExplicitReceiverKind explicitReceiverKind, List<ReceiverValueWithSmartCastInfo> extensionReceiverCandidates) {
        Intrinsics.checkNotNullParameter(towerCandidate, "towerCandidate");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        Intrinsics.checkNotNullParameter(extensionReceiverCandidates, "extensionReceiverCandidates");
        SimpleKotlinCallArgument simpleKotlinCallArgumentCreateReceiverArgument = createReceiverArgument(getExplicitDispatchReceiver(this.kotlinCall, explicitReceiverKind), towerCandidate.getDispatchReceiver());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = extensionReceiverCandidates.iterator();
        while (it.hasNext()) {
            SimpleKotlinCallArgument simpleKotlinCallArgumentCreateReceiverArgument2 = createReceiverArgument(getExplicitExtensionReceiver(this.kotlinCall, explicitReceiverKind), (ReceiverValueWithSmartCastInfo) it.next());
            if (simpleKotlinCallArgumentCreateReceiverArgument2 != null) {
                arrayList.add(simpleKotlinCallArgumentCreateReceiverArgument2);
            }
        }
        return createCandidate(towerCandidate.getDescriptor(), explicitReceiverKind, simpleKotlinCallArgumentCreateReceiverArgument, null, arrayList, towerCandidate.getDiagnostics(), null);
    }

    private final SimpleResolutionCandidate createCandidate(CallableDescriptor descriptor, ExplicitReceiverKind explicitReceiverKind, SimpleKotlinCallArgument dispatchArgumentReceiver, SimpleKotlinCallArgument extensionArgumentReceiver, List<? extends SimpleKotlinCallArgument> extensionArgumentReceiverCandidates, Collection<? extends KotlinCallDiagnostic> initialDiagnostics, TypeSubstitutor knownSubstitutor) {
        MutableResolvedCallAtom mutableResolvedCallAtom = new MutableResolvedCallAtom(this.kotlinCall, descriptor, explicitReceiverKind, dispatchArgumentReceiver, extensionArgumentReceiver, extensionArgumentReceiverCandidates, null, null, 192, null);
        CallableDescriptor callableDescriptor = descriptor;
        if (ErrorUtils.isError(callableDescriptor)) {
            return new SimpleErrorResolutionCandidate(this.callComponents, this.resolutionCallbacks, this.scopeTower, this.baseSystem, mutableResolvedCallAtom);
        }
        SimpleResolutionCandidate simpleResolutionCandidate = new SimpleResolutionCandidate(this.callComponents, this.resolutionCallbacks, this.scopeTower, this.baseSystem, mutableResolvedCallAtom, knownSubstitutor);
        Iterator<T> it = initialDiagnostics.iterator();
        while (it.hasNext()) {
            simpleResolutionCandidate.addDiagnostic((KotlinCallDiagnostic) it.next());
        }
        if (this.callComponents.getStatelessCallbacks().isHiddenInResolution(callableDescriptor, this.kotlinCall, this.resolutionCallbacks)) {
            simpleResolutionCandidate.addDiagnostic(HiddenDescriptor.INSTANCE);
        }
        if (extensionArgumentReceiver != null) {
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            KotlinType type = extensionReceiverParameter.getValue().getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            boolean zIsDynamic = DynamicTypesKt.isDynamic(type);
            KotlinType type2 = extensionArgumentReceiver.getReceiver().getReceiverValue().getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (zIsDynamic != DynamicTypesKt.isDynamic(type2) || (zIsDynamic && !AnnotationsForResolveUtilsKt.hasDynamicExtensionAnnotation(descriptor))) {
                simpleResolutionCandidate.addDiagnostic(HiddenExtensionRelatedToDynamicTypes.INSTANCE);
            }
        }
        return simpleResolutionCandidate;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
    public SimpleResolutionCandidate createErrorCandidate() {
        Iterable contributedFunctions;
        ErrorScope errorScopeCreateErrorScope = ErrorUtils.createErrorScope(ErrorScopeKind.SCOPE_FOR_ERROR_RESOLUTION_CANDIDATE, this.kotlinCall.toString());
        if (this.kotlinCall.getCallKind() == KotlinCallKind.VARIABLE) {
            contributedFunctions = errorScopeCreateErrorScope.getContributedVariables(this.kotlinCall.getName(), this.scopeTower.getLocation());
        } else {
            contributedFunctions = errorScopeCreateErrorScope.getContributedFunctions(this.kotlinCall.getName(), this.scopeTower.getLocation());
        }
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) CollectionsKt.first(contributedFunctions);
        SimpleKotlinCallArgument simpleKotlinCallArgumentCreateReceiverArgument = createReceiverArgument(this.kotlinCall.getExplicitReceiver(), null);
        return createCandidate(callableMemberDescriptor, simpleKotlinCallArgumentCreateReceiverArgument == null ? ExplicitReceiverKind.NO_EXPLICIT_RECEIVER : ExplicitReceiverKind.DISPATCH_RECEIVER, simpleKotlinCallArgumentCreateReceiverArgument, null, null, CollectionsKt.emptyList(), null);
    }
}
