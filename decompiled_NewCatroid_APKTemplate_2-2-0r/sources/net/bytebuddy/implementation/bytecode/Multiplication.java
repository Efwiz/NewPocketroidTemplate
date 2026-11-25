package net.bytebuddy.implementation.bytecode;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

/* JADX INFO: loaded from: classes12.dex */
public enum Multiplication implements StackManipulation {
    INTEGER(104, StackSize.SINGLE),
    LONG(105, StackSize.DOUBLE),
    FLOAT(106, StackSize.SINGLE),
    DOUBLE(107, StackSize.DOUBLE);

    private final int opcode;
    private final StackSize stackSize;

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public boolean isValid() {
        return true;
    }

    Multiplication(int i, StackSize stackSize) {
        this.opcode = i;
        this.stackSize = stackSize;
    }

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        methodVisitor.visitInsn(this.opcode);
        return this.stackSize.toDecreasingSize();
    }
}
