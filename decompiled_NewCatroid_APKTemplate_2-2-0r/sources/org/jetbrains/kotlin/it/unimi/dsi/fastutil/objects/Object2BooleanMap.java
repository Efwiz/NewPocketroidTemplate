package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface Object2BooleanMap<K> extends Object2BooleanFunction<K>, Map<K, Boolean> {
    boolean containsValue(boolean z);

    @Override // java.util.Map
    ObjectSet<K> keySet();

    ObjectSet<Entry<K>> object2BooleanEntrySet();

    @Override // java.util.Map
    Collection<Boolean> values();

    public interface FastEntrySet<K> extends ObjectSet<Entry<K>> {
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
    default ObjectSet<Map.Entry<K, Boolean>> entrySet() {
        return object2BooleanEntrySet();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Map
    @Deprecated
    default Boolean put(K k, Boolean bool) {
        return super.put((Object) k, bool);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2BooleanFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
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

    @Override // java.util.Map
    @Deprecated
    default boolean containsValue(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsValue(((Boolean) obj).booleanValue());
    }

    @Override // java.util.Map
    default void forEach(final BiConsumer<? super K, ? super Boolean> biConsumer) {
        ObjectSet<Entry<K>> objectSetObject2BooleanEntrySet = object2BooleanEntrySet();
        Consumer<? super Entry<K>> consumer = new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2BooleanMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Object2BooleanMap.Entry entry = (Object2BooleanMap.Entry) obj;
                biConsumer.accept(entry.getKey(), Boolean.valueOf(entry.getBooleanValue()));
            }
        };
        if (objectSetObject2BooleanEntrySet instanceof FastEntrySet) {
            ((FastEntrySet) objectSetObject2BooleanEntrySet).fastForEach(consumer);
        } else {
            objectSetObject2BooleanEntrySet.forEach(consumer);
        }
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean getOrDefault(Object obj, Boolean bool) {
        return (Boolean) super.getOrDefault(obj, bool);
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean putIfAbsent(K k, Boolean bool) {
        return (Boolean) super.putIfAbsent((Object) k, bool);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean replace(K k, Boolean bool, Boolean bool2) {
        return super.replace((Object) k, bool, bool2);
    }

    @Override // java.util.Map
    @Deprecated
    default Boolean replace(K k, Boolean bool) {
        return (Boolean) super.replace((Object) k, bool);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    default Boolean merge(K k, Boolean bool, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return (Boolean) super.merge((Object) k, bool, biFunction);
    }

    public interface Entry<K> extends Map.Entry<K, Boolean> {
        boolean getBooleanValue();

        boolean setValue(boolean z);

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
