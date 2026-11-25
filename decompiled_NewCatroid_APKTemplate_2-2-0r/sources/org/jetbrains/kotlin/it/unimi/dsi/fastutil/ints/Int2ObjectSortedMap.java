package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSortedSet;

/* JADX INFO: loaded from: classes5.dex */
public interface Int2ObjectSortedMap<V> extends Int2ObjectMap<V>, SortedMap<Integer, V> {

    public interface FastSortedEntrySet<V> extends Int2ObjectMap.FastEntrySet<V>, ObjectSortedSet<Int2ObjectMap.Entry<V>> {
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap.FastEntrySet
        ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> fastIterator();
    }

    @Override // java.util.SortedMap
    Comparator<? super Integer> comparator();

    int firstIntKey();

    Int2ObjectSortedMap<V> headMap(int i);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap
    ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
    Set<Integer> keySet();

    int lastIntKey();

    Int2ObjectSortedMap<V> subMap(int i, int i2);

    Int2ObjectSortedMap<V> tailMap(int i);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
    ObjectCollection<V> values();

    @Override // java.util.SortedMap
    @Deprecated
    default Int2ObjectSortedMap<V> subMap(Integer num, Integer num2) {
        return subMap(num.intValue(), num2.intValue());
    }

    @Override // java.util.SortedMap
    @Deprecated
    default Int2ObjectSortedMap<V> headMap(Integer num) {
        return headMap(num.intValue());
    }

    @Override // java.util.SortedMap
    @Deprecated
    default Int2ObjectSortedMap<V> tailMap(Integer num) {
        return tailMap(num.intValue());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.SortedMap
    @Deprecated
    default Integer firstKey() {
        return Integer.valueOf(firstIntKey());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.SortedMap
    @Deprecated
    default Integer lastKey() {
        return Integer.valueOf(lastIntKey());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
    @Deprecated
    default ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
        return int2ObjectEntrySet();
    }
}
