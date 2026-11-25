package org.jetbrains.org.objectweb.asm.util;

import org.jetbrains.org.objectweb.asm.ModuleVisitor;

/* JADX INFO: loaded from: classes6.dex */
public final class TraceModuleVisitor extends ModuleVisitor {
    public final Printer p;

    public TraceModuleVisitor(ModuleVisitor moduleVisitor, Printer printer) {
        super(589824, moduleVisitor);
        this.p = printer;
    }

    @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
    public void visitMainClass(String str) {
        this.p.visitMainClass(str);
        super.visitMainClass(str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
    public void visitPackage(String str) {
        this.p.visitPackage(str);
        super.visitPackage(str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
    public void visitRequire(String str, int i, String str2) {
        this.p.visitRequire(str, i, str2);
        super.visitRequire(str, i, str2);
    }

    @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
    public void visitExport(String str, int i, String... strArr) {
        this.p.visitExport(str, i, strArr);
        super.visitExport(str, i, strArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
    public void visitOpen(String str, int i, String... strArr) {
        this.p.visitOpen(str, i, strArr);
        super.visitOpen(str, i, strArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
    public void visitUse(String str) {
        this.p.visitUse(str);
        super.visitUse(str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
    public void visitProvide(String str, String... strArr) {
        this.p.visitProvide(str, strArr);
        super.visitProvide(str, strArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
    public void visitEnd() {
        this.p.visitModuleEnd();
        super.visitEnd();
    }
}
