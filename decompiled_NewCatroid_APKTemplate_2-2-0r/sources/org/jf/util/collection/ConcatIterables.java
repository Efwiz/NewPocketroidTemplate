package org.jf.util.collection;

import java.util.Iterator;

/* JADX INFO: loaded from: classes6.dex */
public class ConcatIterables<T> implements Iterable<T> {
    private final Iterable<? extends Iterable<? extends T>> iterables;

    public ConcatIterables(Iterable<? extends Iterable<? extends T>> iterable) {
        this.iterables = iterable;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        Iterable<? extends Iterable<? extends T>> iterable = this.iterables;
        if (iterable == null) {
            return EmptyIterator.of();
        }
        return new ConcatIterators(iterable.iterator(), false);
    }
}
