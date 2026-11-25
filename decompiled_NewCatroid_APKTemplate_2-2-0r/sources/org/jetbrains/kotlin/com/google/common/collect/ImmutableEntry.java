package org.jetbrains.kotlin.com.google.common.collect;

import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    final K key;
    final V value;

    ImmutableEntry(K k, V v) {
        this.key = k;
        this.value = v;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
    public final K getKey() {
        return this.key;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
    public final V getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}
