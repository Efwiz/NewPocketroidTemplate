package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceArgumentResolver;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceOverloadConflictResolver;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceResolutionKt;
import org.jetbrains.kotlin.resolve.calls.components.KotlinCallCompleter;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.NewOverloadingConflictResolver;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.model.CallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferencesCandidateFactory;
import org.jetbrains.kotlin.resolve.calls.model.CandidateChosenUsingOverloadResolutionByLambdaAnnotation;
import org.jetbrains.kotlin.resolve.calls.model.EnumEntryAmbiguityWarning;
import org.jetbrains.kotlin.resolve.calls.model.GivenCandidate;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallKind;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallKt;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleCandidateFactory;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateFactoryProviderForInvoke;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.resolve.calls.tower.InvokeProcessorsKt;
import org.jetbrains.kotlin.resolve.calls.tower.KnownResultProcessor;
import org.jetbrains.kotlin.resolve.calls.tower.PrioritizedCompositeScopeTowerProcessor;
import org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor;
import org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessorsKt;
import org.jetbrains.kotlin.resolve.calls.tower.TowerResolver;
import org.jetbrains.kotlin.resolve.calls.tower.TowerUtilsKt;
import org.jetbrains.kotlin.resolve.calls.tower.VariableAndObjectScopeTowerProcessor;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.DetailedReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ,\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015H\u0002JH\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 H\u0002J0\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J&\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J0\u0010#\u001a\u00020$2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010%\u001a\u00020&J>\u0010'\u001a\u00020$2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u00152\u0006\u0010%\u001a\u00020&JF\u0010*\u001a\b\u0012\u0004\u0012\u0002H+0\u0015\"\b\b\u0000\u0010+*\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010%\u001a\u00020&2\f\u0010,\u001a\b\u0012\u0004\u0012\u0002H+0\u0017H\u0002J6\u0010*\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010%\u001a\u00020&J.\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00180\u00152\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;", "", "towerResolver", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver;", "kotlinCallCompleter", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinCallCompleter;", "overloadingConflictResolver", "Lorg/jetbrains/kotlin/resolve/calls/components/NewOverloadingConflictResolver;", "callableReferenceArgumentResolver", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceArgumentResolver;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver;Lorg/jetbrains/kotlin/resolve/calls/components/KotlinCallCompleter;Lorg/jetbrains/kotlin/resolve/calls/components/NewOverloadingConflictResolver;Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceArgumentResolver;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;)V", "choseMostSpecific", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "candidates", "", "createCallableReferenceCallFactory", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "createFactory", "createSimpleCallFactory", "resolveAndCompleteCall", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "collectAllCandidates", "", "resolveAndCompleteGivenCandidates", "givenCandidates", "Lorg/jetbrains/kotlin/resolve/calls/model/GivenCandidate;", "resolveCall", "C", "candidateFactory", "resolveCallableReferenceArgument", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinCallResolver {
    private final KotlinCallComponents callComponents;
    private final CallableReferenceArgumentResolver callableReferenceArgumentResolver;
    private final KotlinCallCompleter kotlinCallCompleter;
    private final NewOverloadingConflictResolver overloadingConflictResolver;
    private final TowerResolver towerResolver;

    /* JADX INFO: compiled from: KotlinCallResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinCallKind.values().length];
            try {
                iArr[KotlinCallKind.CALLABLE_REFERENCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinCallKind.VARIABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinCallKind.FUNCTION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KotlinCallKind.INVOKE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KotlinCallKind.UNSUPPORTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KotlinCallResolver(TowerResolver towerResolver, KotlinCallCompleter kotlinCallCompleter, NewOverloadingConflictResolver overloadingConflictResolver, CallableReferenceArgumentResolver callableReferenceArgumentResolver, KotlinCallComponents callComponents) {
        Intrinsics.checkNotNullParameter(towerResolver, "towerResolver");
        Intrinsics.checkNotNullParameter(kotlinCallCompleter, "kotlinCallCompleter");
        Intrinsics.checkNotNullParameter(overloadingConflictResolver, "overloadingConflictResolver");
        Intrinsics.checkNotNullParameter(callableReferenceArgumentResolver, "callableReferenceArgumentResolver");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        this.towerResolver = towerResolver;
        this.kotlinCallCompleter = kotlinCallCompleter;
        this.overloadingConflictResolver = overloadingConflictResolver;
        this.callableReferenceArgumentResolver = callableReferenceArgumentResolver;
        this.callComponents = callComponents;
    }

    public final CallResolutionResult resolveAndCompleteCall(ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, KotlinCall kotlinCall, UnwrappedType expectedType, boolean collectAllCandidates) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        CandidateFactory<ResolutionCandidate> candidateFactoryCreateFactory = createFactory(scopeTower, kotlinCall, resolutionCallbacks, expectedType);
        Collection<? extends ResolutionCandidate> collectionResolveCall = resolveCall(scopeTower, resolutionCallbacks, kotlinCall, collectAllCandidates, candidateFactoryCreateFactory);
        if (collectAllCandidates) {
            return this.kotlinCallCompleter.createAllCandidatesResult(collectionResolveCall, expectedType, resolutionCallbacks);
        }
        return this.kotlinCallCompleter.runCompletion(candidateFactoryCreateFactory, collectionResolveCall, expectedType, resolutionCallbacks);
    }

    public final Collection<ResolutionCandidate> resolveCall(ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, KotlinCall kotlinCall, UnwrappedType expectedType, boolean collectAllCandidates) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        return resolveCall(scopeTower, resolutionCallbacks, kotlinCall, collectAllCandidates, createFactory(scopeTower, kotlinCall, resolutionCallbacks, expectedType));
    }

    public final CallResolutionResult resolveAndCompleteGivenCandidates(ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, KotlinCall kotlinCall, UnwrappedType expectedType, Collection<GivenCandidate> givenCandidates, boolean collectAllCandidates) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        Intrinsics.checkNotNullParameter(givenCandidates, "givenCandidates");
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        KotlinCallKt.checkCallInvariants(kotlinCall);
        SimpleCandidateFactory simpleCandidateFactory = new SimpleCandidateFactory(this.callComponents, scopeTower, kotlinCall, resolutionCallbacks);
        Collection<GivenCandidate> collection = givenCandidates;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add((SimpleResolutionCandidate) TowerUtilsKt.forceResolution(simpleCandidateFactory.createCandidate((GivenCandidate) it.next())));
        }
        ArrayList arrayList2 = arrayList;
        if (collectAllCandidates) {
            return this.kotlinCallCompleter.createAllCandidatesResult(this.towerResolver.runWithEmptyTowerData(new KnownResultProcessor(arrayList2), new TowerResolver.AllCandidatesCollector(), false), expectedType, resolutionCallbacks);
        }
        return this.kotlinCallCompleter.runCompletion(simpleCandidateFactory, choseMostSpecific(kotlinCall, resolutionCallbacks, this.towerResolver.runWithEmptyTowerData(new KnownResultProcessor(arrayList2), new TowerResolver.SuccessfulResultCollector(), true)), expectedType, resolutionCallbacks);
    }

    public final Collection<CallableReferenceResolutionCandidate> resolveCallableReferenceArgument(CallableReferenceKotlinCallArgument argument, UnwrappedType expectedType, ConstraintStorage baseSystem, KotlinResolutionCallbacks resolutionCallbacks) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(baseSystem, "baseSystem");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        ImplicitScopeTower scopeTowerForCallableReferenceArgument = this.callComponents.getStatelessCallbacks().getScopeTowerForCallableReferenceArgument(argument);
        return resolveCall(scopeTowerForCallableReferenceArgument, resolutionCallbacks, argument.getCall(), false, (CandidateFactory) createCallableReferenceCallFactory(scopeTowerForCallableReferenceArgument, argument.getCall(), resolutionCallbacks, expectedType, argument, baseSystem));
    }

    static /* synthetic */ CandidateFactory createCallableReferenceCallFactory$default(KotlinCallResolver kotlinCallResolver, ImplicitScopeTower implicitScopeTower, KotlinCall kotlinCall, KotlinResolutionCallbacks kotlinResolutionCallbacks, UnwrappedType unwrappedType, CallableReferenceKotlinCallArgument callableReferenceKotlinCallArgument, ConstraintStorage constraintStorage, int i, Object obj) {
        return kotlinCallResolver.createCallableReferenceCallFactory(implicitScopeTower, kotlinCall, kotlinResolutionCallbacks, unwrappedType, (i & 16) != 0 ? null : callableReferenceKotlinCallArgument, (i & 32) != 0 ? null : constraintStorage);
    }

    private final CandidateFactory<CallableReferenceResolutionCandidate> createCallableReferenceCallFactory(ImplicitScopeTower scopeTower, KotlinCall kotlinCall, KotlinResolutionCallbacks resolutionCallbacks, UnwrappedType expectedType, CallableReferenceKotlinCallArgument argument, ConstraintStorage baseSystem) {
        CallableReferenceKotlinCall callableReferenceKotlinCall;
        if (argument != null) {
            callableReferenceKotlinCall = argument;
        } else {
            callableReferenceKotlinCall = new CallableReferenceKotlinCall(kotlinCall, resolutionCallbacks.getLhsResult(kotlinCall), kotlinCall.getName());
        }
        return new CallableReferencesCandidateFactory(callableReferenceKotlinCall, this.callComponents, scopeTower, expectedType, baseSystem, resolutionCallbacks);
    }

    private final CandidateFactory<ResolutionCandidate> createSimpleCallFactory(ImplicitScopeTower scopeTower, KotlinCall kotlinCall, KotlinResolutionCallbacks resolutionCallbacks) {
        return new SimpleCandidateFactory(this.callComponents, scopeTower, kotlinCall, resolutionCallbacks);
    }

    private final CandidateFactory<ResolutionCandidate> createFactory(ImplicitScopeTower scopeTower, KotlinCall kotlinCall, KotlinResolutionCallbacks resolutionCallbacks, UnwrappedType expectedType) {
        if (WhenMappings.$EnumSwitchMapping$0[kotlinCall.getCallKind().ordinal()] == 1) {
            return createCallableReferenceCallFactory$default(this, scopeTower, kotlinCall, resolutionCallbacks, expectedType, null, null, 48, null);
        }
        return createSimpleCallFactory(scopeTower, kotlinCall, resolutionCallbacks);
    }

    private final <C extends ResolutionCandidate> Collection<C> resolveCall(final ImplicitScopeTower scopeTower, KotlinResolutionCallbacks resolutionCallbacks, final KotlinCall kotlinCall, boolean collectAllCandidates, final CandidateFactory<? extends C> candidateFactory) {
        VariableAndObjectScopeTowerProcessor variableAndObjectScopeTowerProcessorCreateCallableReferenceProcessor;
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        KotlinCallKt.checkCallInvariants(kotlinCall);
        int i = WhenMappings.$EnumSwitchMapping$0[kotlinCall.getCallKind().ordinal()];
        if (i != 1) {
            if (i == 2) {
                Name name2 = kotlinCall.getName();
                ReceiverKotlinCallArgument explicitReceiver = kotlinCall.getExplicitReceiver();
                variableAndObjectScopeTowerProcessorCreateCallableReferenceProcessor = ScopeTowerProcessorsKt.createVariableAndObjectProcessor$default(scopeTower, name2, candidateFactory, explicitReceiver != null ? explicitReceiver.getReceiver() : null, false, 16, null);
            } else if (i == 3) {
                Name name3 = kotlinCall.getName();
                CandidateFactoryProviderForInvoke<ResolutionCandidate> candidateFactoryForInvoke = resolutionCallbacks.getCandidateFactoryForInvoke(scopeTower, kotlinCall);
                ReceiverKotlinCallArgument explicitReceiver2 = kotlinCall.getExplicitReceiver();
                PrioritizedCompositeScopeTowerProcessor prioritizedCompositeScopeTowerProcessorCreateFunctionProcessor = ScopeTowerProcessorsKt.createFunctionProcessor(scopeTower, name3, candidateFactory, candidateFactoryForInvoke, explicitReceiver2 != null ? explicitReceiver2.getReceiver() : null);
                Intrinsics.checkNotNull(prioritizedCompositeScopeTowerProcessorCreateFunctionProcessor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor<C of org.jetbrains.kotlin.resolve.calls.KotlinCallResolver.resolveCall>");
                variableAndObjectScopeTowerProcessorCreateCallableReferenceProcessor = prioritizedCompositeScopeTowerProcessorCreateFunctionProcessor;
            } else {
                if (i != 4) {
                    if (i != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                    throw new UnsupportedOperationException();
                }
                ReceiverKotlinCallArgument explicitReceiver3 = kotlinCall.getExplicitReceiver();
                variableAndObjectScopeTowerProcessorCreateCallableReferenceProcessor = ScopeTowerProcessorsKt.createProcessorWithReceiverValueOrEmpty(explicitReceiver3 != null ? explicitReceiver3.getReceiver() : null, new Function1<ReceiverValueWithSmartCastInfo, ScopeTowerProcessor<? extends C>>() { // from class: org.jetbrains.kotlin.resolve.calls.KotlinCallResolver$resolveCall$processor$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ScopeTowerProcessor<C> invoke(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
                        ImplicitScopeTower implicitScopeTower = scopeTower;
                        CandidateFactory<C> candidateFactory2 = candidateFactory;
                        ReceiverKotlinCallArgument dispatchReceiverForInvokeExtension = kotlinCall.getDispatchReceiverForInvokeExtension();
                        DetailedReceiver receiver = dispatchReceiverForInvokeExtension != null ? dispatchReceiverForInvokeExtension.getReceiver() : null;
                        Intrinsics.checkNotNull(receiver, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo");
                        return InvokeProcessorsKt.createCallTowerProcessorForExplicitInvoke(implicitScopeTower, candidateFactory2, (ReceiverValueWithSmartCastInfo) receiver, receiverValueWithSmartCastInfo);
                    }
                });
            }
        } else {
            Intrinsics.checkNotNull(candidateFactory, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.CallableReferencesCandidateFactory");
            variableAndObjectScopeTowerProcessorCreateCallableReferenceProcessor = CallableReferenceResolutionKt.createCallableReferenceProcessor((CallableReferencesCandidateFactory) candidateFactory);
            Intrinsics.checkNotNull(variableAndObjectScopeTowerProcessorCreateCallableReferenceProcessor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor<C of org.jetbrains.kotlin.resolve.calls.KotlinCallResolver.resolveCall>");
        }
        if (collectAllCandidates) {
            return this.towerResolver.collectAllCandidates(scopeTower, variableAndObjectScopeTowerProcessorCreateCallableReferenceProcessor, kotlinCall.getName());
        }
        Set<ResolutionCandidate> setChoseMostSpecific = choseMostSpecific(kotlinCall, resolutionCallbacks, this.towerResolver.runResolve(scopeTower, variableAndObjectScopeTowerProcessorCreateCallableReferenceProcessor, kotlinCall.getCallKind() != KotlinCallKind.UNSUPPORTED, kotlinCall.getName()));
        Intrinsics.checkNotNull(setChoseMostSpecific, "null cannot be cast to non-null type kotlin.collections.Set<C of org.jetbrains.kotlin.resolve.calls.KotlinCallResolver.resolveCall>");
        return setChoseMostSpecific;
    }

    /* JADX WARN: Code duplicated, block: B:111:0x019b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:113:0x0178 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:0x01f7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:117:? A[LOOP:4: B:88:0x01e5->B:117:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:119:0x0215 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:120:? A[LOOP:5: B:67:0x0155->B:120:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:15:0x004e  */
    /* JADX WARN: Code duplicated, block: B:66:0x0151  */
    /* JADX WARN: Code duplicated, block: B:69:0x015b  */
    /* JADX WARN: Code duplicated, block: B:75:0x017e  */
    /* JADX WARN: Code duplicated, block: B:80:0x01b6  */
    /* JADX WARN: Code duplicated, block: B:82:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:84:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:87:0x01e1  */
    /* JADX WARN: Code duplicated, block: B:90:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:94:0x020b A[EDGE_INSN: B:94:0x020b->B:95:0x0215 BREAK  A[LOOP:4: B:88:0x01e5->B:117:?]] */
    private final Set<ResolutionCandidate> choseMostSpecific(KotlinCall kotlinCall, KotlinResolutionCallbacks resolutionCallbacks, Collection<? extends ResolutionCandidate> candidates) {
        ArrayList arrayList;
        Set<ResolutionCandidate> setChooseMaximallySpecificCandidates;
        Iterator<T> it;
        ArrayList arrayList2;
        List listMinus;
        Set set;
        List list;
        Iterator it2;
        ResolutionCandidate resolutionCandidate;
        Object obj;
        Object next;
        if (this.callComponents.getLanguageVersionSettings().supportsFeature(LanguageFeature.RefinedSamAdaptersPriority) || kotlinCall.getCallKind() == KotlinCallKind.CALLABLE_REFERENCE) {
            arrayList = candidates;
        } else {
            ArrayList arrayList3 = new ArrayList();
            for (Object obj2 : candidates) {
                if (!TowerUtilsKt.isSynthesized(((ResolutionCandidate) obj2).getResolvedCall().get_candidateDescriptor())) {
                    arrayList3.add(obj2);
                }
            }
            arrayList = arrayList3;
            if (arrayList.isEmpty()) {
                arrayList = candidates;
            }
        }
        if (kotlinCall.getCallKind() == KotlinCallKind.CALLABLE_REFERENCE) {
            CallableReferenceOverloadConflictResolver callableReferenceOverloadConflictResolver = this.callableReferenceArgumentResolver.getCallableReferenceOverloadConflictResolver();
            Intrinsics.checkNotNull(arrayList, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate>");
            setChooseMaximallySpecificCandidates = callableReferenceOverloadConflictResolver.chooseMaximallySpecificCandidates(arrayList, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, false);
        } else {
            setChooseMaximallySpecificCandidates = this.overloadingConflictResolver.chooseMaximallySpecificCandidates(arrayList, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, true);
        }
        if (setChooseMaximallySpecificCandidates.size() > 1) {
            if (setChooseMaximallySpecificCandidates.size() == 2) {
                Set<ResolutionCandidate> set2 = setChooseMaximallySpecificCandidates;
                Iterator<T> it3 = set2.iterator();
                while (true) {
                    obj = null;
                    if (!it3.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it3.next();
                    CallableDescriptor callableDescriptor = ((ResolutionCandidate) next).getResolvedCall().get_candidateDescriptor();
                    if ((callableDescriptor instanceof FakeCallableDescriptorForObject) && ((FakeCallableDescriptorForObject) callableDescriptor).getClassDescriptor().getClassKind() == ClassKind.ENUM_ENTRY) {
                        break;
                    }
                }
                ResolutionCandidate resolutionCandidate2 = (ResolutionCandidate) next;
                if (resolutionCandidate2 != null) {
                    for (Object obj3 : set2) {
                        if (!(((ResolutionCandidate) obj3).getResolvedCall().get_candidateDescriptor() instanceof FakeCallableDescriptorForObject)) {
                            obj = obj3;
                            break;
                        }
                    }
                    ResolutionCandidate resolutionCandidate3 = (ResolutionCandidate) obj;
                    if (resolutionCandidate3 != null) {
                        CallableDescriptor callableDescriptor2 = resolutionCandidate3.getResolvedCall().get_candidateDescriptor();
                        if (callableDescriptor2 instanceof PropertyDescriptor) {
                            CallableDescriptor callableDescriptor3 = resolutionCandidate2.getResolvedCall().get_candidateDescriptor();
                            Intrinsics.checkNotNull(callableDescriptor3, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject");
                            resolutionCandidate3.addDiagnostic(new EnumEntryAmbiguityWarning((PropertyDescriptor) callableDescriptor2, ((FakeCallableDescriptorForObject) callableDescriptor3).getClassDescriptor()));
                            return SetsKt.setOf(resolutionCandidate3);
                        }
                    }
                }
            }
            if (this.callComponents.getLanguageVersionSettings().supportsFeature(LanguageFeature.OverloadResolutionByLambdaReturnType) && kotlinCall.getCallKind() != KotlinCallKind.CALLABLE_REFERENCE) {
                Collection<? extends ResolutionCandidate> collection = candidates;
                boolean z = collection instanceof Collection;
                if (!z || !collection.isEmpty()) {
                    Iterator<T> it4 = collection.iterator();
                    while (it4.hasNext()) {
                        if (!((ResolutionCandidate) it4.next()).getIsSuccessful()) {
                        }
                    }
                    if (z || !collection.isEmpty()) {
                        it = collection.iterator();
                        while (it.hasNext()) {
                            if (!resolutionCallbacks.getInferenceSession().shouldRunCompletion((ResolutionCandidate) it.next())) {
                            }
                        }
                        arrayList2 = new ArrayList();
                        for (Object obj4 : collection) {
                            if (((ResolutionCandidate) obj4).getResolvedCall().get_candidateDescriptor().getAnnotations().hasAnnotation(AnnotationsForResolveKt.getOVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION_FQ_NAME())) {
                                arrayList2.add(obj4);
                            }
                        }
                        Set set3 = CollectionsKt.toSet(arrayList2);
                        listMinus = CollectionsKt.minus((Iterable) collection, (Iterable) set3);
                        set = set3;
                        if (!set.isEmpty()) {
                            KotlinCallCompleter kotlinCallCompleter = this.kotlinCallCompleter;
                            Intrinsics.checkNotNull(setChooseMaximallySpecificCandidates, "null cannot be cast to non-null type kotlin.collections.Set<org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate>");
                            setChooseMaximallySpecificCandidates = this.overloadingConflictResolver.chooseMaximallySpecificCandidates(kotlinCallCompleter.chooseCandidateRegardingOverloadResolutionByLambdaReturnType(setChooseMaximallySpecificCandidates, resolutionCallbacks), CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, true);
                            if (setChooseMaximallySpecificCandidates.size() > 1) {
                                list = listMinus;
                                if ((list instanceof Collection) || !list.isEmpty()) {
                                    it2 = list.iterator();
                                    while (it2.hasNext()) {
                                        if (setChooseMaximallySpecificCandidates.contains((ResolutionCandidate) it2.next())) {
                                            setChooseMaximallySpecificCandidates = CollectionsKt.toMutableSet(setChooseMaximallySpecificCandidates);
                                            setChooseMaximallySpecificCandidates.removeAll(set);
                                            resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(setChooseMaximallySpecificCandidates);
                                            if (resolutionCandidate == null) {
                                                break;
                                            }
                                            resolutionCandidate.addDiagnostic(new CandidateChosenUsingOverloadResolutionByLambdaAnnotation());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        arrayList2 = new ArrayList();
                        while (r1.hasNext()) {
                            if (((ResolutionCandidate) obj4).getResolvedCall().get_candidateDescriptor().getAnnotations().hasAnnotation(AnnotationsForResolveKt.getOVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION_FQ_NAME())) {
                                arrayList2.add(obj4);
                            }
                        }
                        Set set4 = CollectionsKt.toSet(arrayList2);
                        listMinus = CollectionsKt.minus((Iterable) collection, (Iterable) set4);
                        set = set4;
                        if (!set.isEmpty()) {
                            KotlinCallCompleter kotlinCallCompleter2 = this.kotlinCallCompleter;
                            Intrinsics.checkNotNull(setChooseMaximallySpecificCandidates, "null cannot be cast to non-null type kotlin.collections.Set<org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate>");
                            setChooseMaximallySpecificCandidates = this.overloadingConflictResolver.chooseMaximallySpecificCandidates(kotlinCallCompleter2.chooseCandidateRegardingOverloadResolutionByLambdaReturnType(setChooseMaximallySpecificCandidates, resolutionCallbacks), CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, true);
                            if (setChooseMaximallySpecificCandidates.size() > 1) {
                                list = listMinus;
                                if (list instanceof Collection) {
                                    it2 = list.iterator();
                                    while (it2.hasNext()) {
                                        if (setChooseMaximallySpecificCandidates.contains((ResolutionCandidate) it2.next())) {
                                            setChooseMaximallySpecificCandidates = CollectionsKt.toMutableSet(setChooseMaximallySpecificCandidates);
                                            setChooseMaximallySpecificCandidates.removeAll(set);
                                            resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(setChooseMaximallySpecificCandidates);
                                            if (resolutionCandidate == null) {
                                                break;
                                            }
                                            resolutionCandidate.addDiagnostic(new CandidateChosenUsingOverloadResolutionByLambdaAnnotation());
                                            break;
                                        }
                                    }
                                } else {
                                    it2 = list.iterator();
                                    while (it2.hasNext()) {
                                        if (setChooseMaximallySpecificCandidates.contains((ResolutionCandidate) it2.next())) {
                                            setChooseMaximallySpecificCandidates = CollectionsKt.toMutableSet(setChooseMaximallySpecificCandidates);
                                            setChooseMaximallySpecificCandidates.removeAll(set);
                                            resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(setChooseMaximallySpecificCandidates);
                                            if (resolutionCandidate == null) {
                                                break;
                                            }
                                            resolutionCandidate.addDiagnostic(new CandidateChosenUsingOverloadResolutionByLambdaAnnotation());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (z) {
                    it = collection.iterator();
                    while (it.hasNext()) {
                        if (!resolutionCallbacks.getInferenceSession().shouldRunCompletion((ResolutionCandidate) it.next())) {
                        }
                    }
                    arrayList2 = new ArrayList();
                    while (r1.hasNext()) {
                        if (((ResolutionCandidate) obj4).getResolvedCall().get_candidateDescriptor().getAnnotations().hasAnnotation(AnnotationsForResolveKt.getOVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION_FQ_NAME())) {
                            arrayList2.add(obj4);
                        }
                    }
                    Set set5 = CollectionsKt.toSet(arrayList2);
                    listMinus = CollectionsKt.minus((Iterable) collection, (Iterable) set5);
                    set = set5;
                    if (!set.isEmpty()) {
                        KotlinCallCompleter kotlinCallCompleter3 = this.kotlinCallCompleter;
                        Intrinsics.checkNotNull(setChooseMaximallySpecificCandidates, "null cannot be cast to non-null type kotlin.collections.Set<org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate>");
                        setChooseMaximallySpecificCandidates = this.overloadingConflictResolver.chooseMaximallySpecificCandidates(kotlinCallCompleter3.chooseCandidateRegardingOverloadResolutionByLambdaReturnType(setChooseMaximallySpecificCandidates, resolutionCallbacks), CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, true);
                        if (setChooseMaximallySpecificCandidates.size() > 1) {
                            list = listMinus;
                            if (list instanceof Collection) {
                                it2 = list.iterator();
                                while (it2.hasNext()) {
                                    if (setChooseMaximallySpecificCandidates.contains((ResolutionCandidate) it2.next())) {
                                        setChooseMaximallySpecificCandidates = CollectionsKt.toMutableSet(setChooseMaximallySpecificCandidates);
                                        setChooseMaximallySpecificCandidates.removeAll(set);
                                        resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(setChooseMaximallySpecificCandidates);
                                        if (resolutionCandidate == null) {
                                            break;
                                        }
                                        resolutionCandidate.addDiagnostic(new CandidateChosenUsingOverloadResolutionByLambdaAnnotation());
                                        break;
                                    }
                                }
                            } else {
                                it2 = list.iterator();
                                while (it2.hasNext()) {
                                    if (setChooseMaximallySpecificCandidates.contains((ResolutionCandidate) it2.next())) {
                                        setChooseMaximallySpecificCandidates = CollectionsKt.toMutableSet(setChooseMaximallySpecificCandidates);
                                        setChooseMaximallySpecificCandidates.removeAll(set);
                                        resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(setChooseMaximallySpecificCandidates);
                                        if (resolutionCandidate == null) {
                                            break;
                                        }
                                        resolutionCandidate.addDiagnostic(new CandidateChosenUsingOverloadResolutionByLambdaAnnotation());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    it = collection.iterator();
                    while (it.hasNext()) {
                        if (!resolutionCallbacks.getInferenceSession().shouldRunCompletion((ResolutionCandidate) it.next())) {
                        }
                    }
                    arrayList2 = new ArrayList();
                    while (r1.hasNext()) {
                        if (((ResolutionCandidate) obj4).getResolvedCall().get_candidateDescriptor().getAnnotations().hasAnnotation(AnnotationsForResolveKt.getOVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION_FQ_NAME())) {
                            arrayList2.add(obj4);
                        }
                    }
                    Set set6 = CollectionsKt.toSet(arrayList2);
                    listMinus = CollectionsKt.minus((Iterable) collection, (Iterable) set6);
                    set = set6;
                    if (!set.isEmpty()) {
                        KotlinCallCompleter kotlinCallCompleter4 = this.kotlinCallCompleter;
                        Intrinsics.checkNotNull(setChooseMaximallySpecificCandidates, "null cannot be cast to non-null type kotlin.collections.Set<org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate>");
                        setChooseMaximallySpecificCandidates = this.overloadingConflictResolver.chooseMaximallySpecificCandidates(kotlinCallCompleter4.chooseCandidateRegardingOverloadResolutionByLambdaReturnType(setChooseMaximallySpecificCandidates, resolutionCallbacks), CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, true);
                        if (setChooseMaximallySpecificCandidates.size() > 1) {
                            list = listMinus;
                            if (list instanceof Collection) {
                                it2 = list.iterator();
                                while (it2.hasNext()) {
                                    if (setChooseMaximallySpecificCandidates.contains((ResolutionCandidate) it2.next())) {
                                        setChooseMaximallySpecificCandidates = CollectionsKt.toMutableSet(setChooseMaximallySpecificCandidates);
                                        setChooseMaximallySpecificCandidates.removeAll(set);
                                        resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(setChooseMaximallySpecificCandidates);
                                        if (resolutionCandidate == null) {
                                            break;
                                        }
                                        resolutionCandidate.addDiagnostic(new CandidateChosenUsingOverloadResolutionByLambdaAnnotation());
                                        break;
                                    }
                                }
                            } else {
                                it2 = list.iterator();
                                while (it2.hasNext()) {
                                    if (setChooseMaximallySpecificCandidates.contains((ResolutionCandidate) it2.next())) {
                                        setChooseMaximallySpecificCandidates = CollectionsKt.toMutableSet(setChooseMaximallySpecificCandidates);
                                        setChooseMaximallySpecificCandidates.removeAll(set);
                                        resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(setChooseMaximallySpecificCandidates);
                                        if (resolutionCandidate == null) {
                                            break;
                                        }
                                        resolutionCandidate.addDiagnostic(new CandidateChosenUsingOverloadResolutionByLambdaAnnotation());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return setChooseMaximallySpecificCandidates;
    }
}
