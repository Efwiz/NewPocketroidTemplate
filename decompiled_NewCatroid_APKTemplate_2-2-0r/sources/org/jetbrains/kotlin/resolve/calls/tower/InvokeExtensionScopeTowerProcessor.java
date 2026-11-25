package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: InvokeProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B%\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u001e\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0013\u001a\u00020\u000fH\u0016R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/InvokeExtensionScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractSimpleScopeTowerProcessor;", "context", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "invokeCandidateDescriptor", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "recordLookups", "", "skippedData", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "simpleProcess", "data", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InvokeExtensionScopeTowerProcessor<C extends Candidate> extends AbstractSimpleScopeTowerProcessor<C> {
    private final ReceiverValueWithSmartCastInfo explicitReceiver;
    private final CandidateWithBoundDispatchReceiver invokeCandidateDescriptor;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
        Intrinsics.checkNotNullParameter(skippedData, "skippedData");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InvokeExtensionScopeTowerProcessor(CandidateFactory<? extends C> context, CandidateWithBoundDispatchReceiver invokeCandidateDescriptor, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(invokeCandidateDescriptor, "invokeCandidateDescriptor");
        this.invokeCandidateDescriptor = invokeCandidateDescriptor;
        this.explicitReceiver = receiverValueWithSmartCastInfo;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.SimpleScopeTowerProcessor
    public Collection<C> simpleProcess(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (this.explicitReceiver != null && Intrinsics.areEqual(data, TowerData.Empty.INSTANCE)) {
            return CollectionsKt.listOf(getCandidateFactory().createCandidate(this.invokeCandidateDescriptor, ExplicitReceiverKind.BOTH_RECEIVERS, this.explicitReceiver));
        }
        if (this.explicitReceiver == null && (data instanceof TowerData.OnlyImplicitReceiver)) {
            return CollectionsKt.listOf(getCandidateFactory().createCandidate(this.invokeCandidateDescriptor, ExplicitReceiverKind.DISPATCH_RECEIVER, ((TowerData.OnlyImplicitReceiver) data).getImplicitReceiver()));
        }
        return CollectionsKt.emptyList();
    }
}
