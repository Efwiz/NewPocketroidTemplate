package io.github.rosemoe.sora.util;

/* JADX INFO: loaded from: classes11.dex */
public class LongArrayList {
    private long[] data = new long[64];
    private int length;

    public void add(long j) {
        long[] jArr = this.data;
        int i = this.length;
        int i2 = i + 1;
        this.length = i2;
        jArr[i] = j;
        if (jArr.length == i2) {
            long[] jArr2 = new long[i2 << 1];
            System.arraycopy(jArr, 0, jArr2, 0, i2);
            this.data = jArr2;
        }
    }

    public int size() {
        return this.length;
    }

    public void set(int i, long j) {
        if (i >= this.length || i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.data[i] = j;
    }

    public int lowerBoundByFirst(int i) {
        int i2 = this.length - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            long first = IntPair.getFirst(this.data[i4]);
            long j = i;
            if (first < j) {
                i3 = i4 + 1;
            } else {
                if (first <= j) {
                    return i4;
                }
                i2 = i4 - 1;
            }
        }
        return i3;
    }

    public int lowerBound(long j) {
        int i = this.length - 1;
        int i2 = 0;
        while (i2 <= i) {
            int i3 = (i2 + i) >>> 1;
            long j2 = this.data[i3];
            if (j2 < j) {
                i2 = i3 + 1;
            } else {
                if (j2 <= j) {
                    return i3;
                }
                i = i3 - 1;
            }
        }
        return i2;
    }

    public long get(int i) {
        if (i >= this.length || i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.data[i];
    }

    public void clear() {
        this.length = 0;
    }
}
