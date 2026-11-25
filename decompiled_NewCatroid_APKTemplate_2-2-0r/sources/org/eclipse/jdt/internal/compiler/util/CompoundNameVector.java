package org.eclipse.jdt.internal.compiler.util;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public final class CompoundNameVector {
    static int INITIAL_SIZE = 10;
    char[][][] elements;
    int maxSize;
    public int size;

    public CompoundNameVector() {
        int i = INITIAL_SIZE;
        this.maxSize = i;
        this.size = 0;
        this.elements = new char[i][][];
    }

    public void add(char[][] cArr) {
        int i = this.size;
        int i2 = this.maxSize;
        if (i == i2) {
            char[][][] cArr2 = this.elements;
            int i3 = i2 * 2;
            this.maxSize = i3;
            char[][][] cArr3 = new char[i3][][];
            this.elements = cArr3;
            System.arraycopy(cArr2, 0, cArr3, 0, i);
        }
        char[][][] cArr4 = this.elements;
        int i4 = this.size;
        this.size = i4 + 1;
        cArr4[i4] = cArr;
    }

    public void addAll(char[][][] cArr) {
        int i = this.size;
        if (cArr.length + i >= this.maxSize) {
            int length = cArr.length + i;
            this.maxSize = length;
            char[][][] cArr2 = this.elements;
            char[][][] cArr3 = new char[length][][];
            this.elements = cArr3;
            System.arraycopy(cArr2, 0, cArr3, 0, i);
        }
        System.arraycopy(cArr, 0, this.elements, this.size, cArr.length);
        this.size += cArr.length;
    }

    public boolean contains(char[][] cArr) {
        int i = this.size;
        do {
            i--;
            if (i < 0) {
                return false;
            }
        } while (!CharOperation.equals(cArr, this.elements[i]));
        return true;
    }

    public char[][] elementAt(int i) {
        return this.elements[i];
    }

    public char[][] remove(char[][] cArr) {
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i2 < 0) {
                return null;
            }
            char[][][] cArr2 = this.elements;
            if (cArr == cArr2[i2]) {
                int i3 = this.size - 1;
                this.size = i3;
                System.arraycopy(cArr2, i, cArr2, i2, i3 - i2);
                this.elements[this.size] = null;
                return cArr;
            }
            i = i2;
        }
    }

    public void removeAll() {
        int i = this.size;
        while (true) {
            i--;
            if (i >= 0) {
                this.elements[i] = null;
            } else {
                this.size = 0;
                return;
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.size; i++) {
            stringBuffer.append(CharOperation.toString(this.elements[i])).append("\n");
        }
        return stringBuffer.toString();
    }
}
