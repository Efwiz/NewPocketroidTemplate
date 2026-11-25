package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import com.google.android.gms.cast.MediaError;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractDouble2ObjectMap<V> extends AbstractDouble2ObjectFunction<V> implements Double2ObjectMap<V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractDouble2ObjectMap() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
    public boolean containsKey(double d) {
        ObjectIterator<Double2ObjectMap.Entry<V>> it = double2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getDoubleKey() == d) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        ObjectIterator<Double2ObjectMap.Entry<V>> it = double2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue() == obj) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    public static class BasicEntry<V> implements Double2ObjectMap.Entry<V> {
        protected double key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(double d, V v) {
            this.key = d;
            this.value = v;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap.Entry
        public double getDoubleKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            if (obj instanceof Double2ObjectMap.Entry) {
                Double2ObjectMap.Entry entry = (Double2ObjectMap.Entry) obj;
                return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && Objects.equals(this.value, entry.getValue());
            }
            Map.Entry entry2 = (Map.Entry) obj;
            Object key = entry2.getKey();
            if (key == null || !(key instanceof Double)) {
                return false;
            }
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double) key).doubleValue()) && Objects.equals(this.value, entry2.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int iDouble2int = HashCommon.double2int(this.key);
            V v = this.value;
            return iDouble2int ^ (v == null ? 0 : v.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap, java.util.Map
    public Set<Double> keySet() {
        return new AbstractDoubleSet() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
            public boolean contains(double d) {
                return AbstractDouble2ObjectMap.this.containsKey(d);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractDouble2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractDouble2ObjectMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap$1$1, reason: invalid class name and collision with other inner class name */
            class C03171 implements DoubleIterator {
                private final ObjectIterator<Double2ObjectMap.Entry<V>> i;

                C03171() {
                    this.i = Double2ObjectMaps.fastIterator(AbstractDouble2ObjectMap.this);
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
                public double nextDouble() {
                    return this.i.next().getDoubleKey();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.i.remove();
                }

                @Override // java.util.PrimitiveIterator
                public void forEachRemaining(final java.util.function.DoubleConsumer doubleConsumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap$1$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            doubleConsumer.accept(((Double2ObjectMap.Entry) obj).getDoubleKey());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
            public DoubleIterator iterator() {
                return new C03171();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
            public DoubleSpliterator spliterator() {
                return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2ObjectMap.this), MediaError.DetailedErrorCode.DASH_NETWORK);
            }
        };
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap.2
            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean contains(Object obj) {
                return AbstractDouble2ObjectMap.this.containsValue(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractDouble2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractDouble2ObjectMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap$2$1, reason: invalid class name */
            class AnonymousClass1 implements ObjectIterator<V> {
                private final ObjectIterator<Double2ObjectMap.Entry<V>> i;

                AnonymousClass1() {
                    this.i = Double2ObjectMaps.fastIterator(AbstractDouble2ObjectMap.this);
                }

                @Override // java.util.Iterator
                public V next() {
                    return this.i.next().getValue();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.i.remove();
                }

                @Override // java.util.Iterator
                public void forEachRemaining(final Consumer<? super V> consumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap$2$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            consumer.accept(((Double2ObjectMap.Entry) obj).getValue());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
            public ObjectIterator<V> iterator() {
                return new AnonymousClass1();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
            public ObjectSpliterator<V> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2ObjectMap.this), 64);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends Double, ? extends V> map) {
        if (map instanceof Double2ObjectMap) {
            ObjectIterator objectIteratorFastIterator = Double2ObjectMaps.fastIterator((Double2ObjectMap) map);
            while (objectIteratorFastIterator.hasNext()) {
                Double2ObjectMap.Entry entry = (Double2ObjectMap.Entry) objectIteratorFastIterator.next();
                put(entry.getDoubleKey(), entry.getValue());
            }
            return;
        }
        int size = map.size();
        Iterator<Map.Entry<? extends Double, ? extends V>> it = map.entrySet().iterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return;
            }
            Map.Entry<? extends Double, ? extends V> next = it.next();
            put(next.getKey(), (Object) next.getValue());
            size = i;
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int size = size();
        ObjectIterator objectIteratorFastIterator = Double2ObjectMaps.fastIterator(this);
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += ((Double2ObjectMap.Entry) objectIteratorFastIterator.next()).hashCode();
            size = i;
        }
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (map.size() != size()) {
            return false;
        }
        return double2ObjectEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ObjectIterator objectIteratorFastIterator = Double2ObjectMaps.fastIterator(this);
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size != 0) {
                if (z) {
                    z = false;
                } else {
                    sb.append(", ");
                }
                Double2ObjectMap.Entry entry = (Double2ObjectMap.Entry) objectIteratorFastIterator.next();
                sb.append(String.valueOf(entry.getDoubleKey()));
                sb.append("=>");
                if (this == entry.getValue()) {
                    sb.append("(this map)");
                } else {
                    sb.append(String.valueOf(entry.getValue()));
                }
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
