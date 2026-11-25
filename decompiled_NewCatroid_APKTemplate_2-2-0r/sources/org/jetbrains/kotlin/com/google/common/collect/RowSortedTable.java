package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

/* JADX INFO: loaded from: classes3.dex */
public interface RowSortedTable<R, C, V> extends Table<R, C, V> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.Table
    SortedSet<R> rowKeySet();

    @Override // org.jetbrains.kotlin.com.google.common.collect.Table
    SortedMap<R, Map<C, V>> rowMap();
}
