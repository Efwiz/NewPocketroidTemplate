package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.DetailedReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000x\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a>\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u001aL\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\r\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00102\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u001a<\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0012\"\b\b\u0000\u0010\u0002*\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u001a\u0010\u0013\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00120\u0014\u001aH\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0012\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001az\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0012\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0017\u001a\u00020\u001828\u0010\u001a\u001a4\u0012\u0004\u0012\u00020\u001c\u0012\u0015\u0012\u0013\u0018\u00010\u0015¢\u0006\f\b\u001d\u0012\b\b\u0006\u0012\u0004\b\b(\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001bj\u0002`!¢\u0006\u0002\b\"H\u0002\u001ar\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b28\u0010\u001a\u001a4\u0012\u0004\u0012\u00020\u001c\u0012\u0015\u0012\u0013\u0018\u00010\u0015¢\u0006\f\b\u001d\u0012\b\b\u0006\u0012\u0004\b\b(\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001bj\u0002`!¢\u0006\u0002\b\"H\u0002\u001aH\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00020%\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001aH\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0012\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u0017\u001a\u00020\u0018*h\b\u0002\u0010'\"0\u0012\u0004\u0012\u00020\u001c\u0012\u0015\u0012\u0013\u0018\u00010\u0015¢\u0006\f\b\u001d\u0012\b\b\u0006\u0012\u0004\b\b(\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001b¢\u0006\u0002\b\"20\u0012\u0004\u0012\u00020\u001c\u0012\u0015\u0012\u0013\u0018\u00010\u0015¢\u0006\f\b\u001d\u0012\b\b\u0006\u0012\u0004\b\b(\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001b¢\u0006\u0002\b\"¨\u0006("}, d2 = {"createCallableReferenceProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/SimpleScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "name", "Lorg/jetbrains/kotlin/name/Name;", "context", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "createFunctionProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/PrioritizedCompositeScopeTowerProcessor;", "simpleContext", "factoryProviderForInvoke", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;", "createProcessorWithReceiverValueOrEmpty", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "createSimpleFunctionProcessor", "classValueReceiver", "", "createSimpleProcessor", "collectCandidates", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "Lkotlin/ParameterName;", "extensionReceiver", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidatesCollector;", "Lkotlin/ExtensionFunctionType;", "createSimpleProcessorWithoutClassValueReceiver", "createVariableAndObjectProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/VariableAndObjectScopeTowerProcessor;", "createVariableProcessor", "CandidatesCollector", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScopeTowerProcessorsKt {
    private static final <C extends Candidate> SimpleScopeTowerProcessor<C> createSimpleProcessorWithoutClassValueReceiver(ImplicitScopeTower implicitScopeTower, CandidateFactory<? extends C> candidateFactory, DetailedReceiver detailedReceiver, Function2<? super ScopeTowerLevel, ? super ReceiverValueWithSmartCastInfo, ? extends Collection<CandidateWithBoundDispatchReceiver>> function2) {
        if (detailedReceiver instanceof ReceiverValueWithSmartCastInfo) {
            return new ExplicitReceiverScopeTowerProcessor(implicitScopeTower, candidateFactory, (ReceiverValueWithSmartCastInfo) detailedReceiver, function2);
        }
        return detailedReceiver instanceof QualifierReceiver ? new QualifierScopeTowerProcessor(implicitScopeTower, candidateFactory, (QualifierReceiver) detailedReceiver, function2) : new NoExplicitReceiverScopeTowerProcessor(candidateFactory, function2);
    }

    private static final <C extends Candidate> ScopeTowerProcessor<C> createSimpleProcessor(ImplicitScopeTower implicitScopeTower, CandidateFactory<? extends C> candidateFactory, DetailedReceiver detailedReceiver, boolean z, Function2<? super ScopeTowerLevel, ? super ReceiverValueWithSmartCastInfo, ? extends Collection<CandidateWithBoundDispatchReceiver>> function2) {
        SimpleScopeTowerProcessor simpleScopeTowerProcessorCreateSimpleProcessorWithoutClassValueReceiver = createSimpleProcessorWithoutClassValueReceiver(implicitScopeTower, candidateFactory, detailedReceiver, function2);
        if (z && (detailedReceiver instanceof QualifierReceiver)) {
            ReceiverValueWithSmartCastInfo classValueReceiverWithSmartCastInfo = ((QualifierReceiver) detailedReceiver).getClassValueReceiverWithSmartCastInfo();
            return classValueReceiverWithSmartCastInfo == null ? simpleScopeTowerProcessorCreateSimpleProcessorWithoutClassValueReceiver : new PrioritizedCompositeScopeTowerProcessor(simpleScopeTowerProcessorCreateSimpleProcessorWithoutClassValueReceiver, new ExplicitReceiverScopeTowerProcessor(implicitScopeTower, candidateFactory, classValueReceiverWithSmartCastInfo, function2));
        }
        return simpleScopeTowerProcessorCreateSimpleProcessorWithoutClassValueReceiver;
    }

    public static final <C extends Candidate> SimpleScopeTowerProcessor<C> createCallableReferenceProcessor(ImplicitScopeTower scopeTower, final Name name2, CandidateFactory<? extends C> context, DetailedReceiver detailedReceiver) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        return new SamePriorityCompositeScopeTowerProcessor(createSimpleProcessorWithoutClassValueReceiver(scopeTower, context, detailedReceiver, new Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<? extends CandidateWithBoundDispatchReceiver>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessorsKt$createCallableReferenceProcessor$variable$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Collection<CandidateWithBoundDispatchReceiver> invoke(ScopeTowerLevel createSimpleProcessorWithoutClassValueReceiver, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
                Intrinsics.checkNotNullParameter(createSimpleProcessorWithoutClassValueReceiver, "$this$createSimpleProcessorWithoutClassValueReceiver");
                return createSimpleProcessorWithoutClassValueReceiver.getVariables(name2, receiverValueWithSmartCastInfo);
            }
        }), createSimpleProcessorWithoutClassValueReceiver(scopeTower, context, detailedReceiver, new Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<? extends CandidateWithBoundDispatchReceiver>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessorsKt$createCallableReferenceProcessor$function$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Collection<CandidateWithBoundDispatchReceiver> invoke(ScopeTowerLevel createSimpleProcessorWithoutClassValueReceiver, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
                Intrinsics.checkNotNullParameter(createSimpleProcessorWithoutClassValueReceiver, "$this$createSimpleProcessorWithoutClassValueReceiver");
                return createSimpleProcessorWithoutClassValueReceiver.getFunctions(name2, receiverValueWithSmartCastInfo);
            }
        }));
    }

    public static /* synthetic */ ScopeTowerProcessor createVariableProcessor$default(ImplicitScopeTower implicitScopeTower, Name name2, CandidateFactory candidateFactory, DetailedReceiver detailedReceiver, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            z = true;
        }
        return createVariableProcessor(implicitScopeTower, name2, candidateFactory, detailedReceiver, z);
    }

    public static final <C extends Candidate> ScopeTowerProcessor<C> createVariableProcessor(ImplicitScopeTower scopeTower, final Name name2, CandidateFactory<? extends C> context, DetailedReceiver detailedReceiver, boolean z) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        return createSimpleProcessor(scopeTower, context, detailedReceiver, z, new Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<? extends CandidateWithBoundDispatchReceiver>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessorsKt.createVariableProcessor.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Collection<CandidateWithBoundDispatchReceiver> invoke(ScopeTowerLevel createSimpleProcessor, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
                Intrinsics.checkNotNullParameter(createSimpleProcessor, "$this$createSimpleProcessor");
                return createSimpleProcessor.getVariables(name2, receiverValueWithSmartCastInfo);
            }
        });
    }

    public static /* synthetic */ VariableAndObjectScopeTowerProcessor createVariableAndObjectProcessor$default(ImplicitScopeTower implicitScopeTower, Name name2, CandidateFactory candidateFactory, DetailedReceiver detailedReceiver, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            z = true;
        }
        return createVariableAndObjectProcessor(implicitScopeTower, name2, candidateFactory, detailedReceiver, z);
    }

    public static final <C extends Candidate> VariableAndObjectScopeTowerProcessor<C> createVariableAndObjectProcessor(ImplicitScopeTower scopeTower, final Name name2, CandidateFactory<? extends C> context, DetailedReceiver detailedReceiver, boolean z) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        return new VariableAndObjectScopeTowerProcessor<>(createVariableProcessor$default(scopeTower, name2, context, detailedReceiver, false, 16, null), createSimpleProcessor(scopeTower, context, detailedReceiver, z, new Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<? extends CandidateWithBoundDispatchReceiver>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessorsKt.createVariableAndObjectProcessor.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Collection<CandidateWithBoundDispatchReceiver> invoke(ScopeTowerLevel createSimpleProcessor, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
                Intrinsics.checkNotNullParameter(createSimpleProcessor, "$this$createSimpleProcessor");
                return createSimpleProcessor.getObjects(name2, receiverValueWithSmartCastInfo);
            }
        }));
    }

    public static /* synthetic */ ScopeTowerProcessor createSimpleFunctionProcessor$default(ImplicitScopeTower implicitScopeTower, Name name2, CandidateFactory candidateFactory, DetailedReceiver detailedReceiver, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            z = true;
        }
        return createSimpleFunctionProcessor(implicitScopeTower, name2, candidateFactory, detailedReceiver, z);
    }

    public static final <C extends Candidate> ScopeTowerProcessor<C> createSimpleFunctionProcessor(ImplicitScopeTower scopeTower, final Name name2, CandidateFactory<? extends C> context, DetailedReceiver detailedReceiver, boolean z) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        return createSimpleProcessor(scopeTower, context, detailedReceiver, z, new Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<? extends CandidateWithBoundDispatchReceiver>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessorsKt.createSimpleFunctionProcessor.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Collection<CandidateWithBoundDispatchReceiver> invoke(ScopeTowerLevel createSimpleProcessor, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
                Intrinsics.checkNotNullParameter(createSimpleProcessor, "$this$createSimpleProcessor");
                return createSimpleProcessor.getFunctions(name2, receiverValueWithSmartCastInfo);
            }
        });
    }

    public static final <C extends Candidate> PrioritizedCompositeScopeTowerProcessor<C> createFunctionProcessor(final ImplicitScopeTower scopeTower, final Name name2, CandidateFactory<? extends C> simpleContext, final CandidateFactoryProviderForInvoke<C> factoryProviderForInvoke, DetailedReceiver detailedReceiver) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(simpleContext, "simpleContext");
        Intrinsics.checkNotNullParameter(factoryProviderForInvoke, "factoryProviderForInvoke");
        return new PrioritizedCompositeScopeTowerProcessor<>(createSimpleFunctionProcessor$default(scopeTower, name2, simpleContext, detailedReceiver, false, 16, null), new InvokeTowerProcessor(scopeTower, name2, factoryProviderForInvoke, detailedReceiver), createProcessorWithReceiverValueOrEmpty(detailedReceiver, new Function1<ReceiverValueWithSmartCastInfo, ScopeTowerProcessor<? extends C>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessorsKt$createFunctionProcessor$invokeExtensionProcessor$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ScopeTowerProcessor<C> invoke(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
                return new InvokeExtensionTowerProcessor(scopeTower, name2, factoryProviderForInvoke, receiverValueWithSmartCastInfo);
            }
        }));
    }

    public static final <C extends Candidate> ScopeTowerProcessor<C> createProcessorWithReceiverValueOrEmpty(DetailedReceiver detailedReceiver, Function1<? super ReceiverValueWithSmartCastInfo, ? extends ScopeTowerProcessor<? extends C>> create) {
        ScopeTowerProcessor<? extends C> scopeTowerProcessorInvoke;
        Intrinsics.checkNotNullParameter(create, "create");
        if (detailedReceiver instanceof QualifierReceiver) {
            ReceiverValueWithSmartCastInfo classValueReceiverWithSmartCastInfo = ((QualifierReceiver) detailedReceiver).getClassValueReceiverWithSmartCastInfo();
            return (classValueReceiverWithSmartCastInfo == null || (scopeTowerProcessorInvoke = create.invoke(classValueReceiverWithSmartCastInfo)) == null) ? new KnownResultProcessor(CollectionsKt.emptyList()) : scopeTowerProcessorInvoke;
        }
        return create.invoke((ReceiverValueWithSmartCastInfo) detailedReceiver);
    }
}
