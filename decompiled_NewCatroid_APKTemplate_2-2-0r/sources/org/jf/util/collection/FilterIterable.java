package org.jf.util.collection;

import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes6.dex */
public class FilterIterable<E, T extends E> implements Iterable<T> {
    private final Predicate<E> filter;
    private final Iterable<? extends T> iterable;

    public FilterIterable(Iterable<? extends T> iterable, Predicate<E> predicate) {
        this.iterable = iterable;
        this.filter = predicate;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new FilterIterator(this.iterable.iterator(), this.filter);
    }
}
