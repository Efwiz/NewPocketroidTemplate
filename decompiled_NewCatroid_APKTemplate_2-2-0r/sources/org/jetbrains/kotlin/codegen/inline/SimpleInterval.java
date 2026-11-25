package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;

/* JADX INFO: compiled from: TryBlockClustering.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SimpleInterval;", "Lorg/jetbrains/kotlin/codegen/inline/Interval;", "startLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "endLabel", "(Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;)V", "getEndLabel", "()Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getStartLabel", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleInterval implements Interval {
    private final LabelNode endLabel;
    private final LabelNode startLabel;

    public SimpleInterval(LabelNode startLabel, LabelNode endLabel) {
        Intrinsics.checkNotNullParameter(startLabel, "startLabel");
        Intrinsics.checkNotNullParameter(endLabel, "endLabel");
        this.startLabel = startLabel;
        this.endLabel = endLabel;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.Interval
    public LabelNode getEndLabel() {
        return this.endLabel;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.Interval
    public LabelNode getStartLabel() {
        return this.startLabel;
    }
}
