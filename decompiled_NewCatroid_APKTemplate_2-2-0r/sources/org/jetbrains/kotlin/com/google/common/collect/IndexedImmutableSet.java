package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import org.jetbrains.kotlin.com.google.common.base.Preconditions;

/* JADX INFO: loaded from: classes3.dex */
abstract class IndexedImmutableSet<E> extends ImmutableSet<E> {
    abstract E get(int i);

    IndexedImmutableSet() {
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, org.jetbrains.kotlin.com.google.common.collect.SortedIterable, java.util.NavigableSet
    public UnmodifiableIterator<E> iterator() {
        return asList().iterator();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
    public Spliterator<E> spliterator() {
        return CollectSpliterators.indexed(size(), 1297, new IntFunction() { // from class: org.jetbrains.kotlin.com.google.common.collect.IndexedImmutableSet$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return this.f$0.get(i);
            }
        });
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super E> consumer) {
        Preconditions.checkNotNull(consumer);
        int size = size();
        for (int i = 0; i < size; i++) {
            consumer.accept(get(i));
        }
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    int copyIntoArray(Object[] objArr, int i) {
        return asList().copyIntoArray(objArr, i);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet
    ImmutableList<E> createAsList() {
        return new ImmutableAsList<E>() { // from class: org.jetbrains.kotlin.com.google.common.collect.IndexedImmutableSet.1
            @Override // java.util.List
            public E get(int i) {
                return (E) IndexedImmutableSet.this.get(i);
            }

            @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableAsList, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
            boolean isPartialView() {
                return IndexedImmutableSet.this.isPartialView();
            }

            @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableAsList, java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return IndexedImmutableSet.this.size();
            }

            @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableAsList
            ImmutableCollection<E> delegateCollection() {
                return IndexedImmutableSet.this;
            }
        };
    }
}
