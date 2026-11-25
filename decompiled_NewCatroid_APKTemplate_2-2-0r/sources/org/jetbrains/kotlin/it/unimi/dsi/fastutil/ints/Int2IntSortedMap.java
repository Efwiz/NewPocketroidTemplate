package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSortedSet;

/* JADX INFO: loaded from: classes5.dex */
public interface Int2IntSortedMap extends Int2IntMap, SortedMap<Integer, Integer> {

    public interface FastSortedEntrySet extends Int2IntMap.FastEntrySet, ObjectSortedSet<Int2IntMap.Entry> {
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.FastEntrySet
        ObjectBidirectionalIterator<Int2IntMap.Entry> fastIterator();
    }

    @Override // java.util.SortedMap
    Comparator<? super Integer> comparator();

    int firstIntKey();

    Int2IntSortedMap headMap(int i);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap
    ObjectSortedSet<Int2IntMap.Entry> int2IntEntrySet();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
    Set<Integer> keySet();

    int lastIntKey();

    Int2IntSortedMap subMap(int i, int i2);

    Int2IntSortedMap tailMap(int i);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
    Collection<Integer> values();

    @Override // java.util.SortedMap
    @Deprecated
    default Int2IntSortedMap subMap(Integer num, Integer num2) {
        return subMap(num.intValue(), num2.intValue());
    }

    @Override // java.util.SortedMap
    @Deprecated
    default Int2IntSortedMap headMap(Integer num) {
        return headMap(num.intValue());
    }

    @Override // java.util.SortedMap
    @Deprecated
    default Int2IntSortedMap tailMap(Integer num) {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
    @Deprecated
    default Set<Map.Entry<Integer, Integer>> entrySet() {
        return int2IntEntrySet();
    }
}
