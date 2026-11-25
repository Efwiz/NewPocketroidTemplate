package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.com.google.common.base.Predicate;

/* JADX INFO: loaded from: classes3.dex */
final class FilteredKeySetMultimap<K, V> extends FilteredKeyMultimap<K, V> implements FilteredSetMultimap<K, V> {
    FilteredKeySetMultimap(SetMultimap<K, V> setMultimap, Predicate<? super K> predicate) {
        super(setMultimap, predicate);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredKeyMultimap, org.jetbrains.kotlin.com.google.common.collect.FilteredMultimap
    public SetMultimap<K, V> unfiltered() {
        return (SetMultimap) this.unfiltered;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredKeyMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> get(K k) {
        return (Set) super.get((Object) k);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredKeyMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> removeAll(Object obj) {
        return (Set) super.removeAll(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues((Object) k, (Iterable) iterable);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.FilteredKeyMultimap, org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap
    public Set<Map.Entry<K, V>> createEntries() {
        return new EntrySet();
    }

    class EntrySet extends FilteredKeyMultimap<K, V>.Entries implements Set<Map.Entry<K, V>> {
        EntrySet() {
            super();
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(Object obj) {
            return Sets.equalsImpl(this, obj);
        }
    }
}
