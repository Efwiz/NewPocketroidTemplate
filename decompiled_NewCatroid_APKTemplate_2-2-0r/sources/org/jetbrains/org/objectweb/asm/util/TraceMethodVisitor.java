package org.jetbrains.org.objectweb.asm.util;

import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Attribute;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes6.dex */
public final class TraceMethodVisitor extends MethodVisitor {
    public final Printer p;

    public TraceMethodVisitor(Printer printer) {
        this(null, printer);
    }

    public TraceMethodVisitor(MethodVisitor methodVisitor, Printer printer) {
        super(589824, methodVisitor);
        this.p = printer;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitParameter(String str, int i) {
        this.p.visitParameter(str, i);
        super.visitParameter(str, i);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitAnnotation(str, z), this.p.visitMethodAnnotation(str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitTypeAnnotation(i, typePath, str, z), this.p.visitMethodTypeAnnotation(i, typePath, str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitAttribute(Attribute attribute) {
        this.p.visitMethodAttribute(attribute);
        super.visitAttribute(attribute);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        return new TraceAnnotationVisitor(super.visitAnnotationDefault(), this.p.visitAnnotationDefault());
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitAnnotableParameterCount(int i, boolean z) {
        this.p.visitAnnotableParameterCount(i, z);
        super.visitAnnotableParameterCount(i, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitParameterAnnotation(i, str, z), this.p.visitParameterAnnotation(i, str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitCode() {
        this.p.visitCode();
        super.visitCode();
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        this.p.visitFrame(i, i2, objArr, i3, objArr2);
        super.visitFrame(i, i2, objArr, i3, objArr2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitInsn(int i) {
        this.p.visitInsn(i);
        super.visitInsn(i);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitIntInsn(int i, int i2) {
        this.p.visitIntInsn(i, i2);
        super.visitIntInsn(i, i2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        this.p.visitVarInsn(i, i2);
        super.visitVarInsn(i, i2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTypeInsn(int i, String str) {
        this.p.visitTypeInsn(i, str);
        super.visitTypeInsn(i, str);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitFieldInsn(int i, String str, String str2, String str3) {
        this.p.visitFieldInsn(i, str, str2, str3);
        super.visitFieldInsn(i, str, str2, str3);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        if (this.p.api < 327680) {
            if (z != (i == 185)) {
                throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM5");
            }
            this.p.visitMethodInsn(i, str, str2, str3);
        } else {
            this.p.visitMethodInsn(i, str, str2, str3, z);
        }
        if (this.mv != null) {
            this.mv.visitMethodInsn(i, str, str2, str3, z);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        this.p.visitInvokeDynamicInsn(str, str2, handle, objArr);
        super.visitInvokeDynamicInsn(str, str2, handle, objArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitJumpInsn(int i, Label label) {
        this.p.visitJumpInsn(i, label);
        super.visitJumpInsn(i, label);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLabel(Label label) {
        this.p.visitLabel(label);
        super.visitLabel(label);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLdcInsn(Object obj) {
        this.p.visitLdcInsn(obj);
        super.visitLdcInsn(obj);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitIincInsn(int i, int i2) {
        this.p.visitIincInsn(i, i2);
        super.visitIincInsn(i, i2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr) {
        this.p.visitTableSwitchInsn(i, i2, label, labelArr);
        super.visitTableSwitchInsn(i, i2, label, labelArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        this.p.visitLookupSwitchInsn(label, iArr, labelArr);
        super.visitLookupSwitchInsn(label, iArr, labelArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMultiANewArrayInsn(String str, int i) {
        this.p.visitMultiANewArrayInsn(str, i);
        super.visitMultiANewArrayInsn(str, i);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitInsnAnnotation(i, typePath, str, z), this.p.visitInsnAnnotation(i, typePath, str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        this.p.visitTryCatchBlock(label, label2, label3, str);
        super.visitTryCatchBlock(label, label2, label3, str);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitTryCatchAnnotation(i, typePath, str, z), this.p.visitTryCatchAnnotation(i, typePath, str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        this.p.visitLocalVariable(str, str2, str3, label, label2, i);
        super.visitLocalVariable(str, str2, str3, label, label2, i);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        return new TraceAnnotationVisitor(super.visitLocalVariableAnnotation(i, typePath, labelArr, labelArr2, iArr, str, z), this.p.visitLocalVariableAnnotation(i, typePath, labelArr, labelArr2, iArr, str, z));
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLineNumber(int i, Label label) {
        this.p.visitLineNumber(i, label);
        super.visitLineNumber(i, label);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMaxs(int i, int i2) {
        this.p.visitMaxs(i, i2);
        super.visitMaxs(i, i2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitEnd() {
        this.p.visitMethodEnd();
        super.visitEnd();
    }
}
