package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.MethodInlinerUtilKt;
import org.jetbrains.kotlin.codegen.optimization.common.InstructionLivenessAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: DeadCodeEliminationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/DeadCodeEliminationMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "shouldRemove", "", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "index", "", "liveness", "", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeadCodeEliminationMethodTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        boolean[] zArrAnalyze = new InstructionLivenessAnalyzer(methodNode, false, 2, null).analyze();
        ArrayList arrayList = new ArrayList();
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        int length = array.length;
        for (int i = 0; i < length; i++) {
            AbstractInsnNode abstractInsnNode = array[i];
            Intrinsics.checkNotNull(abstractInsnNode);
            if (shouldRemove(abstractInsnNode, i, zArrAnalyze)) {
                arrayList.add(abstractInsnNode);
            }
        }
        MethodInlinerUtilKt.remove(methodNode, arrayList);
        UtilKt.removeEmptyCatchBlocks(methodNode);
        UtilKt.removeUnusedLocalVariables(methodNode);
    }

    private final boolean shouldRemove(AbstractInsnNode insn, int index, boolean[] liveness) {
        if (!(insn instanceof LineNumberNode)) {
            return !liveness[index];
        }
        AbstractInsnNode next = insn;
        boolean z = false;
        while (true) {
            next = next.getNext();
            if (next == null) {
                return true;
            }
            index++;
            if (!(next instanceof LabelNode)) {
                if (next instanceof LineNumberNode) {
                    if (((LineNumberNode) next).line != ((LineNumberNode) insn).line) {
                        return z;
                    }
                } else {
                    if (liveness[index]) {
                        return false;
                    }
                    z = true;
                }
            }
        }
    }
}
