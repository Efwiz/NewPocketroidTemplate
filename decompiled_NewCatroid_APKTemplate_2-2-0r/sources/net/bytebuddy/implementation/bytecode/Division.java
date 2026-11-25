package net.bytebuddy.implementation.bytecode;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

/* JADX INFO: loaded from: classes12.dex */
public enum Division implements StackManipulation {
    INTEGER(108, StackSize.SINGLE),
    LONG(109, StackSize.DOUBLE),
    FLOAT(110, StackSize.SINGLE),
    DOUBLE(111, StackSize.DOUBLE);

    private final int opcode;
    private final StackSize stackSize;

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public boolean isValid() {
        return true;
    }

    Division(int i, StackSize stackSize) {
        this.opcode = i;
        this.stackSize = stackSize;
    }

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        methodVisitor.visitInsn(this.opcode);
        return this.stackSize.toDecreasingSize();
    }
}
