package com.artemis.utils;

import java.util.Arrays;

/* JADX INFO: loaded from: classes8.dex */
public class IntDeque {
    private int beginIndex;
    private int[] elements;
    protected int size;

    public IntDeque() {
        this(64);
    }

    public IntDeque(int i) {
        this.size = 0;
        this.elements = new int[i];
    }

    public boolean contains(int i) {
        for (int i2 = 0; this.size > i2; i2++) {
            if (i == this.elements[index(i2)]) {
                return true;
            }
        }
        return false;
    }

    public int get(int i) {
        return this.elements[index(i)];
    }

    public int size() {
        return this.size;
    }

    public int getCapacity() {
        return this.elements.length;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(int i) {
        int i2 = this.size;
        int[] iArr = this.elements;
        if (i2 == iArr.length) {
            grow(((iArr.length * 7) / 4) + 1);
        }
        int[] iArr2 = this.elements;
        int i3 = this.size;
        this.size = i3 + 1;
        iArr2[index(i3)] = i;
    }

    private int index(int i) {
        return (this.beginIndex + i) % this.elements.length;
    }

    public void set(int i, int i2) {
        if (i >= this.elements.length) {
            grow(((i * 7) / 4) + 1);
        }
        this.size = Math.max(this.size, i + 1);
        this.elements[index(i)] = i2;
    }

    private void grow(int i) {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < this.size; i2++) {
            iArr[i2] = get(i2);
        }
        this.elements = iArr;
        this.beginIndex = 0;
    }

    public void ensureCapacity(int i) {
        if (i >= this.elements.length) {
            grow(i);
        }
    }

    public void clear() {
        Arrays.fill(this.elements, 0, this.size, 0);
        this.size = 0;
        this.beginIndex = 0;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public int popLast() {
        assertNotEmpty();
        int i = this.size - 1;
        this.size = i;
        return this.elements[index(i)];
    }

    public int popFirst() {
        assertNotEmpty();
        int[] iArr = this.elements;
        int i = this.beginIndex;
        int i2 = iArr[i];
        this.beginIndex = (i + 1) % iArr.length;
        this.size--;
        return i2;
    }

    private void assertNotEmpty() {
        if (this.size == 0) {
            throw new RuntimeException("Deque is empty.");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IntDeque intDeque = (IntDeque) obj;
        if (this.size != intDeque.size) {
            return false;
        }
        for (int i = 0; this.size > i; i++) {
            if (get(i) != intDeque.get(i)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = this.size;
        int i2 = 0;
        for (int i3 = 0; i > i3; i3++) {
            i2 = (i2 * 127) + this.elements[i3];
        }
        return i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IntDeque(");
        for (int i = 0; this.size > i; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(this.elements[index(i)]);
        }
        sb.append(')');
        return sb.toString();
    }
}
