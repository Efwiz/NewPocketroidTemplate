package org.jetbrains.org.objectweb.asm.tree;

import java.util.Map;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class MethodInsnNode extends AbstractInsnNode {
    public String desc;
    public boolean itf;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f1015name;
    public String owner;

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public int getType() {
        return 5;
    }

    public MethodInsnNode(int i, String str, String str2, String str3, boolean z) {
        super(i);
        this.owner = str;
        this.f1015name = str2;
        this.desc = str3;
        this.itf = z;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitMethodInsn(this.opcode, this.owner, this.f1015name, this.desc, this.itf);
        acceptAnnotations(methodVisitor);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> map) {
        return new MethodInsnNode(this.opcode, this.owner, this.f1015name, this.desc, this.itf).cloneAnnotations(this);
    }
}
