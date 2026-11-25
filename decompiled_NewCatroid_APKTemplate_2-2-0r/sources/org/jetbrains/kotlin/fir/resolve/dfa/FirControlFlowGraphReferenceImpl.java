package org.jetbrains.kotlin.fir.resolve.dfa;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirControlFlowGraphReferenceImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J5\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0016J)\u0010\u0017\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00122\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00120\u00192\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/FirControlFlowGraphReferenceImpl;", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "controlFlowGraph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "dataFlowInfo", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowInfo;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowInfo;)V", "getControlFlowGraph", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "getDataFlowInfo", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowInfo;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirControlFlowGraphReferenceImpl extends FirControlFlowGraphReference {
    private final ControlFlowGraph controlFlowGraph;
    private final DataFlowInfo dataFlowInfo;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return null;
    }

    public /* synthetic */ FirControlFlowGraphReferenceImpl(ControlFlowGraph controlFlowGraph, DataFlowInfo dataFlowInfo, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(controlFlowGraph, (i & 2) != 0 ? null : dataFlowInfo);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    public final ControlFlowGraph getControlFlowGraph() {
        return this.controlFlowGraph;
    }

    public final DataFlowInfo getDataFlowInfo() {
        return this.dataFlowInfo;
    }

    public FirControlFlowGraphReferenceImpl(ControlFlowGraph controlFlowGraph, DataFlowInfo dataFlowInfo) {
        Intrinsics.checkNotNullParameter(controlFlowGraph, "controlFlowGraph");
        this.controlFlowGraph = controlFlowGraph;
        this.dataFlowInfo = dataFlowInfo;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirControlFlowGraphReference transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }
}
