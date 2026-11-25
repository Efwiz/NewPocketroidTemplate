package org.jetbrains.kotlin.util.slicedMap;

/* JADX INFO: loaded from: classes6.dex */
public interface ReadOnlySlice<K, V> {
    V computeValue(SlicedMap slicedMap, K k, V v, boolean z);

    KeyWithSlice<K, V, ? extends ReadOnlySlice<K, V>> getKey();

    ReadOnlySlice<K, V> makeRawValueVersion();
}
