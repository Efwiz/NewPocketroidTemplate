package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;

/* JADX INFO: compiled from: CoroutineTransformerMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0003H\u0086\u0002J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012H\u0002R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u001a\u0010\r\u001a\u00020\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPoint;", "", "suspensionCallBegin", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "suspensionCallEnd", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)V", "stateLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getStateLabel", "()Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getSuspensionCallBegin", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getSuspensionCallEnd", "tryCatchBlocksContinuationLabel", "getTryCatchBlocksContinuationLabel", "setTryCatchBlocksContinuationLabel", "(Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;)V", "unboxInlineClassInstructions", "", "getUnboxInlineClassInstructions", "()Ljava/util/List;", "contains", "", "insn", "findUnboxInlineClassInstructions", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspensionPoint {
    private final LabelNode stateLabel;
    private final AbstractInsnNode suspensionCallBegin;
    private final AbstractInsnNode suspensionCallEnd;
    public LabelNode tryCatchBlocksContinuationLabel;
    private final List<AbstractInsnNode> unboxInlineClassInstructions;

    public SuspensionPoint(AbstractInsnNode suspensionCallBegin, AbstractInsnNode suspensionCallEnd) {
        Intrinsics.checkNotNullParameter(suspensionCallBegin, "suspensionCallBegin");
        Intrinsics.checkNotNullParameter(suspensionCallEnd, "suspensionCallEnd");
        this.suspensionCallBegin = suspensionCallBegin;
        this.suspensionCallEnd = suspensionCallEnd;
        this.stateLabel = CodegenUtilKt.linkWithLabel(new LabelNode());
        this.unboxInlineClassInstructions = findUnboxInlineClassInstructions();
    }

    public final AbstractInsnNode getSuspensionCallBegin() {
        return this.suspensionCallBegin;
    }

    public final AbstractInsnNode getSuspensionCallEnd() {
        return this.suspensionCallEnd;
    }

    public final LabelNode getTryCatchBlocksContinuationLabel() {
        LabelNode labelNode = this.tryCatchBlocksContinuationLabel;
        if (labelNode != null) {
            return labelNode;
        }
        Intrinsics.throwUninitializedPropertyAccessException("tryCatchBlocksContinuationLabel");
        return null;
    }

    public final void setTryCatchBlocksContinuationLabel(LabelNode labelNode) {
        Intrinsics.checkNotNullParameter(labelNode, "<set-?>");
        this.tryCatchBlocksContinuationLabel = labelNode;
    }

    public final LabelNode getStateLabel() {
        return this.stateLabel;
    }

    public final List<AbstractInsnNode> getUnboxInlineClassInstructions() {
        return this.unboxInlineClassInstructions;
    }

    private final List<AbstractInsnNode> findUnboxInlineClassInstructions() {
        AbstractInsnNode next = this.suspensionCallEnd.getNext();
        AbstractInsnNode next2 = next != null ? next.getNext() : null;
        if (next2 != null && InlineCodegenUtilsKt.isBeforeUnboxInlineClassMarker(next2)) {
            AbstractInsnNode next3 = next2.getNext();
            while (next3 != null && !InlineCodegenUtilsKt.isAfterUnboxInlineClassMarker(next3)) {
                next3 = next3.getNext();
            }
            if (next3 == null) {
                throw new IllegalStateException("Before unbox inline class marker without after unbox inline class marker".toString());
            }
            AbstractInsnNode next4 = next2.getNext();
            Intrinsics.checkNotNullExpressionValue(next4, "getNext(...)");
            return SequencesKt.toList(new InsnSequence(next4, next3.getPrevious().getPrevious()));
        }
        return CollectionsKt.emptyList();
    }

    public final boolean contains(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Iterator<AbstractInsnNode> it = new InsnSequence(this.suspensionCallBegin, this.suspensionCallEnd.getNext()).iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(it.next(), insn)) {
                return true;
            }
        }
        return false;
    }
}
