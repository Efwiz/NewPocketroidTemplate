package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: NegatedJumpsMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/NegatedJumpsMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "negatedConditionalJumpOpcode", "", "negateConditionalJumpOpcode", "", "opcode", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NegatedJumpsMethodTransformer extends MethodTransformer {
    private final int[] negatedConditionalJumpOpcode;

    public NegatedJumpsMethodTransformer() {
        int[] iArr = new int[255];
        negatedConditionalJumpOpcode$lambda$0$negated(iArr, 198, 199);
        negatedConditionalJumpOpcode$lambda$0$negated(iArr, 153, 154);
        negatedConditionalJumpOpcode$lambda$0$negated(iArr, 155, 156);
        negatedConditionalJumpOpcode$lambda$0$negated(iArr, 158, 157);
        negatedConditionalJumpOpcode$lambda$0$negated(iArr, 159, 160);
        negatedConditionalJumpOpcode$lambda$0$negated(iArr, 161, 162);
        negatedConditionalJumpOpcode$lambda$0$negated(iArr, 164, 163);
        negatedConditionalJumpOpcode$lambda$0$negated(iArr, 165, 166);
        this.negatedConditionalJumpOpcode = iArr;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        AbstractInsnNode next;
        AbstractInsnNode next2;
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        InsnList insnList = methodNode.instructions;
        AbstractInsnNode[] array = insnList.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode.getType() == 7 && abstractInsnNode.getOpcode() != 167 && (next = abstractInsnNode.getNext()) != null && next.getOpcode() == 167 && (next2 = next.getNext()) != null) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
                if (Intrinsics.areEqual(next2, jumpInsnNode.label)) {
                    insnList.insertBefore(abstractInsnNode, new JumpInsnNode(negateConditionalJumpOpcode(jumpInsnNode.getOpcode()), ((JumpInsnNode) next).label));
                    insnList.remove(abstractInsnNode);
                    insnList.remove(next);
                }
            }
        }
    }

    private static final void negatedConditionalJumpOpcode$lambda$0$negated(int[] iArr, int i, int i2) {
        iArr[i] = i2;
        iArr[i2] = i;
    }

    private final int negateConditionalJumpOpcode(int opcode) {
        return this.negatedConditionalJumpOpcode[opcode];
    }
}
