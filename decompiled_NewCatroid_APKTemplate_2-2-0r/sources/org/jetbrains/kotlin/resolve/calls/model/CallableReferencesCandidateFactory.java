package org.jetbrains.kotlin.resolve.calls.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsToParametersMapper;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.CallableReceiver;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceAdaptation;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceResolutionKt;
import org.jetbrains.kotlin.resolve.calls.components.InputOutputTypes;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.SuspendConversionStrategy;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.LowerPriorityToPreserveCompatibility;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateWithBoundDispatchReceiver;
import org.jetbrains.kotlin.resolve.calls.tower.HiddenDescriptor;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessorsKt;
import org.jetbrains.kotlin.resolve.calls.tower.SimpleScopeTowerProcessor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.DetailedReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.error.LazyWrappedTypeComputationException;
import org.jetbrains.kotlin.types.expressions.CoercionStrategy;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: CallableReferencesCandidateFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001LB9\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJN\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u001f0\u001e2\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u001a2\b\u0010#\u001a\u0004\u0018\u00010\u001a2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010$\u001a\u00020%2\b\b\u0002\u0010&\u001a\u00020'H\u0002J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+J&\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020302H\u0016J\"\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\b\u0010#\u001a\u0004\u0018\u000103H\u0016J\b\u00104\u001a\u00020\u0002H\u0016J.\u00105\u001a\b\u0012\u0004\u0012\u000206022\u0006\u0010 \u001a\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u000209H\u0002J,\u0010=\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u0002072\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010<\u001a\u0002092\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010>\u001a\u00020'2\u0006\u0010?\u001a\u00020\u001fH\u0002J\u001a\u0010@\u001a\u00020'2\b\u0010?\u001a\u0004\u0018\u00010\u001f2\u0006\u0010A\u001a\u00020!H\u0002J\u0018\u0010B\u001a\u00020\u001a2\u0006\u0010C\u001a\u0002032\u0006\u0010D\u001a\u00020'H\u0002J6\u0010E\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010F\u0012\u0004\u0012\u00020G0\u001e2\u0006\u0010H\u001a\u00020F2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020G2\u0006\u0010$\u001a\u00020%H\u0002R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0018\u0010\u0018\u001a\u00020\u0019*\u00020\u001a8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferencesCandidateFactory;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;)V", "getCallComponents", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "asReceiverValueForVisibilityChecks", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "getAsReceiverValueForVisibilityChecks", "(Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;)Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "buildReflectionType", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceAdaptation;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "dispatchReceiver", "extensionReceiver", "builtins", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "buildTypeWithConversions", "", "createCallableProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/SimpleScopeTowerProcessor;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "createCandidate", "towerCandidate", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "extensionReceiverCandidates", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "createErrorCandidate", "createFakeArgumentsForReference", "Lorg/jetbrains/kotlin/resolve/calls/model/FakeKotlinCallArgumentForCallableReference;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "expectedArgumentCount", "", "inputOutputTypes", "Lorg/jetbrains/kotlin/resolve/calls/components/InputOutputTypes;", "unboundReceiverCount", "getCallableReferenceAdaptation", "hasNonTrivialAdaptation", "callableReferenceAdaptation", "needCompatibilityResolveForCallableReference", "candidate", "toCallableReceiver", "receiver", "isExplicit", "varargParameterTypeByExpectedParameter", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferencesCandidateFactory$VarargMappingState;", "expectedParameterType", "substitutedParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "varargMappingState", "VarargMappingState", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferencesCandidateFactory implements CandidateFactory<CallableReferenceResolutionCandidate> {
    private final ConstraintStorage baseSystem;
    private final KotlinCallComponents callComponents;
    private final UnwrappedType expectedType;
    private final CallableReferenceResolutionAtom kotlinCall;
    private final KotlinResolutionCallbacks resolutionCallbacks;
    private final ImplicitScopeTower scopeTower;

    /* JADX INFO: compiled from: CallableReferencesCandidateFactory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferencesCandidateFactory$VarargMappingState;", "", "(Ljava/lang/String;I)V", "UNMAPPED", "MAPPED_WITH_PLAIN_ARGS", "MAPPED_WITH_ARRAY", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum VarargMappingState {
        UNMAPPED,
        MAPPED_WITH_PLAIN_ARGS,
        MAPPED_WITH_ARRAY;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<VarargMappingState> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: CallableReferencesCandidateFactory.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VarargMappingState.values().length];
            try {
                iArr[VarargMappingState.MAPPED_WITH_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VarargMappingState.MAPPED_WITH_PLAIN_ARGS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VarargMappingState.UNMAPPED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public CallableReferencesCandidateFactory(CallableReferenceResolutionAtom kotlinCall, KotlinCallComponents callComponents, ImplicitScopeTower scopeTower, UnwrappedType unwrappedType, ConstraintStorage constraintStorage, KotlinResolutionCallbacks resolutionCallbacks) {
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        this.kotlinCall = kotlinCall;
        this.callComponents = callComponents;
        this.scopeTower = scopeTower;
        this.expectedType = unwrappedType;
        this.baseSystem = constraintStorage;
        this.resolutionCallbacks = resolutionCallbacks;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
    public /* bridge */ /* synthetic */ Candidate createCandidate(CandidateWithBoundDispatchReceiver candidateWithBoundDispatchReceiver, ExplicitReceiverKind explicitReceiverKind, List list) {
        return createCandidate(candidateWithBoundDispatchReceiver, explicitReceiverKind, (List<ReceiverValueWithSmartCastInfo>) list);
    }

    public final CallableReferenceResolutionAtom getKotlinCall() {
        return this.kotlinCall;
    }

    public final KotlinCallComponents getCallComponents() {
        return this.callComponents;
    }

    public final ImplicitScopeTower getScopeTower() {
        return this.scopeTower;
    }

    public final UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    private final ReceiverValue getAsReceiverValueForVisibilityChecks(CallableReceiver callableReceiver) {
        return callableReceiver.getReceiver().getReceiverValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
    public CallableReferenceResolutionCandidate createErrorCandidate() {
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.first(ErrorUtils.createErrorScope(ErrorScopeKind.SCOPE_FOR_ERROR_RESOLUTION_CANDIDATE, this.kotlinCall.toString()).getContributedFunctions(this.kotlinCall.getRhsName(), this.scopeTower.getLocation()));
        Pair<UnwrappedType, CallableReferenceAdaptation> pairBuildReflectionType = buildReflectionType(simpleFunctionDescriptor, null, null, this.expectedType, this.callComponents.getBuiltIns(), this.kotlinCall instanceof CallableReferenceKotlinCallArgument);
        return new CallableReferenceResolutionCandidate(simpleFunctionDescriptor, null, null, ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, pairBuildReflectionType.component1(), pairBuildReflectionType.component2(), this.kotlinCall, this.expectedType, this.callComponents, this.scopeTower, this.resolutionCallbacks, this.baseSystem);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
    public CallableReferenceResolutionCandidate createCandidate(CandidateWithBoundDispatchReceiver towerCandidate, ExplicitReceiverKind explicitReceiverKind, ReceiverValueWithSmartCastInfo extensionReceiver) {
        CallableReceiver callableReceiver;
        CallableReceiver callableReceiver2;
        Intrinsics.checkNotNullParameter(towerCandidate, "towerCandidate");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        ReceiverValueWithSmartCastInfo dispatchReceiver = towerCandidate.getDispatchReceiver();
        if (dispatchReceiver != null) {
            callableReceiver = toCallableReceiver(dispatchReceiver, explicitReceiverKind == ExplicitReceiverKind.DISPATCH_RECEIVER);
        } else {
            callableReceiver = null;
        }
        if (extensionReceiver != null) {
            callableReceiver2 = toCallableReceiver(extensionReceiver, explicitReceiverKind == ExplicitReceiverKind.EXTENSION_RECEIVER);
        } else {
            callableReceiver2 = null;
        }
        CallableDescriptor descriptor = towerCandidate.getDescriptor();
        SmartList smartList = new SmartList();
        Pair<UnwrappedType, CallableReferenceAdaptation> pairBuildReflectionType = buildReflectionType(descriptor, callableReceiver, callableReceiver2, this.expectedType, this.callComponents.getBuiltIns(), this.kotlinCall instanceof CallableReferenceKotlinCallArgument);
        UnwrappedType unwrappedTypeComponent1 = pairBuildReflectionType.component1();
        CallableReferenceAdaptation callableReferenceAdaptationComponent2 = pairBuildReflectionType.component2();
        if (this.callComponents.getStatelessCallbacks().isHiddenInResolution(descriptor, this.kotlinCall.getCall(), this.resolutionCallbacks)) {
            smartList.add(HiddenDescriptor.INSTANCE);
            return createCandidate$createCallableReferenceCallCandidate(descriptor, callableReceiver, callableReceiver2, explicitReceiverKind, unwrappedTypeComponent1, callableReferenceAdaptationComponent2, this, smartList);
        }
        if (needCompatibilityResolveForCallableReference(callableReferenceAdaptationComponent2, descriptor)) {
            ResolvedCallAtomsKt.markCandidateForCompatibilityResolve$default(this, smartList, false, 2, null);
        }
        if (callableReferenceAdaptationComponent2 != null && this.expectedType != null && hasNonTrivialAdaptation(callableReferenceAdaptationComponent2) && !FunctionTypesKt.isFunctionType(this.expectedType) && !FunctionTypesKt.isSuspendFunctionType(this.expectedType)) {
            smartList.add(new AdaptedCallableReferenceIsUsedWithReflection(this.kotlinCall));
        }
        if (callableReferenceAdaptationComponent2 != null && callableReferenceAdaptationComponent2.getDefaults() != 0 && !this.callComponents.getLanguageVersionSettings().supportsFeature(LanguageFeature.FunctionReferenceWithDefaultValueAsOtherType)) {
            smartList.add(new CallableReferencesDefaultArgumentUsed(this.kotlinCall, descriptor, callableReferenceAdaptationComponent2.getDefaults()));
        }
        if (!(descriptor instanceof CallableMemberDescriptor)) {
            return createCandidate$createCallableReferenceCallCandidate(descriptor, callableReceiver, callableReceiver2, explicitReceiverKind, unwrappedTypeComponent1, callableReferenceAdaptationComponent2, this, CollectionsKt.listOf(new NotCallableMemberReference(this.kotlinCall, descriptor)));
        }
        if ((descriptor instanceof PropertyDescriptor) && SimpleCandidateFactoryKt.isSyntheticEnumEntries((PropertyDescriptor) descriptor)) {
            smartList.add(KotlinCallDiagnosticsKt.asDiagnostic(new LowerPriorityToPreserveCompatibility(false)));
        }
        smartList.addAll(towerCandidate.getDiagnostics());
        return createCandidate$createCallableReferenceCallCandidate(descriptor, callableReceiver, callableReceiver2, explicitReceiverKind, unwrappedTypeComponent1, callableReferenceAdaptationComponent2, this, smartList);
    }

    private static final CallableReferenceResolutionCandidate createCandidate$createCallableReferenceCallCandidate(CallableDescriptor callableDescriptor, CallableReceiver callableReceiver, CallableReceiver callableReceiver2, ExplicitReceiverKind explicitReceiverKind, UnwrappedType unwrappedType, CallableReferenceAdaptation callableReferenceAdaptation, CallableReferencesCandidateFactory callableReferencesCandidateFactory, List<? extends KotlinCallDiagnostic> list) {
        CallableReferenceResolutionCandidate callableReferenceResolutionCandidate = new CallableReferenceResolutionCandidate(callableDescriptor, callableReceiver, callableReceiver2, explicitReceiverKind, unwrappedType, callableReferenceAdaptation, callableReferencesCandidateFactory.kotlinCall, callableReferencesCandidateFactory.expectedType, callableReferencesCandidateFactory.callComponents, callableReferencesCandidateFactory.scopeTower, callableReferencesCandidateFactory.resolutionCallbacks, callableReferencesCandidateFactory.baseSystem);
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            callableReferenceResolutionCandidate.addDiagnostic((KotlinCallDiagnostic) it.next());
        }
        return callableReferenceResolutionCandidate;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory
    public CallableReferenceResolutionCandidate createCandidate(CandidateWithBoundDispatchReceiver towerCandidate, ExplicitReceiverKind explicitReceiverKind, List<ReceiverValueWithSmartCastInfo> extensionReceiverCandidates) {
        Intrinsics.checkNotNullParameter(towerCandidate, "towerCandidate");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        Intrinsics.checkNotNullParameter(extensionReceiverCandidates, "extensionReceiverCandidates");
        throw new IllegalStateException((Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + " doesn't support candidates with multiple extension receiver candidates").toString());
    }

    public final SimpleScopeTowerProcessor<CallableReferenceResolutionCandidate> createCallableProcessor(DetailedReceiver explicitReceiver) {
        return ScopeTowerProcessorsKt.createCallableReferenceProcessor(this.scopeTower, this.kotlinCall.getRhsName(), this, explicitReceiver);
    }

    private final boolean needCompatibilityResolveForCallableReference(CallableReferenceAdaptation callableReferenceAdaptation, CallableDescriptor candidate) {
        DeclarationDescriptor containingDeclaration = candidate.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (DescriptorUtilsKt.isCompanionObject(containingDeclaration) && (this.kotlinCall.getLhsResult() instanceof LHSResult.Type)) {
            return true;
        }
        if (callableReferenceAdaptation == null) {
            return false;
        }
        return hasNonTrivialAdaptation(callableReferenceAdaptation);
    }

    private final boolean hasNonTrivialAdaptation(CallableReferenceAdaptation callableReferenceAdaptation) {
        if (callableReferenceAdaptation.getDefaults() == 0 && callableReferenceAdaptation.getSuspendConversionStrategy() == SuspendConversionStrategy.NO_CONVERSION && callableReferenceAdaptation.getCoercionStrategy() == CoercionStrategy.NO_COERCION) {
            Collection<ResolvedCallArgument> collectionValues = callableReferenceAdaptation.getMappedArguments().values();
            if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
                Iterator<T> it = collectionValues.iterator();
                while (it.hasNext()) {
                    if (((ResolvedCallArgument) it.next()) instanceof ResolvedCallArgument.VarargArgument) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    private final CallableReferenceAdaptation getCallableReferenceAdaptation(FunctionDescriptor descriptor, UnwrappedType expectedType, int unboundReceiverCount, KotlinBuiltIns builtins) {
        InputOutputTypes inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType;
        int size;
        CoercionStrategy coercionStrategy;
        Map mapEmptyMap;
        SuspendConversionStrategy suspendConversionStrategy;
        UnwrappedType unwrappedType;
        KotlinType type;
        CallableReferencesCandidateFactory callableReferencesCandidateFactory = this;
        if (expectedType != null) {
            UnwrappedType unwrappedType2 = expectedType;
            if (!TypeUtils.noExpectedType(unwrappedType2)) {
                if (ReflectionTypes.INSTANCE.hasKCallableTypeFqName(unwrappedType2) || (inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType = CallableReferenceResolutionKt.extractInputOutputTypesFromCallableReferenceExpectedType(expectedType)) == null || (size = inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.getInputTypes().size() - unboundReceiverCount) < 0) {
                    return null;
                }
                List<FakeKotlinCallArgumentForCallableReference> listCreateFakeArgumentsForReference = callableReferencesCandidateFactory.createFakeArgumentsForReference(descriptor, size, inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType, unboundReceiverCount);
                ArgumentsToParametersMapper.ArgumentMapping argumentMappingMapArguments = callableReferencesCandidateFactory.callComponents.getArgumentsToParametersMapper().mapArguments(listCreateFakeArgumentsForReference, null, descriptor);
                List<KotlinCallDiagnostic> diagnostics = argumentMappingMapArguments.getDiagnostics();
                if (!(diagnostics instanceof Collection) || !diagnostics.isEmpty()) {
                    Iterator<T> it = diagnostics.iterator();
                    while (it.hasNext()) {
                        if (!CandidateApplicabilityKt.isSuccess(((KotlinCallDiagnostic) it.next()).getCandidateApplicability())) {
                            return null;
                        }
                    }
                }
                VarargMappingState varargMappingStateComponent2 = VarargMappingState.UNMAPPED;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                int size2 = listCreateFakeArgumentsForReference.size();
                KotlinType[] kotlinTypeArr = new KotlinType[size2];
                Iterator<Map.Entry<ValueParameterDescriptor, ResolvedCallArgument>> it2 = argumentMappingMapArguments.getParameterToCallArgumentMap().entrySet().iterator();
                int i = 0;
                while (it2.hasNext()) {
                    Map.Entry<ValueParameterDescriptor, ResolvedCallArgument> next = it2.next();
                    ValueParameterDescriptor key = next.getKey();
                    ResolvedCallArgument value = next.getValue();
                    for (KotlinCallArgument kotlinCallArgument : value.getArguments()) {
                        Intrinsics.checkNotNull(kotlinCallArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.FakeKotlinCallArgumentForCallableReference");
                        int index = ((FakeKotlinCallArgumentForCallableReference) kotlinCallArgument).getIndex();
                        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
                        it2 = it2;
                        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, key.getIndex());
                        if (valueParameterDescriptor != null) {
                            if (ArgumentsUtilsKt.isVararg(valueParameterDescriptor)) {
                                unwrappedType = unwrappedType2;
                                Pair<KotlinType, VarargMappingState> pairVarargParameterTypeByExpectedParameter = callableReferencesCandidateFactory.varargParameterTypeByExpectedParameter(inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.getInputTypes().get(index + unboundReceiverCount), valueParameterDescriptor, varargMappingStateComponent2, builtins);
                                type = pairVarargParameterTypeByExpectedParameter.component1();
                                varargMappingStateComponent2 = pairVarargParameterTypeByExpectedParameter.component2();
                                int i2 = WhenMappings.$EnumSwitchMapping$0[varargMappingStateComponent2.ordinal()];
                                if (i2 == 1) {
                                    linkedHashMap.put(key, new ResolvedCallArgument.SimpleArgument(kotlinCallArgument));
                                } else if (i2 == 2) {
                                    LinkedHashMap linkedHashMap3 = linkedHashMap2;
                                    Object obj = linkedHashMap3.get(key);
                                    if (obj == null) {
                                        obj = (List) new ArrayList();
                                        linkedHashMap3.put(key, obj);
                                    }
                                    ((List) obj).add(kotlinCallArgument);
                                }
                            } else {
                                unwrappedType = unwrappedType2;
                                type = valueParameterDescriptor.getType();
                                linkedHashMap.put(key, value);
                            }
                            kotlinTypeArr[index] = type;
                            callableReferencesCandidateFactory = this;
                            unwrappedType2 = unwrappedType;
                        }
                    }
                    UnwrappedType unwrappedType3 = unwrappedType2;
                    Iterator<Map.Entry<ValueParameterDescriptor, ResolvedCallArgument>> it3 = it2;
                    if (Intrinsics.areEqual(value, ResolvedCallArgument.DefaultArgument.INSTANCE)) {
                        i++;
                        linkedHashMap.put(key, value);
                    }
                    callableReferencesCandidateFactory = this;
                    it2 = it3;
                    unwrappedType2 = unwrappedType3;
                }
                UnwrappedType unwrappedType4 = unwrappedType2;
                for (int i3 = 0; i3 < size2; i3++) {
                    if (kotlinTypeArr[i3] == null) {
                        return null;
                    }
                }
                for (Map.Entry entry : linkedHashMap2.entrySet()) {
                    linkedHashMap.put((ValueParameterDescriptor) entry.getKey(), new ResolvedCallArgument.VarargArgument((List) entry.getValue()));
                }
                for (ValueParameterDescriptor valueParameterDescriptor2 : descriptor.getValueParameters()) {
                    Intrinsics.checkNotNull(valueParameterDescriptor2);
                    if (ArgumentsUtilsKt.isVararg(valueParameterDescriptor2)) {
                        linkedHashMap.putIfAbsent(valueParameterDescriptor2.getOriginal(), new ResolvedCallArgument.VarargArgument(CollectionsKt.emptyList()));
                    }
                }
                if (TypeUtilsKt.isUnit(inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.getOutputType()) && getCallableReferenceAdaptation$isReturnTypeNonUnitSafe(descriptor)) {
                    coercionStrategy = CoercionStrategy.COERCION_TO_UNIT;
                } else {
                    coercionStrategy = CoercionStrategy.NO_COERCION;
                }
                CoercionStrategy coercionStrategy2 = coercionStrategy;
                if (ReflectionTypes.INSTANCE.isBaseTypeForNumberedReferenceTypes(unwrappedType4)) {
                    mapEmptyMap = MapsKt.emptyMap();
                } else {
                    mapEmptyMap = linkedHashMap;
                }
                if (!descriptor.isSuspend() && FunctionTypesKt.isSuspendFunctionType(unwrappedType4)) {
                    suspendConversionStrategy = SuspendConversionStrategy.SUSPEND_CONVERSION;
                } else {
                    suspendConversionStrategy = SuspendConversionStrategy.NO_CONVERSION;
                }
                return new CallableReferenceAdaptation(kotlinTypeArr, coercionStrategy2, i, mapEmptyMap, suspendConversionStrategy);
            }
        }
        return null;
    }

    private static final boolean getCallableReferenceAdaptation$isReturnTypeNonUnitSafe(FunctionDescriptor functionDescriptor) {
        try {
            KotlinType returnType = functionDescriptor.getReturnType();
            return (returnType == null || TypeUtilsKt.isUnit(returnType)) ? false : true;
        } catch (LazyWrappedTypeComputationException unused) {
            return false;
        }
    }

    private final List<FakeKotlinCallArgumentForCallableReference> createFakeArgumentsForReference(FunctionDescriptor descriptor, int expectedArgumentCount, InputOutputTypes inputOutputTypes, int unboundReceiverCount) {
        boolean z = false;
        IntRange intRangeUntil = RangesKt.until(0, expectedArgumentCount);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        boolean z2 = false;
        UnwrappedType unwrappedType = null;
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            UnwrappedType unwrappedType2 = (UnwrappedType) CollectionsKt.getOrNull(inputOutputTypes.getInputTypes(), iNextInt + unboundReceiverCount);
            if (z && !Intrinsics.areEqual(unwrappedType, unwrappedType2)) {
                z2 = true;
            }
            List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, iNextInt);
            Name name2 = (z2 && valueParameterDescriptor != null && valueParameterDescriptor.declaresDefaultValue()) ? valueParameterDescriptor.getShortName() : null;
            if (valueParameterDescriptor != null && ArgumentsUtilsKt.isVararg(valueParameterDescriptor)) {
                unwrappedType = unwrappedType2;
                z = true;
            }
            arrayList.add(new FakeKotlinCallArgumentForCallableReference(iNextInt, name2));
        }
        return arrayList;
    }

    private final Pair<KotlinType, VarargMappingState> varargParameterTypeByExpectedParameter(KotlinType expectedParameterType, ValueParameterDescriptor substitutedParameter, VarargMappingState varargMappingState, KotlinBuiltIns builtins) {
        KotlinType varargElementType = substitutedParameter.getVarargElementType();
        if (varargElementType == null) {
            throw new IllegalStateException(("Vararg parameter " + substitutedParameter + " does not have vararg type").toString());
        }
        int i = WhenMappings.$EnumSwitchMapping$0[varargMappingState.ordinal()];
        if (i == 1) {
            return TuplesKt.to(null, VarargMappingState.MAPPED_WITH_ARRAY);
        }
        if (i == 2) {
            if (KotlinBuiltIns.isArrayOrPrimitiveArray(expectedParameterType)) {
                return TuplesKt.to(null, VarargMappingState.MAPPED_WITH_PLAIN_ARGS);
            }
            return TuplesKt.to(varargElementType, VarargMappingState.MAPPED_WITH_PLAIN_ARGS);
        }
        if (i == 3) {
            if (KotlinBuiltIns.isArrayOrPrimitiveArray(expectedParameterType) || (expectedParameterType.getConstructor() instanceof TypeVariableTypeConstructor)) {
                SimpleType primitiveArrayKotlinTypeByPrimitiveKotlinType = builtins.getPrimitiveArrayKotlinTypeByPrimitiveKotlinType(varargElementType);
                if (primitiveArrayKotlinTypeByPrimitiveKotlinType == null) {
                    primitiveArrayKotlinTypeByPrimitiveKotlinType = builtins.getArrayType(Variance.OUT_VARIANCE, varargElementType);
                }
                Intrinsics.checkNotNull(primitiveArrayKotlinTypeByPrimitiveKotlinType);
                return TuplesKt.to(primitiveArrayKotlinTypeByPrimitiveKotlinType, VarargMappingState.MAPPED_WITH_ARRAY);
            }
            return TuplesKt.to(varargElementType, VarargMappingState.MAPPED_WITH_PLAIN_ARGS);
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX WARN: Code duplicated, block: B:27:0x00c6  */
    private final Pair<UnwrappedType, CallableReferenceAdaptation> buildReflectionType(CallableDescriptor descriptor, CallableReceiver dispatchReceiver, CallableReceiver extensionReceiver, UnwrappedType expectedType, KotlinBuiltIns builtins, boolean buildTypeWithConversions) {
        ArrayList arrayList = new ArrayList(descriptor.getValueParameters().size() + 2 + descriptor.getContextReceiverParameters().size());
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList2.add(((ReceiverParameterDescriptor) it.next()).getType());
        }
        arrayList.addAll(arrayList2);
        if (dispatchReceiver instanceof CallableReceiver.UnboundReference) {
            arrayList.add(ArgumentsUtilsKt.getStableType(((CallableReceiver.UnboundReference) dispatchReceiver).getReceiver()));
        }
        if (extensionReceiver instanceof CallableReceiver.UnboundReference) {
            arrayList.add(ArgumentsUtilsKt.getStableType(((CallableReceiver.UnboundReference) extensionReceiver).getReceiver()));
        }
        SimpleType returnType = descriptor.getReturnType();
        boolean z = true;
        if (returnType == null) {
            returnType = ErrorUtils.createErrorType(ErrorTypeKind.RETURN_TYPE, descriptor.toString());
        }
        Intrinsics.checkNotNull(returnType);
        if (descriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
            if (propertyDescriptor.isVar()) {
                PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                if (setter != null) {
                    if (!DescriptorVisibilities.isVisible(dispatchReceiver != null ? getAsReceiverValueForVisibilityChecks(dispatchReceiver) : null, setter, this.scopeTower.getLexicalScope().getOwnerDescriptor(), false)) {
                        z = false;
                    }
                }
            } else {
                z = false;
            }
            return TuplesKt.to(this.callComponents.getReflectionTypes().getKPropertyType(Annotations.INSTANCE.getEMPTY(), arrayList, returnType, z), null);
        }
        if (descriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            CallableReferenceAdaptation callableReferenceAdaptation = getCallableReferenceAdaptation(functionDescriptor, expectedType, arrayList.size(), builtins);
            if (callableReferenceAdaptation == null || !buildTypeWithConversions) {
                List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                Iterator<T> it2 = valueParameters.iterator();
                while (it2.hasNext()) {
                    arrayList.add(((ValueParameterDescriptor) it2.next()).getType());
                }
            } else {
                KotlinType[] argumentTypes = callableReferenceAdaptation.getArgumentTypes();
                CoercionStrategy coercionStrategy = callableReferenceAdaptation.getCoercionStrategy();
                CollectionsKt.addAll(arrayList, argumentTypes);
                if (coercionStrategy == CoercionStrategy.COERCION_TO_UNIT) {
                    SimpleType unitType = DescriptorUtilsKt.getBuiltIns(descriptor).getUnitType();
                    Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
                    returnType = unitType;
                }
            }
            return TuplesKt.to(this.callComponents.getReflectionTypes().getKFunctionType(Annotations.INSTANCE.getEMPTY(), null, CollectionsKt.emptyList(), arrayList, null, returnType, DescriptorUtilsKt.getBuiltIns(descriptor), functionDescriptor.isSuspend() || ((callableReferenceAdaptation != null ? callableReferenceAdaptation.getSuspendConversionStrategy() : null) == SuspendConversionStrategy.SUSPEND_CONVERSION && buildTypeWithConversions)), callableReferenceAdaptation);
        }
        DescriptorUtilKt.isSupportedForCallableReference(descriptor);
        return TuplesKt.to(ErrorUtils.createErrorType(ErrorTypeKind.UNSUPPORTED_CALLABLE_REFERENCE_TYPE, descriptor.toString()), null);
    }

    private final CallableReceiver toCallableReceiver(ReceiverValueWithSmartCastInfo receiver, boolean isExplicit) {
        ReceiverValue classValueReceiver;
        if (!isExplicit) {
            return new CallableReceiver.ScopeReceiver(receiver);
        }
        LHSResult lhsResult = this.kotlinCall.getLhsResult();
        if (lhsResult instanceof LHSResult.Expression) {
            return new CallableReceiver.ExplicitValueReceiver(receiver);
        }
        if (lhsResult instanceof LHSResult.Type) {
            QualifierReceiver qualifier = ((LHSResult.Type) lhsResult).getQualifier();
            if (Intrinsics.areEqual((qualifier == null || (classValueReceiver = qualifier.getClassValueReceiver()) == null) ? null : classValueReceiver.getType(), receiver.getReceiverValue().getType())) {
                return new CallableReceiver.BoundValueReference(receiver);
            }
            return new CallableReceiver.UnboundReference(receiver);
        }
        if (lhsResult instanceof LHSResult.Object) {
            return new CallableReceiver.BoundValueReference(receiver);
        }
        throw new IllegalStateException("Unsupported kind of lhsResult: " + lhsResult);
    }
}
