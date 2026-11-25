package org.eclipse.jdt.internal.compiler.impl;

/* JADX INFO: loaded from: classes14.dex */
public class LongConstant extends Constant {
    private long value;
    private static final LongConstant ZERO = new LongConstant(0);
    private static final LongConstant MIN_VALUE = new LongConstant(Long.MIN_VALUE);

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public int typeID() {
        return 7;
    }

    public static Constant fromValue(long j) {
        if (j == 0) {
            return ZERO;
        }
        if (j == Long.MIN_VALUE) {
            return MIN_VALUE;
        }
        return new LongConstant(j);
    }

    private LongConstant(long j) {
        this.value = j;
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
        return (int) this.value;
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
        return "(long)" + this.value;
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((LongConstant) obj).value;
    }
}
