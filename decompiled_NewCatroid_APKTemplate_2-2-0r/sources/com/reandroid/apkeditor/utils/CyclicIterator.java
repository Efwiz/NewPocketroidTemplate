package com.reandroid.apkeditor.utils;

import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class CyclicIterator<T> implements Iterator<T> {
    private int count;
    private int cycleCount;
    private final T[] elements;
    private int mIndex;

    public CyclicIterator(T[] tArr) {
        if (tArr == null || tArr.length == 0) {
            throw new IllegalArgumentException("Elements can not be empty");
        }
        this.elements = tArr;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.elements.length != 0;
    }

    @Override // java.util.Iterator
    public T next() {
        int i = this.mIndex;
        T[] tArr = this.elements;
        T t = tArr[i];
        int i2 = i + 1;
        int length = tArr.length;
        if (i2 >= length) {
            i2 = 0;
        }
        this.mIndex = i2;
        int i3 = this.count + 1;
        this.count = i3;
        if (i3 >= length) {
            this.count = 0;
            this.cycleCount++;
        }
        return t;
    }

    public int length() {
        return this.elements.length;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getCycleCount() {
        return this.cycleCount;
    }

    public void resetCycleCount() {
        this.cycleCount = 0;
        this.count = 0;
    }

    public String toString() {
        return "[cycle = " + getCycleCount() + ": " + getIndex() + "/" + length() + "] " + this.elements[this.mIndex];
    }
}
