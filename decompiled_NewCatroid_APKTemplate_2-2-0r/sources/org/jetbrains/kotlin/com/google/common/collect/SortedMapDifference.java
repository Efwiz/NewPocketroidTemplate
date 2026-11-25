package org.jetbrains.kotlin.com.google.common.collect;

import java.util.SortedMap;

/* JADX INFO: loaded from: classes3.dex */
public interface SortedMapDifference<K, V> extends MapDifference<K, V> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.MapDifference
    SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering();

    @Override // org.jetbrains.kotlin.com.google.common.collect.MapDifference
    SortedMap<K, V> entriesInCommon();

    @Override // org.jetbrains.kotlin.com.google.common.collect.MapDifference
    SortedMap<K, V> entriesOnlyOnLeft();

    @Override // org.jetbrains.kotlin.com.google.common.collect.MapDifference
    SortedMap<K, V> entriesOnlyOnRight();
}
