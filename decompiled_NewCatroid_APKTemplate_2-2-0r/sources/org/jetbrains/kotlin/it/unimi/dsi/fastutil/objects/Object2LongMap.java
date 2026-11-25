package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface Object2LongMap<K> extends Object2LongFunction<K>, Map<K, Long> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    boolean containsKey(Object obj);

    boolean containsValue(long j);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunction
    long defaultReturnValue();

    @Override // java.util.Map
    ObjectSet<K> keySet();

    ObjectSet<Entry<K>> object2LongEntrySet();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    int size();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    Collection<Long> values();

    public interface FastEntrySet<K> extends ObjectSet<Entry<K>> {
        ObjectIterator<Entry<K>> fastIterator();

        default void fastForEach(Consumer<? super Entry<K>> consumer) {
            forEach(consumer);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    default ObjectSet<Map.Entry<K, Long>> entrySet() {
        return object2LongEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Long put(K k, Long l) {
        return super.put((Object) k, l);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        return super.get(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Long remove(Object obj) {
        return super.remove(obj);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean containsValue(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsValue(((Long) obj).longValue());
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super K, ? super Long> biConsumer) {
        ObjectSet<Entry<K>> objectSetObject2LongEntrySet = object2LongEntrySet();
        Consumer<? super Entry<K>> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Object2LongMap.Entry entry = (Object2LongMap.Entry) obj;
                biConsumer.accept(entry.getKey(), Long.valueOf(entry.getLongValue()));
            }
        };
        if (objectSetObject2LongEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetObject2LongEntrySet).fastForEach(consumer);
        } else {
            objectSetObject2LongEntrySet.forEach(consumer);
        }
    }

    default long getOrDefault(Object obj, long j) {
        long j2 = getLong(obj);
        return (j2 != defaultReturnValue() || containsKey(obj)) ? j2 : j;
    }

    default long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long j = getLong(k);
        long jDefaultReturnValue = defaultReturnValue();
        boolean z = j != jDefaultReturnValue || containsKey(k);
        Long lApply = biFunction.apply(k, z ? Long.valueOf(j) : null);
        if (lApply == null) {
            if (z) {
                removeLong(k);
            }
            return jDefaultReturnValue;
        }
        long jLongValue = lApply.longValue();
        put(k, jLongValue);
        return jLongValue;
    }

    @Override // java.util.Map
    @Deprecated
    default Long getOrDefault(Object obj, Long l) {
        return (Long) super.getOrDefault(obj, l);
    }

    @Override // java.util.Map
    @Deprecated
    default Long putIfAbsent(K k, Long l) {
        return (Long) super.putIfAbsent((Object) k, l);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(K k, Long l, Long l2) {
        return super.replace((Object) k, l, l2);
    }

    @Override // java.util.Map
    @Deprecated
    default Long replace(K k, Long l) {
        return (Long) super.replace((Object) k, l);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default Long merge(K k, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return (Long) super.merge((Object) k, l, biFunction);
    }

    public interface Entry<K> extends Map.Entry<K, Long> {
        long getLongValue();

        long setValue(long j);

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Long getValue() {
            return Long.valueOf(getLongValue());
        }

        @Override // java.util.Map.Entry
        @Deprecated
        default Long setValue(Long l) {
            return Long.valueOf(setValue(l.longValue()));
        }
    }
}
