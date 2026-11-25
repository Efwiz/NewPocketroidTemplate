package org.jetbrains.org.objectweb.asm.util;

import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Attribute;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes6.dex */
public final class TraceRecordComponentVisitor extends RecordComponentVisitor {
    public final Printer printer;

    public TraceRecordComponentVisitor(RecordComponentVisitor recordComponentVisitor, Printer printer) {
        super(589824, recordComponentVisitor);
        this.printer = printer;
    }

    @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitAnnotation(str, z), this.printer.visitRecordComponentAnnotation(str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitTypeAnnotation(i, typePath, str, z), this.printer.visitRecordComponentTypeAnnotation(i, typePath, str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
    public void visitAttribute(Attribute attribute) {
        this.printer.visitRecordComponentAttribute(attribute);
        super.visitAttribute(attribute);
    }

    @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
    public void visitEnd() {
        this.printer.visitRecordComponentEnd();
        super.visitEnd();
    }
}
