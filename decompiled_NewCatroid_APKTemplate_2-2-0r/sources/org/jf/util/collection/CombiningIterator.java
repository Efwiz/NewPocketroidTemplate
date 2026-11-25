package org.jf.util.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes6.dex */
public class CombiningIterator<T> implements Iterator<T> {
    private final Iterable<? extends T> iterable2;
    private final Iterator<? extends T> iterator1;
    private final Iterator<? extends T> iterator2;
    private boolean mFirstFinished;
    private Iterator<? extends T> mSecond;

    public CombiningIterator(Iterator<? extends T> it, Iterator<? extends T> it2) {
        this.iterator1 = it;
        this.iterator2 = it2;
        this.iterable2 = null;
    }

    public CombiningIterator(Iterator<? extends T> it, Iterable<? extends T> iterable) {
        this.iterator1 = it;
        this.iterator2 = null;
        this.iterable2 = iterable;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.mFirstFinished) {
            Iterator<? extends T> it = this.iterator1;
            if (it != null && it.hasNext()) {
                return true;
            }
            this.mFirstFinished = true;
        }
        Iterator<? extends T> second = getSecond();
        return second != null && second.hasNext();
    }

    @Override // java.util.Iterator
    public T next() {
        Iterator<? extends T> second;
        if (this.mFirstFinished) {
            second = getSecond();
        } else {
            second = this.iterator1;
        }
        if (second == null) {
            throw new NoSuchElementException();
        }
        return second.next();
    }

    private Iterator<? extends T> getSecond() {
        Iterator<? extends T> it = this.mSecond;
        if (it != null) {
            return it;
        }
        Iterator<? extends T> itOf = this.iterator2;
        if (itOf == null) {
            Iterable<? extends T> iterable = this.iterable2;
            if (iterable != null) {
                itOf = iterable.iterator();
            } else {
                itOf = EmptyIterator.of();
            }
        }
        this.mSecond = itOf;
        return itOf;
    }
}
