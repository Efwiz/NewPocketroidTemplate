package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.resolve.calls.tasks.SynthesizedInvokesKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: InvokeProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a>\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\t\u001a\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\u00052\u0006\u0010\r\u001a\u00020\tH\u0002¨\u0006\u000e"}, d2 = {"createCallTowerProcessorForExplicitInvoke", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "functionContext", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "expressionForInvoke", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "explicitReceiver", "getExtensionInvokeCandidateDescriptor", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "extensionFunctionReceiver", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InvokeProcessorsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final CandidateWithBoundDispatchReceiver getExtensionInvokeCandidateDescriptor(ImplicitScopeTower implicitScopeTower, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
        KotlinType type = receiverValueWithSmartCastInfo.getReceiverValue().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if (!FunctionTypesKt.isBuiltinExtensionFunctionalType(type)) {
            return null;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.single(type.getMemberScope().getContributedFunctions(OperatorNameConventions.INVOKE, implicitScopeTower.getLocation()));
        Collection<FunctionDescriptor> collectionCreateSynthesizedInvokes = SynthesizedInvokesKt.createSynthesizedInvokes(CollectionsKt.listOf(simpleFunctionDescriptor));
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) CollectionsKt.singleOrNull(collectionCreateSynthesizedInvokes);
        if (functionDescriptor == null) {
            throw new IllegalStateException(("No single synthesized invoke for " + simpleFunctionDescriptor + ": " + collectionCreateSynthesizedInvokes).toString());
        }
        return new CandidateWithBoundDispatchReceiver(receiverValueWithSmartCastInfo, functionDescriptor, CollectionsKt.emptyList());
    }

    public static final <C extends Candidate> ScopeTowerProcessor<C> createCallTowerProcessorForExplicitInvoke(ImplicitScopeTower scopeTower, CandidateFactory<? extends C> functionContext, ReceiverValueWithSmartCastInfo expressionForInvoke, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(functionContext, "functionContext");
        Intrinsics.checkNotNullParameter(expressionForInvoke, "expressionForInvoke");
        CandidateWithBoundDispatchReceiver extensionInvokeCandidateDescriptor = getExtensionInvokeCandidateDescriptor(scopeTower, expressionForInvoke);
        if (receiverValueWithSmartCastInfo != null) {
            if (extensionInvokeCandidateDescriptor == null) {
                return new KnownResultProcessor(CollectionsKt.emptyList());
            }
            return new InvokeExtensionScopeTowerProcessor(functionContext, extensionInvokeCandidateDescriptor, receiverValueWithSmartCastInfo);
        }
        ExplicitReceiverScopeTowerProcessor explicitReceiverScopeTowerProcessor = new ExplicitReceiverScopeTowerProcessor(scopeTower, functionContext, expressionForInvoke, new Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<? extends CandidateWithBoundDispatchReceiver>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.InvokeProcessorsKt$createCallTowerProcessorForExplicitInvoke$usualInvoke$1
            @Override // kotlin.jvm.functions.Function2
            public final Collection<CandidateWithBoundDispatchReceiver> invoke(ScopeTowerLevel $receiver, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo2) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                return $receiver.getFunctions(OperatorNameConventions.INVOKE, receiverValueWithSmartCastInfo2);
            }
        });
        if (extensionInvokeCandidateDescriptor == null) {
            return explicitReceiverScopeTowerProcessor;
        }
        return new PrioritizedCompositeScopeTowerProcessor(explicitReceiverScopeTowerProcessor, new InvokeExtensionScopeTowerProcessor(functionContext, extensionInvokeCandidateDescriptor, null));
    }
}
