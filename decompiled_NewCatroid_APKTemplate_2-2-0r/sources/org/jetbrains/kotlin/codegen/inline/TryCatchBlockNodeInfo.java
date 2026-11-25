package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: TryBlockClustering.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00000\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0006H\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000bR\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/TryCatchBlockNodeInfo;", "Lorg/jetbrains/kotlin/codegen/inline/IntervalWithHandler;", "Lorg/jetbrains/kotlin/codegen/inline/SplittableInterval;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "onlyCopyNotProcess", "", "(Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;Z)V", "endLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getEndLabel", "()Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "handler", "getHandler", "getNode", "()Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "getOnlyCopyNotProcess", "()Z", "startLabel", "getStartLabel", ModuleXmlParser.TYPE, "", "getType", "()Ljava/lang/String;", "split", "Lorg/jetbrains/kotlin/codegen/inline/SplitPair;", "splitBy", "Lorg/jetbrains/kotlin/codegen/inline/Interval;", "keepStart", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TryCatchBlockNodeInfo implements IntervalWithHandler, SplittableInterval<TryCatchBlockNodeInfo> {
    private final TryCatchBlockNode node;
    private final boolean onlyCopyNotProcess;

    public TryCatchBlockNodeInfo(TryCatchBlockNode node, boolean z) {
        Intrinsics.checkNotNullParameter(node, "node");
        this.node = node;
        this.onlyCopyNotProcess = z;
    }

    public final TryCatchBlockNode getNode() {
        return this.node;
    }

    public final boolean getOnlyCopyNotProcess() {
        return this.onlyCopyNotProcess;
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

    @Override // org.jetbrains.kotlin.codegen.inline.IntervalWithHandler
    public LabelNode getHandler() {
        LabelNode handler = this.node.handler;
        Intrinsics.checkNotNullExpressionValue(handler, "handler");
        return handler;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.IntervalWithHandler
    public String getType() {
        return this.node.type;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SplittableInterval
    public SplitPair<TryCatchBlockNodeInfo> split(Interval splitBy, boolean keepStart) {
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
        return new SplitPair<>(this, new TryCatchBlockNodeInfo(new TryCatchBlockNode((LabelNode) pair.getFirst(), (LabelNode) pair.getSecond(), getHandler(), getType()), this.onlyCopyNotProcess));
    }
}
