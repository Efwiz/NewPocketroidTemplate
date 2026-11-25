package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSet;

/* JADX INFO: loaded from: classes5.dex */
public interface Long2ObjectMap<V> extends Long2ObjectFunction<V>, Map<Long, V> {
    @Override // java.util.Map
    Set<Long> keySet();

    ObjectSet<Entry<V>> long2ObjectEntrySet();

    @Override // java.util.Map
    ObjectCollection<V> values();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    /* bridge */ /* synthetic */ default Object merge(Long l, Object obj, BiFunction biFunction) {
        return merge2(l, obj, (BiFunction<? super Object, ? super Object, ? extends Object>) biFunction);
    }

    public interface FastEntrySet<V> extends ObjectSet<Entry<V>> {
        default void fastForEach(Consumer<? super Entry<V>> consumer) {
            forEach(consumer);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    default ObjectSet<Map.Entry<Long, V>> entrySet() {
        return long2ObjectEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default V put(Long l, V v) {
        return (V) super.put(l, (Object) v);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.Long2ObjectFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        return (V) super.get(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.Long2ObjectFunction, java.util.Map
    @Deprecated
    default V remove(Object obj) {
        return (V) super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.Long2ObjectFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        return super.containsKey(obj);
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super Long, ? super V> biConsumer) {
        ObjectSet<Entry<V>> objectSetLong2ObjectEntrySet = long2ObjectEntrySet();
        Consumer<? super Entry<V>> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.Long2ObjectMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Long2ObjectMap.Entry entry = (Long2ObjectMap.Entry) obj;
                biConsumer.accept(Long.valueOf(entry.getLongKey()), entry.getValue());
            }
        };
        if (objectSetLong2ObjectEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetLong2ObjectEntrySet).fastForEach(consumer);
        } else {
            objectSetLong2ObjectEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    @Deprecated
    default V getOrDefault(Object obj, V v) {
        return (V) super.getOrDefault(obj, v);
    }

    @Override // java.util.Map
    @Deprecated
    default V putIfAbsent(Long l, V v) {
        return (V) super.putIfAbsent(l, (Object) v);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(Long l, V v, V v2) {
        return super.replace(l, (Object) v, (Object) v2);
    }

    @Override // java.util.Map
    @Deprecated
    default V replace(Long l, V v) {
        return (V) super.replace(l, (Object) v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V computeIfAbsent(Long l, Function<? super Long, ? extends V> function) {
        return (V) super.computeIfAbsent(l, function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V computeIfPresent(Long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
        return (V) super.computeIfPresent(l, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V compute(Long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
        return (V) super.compute(l, biFunction);
    }

    @Deprecated
    /* JADX INFO: renamed from: merge, reason: avoid collision after fix types in other method */
    default V merge2(Long l, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return (V) super.merge(l, (Object) v, (BiFunction) biFunction);
    }

    public interface Entry<V> extends Map.Entry<Long, V> {
        long getLongKey();

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Long getKey() {
            return Long.valueOf(getLongKey());
        }
    }
}
