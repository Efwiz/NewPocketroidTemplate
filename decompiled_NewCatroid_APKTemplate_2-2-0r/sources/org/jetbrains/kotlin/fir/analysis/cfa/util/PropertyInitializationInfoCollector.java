package org.jetbrains.kotlin.fir.analysis.cfa.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeWithSubgraphs;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ClassExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.Edge;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopBlockEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopConditionEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.NormalPath;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.PropertyInitializerEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.PropertyInitializerExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableAssignmentNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableDeclarationNode;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: PropertyInitializationInfoCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 )2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001)B-\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJP\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000e2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000eH\u0016J8\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000eH\u0016J8\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000e2\u0006\u0010\u0019\u001a\u00020\u001c2\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000eH\u0016JD\u0010\u001d\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\b\u0012\u0004\u0012\u00020\u0002`\u001e2\u0006\u0010\u0019\u001a\u00020\u001f2\u001c\u0010\u0017\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\b\u0012\u0004\u0012\u00020\u0002`\u001eH\u0016J\u001c\u0010 \u001a\u00020!2\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\u0006\u0010#\u001a\u00020$H\u0016J8\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000e2\u0006\u0010\u0019\u001a\u00020&2\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000eH\u0016J8\u0010'\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000e2\u0006\u0010\u0019\u001a\u00020(2\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000eH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00020\fj\u0002`\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoCollector;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowGraphVisitor;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfo;", "localProperties", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "expectedReceiver", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "declaredVariableCollector", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/DeclaredVariableCollector;", "(Ljava/util/Set;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/analysis/cfa/util/DeclaredVariableCollector;)V", "emptyInfo", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwarePropertyInitializationInfo;", "getEmptyInfo", "()Lkotlinx/collections/immutable/PersistentMap;", "visitEdge", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "to", "metadata", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;", "data", "visitLoopEnterNode", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopEnterNode;", "visitLoopExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;", "visitPropertyInitializerExitNode", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowInfo;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerExitNode;", "visitSubGraph", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithSubgraphs;", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "visitVariableAssignmentNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "visitVariableDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyInitializationInfoCollector extends PathAwareControlFlowGraphVisitor<PropertyInitializationInfo> {
    private static final PersistentMap<EdgeLabel, PropertyInitializationInfo> EMPTY_INFO = ExtensionsKt.persistentMapOf(TuplesKt.to(NormalPath.INSTANCE, PropertyInitializationInfo.INSTANCE.getEMPTY()));
    private final DeclaredVariableCollector declaredVariableCollector;
    private final FirBasedSymbol<?> expectedReceiver;
    private final Set<FirPropertySymbol> localProperties;

    public /* synthetic */ PropertyInitializationInfoCollector(Set set, FirBasedSymbol firBasedSymbol, DeclaredVariableCollector declaredVariableCollector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, (i & 2) != 0 ? null : firBasedSymbol, (i & 4) != 0 ? new DeclaredVariableCollector() : declaredVariableCollector);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PropertyInitializationInfoCollector(Set<? extends FirPropertySymbol> localProperties, FirBasedSymbol<?> firBasedSymbol, DeclaredVariableCollector declaredVariableCollector) {
        Intrinsics.checkNotNullParameter(localProperties, "localProperties");
        Intrinsics.checkNotNullParameter(declaredVariableCollector, "declaredVariableCollector");
        this.localProperties = localProperties;
        this.expectedReceiver = firBasedSymbol;
        this.declaredVariableCollector = declaredVariableCollector;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor
    public PersistentMap<EdgeLabel, PropertyInitializationInfo> getEmptyInfo() {
        return EMPTY_INFO;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor
    public boolean visitSubGraph(CFGNodeWithSubgraphs<?> node, ControlFlowGraph graph) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(graph, "graph");
        return (this.expectedReceiver != null && (node instanceof ClassExitNode) && node == node.getOwner().getExitNode()) ? false : true;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public PersistentMap<EdgeLabel, PropertyInitializationInfo> visitVariableAssignmentNode(VariableAssignmentNode node, PersistentMap<EdgeLabel, PropertyInitializationInfo> data) {
        FirReference calleeReference;
        FirPropertySymbol resolvedPropertySymbol$default;
        FirThisReference calleeReference2;
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(data, "data");
        PersistentMap<EdgeLabel, PropertyInitializationInfo> persistentMapVisitNode = visitNode((CFGNode<?>) node, (PersistentMap) data);
        FirExpression firExpressionUnwrapSmartcastExpression = FirExpressionUtilKt.unwrapSmartcastExpression(FirExpressionUtilKt.getDispatchReceiver(node.getFir()));
        FirThisReceiverExpression firThisReceiverExpression = firExpressionUnwrapSmartcastExpression instanceof FirThisReceiverExpression ? (FirThisReceiverExpression) firExpressionUnwrapSmartcastExpression : null;
        return (Intrinsics.areEqual((firThisReceiverExpression == null || (calleeReference2 = firThisReceiverExpression.getCalleeReference()) == null) ? null : calleeReference2.getBoundSymbol(), this.expectedReceiver) && (calleeReference = FirExpressionUtilKt.getCalleeReference(node.getFir())) != null && (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) != null && this.localProperties.contains(resolvedPropertySymbol$default)) ? PropertyInitializationInfoCollectorKt.addRange(persistentMapVisitNode, resolvedPropertySymbol$default, EventOccurrencesRange.EXACTLY_ONCE) : persistentMapVisitNode;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public PersistentMap<EdgeLabel, PropertyInitializationInfo> visitVariableDeclarationNode(VariableDeclarationNode node, PersistentMap<EdgeLabel, PropertyInitializationInfo> data) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(data, "data");
        PersistentMap<EdgeLabel, PropertyInitializationInfo> persistentMapVisitNode = visitNode((CFGNode<?>) node, (PersistentMap) data);
        if (this.expectedReceiver != null) {
            return persistentMapVisitNode;
        }
        return (node.getFir().getInitializer() == null && node.getFir().getDelegate() == null) ? PropertyInitializationInfoCollectorKt.overwriteRange(persistentMapVisitNode, node.getFir().getSymbol(), EventOccurrencesRange.ZERO) : PropertyInitializationInfoCollectorKt.overwriteRange(persistentMapVisitNode, node.getFir().getSymbol(), EventOccurrencesRange.EXACTLY_ONCE);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public PersistentMap<EdgeLabel, PropertyInitializationInfo> visitPropertyInitializerExitNode(PropertyInitializerExitNode node, PersistentMap<EdgeLabel, PropertyInitializationInfo> data) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(data, "data");
        PropertyInitializerExitNode propertyInitializerExitNode = node;
        PersistentMap<EdgeLabel, PropertyInitializationInfo> persistentMapVisitNode = visitNode((CFGNode<?>) propertyInitializerExitNode, (PersistentMap) data);
        return CFGNodeKt.getFirstPreviousNode(propertyInitializerExitNode) instanceof PropertyInitializerEnterNode ? persistentMapVisitNode : PropertyInitializationInfoCollectorKt.overwriteRange(persistentMapVisitNode, node.getFir().getSymbol(), EventOccurrencesRange.EXACTLY_ONCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.PathAwareControlFlowGraphVisitor
    public PersistentMap<EdgeLabel, PropertyInitializationInfo> visitEdge(CFGNode<?> from, CFGNode<?> to, Edge metadata, PersistentMap<EdgeLabel, PropertyInitializationInfo> data) {
        Set set;
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(data, "data");
        PersistentMap<EdgeLabel, PropertyInitializationInfo> persistentMapVisitEdge = super.visitEdge(from, to, metadata, data);
        if (!metadata.getKind().getIsBack()) {
            return persistentMapVisitEdge;
        }
        if (to instanceof LoopEnterNode) {
            set = (Set) this.declaredVariableCollector.getDeclaredVariablesPerElement().get(((LoopEnterNode) to).getFir());
        } else if (to instanceof LoopBlockEnterNode) {
            set = (Set) this.declaredVariableCollector.getDeclaredVariablesPerElement().get(((LoopBlockEnterNode) to).getFir());
        } else {
            if (!(to instanceof LoopConditionEnterNode)) {
                return persistentMapVisitEdge;
            }
            set = this.declaredVariableCollector.getDeclaredVariablesPerElement().get(((LoopConditionEnterNode) to).getLoop());
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            data = PropertyInitializationInfoCollectorKt.removeRange(data, (FirPropertySymbol) it.next());
        }
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public PersistentMap<EdgeLabel, PropertyInitializationInfo> visitLoopEnterNode(LoopEnterNode node, PersistentMap<EdgeLabel, PropertyInitializationInfo> data) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(data, "data");
        this.declaredVariableCollector.enterCapturingStatement(node.getFir());
        return visitNode((CFGNode<?>) node, (PersistentMap) data);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public PersistentMap<EdgeLabel, PropertyInitializationInfo> visitLoopExitNode(LoopExitNode node, PersistentMap<EdgeLabel, PropertyInitializationInfo> data) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(data, "data");
        this.declaredVariableCollector.exitCapturingStatement(node.getFir());
        return visitNode((CFGNode<?>) node, (PersistentMap) data);
    }
}
