package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ControlFlowGraph.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u00011B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010(\u001a\u00020)H\u0007J+\u0010*\u001a\u00020)\"\u0004\b\u0000\u0010+2\u0010\u0010,\u001a\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H+0-2\u0006\u0010.\u001a\u0002H+¢\u0006\u0002\u0010/J\u000e\u0010*\u001a\u00020)2\u0006\u0010,\u001a\u000200R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR,\u0010\r\u001a\u0006\u0012\u0002\b\u00030\f2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f@GX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R,\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\f2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f@GX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR$\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u000b\u001a\u00020\u001c@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R2\u0010#\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\"2\u0010\u0010\u000b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\"@BX\u0086.¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00000\"8F¢\u0006\u0006\u001a\u0004\b'\u0010%¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "name", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph$Kind;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Ljava/lang/String;Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph$Kind;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "<set-?>", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "enterNode", "getEnterNode", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "setEnterNode", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;)V", "exitNode", "getExitNode", "setExitNode", "isSubGraph", "", "()Z", "getKind", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph$Kind;", "getName", "()Ljava/lang/String;", "", "nodeCount", "getNodeCount", "()I", "setNodeCount", "(I)V", "", "nodes", "getNodes", "()Ljava/util/List;", "subGraphs", "getSubGraphs", "complete", "", "traverse", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)V", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitorVoid;", Namer.CLASS_KIND_ENUM, "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowGraph {
    private final FirDeclaration declaration;
    public CFGNode<?> enterNode;
    public CFGNode<?> exitNode;
    private final Kind kind;
    private final String name;
    private int nodeCount;
    private List<? extends CFGNode<?>> nodes;

    /* JADX INFO: compiled from: ControlFlowGraph.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph$Kind;", "", "(Ljava/lang/String;I)V", "Class", "Function", "LocalFunction", "AnonymousFunction", "AnonymousFunctionCalledInPlace", "PropertyInitializer", "ClassInitializer", "FieldInitializer", "FakeCall", "DefaultArgument", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        Class,
        Function,
        LocalFunction,
        AnonymousFunction,
        AnonymousFunctionCalledInPlace,
        PropertyInitializer,
        ClassInitializer,
        FieldInitializer,
        FakeCall,
        DefaultArgument;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }
    }

    public ControlFlowGraph(FirDeclaration firDeclaration, String name2, Kind kind) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.declaration = firDeclaration;
        this.name = name2;
        this.kind = kind;
    }

    public final FirDeclaration getDeclaration() {
        return this.declaration;
    }

    public final Kind getKind() {
        return this.kind;
    }

    public final String getName() {
        return this.name;
    }

    public final int getNodeCount() {
        return this.nodeCount;
    }

    @CfgInternals
    public final void setNodeCount(int i) {
        this.nodeCount = i;
    }

    public final List<CFGNode<?>> getNodes() {
        List list = this.nodes;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("nodes");
        return null;
    }

    public final CFGNode<?> getEnterNode() {
        CFGNode<?> cFGNode = this.enterNode;
        if (cFGNode != null) {
            return cFGNode;
        }
        Intrinsics.throwUninitializedPropertyAccessException("enterNode");
        return null;
    }

    @CfgInternals
    public final void setEnterNode(CFGNode<?> cFGNode) {
        Intrinsics.checkNotNullParameter(cFGNode, "<set-?>");
        this.enterNode = cFGNode;
    }

    public final CFGNode<?> getExitNode() {
        CFGNode<?> cFGNode = this.exitNode;
        if (cFGNode != null) {
            return cFGNode;
        }
        Intrinsics.throwUninitializedPropertyAccessException("exitNode");
        return null;
    }

    @CfgInternals
    public final void setExitNode(CFGNode<?> cFGNode) {
        Intrinsics.checkNotNullParameter(cFGNode, "<set-?>");
        this.exitNode = cFGNode;
    }

    public final boolean isSubGraph() {
        return !getEnterNode().getPreviousNodes().isEmpty();
    }

    public final List<ControlFlowGraph> getSubGraphs() {
        List<ControlFlowGraph> listEmptyList;
        List<CFGNode<?>> nodes = getNodes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = nodes.iterator();
        while (it.hasNext()) {
            CFGNode cFGNode = (CFGNode) it.next();
            CFGNodeWithSubgraphs cFGNodeWithSubgraphs = cFGNode instanceof CFGNodeWithSubgraphs ? (CFGNodeWithSubgraphs) cFGNode : null;
            if (cFGNodeWithSubgraphs == null || (listEmptyList = cFGNodeWithSubgraphs.getSubGraphs()) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        return arrayList;
    }

    @CfgInternals
    public final void complete() {
        this.nodes = ControlFlowGraphKt.orderNodes(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <D> void traverse(ControlFlowGraphVisitor<?, ? super D> visitor, D data) {
        List<ControlFlowGraph> subGraphs;
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        for (CFGNode<?> cFGNode : getNodes()) {
            cFGNode.accept(visitor, data);
            CFGNodeWithSubgraphs cFGNodeWithSubgraphs = cFGNode instanceof CFGNodeWithSubgraphs ? (CFGNodeWithSubgraphs) cFGNode : null;
            if (cFGNodeWithSubgraphs != null && (subGraphs = cFGNodeWithSubgraphs.getSubGraphs()) != null) {
                Iterator<T> it = subGraphs.iterator();
                while (it.hasNext()) {
                    ((ControlFlowGraph) it.next()).traverse(visitor, data);
                }
            }
        }
    }

    public final void traverse(ControlFlowGraphVisitorVoid visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        traverse(visitor, null);
    }
}
