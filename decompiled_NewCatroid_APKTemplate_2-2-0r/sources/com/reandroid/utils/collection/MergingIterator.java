package com.reandroid.utils.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes11.dex */
public class MergingIterator<T> implements Iterator<T> {
    private final Iterator<Iterator<T>> iteratorIterator;
    private Iterator<T> mCurrent;

    public MergingIterator(Iterator<Iterator<T>> it) {
        this.iteratorIterator = it;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        Iterator<T> current = getCurrent();
        return current != null && current.hasNext();
    }

    @Override // java.util.Iterator
    public T next() {
        Iterator<T> current = getCurrent();
        if (current == null) {
            throw new NoSuchElementException();
        }
        return current.next();
    }

    private Iterator<T> getCurrent() {
        Iterator<T> next = this.mCurrent;
        while (true) {
            if (next == null || !next.hasNext()) {
                if (!this.iteratorIterator.hasNext()) {
                    return null;
                }
                next = this.iteratorIterator.next();
            } else {
                this.mCurrent = next;
                return next;
            }
        }
    }
}
