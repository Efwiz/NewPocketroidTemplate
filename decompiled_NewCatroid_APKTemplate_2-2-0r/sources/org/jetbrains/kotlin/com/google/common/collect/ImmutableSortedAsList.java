package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Comparator;
import java.util.Spliterator;

/* JADX INFO: loaded from: classes3.dex */
final class ImmutableSortedAsList<E> extends RegularImmutableAsList<E> implements SortedIterable<E> {
    ImmutableSortedAsList(ImmutableSortedSet<E> immutableSortedSet, ImmutableList<E> immutableList) {
        super(immutableSortedSet, immutableList);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.RegularImmutableAsList, org.jetbrains.kotlin.com.google.common.collect.ImmutableAsList
    public ImmutableSortedSet<E> delegateCollection() {
        return (ImmutableSortedSet) super.delegateCollection();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.SortedIterable
    public Comparator<? super E> comparator() {
        return delegateCollection().comparator();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, java.util.List
    public int indexOf(Object obj) {
        int iIndexOf = delegateCollection().indexOf(obj);
        if (iIndexOf < 0 || !get(iIndexOf).equals(obj)) {
            return -1;
        }
        return iIndexOf;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, java.util.List
    public int lastIndexOf(Object obj) {
        return indexOf(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableAsList, org.jetbrains.kotlin.com.google.common.collect.ImmutableList, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList
    ImmutableList<E> subListUnchecked(int i, int i2) {
        return new RegularImmutableSortedSet(super.subListUnchecked(i, i2), comparator()).asList();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
    public Spliterator<E> spliterator() {
        int size = size();
        ImmutableList<? extends E> immutableListDelegateList = delegateList();
        immutableListDelegateList.getClass();
        return CollectSpliterators.indexed(size, 1301, new ImmutableList$$ExternalSyntheticLambda0(immutableListDelegateList), comparator());
    }
}
