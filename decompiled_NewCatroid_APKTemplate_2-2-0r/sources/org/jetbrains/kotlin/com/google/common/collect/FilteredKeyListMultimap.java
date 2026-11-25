package org.jetbrains.kotlin.com.google.common.collect;

import java.util.List;
import org.jetbrains.kotlin.com.google.common.base.Predicate;

/* JADX INFO: loaded from: classes3.dex */
final class FilteredKeyListMultimap<K, V> extends FilteredKeyMultimap<K, V> implements ListMultimap<K, V> {
    FilteredKeyListMultimap(ListMultimap<K, V> listMultimap, Predicate<? super K> predicate) {
        super(listMultimap, predicate);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredKeyMultimap, org.jetbrains.kotlin.com.google.common.collect.FilteredMultimap
    public ListMultimap<K, V> unfiltered() {
        return (ListMultimap) super.unfiltered();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredKeyMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public List<V> get(K k) {
        return (List) super.get((Object) k);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredKeyMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public List<V> removeAll(Object obj) {
        return (List) super.removeAll(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (List) super.replaceValues((Object) k, (Iterable) iterable);
    }
}
