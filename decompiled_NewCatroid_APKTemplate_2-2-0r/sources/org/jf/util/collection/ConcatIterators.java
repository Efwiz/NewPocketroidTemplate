package org.jf.util.collection;

import java.util.Iterator;

/* JADX INFO: loaded from: classes6.dex */
public class ConcatIterators<T> implements Iterator<T> {
    private final Iterator<? extends Iterable<? extends T>> iterables;
    private final Iterator<? extends Iterator<? extends T>> iterators;
    private Iterator<? extends T> mCurrentIterator;

    public ConcatIterators(Iterator<? extends Iterator<? extends T>> it) {
        this.iterators = it;
        this.iterables = null;
    }

    public ConcatIterators(Iterator<? extends Iterable<? extends T>> it, boolean z) {
        this.iterators = null;
        this.iterables = it;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        Iterator<? extends T> current = getCurrent();
        return current != null && current.hasNext();
    }

    @Override // java.util.Iterator
    public T next() {
        return getCurrent().next();
    }

    private Iterator<? extends T> getCurrent() {
        if (this.iterators != null) {
            return getCurrentIterator();
        }
        return getCurrentIterables();
    }

    private Iterator<? extends T> getCurrentIterator() {
        Iterator<? extends T> next;
        Iterator<? extends T> it = this.mCurrentIterator;
        if (it != null && it.hasNext()) {
            return it;
        }
        this.mCurrentIterator = null;
        Iterator<? extends Iterator<? extends T>> it2 = this.iterators;
        if (it2 == null || !it2.hasNext()) {
            return null;
        }
        loop0: while (true) {
            next = null;
            while (true) {
                if (next != null || !this.iterators.hasNext()) {
                    break loop0;
                }
                next = this.iterators.next();
                if (next == null || next.hasNext()) {
                }
            }
        }
        this.mCurrentIterator = next;
        return next;
    }

    private Iterator<? extends T> getCurrentIterables() {
        Iterator<? extends T> it;
        Iterator<? extends T> it2 = this.mCurrentIterator;
        if (it2 != null && it2.hasNext()) {
            return it2;
        }
        this.mCurrentIterator = null;
        Iterator<? extends Iterable<? extends T>> it3 = this.iterables;
        if (it3 == null || !it3.hasNext()) {
            return null;
        }
        loop0: while (true) {
            it = null;
            while (true) {
                if (it != null || !this.iterables.hasNext()) {
                    break loop0;
                }
                Iterable<? extends T> next = this.iterables.next();
                if (next != null) {
                    it = next.iterator();
                }
                if (it == null || it.hasNext()) {
                }
            }
        }
        this.mCurrentIterator = it;
        return it;
    }
}
