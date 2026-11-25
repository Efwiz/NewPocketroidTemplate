package com.artemis.utils;

import java.util.Arrays;

/* JADX INFO: loaded from: classes8.dex */
public class IntBag implements ImmutableIntBag {
    private int[] data;
    protected int size;

    public IntBag() {
        this(64);
    }

    public IntBag(int i) {
        this.size = 0;
        this.data = new int[i];
    }

    public boolean removeValue(int i) {
        int iIndexOf = indexOf(i);
        if (iIndexOf > -1) {
            removeIndex(iIndexOf);
        }
        return iIndexOf > -1;
    }

    @Deprecated
    public int remove(int i) {
        int[] iArr = this.data;
        int i2 = iArr[i];
        int i3 = this.size - 1;
        this.size = i3;
        iArr[i] = iArr[i3];
        iArr[i3] = 0;
        return i2;
    }

    public int removeIndex(int i) {
        int[] iArr = this.data;
        int i2 = iArr[i];
        int i3 = this.size - 1;
        this.size = i3;
        iArr[i] = iArr[i3];
        iArr[i3] = 0;
        return i2;
    }

    public int indexOf(int i) {
        for (int i2 = 0; this.size > i2; i2++) {
            if (i == this.data[i2]) {
                return i2;
            }
        }
        return -1;
    }

    @Override // com.artemis.utils.ImmutableIntBag
    public boolean contains(int i) {
        for (int i2 = 0; this.size > i2; i2++) {
            if (i == this.data[i2]) {
                return true;
            }
        }
        return false;
    }

    @Override // com.artemis.utils.ImmutableIntBag
    public int get(int i) {
        if (i >= this.size) {
            throw new ArrayIndexOutOfBoundsException("tried accessing element " + i + "/" + this.size);
        }
        return this.data[i];
    }

    @Override // com.artemis.utils.ImmutableIntBag
    public int size() {
        return this.size;
    }

    public int getCapacity() {
        return this.data.length;
    }

    public boolean isIndexWithinBounds(int i) {
        return i < getCapacity();
    }

    @Override // com.artemis.utils.ImmutableIntBag
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(int i) {
        int i2 = this.size;
        int[] iArr = this.data;
        if (i2 == iArr.length) {
            grow(iArr.length * 2);
        }
        int[] iArr2 = this.data;
        int i3 = this.size;
        this.size = i3 + 1;
        iArr2[i3] = i;
    }

    public void addAll(IntBag intBag) {
        for (int i = 0; i < intBag.size(); i++) {
            add(intBag.data[i]);
        }
    }

    public void set(int i, int i2) {
        int[] iArr = this.data;
        if (i >= iArr.length) {
            grow(Math.max(iArr.length * 2, i + 1));
        }
        this.size = Math.max(this.size, i + 1);
        this.data[i] = i2;
    }

    private void grow(int i) {
        int[] iArr = this.data;
        int[] iArr2 = new int[i];
        this.data = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
    }

    public void ensureCapacity(int i) {
        if (i >= this.data.length) {
            grow(i + 1);
        }
    }

    public void clear() {
        Arrays.fill(this.data, 0, this.size, 0);
        this.size = 0;
    }

    public int[] getData() {
        return this.data;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IntBag intBag = (IntBag) obj;
        if (this.size != intBag.size()) {
            return false;
        }
        for (int i = 0; this.size > i; i++) {
            if (this.data[i] != intBag.data[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = this.size;
        int i2 = 0;
        for (int i3 = 0; i > i3; i3++) {
            i2 = (i2 * 127) + this.data[i3];
        }
        return i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IntBag(");
        for (int i = 0; this.size > i; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(this.data[i]);
        }
        sb.append(')');
        return sb.toString();
    }
}
