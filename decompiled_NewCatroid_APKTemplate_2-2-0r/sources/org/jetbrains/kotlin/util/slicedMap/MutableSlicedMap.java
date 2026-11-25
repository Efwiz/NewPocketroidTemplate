package org.jetbrains.kotlin.util.slicedMap;

import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;

/* JADX INFO: loaded from: classes6.dex */
public interface MutableSlicedMap extends SlicedMap {
    void clear();

    <K, V> ImmutableMap<K, V> getSliceContents(ReadOnlySlice<K, V> readOnlySlice);

    <K, V> void put(WritableSlice<K, V> writableSlice, K k, V v);
}
