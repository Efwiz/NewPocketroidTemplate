package org.eclipse.jdt.internal.compiler.impl;

/* JADX INFO: loaded from: classes14.dex */
public class CharConstant extends Constant {
    private char value;

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public int typeID() {
        return 2;
    }

    public static Constant fromValue(char c) {
        return new CharConstant(c);
    }

    private CharConstant(char c) {
        this.value = c;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public byte byteValue() {
        return (byte) this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public char charValue() {
        return this.value;
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
        return "(char)" + this.value;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((CharConstant) obj).value;
    }
}
