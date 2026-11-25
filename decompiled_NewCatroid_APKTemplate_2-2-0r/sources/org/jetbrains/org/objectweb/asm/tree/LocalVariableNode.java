package org.jetbrains.org.objectweb.asm.tree;

import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class LocalVariableNode {
    public String desc;
    public LabelNode end;
    public int index;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f1014name;
    public String signature;
    public LabelNode start;

    public LocalVariableNode(String str, String str2, String str3, LabelNode labelNode, LabelNode labelNode2, int i) {
        this.f1014name = str;
        this.desc = str2;
        this.signature = str3;
        this.start = labelNode;
        this.end = labelNode2;
        this.index = i;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitLocalVariable(this.f1014name, this.desc, this.signature, this.start.getLabel(), this.end.getLabel(), this.index);
    }
}
