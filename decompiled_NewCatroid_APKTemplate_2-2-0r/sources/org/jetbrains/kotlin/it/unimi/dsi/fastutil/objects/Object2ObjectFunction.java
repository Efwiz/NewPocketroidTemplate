package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Object2ObjectFunction<K, V> extends Function<K, V> {
    default V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    default V remove(Object obj) {
        throw new UnsupportedOperationException();
    }
}
