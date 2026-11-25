package org.eclipse.jdt.internal.compiler.impl;

/* JADX INFO: loaded from: classes14.dex */
public class ShortConstant extends Constant {
    private short value;

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public int typeID() {
        return 4;
    }

    public static Constant fromValue(short s) {
        return new ShortConstant(s);
    }

    private ShortConstant(short s) {
        this.value = s;
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
        return this.value;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String stringValue() {
        return String.valueOf((int) this.value);
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.Constant
    public String toString() {
        return "(short)" + ((int) this.value);
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((ShortConstant) obj).value;
    }
}
