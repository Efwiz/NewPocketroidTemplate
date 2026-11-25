package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Map;
import java.util.SortedMap;

/* JADX INFO: loaded from: classes5.dex */
public interface Reference2ObjectSortedMap<K, V> extends Reference2ObjectMap<K, V>, SortedMap<K, V> {

    public interface FastSortedEntrySet<K, V> extends ObjectSortedSet<Reference2ObjectMap.Entry<K, V>>, Reference2ObjectMap.FastEntrySet<K, V> {
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap.FastEntrySet
        ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> fastIterator();
    }

    @Override // java.util.SortedMap
    Reference2ObjectSortedMap<K, V> headMap(K k);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
    ReferenceSortedSet<K> keySet();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap
    ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet();

    @Override // java.util.SortedMap
    Reference2ObjectSortedMap<K, V> subMap(K k, K k2);

    @Override // java.util.SortedMap
    Reference2ObjectSortedMap<K, V> tailMap(K k);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
    ObjectCollection<V> values();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
    default ObjectSortedSet<Map.Entry<K, V>> entrySet() {
        return reference2ObjectEntrySet();
    }
}
