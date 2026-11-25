package org.jf.util.collection;

import java.util.Iterator;

/* JADX INFO: loaded from: classes6.dex */
public class CombiningIterable<T> implements Iterable<T> {
    private final Iterable<? extends T> iterable1;
    private final Iterable<? extends T> iterable2;

    public CombiningIterable(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        this.iterable1 = iterable;
        this.iterable2 = iterable2;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        Iterable<? extends T> iterable = this.iterable1;
        return new CombiningIterator(iterable != null ? iterable.iterator() : null, this.iterable2);
    }
}
