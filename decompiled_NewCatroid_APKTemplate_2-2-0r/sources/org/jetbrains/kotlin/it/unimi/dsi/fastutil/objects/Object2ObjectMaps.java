package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes5.dex */
public final class Object2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static <K, V> ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap<K, V> object2ObjectMap) {
        ObjectSet<Object2ObjectMap.Entry<K, V>> objectSetObject2ObjectEntrySet = object2ObjectMap.object2ObjectEntrySet();
        return objectSetObject2ObjectEntrySet instanceof Object2ObjectMap.FastEntrySet ? ((Object2ObjectMap.FastEntrySet) objectSetObject2ObjectEntrySet).fastIterator() : objectSetObject2ObjectEntrySet.iterator();
    }

    public static class EmptyMap<K, V> extends Object2ObjectFunctions.EmptyFunction<K, V> implements Object2ObjectMap<K, V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap, java.util.Map
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectFunctions.EmptyFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap, java.util.Map
        public V getOrDefault(Object obj, V v) {
            return v;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectFunctions.EmptyFunction, java.util.Map
        public int hashCode() {
            return 0;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return true;
        }

        protected EmptyMap() {
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap
        public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectFunctions.EmptyFunction
        public Object clone() {
            return Object2ObjectMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }
}
