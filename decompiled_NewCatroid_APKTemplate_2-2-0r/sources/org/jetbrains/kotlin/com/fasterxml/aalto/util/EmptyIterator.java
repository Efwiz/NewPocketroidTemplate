package org.jetbrains.kotlin.com.fasterxml.aalto.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
public final class EmptyIterator<T> implements Iterator<T> {
    static final EmptyIterator<Object> sInstance = new EmptyIterator<>();

    @Override // java.util.Iterator
    public boolean hasNext() {
        return false;
    }

    private EmptyIterator() {
    }

    public static <T> EmptyIterator<T> getInstance() {
        return (EmptyIterator<T>) sInstance;
    }

    @Override // java.util.Iterator
    public T next() {
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new IllegalStateException();
    }
}
