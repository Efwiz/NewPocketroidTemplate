package net.bytebuddy.implementation.bytecode;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

/* JADX INFO: loaded from: classes12.dex */
public enum ShiftRight implements StackManipulation {
    INTEGER(122, StackSize.SINGLE, Unsigned.INTEGER),
    LONG(123, StackSize.DOUBLE, Unsigned.LONG);

    private final int opcode;
    private final StackSize stackSize;
    private final StackManipulation unsigned;

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public boolean isValid() {
        return true;
    }

    ShiftRight(int i, StackSize stackSize, StackManipulation stackManipulation) {
        this.opcode = i;
        this.stackSize = stackSize;
        this.unsigned = stackManipulation;
    }

    public StackManipulation toUnsigned() {
        return this.unsigned;
    }

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        methodVisitor.visitInsn(this.opcode);
        return this.stackSize.toDecreasingSize();
    }

    protected enum Unsigned implements StackManipulation {
        INTEGER(124, StackSize.SINGLE),
        LONG(125, StackSize.DOUBLE);

        private final int opcode;
        private final StackSize stackSize;

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public boolean isValid() {
            return true;
        }

        Unsigned(int i, StackSize stackSize) {
            this.opcode = i;
            this.stackSize = stackSize;
        }

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
            methodVisitor.visitInsn(this.opcode);
            return this.stackSize.toDecreasingSize();
        }
    }
}
