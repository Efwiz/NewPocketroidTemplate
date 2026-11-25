package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.kotlin.com.google.common.base.Preconditions;
import org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes3.dex */
@DoNotMock("Use ImmutableMultimap, HashMultimap, or another implementation")
public interface Multimap<K, V> {
    Map<K, Collection<V>> asMap();

    void clear();

    boolean containsEntry(Object obj, Object obj2);

    boolean containsKey(Object obj);

    boolean containsValue(Object obj);

    Collection<Map.Entry<K, V>> entries();

    boolean equals(Object obj);

    Collection<V> get(K k);

    int hashCode();

    boolean isEmpty();

    Set<K> keySet();

    Multiset<K> keys();

    boolean put(K k, V v);

    boolean putAll(K k, Iterable<? extends V> iterable);

    boolean putAll(Multimap<? extends K, ? extends V> multimap);

    boolean remove(Object obj, Object obj2);

    Collection<V> removeAll(Object obj);

    Collection<V> replaceValues(K k, Iterable<? extends V> iterable);

    int size();

    Collection<V> values();

    default void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        entries().forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.google.common.collect.Multimap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                biConsumer.accept(entry.getKey(), entry.getValue());
            }
        });
    }
}
