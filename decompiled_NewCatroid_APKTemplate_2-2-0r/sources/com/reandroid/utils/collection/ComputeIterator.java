package com.reandroid.utils.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class ComputeIterator<E, T> implements Iterator<T> {
    private final Predicate<T> filter;
    private final Function<? super E, T> function;
    private final Iterator<? extends E> iterator;
    private T mNext;

    public ComputeIterator(Iterator<? extends E> it, Function<? super E, T> function, Predicate<T> predicate) {
        this.iterator = it;
        this.function = function;
        this.filter = predicate;
    }

    public ComputeIterator(Iterator<? extends E> it, Function<? super E, T> function) {
        this(it, function, null);
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
        Predicate<T> predicate;
        if (this.mNext == null) {
            while (this.iterator.hasNext()) {
                T tApply = this.function.apply(this.iterator.next());
                if (tApply != null && ((predicate = this.filter) == null || predicate.test(tApply))) {
                    this.mNext = tApply;
                    break;
                }
            }
        }
        return this.mNext;
    }

    public static <E1, T1> Iterator<T1> of(Iterator<? extends E1> it, Function<? super E1, T1> function) {
        if (!it.hasNext()) {
            return EmptyIterator.of();
        }
        return new ComputeIterator(it, function);
    }
}
