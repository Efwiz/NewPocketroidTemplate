package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSet;

/* JADX INFO: loaded from: classes5.dex */
public interface Int2ObjectMap<V> extends Int2ObjectFunction<V>, Map<Integer, V> {
    ObjectSet<Entry<V>> int2ObjectEntrySet();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    Set<Integer> keySet();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    int size();

    @Override // java.util.Map
    ObjectCollection<V> values();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    /* bridge */ /* synthetic */ default Object merge(Integer num, Object obj, BiFunction biFunction) {
        return merge2(num, obj, (BiFunction<? super Object, ? super Object, ? extends Object>) biFunction);
    }

    public interface FastEntrySet<V> extends ObjectSet<Entry<V>> {
        ObjectIterator<Entry<V>> fastIterator();

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
    default ObjectSet<Map.Entry<Integer, V>> entrySet() {
        return int2ObjectEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default V put(Integer num, V v) {
        return (V) super.put(num, (Object) v);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        return (V) super.get(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
    @Deprecated
    default V remove(Object obj) {
        return (V) super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        return super.containsKey(obj);
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super Integer, ? super V> biConsumer) {
        ObjectSet<Entry<V>> objectSetInt2ObjectEntrySet = int2ObjectEntrySet();
        Consumer<? super Entry<V>> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry) obj;
                biConsumer.accept(Integer.valueOf(entry.getIntKey()), entry.getValue());
            }
        };
        if (objectSetInt2ObjectEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetInt2ObjectEntrySet).fastForEach(consumer);
        } else {
            objectSetInt2ObjectEntrySet.forEach(consumer);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
    @Deprecated
    default V getOrDefault(Object obj, V v) {
        return (V) super.getOrDefault(obj, v);
    }

    @Override // java.util.Map
    @Deprecated
    default V putIfAbsent(Integer num, V v) {
        return (V) super.putIfAbsent(num, (Object) v);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(Integer num, V v, V v2) {
        return super.replace(num, (Object) v, (Object) v2);
    }

    @Override // java.util.Map
    @Deprecated
    default V replace(Integer num, V v) {
        return (V) super.replace(num, (Object) v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V computeIfAbsent(Integer num, Function<? super Integer, ? extends V> function) {
        return (V) super.computeIfAbsent(num, function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V computeIfPresent(Integer num, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        return (V) super.computeIfPresent(num, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V compute(Integer num, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        return (V) super.compute(num, biFunction);
    }

    @Deprecated
    /* JADX INFO: renamed from: merge, reason: avoid collision after fix types in other method */
    default V merge2(Integer num, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return (V) super.merge(num, (Object) v, (BiFunction) biFunction);
    }

    public interface Entry<V> extends Map.Entry<Integer, V> {
        int getIntKey();

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Integer getKey() {
            return Integer.valueOf(getIntKey());
        }
    }
}
