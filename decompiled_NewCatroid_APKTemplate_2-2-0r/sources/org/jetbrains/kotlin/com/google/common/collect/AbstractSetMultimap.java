package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractSetMultimap<K, V> extends AbstractMapBasedMultimap<K, V> implements SetMultimap<K, V> {
    private static final long serialVersionUID = 7431625294878419160L;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap
    public abstract Set<V> createCollection();

    protected AbstractSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap
    public Set<V> createUnmodifiableEmptyCollection() {
        return Collections.emptySet();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap
    <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        return Collections.unmodifiableSet((Set) collection);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap
    Collection<V> wrapCollection(K k, Collection<V> collection) {
        return new AbstractMapBasedMultimap.WrappedSet(k, (Set) collection);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> get(K k) {
        return (Set) super.get((Object) k);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> removeAll(Object obj) {
        return (Set) super.removeAll(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues((Object) k, (Iterable) iterable);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean put(K k, V v) {
        return super.put(k, v);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
