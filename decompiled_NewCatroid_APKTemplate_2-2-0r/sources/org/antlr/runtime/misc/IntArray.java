package org.antlr.runtime.misc;

/* JADX INFO: loaded from: classes12.dex */
public class IntArray {
    public static final int INITIAL_SIZE = 10;
    public int[] data;
    protected int p = -1;

    public void add(int i) {
        ensureCapacity(this.p + 1);
        int[] iArr = this.data;
        int i2 = this.p + 1;
        this.p = i2;
        iArr[i2] = i;
    }

    public void push(int i) {
        add(i);
    }

    public int pop() {
        int[] iArr = this.data;
        int i = this.p;
        int i2 = iArr[i];
        this.p = i - 1;
        return i2;
    }

    public int size() {
        return this.p;
    }

    public void clear() {
        this.p = -1;
    }

    public void ensureCapacity(int i) {
        int[] iArr = this.data;
        if (iArr == null) {
            this.data = new int[10];
            return;
        }
        int i2 = i + 1;
        if (i2 >= iArr.length) {
            int length = iArr.length * 2;
            if (i <= length) {
                i2 = length;
            }
            int[] iArr2 = new int[i2];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.data = iArr2;
        }
    }
}
