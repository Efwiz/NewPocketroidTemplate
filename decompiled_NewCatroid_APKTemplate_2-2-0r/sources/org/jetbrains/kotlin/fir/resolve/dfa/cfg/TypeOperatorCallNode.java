package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00110\u00132\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u0015R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0005\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TypeOperatorCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "fir", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;I)V", "canThrow", "", "getCanThrow", "()Z", "getFir", "()Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeOperatorCallNode extends CFGNode<FirTypeOperatorCall> {
    private final FirTypeOperatorCall fir;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeOperatorCallNode(ControlFlowGraph owner, FirTypeOperatorCall fir, int i) {
        super(owner, i, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(fir, "fir");
        this.fir = fir;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public FirTypeOperatorCall getFir() {
        return this.fir;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public boolean getCanThrow() {
        return getFir().getOperation() == FirOperation.AS;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public <R, D> R accept(ControlFlowGraphVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeOperatorCallNode(this, data);
    }
}
