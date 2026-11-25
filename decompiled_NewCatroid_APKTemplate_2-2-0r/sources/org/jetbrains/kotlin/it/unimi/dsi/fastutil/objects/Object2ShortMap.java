package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface Object2ShortMap<K> extends Object2ShortFunction<K>, Map<K, Short> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    boolean containsKey(Object obj);

    boolean containsValue(short s);

    @Override // java.util.Map
    ObjectSet<K> keySet();

    ObjectSet<Entry<K>> object2ShortEntrySet();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    int size();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    Collection<Short> values();

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
    default ObjectSet<Map.Entry<K, Short>> entrySet() {
        return object2ShortEntrySet();
    }

    @Override // java.util.Map
    @Deprecated
    default Short put(K k, Short sh) {
        return super.put((Object) k, sh);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Short get(Object obj) {
        return super.get(obj);
    }

    @Override // java.util.Map
    @Deprecated
    default Short remove(Object obj) {
        return super.remove(obj);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean containsValue(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsValue(((Short) obj).shortValue());
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super K, ? super Short> biConsumer) {
        ObjectSet<Entry<K>> objectSetObject2ShortEntrySet = object2ShortEntrySet();
        Consumer<? super Entry<K>> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Object2ShortMap.Entry entry = (Object2ShortMap.Entry) obj;
                biConsumer.accept(entry.getKey(), Short.valueOf(entry.getShortValue()));
            }
        };
        if (objectSetObject2ShortEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetObject2ShortEntrySet).fastForEach(consumer);
        } else {
            objectSetObject2ShortEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    @Deprecated
    default Short getOrDefault(Object obj, Short sh) {
        return (Short) super.getOrDefault(obj, sh);
    }

    @Override // java.util.Map
    @Deprecated
    default Short putIfAbsent(K k, Short sh) {
        return (Short) super.putIfAbsent((Object) k, sh);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(K k, Short sh, Short sh2) {
        return super.replace((Object) k, sh, sh2);
    }

    @Override // java.util.Map
    @Deprecated
    default Short replace(K k, Short sh) {
        return (Short) super.replace((Object) k, sh);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default Short merge(K k, Short sh, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return (Short) super.merge((Object) k, sh, biFunction);
    }

    public interface Entry<K> extends Map.Entry<K, Short> {
        short getShortValue();

        short setValue(short s);

        @Override // java.util.Map.Entry
        @Deprecated
        default Short getValue() {
            return Short.valueOf(getShortValue());
        }

        @Override // java.util.Map.Entry
        @Deprecated
        default Short setValue(Short sh) {
            return Short.valueOf(setValue(sh.shortValue()));
        }
    }
}
