package org.jetbrains.kotlin.codegen.inline;

import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class MaxLocalsCalculator extends MethodVisitor {
    private int maxLocals;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "desc";
        } else if (i == 2) {
            objArr[0] = "start";
        } else if (i != 3) {
            objArr[0] = "name";
        } else {
            objArr[0] = "end";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/inline/MaxLocalsCalculator";
        objArr[2] = "visitLocalVariable";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public MaxLocalsCalculator(int i, int i2, String str, MethodVisitor methodVisitor) {
        super(i, methodVisitor);
        int argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(str) >> 2;
        this.maxLocals = (i2 & 8) != 0 ? argumentsAndReturnSizes - 1 : argumentsAndReturnSizes;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        updateMaxLocals((i == 22 || i == 24 || i == 55 || i == 57) ? i2 + 2 : i2 + 1);
        super.visitVarInsn(i, i2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitIincInsn(int i, int i2) {
        updateMaxLocals(i + 1);
        super.visitIincInsn(i, i2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        if (label == null) {
            $$$reportNull$$$0(2);
        }
        if (label2 == null) {
            $$$reportNull$$$0(3);
        }
        char cCharAt = str2.charAt(0);
        updateMaxLocals(((cCharAt == 'J' || cCharAt == 'D') ? 2 : 1) + i);
        super.visitLocalVariable(str, str2, str3, label, label2, i);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMaxs(int i, int i2) {
        super.visitMaxs(i, this.maxLocals);
    }

    public int getMaxLocals() {
        return this.maxLocals;
    }

    private void updateMaxLocals(int i) {
        if (i > this.maxLocals) {
            this.maxLocals = i;
        }
    }
}
