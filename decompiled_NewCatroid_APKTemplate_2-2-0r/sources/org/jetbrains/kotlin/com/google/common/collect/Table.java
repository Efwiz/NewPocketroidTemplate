package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes3.dex */
@DoNotMock("Use ImmutableTable, HashBasedTable, or another implementation")
public interface Table<R, C, V> {
    Set<Object<R, C, V>> cellSet();

    void clear();

    Map<R, V> column(C c);

    Set<C> columnKeySet();

    Map<C, Map<R, V>> columnMap();

    boolean contains(Object obj, Object obj2);

    boolean containsColumn(Object obj);

    boolean containsRow(Object obj);

    boolean containsValue(Object obj);

    boolean equals(Object obj);

    V get(Object obj, Object obj2);

    int hashCode();

    boolean isEmpty();

    V put(R r, C c, V v);

    void putAll(Table<? extends R, ? extends C, ? extends V> table);

    V remove(Object obj, Object obj2);

    Map<C, V> row(R r);

    Set<R> rowKeySet();

    Map<R, Map<C, V>> rowMap();

    int size();

    Collection<V> values();
}
