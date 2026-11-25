package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSets;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator;

/* JADX INFO: loaded from: classes5.dex */
public final class Double2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static <V> ObjectIterator<Double2ObjectMap.Entry<V>> fastIterator(Double2ObjectMap<V> double2ObjectMap) {
        ObjectSet<Double2ObjectMap.Entry<V>> objectSetDouble2ObjectEntrySet = double2ObjectMap.double2ObjectEntrySet();
        return objectSetDouble2ObjectEntrySet instanceof Double2ObjectMap.FastEntrySet ? ((Double2ObjectMap.FastEntrySet) objectSetDouble2ObjectEntrySet).fastIterator() : objectSetDouble2ObjectEntrySet.iterator();
    }

    public static <V> ObjectIterable<Double2ObjectMap.Entry<V>> fastIterable(Double2ObjectMap<V> double2ObjectMap) {
        final ObjectSet<Double2ObjectMap.Entry<V>> objectSetDouble2ObjectEntrySet = double2ObjectMap.double2ObjectEntrySet();
        return objectSetDouble2ObjectEntrySet instanceof Double2ObjectMap.FastEntrySet ? new ObjectIterable<Double2ObjectMap.Entry<V>>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMaps.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
            public ObjectIterator<Double2ObjectMap.Entry<V>> iterator() {
                return ((Double2ObjectMap.FastEntrySet) objectSetDouble2ObjectEntrySet).fastIterator();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
            public ObjectSpliterator<Double2ObjectMap.Entry<V>> spliterator() {
                return objectSetDouble2ObjectEntrySet.spliterator();
            }

            @Override // java.lang.Iterable
            public void forEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
                ((Double2ObjectMap.FastEntrySet) objectSetDouble2ObjectEntrySet).fastForEach(consumer);
            }
        } : objectSetDouble2ObjectEntrySet;
    }

    public static class EmptyMap<V> extends Double2ObjectFunctions.EmptyFunction<V> implements Double2ObjectMap<V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap, java.util.Map
        public void forEach(BiConsumer<? super Double, ? super V> biConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap, java.util.Map
        @Deprecated
        public V getOrDefault(Object obj, V v) {
            return v;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunctions.EmptyFunction, java.util.Map
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
        public void putAll(Map<? extends Double, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap, java.util.Map
        public Set<Double> keySet() {
            return DoubleSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap, java.util.Map
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunctions.EmptyFunction
        public Object clone() {
            return Double2ObjectMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }
}
