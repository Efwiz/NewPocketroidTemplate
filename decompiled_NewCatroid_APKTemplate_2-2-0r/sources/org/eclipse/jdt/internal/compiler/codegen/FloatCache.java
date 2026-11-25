package org.eclipse.jdt.internal.compiler.codegen;

/* JADX INFO: loaded from: classes14.dex */
public class FloatCache {
    private int elementSize;
    private float[] keyTable;
    private int[] valueTable;

    public FloatCache() {
        this(13);
    }

    public FloatCache(int i) {
        this.elementSize = 0;
        this.keyTable = new float[i];
        this.valueTable = new int[i];
    }

    public void clear() {
        int length = this.keyTable.length;
        while (true) {
            length--;
            if (length >= 0) {
                this.keyTable[length] = 0.0f;
                this.valueTable[length] = 0;
            } else {
                this.elementSize = 0;
                return;
            }
        }
    }

    public boolean containsKey(float f) {
        if (f == 0.0f) {
            int i = this.elementSize;
            for (int i2 = 0; i2 < i; i2++) {
                if (this.keyTable[i2] == 0.0f) {
                    int iFloatToIntBits = Float.floatToIntBits(f);
                    int iFloatToIntBits2 = Float.floatToIntBits(this.keyTable[i2]);
                    if (iFloatToIntBits == Integer.MIN_VALUE && iFloatToIntBits2 == Integer.MIN_VALUE) {
                        return true;
                    }
                    if (iFloatToIntBits == 0 && iFloatToIntBits2 == 0) {
                        return true;
                    }
                }
            }
        } else {
            int i3 = this.elementSize;
            for (int i4 = 0; i4 < i3; i4++) {
                if (this.keyTable[i4] == f) {
                    return true;
                }
            }
        }
        return false;
    }

    public int put(float f, int i) {
        int i2 = this.elementSize;
        float[] fArr = this.keyTable;
        if (i2 == fArr.length) {
            float[] fArr2 = new float[i2 * 2];
            this.keyTable = fArr2;
            System.arraycopy(fArr, 0, fArr2, 0, i2);
            int[] iArr = this.valueTable;
            int i3 = this.elementSize;
            int[] iArr2 = new int[i3 * 2];
            this.valueTable = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, i3);
        }
        float[] fArr3 = this.keyTable;
        int i4 = this.elementSize;
        fArr3[i4] = f;
        this.valueTable[i4] = i;
        this.elementSize = i4 + 1;
        return i;
    }

    public int putIfAbsent(float f, int i) {
        if (f == 0.0f) {
            int i2 = this.elementSize;
            for (int i3 = 0; i3 < i2; i3++) {
                if (this.keyTable[i3] == 0.0f) {
                    int iFloatToIntBits = Float.floatToIntBits(f);
                    int iFloatToIntBits2 = Float.floatToIntBits(this.keyTable[i3]);
                    if (iFloatToIntBits == Integer.MIN_VALUE && iFloatToIntBits2 == Integer.MIN_VALUE) {
                        return this.valueTable[i3];
                    }
                    if (iFloatToIntBits == 0 && iFloatToIntBits2 == 0) {
                        return this.valueTable[i3];
                    }
                }
            }
        } else {
            int i4 = this.elementSize;
            for (int i5 = 0; i5 < i4; i5++) {
                if (this.keyTable[i5] == f) {
                    return this.valueTable[i5];
                }
            }
        }
        int i6 = this.elementSize;
        float[] fArr = this.keyTable;
        if (i6 == fArr.length) {
            float[] fArr2 = new float[i6 * 2];
            this.keyTable = fArr2;
            System.arraycopy(fArr, 0, fArr2, 0, i6);
            int[] iArr = this.valueTable;
            int i7 = this.elementSize;
            int[] iArr2 = new int[i7 * 2];
            this.valueTable = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, i7);
        }
        float[] fArr3 = this.keyTable;
        int i8 = this.elementSize;
        fArr3[i8] = f;
        this.valueTable[i8] = i;
        this.elementSize = i8 + 1;
        return -i;
    }

    public String toString() {
        int i = this.elementSize;
        StringBuffer stringBuffer = new StringBuffer("{");
        for (int i2 = 0; i2 < i; i2++) {
            float f = this.keyTable[i2];
            if (f != 0.0f || (f == 0.0f && this.valueTable[i2] != 0)) {
                stringBuffer.append(f).append("->").append(this.valueTable[i2]);
            }
            if (i2 < i) {
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
