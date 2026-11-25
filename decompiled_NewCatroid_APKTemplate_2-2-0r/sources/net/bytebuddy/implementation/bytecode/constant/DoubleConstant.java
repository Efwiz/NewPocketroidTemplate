package net.bytebuddy.implementation.bytecode.constant;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public enum DoubleConstant implements StackManipulation {
    ZERO(14),
    ONE(15);

    private static final StackManipulation.Size SIZE = StackSize.DOUBLE.toIncreasingSize();
    private final int opcode;

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public boolean isValid() {
        return true;
    }

    DoubleConstant(int i) {
        this.opcode = i;
    }

    public static StackManipulation forValue(double d) {
        if (d == 0.0d) {
            return ZERO;
        }
        if (d == 1.0d) {
            return ONE;
        }
        return new ConstantPool(d);
    }

    @Override // net.bytebuddy.implementation.bytecode.StackManipulation
    public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        methodVisitor.visitInsn(this.opcode);
        return SIZE;
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ConstantPool extends StackManipulation.AbstractBase {
        private final double value;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && Double.compare(this.value, ((ConstantPool) obj).value) == 0;
        }

        public int hashCode() {
            int iHashCode = getClass().hashCode() * 31;
            long jDoubleToLongBits = Double.doubleToLongBits(this.value);
            return iHashCode + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
        }

        protected ConstantPool(double d) {
            this.value = d;
        }

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
            methodVisitor.visitLdcInsn(Double.valueOf(this.value));
            return DoubleConstant.SIZE;
        }
    }
}
