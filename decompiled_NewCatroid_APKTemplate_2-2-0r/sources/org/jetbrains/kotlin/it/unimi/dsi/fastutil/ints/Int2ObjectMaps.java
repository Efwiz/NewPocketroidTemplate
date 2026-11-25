package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSets;

/* JADX INFO: loaded from: classes5.dex */
public final class Int2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    public static <V> ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap<V> int2ObjectMap) {
        ObjectSet<Int2ObjectMap.Entry<V>> objectSetInt2ObjectEntrySet = int2ObjectMap.int2ObjectEntrySet();
        return objectSetInt2ObjectEntrySet instanceof Int2ObjectMap.FastEntrySet ? ((Int2ObjectMap.FastEntrySet) objectSetInt2ObjectEntrySet).fastIterator() : objectSetInt2ObjectEntrySet.iterator();
    }

    public static class EmptyMap<V> extends Int2ObjectFunctions.EmptyFunction<V> implements Int2ObjectMap<V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        public void forEach(BiConsumer<? super Integer, ? super V> biConsumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
        @Deprecated
        public V getOrDefault(Object obj, V v) {
            return v;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunctions.EmptyFunction, java.util.Map
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
        public void putAll(Map<? extends Integer, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        public Set<Integer> keySet() {
            return IntSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunctions.EmptyFunction
        public Object clone() {
            return Int2ObjectMaps.EMPTY_MAP;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object obj) {
            if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }

    public static class SynchronizedMap<V> extends Int2ObjectFunctions.SynchronizedFunction<V> implements Int2ObjectMap<V>, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
        protected transient IntSet keys;
        protected final Int2ObjectMap<V> map;
        protected transient ObjectCollection<V> values;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public /* bridge */ /* synthetic */ Object merge(Integer num, Object obj, BiFunction biFunction) {
            return merge(num, obj, (BiFunction<? super Object, ? super Object, ? extends Object>) biFunction);
        }

        protected SynchronizedMap(Int2ObjectMap<V> int2ObjectMap) {
            super(int2ObjectMap);
            this.map = int2ObjectMap;
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            boolean zContainsValue;
            synchronized (this.sync) {
                zContainsValue = this.map.containsValue(obj);
            }
            return zContainsValue;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Integer, ? extends V> map) {
            synchronized (this.sync) {
                this.map.putAll(map);
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            ObjectSet<Int2ObjectMap.Entry<V>> objectSet;
            synchronized (this.sync) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.int2ObjectEntrySet(), this.sync);
                }
                objectSet = this.entries;
            }
            return objectSet;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public ObjectSet<Map.Entry<Integer, V>> entrySet() {
            return int2ObjectEntrySet();
        }

        /* JADX WARN: Type inference failed for: r1v4, types: [org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet] */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        public Set<Integer> keySet() {
            IntSet intSet;
            synchronized (this.sync) {
                if (this.keys == null) {
                    this.keys = IntSets.synchronize(this.map.keySet(), this.sync);
                }
                intSet = this.keys;
            }
            return intSet;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        public ObjectCollection<V> values() {
            ObjectCollection<V> objectCollection;
            synchronized (this.sync) {
                if (this.values == null) {
                    this.values = ObjectCollections.synchronize(this.map.values(), this.sync);
                }
                objectCollection = this.values;
            }
            return objectCollection;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.sync) {
                zIsEmpty = this.map.isEmpty();
            }
            return zIsEmpty;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunctions.SynchronizedFunction, java.util.Map
        public int hashCode() {
            int iHashCode;
            synchronized (this.sync) {
                iHashCode = this.map.hashCode();
            }
            return iHashCode;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunctions.SynchronizedFunction, java.util.Map
        public boolean equals(Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.sync) {
                zEquals = this.map.equals(obj);
            }
            return zEquals;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.sync) {
                objectOutputStream.defaultWriteObject();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        public void forEach(BiConsumer<? super Integer, ? super V> biConsumer) {
            synchronized (this.sync) {
                this.map.forEach(biConsumer);
            }
        }

        @Override // java.util.Map
        public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            synchronized (this.sync) {
                this.map.replaceAll(biFunction);
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunctions.SynchronizedFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
        @Deprecated
        public V getOrDefault(Object obj, V v) {
            V orDefault;
            synchronized (this.sync) {
                orDefault = this.map.getOrDefault(obj, v);
            }
            return orDefault;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public boolean remove(Object obj, Object obj2) {
            boolean zRemove;
            synchronized (this.sync) {
                zRemove = this.map.remove(obj, obj2);
            }
            return zRemove;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public V replace(Integer num, V v) {
            V vReplace;
            synchronized (this.sync) {
                vReplace = this.map.replace(num, (Object) v);
            }
            return vReplace;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public boolean replace(Integer num, V v, V v2) {
            boolean zReplace;
            synchronized (this.sync) {
                zReplace = this.map.replace(num, (Object) v, (Object) v2);
            }
            return zReplace;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public V putIfAbsent(Integer num, V v) {
            V vPutIfAbsent;
            synchronized (this.sync) {
                vPutIfAbsent = this.map.putIfAbsent(num, (Object) v);
            }
            return vPutIfAbsent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public V computeIfAbsent(Integer num, Function<? super Integer, ? extends V> function) {
            V vComputeIfAbsent;
            synchronized (this.sync) {
                vComputeIfAbsent = this.map.computeIfAbsent(num, (Function) function);
            }
            return vComputeIfAbsent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public V computeIfPresent(Integer num, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            V vComputeIfPresent;
            synchronized (this.sync) {
                vComputeIfPresent = this.map.computeIfPresent(num, (BiFunction) biFunction);
            }
            return vComputeIfPresent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public V compute(Integer num, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            V vCompute;
            synchronized (this.sync) {
                vCompute = this.map.compute(num, (BiFunction) biFunction);
            }
            return vCompute;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap
        @Deprecated
        public V merge(Integer num, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            V vMerge2;
            synchronized (this.sync) {
                vMerge2 = this.map.merge2(num, (Object) v, (BiFunction) biFunction);
            }
            return vMerge2;
        }
    }

    public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> int2ObjectMap) {
        return new SynchronizedMap(int2ObjectMap);
    }
}
