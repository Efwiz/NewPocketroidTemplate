package org.jetbrains.org.objectweb.asm.commons;

import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes6.dex */
public class MethodRemapper extends MethodVisitor {
    protected final Remapper remapper;

    public MethodRemapper(MethodVisitor methodVisitor, Remapper remapper) {
        this(589824, methodVisitor, remapper);
    }

    protected MethodRemapper(int i, MethodVisitor methodVisitor, Remapper remapper) {
        super(i, methodVisitor);
        this.remapper = remapper;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        AnnotationVisitor annotationVisitorVisitAnnotationDefault = super.visitAnnotationDefault();
        return annotationVisitorVisitAnnotationDefault == null ? annotationVisitorVisitAnnotationDefault : createAnnotationRemapper(annotationVisitorVisitAnnotationDefault);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitAnnotation = super.visitAnnotation(this.remapper.mapDesc(str), z);
        return annotationVisitorVisitAnnotation == null ? annotationVisitorVisitAnnotation : createAnnotationRemapper(annotationVisitorVisitAnnotation);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitTypeAnnotation = super.visitTypeAnnotation(i, typePath, this.remapper.mapDesc(str), z);
        return annotationVisitorVisitTypeAnnotation == null ? annotationVisitorVisitTypeAnnotation : createAnnotationRemapper(annotationVisitorVisitTypeAnnotation);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitParameterAnnotation = super.visitParameterAnnotation(i, this.remapper.mapDesc(str), z);
        return annotationVisitorVisitParameterAnnotation == null ? annotationVisitorVisitParameterAnnotation : createAnnotationRemapper(annotationVisitorVisitParameterAnnotation);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        super.visitFrame(i, i2, remapFrameTypes(i2, objArr), i3, remapFrameTypes(i3, objArr2));
    }

    private Object[] remapFrameTypes(int i, Object[] objArr) {
        if (objArr == null) {
            return objArr;
        }
        Object[] objArr2 = null;
        for (int i2 = 0; i2 < i; i2++) {
            if (objArr[i2] instanceof String) {
                if (objArr2 == null) {
                    objArr2 = new Object[i];
                    System.arraycopy(objArr, 0, objArr2, 0, i);
                }
                objArr2[i2] = this.remapper.mapType((String) objArr[i2]);
            }
        }
        return objArr2 == null ? objArr : objArr2;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitFieldInsn(int i, String str, String str2, String str3) {
        super.visitFieldInsn(i, this.remapper.mapType(str), this.remapper.mapFieldName(str, str2, str3), this.remapper.mapDesc(str3));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        if (this.api < 327680 && (i & 256) == 0) {
            super.visitMethodInsn(i, str, str2, str3, z);
        } else {
            super.visitMethodInsn(i, this.remapper.mapType(str), this.remapper.mapMethodName(str, str2, str3), this.remapper.mapMethodDesc(str3), z);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        Object[] objArr2 = new Object[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            objArr2[i] = this.remapper.mapValue(objArr[i]);
        }
        super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(str, str2), this.remapper.mapMethodDesc(str2), (Handle) this.remapper.mapValue(handle), objArr2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTypeInsn(int i, String str) {
        super.visitTypeInsn(i, this.remapper.mapType(str));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLdcInsn(Object obj) {
        super.visitLdcInsn(this.remapper.mapValue(obj));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMultiANewArrayInsn(String str, int i) {
        super.visitMultiANewArrayInsn(this.remapper.mapDesc(str), i);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitInsnAnnotation = super.visitInsnAnnotation(i, typePath, this.remapper.mapDesc(str), z);
        return annotationVisitorVisitInsnAnnotation == null ? annotationVisitorVisitInsnAnnotation : createAnnotationRemapper(annotationVisitorVisitInsnAnnotation);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        super.visitTryCatchBlock(label, label2, label3, str == null ? null : this.remapper.mapType(str));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitTryCatchAnnotation = super.visitTryCatchAnnotation(i, typePath, this.remapper.mapDesc(str), z);
        return annotationVisitorVisitTryCatchAnnotation == null ? annotationVisitorVisitTryCatchAnnotation : createAnnotationRemapper(annotationVisitorVisitTryCatchAnnotation);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        super.visitLocalVariable(str, this.remapper.mapDesc(str2), this.remapper.mapSignature(str3, true), label, label2, i);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        AnnotationVisitor annotationVisitorVisitLocalVariableAnnotation = super.visitLocalVariableAnnotation(i, typePath, labelArr, labelArr2, iArr, this.remapper.mapDesc(str), z);
        return annotationVisitorVisitLocalVariableAnnotation == null ? annotationVisitorVisitLocalVariableAnnotation : createAnnotationRemapper(annotationVisitorVisitLocalVariableAnnotation);
    }

    protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, annotationVisitor, this.remapper);
    }
}
