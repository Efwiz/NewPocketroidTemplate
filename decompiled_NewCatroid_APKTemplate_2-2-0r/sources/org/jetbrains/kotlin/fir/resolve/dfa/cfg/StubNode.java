package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.dfa.FlowPath;
import org.jetbrains.kotlin.fir.resolve.dfa.PersistentFlow;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J5\u0010\u0017\u001a\u0002H\u0018\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190\u001b2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010\u001dJ\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0011H\u0017J\u0012\u0010!\u001a\u0004\u0018\u00010\u00112\u0006\u0010 \u001a\u00020\nH\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118V@WX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StubNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FirStub;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;I)V", "alternateFlowPaths", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath;", "getAlternateFlowPaths", "()Ljava/util/Set;", "fir", "getFir", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FirStub;", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;", "flow", "getFlow", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;", "setFlow", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "addAlternateFlow", "", "path", "getAlternateFlow", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StubNode extends CFGNode<FirStub> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StubNode(ControlFlowGraph owner, int i) {
        super(owner, i, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        setDead(true);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public FirStub getFir() {
        return FirStub.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public PersistentFlow getFlow() {
        return CFGNodeKt.getFirstPreviousNode(this).getFlow();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    @CfgInternals
    public void setFlow(PersistentFlow persistentFlow) {
        Intrinsics.checkNotNullParameter(persistentFlow, "<anonymous parameter 0>");
        throw new IllegalStateException("can't set flow for stub node");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public Set<FlowPath> getAlternateFlowPaths() {
        return CFGNodeKt.getFirstPreviousNode(this).getAlternateFlowPaths();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public PersistentFlow getAlternateFlow(FlowPath path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return CFGNodeKt.getFirstPreviousNode(this).getAlternateFlow(path);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    @CfgInternals
    public void addAlternateFlow(FlowPath path, PersistentFlow flow) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(flow, "flow");
        CFGNodeKt.getFirstPreviousNode(this).addAlternateFlow(path, flow);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public <R, D> R accept(ControlFlowGraphVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitStubNode(this, data);
    }
}
