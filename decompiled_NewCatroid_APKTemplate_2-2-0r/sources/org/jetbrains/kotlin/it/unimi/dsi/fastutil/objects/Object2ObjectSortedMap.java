package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Map;
import java.util.SortedMap;

/* JADX INFO: loaded from: classes5.dex */
public interface Object2ObjectSortedMap<K, V> extends Object2ObjectMap<K, V>, SortedMap<K, V> {

    public interface FastSortedEntrySet<K, V> extends Object2ObjectMap.FastEntrySet<K, V>, ObjectSortedSet<Object2ObjectMap.Entry<K, V>> {
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap.FastEntrySet
        ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> fastIterator();
    }

    @Override // java.util.SortedMap
    Object2ObjectSortedMap<K, V> headMap(K k);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap, java.util.Map
    ObjectSortedSet<K> keySet();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap
    ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet();

    @Override // java.util.SortedMap
    Object2ObjectSortedMap<K, V> subMap(K k, K k2);

    @Override // java.util.SortedMap
    Object2ObjectSortedMap<K, V> tailMap(K k);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap, java.util.Map
    ObjectCollection<V> values();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap, java.util.Map
    default ObjectSortedSet<Map.Entry<K, V>> entrySet() {
        return object2ObjectEntrySet();
    }
}
