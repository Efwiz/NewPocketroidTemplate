package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn;
import org.jetbrains.kotlin.utils.SmartSet;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: AnalyzeTryCatchBlocks.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u001c\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001aD\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\nH\u0002\u001a\u001c\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a$\u0010\f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u000eH\u0002\u001a\u0014\u0010\u000f\u001a\u00020\u0010*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\f\u0010\u0013\u001a\u00020\u0014*\u00020\u0012H\u0002¨\u0006\u0015"}, d2 = {"collectDecompiledTryDescriptors", "", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/DecompiledTryDescriptor;", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "insertSaveRestoreStackMarkers", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "decompiledTryDescriptorForStart", "newTryStartLabels", "", "insertTryCatchBlocksMarkers", "transformTryCatchBlocks", "", "Ljava/util/HashMap;", "debugString", "", "tcb", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "isDefaultHandlerNode", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnalyzeTryCatchBlocksKt {
    private static final boolean isDefaultHandlerNode(TryCatchBlockNode tryCatchBlockNode) {
        return Intrinsics.areEqual(tryCatchBlockNode.start, tryCatchBlockNode.handler);
    }

    private static final String debugString(MethodNode methodNode, TryCatchBlockNode tryCatchBlockNode) {
        return "TCB<" + methodNode.instructions.indexOf(tryCatchBlockNode.start) + ", " + methodNode.instructions.indexOf(tryCatchBlockNode.end) + ", " + methodNode.instructions.indexOf(tryCatchBlockNode.handler) + '>';
    }

    public static final Map<AbstractInsnNode, AbstractInsnNode> insertTryCatchBlocksMarkers(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        if (methodNode.tryCatchBlocks.isEmpty()) {
            return MapsKt.emptyMap();
        }
        Map<LabelNode, DecompiledTryDescriptor> mapCollectDecompiledTryDescriptors = collectDecompiledTryDescriptors(methodNode);
        HashMap map = new HashMap();
        Map<AbstractInsnNode, AbstractInsnNode> mapInsertSaveRestoreStackMarkers = insertSaveRestoreStackMarkers(mapCollectDecompiledTryDescriptors, methodNode, map);
        transformTryCatchBlocks(methodNode, map);
        return mapInsertSaveRestoreStackMarkers;
    }

    private static final void transformTryCatchBlocks(MethodNode methodNode, HashMap<LabelNode, LabelNode> map) {
        List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
        Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
        List<TryCatchBlockNode> list = tryCatchBlocks;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TryCatchBlockNode tryCatchBlockNode : list) {
            LabelNode labelNode = map.get(tryCatchBlockNode.start);
            if (labelNode != null) {
                tryCatchBlockNode = new TryCatchBlockNode(labelNode, tryCatchBlockNode.end, tryCatchBlockNode.handler, tryCatchBlockNode.type);
            }
            arrayList.add(tryCatchBlockNode);
        }
        methodNode.tryCatchBlocks = arrayList;
    }

    private static final Map<AbstractInsnNode, AbstractInsnNode> insertSaveRestoreStackMarkers(Map<LabelNode, DecompiledTryDescriptor> map, MethodNode methodNode, Map<LabelNode, LabelNode> map2) {
        MethodInsnNode methodInsnNodeCreateInsnNode;
        HashMap map3 = new HashMap();
        HashMap map4 = new HashMap();
        HashSet hashSet = new HashSet();
        for (DecompiledTryDescriptor decompiledTryDescriptor : map.values()) {
            HashMap map5 = map4;
            if (map5.containsKey(decompiledTryDescriptor.getTryStartLabel())) {
                Object obj = map4.get(decompiledTryDescriptor.getTryStartLabel());
                Intrinsics.checkNotNull(obj);
                methodInsnNodeCreateInsnNode = (AbstractInsnNode) obj;
            } else {
                AbstractInsnNode next = decompiledTryDescriptor.getTryStartLabel().getNext();
                while (next != null && !UtilKt.hasOpcode(next)) {
                    next = next.getNext();
                }
                Intrinsics.checkNotNull(next);
                next.getOpcode();
                LabelNode labelNode = new LabelNode(new Label());
                map2.put(decompiledTryDescriptor.getTryStartLabel(), labelNode);
                methodInsnNodeCreateInsnNode = PseudoInsn.SAVE_STACK_BEFORE_TRY.createInsnNode();
                MethodInsnNode methodInsnNodeCreateInsnNode2 = PseudoInsn.RESTORE_STACK_IN_TRY_CATCH.createInsnNode();
                map5.put(decompiledTryDescriptor.getTryStartLabel(), methodInsnNodeCreateInsnNode);
                map3.put(methodInsnNodeCreateInsnNode2, methodInsnNodeCreateInsnNode);
                methodNode.instructions.insertBefore(next, methodInsnNodeCreateInsnNode);
                methodNode.instructions.insertBefore(next, labelNode);
                methodNode.instructions.insert(next, methodInsnNodeCreateInsnNode2);
            }
            for (LabelNode labelNode2 : decompiledTryDescriptor.getHandlerStartLabels()) {
                if (!hashSet.contains(labelNode2)) {
                    hashSet.add(labelNode2);
                    Intrinsics.checkNotNull(labelNode2);
                    AbstractInsnNode next2 = labelNode2.getNext();
                    while (next2 != null && !UtilKt.hasOpcode(next2)) {
                        next2 = next2.getNext();
                    }
                    Intrinsics.checkNotNull(next2);
                    next2.getOpcode();
                    MethodInsnNode methodInsnNodeCreateInsnNode3 = PseudoInsn.RESTORE_STACK_IN_TRY_CATCH.createInsnNode();
                    map3.put(methodInsnNodeCreateInsnNode3, methodInsnNodeCreateInsnNode);
                    methodNode.instructions.insert(next2, methodInsnNodeCreateInsnNode3);
                }
            }
        }
        return map3;
    }

    private static final Map<LabelNode, DecompiledTryDescriptor> collectDecompiledTryDescriptors(MethodNode methodNode) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
        Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
        SmartSet smartSetCreate = SmartSet.INSTANCE.create();
        for (TryCatchBlockNode tryCatchBlockNode : tryCatchBlocks) {
            Intrinsics.checkNotNull(tryCatchBlockNode);
            LabelNode labelNode = isDefaultHandlerNode(tryCatchBlockNode) ? tryCatchBlockNode.handler : null;
            if (labelNode != null) {
                smartSetCreate.add(labelNode);
            }
        }
        SmartSet smartSet = smartSetCreate;
        for (TryCatchBlockNode tryCatchBlockNode2 : methodNode.tryCatchBlocks) {
            Intrinsics.checkNotNull(tryCatchBlockNode2);
            if (isDefaultHandlerNode(tryCatchBlockNode2)) {
                map2.containsKey(tryCatchBlockNode2.start);
            }
            LabelNode handler = tryCatchBlockNode2.handler;
            Intrinsics.checkNotNullExpressionValue(handler, "handler");
            Object obj = map2.get(handler);
            if (obj == null) {
                LabelNode start = tryCatchBlockNode2.start;
                Intrinsics.checkNotNullExpressionValue(start, "start");
                Object decompiledTryDescriptor = map.get(start);
                if (decompiledTryDescriptor == null) {
                    LabelNode start2 = tryCatchBlockNode2.start;
                    Intrinsics.checkNotNullExpressionValue(start2, "start");
                    decompiledTryDescriptor = new DecompiledTryDescriptor(start2);
                    map.put(start, decompiledTryDescriptor);
                }
                obj = (DecompiledTryDescriptor) decompiledTryDescriptor;
                map2.put(handler, obj);
            }
            DecompiledTryDescriptor decompiledTryDescriptor2 = (DecompiledTryDescriptor) obj;
            if (isDefaultHandlerNode(tryCatchBlockNode2)) {
                decompiledTryDescriptor2.getDefaultHandlerTcb();
                decompiledTryDescriptor2.setDefaultHandlerTcb(tryCatchBlockNode2);
            }
            if (!smartSet.contains(tryCatchBlockNode2.handler)) {
                decompiledTryDescriptor2.getHandlerStartLabels().add(tryCatchBlockNode2.handler);
            }
        }
        return map;
    }
}
