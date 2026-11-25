package org.jetbrains.kotlin.fir.analysis.cfa.util;

import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: PropertyInitializationInfoCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B)\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\"\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u0002`\u001c2\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\fRA\u0010\n\u001a(\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\b\u0012\u0004\u0012\u00020\u000f`\u00100\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoData;", "", "properties", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "receiver", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "(Ljava/util/Set;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;)V", "data", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfo;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowInfo;", "getData", "()Ljava/util/Map;", "data$delegate", "Lkotlin/Lazy;", "getGraph", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "getProperties", "()Ljava/util/Set;", "getReceiver", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getValue", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwarePropertyInitializationInfo;", "node", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyInitializationInfoData {

    /* JADX INFO: renamed from: data$delegate, reason: from kotlin metadata */
    private final Lazy data;
    private final ControlFlowGraph graph;
    private final Set<FirPropertySymbol> properties;
    private final FirBasedSymbol<?> receiver;

    /* JADX WARN: Multi-variable type inference failed */
    public PropertyInitializationInfoData(Set<? extends FirPropertySymbol> properties, FirBasedSymbol<?> firBasedSymbol, ControlFlowGraph graph) {
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(graph, "graph");
        this.properties = properties;
        this.receiver = firBasedSymbol;
        this.graph = graph;
        this.data = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<CFGNode<?>, ? extends PersistentMap<EdgeLabel, ? extends PropertyInitializationInfo>>>() { // from class: org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfoData$data$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<CFGNode<?>, ? extends PersistentMap<EdgeLabel, ? extends PropertyInitializationInfo>> invoke() {
                return CfgTraverserKt.collectDataForNode(this.this$0.getGraph(), TraverseDirection.Forward, new PropertyInitializationInfoCollector(this.this$0.getProperties(), this.this$0.getReceiver(), null, 4, null));
            }
        });
    }

    public final Set<FirPropertySymbol> getProperties() {
        return this.properties;
    }

    public final FirBasedSymbol<?> getReceiver() {
        return this.receiver;
    }

    public final ControlFlowGraph getGraph() {
        return this.graph;
    }

    private final Map<CFGNode<?>, PersistentMap<EdgeLabel, PropertyInitializationInfo>> getData() {
        return (Map) this.data.getValue();
    }

    public final PersistentMap<EdgeLabel, PropertyInitializationInfo> getValue(CFGNode<?> node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return (PersistentMap) MapsKt.getValue(getData(), node);
    }
}
