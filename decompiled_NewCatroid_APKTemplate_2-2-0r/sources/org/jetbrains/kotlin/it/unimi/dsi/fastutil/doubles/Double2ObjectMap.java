package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

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
public interface Double2ObjectMap<V> extends Double2ObjectFunction<V>, Map<Double, V> {
    ObjectSet<Entry<V>> double2ObjectEntrySet();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    Set<Double> keySet();

    @Override // java.util.Map
    ObjectCollection<V> values();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    /* bridge */ /* synthetic */ default Object merge(Double d, Object obj, BiFunction biFunction) {
        return merge2(d, obj, (BiFunction<? super Object, ? super Object, ? extends Object>) biFunction);
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
    default ObjectSet<Map.Entry<Double, V>> entrySet() {
        return double2ObjectEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default V put(Double d, V v) {
        return (V) super.put(d, (Object) v);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        return (V) super.get(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction, java.util.Map
    @Deprecated
    default V remove(Object obj) {
        return (V) super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        return super.containsKey(obj);
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super Double, ? super V> biConsumer) {
        ObjectSet<Entry<V>> objectSetDouble2ObjectEntrySet = double2ObjectEntrySet();
        Consumer<? super Entry<V>> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Double2ObjectMap.Entry entry = (Double2ObjectMap.Entry) obj;
                biConsumer.accept(Double.valueOf(entry.getDoubleKey()), entry.getValue());
            }
        };
        if (objectSetDouble2ObjectEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetDouble2ObjectEntrySet).fastForEach(consumer);
        } else {
            objectSetDouble2ObjectEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    @Deprecated
    default V getOrDefault(Object obj, V v) {
        return (V) super.getOrDefault(obj, v);
    }

    @Override // java.util.Map
    @Deprecated
    default V putIfAbsent(Double d, V v) {
        return (V) super.putIfAbsent(d, (Object) v);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(Double d, V v, V v2) {
        return super.replace(d, (Object) v, (Object) v2);
    }

    @Override // java.util.Map
    @Deprecated
    default V replace(Double d, V v) {
        return (V) super.replace(d, (Object) v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V computeIfAbsent(Double d, Function<? super Double, ? extends V> function) {
        return (V) super.computeIfAbsent(d, function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V computeIfPresent(Double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
        return (V) super.computeIfPresent(d, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default V compute(Double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
        return (V) super.compute(d, biFunction);
    }

    @Deprecated
    /* JADX INFO: renamed from: merge, reason: avoid collision after fix types in other method */
    default V merge2(Double d, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return (V) super.merge(d, (Object) v, (BiFunction) biFunction);
    }

    public interface Entry<V> extends Map.Entry<Double, V> {
        double getDoubleKey();

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Double getKey() {
            return Double.valueOf(getDoubleKey());
        }
    }
}
