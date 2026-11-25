package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public interface SetMultimap<K, V> extends Multimap<K, V> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    Map<K, Collection<V>> asMap();

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    Set<Map.Entry<K, V>> entries();

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    boolean equals(Object obj);

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    Set<V> get(K k);

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    Set<V> removeAll(Object obj);

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    Set<V> replaceValues(K k, Iterable<? extends V> iterable);
}
