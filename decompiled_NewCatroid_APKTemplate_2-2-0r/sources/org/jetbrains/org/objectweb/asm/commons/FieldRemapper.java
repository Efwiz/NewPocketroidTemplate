package org.jetbrains.org.objectweb.asm.commons;

import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes6.dex */
public class FieldRemapper extends FieldVisitor {
    protected final Remapper remapper;

    public FieldRemapper(FieldVisitor fieldVisitor, Remapper remapper) {
        this(589824, fieldVisitor, remapper);
    }

    protected FieldRemapper(int i, FieldVisitor fieldVisitor, Remapper remapper) {
        super(i, fieldVisitor);
        this.remapper = remapper;
    }

    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitAnnotation = super.visitAnnotation(this.remapper.mapDesc(str), z);
        if (annotationVisitorVisitAnnotation == null) {
            return null;
        }
        return createAnnotationRemapper(annotationVisitorVisitAnnotation);
    }

    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitTypeAnnotation = super.visitTypeAnnotation(i, typePath, this.remapper.mapDesc(str), z);
        if (annotationVisitorVisitTypeAnnotation == null) {
            return null;
        }
        return createAnnotationRemapper(annotationVisitorVisitTypeAnnotation);
    }

    protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, annotationVisitor, this.remapper);
    }
}
