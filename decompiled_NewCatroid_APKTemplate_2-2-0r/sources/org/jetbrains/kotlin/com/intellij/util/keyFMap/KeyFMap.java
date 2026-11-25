package org.jetbrains.kotlin.com.intellij.util.keyFMap;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
public interface KeyFMap {
    public static final KeyFMap EMPTY_MAP = EmptyFMap.create();

    <V> V get(Key<V> key);

    Key[] getKeys();

    boolean isEmpty();

    KeyFMap minus(Key<?> key);

    <V> KeyFMap plus(Key<V> key, V v);

    int size();
}
