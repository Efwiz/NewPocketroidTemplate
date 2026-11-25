package org.jetbrains.org.objectweb.asm.tree;

import java.util.Map;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class InvokeDynamicInsnNode extends AbstractInsnNode {
    public Handle bsm;
    public Object[] bsmArgs;
    public String desc;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f1013name;

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public int getType() {
        return 6;
    }

    public InvokeDynamicInsnNode(String str, String str2, Handle handle, Object... objArr) {
        super(186);
        this.f1013name = str;
        this.desc = str2;
        this.bsm = handle;
        this.bsmArgs = objArr;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitInvokeDynamicInsn(this.f1013name, this.desc, this.bsm, this.bsmArgs);
        acceptAnnotations(methodVisitor);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> map) {
        return new InvokeDynamicInsnNode(this.f1013name, this.desc, this.bsm, this.bsmArgs).cloneAnnotations(this);
    }
}
