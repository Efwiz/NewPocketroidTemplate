package com.reandroid.utils.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class InstanceIterator<T> implements Iterator<T> {
    private final Predicate<? super T> filter;
    private final Class<T> instance;
    private final Iterator<?> iterator;
    private T mCurrent;

    public InstanceIterator(Iterator<?> it, Class<T> cls, Predicate<? super T> predicate) {
        this.iterator = it;
        this.instance = cls;
        this.filter = predicate;
    }

    public InstanceIterator(Iterator<?> it, Class<T> cls) {
        this(it, cls, null);
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [T, java.lang.Object] */
    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.mCurrent != null) {
            return true;
        }
        Iterator<?> it = this.iterator;
        Class<T> cls = this.instance;
        Predicate<? super T> predicate = this.filter;
        while (it.hasNext()) {
            ?? r4 = (T) it.next();
            if (r4 != 0 && cls.isInstance(r4) && (predicate == null || predicate.test(r4))) {
                this.mCurrent = r4;
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Iterator
    public T next() {
        T t = this.mCurrent;
        if (t == null) {
            throw new NoSuchElementException();
        }
        this.mCurrent = null;
        return t;
    }

    public static <T1> Iterator<T1> of(Iterator<?> it, Class<T1> cls) {
        if (!it.hasNext()) {
            return EmptyIterator.of();
        }
        return new InstanceIterator(it, cls);
    }

    public static <T1> Iterator<T1> of(Iterator<?> it, Class<T1> cls, Predicate<? super T1> predicate) {
        if (!it.hasNext()) {
            return EmptyIterator.of();
        }
        return new InstanceIterator(it, cls, predicate);
    }
}
