package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;

/* JADX INFO: compiled from: TryBlockClustering.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/Interval;", "", "endLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getEndLabel", "()Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "startLabel", "getStartLabel", "isEmpty", "", "verify", "", "processor", "Lorg/jetbrains/kotlin/codegen/inline/CoveringTryCatchNodeProcessor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Interval {
    LabelNode getEndLabel();

    LabelNode getStartLabel();

    default boolean isEmpty() {
        return Intrinsics.areEqual(getStartLabel(), getEndLabel());
    }

    default void verify(CoveringTryCatchNodeProcessor processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        processor.instructionIndex(getStartLabel());
        processor.instructionIndex(getEndLabel());
    }
}
