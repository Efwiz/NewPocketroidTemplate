package org.jetbrains.kotlin.com.google.common.collect;

import java.io.Serializable;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.kotlin.com.google.common.base.Preconditions;

/* JADX INFO: loaded from: classes3.dex */
final class ImmutableMapKeySet<K, V> extends IndexedImmutableSet<K> {
    private final ImmutableMap<K, V> map;

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return true;
    }

    ImmutableMapKeySet(ImmutableMap<K, V> immutableMap) {
        this.map = immutableMap;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.map.size();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.IndexedImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, org.jetbrains.kotlin.com.google.common.collect.SortedIterable, java.util.NavigableSet
    public UnmodifiableIterator<K> iterator() {
        return this.map.keyIterator();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.IndexedImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
    public Spliterator<K> spliterator() {
        return this.map.keySpliterator();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.IndexedImmutableSet
    K get(int i) {
        return this.map.entrySet().asList().get(i).getKey();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.IndexedImmutableSet, java.lang.Iterable
    public void forEach(final Consumer<? super K> consumer) {
        Preconditions.checkNotNull(consumer);
        this.map.forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.com.google.common.collect.ImmutableMapKeySet$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept(obj);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new KeySetSerializedForm(this.map);
    }

    private static class KeySetSerializedForm<K> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<K, ?> map;

        KeySetSerializedForm(ImmutableMap<K, ?> immutableMap) {
            this.map = immutableMap;
        }

        Object readResolve() {
            return this.map.keySet();
        }
    }
}
