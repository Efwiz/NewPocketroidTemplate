package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImpl;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: ControlFlowGraphRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 $2\u00020\u0001:\u0001$B\u001b\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u0013*\u00020\f2\u0006\u0010\u001d\u001a\u00020\u000eH\u0002J\f\u0010\u001e\u001a\u00020\u0013*\u00020\fH\u0002J$\u0010\u001f\u001a\u00020\u0013*\u00020\f2\u0016\u0010 \u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\"\u0012\u0004\u0012\u00020\t0!H\u0002J$\u0010#\u001a\u00020\u0013*\u00020\f2\u0016\u0010 \u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\"\u0012\u0004\u0012\u00020\t0!H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FirControlFlowGraphRenderVisitor;", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitorVoid;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "renderLevels", "", "(Ljava/lang/StringBuilder;Z)V", "clusterCounter", "", "nodeCounter", "printer", "Lorg/jetbrains/kotlin/utils/Printer;", "style", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;", "getStyle", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;)Ljava/lang/String;", "visitControlFlowGraphReference", "", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "enterCluster", TypedValues.Custom.S_COLOR, "exitCluster", "renderEdges", "nodes", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "renderNodes", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirControlFlowGraphRenderVisitor extends FirVisitorVoid {
    private static final Regex DIGIT_REGEX = new Regex("\\d");
    private int clusterCounter;
    private int nodeCounter;
    private final Printer printer;
    private final boolean renderLevels;

    public /* synthetic */ FirControlFlowGraphRenderVisitor(StringBuilder sb, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sb, (i & 2) != 0 ? false : z);
    }

    public FirControlFlowGraphRenderVisitor(StringBuilder builder, boolean z) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.renderLevels = z;
        this.printer = new Printer(builder);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        String strReplace$default = StringsKt.replace$default(file.getName(), ".", "_", false, 4, (Object) null);
        if (DIGIT_REGEX.matches(String.valueOf(StringsKt.first(strReplace$default)))) {
            strReplace$default = "_" + strReplace$default;
        }
        this.printer.println("digraph " + strReplace$default + " {").pushIndent().println("graph [nodesep=3]").println("node [shape=box penwidth=2]").println("edge [penwidth=2]").println(new Object[0]);
        mo6787visitElement(file);
        this.printer.popIndent().println("}");
    }

    private final void renderNodes(Printer printer, Map<CFGNode<?>, Integer> map) {
        String str;
        String str2 = "red";
        for (Map.Entry<CFGNode<?>, Integer> entry : map.entrySet()) {
            CFGNode<?> key = entry.getKey();
            int iIntValue = entry.getValue().intValue();
            if (key instanceof EnterNodeMarker) {
                enterCluster(printer, str2);
                str2 = "blue";
            }
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            sb.append(StringsKt.replace$default(CFGNodeRendererKt.render(key), "\"", "", false, 4, (Object) null));
            if (this.renderLevels) {
                sb.append(" [" + key.getLevel() + ']');
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            ArrayList arrayList2 = arrayList;
            arrayList2.add("label=\"" + string + '\"');
            if (key.getIsDead()) {
                str = "gray";
            } else if (Intrinsics.areEqual(key, key.getOwner().getEnterNode()) || Intrinsics.areEqual(key, key.getOwner().getExitNode())) {
                str = "red";
            } else {
                str = key.isUnion() ? "yellow" : null;
            }
            if (str != null) {
                arrayList2.add("style=\"filled\"");
                arrayList2.add("fillcolor=" + str);
            }
            printer.println(Integer.valueOf(iIntValue), CollectionsKt.joinToString$default(arrayList, " ", " [", "];", 0, null, null, 56, null));
            if (key instanceof ExitNodeMarker) {
                exitCluster(printer);
            }
        }
    }

    private final String getStyle(Edge edge) {
        String str;
        String str2;
        String[] strArr = new String[3];
        if (edge.getKind().getUsedInDfa() || edge.getKind().getUsedInDeadDfa()) {
            str = !edge.getKind().getUsedInCfa() ? "color=red" : null;
        } else {
            str = "color=green";
        }
        strArr[0] = str;
        if (edge.getKind().getIsDead()) {
            str2 = "style=dotted";
        } else {
            str2 = edge.getKind().getIsBack() ? "style=dashed" : null;
        }
        strArr[1] = str2;
        String label = edge.getLabel().getLabel();
        strArr[2] = label != null ? "label=\"" + label + '\"' : null;
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) strArr);
        if (listListOfNotNull.isEmpty()) {
            listListOfNotNull = null;
        }
        List list = listListOfNotNull;
        if (list != null) {
            return CollectionsKt.joinToString$default(list, " ", "[", "]", 0, null, null, 56, null);
        }
        return null;
    }

    private final void renderEdges(Printer printer, Map<CFGNode<?>, Integer> map) {
        for (Map.Entry<CFGNode<?>, Integer> entry : map.entrySet()) {
            CFGNode<?> key = entry.getKey();
            int iIntValue = entry.getValue().intValue();
            List<CFGNode<?>> followingNodes = key.getFollowingNodes();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : followingNodes) {
                String style = getStyle(key.edgeTo((CFGNode) obj));
                Object obj2 = linkedHashMap.get(style);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(style, obj2);
                }
                ((List) obj2).add(obj);
            }
            for (Map.Entry entry2 : CollectionsKt.sortedWith(linkedHashMap.entrySet(), new Comparator() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.cfg.FirControlFlowGraphRenderVisitor$renderEdges$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues((String) ((Map.Entry) t).getKey(), (String) ((Map.Entry) t2).getKey());
                }
            })) {
                String str = (String) entry2.getKey();
                List list = (List) entry2.getValue();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((Number) MapsKt.getValue(map, (CFGNode) it.next())).intValue()));
                }
                printer.print(Integer.valueOf(iIntValue), " -> ", CollectionsKt.joinToString$default(CollectionsKt.sorted(arrayList), " ", "{", "}", 0, null, null, 56, null));
                if (str != null) {
                    printer.printWithNoIndent(" " + str);
                }
                printer.printlnWithNoIndent(";");
            }
            if (key instanceof CFGNodeWithSubgraphs) {
                List<ControlFlowGraph> subGraphs = ((CFGNodeWithSubgraphs) key).getSubGraphs();
                ArrayList arrayList2 = new ArrayList();
                Iterator<T> it2 = subGraphs.iterator();
                while (it2.hasNext()) {
                    Integer num = map.get(((ControlFlowGraph) it2.next()).getEnterNode());
                    if (num != null) {
                        arrayList2.add(num);
                    }
                }
                List listSorted = CollectionsKt.sorted(arrayList2);
                if (!listSorted.isEmpty()) {
                    printer.print(Integer.valueOf(iIntValue), " -> ", CollectionsKt.joinToString$default(listSorted, " ", "{", "}", 0, null, null, 56, null));
                    printer.printlnWithNoIndent(" [style=dashed];");
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    /* JADX INFO: renamed from: visitElement */
    public void mo6787visitElement(FirElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.acceptChildren(this);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitControlFlowGraphReference(FirControlFlowGraphReference controlFlowGraphReference) {
        ControlFlowGraph controlFlowGraph;
        Intrinsics.checkNotNullParameter(controlFlowGraphReference, "controlFlowGraphReference");
        FirControlFlowGraphReferenceImpl firControlFlowGraphReferenceImpl = controlFlowGraphReference instanceof FirControlFlowGraphReferenceImpl ? (FirControlFlowGraphReferenceImpl) controlFlowGraphReference : null;
        if (firControlFlowGraphReferenceImpl == null || (controlFlowGraph = firControlFlowGraphReferenceImpl.getControlFlowGraph()) == null || controlFlowGraph.isSubGraph()) {
            return;
        }
        List list = DFS.topologicalOrder(CollectionsKt.listOf(controlFlowGraph.getEnterNode()), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.cfg.FirControlFlowGraphRenderVisitor$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return FirControlFlowGraphRenderVisitor.visitControlFlowGraphReference$lambda$9((CFGNode) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(list, "topologicalOrder(...)");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            int i = this.nodeCounter;
            this.nodeCounter = i + 1;
            linkedHashMap.put(obj, Integer.valueOf(i));
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        renderNodes(this.printer, linkedHashMap2);
        renderEdges(this.printer, linkedHashMap2);
        this.printer.println(new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable visitControlFlowGraphReference$lambda$9(CFGNode cFGNode) {
        return cFGNode.getFollowingNodes();
    }

    private final void enterCluster(Printer printer, String str) {
        StringBuilder sb = new StringBuilder("subgraph cluster_");
        int i = this.clusterCounter;
        this.clusterCounter = i + 1;
        printer.println(sb.append(i).append(" {").toString());
        printer.pushIndent();
        printer.println("color=" + str);
    }

    private final void exitCluster(Printer printer) {
        printer.popIndent();
        printer.println("}");
    }
}
