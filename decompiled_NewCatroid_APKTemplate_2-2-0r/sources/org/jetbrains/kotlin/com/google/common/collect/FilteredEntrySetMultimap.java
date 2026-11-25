package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.com.google.common.base.Predicate;

/* JADX INFO: loaded from: classes3.dex */
final class FilteredEntrySetMultimap<K, V> extends FilteredEntryMultimap<K, V> implements FilteredSetMultimap<K, V> {
    FilteredEntrySetMultimap(SetMultimap<K, V> setMultimap, Predicate<? super Map.Entry<K, V>> predicate) {
        super(setMultimap, predicate);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredEntryMultimap, org.jetbrains.kotlin.com.google.common.collect.FilteredMultimap
    public SetMultimap<K, V> unfiltered() {
        return (SetMultimap) this.unfiltered;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredEntryMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> get(K k) {
        return (Set) super.get((Object) k);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredEntryMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> removeAll(Object obj) {
        return (Set) super.removeAll(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues((Object) k, (Iterable) iterable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredEntryMultimap, org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap
    public Set<Map.Entry<K, V>> createEntries() {
        return Sets.filter(unfiltered().entries(), entryPredicate());
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }
}
