package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public interface SortedMultiset<E> extends SortedIterable<E>, SortedMultisetBridge<E> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.SortedIterable
    Comparator<? super E> comparator();

    SortedMultiset<E> descendingMultiset();

    @Override // org.jetbrains.kotlin.com.google.common.collect.SortedMultisetBridge, org.jetbrains.kotlin.com.google.common.collect.Multiset
    NavigableSet<E> elementSet();

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    Set<Multiset.Entry<E>> entrySet();

    Multiset.Entry<E> firstEntry();

    SortedMultiset<E> headMultiset(E e, BoundType boundType);

    @Override // org.jetbrains.kotlin.com.google.common.collect.SortedIterable, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    Iterator<E> iterator();

    Multiset.Entry<E> lastEntry();

    Multiset.Entry<E> pollFirstEntry();

    Multiset.Entry<E> pollLastEntry();

    SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2);

    SortedMultiset<E> tailMultiset(E e, BoundType boundType);
}
