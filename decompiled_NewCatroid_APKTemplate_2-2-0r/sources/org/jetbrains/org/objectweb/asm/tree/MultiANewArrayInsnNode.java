package org.jetbrains.org.objectweb.asm.tree;

import java.util.Map;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class MultiANewArrayInsnNode extends AbstractInsnNode {
    public String desc;
    public int dims;

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public int getType() {
        return 13;
    }

    public MultiANewArrayInsnNode(String str, int i) {
        super(197);
        this.desc = str;
        this.dims = i;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitMultiANewArrayInsn(this.desc, this.dims);
        acceptAnnotations(methodVisitor);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> map) {
        return new MultiANewArrayInsnNode(this.desc, this.dims).cloneAnnotations(this);
    }
}
