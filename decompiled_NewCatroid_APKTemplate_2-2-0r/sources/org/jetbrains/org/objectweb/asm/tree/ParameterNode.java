package org.jetbrains.org.objectweb.asm.tree;

import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class ParameterNode {
    public int access;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f1018name;

    public ParameterNode(String str, int i) {
        this.f1018name = str;
        this.access = i;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitParameter(this.f1018name, this.access);
    }
}
