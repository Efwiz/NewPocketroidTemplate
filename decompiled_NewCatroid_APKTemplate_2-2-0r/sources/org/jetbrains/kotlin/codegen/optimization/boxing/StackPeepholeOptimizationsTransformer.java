package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: StackPeepholeOptimizationsTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\f\u0010\u000b\u001a\u00020\n*\u00020\fH\u0002J\f\u0010\r\u001a\u00020\n*\u00020\fH\u0002J\f\u0010\u000e\u001a\u00020\n*\u00020\fH\u0002J\f\u0010\u000f\u001a\u00020\n*\u00020\fH\u0002J\f\u0010\u0010\u001a\u00020\n*\u00020\fH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/StackPeepholeOptimizationsTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "transformOnce", "", "isEliminatedByPop", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isEliminatedByPop2", "isLdcOfSize2", "isPurePushOfSize1", "isPurePushOfSize2", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StackPeepholeOptimizationsTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        while (transformOnce(methodNode)) {
        }
    }

    private final boolean transformOnce(MethodNode methodNode) {
        InsnList insnList = methodNode.instructions;
        boolean[] zArrFindMergeNodes = FastMethodAnalyzer.INSTANCE.findMergeNodes(methodNode);
        AbstractInsnNode first = insnList.getFirst();
        boolean z = false;
        while (first != null) {
            first = first.getNext();
            AbstractInsnNode abstractInsnNodeTransformOnce$previousMeaningful = transformOnce$previousMeaningful(first, zArrFindMergeNodes, insnList);
            if (abstractInsnNodeTransformOnce$previousMeaningful != null) {
                int opcode = first.getOpcode();
                if (opcode == 87) {
                    if (isEliminatedByPop(abstractInsnNodeTransformOnce$previousMeaningful)) {
                        insnList.set(first, new InsnNode(0));
                        insnList.set(abstractInsnNodeTransformOnce$previousMeaningful, new InsnNode(0));
                    } else if (abstractInsnNodeTransformOnce$previousMeaningful.getOpcode() == 90) {
                        insnList.set(first, new InsnNode(0));
                        insnList.set(abstractInsnNodeTransformOnce$previousMeaningful, new InsnNode(95));
                    }
                    z = true;
                } else if (opcode != 88) {
                    if (opcode == 95) {
                        AbstractInsnNode abstractInsnNodeTransformOnce$previousMeaningful2 = transformOnce$previousMeaningful(abstractInsnNodeTransformOnce$previousMeaningful, zArrFindMergeNodes, insnList);
                        if (abstractInsnNodeTransformOnce$previousMeaningful2 != null && isPurePushOfSize1(abstractInsnNodeTransformOnce$previousMeaningful) && isPurePushOfSize1(abstractInsnNodeTransformOnce$previousMeaningful2)) {
                            insnList.set(first, new InsnNode(0));
                            insnList.set(abstractInsnNodeTransformOnce$previousMeaningful, abstractInsnNodeTransformOnce$previousMeaningful2.clone(MapsKt.emptyMap()));
                            insnList.set(abstractInsnNodeTransformOnce$previousMeaningful2, abstractInsnNodeTransformOnce$previousMeaningful.clone(MapsKt.emptyMap()));
                            z = true;
                        }
                    } else if (opcode == 133) {
                        int opcode2 = abstractInsnNodeTransformOnce$previousMeaningful.getOpcode();
                        if (opcode2 == 3) {
                            insnList.set(first, new InsnNode(0));
                            insnList.set(abstractInsnNodeTransformOnce$previousMeaningful, new InsnNode(9));
                            z = true;
                        } else if (opcode2 == 4) {
                            insnList.set(first, new InsnNode(0));
                            insnList.set(abstractInsnNodeTransformOnce$previousMeaningful, new InsnNode(10));
                            z = true;
                        }
                    }
                } else if (isEliminatedByPop2(abstractInsnNodeTransformOnce$previousMeaningful)) {
                    insnList.set(first, new InsnNode(0));
                    insnList.set(abstractInsnNodeTransformOnce$previousMeaningful, new InsnNode(0));
                    z = true;
                } else {
                    AbstractInsnNode abstractInsnNodeTransformOnce$previousMeaningful3 = transformOnce$previousMeaningful(abstractInsnNodeTransformOnce$previousMeaningful, zArrFindMergeNodes, insnList);
                    if (abstractInsnNodeTransformOnce$previousMeaningful3 != null && isEliminatedByPop(abstractInsnNodeTransformOnce$previousMeaningful) && isEliminatedByPop(abstractInsnNodeTransformOnce$previousMeaningful3)) {
                        insnList.set(first, new InsnNode(0));
                        insnList.set(abstractInsnNodeTransformOnce$previousMeaningful, new InsnNode(0));
                        insnList.set(abstractInsnNodeTransformOnce$previousMeaningful3, new InsnNode(0));
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    private final boolean isEliminatedByPop(AbstractInsnNode abstractInsnNode) {
        return isPurePushOfSize1(abstractInsnNode) || abstractInsnNode.getOpcode() == 89;
    }

    private final boolean isPurePushOfSize1(AbstractInsnNode abstractInsnNode) {
        if (!isLdcOfSize2(abstractInsnNode)) {
            int opcode = abstractInsnNode.getOpcode();
            if (1 <= opcode && opcode < 14) {
                return true;
            }
            int opcode2 = abstractInsnNode.getOpcode();
            if ((16 <= opcode2 && opcode2 < 22) || abstractInsnNode.getOpcode() == 23 || abstractInsnNode.getOpcode() == 25 || PopBackwardPropagationTransformerKt.isUnitInstance(abstractInsnNode)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isEliminatedByPop2(AbstractInsnNode abstractInsnNode) {
        return isPurePushOfSize2(abstractInsnNode) || abstractInsnNode.getOpcode() == 92;
    }

    private final boolean isPurePushOfSize2(AbstractInsnNode abstractInsnNode) {
        return isLdcOfSize2(abstractInsnNode) || abstractInsnNode.getOpcode() == 9 || abstractInsnNode.getOpcode() == 10 || abstractInsnNode.getOpcode() == 14 || abstractInsnNode.getOpcode() == 15 || abstractInsnNode.getOpcode() == 22 || abstractInsnNode.getOpcode() == 24;
    }

    private final boolean isLdcOfSize2(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode.getOpcode() == 18 && (abstractInsnNode instanceof LdcInsnNode)) {
            LdcInsnNode ldcInsnNode = (LdcInsnNode) abstractInsnNode;
            if ((ldcInsnNode.cst instanceof Double) || (ldcInsnNode.cst instanceof Long)) {
                return true;
            }
        }
        return false;
    }

    private static final AbstractInsnNode transformOnce$previousMeaningful(AbstractInsnNode abstractInsnNode, boolean[] zArr, InsnList insnList) {
        AbstractInsnNode previous = abstractInsnNode.getPrevious();
        while (previous != null && (previous.getOpcode() == 0 || previous.getType() == 15 || (previous.getType() == 8 && !zArr[insnList.indexOf(previous)]))) {
            previous = previous.getPrevious();
        }
        return previous;
    }
}
