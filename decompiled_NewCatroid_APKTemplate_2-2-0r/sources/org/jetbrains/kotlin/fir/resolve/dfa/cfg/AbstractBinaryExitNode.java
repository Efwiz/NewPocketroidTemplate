package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0015\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u00038F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0015\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AbstractBinaryExitNode;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/FirElement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;I)V", "leftOperandNode", "getLeftOperandNode", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "rightOperandNode", "getRightOperandNode", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractBinaryExitNode<T extends FirElement> extends CFGNode<T> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractBinaryExitNode(ControlFlowGraph owner, int i) {
        super(owner, i, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    public final CFGNode<?> getLeftOperandNode() {
        return getPreviousNodes().get(0);
    }

    public final CFGNode<?> getRightOperandNode() {
        return getPreviousNodes().get(1);
    }
}
