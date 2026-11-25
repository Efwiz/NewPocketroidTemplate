package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import com.google.android.gms.cast.MediaError;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractInt2ObjectMap<V> extends AbstractInt2ObjectFunction<V> implements Int2ObjectMap<V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractInt2ObjectMap() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
    public boolean containsKey(int i) {
        ObjectIterator<Int2ObjectMap.Entry<V>> it = int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getIntKey() == i) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        ObjectIterator<Int2ObjectMap.Entry<V>> it = int2ObjectEntrySet().iterator();
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

    public static class BasicEntry<V> implements Int2ObjectMap.Entry<V> {
        protected int key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(int i, V v) {
            this.key = i;
            this.value = v;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry
        public int getIntKey() {
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
            if (obj instanceof Int2ObjectMap.Entry) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry) obj;
                return this.key == entry.getIntKey() && Objects.equals(this.value, entry.getValue());
            }
            Map.Entry entry2 = (Map.Entry) obj;
            Object key = entry2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            return this.key == ((Integer) key).intValue() && Objects.equals(this.value, entry2.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int i = this.key;
            V v = this.value;
            return i ^ (v == null ? 0 : v.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
    public Set<Integer> keySet() {
        return new AbstractIntSet() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
            public boolean contains(int i) {
                return AbstractInt2ObjectMap.this.containsKey(i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractInt2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractInt2ObjectMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap$1$1, reason: invalid class name and collision with other inner class name */
            class C03191 implements IntIterator {
                private final ObjectIterator<Int2ObjectMap.Entry<V>> i;

                C03191() {
                    this.i = Int2ObjectMaps.fastIterator(AbstractInt2ObjectMap.this);
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    return this.i.next().getIntKey();
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
                public void forEachRemaining(final java.util.function.IntConsumer intConsumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap$1$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            intConsumer.accept(((Int2ObjectMap.Entry) obj).getIntKey());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
            public IntIterator iterator() {
                return new C03191();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2ObjectMap.this), MediaError.DetailedErrorCode.DASH_NETWORK);
            }
        };
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap.2
            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean contains(Object obj) {
                return AbstractInt2ObjectMap.this.containsValue(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractInt2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractInt2ObjectMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap$2$1, reason: invalid class name */
            class AnonymousClass1 implements ObjectIterator<V> {
                private final ObjectIterator<Int2ObjectMap.Entry<V>> i;

                AnonymousClass1() {
                    this.i = Int2ObjectMaps.fastIterator(AbstractInt2ObjectMap.this);
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
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap$2$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            consumer.accept(((Int2ObjectMap.Entry) obj).getValue());
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
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2ObjectMap.this), 64);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends Integer, ? extends V> map) {
        if (map instanceof Int2ObjectMap) {
            ObjectIterator objectIteratorFastIterator = Int2ObjectMaps.fastIterator((Int2ObjectMap) map);
            while (objectIteratorFastIterator.hasNext()) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry) objectIteratorFastIterator.next();
                put(entry.getIntKey(), entry.getValue());
            }
            return;
        }
        int size = map.size();
        Iterator<Map.Entry<? extends Integer, ? extends V>> it = map.entrySet().iterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return;
            }
            Map.Entry<? extends Integer, ? extends V> next = it.next();
            put(next.getKey(), (Object) next.getValue());
            size = i;
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int size = size();
        ObjectIterator objectIteratorFastIterator = Int2ObjectMaps.fastIterator(this);
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += ((Int2ObjectMap.Entry) objectIteratorFastIterator.next()).hashCode();
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
        return int2ObjectEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ObjectIterator objectIteratorFastIterator = Int2ObjectMaps.fastIterator(this);
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
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry) objectIteratorFastIterator.next();
                sb.append(String.valueOf(entry.getIntKey()));
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
