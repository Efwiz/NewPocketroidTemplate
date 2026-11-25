package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class EmptyIterator<T> implements Iterator<T> {
    private static final EmptyIterator INSTANCE = new EmptyIterator();

    @Override // java.util.Iterator
    public boolean hasNext() {
        return false;
    }

    public static <T> EmptyIterator<T> getInstance() {
        return INSTANCE;
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
