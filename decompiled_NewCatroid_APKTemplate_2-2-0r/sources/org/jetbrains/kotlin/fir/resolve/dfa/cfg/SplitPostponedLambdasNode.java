package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ5\u0010\u0012\u001a\u0002H\u0013\"\u0004\b\u0000\u0010\u0013\"\u0004\b\u0001\u0010\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00140\u00162\u0006\u0010\u0017\u001a\u0002H\u0014H\u0016¢\u0006\u0002\u0010\u0018R\u0014\u0010\u0005\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SplitPostponedLambdasNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithSubgraphs;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "fir", "lambdas", "", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Ljava/util/List;I)V", "getFir", "()Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "getLambdas", "()Ljava/util/List;", "subGraphs", "getSubGraphs", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SplitPostponedLambdasNode extends CFGNodeWithSubgraphs<FirStatement> {
    private final FirStatement fir;
    private final List<FirAnonymousFunction> lambdas;

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public FirStatement getFir() {
        return this.fir;
    }

    public final List<FirAnonymousFunction> getLambdas() {
        return this.lambdas;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SplitPostponedLambdasNode(ControlFlowGraph owner, FirStatement fir, List<? extends FirAnonymousFunction> lambdas, int i) {
        super(owner, i, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(fir, "fir");
        Intrinsics.checkNotNullParameter(lambdas, "lambdas");
        this.fir = fir;
        this.lambdas = lambdas;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeWithSubgraphs
    public List<ControlFlowGraph> getSubGraphs() {
        List<FirAnonymousFunction> list = this.lambdas;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            FirControlFlowGraphReference controlFlowGraphReference = ((FirAnonymousFunction) it.next()).getControlFlowGraphReference();
            ControlFlowGraph controlFlowGraph = controlFlowGraphReference != null ? FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference) : null;
            if (controlFlowGraph != null) {
                arrayList.add(controlFlowGraph);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public <R, D> R accept(ControlFlowGraphVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSplitPostponedLambdasNode(this, data);
    }
}
