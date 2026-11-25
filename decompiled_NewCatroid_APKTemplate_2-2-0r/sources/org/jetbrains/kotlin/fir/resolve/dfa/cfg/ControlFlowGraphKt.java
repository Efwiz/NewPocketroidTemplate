package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ControlFlowGraph.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0006*\u00020\u0007H\u0002\"\u001c\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\b"}, d2 = {"previousNodeCount", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "getPreviousNodeCount", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;)I", "orderNodes", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowGraphKt {
    private static final int getPreviousNodeCount(CFGNode<?> cFGNode) {
        List<CFGNode<?>> previousNodes = cFGNode.getPreviousNodes();
        int i = 0;
        if (!(previousNodes instanceof Collection) || !previousNodes.isEmpty()) {
            for (CFGNode<?> cFGNode2 : previousNodes) {
                if (Intrinsics.areEqual(cFGNode2.getOwner(), cFGNode.getOwner()) && !cFGNode.edgeFrom(cFGNode2).getKind().getIsBack() && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<CFGNode<?>> orderNodes(ControlFlowGraph controlFlowGraph) {
        ArrayList arrayList = new ArrayList(controlFlowGraph.getNodeCount());
        arrayList.add(controlFlowGraph.getEnterNode());
        int[] iArr = new int[controlFlowGraph.getNodeCount()];
        int i = 0;
        while (i < arrayList.size()) {
            int i2 = i + 1;
            Object obj = arrayList.get(i);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            CFGNode cFGNode = (CFGNode) obj;
            for (CFGNode<?> cFGNode2 : cFGNode.getFollowingNodes()) {
                if (Intrinsics.areEqual(cFGNode2.getOwner(), controlFlowGraph)) {
                    if (cFGNode2.getPreviousNodes().size() == 1) {
                        arrayList.add(cFGNode2);
                    } else if (!cFGNode.edgeTo(cFGNode2).getKind().getIsBack()) {
                        int previousNodeCount = iArr[cFGNode2.getId()];
                        if (previousNodeCount == 0) {
                            previousNodeCount = getPreviousNodeCount(cFGNode2);
                        }
                        int i3 = previousNodeCount - 1;
                        if (i3 == 0) {
                            arrayList.add(cFGNode2);
                        }
                        iArr[cFGNode2.getId()] = i3;
                    }
                }
            }
            i = i2;
        }
        arrayList.size();
        controlFlowGraph.getNodeCount();
        return arrayList;
    }
}
