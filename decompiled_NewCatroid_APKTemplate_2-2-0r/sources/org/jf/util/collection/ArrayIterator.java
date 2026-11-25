package org.jf.util.collection;

import java.util.Iterator;

/* JADX INFO: loaded from: classes6.dex */
public class ArrayIterator<T> implements Iterator<T> {
    private final T[] elements;
    private int index;

    public ArrayIterator(T[] tArr) {
        this.elements = tArr;
    }

    T get(int i) {
        return this.elements[i];
    }

    int getIndex() {
        return this.index;
    }

    int setIndex(int i) {
        this.index = i;
        return i;
    }

    public int length() {
        T[] tArr = this.elements;
        if (tArr != null) {
            return tArr.length;
        }
        return 0;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.index < length();
    }

    @Override // java.util.Iterator
    public T next() {
        T[] tArr = this.elements;
        int i = this.index;
        T t = tArr[i];
        this.index = i + 1;
        return t;
    }
}
