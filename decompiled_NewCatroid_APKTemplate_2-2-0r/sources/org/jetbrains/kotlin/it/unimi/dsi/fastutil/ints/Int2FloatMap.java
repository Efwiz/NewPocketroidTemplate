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
public interface Int2FloatMap extends Int2FloatFunction, Map<Integer, Float> {
    boolean containsValue(float f);

    ObjectSet<Entry> int2FloatEntrySet();

    @Override // java.util.Map
    Set<Integer> keySet();

    @Override // java.util.Map
    Collection<Float> values();

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
    default Set<Map.Entry<Integer, Float>> entrySet() {
        return int2FloatEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Float put(Integer num, Float f) {
        return super.put(num, f);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2FloatFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Float get(Object obj) {
        return super.get(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Float remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2FloatFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
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
        return containsValue(((Float) obj).floatValue());
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super Integer, ? super Float> biConsumer) {
        ObjectSet<Entry> objectSetInt2FloatEntrySet = int2FloatEntrySet();
        Consumer<? super Entry> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2FloatMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Int2FloatMap.Entry entry = (Int2FloatMap.Entry) obj;
                biConsumer.accept(Integer.valueOf(entry.getIntKey()), Float.valueOf(entry.getFloatValue()));
            }
        };
        if (objectSetInt2FloatEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetInt2FloatEntrySet).fastForEach(consumer);
        } else {
            objectSetInt2FloatEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    @Deprecated
    default Float getOrDefault(Object obj, Float f) {
        return (Float) super.getOrDefault(obj, f);
    }

    @Override // java.util.Map
    @Deprecated
    default Float putIfAbsent(Integer num, Float f) {
        return (Float) super.putIfAbsent(num, f);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(Integer num, Float f, Float f2) {
        return super.replace(num, f, f2);
    }

    @Override // java.util.Map
    @Deprecated
    default Float replace(Integer num, Float f) {
        return (Float) super.replace(num, f);
    }

    @Override // java.util.Map
    @Deprecated
    default Float computeIfAbsent(Integer num, Function<? super Integer, ? extends Float> function) {
        return (Float) super.computeIfAbsent(num, (Function<? super Integer, ? extends V>) function);
    }

    @Override // java.util.Map
    @Deprecated
    default Float computeIfPresent(Integer num, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
        return (Float) super.computeIfPresent(num, (BiFunction<? super Integer, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.Map
    @Deprecated
    default Float compute(Integer num, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
        return (Float) super.compute(num, (BiFunction<? super Integer, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.Map
    @Deprecated
    default Float merge(Integer num, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return (Float) super.merge(num, f, biFunction);
    }

    public interface Entry extends Map.Entry<Integer, Float> {
        float getFloatValue();

        int getIntKey();

        float setValue(float f);

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Integer getKey() {
            return Integer.valueOf(getIntKey());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Float getValue() {
            return Float.valueOf(getFloatValue());
        }

        @Override // java.util.Map.Entry
        @Deprecated
        default Float setValue(Float f) {
            return Float.valueOf(setValue(f.floatValue()));
        }
    }
}
