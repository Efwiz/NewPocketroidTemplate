package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;

/* JADX INFO: compiled from: FirControlFlowGraphReferenceImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"controlFlowGraph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraph", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "dataFlowInfo", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowInfo;", "getDataFlowInfo", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowInfo;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirControlFlowGraphReferenceImplKt {
    public static final ControlFlowGraph getControlFlowGraph(FirControlFlowGraphReference firControlFlowGraphReference) {
        Intrinsics.checkNotNullParameter(firControlFlowGraphReference, "<this>");
        FirControlFlowGraphReferenceImpl firControlFlowGraphReferenceImpl = firControlFlowGraphReference instanceof FirControlFlowGraphReferenceImpl ? (FirControlFlowGraphReferenceImpl) firControlFlowGraphReference : null;
        if (firControlFlowGraphReferenceImpl != null) {
            return firControlFlowGraphReferenceImpl.getControlFlowGraph();
        }
        return null;
    }

    public static final DataFlowInfo getDataFlowInfo(FirControlFlowGraphReference firControlFlowGraphReference) {
        Intrinsics.checkNotNullParameter(firControlFlowGraphReference, "<this>");
        FirControlFlowGraphReferenceImpl firControlFlowGraphReferenceImpl = firControlFlowGraphReference instanceof FirControlFlowGraphReferenceImpl ? (FirControlFlowGraphReferenceImpl) firControlFlowGraphReference : null;
        if (firControlFlowGraphReferenceImpl != null) {
            return firControlFlowGraphReferenceImpl.getDataFlowInfo();
        }
        return null;
    }
}
