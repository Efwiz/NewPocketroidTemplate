package org.jetbrains.org.objectweb.asm.tree;

import java.util.Map;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class TypeInsnNode extends AbstractInsnNode {
    public String desc;

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public int getType() {
        return 3;
    }

    public TypeInsnNode(int i, String str) {
        super(i);
        this.desc = str;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitTypeInsn(this.opcode, this.desc);
        acceptAnnotations(methodVisitor);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> map) {
        return new TypeInsnNode(this.opcode, this.desc).cloneAnnotations(this);
    }
}
