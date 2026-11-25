package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collections;
import java.util.Spliterator;
import org.jetbrains.kotlin.com.google.common.base.Preconditions;

/* JADX INFO: loaded from: classes3.dex */
final class SingletonImmutableList<E> extends ImmutableList<E> {
    final transient E element;

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return 1;
    }

    SingletonImmutableList(E e) {
        this.element = (E) Preconditions.checkNotNull(e);
    }

    @Override // java.util.List
    public E get(int i) {
        Preconditions.checkElementIndex(i, 1);
        return this.element;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, org.jetbrains.kotlin.com.google.common.collect.SortedIterable, java.util.NavigableSet
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
    public Spliterator<E> spliterator() {
        return Collections.singleton(this.element).spliterator();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, java.util.List
    public ImmutableList<E> subList(int i, int i2) {
        Preconditions.checkPositionIndexes(i, i2, 1);
        return i == i2 ? ImmutableList.of() : this;
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return "[" + this.element.toString() + ']';
    }
}
