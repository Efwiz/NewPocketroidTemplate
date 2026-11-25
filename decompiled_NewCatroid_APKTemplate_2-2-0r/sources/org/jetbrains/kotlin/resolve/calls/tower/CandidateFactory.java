package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: TowerResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003J+\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH&¢\u0006\u0002\u0010\fJ'\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\r\u001a\u0004\u0018\u00010\u000bH&¢\u0006\u0002\u0010\u000eJ\r\u0010\u000f\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "", "createCandidate", "towerCandidate", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "extensionReceiverCandidates", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Ljava/util/List;)Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "extensionReceiver", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "createErrorCandidate", "()Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CandidateFactory<C extends Candidate> {
    C createCandidate(CandidateWithBoundDispatchReceiver towerCandidate, ExplicitReceiverKind explicitReceiverKind, List<ReceiverValueWithSmartCastInfo> extensionReceiverCandidates);

    C createCandidate(CandidateWithBoundDispatchReceiver towerCandidate, ExplicitReceiverKind explicitReceiverKind, ReceiverValueWithSmartCastInfo extensionReceiver);

    C createErrorCandidate();
}
