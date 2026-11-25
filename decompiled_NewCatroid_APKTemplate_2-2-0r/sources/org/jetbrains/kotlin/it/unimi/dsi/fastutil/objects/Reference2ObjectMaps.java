package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes5.dex */
public final class Reference2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static <K, V> ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator(Reference2ObjectMap<K, V> reference2ObjectMap) {
        ObjectSet<Reference2ObjectMap.Entry<K, V>> objectSetReference2ObjectEntrySet = reference2ObjectMap.reference2ObjectEntrySet();
        return objectSetReference2ObjectEntrySet instanceof Reference2ObjectMap.FastEntrySet ? ((Reference2ObjectMap.FastEntrySet) objectSetReference2ObjectEntrySet).fastIterator() : objectSetReference2ObjectEntrySet.iterator();
    }

    public static class EmptyMap<K, V> extends Reference2ObjectFunctions.EmptyFunction<K, V> implements Reference2ObjectMap<K, V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunctions.EmptyFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
        public V getOrDefault(Object obj, V v) {
            return v;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunctions.EmptyFunction, java.util.Map
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap
        public ObjectSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
        public ReferenceSet<K> keySet() {
            return ReferenceSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunctions.EmptyFunction
        public Object clone() {
            return Reference2ObjectMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }
}
