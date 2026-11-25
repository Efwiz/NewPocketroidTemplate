package com.reandroid.utils.collection;

import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class ExpandIterator<T> implements Iterator<T> {
    private final Iterator<? extends Iterable<? extends T>> iterator;
    private Iterator<? extends T> mCurrent;

    public ExpandIterator(Iterator<? extends Iterable<? extends T>> it) {
        this.iterator = it;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return getCurrent() != null;
    }

    @Override // java.util.Iterator
    public T next() {
        return this.mCurrent.next();
    }

    private Iterator<? extends T> getCurrent() {
        Iterator<? extends T> it = this.mCurrent;
        if (it == null || !it.hasNext()) {
            this.mCurrent = null;
            while (this.iterator.hasNext()) {
                Iterable<? extends T> next = this.iterator.next();
                if (next != null) {
                    Iterator<? extends T> it2 = next.iterator();
                    if (it2.hasNext()) {
                        this.mCurrent = it2;
                        break;
                    }
                }
            }
        }
        return this.mCurrent;
    }

    public static <T1> Iterator<T1> of(Iterator<? extends Iterable<? extends T1>> it) {
        if (!it.hasNext()) {
            return EmptyIterator.of();
        }
        return new ExpandIterator(it);
    }
}
