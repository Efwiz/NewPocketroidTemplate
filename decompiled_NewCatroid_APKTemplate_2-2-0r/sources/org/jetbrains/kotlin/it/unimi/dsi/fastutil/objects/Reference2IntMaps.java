package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSets;

/* JADX INFO: loaded from: classes5.dex */
public final class Reference2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static <K> ObjectIterator<Reference2IntMap.Entry<K>> fastIterator(Reference2IntMap<K> reference2IntMap) {
        ObjectSet<Reference2IntMap.Entry<K>> objectSetReference2IntEntrySet = reference2IntMap.reference2IntEntrySet();
        return objectSetReference2IntEntrySet instanceof Reference2IntMap.FastEntrySet ? ((Reference2IntMap.FastEntrySet) objectSetReference2IntEntrySet).fastIterator() : objectSetReference2IntEntrySet.iterator();
    }

    public static class EmptyMap<K> extends Reference2IntFunctions.EmptyFunction<K> implements Reference2IntMap<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap
        public boolean containsValue(int i) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap, java.util.Map
        public void forEach(BiConsumer<? super K, ? super Integer> biConsumer) {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap, java.util.Map
        @Deprecated
        public Integer getOrDefault(Object obj, Integer num) {
            return num;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntFunctions.EmptyFunction, java.util.Map
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap
        public ObjectSet<Reference2IntMap.Entry<K>> reference2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap, java.util.Map
        public ReferenceSet<K> keySet() {
            return ReferenceSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap, java.util.Map
        public Collection<Integer> values() {
            return IntSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntFunctions.EmptyFunction
        public Object clone() {
            return Reference2IntMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }
}
