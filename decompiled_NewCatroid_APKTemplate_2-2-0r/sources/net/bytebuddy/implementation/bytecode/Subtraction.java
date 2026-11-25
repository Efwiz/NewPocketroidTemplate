package net.bytebuddy.implementation.bytecode;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

/* JADX INFO: loaded from: classes12.dex */
public enum Subtraction implements StackManipulation {
    INTEGER(100, StackSize.SINGLE),
    LONG(101, StackSize.DOUBLE),
    FLOAT(102, StackSize.SINGLE),
    DOUBLE(103, StackSize.DOUBLE);

    private final int opcode;
    private final StackSize stackSize;

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public boolean isValid() {
        return true;
    }

    Subtraction(int i, StackSize stackSize) {
        this.opcode = i;
        this.stackSize = stackSize;
    }

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        methodVisitor.visitInsn(this.opcode);
        return this.stackSize.toDecreasingSize();
    }
}
