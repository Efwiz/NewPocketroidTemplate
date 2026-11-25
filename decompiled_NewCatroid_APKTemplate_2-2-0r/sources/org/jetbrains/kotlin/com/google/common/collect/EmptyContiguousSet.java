package org.jetbrains.kotlin.com.google.common.collect;

import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import java.util.Set;
import okhttp3.HttpUrl;

/* JADX INFO: loaded from: classes3.dex */
final class EmptyContiguousSet<C extends Comparable> extends ContiguousSet<C> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.ContiguousSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet
    public ContiguousSet<C> headSetImpl(C c, boolean z) {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet
    int indexOf(Object obj) {
        return -1;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ContiguousSet
    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        return this;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet
    boolean isHashCodeFast() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.ContiguousSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet
    public ContiguousSet<C> subSetImpl(C c, boolean z, C c2, boolean z2) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.ContiguousSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet
    public ContiguousSet<C> tailSetImpl(C c, boolean z) {
        return this;
    }

    EmptyContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    public C first() {
        throw new NoSuchElementException();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    public C last() {
        throw new NoSuchElementException();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ContiguousSet
    public Range<C> range() {
        throw new NoSuchElementException();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ContiguousSet
    public Range<C> range(BoundType boundType, BoundType boundType2) {
        throw new NoSuchElementException();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, org.jetbrains.kotlin.com.google.common.collect.SortedIterable, java.util.NavigableSet
    public UnmodifiableIterator<C> iterator() {
        return Iterators.emptyIterator();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public UnmodifiableIterator<C> descendingIterator() {
        return Iterators.emptyIterator();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    public ImmutableList<C> asList() {
        return ImmutableList.of();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ContiguousSet, java.util.AbstractCollection
    public String toString() {
        return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (obj instanceof Set) {
            return ((Set) obj).isEmpty();
        }
        return false;
    }

    private static final class SerializedForm<C extends Comparable> implements Serializable {
        private static final long serialVersionUID = 0;
        private final DiscreteDomain<C> domain;

        private SerializedForm(DiscreteDomain<C> discreteDomain) {
            this.domain = discreteDomain;
        }

        private Object readResolve() {
            return new EmptyContiguousSet(this.domain);
        }
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new SerializedForm(this.domain);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ContiguousSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<C> createDescendingSet() {
        return ImmutableSortedSet.emptySet(Ordering.natural().reverse());
    }
}
