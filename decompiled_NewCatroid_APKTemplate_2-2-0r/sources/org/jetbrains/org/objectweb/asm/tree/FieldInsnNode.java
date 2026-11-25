package org.jetbrains.org.objectweb.asm.tree;

import java.util.Map;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class FieldInsnNode extends AbstractInsnNode {
    public String desc;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f1010name;
    public String owner;

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public int getType() {
        return 4;
    }

    public FieldInsnNode(int i, String str, String str2, String str3) {
        super(i);
        this.owner = str;
        this.f1010name = str2;
        this.desc = str3;
    }

    public void setOpcode(int i) {
        this.opcode = i;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitFieldInsn(this.opcode, this.owner, this.f1010name, this.desc);
        acceptAnnotations(methodVisitor);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> map) {
        return new FieldInsnNode(this.opcode, this.owner, this.f1010name, this.desc).cloneAnnotations(this);
    }
}
