package org.jetbrains.org.objectweb.asm.util;

import org.jetbrains.org.objectweb.asm.AnnotationVisitor;

/* JADX INFO: loaded from: classes6.dex */
public final class TraceAnnotationVisitor extends AnnotationVisitor {
    private final Printer printer;

    public TraceAnnotationVisitor(AnnotationVisitor annotationVisitor, Printer printer) {
        super(589824, annotationVisitor);
        this.printer = printer;
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visit(String str, Object obj) {
        this.printer.visit(str, obj);
        super.visit(str, obj);
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visitEnum(String str, String str2, String str3) {
        this.printer.visitEnum(str, str2, str3);
        super.visitEnum(str, str2, str3);
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String str, String str2) {
        return new TraceAnnotationVisitor(super.visitAnnotation(str, str2), this.printer.visitAnnotation(str, str2));
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String str) {
        return new TraceAnnotationVisitor(super.visitArray(str), this.printer.visitArray(str));
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visitEnd() {
        this.printer.visitAnnotationEnd();
        super.visitEnd();
    }
}
