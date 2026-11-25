package org.eclipse.jdt.internal.compiler.impl;

/* JADX INFO: loaded from: classes14.dex */
public class ByteConstant extends Constant {
    private byte value;

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public int typeID() {
        return 3;
    }

    public static Constant fromValue(byte b) {
        return new ByteConstant(b);
    }

    private ByteConstant(byte b) {
        this.value = b;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public byte byteValue() {
        return this.value;
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
        return this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String stringValue() {
        return String.valueOf((int) this.value);
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String toString() {
        return "(byte)" + ((int) this.value);
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((ByteConstant) obj).value;
    }
}
