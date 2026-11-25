package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;

/* JADX INFO: compiled from: CoveringTryCatchNodeProcessor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00000\u000f2\u0006\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/LocalVarNodeWrapper;", "Lorg/jetbrains/kotlin/codegen/inline/Interval;", "Lorg/jetbrains/kotlin/codegen/inline/SplittableInterval;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;)V", "endLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getEndLabel", "()Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getNode", "()Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;", "startLabel", "getStartLabel", "split", "Lorg/jetbrains/kotlin/codegen/inline/SplitPair;", "splitBy", "keepStart", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalVarNodeWrapper implements Interval, SplittableInterval<LocalVarNodeWrapper> {
    private final LocalVariableNode node;

    public LocalVarNodeWrapper(LocalVariableNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        this.node = node;
    }

    public final LocalVariableNode getNode() {
        return this.node;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.Interval
    public LabelNode getStartLabel() {
        LabelNode start = this.node.start;
        Intrinsics.checkNotNullExpressionValue(start, "start");
        return start;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.Interval
    public LabelNode getEndLabel() {
        LabelNode end = this.node.end;
        Intrinsics.checkNotNullExpressionValue(end, "end");
        return end;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SplittableInterval
    public SplitPair<LocalVarNodeWrapper> split(Interval splitBy, boolean keepStart) {
        Pair pair;
        Intrinsics.checkNotNullParameter(splitBy, "splitBy");
        if (keepStart) {
            LabelNode endLabel = getEndLabel();
            this.node.end = splitBy.getStartLabel();
            pair = new Pair(splitBy.getEndLabel(), endLabel);
        } else {
            LabelNode startLabel = getStartLabel();
            this.node.start = splitBy.getEndLabel();
            pair = new Pair(startLabel, splitBy.getStartLabel());
        }
        return new SplitPair<>(this, new LocalVarNodeWrapper(new LocalVariableNode(this.node.f1014name, this.node.desc, this.node.signature, (LabelNode) pair.getFirst(), (LabelNode) pair.getSecond(), this.node.index)));
    }
}
