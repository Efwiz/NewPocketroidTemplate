package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BinaryAndEnterRightOperandNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BinaryAndExitLeftOperandNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BinaryOrEnterRightOperandNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.BinaryOrExitLeftOperandNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EnterNodeMarker;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ExitNodeMarker;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopBlockEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.SplitPostponedLambdasNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.StubNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.TryExpressionEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenBranchResultEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenBranchResultExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenSyntheticElseBranchNode;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;

/* JADX INFO: compiled from: UnreachableCodeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0019B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J*\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100\u000f*\u00020\t2\u0012\b\u0002\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100\u0012H\u0002J\u001a\u0010\u0013\u001a\u00020\u0007*\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u0018*\u0006\u0012\u0002\b\u00030\u0010H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnreachableCodeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "()V", "sourceKindsToSkip", "", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "analyze", "", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "allNodes", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "acc", "", "collectInnerNodes", "Lorg/jetbrains/kotlin/fir/FirElement;", "nodes", "", "skipNode", "", "CollectNodesVisitor", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnreachableCodeChecker extends FirControlFlowChecker {
    public static final UnreachableCodeChecker INSTANCE = new UnreachableCodeChecker();
    private static final Set<KtFakeSourceElementKind> sourceKindsToSkip = SetsKt.setOf((Object[]) new KtFakeSourceElementKind[]{KtFakeSourceElementKind.ImplicitReturn.FromExpressionBody.INSTANCE, KtFakeSourceElementKind.ImplicitReturn.FromLastStatement.INSTANCE, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE});

    private UnreachableCodeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker
    public void analyze(ControlFlowGraph graph, DiagnosticReporter reporter, CheckerContext context) {
        Intrinsics.checkNotNullParameter(graph, "graph");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(context, "context");
        List listAllNodes$default = allNodes$default(this, graph, null, 1, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAllNodes$default) {
            if (!INSTANCE.skipNode((CFGNode) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (((CFGNode) obj2).getIsDead()) {
                arrayList2.add(obj2);
            } else {
                arrayList3.add(obj2);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        if (list.isEmpty()) {
            return;
        }
        List list3 = list;
        ArrayList arrayList4 = new ArrayList();
        Iterator it = list3.iterator();
        while (it.hasNext()) {
            KtSourceElement source = ((CFGNode) it.next()).getFir().getSource();
            if (source != null) {
                arrayList4.add(source);
            }
        }
        Set set = CollectionsKt.toSet(arrayList4);
        ArrayList arrayList5 = new ArrayList();
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            KtSourceElement source2 = ((CFGNode) it2.next()).getFir().getSource();
            if (source2 != null) {
                arrayList5.add(source2);
            }
        }
        Set set2 = CollectionsKt.toSet(arrayList5);
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator it3 = list3.iterator();
        while (it3.hasNext()) {
            arrayList6.add(((CFGNode) it3.next()).getFir());
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList arrayList7 = arrayList6;
        Iterator it4 = arrayList7.iterator();
        while (it4.hasNext()) {
            INSTANCE.collectInnerNodes((FirElement) it4.next(), linkedHashSet);
        }
        HashSet hashSet = new HashSet();
        ArrayList<FirElement> arrayList8 = new ArrayList();
        for (Object obj3 : arrayList7) {
            if (hashSet.add(((FirElement) obj3).getSource())) {
                arrayList8.add(obj3);
            }
        }
        for (FirElement firElement : arrayList8) {
            if (!linkedHashSet.contains(firElement)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firElement.getSource(), FirErrors.INSTANCE.getUNREACHABLE_CODE(), set2, set, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ List allNodes$default(UnreachableCodeChecker unreachableCodeChecker, ControlFlowGraph controlFlowGraph, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = new ArrayList();
        }
        return unreachableCodeChecker.allNodes(controlFlowGraph, list);
    }

    private final List<CFGNode<?>> allNodes(ControlFlowGraph controlFlowGraph, List<CFGNode<?>> list) {
        list.addAll(controlFlowGraph.getNodes());
        Iterator<T> it = controlFlowGraph.getSubGraphs().iterator();
        while (it.hasNext()) {
            INSTANCE.allNodes((ControlFlowGraph) it.next(), list);
        }
        return list;
    }

    private final boolean skipNode(CFGNode<?> cFGNode) {
        boolean z = (cFGNode instanceof ExitNodeMarker) || (cFGNode instanceof EnterNodeMarker) || (cFGNode instanceof StubNode) || (cFGNode instanceof SplitPostponedLambdasNode) || (cFGNode instanceof BinaryOrExitLeftOperandNode) || (cFGNode instanceof BinaryOrEnterRightOperandNode) || (cFGNode instanceof BinaryAndExitLeftOperandNode) || (cFGNode instanceof BinaryAndEnterRightOperandNode) || (cFGNode instanceof WhenSyntheticElseBranchNode) || (cFGNode instanceof WhenBranchResultEnterNode) || (cFGNode instanceof WhenBranchResultExitNode);
        if ((cFGNode instanceof LoopEnterNode) || (cFGNode instanceof LoopBlockEnterNode) || (cFGNode instanceof TryExpressionEnterNode)) {
            return false;
        }
        if (!z) {
            Set<KtFakeSourceElementKind> set = sourceKindsToSkip;
            KtSourceElement source = cFGNode.getFir().getSource();
            if (!CollectionsKt.contains(set, source != null ? source.getKind() : null)) {
                return false;
            }
        }
        return true;
    }

    private final void collectInnerNodes(FirElement firElement, Set<FirElement> set) {
        firElement.acceptChildren(new CollectNodesVisitor(set));
    }

    /* JADX INFO: compiled from: UnreachableCodeChecker.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UnreachableCodeChecker$CollectNodesVisitor;", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitorVoid;", "nodes", "", "Lorg/jetbrains/kotlin/fir/FirElement;", "(Ljava/util/Set;)V", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CollectNodesVisitor extends FirVisitorVoid {
        private final Set<FirElement> nodes;

        public CollectNodesVisitor(Set<FirElement> nodes) {
            Intrinsics.checkNotNullParameter(nodes, "nodes");
            this.nodes = nodes;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        /* JADX INFO: renamed from: visitElement */
        public void mo6787visitElement(FirElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            this.nodes.add(element);
            element.acceptChildren(this);
        }
    }
}
