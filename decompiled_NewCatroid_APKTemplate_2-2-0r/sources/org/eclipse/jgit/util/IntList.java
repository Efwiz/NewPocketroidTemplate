package org.eclipse.jgit.util;

/* JADX INFO: loaded from: classes14.dex */
public class IntList {
    private int count;
    private int[] entries;

    public IntList() {
        this(10);
    }

    public IntList(int i) {
        this.entries = new int[i];
    }

    public int size() {
        return this.count;
    }

    public boolean contains(int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            if (this.entries[i2] == i) {
                return true;
            }
        }
        return false;
    }

    public int get(int i) {
        if (this.count <= i) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.entries[i];
    }

    public void clear() {
        this.count = 0;
    }

    public void add(int i) {
        if (this.count == this.entries.length) {
            grow();
        }
        int[] iArr = this.entries;
        int i2 = this.count;
        this.count = i2 + 1;
        iArr[i2] = i;
    }

    public void set(int i, int i2) {
        int i3 = this.count;
        if (i3 < i) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i3 == i) {
            add(i2);
        } else {
            this.entries[i] = i2;
        }
    }

    public void fillTo(int i, int i2) {
        while (this.count < i) {
            add(i2);
        }
    }

    private void grow() {
        int[] iArr = this.entries;
        int[] iArr2 = new int[((iArr.length + 16) * 3) / 2];
        System.arraycopy(iArr, 0, iArr2, 0, this.count);
        this.entries = iArr2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.count; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(this.entries[i]);
        }
        sb.append(']');
        return sb.toString();
    }
}
