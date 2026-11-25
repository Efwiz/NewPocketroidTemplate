package net.bytebuddy.utility.visitor;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.utility.OpenedClassReader;

/* JADX INFO: loaded from: classes12.dex */
public class LocalVariableAwareMethodVisitor extends MethodVisitor {
    private int freeOffset;

    public LocalVariableAwareMethodVisitor(MethodVisitor methodVisitor, MethodDescription methodDescription) {
        super(OpenedClassReader.ASM_API, methodVisitor);
        this.freeOffset = methodDescription.getStackSize();
    }

    @Override // net.bytebuddy.jar.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        switch (i) {
            case 54:
            case 56:
            case 58:
                this.freeOffset = Math.max(this.freeOffset, i2 + 1);
                break;
            case 55:
            case 57:
                this.freeOffset = Math.max(this.freeOffset, i2 + 2);
                break;
        }
        super.visitVarInsn(i, i2);
    }

    public int getFreeOffset() {
        return this.freeOffset;
    }
}
