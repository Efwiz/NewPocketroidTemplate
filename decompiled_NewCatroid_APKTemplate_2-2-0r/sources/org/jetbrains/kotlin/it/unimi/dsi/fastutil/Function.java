package org.jetbrains.kotlin.it.unimi.dsi.fastutil;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Function<K, V> extends java.util.function.Function<K, V> {
    default boolean containsKey(Object obj) {
        return true;
    }

    V get(Object obj);

    default int size() {
        return -1;
    }

    @Override // java.util.function.Function
    default V apply(K k) {
        return get(k);
    }

    default void clear() {
        throw new UnsupportedOperationException();
    }
}
