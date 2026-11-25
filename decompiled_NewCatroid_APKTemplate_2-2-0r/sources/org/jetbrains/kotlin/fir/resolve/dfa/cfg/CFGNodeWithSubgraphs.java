package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.FirElement;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0017\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u0082\u0001\u0005\r\u000e\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithSubgraphs;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;I)V", "subGraphs", "", "getSubGraphs", "()Ljava/util/List;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousFunctionExpressionNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithCfgOwner;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SplitPostponedLambdasNode;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CFGNodeWithSubgraphs<E extends FirElement> extends CFGNode<E> {
    public /* synthetic */ CFGNodeWithSubgraphs(ControlFlowGraph controlFlowGraph, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(controlFlowGraph, i);
    }

    public abstract List<ControlFlowGraph> getSubGraphs();

    private CFGNodeWithSubgraphs(ControlFlowGraph controlFlowGraph, int i) {
        super(controlFlowGraph, i, null);
    }
}
