package org.jetbrains.org.objectweb.asm.commons;

import org.jetbrains.org.objectweb.asm.AnnotationVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class AnnotationRemapper extends AnnotationVisitor {
    protected final Remapper remapper;

    public AnnotationRemapper(AnnotationVisitor annotationVisitor, Remapper remapper) {
        this(589824, annotationVisitor, remapper);
    }

    protected AnnotationRemapper(int i, AnnotationVisitor annotationVisitor, Remapper remapper) {
        super(i, annotationVisitor);
        this.remapper = remapper;
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visit(String str, Object obj) {
        super.visit(str, this.remapper.mapValue(obj));
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visitEnum(String str, String str2, String str3) {
        super.visitEnum(str, this.remapper.mapDesc(str2), str3);
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String str, String str2) {
        AnnotationVisitor annotationVisitorVisitAnnotation = super.visitAnnotation(str, this.remapper.mapDesc(str2));
        if (annotationVisitorVisitAnnotation == null) {
            return null;
        }
        return annotationVisitorVisitAnnotation == this.av ? this : createAnnotationRemapper(annotationVisitorVisitAnnotation);
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String str) {
        AnnotationVisitor annotationVisitorVisitArray = super.visitArray(str);
        if (annotationVisitorVisitArray == null) {
            return null;
        }
        return annotationVisitorVisitArray == this.av ? this : createAnnotationRemapper(annotationVisitorVisitArray);
    }

    protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, annotationVisitor, this.remapper);
    }
}
