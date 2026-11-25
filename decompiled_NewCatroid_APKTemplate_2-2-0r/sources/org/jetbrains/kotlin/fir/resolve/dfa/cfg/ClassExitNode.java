package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ5\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u0017\"\u0004\b\u0001\u0010\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u001a2\u0006\u0010\u001b\u001a\u0002H\u0018H\u0016¢\u0006\u0002\u0010\u001cR\u0014\u0010\u0006\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR0\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u00102\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0010@WX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithSubgraphs;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GraphExitNodeMarker;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "fir", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;Lorg/jetbrains/kotlin/fir/declarations/FirClass;I)V", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "isUnion", "", "()Z", "<set-?>", "", "subGraphs", "getSubGraphs", "()Ljava/util/List;", "setSubGraphs", "(Ljava/util/List;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassExitNode extends CFGNodeWithSubgraphs<FirClass> implements GraphExitNodeMarker {
    private final FirClass fir;
    public List<ControlFlowGraph> subGraphs;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassExitNode(ControlFlowGraph owner, FirClass fir, int i) {
        super(owner, i, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(fir, "fir");
        this.fir = fir;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public FirClass getFir() {
        return this.fir;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public boolean isUnion() {
        return (getFir() instanceof FirAnonymousObject) && getFir().getClassKind() != ClassKind.ENUM_ENTRY;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeWithSubgraphs
    public List<ControlFlowGraph> getSubGraphs() {
        List<ControlFlowGraph> list = this.subGraphs;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("subGraphs");
        return null;
    }

    @CfgInternals
    public void setSubGraphs(List<ControlFlowGraph> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.subGraphs = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public <R, D> R accept(ControlFlowGraphVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitClassExitNode(this, data);
    }
}
