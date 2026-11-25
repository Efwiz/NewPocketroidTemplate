package org.jetbrains.org.objectweb.asm.util;

import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Attribute;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes6.dex */
public final class TraceFieldVisitor extends FieldVisitor {
    public final Printer p;

    public TraceFieldVisitor(FieldVisitor fieldVisitor, Printer printer) {
        super(589824, fieldVisitor);
        this.p = printer;
    }

    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitAnnotation(str, z), this.p.visitFieldAnnotation(str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitTypeAnnotation(i, typePath, str, z), this.p.visitFieldTypeAnnotation(i, typePath, str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
    public void visitAttribute(Attribute attribute) {
        this.p.visitFieldAttribute(attribute);
        super.visitAttribute(attribute);
    }

    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
    public void visitEnd() {
        this.p.visitFieldEnd();
        super.visitEnd();
    }
}
