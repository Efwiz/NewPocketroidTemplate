package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSets;

/* JADX INFO: loaded from: classes5.dex */
public final class Int2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static ObjectIterator<Int2IntMap.Entry> fastIterator(Int2IntMap int2IntMap) {
        ObjectSet<Int2IntMap.Entry> objectSetInt2IntEntrySet = int2IntMap.int2IntEntrySet();
        return objectSetInt2IntEntrySet instanceof Int2IntMap.FastEntrySet ? ((Int2IntMap.FastEntrySet) objectSetInt2IntEntrySet).fastIterator() : objectSetInt2IntEntrySet.iterator();
    }

    public static class EmptyMap extends Int2IntFunctions.EmptyFunction implements Int2IntMap, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap
        public boolean containsValue(int i) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
        public void forEach(BiConsumer<? super Integer, ? super Integer> biConsumer) {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
        @Deprecated
        public Integer getOrDefault(Object obj, Integer num) {
            return num;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunctions.EmptyFunction, java.util.Map
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
        public void putAll(Map<? extends Integer, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap
        public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
        public Set<Integer> keySet() {
            return IntSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
        public Collection<Integer> values() {
            return IntSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunctions.EmptyFunction
        public Object clone() {
            return Int2IntMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }
}
