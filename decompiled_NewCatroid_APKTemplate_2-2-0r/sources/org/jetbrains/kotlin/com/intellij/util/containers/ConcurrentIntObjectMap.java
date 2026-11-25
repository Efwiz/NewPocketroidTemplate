package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Enumeration;

/* JADX INFO: loaded from: classes3.dex */
public interface ConcurrentIntObjectMap<V> extends IntObjectMap<V> {
    V cacheOrGet(int i, V v);

    Enumeration<V> elements();

    V putIfAbsent(int i, V v);

    boolean remove(int i, V v);

    boolean replace(int i, V v, V v2);

    int size();
}
