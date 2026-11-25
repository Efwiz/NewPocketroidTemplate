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
public interface Int2BooleanMap extends Int2BooleanFunction, Map<Integer, Boolean> {
    boolean containsValue(boolean z);

    ObjectSet<Entry> int2BooleanEntrySet();

    @Override // java.util.Map
    Set<Integer> keySet();

    @Override // java.util.Map
    Collection<Boolean> values();

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
    default Set<Map.Entry<Integer, Boolean>> entrySet() {
        return int2BooleanEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Boolean put(Integer num, Boolean bool) {
        return super.put(num, bool);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2BooleanFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Boolean get(Object obj) {
        return super.get(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2BooleanFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
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
        return containsValue(((Boolean) obj).booleanValue());
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super Integer, ? super Boolean> biConsumer) {
        ObjectSet<Entry> objectSetInt2BooleanEntrySet = int2BooleanEntrySet();
        Consumer<? super Entry> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2BooleanMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry) obj;
                biConsumer.accept(Integer.valueOf(entry.getIntKey()), Boolean.valueOf(entry.getBooleanValue()));
            }
        };
        if (objectSetInt2BooleanEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetInt2BooleanEntrySet).fastForEach(consumer);
        } else {
            objectSetInt2BooleanEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean getOrDefault(Object obj, Boolean bool) {
        return (Boolean) super.getOrDefault(obj, bool);
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean putIfAbsent(Integer num, Boolean bool) {
        return (Boolean) super.putIfAbsent(num, bool);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(Integer num, Boolean bool, Boolean bool2) {
        return super.replace(num, bool, bool2);
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean replace(Integer num, Boolean bool) {
        return (Boolean) super.replace(num, bool);
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean computeIfAbsent(Integer num, Function<? super Integer, ? extends Boolean> function) {
        return (Boolean) super.computeIfAbsent(num, (Function<? super Integer, ? extends V>) function);
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean computeIfPresent(Integer num, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
        return (Boolean) super.computeIfPresent(num, (BiFunction<? super Integer, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean compute(Integer num, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
        return (Boolean) super.compute(num, (BiFunction<? super Integer, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean merge(Integer num, Boolean bool, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return (Boolean) super.merge(num, bool, biFunction);
    }

    public interface Entry extends Map.Entry<Integer, Boolean> {
        boolean getBooleanValue();

        int getIntKey();

        boolean setValue(boolean z);

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Integer getKey() {
            return Integer.valueOf(getIntKey());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        @Deprecated
        default Boolean getValue() {
            return Boolean.valueOf(getBooleanValue());
        }

        @Override // java.util.Map.Entry
        @Deprecated
        default Boolean setValue(Boolean bool) {
            return Boolean.valueOf(setValue(bool.booleanValue()));
        }
    }
}
