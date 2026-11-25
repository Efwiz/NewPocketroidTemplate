package org.jf.util.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes6.dex */
public class FilterIterator<E, T extends E> implements Iterator<T> {
    private final Predicate<E> filter;
    private final Iterator<? extends T> iterator;
    private T mNext;
    private boolean mNextComputed;

    public FilterIterator(Iterator<? extends T> it, Predicate<E> predicate) {
        this.iterator = it;
        this.filter = predicate;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        getNext();
        return this.mNextComputed;
    }

    @Override // java.util.Iterator
    public T next() {
        T t = (T) getNext();
        if (!this.mNextComputed) {
            throw new NoSuchElementException();
        }
        this.mNextComputed = false;
        this.mNext = null;
        return t;
    }

    private T getNext() {
        if (!this.mNextComputed) {
            while (this.iterator.hasNext()) {
                T next = this.iterator.next();
                if (testAll(next)) {
                    this.mNext = next;
                    this.mNextComputed = true;
                    break;
                }
            }
        }
        return this.mNext;
    }

    private boolean testAll(T t) {
        Predicate<E> predicate = this.filter;
        if (predicate == null) {
            return true;
        }
        return predicate.test(t);
    }
}
