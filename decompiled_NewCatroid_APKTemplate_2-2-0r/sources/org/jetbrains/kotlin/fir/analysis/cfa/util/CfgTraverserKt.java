package org.jetbrains.kotlin.fir.analysis.cfa.util;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeWithSubgraphs;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.Edge;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: CfgTraverser.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0000\u001a^\u0010\u0000\u001a(\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00050\u0003j\b\u0012\u0004\u0012\u0002H\u0005`\u00060\u0001\"\u0016\b\u0000\u0010\u0005*\u0010\u0012\u0004\u0012\u0002H\u0005\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\f\u001ah\u0010\r\u001a\u00020\u000e\"\u0016\b\u0000\u0010\u0005*\u0010\u0012\u0004\u0012\u0002H\u0005\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\f2,\u0010\u000f\u001a(\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00050\u0003j\b\u0012\u0004\u0012\u0002H\u0005`\u00060\u0010H\u0002¨\u0006\u0011"}, d2 = {"collectDataForNode", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "I", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowInfo;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/TraverseDirection;", "visitor", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowGraphVisitor;", "collectDataForNodeInternal", "", "nodeMap", "", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CfgTraverserKt {

    /* JADX INFO: compiled from: CfgTraverser.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TraverseDirection.values().length];
            try {
                iArr[TraverseDirection.Forward.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TraverseDirection.Backward.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final <I extends ControlFlowInfo<I, ?, ?>> Map<CFGNode<?>, PersistentMap<EdgeLabel, I>> collectDataForNode(ControlFlowGraph controlFlowGraph, TraverseDirection direction, PathAwareControlFlowGraphVisitor<I> visitor) {
        LinkedHashMap linkedHashMap;
        Intrinsics.checkNotNullParameter(controlFlowGraph, "<this>");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        do {
            linkedHashMap = linkedHashMap2;
        } while (collectDataForNodeInternal(controlFlowGraph, direction, visitor, linkedHashMap));
        return linkedHashMap;
    }

    private static final <I extends ControlFlowInfo<I, ?, ?>> boolean collectDataForNodeInternal(ControlFlowGraph controlFlowGraph, TraverseDirection traverseDirection, PathAwareControlFlowGraphVisitor<I> pathAwareControlFlowGraphVisitor, Map<CFGNode<?>, PersistentMap<EdgeLabel, I>> map) {
        List<CFGNode<?>> previousCfgNodes;
        Object next;
        Edge edgeEdgeFrom;
        boolean z = false;
        for (CFGNode<?> cFGNode : CfgUtilsKt.getNodesInOrder(controlFlowGraph, traverseDirection)) {
            if (traverseDirection == TraverseDirection.Backward && (cFGNode instanceof CFGNodeWithSubgraphs)) {
                CFGNodeWithSubgraphs<?> cFGNodeWithSubgraphs = (CFGNodeWithSubgraphs) cFGNode;
                for (ControlFlowGraph controlFlowGraph2 : cFGNodeWithSubgraphs.getSubGraphs()) {
                    z |= pathAwareControlFlowGraphVisitor.visitSubGraph(cFGNodeWithSubgraphs, controlFlowGraph2) && collectDataForNodeInternal(controlFlowGraph2, traverseDirection, pathAwareControlFlowGraphVisitor, map);
                }
            }
            boolean zIsUnion = cFGNode.isUnion();
            int i = WhenMappings.$EnumSwitchMapping$0[traverseDirection.ordinal()];
            if (i == 1) {
                previousCfgNodes = CfgUtilsKt.getPreviousCfgNodes(cFGNode);
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                previousCfgNodes = CfgUtilsKt.getFollowingCfgNodes(cFGNode);
            }
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = previousCfgNodes.iterator();
            while (true) {
                next = null;
                if (it.hasNext()) {
                    CFGNode<?> cFGNode2 = (CFGNode) it.next();
                    PersistentMap<EdgeLabel, I> persistentMap = map.get(cFGNode2);
                    if (persistentMap != null) {
                        int i2 = WhenMappings.$EnumSwitchMapping$0[traverseDirection.ordinal()];
                        if (i2 == 1) {
                            edgeEdgeFrom = cFGNode.edgeFrom(cFGNode2);
                        } else {
                            if (i2 != 2) {
                                throw new NoWhenBranchMatchedException();
                            }
                            edgeEdgeFrom = cFGNode.edgeTo(cFGNode2);
                        }
                        next = pathAwareControlFlowGraphVisitor.visitEdge(cFGNode2, cFGNode, edgeEdgeFrom, persistentMap);
                    }
                    if (next != null) {
                        arrayList.add(next);
                    }
                }
            }
            Iterator it2 = arrayList.iterator();
            if (it2.hasNext()) {
                next = it2.next();
                while (it2.hasNext()) {
                    next = PathAwareControlFlowGraphVisitorKt.join((PersistentMap) next, (PersistentMap) it2.next(), zIsUnion);
                }
            }
            PersistentMap<EdgeLabel, I> emptyInfo = (PersistentMap) next;
            PathAwareControlFlowGraphVisitor<I> pathAwareControlFlowGraphVisitor2 = pathAwareControlFlowGraphVisitor;
            if (emptyInfo == null) {
                emptyInfo = pathAwareControlFlowGraphVisitor.getEmptyInfo();
            }
            PersistentMap<EdgeLabel, I> persistentMap2 = (PersistentMap) cFGNode.accept(pathAwareControlFlowGraphVisitor2, emptyInfo);
            if (!Intrinsics.areEqual(persistentMap2, map.put(cFGNode, persistentMap2))) {
                z = true;
            }
            if (traverseDirection == TraverseDirection.Forward && (cFGNode instanceof CFGNodeWithSubgraphs)) {
                CFGNodeWithSubgraphs<?> cFGNodeWithSubgraphs2 = (CFGNodeWithSubgraphs) cFGNode;
                for (ControlFlowGraph controlFlowGraph3 : cFGNodeWithSubgraphs2.getSubGraphs()) {
                    z |= pathAwareControlFlowGraphVisitor.visitSubGraph(cFGNodeWithSubgraphs2, controlFlowGraph3) && collectDataForNodeInternal(controlFlowGraph3, traverseDirection, pathAwareControlFlowGraphVisitor, map);
                }
            }
        }
        return z;
    }
}
