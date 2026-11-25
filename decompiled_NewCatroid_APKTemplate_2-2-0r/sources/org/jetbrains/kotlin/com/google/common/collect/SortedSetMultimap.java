package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: loaded from: classes3.dex */
public interface SortedSetMultimap<K, V> extends SetMultimap<K, V> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.SetMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    Map<K, Collection<V>> asMap();

    @Override // org.jetbrains.kotlin.com.google.common.collect.SetMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    SortedSet<V> get(K k);

    @Override // org.jetbrains.kotlin.com.google.common.collect.SetMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    SortedSet<V> removeAll(Object obj);

    @Override // org.jetbrains.kotlin.com.google.common.collect.SetMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable);

    Comparator<? super V> valueComparator();
}
