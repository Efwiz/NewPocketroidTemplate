package org.jetbrains.kotlin.com.google.common.collect;

/* JADX INFO: loaded from: classes3.dex */
interface FilteredSetMultimap<K, V> extends FilteredMultimap<K, V>, SetMultimap<K, V> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredMultimap
    SetMultimap<K, V> unfiltered();
}
