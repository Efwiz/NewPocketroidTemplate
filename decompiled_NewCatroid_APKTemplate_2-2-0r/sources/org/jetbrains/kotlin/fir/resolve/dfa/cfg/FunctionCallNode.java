package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0016R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0005\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "fir", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;I)V", "canThrow", "", "getCanThrow", "()Z", "getFir", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "isUnion", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionCallNode extends CFGNode<FirFunctionCall> {
    private final FirFunctionCall fir;

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public boolean getCanThrow() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public boolean isUnion() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public FirFunctionCall getFir() {
        return this.fir;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionCallNode(ControlFlowGraph owner, FirFunctionCall fir, int i) {
        super(owner, i, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(fir, "fir");
        this.fir = fir;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public <R, D> R accept(ControlFlowGraphVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFunctionCallNode(this, data);
    }
}
