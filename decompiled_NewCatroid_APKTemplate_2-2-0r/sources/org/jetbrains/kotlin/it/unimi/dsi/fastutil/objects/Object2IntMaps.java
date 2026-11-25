package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSets;

/* JADX INFO: loaded from: classes5.dex */
public final class Object2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static <K> ObjectIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap<K> object2IntMap) {
        ObjectSet<Object2IntMap.Entry<K>> objectSetObject2IntEntrySet = object2IntMap.object2IntEntrySet();
        return objectSetObject2IntEntrySet instanceof Object2IntMap.FastEntrySet ? ((Object2IntMap.FastEntrySet) objectSetObject2IntEntrySet).fastIterator() : objectSetObject2IntEntrySet.iterator();
    }

    public static class EmptyMap<K> extends Object2IntFunctions.EmptyFunction<K> implements Object2IntMap<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap
        public boolean containsValue(int i) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap, java.util.Map
        public void forEach(BiConsumer<? super K, ? super Integer> biConsumer) {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap, java.util.Map
        @Deprecated
        public Integer getOrDefault(Object obj, Integer num) {
            return num;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntFunctions.EmptyFunction, java.util.Map
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
        public void putAll(Map<? extends K, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap, java.util.Map
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap, java.util.Map
        public Collection<Integer> values() {
            return IntSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntFunctions.EmptyFunction
        public Object clone() {
            return Object2IntMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }
}
