package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSets;

/* JADX INFO: loaded from: classes5.dex */
public final class Object2ShortMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static <K> ObjectIterator<Object2ShortMap.Entry<K>> fastIterator(Object2ShortMap<K> object2ShortMap) {
        ObjectSet<Object2ShortMap.Entry<K>> objectSetObject2ShortEntrySet = object2ShortMap.object2ShortEntrySet();
        return objectSetObject2ShortEntrySet instanceof Object2ShortMap.FastEntrySet ? ((Object2ShortMap.FastEntrySet) objectSetObject2ShortEntrySet).fastIterator() : objectSetObject2ShortEntrySet.iterator();
    }

    public static class EmptyMap<K> extends Object2ShortFunctions.EmptyFunction<K> implements Object2ShortMap<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap
        public boolean containsValue(short s) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap, java.util.Map
        public void forEach(BiConsumer<? super K, ? super Short> biConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap, java.util.Map
        @Deprecated
        public Short getOrDefault(Object obj, Short sh) {
            return sh;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortFunctions.EmptyFunction, java.util.Map
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
        public void putAll(Map<? extends K, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap
        public ObjectSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap, java.util.Map
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap, java.util.Map
        public Collection<Short> values() {
            return ShortSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortFunctions.EmptyFunction
        public Object clone() {
            return Object2ShortMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }

    public static <K> Object2ShortMap<K> emptyMap() {
        return EMPTY_MAP;
    }
}
