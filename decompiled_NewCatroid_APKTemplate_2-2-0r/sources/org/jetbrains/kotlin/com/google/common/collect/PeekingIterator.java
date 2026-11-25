package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Iterator;
import org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes3.dex */
@DoNotMock("Use Iterators.peekingIterator")
public interface PeekingIterator<E> extends Iterator<E> {
    @Override // java.util.Iterator
    E next();

    E peek();

    @Override // java.util.Iterator
    void remove();
}
