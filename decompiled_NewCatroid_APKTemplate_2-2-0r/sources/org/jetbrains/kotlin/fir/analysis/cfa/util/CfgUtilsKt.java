package org.jetbrains.kotlin.fir.analysis.cfa.util;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeKind;

/* JADX INFO: compiled from: CfgUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001*\u00020\b2\u0006\u0010\t\u001a\u00020\n\"#\u0010\u0000\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"#\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u000b"}, d2 = {"followingCfgNodes", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "getFollowingCfgNodes", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;)Ljava/util/List;", "previousCfgNodes", "getPreviousCfgNodes", "getNodesInOrder", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/TraverseDirection;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CfgUtilsKt {

    /* JADX INFO: compiled from: CfgUtils.kt */
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

    public static final List<CFGNode<?>> getNodesInOrder(ControlFlowGraph controlFlowGraph, TraverseDirection direction) {
        Intrinsics.checkNotNullParameter(controlFlowGraph, "<this>");
        Intrinsics.checkNotNullParameter(direction, "direction");
        int i = WhenMappings.$EnumSwitchMapping$0[direction.ordinal()];
        if (i == 1) {
            return controlFlowGraph.getNodes();
        }
        if (i == 2) {
            return CollectionsKt.asReversed(controlFlowGraph.getNodes());
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final List<CFGNode<?>> getPreviousCfgNodes(CFGNode<?> cFGNode) {
        boolean usedInCfa;
        Intrinsics.checkNotNullParameter(cFGNode, "<this>");
        List<CFGNode<?>> previousNodes = cFGNode.getPreviousNodes();
        ArrayList arrayList = new ArrayList();
        for (Object obj : previousNodes) {
            EdgeKind kind = cFGNode.edgeFrom((CFGNode) obj).getKind();
            if (cFGNode.getIsDead()) {
                usedInCfa = kind.getUsedInCfa();
            } else {
                usedInCfa = kind.getUsedInCfa() && !kind.getIsDead();
            }
            if (usedInCfa) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final List<CFGNode<?>> getFollowingCfgNodes(CFGNode<?> cFGNode) {
        Intrinsics.checkNotNullParameter(cFGNode, "<this>");
        List<CFGNode<?>> followingNodes = cFGNode.getFollowingNodes();
        ArrayList arrayList = new ArrayList();
        for (Object obj : followingNodes) {
            EdgeKind kind = cFGNode.edgeTo((CFGNode) obj).getKind();
            if (kind.getUsedInCfa() && !kind.getIsDead()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
