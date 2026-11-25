package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ5\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013R\u0014\u0010\n\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FakeExpressionEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FirStub;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GraphEnterNodeMarker;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GraphExitNodeMarker;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;I)V", "fir", "getFir", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FirStub;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeExpressionEnterNode extends CFGNode<FirStub> implements GraphEnterNodeMarker, GraphExitNodeMarker {
    private final FirStub fir;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FakeExpressionEnterNode(ControlFlowGraph owner, int i) {
        super(owner, i, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        setDead(true);
        this.fir = FirStub.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public FirStub getFir() {
        return this.fir;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public <R, D> R accept(ControlFlowGraphVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        throw new IllegalStateException("fake expressions should not appear in graphs");
    }
}
