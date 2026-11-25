package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0017\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u0082\u0001\u0004\r\u000e\u000f\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithCfgOwner;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithSubgraphs;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;I)V", "subGraphs", "", "getSubGraphs", "()Ljava/util/List;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterValueParameterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalClassExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalFunctionDeclarationNode;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CFGNodeWithCfgOwner<E extends FirControlFlowGraphOwner> extends CFGNodeWithSubgraphs<E> {
    public /* synthetic */ CFGNodeWithCfgOwner(ControlFlowGraph controlFlowGraph, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(controlFlowGraph, i);
    }

    private CFGNodeWithCfgOwner(ControlFlowGraph controlFlowGraph, int i) {
        super(controlFlowGraph, i, null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeWithSubgraphs
    public final List<ControlFlowGraph> getSubGraphs() {
        FirControlFlowGraphReference controlFlowGraphReference = getFir().getControlFlowGraphReference();
        return CollectionsKt.listOfNotNull(controlFlowGraphReference != null ? FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference) : null);
    }
}
