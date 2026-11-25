package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSet;

/* JADX INFO: loaded from: classes5.dex */
public interface Long2LongMap extends Long2LongFunction, Map<Long, Long> {
    boolean containsValue(long j);

    @Override // java.util.Map
    Set<Long> keySet();

    ObjectSet<Entry> long2LongEntrySet();

    @Override // java.util.Map
    Collection<Long> values();

    public interface FastEntrySet extends ObjectSet<Entry> {
        default void fastForEach(Consumer<? super Entry> consumer) {
            forEach(consumer);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    default Set<Map.Entry<Long, Long>> entrySet() {
        return long2LongEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Long put(Long l, Long l2) {
        return super.put(l, l2);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.Long2LongFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.Long2LongFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        return super.containsKey(obj);
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
    default void forEach(final BiConsumer<? super Long, ? super Long> biConsumer) {
        ObjectSet<Entry> objectSetLong2LongEntrySet = long2LongEntrySet();
        Consumer<? super Entry> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.Long2LongMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Long2LongMap.Entry entry = (Long2LongMap.Entry) obj;
                biConsumer.accept(Long.valueOf(entry.getLongKey()), Long.valueOf(entry.getLongValue()));
            }
        };
        if (objectSetLong2LongEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetLong2LongEntrySet).fastForEach(consumer);
        } else {
            objectSetLong2LongEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    @Deprecated
    default Long getOrDefault(Object obj, Long l) {
        return (Long) super.getOrDefault(obj, l);
    }

    @Override // java.util.Map
    @Deprecated
    default Long putIfAbsent(Long l, Long l2) {
        return (Long) super.putIfAbsent(l, l2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(Long l, Long l2, Long l3) {
        return super.replace(l, l2, l3);
    }

    @Override // java.util.Map
    @Deprecated
    default Long replace(Long l, Long l2) {
        return (Long) super.replace(l, l2);
    }

    @Override // java.util.Map
    @Deprecated
    default Long computeIfAbsent(Long l, Function<? super Long, ? extends Long> function) {
        return (Long) super.computeIfAbsent(l, (Function<? super Long, ? extends V>) function);
    }

    @Override // java.util.Map
    @Deprecated
    default Long computeIfPresent(Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return (Long) super.computeIfPresent(l, (BiFunction<? super Long, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.Map
    @Deprecated
    default Long compute(Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return (Long) super.compute(l, (BiFunction<? super Long, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.Map
    @Deprecated
    default Long merge(Long l, Long l2, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return (Long) super.merge(l, l2, biFunction);
    }

    public interface Entry extends Map.Entry<Long, Long> {
        long getLongKey();

        long getLongValue();

        long setValue(long j);

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Long getKey() {
            return Long.valueOf(getLongKey());
        }

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
