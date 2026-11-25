package org.jetbrains.org.objectweb.asm.tree;

import org.jetbrains.org.objectweb.asm.ClassVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class InnerClassNode {
    public int access;
    public String innerName;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f1012name;
    public String outerName;

    public InnerClassNode(String str, String str2, String str3, int i) {
        this.f1012name = str;
        this.outerName = str2;
        this.innerName = str3;
        this.access = i;
    }

    public void accept(ClassVisitor classVisitor) {
        classVisitor.visitInnerClass(this.f1012name, this.outerName, this.innerName, this.access);
    }
}
