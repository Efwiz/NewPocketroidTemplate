package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: RedundantNopsCleanupMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u001e\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/RedundantNopsCleanupMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "recordNopsRequiredForDebugger", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "requiredNops", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "recordNopsRequiredForTryCatchBlocks", "transform", "internalClassName", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantNopsCleanupMethodTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        new LabelNormalizationMethodTransformer().transform(internalClassName, methodNode);
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = hashSet;
        recordNopsRequiredForTryCatchBlocks(methodNode, hashSet2);
        recordNopsRequiredForDebugger(methodNode, hashSet2);
        AbstractInsnNode first = methodNode.instructions.getFirst();
        while (first != null) {
            if (first.getOpcode() == 0 && !hashSet.contains(first)) {
                AbstractInsnNode next = first.getNext();
                methodNode.instructions.remove(first);
                first = next;
            } else {
                first = first.getNext();
            }
        }
    }

    private final void recordNopsRequiredForDebugger(MethodNode methodNode, Set<AbstractInsnNode> requiredNops) {
        HashSet hashSet = new HashSet();
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            hashSet.add(localVariableNode.start);
            hashSet.add(localVariableNode.end);
        }
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (AbstractInsnNode abstractInsnNode : array) {
            AbstractInsnNode abstractInsnNode2 = abstractInsnNode;
            if (CollectionsKt.contains(hashSet, abstractInsnNode2) || (abstractInsnNode2 instanceof LineNumberNode)) {
                arrayList.add(abstractInsnNode);
            }
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size() - 2;
        if (size < 0) {
            return;
        }
        while (true) {
            AbstractInsnNode abstractInsnNode3 = (AbstractInsnNode) arrayList2.get(i);
            int i2 = i + 1;
            AbstractInsnNode abstractInsnNode4 = (AbstractInsnNode) arrayList2.get(i2);
            if (abstractInsnNode3 instanceof LineNumberNode) {
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(requiredNops, RedundantNopsCleanupMethodTransformerKt.getRequiredNopInRange(abstractInsnNode3, abstractInsnNode4));
            }
            if (i == size) {
                return;
            } else {
                i = i2;
            }
        }
    }

    private final void recordNopsRequiredForTryCatchBlocks(MethodNode methodNode, Set<AbstractInsnNode> requiredNops) {
        Iterator<TryCatchBlockNode> it = methodNode.tryCatchBlocks.iterator();
        while (it.hasNext()) {
            LabelNode start = it.next().start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            AbstractInsnNode next = start.getNext();
            while (next != null && !UtilKt.isMeaningful(next)) {
                next = next.getNext();
            }
            if (next != null && next.getOpcode() == 0) {
                requiredNops.add(next);
            }
        }
    }
}
