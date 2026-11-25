package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractListMultimap<K, V> extends AbstractMapBasedMultimap<K, V> implements ListMultimap<K, V> {
    private static final long serialVersionUID = 6588350623831699109L;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap
    public abstract List<V> createCollection();

    protected AbstractListMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap
    public List<V> createUnmodifiableEmptyCollection() {
        return Collections.emptyList();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap
    <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        return Collections.unmodifiableList((List) collection);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap
    Collection<V> wrapCollection(K k, Collection<V> collection) {
        return wrapList(k, (List) collection, null);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public List<V> get(K k) {
        return (List) super.get((Object) k);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public List<V> removeAll(Object obj) {
        return (List) super.removeAll(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (List) super.replaceValues((Object) k, (Iterable) iterable);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMapBasedMultimap, org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean put(K k, V v) {
        return super.put(k, v);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractMultimap, org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
