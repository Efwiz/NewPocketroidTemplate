package org.eclipse.jdt.internal.compiler.codegen;

/* JADX INFO: loaded from: classes14.dex */
public class DoubleCache {
    private int elementSize;
    private double[] keyTable;
    private int[] valueTable;

    public DoubleCache() {
        this(13);
    }

    public DoubleCache(int i) {
        this.elementSize = 0;
        this.keyTable = new double[i];
        this.valueTable = new int[i];
    }

    public void clear() {
        int length = this.keyTable.length;
        while (true) {
            length--;
            if (length >= 0) {
                this.keyTable[length] = 0.0d;
                this.valueTable[length] = 0;
            } else {
                this.elementSize = 0;
                return;
            }
        }
    }

    public boolean containsKey(double d) {
        if (d == 0.0d) {
            int i = this.elementSize;
            for (int i2 = 0; i2 < i; i2++) {
                if (this.keyTable[i2] == 0.0d) {
                    long jDoubleToLongBits = Double.doubleToLongBits(d);
                    long jDoubleToLongBits2 = Double.doubleToLongBits(this.keyTable[i2]);
                    if (jDoubleToLongBits == Long.MIN_VALUE && jDoubleToLongBits2 == Long.MIN_VALUE) {
                        return true;
                    }
                    if (jDoubleToLongBits == 0 && jDoubleToLongBits2 == 0) {
                        return true;
                    }
                }
            }
        } else {
            int i3 = this.elementSize;
            for (int i4 = 0; i4 < i3; i4++) {
                if (this.keyTable[i4] == d) {
                    return true;
                }
            }
        }
        return false;
    }

    public int put(double d, int i) {
        int i2 = this.elementSize;
        double[] dArr = this.keyTable;
        if (i2 == dArr.length) {
            double[] dArr2 = new double[i2 * 2];
            this.keyTable = dArr2;
            System.arraycopy(dArr, 0, dArr2, 0, i2);
            int[] iArr = this.valueTable;
            int i3 = this.elementSize;
            int[] iArr2 = new int[i3 * 2];
            this.valueTable = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, i3);
        }
        double[] dArr3 = this.keyTable;
        int i4 = this.elementSize;
        dArr3[i4] = d;
        this.valueTable[i4] = i;
        this.elementSize = i4 + 1;
        return i;
    }

    public int putIfAbsent(double d, int i) {
        if (d == 0.0d) {
            int i2 = this.elementSize;
            for (int i3 = 0; i3 < i2; i3++) {
                if (this.keyTable[i3] == 0.0d) {
                    long jDoubleToLongBits = Double.doubleToLongBits(d);
                    long jDoubleToLongBits2 = Double.doubleToLongBits(this.keyTable[i3]);
                    if (jDoubleToLongBits == Long.MIN_VALUE && jDoubleToLongBits2 == Long.MIN_VALUE) {
                        return this.valueTable[i3];
                    }
                    if (jDoubleToLongBits == 0 && jDoubleToLongBits2 == 0) {
                        return this.valueTable[i3];
                    }
                }
            }
        } else {
            int i4 = this.elementSize;
            for (int i5 = 0; i5 < i4; i5++) {
                if (this.keyTable[i5] == d) {
                    return this.valueTable[i5];
                }
            }
        }
        int i6 = this.elementSize;
        double[] dArr = this.keyTable;
        if (i6 == dArr.length) {
            double[] dArr2 = new double[i6 * 2];
            this.keyTable = dArr2;
            System.arraycopy(dArr, 0, dArr2, 0, i6);
            int[] iArr = this.valueTable;
            int i7 = this.elementSize;
            int[] iArr2 = new int[i7 * 2];
            this.valueTable = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, i7);
        }
        double[] dArr3 = this.keyTable;
        int i8 = this.elementSize;
        dArr3[i8] = d;
        this.valueTable[i8] = i;
        this.elementSize = i8 + 1;
        return -i;
    }

    public String toString() {
        int i = this.elementSize;
        StringBuffer stringBuffer = new StringBuffer("{");
        for (int i2 = 0; i2 < i; i2++) {
            double d = this.keyTable[i2];
            if (d != 0.0d || (d == 0.0d && this.valueTable[i2] != 0)) {
                stringBuffer.append(d).append("->").append(this.valueTable[i2]);
            }
            if (i2 < i) {
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
