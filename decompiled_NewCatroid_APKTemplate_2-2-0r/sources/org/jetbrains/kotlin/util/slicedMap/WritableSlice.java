package org.jetbrains.kotlin.util.slicedMap;

/* JADX INFO: loaded from: classes6.dex */
public interface WritableSlice<K, V> extends ReadOnlySlice<K, V> {
    void afterPut(MutableSlicedMap mutableSlicedMap, K k, V v);

    boolean check(K k, V v);

    @Override // org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
    KeyWithSlice<K, V, WritableSlice<K, V>> getKey();

    RewritePolicy getRewritePolicy();

    boolean isCollective();
}
