package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;

/* JADX INFO: compiled from: TryBlockClustering.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0019\u001a\u00020\u001aH\u0096\u0001J\u0011\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0096\u0001R\u0012\u0010\u0007\u001a\u00020\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\nR\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/TryCatchBlockNodePosition;", "Lorg/jetbrains/kotlin/codegen/inline/IntervalWithHandler;", "nodeInfo", "Lorg/jetbrains/kotlin/codegen/inline/TryCatchBlockNodeInfo;", "position", "Lorg/jetbrains/kotlin/codegen/inline/TryCatchPosition;", "(Lorg/jetbrains/kotlin/codegen/inline/TryCatchBlockNodeInfo;Lorg/jetbrains/kotlin/codegen/inline/TryCatchPosition;)V", "endLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getEndLabel", "()Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "handler", "getHandler", "getNodeInfo", "()Lorg/jetbrains/kotlin/codegen/inline/TryCatchBlockNodeInfo;", "getPosition", "()Lorg/jetbrains/kotlin/codegen/inline/TryCatchPosition;", "setPosition", "(Lorg/jetbrains/kotlin/codegen/inline/TryCatchPosition;)V", "startLabel", "getStartLabel", ModuleXmlParser.TYPE, "", "getType", "()Ljava/lang/String;", "isEmpty", "", "verify", "", "processor", "Lorg/jetbrains/kotlin/codegen/inline/CoveringTryCatchNodeProcessor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TryCatchBlockNodePosition implements IntervalWithHandler {
    private final TryCatchBlockNodeInfo nodeInfo;
    private TryCatchPosition position;

    @Override // org.jetbrains.kotlin.codegen.inline.Interval
    public LabelNode getEndLabel() {
        return this.nodeInfo.getEndLabel();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.IntervalWithHandler
    public LabelNode getHandler() {
        return this.nodeInfo.getHandler();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.Interval
    public LabelNode getStartLabel() {
        return this.nodeInfo.getStartLabel();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.IntervalWithHandler
    public String getType() {
        return this.nodeInfo.getType();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.Interval
    public boolean isEmpty() {
        return this.nodeInfo.isEmpty();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.Interval
    public void verify(CoveringTryCatchNodeProcessor processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.nodeInfo.verify(processor);
    }

    public TryCatchBlockNodePosition(TryCatchBlockNodeInfo nodeInfo, TryCatchPosition position) {
        Intrinsics.checkNotNullParameter(nodeInfo, "nodeInfo");
        Intrinsics.checkNotNullParameter(position, "position");
        this.nodeInfo = nodeInfo;
        this.position = position;
    }

    public final TryCatchBlockNodeInfo getNodeInfo() {
        return this.nodeInfo;
    }

    public final TryCatchPosition getPosition() {
        return this.position;
    }

    public final void setPosition(TryCatchPosition tryCatchPosition) {
        Intrinsics.checkNotNullParameter(tryCatchPosition, "<set-?>");
        this.position = tryCatchPosition;
    }
}
