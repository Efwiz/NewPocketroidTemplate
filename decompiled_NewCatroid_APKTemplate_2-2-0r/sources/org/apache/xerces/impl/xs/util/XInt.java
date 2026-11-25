package org.apache.xerces.impl.xs.util;

/* JADX INFO: loaded from: classes13.dex */
public final class XInt {
    private final int fValue;

    XInt(int i) {
        this.fValue = i;
    }

    public final boolean equals(XInt xInt) {
        return this.fValue == xInt.fValue;
    }

    public final int intValue() {
        return this.fValue;
    }

    public final short shortValue() {
        return (short) this.fValue;
    }

    public String toString() {
        return Integer.toString(this.fValue);
    }
}
