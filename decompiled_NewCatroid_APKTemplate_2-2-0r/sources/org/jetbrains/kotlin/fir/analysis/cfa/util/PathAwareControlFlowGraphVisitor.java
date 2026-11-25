package org.jetbrains.kotlin.fir.analysis.cfa.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeWithSubgraphs;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.Edge;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FinallyBlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.NormalPath;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.UncaughtExceptionPath;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: PathAwareControlFlowGraphVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\u0016\b\u0000\u0010\u0001*\u0010\u0012\u0004\u0012\u0002H\u0001\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00022:\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00010\u0004j\b\u0012\u0004\u0012\u0002H\u0001`\u0006\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00010\u0004j\b\u0012\u0004\u0012\u0002H\u0001`\u00060\u0003B\u0005¢\u0006\u0002\u0010\u0007J\\\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\u0004j\b\u0012\u0004\u0012\u00028\u0000`\u00062\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\u000f\u001a\u00020\u00102\u001c\u0010\u0011\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\u0004j\b\u0012\u0004\u0012\u00028\u0000`\u0006H\u0016JH\u0010\u0012\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\u0004j\b\u0012\u0004\u0012\u00028\u0000`\u00062\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\r2\u001c\u0010\u0011\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\u0004j\b\u0012\u0004\u0012\u00028\u0000`\u0006H\u0016J\u001c\u0010\u0014\u001a\u00020\u00152\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R(\u0010\b\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\u0004j\b\u0012\u0004\u0012\u00028\u0000`\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowGraphVisitor;", "I", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowInfo;", "()V", "emptyInfo", "getEmptyInfo", "()Lkotlinx/collections/immutable/PersistentMap;", "visitEdge", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "to", "metadata", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;", "data", "visitNode", "node", "visitSubGraph", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithSubgraphs;", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PathAwareControlFlowGraphVisitor<I extends ControlFlowInfo<I, ?, ?>> extends ControlFlowGraphVisitor<PersistentMap<EdgeLabel, ? extends I>, PersistentMap<EdgeLabel, ? extends I>> {
    public abstract PersistentMap<EdgeLabel, I> getEmptyInfo();

    /* JADX WARN: Multi-variable type inference failed */
    public PersistentMap<EdgeLabel, I> visitNode(CFGNode<?> node, PersistentMap<EdgeLabel, ? extends I> data) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    public boolean visitSubGraph(CFGNodeWithSubgraphs<?> node, ControlFlowGraph graph) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(graph, "graph");
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitNode(CFGNode cFGNode, Object obj) {
        return visitNode((CFGNode<?>) cFGNode, (PersistentMap) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PersistentMap<EdgeLabel, I> visitEdge(CFGNode<?> from, CFGNode<?> to, Edge metadata, PersistentMap<EdgeLabel, ? extends I> data) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(data, "data");
        EdgeLabel label = metadata.getLabel();
        if (from instanceof FinallyBlockExitNode) {
            if (!Intrinsics.areEqual(label, UncaughtExceptionPath.INSTANCE)) {
                ControlFlowInfo controlFlowInfo = (ControlFlowInfo) data.get(label);
                return controlFlowInfo == null ? getEmptyInfo() : ExtensionsKt.persistentMapOf(TuplesKt.to(NormalPath.INSTANCE, controlFlowInfo));
            }
            PersistentMap.Builder builder = data.builder();
            PersistentMap.Builder builder2 = builder;
            Iterator<CFGNode<?>> it = from.getFollowingNodes().iterator();
            while (it.hasNext()) {
                EdgeLabel label2 = from.edgeTo(it.next()).getLabel();
                if (!Intrinsics.areEqual(label2, UncaughtExceptionPath.INSTANCE)) {
                    builder2.remove(label2);
                }
            }
            PersistentMap<EdgeLabel, I> persistentMapBuild = builder.build();
            if (persistentMapBuild.isEmpty()) {
                persistentMapBuild = getEmptyInfo();
            }
            return persistentMapBuild;
        }
        if (Intrinsics.areEqual(label, NormalPath.INSTANCE)) {
            return data;
        }
        Pair[] pairArr = new Pair[1];
        Iterator it2 = data.values().iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = ((ControlFlowInfo) next).merge((ControlFlowInfo) it2.next());
        }
        pairArr[0] = TuplesKt.to(label, next);
        return ExtensionsKt.persistentMapOf(pairArr);
    }
}
