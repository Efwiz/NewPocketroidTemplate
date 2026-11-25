package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001d\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u0001*\u0006\u0012\u0002\b\u00030\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u001d\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0001*\u0006\u0012\u0002\b\u00030\u00018F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003\"/\u0010\u0006\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\b0\u0007*\u0006\u0012\u0002\b\u00030\u00018F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"#\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\u0007*\u0006\u0012\u0002\b\u00030\u00018F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b¨\u0006\u000e"}, d2 = {"firstPreviousNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "getFirstPreviousNode", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;)Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "lastPreviousNode", "getLastPreviousNode", "previousDfaNodes", "Lkotlin/sequences/Sequence;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;", "getPreviousDfaNodes", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;)Lkotlin/sequences/Sequence;", "previousLiveNodes", "getPreviousLiveNodes", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CFGNodeKt {
    public static final CFGNode<?> getFirstPreviousNode(CFGNode<?> cFGNode) {
        Intrinsics.checkNotNullParameter(cFGNode, "<this>");
        return cFGNode.getPreviousNodes().get(0);
    }

    public static final CFGNode<?> getLastPreviousNode(CFGNode<?> cFGNode) {
        Intrinsics.checkNotNullParameter(cFGNode, "<this>");
        return (CFGNode) CollectionsKt.last((List) cFGNode.getPreviousNodes());
    }

    public static final Sequence<Pair<Edge, CFGNode<?>>> getPreviousDfaNodes(final CFGNode<?> cFGNode) {
        Intrinsics.checkNotNullParameter(cFGNode, "<this>");
        return SequencesKt.filter(SequencesKt.map(CollectionsKt.asSequence(cFGNode.getPreviousNodes()), new Function1<CFGNode<?>, Pair<? extends Edge, ? extends CFGNode<?>>>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeKt$previousDfaNodes$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Pair<Edge, CFGNode<?>> invoke(CFGNode<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return TuplesKt.to(cFGNode.edgeFrom(it), it);
            }
        }), new Function1<Pair<? extends Edge, ? extends CFGNode<?>>, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeKt$previousDfaNodes$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(Pair<Edge, ? extends CFGNode<?>> pair) {
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                Edge edgeComponent1 = pair.component1();
                boolean isDead = cFGNode.getIsDead();
                EdgeKind kind = edgeComponent1.getKind();
                return Boolean.valueOf(isDead ? kind.getUsedInDeadDfa() : kind.getUsedInDfa());
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Pair<? extends Edge, ? extends CFGNode<?>> pair) {
                return invoke2((Pair<Edge, ? extends CFGNode<?>>) pair);
            }
        });
    }

    public static final Sequence<CFGNode<?>> getPreviousLiveNodes(CFGNode<?> cFGNode) {
        Intrinsics.checkNotNullParameter(cFGNode, "<this>");
        return cFGNode.getIsDead() ? CollectionsKt.asSequence(cFGNode.getPreviousNodes()) : SequencesKt.mapNotNull(CollectionsKt.asSequence(cFGNode.getPreviousNodes()), new Function1<CFGNode<?>, CFGNode<?>>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeKt$previousLiveNodes$1
            @Override // kotlin.jvm.functions.Function1
            public final CFGNode<?> invoke(CFGNode<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.getIsDead()) {
                    return null;
                }
                return it;
            }
        });
    }
}
