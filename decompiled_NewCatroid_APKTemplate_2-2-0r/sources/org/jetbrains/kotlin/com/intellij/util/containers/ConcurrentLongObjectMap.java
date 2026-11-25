package org.jetbrains.kotlin.com.intellij.util.containers;

/* JADX INFO: loaded from: classes3.dex */
public interface ConcurrentLongObjectMap<V> {

    public interface LongEntry<V> {
        long getKey();

        V getValue();
    }

    Iterable<LongEntry<V>> entries();

    V get(long j);

    V put(long j, V v);

    V putIfAbsent(long j, V v);

    V remove(long j);
}
