package com.reandroid.utils.collection;

import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class SingleIterator<T> implements Iterator<T> {
    private T mItem;

    public SingleIterator(T t) {
        this.mItem = t;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.mItem != null;
    }

    @Override // java.util.Iterator
    public T next() {
        T t = this.mItem;
        this.mItem = null;
        return t;
    }

    public static <T1> Iterator<T1> of(T1 t1) {
        if (t1 == null) {
            return EmptyIterator.of();
        }
        return new SingleIterator(t1);
    }
}
