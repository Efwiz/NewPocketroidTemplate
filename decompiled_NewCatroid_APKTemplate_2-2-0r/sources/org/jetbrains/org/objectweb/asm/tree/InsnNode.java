package org.jetbrains.org.objectweb.asm.tree;

import java.util.Map;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class InsnNode extends AbstractInsnNode {
    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public int getType() {
        return 0;
    }

    public InsnNode(int i) {
        super(i);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(this.opcode);
        acceptAnnotations(methodVisitor);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> map) {
        return new InsnNode(this.opcode).cloneAnnotations(this);
    }
}
