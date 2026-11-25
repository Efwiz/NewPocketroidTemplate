package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SingletonIteratorBase<T> implements Iterator<T> {
    private boolean myVisited;

    protected abstract void checkCoModification();

    protected abstract T getElement();

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return !this.myVisited;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (this.myVisited) {
            throw new NoSuchElementException();
        }
        this.myVisited = true;
        checkCoModification();
        return getElement();
    }
}
