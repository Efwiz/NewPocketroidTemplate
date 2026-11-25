package org.jf.util.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes6.dex */
public abstract class AbstractIterator<T> implements Iterator<T> {
    private boolean mFinished;
    private T mNext;

    public abstract T computeNext();

    public final T endOfData() {
        this.mFinished = true;
        return null;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return getNext() != null;
    }

    @Override // java.util.Iterator
    public T next() {
        T next = getNext();
        if (next == null) {
            throw new NoSuchElementException();
        }
        this.mNext = null;
        return next;
    }

    private T getNext() {
        T t = this.mNext;
        if (t != null || this.mFinished) {
            return t;
        }
        T tComputeNext = computeNext();
        this.mNext = tComputeNext;
        return tComputeNext;
    }
}
