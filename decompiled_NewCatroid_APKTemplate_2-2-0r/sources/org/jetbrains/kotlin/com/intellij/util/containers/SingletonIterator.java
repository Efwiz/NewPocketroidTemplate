package org.jetbrains.kotlin.com.intellij.util.containers;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class SingletonIterator<T> extends SingletonIteratorBase<T> {
    private final T myElement;

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.SingletonIteratorBase
    protected void checkCoModification() {
    }

    public SingletonIterator(T t) {
        this.myElement = t;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.SingletonIteratorBase
    protected T getElement() {
        return this.myElement;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new IncorrectOperationException();
    }
}
