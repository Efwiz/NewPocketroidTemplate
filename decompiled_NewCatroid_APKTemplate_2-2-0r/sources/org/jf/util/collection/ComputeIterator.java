package org.jf.util.collection;

import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: loaded from: classes6.dex */
public class ComputeIterator<E, T> implements Iterator<T> {
    private final Function<? super E, ? extends T> function;
    private final Iterator<? extends E> iterator;

    public ComputeIterator(Iterator<? extends E> it, Function<? super E, ? extends T> function) {
        this.iterator = it;
        this.function = function;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public T next() {
        return this.function.apply(this.iterator.next());
    }
}
