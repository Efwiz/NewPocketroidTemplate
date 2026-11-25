package org.jetbrains.org.objectweb.asm.commons;

import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes6.dex */
public class RecordComponentRemapper extends RecordComponentVisitor {
    protected final Remapper remapper;

    protected RecordComponentRemapper(int i, RecordComponentVisitor recordComponentVisitor, Remapper remapper) {
        super(i, recordComponentVisitor);
        this.remapper = remapper;
    }

    @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitAnnotation = super.visitAnnotation(this.remapper.mapDesc(str), z);
        if (annotationVisitorVisitAnnotation == null) {
            return null;
        }
        return createAnnotationRemapper(annotationVisitorVisitAnnotation);
    }

    @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
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
