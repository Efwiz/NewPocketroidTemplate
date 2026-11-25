package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.utils.SmartIdentityTable;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FrameNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: LabelNormalizationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/LabelNormalizationMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "TransformerForMethod", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LabelNormalizationMethodTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        new TransformerForMethod(methodNode).transform();
    }

    /* JADX INFO: compiled from: LabelNormalizationMethodTransformer.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020!H\u0002J\u0012\u0010\"\u001a\u0004\u0018\u00010\u00152\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020!H\u0002J\u0012\u0010&\u001a\u0004\u0018\u00010\u00152\u0006\u0010#\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020!H\u0002J\u0006\u0010)\u001a\u00020!J\f\u0010*\u001a\u00020\u0015*\u00020\u0017H\u0002J\f\u0010*\u001a\u00020\u0015*\u00020\u001aH\u0002J\f\u0010*\u001a\u00020\u0015*\u00020\u001fH\u0002J\f\u0010*\u001a\u00020\u0015*\u00020$H\u0002J\f\u0010*\u001a\u00020\u0015*\u00020'H\u0002R\u0019\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/LabelNormalizationMethodTransformer$TransformerForMethod;", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getInstructions", "()Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "getMethodNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "newLabelNodes", "Lorg/jetbrains/kotlin/utils/SmartIdentityTable;", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getNewLabelNodes", "()Lorg/jetbrains/kotlin/utils/SmartIdentityTable;", "getNew", "oldLabelNode", "getNewOrOld", "rewriteFrameNode", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "oldFrameNode", "Lorg/jetbrains/org/objectweb/asm/tree/FrameNode;", "rewriteJumpInsn", "oldJumpNode", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "rewriteLabelInstructions", "", "rewriteLineNumberNode", "oldLineNode", "Lorg/jetbrains/org/objectweb/asm/tree/LineNumberNode;", "rewriteLocalVars", "", "rewriteLookupSwitchInsn", "oldSwitchNode", "Lorg/jetbrains/org/objectweb/asm/tree/LookupSwitchInsnNode;", "rewriteNonLabelInstructions", "rewriteTableSwitchInsn", "Lorg/jetbrains/org/objectweb/asm/tree/TableSwitchInsnNode;", "rewriteTryCatchBlocks", "transform", "rewriteLabels", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TransformerForMethod {
        private final InsnList instructions;
        private final MethodNode methodNode;
        private final SmartIdentityTable<LabelNode, LabelNode> newLabelNodes;

        public TransformerForMethod(MethodNode methodNode) {
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            this.methodNode = methodNode;
            this.instructions = methodNode.instructions;
            this.newLabelNodes = new SmartIdentityTable<>();
        }

        public final MethodNode getMethodNode() {
            return this.methodNode;
        }

        public final InsnList getInstructions() {
            return this.instructions;
        }

        public final SmartIdentityTable<LabelNode, LabelNode> getNewLabelNodes() {
            return this.newLabelNodes;
        }

        public final void transform() {
            if (rewriteLabelInstructions()) {
                rewriteNonLabelInstructions();
                rewriteTryCatchBlocks();
                rewriteLocalVars();
                UtilKt.removeEmptyCatchBlocks(this.methodNode);
            }
        }

        private final boolean rewriteLabelInstructions() {
            AbstractInsnNode first = this.instructions.getFirst();
            boolean z = false;
            while (first != null) {
                if (first instanceof LabelNode) {
                    LabelNode labelNode = (LabelNode) first;
                    AbstractInsnNode previous = labelNode.getPrevious();
                    if (previous instanceof LabelNode) {
                        this.newLabelNodes.set((LabelNode) first, (LabelNode) previous);
                        InsnList insnList = this.instructions;
                        Intrinsics.checkNotNull(insnList);
                        first = LabelNormalizationMethodTransformerKt.removeNodeGetNext(insnList, first);
                        z = true;
                    } else {
                        this.newLabelNodes.set((LabelNode) first, (LabelNode) first);
                        first = labelNode.getNext();
                    }
                } else {
                    first = first.getNext();
                }
            }
            return z;
        }

        private final void rewriteNonLabelInstructions() {
            AbstractInsnNode first = this.instructions.getFirst();
            while (first != null) {
                if (first instanceof JumpInsnNode) {
                    first = rewriteJumpInsn((JumpInsnNode) first);
                } else if (first instanceof LineNumberNode) {
                    first = rewriteLineNumberNode((LineNumberNode) first);
                } else if (first instanceof LookupSwitchInsnNode) {
                    first = rewriteLookupSwitchInsn((LookupSwitchInsnNode) first);
                } else if (first instanceof TableSwitchInsnNode) {
                    first = rewriteTableSwitchInsn((TableSwitchInsnNode) first);
                } else if (first instanceof FrameNode) {
                    first = rewriteFrameNode((FrameNode) first);
                } else {
                    first = first.getNext();
                }
            }
        }

        private final AbstractInsnNode rewriteLineNumberNode(LineNumberNode oldLineNode) {
            InsnList insnList = this.instructions;
            Intrinsics.checkNotNull(insnList);
            return LabelNormalizationMethodTransformerKt.replaceNodeGetNext(insnList, oldLineNode, rewriteLabels(oldLineNode));
        }

        private final AbstractInsnNode rewriteJumpInsn(JumpInsnNode oldJumpNode) {
            InsnList insnList = this.instructions;
            Intrinsics.checkNotNull(insnList);
            return LabelNormalizationMethodTransformerKt.replaceNodeGetNext(insnList, oldJumpNode, rewriteLabels(oldJumpNode));
        }

        private final AbstractInsnNode rewriteLookupSwitchInsn(LookupSwitchInsnNode oldSwitchNode) {
            InsnList insnList = this.instructions;
            Intrinsics.checkNotNull(insnList);
            return LabelNormalizationMethodTransformerKt.replaceNodeGetNext(insnList, oldSwitchNode, rewriteLabels(oldSwitchNode));
        }

        private final AbstractInsnNode rewriteTableSwitchInsn(TableSwitchInsnNode oldSwitchNode) {
            InsnList insnList = this.instructions;
            Intrinsics.checkNotNull(insnList);
            return LabelNormalizationMethodTransformerKt.replaceNodeGetNext(insnList, oldSwitchNode, rewriteLabels(oldSwitchNode));
        }

        private final AbstractInsnNode rewriteFrameNode(FrameNode oldFrameNode) {
            InsnList insnList = this.instructions;
            Intrinsics.checkNotNull(insnList);
            return LabelNormalizationMethodTransformerKt.replaceNodeGetNext(insnList, oldFrameNode, rewriteLabels(oldFrameNode));
        }

        private final void rewriteTryCatchBlocks() {
            MethodNode methodNode = this.methodNode;
            List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
            Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
            List<TryCatchBlockNode> list = tryCatchBlocks;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (TryCatchBlockNode tryCatchBlockNode : list) {
                LabelNode start = tryCatchBlockNode.start;
                Intrinsics.checkNotNullExpressionValue(start, "start");
                LabelNode labelNode = getNew(start);
                LabelNode end = tryCatchBlockNode.end;
                Intrinsics.checkNotNullExpressionValue(end, "end");
                LabelNode labelNode2 = getNew(end);
                LabelNode handler = tryCatchBlockNode.handler;
                Intrinsics.checkNotNullExpressionValue(handler, "handler");
                TryCatchBlockNode tryCatchBlockNode2 = new TryCatchBlockNode(labelNode, labelNode2, getNew(handler), tryCatchBlockNode.type);
                tryCatchBlockNode2.visibleTypeAnnotations = tryCatchBlockNode.visibleTypeAnnotations;
                tryCatchBlockNode2.invisibleTypeAnnotations = tryCatchBlockNode.invisibleTypeAnnotations;
                arrayList.add(tryCatchBlockNode2);
            }
            methodNode.tryCatchBlocks = arrayList;
        }

        private final void rewriteLocalVars() {
            MethodNode methodNode = this.methodNode;
            List<LocalVariableNode> localVariables = methodNode.localVariables;
            Intrinsics.checkNotNullExpressionValue(localVariables, "localVariables");
            List<LocalVariableNode> list = localVariables;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (LocalVariableNode localVariableNode : list) {
                String str = localVariableNode.f1014name;
                String str2 = localVariableNode.desc;
                String str3 = localVariableNode.signature;
                LabelNode start = localVariableNode.start;
                Intrinsics.checkNotNullExpressionValue(start, "start");
                LabelNode labelNode = getNew(start);
                LabelNode end = localVariableNode.end;
                Intrinsics.checkNotNullExpressionValue(end, "end");
                arrayList.add(new LocalVariableNode(str, str2, str3, labelNode, getNew(end), localVariableNode.index));
            }
            methodNode.localVariables = arrayList;
        }

        private final AbstractInsnNode rewriteLabels(LineNumberNode lineNumberNode) {
            int i = lineNumberNode.line;
            LabelNode start = lineNumberNode.start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            return new LineNumberNode(i, getNewOrOld(start));
        }

        private final AbstractInsnNode rewriteLabels(JumpInsnNode jumpInsnNode) {
            int opcode = jumpInsnNode.getOpcode();
            LabelNode label = jumpInsnNode.label;
            Intrinsics.checkNotNullExpressionValue(label, "label");
            return new JumpInsnNode(opcode, getNew(label));
        }

        private final AbstractInsnNode rewriteLabels(LookupSwitchInsnNode lookupSwitchInsnNode) {
            LabelNode dflt = lookupSwitchInsnNode.dflt;
            Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
            LabelNode labelNode = getNew(dflt);
            List<Integer> keys = lookupSwitchInsnNode.keys;
            Intrinsics.checkNotNullExpressionValue(keys, "keys");
            LookupSwitchInsnNode lookupSwitchInsnNode2 = new LookupSwitchInsnNode(labelNode, CollectionsKt.toIntArray(keys), new LabelNode[0]);
            List<LabelNode> labels = lookupSwitchInsnNode.labels;
            Intrinsics.checkNotNullExpressionValue(labels, "labels");
            List<LabelNode> list = labels;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (LabelNode labelNode2 : list) {
                Intrinsics.checkNotNull(labelNode2);
                arrayList.add(getNew(labelNode2));
            }
            lookupSwitchInsnNode2.labels = arrayList;
            return lookupSwitchInsnNode2;
        }

        private final AbstractInsnNode rewriteLabels(TableSwitchInsnNode tableSwitchInsnNode) {
            int i = tableSwitchInsnNode.min;
            int i2 = tableSwitchInsnNode.max;
            LabelNode dflt = tableSwitchInsnNode.dflt;
            Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
            TableSwitchInsnNode tableSwitchInsnNode2 = new TableSwitchInsnNode(i, i2, getNew(dflt), new LabelNode[0]);
            List<LabelNode> labels = tableSwitchInsnNode.labels;
            Intrinsics.checkNotNullExpressionValue(labels, "labels");
            List<LabelNode> list = labels;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (LabelNode labelNode : list) {
                Intrinsics.checkNotNull(labelNode);
                arrayList.add(getNew(labelNode));
            }
            tableSwitchInsnNode2.labels = arrayList;
            return tableSwitchInsnNode2;
        }

        private final AbstractInsnNode rewriteLabels(FrameNode frameNode) {
            FrameNode frameNode2 = new FrameNode(frameNode.type, 0, new Object[0], 0, new Object[0]);
            List<Object> local = frameNode.local;
            Intrinsics.checkNotNullExpressionValue(local, "local");
            List<Object> list = local;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (Object newOrOld : list) {
                if (newOrOld instanceof LabelNode) {
                    newOrOld = getNewOrOld((LabelNode) newOrOld);
                }
                arrayList.add(newOrOld);
            }
            frameNode2.local = arrayList;
            List<Object> stack = frameNode.stack;
            Intrinsics.checkNotNullExpressionValue(stack, "stack");
            List<Object> list2 = stack;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (Object newOrOld2 : list2) {
                if (newOrOld2 instanceof LabelNode) {
                    newOrOld2 = getNewOrOld((LabelNode) newOrOld2);
                }
                arrayList2.add(newOrOld2);
            }
            frameNode2.stack = arrayList2;
            return frameNode2;
        }

        private final LabelNode getNew(LabelNode oldLabelNode) {
            LabelNode labelNode = this.newLabelNodes.get(oldLabelNode);
            if (labelNode != null) {
                return labelNode;
            }
            throw new IllegalStateException("Label wasn't found during iterating through instructions");
        }

        private final LabelNode getNewOrOld(LabelNode oldLabelNode) {
            LabelNode labelNode = this.newLabelNodes.get(oldLabelNode);
            return labelNode == null ? oldLabelNode : labelNode;
        }
    }
}
