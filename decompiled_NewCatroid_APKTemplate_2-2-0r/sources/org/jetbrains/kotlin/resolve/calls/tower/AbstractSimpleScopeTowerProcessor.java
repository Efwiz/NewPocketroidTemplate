package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b \u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J,\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\n2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractSimpleScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/SimpleScopeTowerProcessor;", "candidateFactory", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;)V", "getCandidateFactory", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "createCandidates", "", "collector", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractSimpleScopeTowerProcessor<C extends Candidate> implements SimpleScopeTowerProcessor<C> {
    private final CandidateFactory<C> candidateFactory;

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractSimpleScopeTowerProcessor(CandidateFactory<? extends C> candidateFactory) {
        Intrinsics.checkNotNullParameter(candidateFactory, "candidateFactory");
        this.candidateFactory = candidateFactory;
    }

    public final CandidateFactory<C> getCandidateFactory() {
        return this.candidateFactory;
    }

    public final Collection<C> createCandidates(Collection<CandidateWithBoundDispatchReceiver> collector, ExplicitReceiverKind kind, ReceiverValueWithSmartCastInfo receiver) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(kind, "kind");
        ArrayList arrayList = new ArrayList();
        for (CandidateWithBoundDispatchReceiver candidateWithBoundDispatchReceiver : collector) {
            if (TowerUtilsKt.getRequiresExtensionReceiver(candidateWithBoundDispatchReceiver) == (receiver != null)) {
                arrayList.add(this.candidateFactory.createCandidate(candidateWithBoundDispatchReceiver, kind, receiver));
            }
        }
        return arrayList;
    }
}
