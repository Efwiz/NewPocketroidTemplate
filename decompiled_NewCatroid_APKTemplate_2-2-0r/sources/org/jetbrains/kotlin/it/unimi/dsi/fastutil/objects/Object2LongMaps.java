package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSets;

/* JADX INFO: loaded from: classes5.dex */
public final class Object2LongMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static <K> ObjectIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap<K> object2LongMap) {
        ObjectSet<Object2LongMap.Entry<K>> objectSetObject2LongEntrySet = object2LongMap.object2LongEntrySet();
        return objectSetObject2LongEntrySet instanceof Object2LongMap.FastEntrySet ? ((Object2LongMap.FastEntrySet) objectSetObject2LongEntrySet).fastIterator() : objectSetObject2LongEntrySet.iterator();
    }

    public static class EmptyMap<K> extends Object2LongFunctions.EmptyFunction<K> implements Object2LongMap<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap
        public boolean containsValue(long j) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
        public void forEach(BiConsumer<? super K, ? super Long> biConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunctions.EmptyFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap
        public long getOrDefault(Object obj, long j) {
            return j;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
        @Deprecated
        public Long getOrDefault(Object obj, Long l) {
            return l;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunctions.EmptyFunction, java.util.Map
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
        public void putAll(Map<? extends K, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap
        public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
        public Collection<Long> values() {
            return LongSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunctions.EmptyFunction
        public Object clone() {
            return Object2LongMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }
}
