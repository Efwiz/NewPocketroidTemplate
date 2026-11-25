package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.declarations.FirFunction;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017R\u0014\u0010\u0007\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GraphExitNodeMarker;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "fir", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;I)V", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "", "getLabel", "()Ljava/lang/String;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionExitNode extends CFGNode<FirFunction> implements EdgeLabel, GraphExitNodeMarker {
    private final FirFunction fir;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionExitNode(ControlFlowGraph owner, FirFunction fir, int i) {
        super(owner, i, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(fir, "fir");
        this.fir = fir;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public FirFunction getFir() {
        return this.fir;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel
    public String getLabel() {
        return "return@" + getFir().getSymbol().getCallableId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode
    public <R, D> R accept(ControlFlowGraphVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFunctionExitNode(this, data);
    }
}
