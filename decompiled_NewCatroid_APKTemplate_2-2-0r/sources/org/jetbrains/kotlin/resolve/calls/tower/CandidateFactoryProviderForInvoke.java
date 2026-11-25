package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: TowerResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J1\u0010\u0004\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007\u0018\u00010\u00052\u0006\u0010\b\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\nH&¢\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u0006\u0010\r\u001a\u00020\nH&J\u001d\u0010\u000e\u001a\u00028\u00002\u0006\u0010\b\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "", "factoryForInvoke", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "variable", "useExplicitReceiver", "", "(Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;Z)Lkotlin/Pair;", "factoryForVariable", "stripExplicitReceiver", "transformCandidate", "invoke", "(Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;)Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CandidateFactoryProviderForInvoke<C extends Candidate> {
    Pair<ReceiverValueWithSmartCastInfo, CandidateFactory<C>> factoryForInvoke(C variable, boolean useExplicitReceiver);

    CandidateFactory<C> factoryForVariable(boolean stripExplicitReceiver);

    C transformCandidate(C variable, C invoke);
}
