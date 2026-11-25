package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: RedundantGotoMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nH\u0002J$\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00040\u0007H\u0002J\u0018\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/RedundantGotoMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "getLastTargetJumpInsn", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "jumpInsn", "labelsToReplace", "", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "alreadyVisited", "", "rewriteLabelIfNeeded", "", "transform", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantGotoMethodTransformer extends MethodTransformer {
    /* JADX WARN: Code duplicated, block: B:21:0x0074  */
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNull(array);
        ArraysKt.reverse(array);
        ArrayList<AbstractInsnNode> arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        HashMap map = new HashMap();
        Intrinsics.checkNotNull(array);
        JumpInsnNode jumpInsnNode = null;
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode instanceof LabelNode) {
                hashSet.add(abstractInsnNode);
                if (jumpInsnNode != null) {
                    map.put(abstractInsnNode, jumpInsnNode);
                }
            } else if (abstractInsnNode.getOpcode() == 167) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                jumpInsnNode = (JumpInsnNode) abstractInsnNode;
                if (hashSet.contains(jumpInsnNode.label)) {
                    arrayList.add(abstractInsnNode);
                } else {
                    hashSet.clear();
                }
            } else if (abstractInsnNode instanceof LineNumberNode) {
                hashSet.clear();
                jumpInsnNode = null;
            } else {
                Intrinsics.checkNotNull(abstractInsnNode);
                if (UtilKt.isMeaningful(abstractInsnNode) && abstractInsnNode.getOpcode() != 0) {
                    hashSet.clear();
                    jumpInsnNode = null;
                }
            }
        }
        HashMap map2 = map;
        if (!map2.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            for (AbstractInsnNode abstractInsnNode2 : array) {
                if (abstractInsnNode2 instanceof JumpInsnNode) {
                    arrayList2.add(abstractInsnNode2);
                }
            }
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                rewriteLabelIfNeeded((JumpInsnNode) it.next(), map2);
            }
        }
        for (AbstractInsnNode abstractInsnNode3 : arrayList) {
            methodNode.instructions.insertBefore(abstractInsnNode3, new InsnNode(0));
            methodNode.instructions.remove(abstractInsnNode3);
        }
    }

    private final void rewriteLabelIfNeeded(JumpInsnNode jumpInsn, Map<LabelNode, ? extends JumpInsnNode> labelsToReplace) {
        JumpInsnNode lastTargetJumpInsn = getLastTargetJumpInsn(jumpInsn, labelsToReplace, new ArrayList());
        if (lastTargetJumpInsn == null || Intrinsics.areEqual(lastTargetJumpInsn, jumpInsn)) {
            return;
        }
        jumpInsn.label = lastTargetJumpInsn.label;
    }

    private final JumpInsnNode getLastTargetJumpInsn(JumpInsnNode jumpInsn, Map<LabelNode, ? extends JumpInsnNode> labelsToReplace, List<JumpInsnNode> alreadyVisited) {
        JumpInsnNode jumpInsnNode = labelsToReplace.get(jumpInsn.label);
        if (jumpInsnNode == null) {
            return jumpInsn;
        }
        if (alreadyVisited.contains(jumpInsnNode)) {
            return null;
        }
        alreadyVisited.add(jumpInsnNode);
        return getLastTargetJumpInsn(jumpInsnNode, labelsToReplace, alreadyVisited);
    }
}
