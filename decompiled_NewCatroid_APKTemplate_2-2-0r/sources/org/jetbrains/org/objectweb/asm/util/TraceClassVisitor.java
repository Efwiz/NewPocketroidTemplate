package org.jetbrains.org.objectweb.asm.util;

import java.io.PrintWriter;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Attribute;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.ModuleVisitor;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes6.dex */
public final class TraceClassVisitor extends ClassVisitor {
    public final Printer p;
    private final PrintWriter printWriter;

    public TraceClassVisitor(PrintWriter printWriter) {
        this(null, printWriter);
    }

    public TraceClassVisitor(ClassVisitor classVisitor, PrintWriter printWriter) {
        this(classVisitor, new Textifier(), printWriter);
    }

    public TraceClassVisitor(ClassVisitor classVisitor, Printer printer, PrintWriter printWriter) {
        super(17432576, classVisitor);
        this.printWriter = printWriter;
        this.p = printer;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
        this.p.visit(i, i2, str, str2, str3, strArr);
        super.visit(i, i2, str, str2, str3, strArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitSource(String str, String str2) {
        this.p.visitSource(str, str2);
        super.visitSource(str, str2);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public ModuleVisitor visitModule(String str, int i, String str2) {
        return new TraceModuleVisitor(super.visitModule(str, i, str2), this.p.visitModule(str, i, str2));
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitNestHost(String str) {
        this.p.visitNestHost(str);
        super.visitNestHost(str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitOuterClass(String str, String str2, String str3) {
        this.p.visitOuterClass(str, str2, str3);
        super.visitOuterClass(str, str2, str3);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitAnnotation(str, z), this.p.visitClassAnnotation(str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitTypeAnnotation(i, typePath, str, z), this.p.visitClassTypeAnnotation(i, typePath, str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitAttribute(Attribute attribute) {
        this.p.visitClassAttribute(attribute);
        super.visitAttribute(attribute);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitNestMember(String str) {
        this.p.visitNestMember(str);
        super.visitNestMember(str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitPermittedSubclass(String str) {
        this.p.visitPermittedSubclass(str);
        super.visitPermittedSubclass(str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitInnerClass(String str, String str2, String str3, int i) {
        this.p.visitInnerClass(str, str2, str3, i);
        super.visitInnerClass(str, str2, str3, i);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public RecordComponentVisitor visitRecordComponent(String str, String str2, String str3) {
        return new TraceRecordComponentVisitor(super.visitRecordComponent(str, str2, str3), this.p.visitRecordComponent(str, str2, str3));
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public FieldVisitor visitField(int i, String str, String str2, String str3, Object obj) {
        return new TraceFieldVisitor(super.visitField(i, str, str2, str3, obj), this.p.visitField(i, str, str2, str3, obj));
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        return new TraceMethodVisitor(super.visitMethod(i, str, str2, str3, strArr), this.p.visitMethod(i, str, str2, str3, strArr));
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitEnd() {
        this.p.visitClassEnd();
        PrintWriter printWriter = this.printWriter;
        if (printWriter != null) {
            this.p.print(printWriter);
            this.printWriter.flush();
        }
        super.visitEnd();
    }
}
