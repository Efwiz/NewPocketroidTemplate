package com.artemis.utils;

import java.util.Arrays;

/* JADX INFO: loaded from: classes8.dex */
public class ShortBag {
    private short[] data;
    protected int size;

    public ShortBag() {
        this(64);
    }

    public ShortBag(int i) {
        this.size = 0;
        this.data = new short[i];
    }

    public boolean removeValue(short s) {
        int iIndexOf = indexOf(s);
        if (iIndexOf > -1) {
            remove(iIndexOf);
        }
        return iIndexOf > -1;
    }

    public int remove(int i) {
        short[] sArr = this.data;
        short s = sArr[i];
        int i2 = this.size - 1;
        this.size = i2;
        sArr[i] = sArr[i2];
        sArr[i2] = 0;
        return s;
    }

    public int indexOf(short s) {
        for (int i = 0; this.size > i; i++) {
            if (s == this.data[i]) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(short s) {
        for (int i = 0; this.size > i; i++) {
            if (s == this.data[i]) {
                return true;
            }
        }
        return false;
    }

    public short get(int i) {
        return this.data[i];
    }

    public int size() {
        return this.size;
    }

    public int getCapacity() {
        return this.data.length;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(short s) {
        if (this.size == this.data.length) {
            grow();
        }
        short[] sArr = this.data;
        int i = this.size;
        this.size = i + 1;
        sArr[i] = s;
    }

    public void addAll(ShortBag shortBag) {
        for (int i = 0; i < shortBag.size(); i++) {
            add(shortBag.get(i));
        }
    }

    public void unsafeSet(int i, short s) {
        this.data[i] = s;
    }

    public void set(int i, short s) {
        if (i >= this.data.length) {
            grow(((i * 7) / 4) + 1);
        }
        this.size = Math.max(this.size, i + 1);
        this.data[i] = s;
    }

    private void grow() {
        grow(((this.data.length * 7) / 4) + 1);
    }

    private void grow(int i) {
        short[] sArr = this.data;
        short[] sArr2 = new short[i];
        this.data = sArr2;
        System.arraycopy(sArr, 0, sArr2, 0, sArr.length);
    }

    public void ensureCapacity(int i) {
        if (i >= this.data.length) {
            grow(i + 1);
        }
    }

    public void clear() {
        int i = this.size;
        for (int i2 = 0; i > i2; i2++) {
            this.data[i2] = 0;
        }
        this.size = 0;
    }

    public short[] getData() {
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
        ShortBag shortBag = (ShortBag) obj;
        return this.size == shortBag.size() && Arrays.equals(this.data, shortBag.data);
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
            sb.append((int) this.data[i]);
        }
        sb.append(')');
        return sb.toString();
    }
}
