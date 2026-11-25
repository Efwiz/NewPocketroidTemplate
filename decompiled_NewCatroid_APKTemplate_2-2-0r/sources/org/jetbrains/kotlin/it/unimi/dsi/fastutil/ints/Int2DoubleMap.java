package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSet;

/* JADX INFO: loaded from: classes5.dex */
public interface Int2DoubleMap extends Int2DoubleFunction, Map<Integer, Double> {
    boolean containsValue(double d);

    ObjectSet<Entry> int2DoubleEntrySet();

    @Override // java.util.Map
    Set<Integer> keySet();

    @Override // java.util.Map
    Collection<Double> values();

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
    default Set<Map.Entry<Integer, Double>> entrySet() {
        return int2DoubleEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Double put(Integer num, Double d) {
        return super.put(num, d);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2DoubleFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Double get(Object obj) {
        return super.get(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Double remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2DoubleFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
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
        return containsValue(((Double) obj).doubleValue());
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super Integer, ? super Double> biConsumer) {
        ObjectSet<Entry> objectSetInt2DoubleEntrySet = int2DoubleEntrySet();
        Consumer<? super Entry> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2DoubleMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Int2DoubleMap.Entry entry = (Int2DoubleMap.Entry) obj;
                biConsumer.accept(Integer.valueOf(entry.getIntKey()), Double.valueOf(entry.getDoubleValue()));
            }
        };
        if (objectSetInt2DoubleEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetInt2DoubleEntrySet).fastForEach(consumer);
        } else {
            objectSetInt2DoubleEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    @Deprecated
    default Double getOrDefault(Object obj, Double d) {
        return (Double) super.getOrDefault(obj, d);
    }

    @Override // java.util.Map
    @Deprecated
    default Double putIfAbsent(Integer num, Double d) {
        return (Double) super.putIfAbsent(num, d);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(Integer num, Double d, Double d2) {
        return super.replace(num, d, d2);
    }

    @Override // java.util.Map
    @Deprecated
    default Double replace(Integer num, Double d) {
        return (Double) super.replace(num, d);
    }

    @Override // java.util.Map
    @Deprecated
    default Double computeIfAbsent(Integer num, Function<? super Integer, ? extends Double> function) {
        return (Double) super.computeIfAbsent(num, (Function<? super Integer, ? extends V>) function);
    }

    @Override // java.util.Map
    @Deprecated
    default Double computeIfPresent(Integer num, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        return (Double) super.computeIfPresent(num, (BiFunction<? super Integer, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.Map
    @Deprecated
    default Double compute(Integer num, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        return (Double) super.compute(num, (BiFunction<? super Integer, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.Map
    @Deprecated
    default Double merge(Integer num, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return (Double) super.merge(num, d, biFunction);
    }

    public interface Entry extends Map.Entry<Integer, Double> {
        double getDoubleValue();

        int getIntKey();

        double setValue(double d);

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Integer getKey() {
            return Integer.valueOf(getIntKey());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Double getValue() {
            return Double.valueOf(getDoubleValue());
        }

        @Override // java.util.Map.Entry
        @Deprecated
        default Double setValue(Double d) {
            return Double.valueOf(setValue(d.doubleValue()));
        }
    }
}
