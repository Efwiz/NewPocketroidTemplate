package org.eclipse.jdt.internal.compiler.impl;

/* JADX INFO: loaded from: classes14.dex */
public class IntConstant extends Constant {
    int value;
    private static final IntConstant MIN_VALUE = new IntConstant(Integer.MIN_VALUE);
    private static final IntConstant MINUS_FOUR = new IntConstant(-4);
    private static final IntConstant MINUS_THREE = new IntConstant(-3);
    private static final IntConstant MINUS_TWO = new IntConstant(-2);
    private static final IntConstant MINUS_ONE = new IntConstant(-1);
    private static final IntConstant ZERO = new IntConstant(0);
    private static final IntConstant ONE = new IntConstant(1);
    private static final IntConstant TWO = new IntConstant(2);
    private static final IntConstant THREE = new IntConstant(3);
    private static final IntConstant FOUR = new IntConstant(4);
    private static final IntConstant FIVE = new IntConstant(5);
    private static final IntConstant SIX = new IntConstant(6);
    private static final IntConstant SEVEN = new IntConstant(7);
    private static final IntConstant EIGHT = new IntConstant(8);
    private static final IntConstant NINE = new IntConstant(9);
    private static final IntConstant TEN = new IntConstant(10);

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public int typeID() {
        return 10;
    }

    public static Constant fromValue(int i) {
        if (i == Integer.MIN_VALUE) {
            return MIN_VALUE;
        }
        switch (i) {
            case -4:
                return MINUS_FOUR;
            case -3:
                return MINUS_THREE;
            case -2:
                return MINUS_TWO;
            case -1:
                return MINUS_ONE;
            case 0:
                return ZERO;
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            case 6:
                return SIX;
            case 7:
                return SEVEN;
            case 8:
                return EIGHT;
            case 9:
                return NINE;
            case 10:
                return TEN;
            default:
                return new IntConstant(i);
        }
    }

    private IntConstant(int i) {
        this.value = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public byte byteValue() {
        return (byte) this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public char charValue() {
        return (char) this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public double doubleValue() {
        return this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public float floatValue() {
        return this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public int intValue() {
        return this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public long longValue() {
        return this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public short shortValue() {
        return (short) this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String stringValue() {
        return String.valueOf(this.value);
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String toString() {
        return "(int)" + this.value;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((IntConstant) obj).value;
    }
}
