package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ForwardingMultimap<K, V> extends ForwardingObject implements Multimap<K, V> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingObject
    public abstract Multimap<K, V> delegate();

    protected ForwardingMultimap() {
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        return delegate().asMap();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public void clear() {
        delegate().clear();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean containsEntry(Object obj, Object obj2) {
        return delegate().containsEntry(obj, obj2);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean containsKey(Object obj) {
        return delegate().containsKey(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean containsValue(Object obj) {
        return delegate().containsValue(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public Collection<Map.Entry<K, V>> entries() {
        return delegate().entries();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Collection<V> get(K k) {
        return delegate().get(k);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public Multiset<K> keys() {
        return delegate().keys();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public Set<K> keySet() {
        return delegate().keySet();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean put(K k, V v) {
        return delegate().put(k, v);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean putAll(K k, Iterable<? extends V> iterable) {
        return delegate().putAll(k, iterable);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        return delegate().putAll(multimap);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public boolean remove(Object obj, Object obj2) {
        return delegate().remove(obj, obj2);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Collection<V> removeAll(Object obj) {
        return delegate().removeAll(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return delegate().replaceValues(k, iterable);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public int size() {
        return delegate().size();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public Collection<V> values() {
        return delegate().values();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap, org.jetbrains.kotlin.com.google.common.collect.ListMultimap
    public boolean equals(Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multimap
    public int hashCode() {
        return delegate().hashCode();
    }
}
