package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface Reference2ObjectMap<K, V> extends Reference2ObjectFunction<K, V>, Map<K, V> {

    public interface Entry<K, V> extends Map.Entry<K, V> {
    }

    @Override // java.util.Map
    ReferenceSet<K> keySet();

    ObjectSet<Entry<K, V>> reference2ObjectEntrySet();

    @Override // java.util.Map
    ObjectCollection<V> values();

    public interface FastEntrySet<K, V> extends ObjectSet<Entry<K, V>> {
        ObjectIterator<Entry<K, V>> fastIterator();

        default void fastForEach(Consumer<? super Entry<K, V>> consumer) {
            forEach(consumer);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    default ObjectSet<Map.Entry<K, V>> entrySet() {
        return reference2ObjectEntrySet();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunction, java.util.Map
    default V put(K k, V v) {
        return (V) super.put(k, v);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunction, java.util.Map
    default V remove(Object obj) {
        return (V) super.remove(obj);
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        ObjectSet<Entry<K, V>> objectSetReference2ObjectEntrySet = reference2ObjectEntrySet();
        Consumer<? super Entry<K, V>> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry) obj;
                biConsumer.accept(entry.getKey(), entry.getValue());
            }
        };
        if (objectSetReference2ObjectEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetReference2ObjectEntrySet).fastForEach(consumer);
        } else {
            objectSetReference2ObjectEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    default V getOrDefault(Object obj, V v) {
        return (V) super.getOrDefault(obj, v);
    }
}
