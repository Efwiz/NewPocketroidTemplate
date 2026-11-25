package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
final class EmptyListIterator<E> extends EmptyIterator<E> implements ListIterator<E> {
    private static final EmptyListIterator<Object> INSTANCE = new EmptyListIterator<>();

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        return false;
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return 0;
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return -1;
    }

    private EmptyListIterator() {
    }

    public static <E> EmptyListIterator<E> getInstance() {
        return (EmptyListIterator<E>) INSTANCE;
    }

    @Override // java.util.ListIterator
    public E previous() {
        throw new NoSuchElementException();
    }

    @Override // java.util.ListIterator
    public void set(E e) {
        throw new IllegalStateException();
    }

    @Override // java.util.ListIterator
    public void add(E e) {
        throw new UnsupportedOperationException();
    }
}
