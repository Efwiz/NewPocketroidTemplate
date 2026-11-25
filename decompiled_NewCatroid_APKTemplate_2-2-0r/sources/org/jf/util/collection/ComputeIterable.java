package org.jf.util.collection;

import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: loaded from: classes6.dex */
public class ComputeIterable<E, T> implements Iterable<T> {
    private final Function<? super E, ? extends T> function;
    private final Iterable<? extends E> iterable;

    public ComputeIterable(Iterable<? extends E> iterable, Function<? super E, ? extends T> function) {
        this.iterable = iterable;
        this.function = function;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new ComputeIterator(this.iterable.iterator(), this.function);
    }
}
