package org.jf.util.collection;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes6.dex */
public class ArrayListIterator<T> extends ArrayIterator<T> implements ListIterator<T> {
    @Override // java.util.ListIterator
    public void add(T t) {
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public void remove() {
    }

    @Override // java.util.ListIterator
    public void set(T t) {
    }

    public ArrayListIterator(T[] tArr) {
        super(tArr);
    }

    public ArrayListIterator(T[] tArr, int i) {
        this(tArr);
        setIndex(i);
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        return getIndex() > 0;
    }

    @Override // java.util.ListIterator
    public T previous() {
        int index = getIndex() - 1;
        T t = get(index);
        setIndex(index);
        return t;
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return setIndex(getIndex() + 1);
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return setIndex(getIndex() - 1);
    }
}
