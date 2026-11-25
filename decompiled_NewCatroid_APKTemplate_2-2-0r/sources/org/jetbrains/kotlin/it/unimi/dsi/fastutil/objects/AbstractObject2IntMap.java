package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractObject2IntMap<K> extends AbstractObject2IntFunction<K> implements Object2IntMap<K>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2IntMap() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public boolean containsKey(Object obj) {
        ObjectIterator<Object2IntMap.Entry<K>> it = object2IntEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey() == obj) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap
    public boolean containsValue(int i) {
        ObjectIterator<Object2IntMap.Entry<K>> it = object2IntEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getIntValue() == i) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    public static class BasicEntry<K> implements Object2IntMap.Entry<K> {
        protected K key;
        protected int value;

        public BasicEntry() {
        }

        public BasicEntry(K k, int i) {
            this.key = k;
            this.value = i;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap.Entry
        public int getIntValue() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap.Entry
        public int setValue(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            if (obj instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry entry = (Object2IntMap.Entry) obj;
                return Objects.equals(this.key, entry.getKey()) && this.value == entry.getIntValue();
            }
            Map.Entry entry2 = (Map.Entry) obj;
            Object key = entry2.getKey();
            Object value = entry2.getValue();
            return value != null && (value instanceof Integer) && Objects.equals(this.key, key) && this.value == ((Integer) value).intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K k = this.key;
            return (k == null ? 0 : k.hashCode()) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap, java.util.Map
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2IntMap.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return AbstractObject2IntMap.this.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractObject2IntMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractObject2IntMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2IntMap$1$1, reason: invalid class name and collision with other inner class name */
            class C03201 implements ObjectIterator<K> {
                private final ObjectIterator<Object2IntMap.Entry<K>> i;

                C03201() {
                    this.i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);
                }

                @Override // java.util.Iterator
                public K next() {
                    return this.i.next().getKey();
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
                public void forEachRemaining(final Consumer<? super K> consumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2IntMap$1$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            consumer.accept(((Object2IntMap.Entry) obj).getKey());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
            public ObjectIterator<K> iterator() {
                return new C03201();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 65);
            }
        };
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap, java.util.Map
    public Collection<Integer> values() {
        return new AbstractIntCollection() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2IntMap.2
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
            public boolean contains(int i) {
                return AbstractObject2IntMap.this.containsValue(i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractObject2IntMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractObject2IntMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2IntMap$2$1, reason: invalid class name */
            class AnonymousClass1 implements IntIterator {
                private final ObjectIterator<Object2IntMap.Entry<K>> i;

                AnonymousClass1() {
                    this.i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    return this.i.next().getIntValue();
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
                public void forEachRemaining(final IntConsumer intConsumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2IntMap$2$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            intConsumer.accept(((Object2IntMap.Entry) obj).getIntValue());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
            public IntIterator iterator() {
                return new AnonymousClass1();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 320);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends Integer> map) {
        if (map instanceof Object2IntMap) {
            ObjectIterator objectIteratorFastIterator = Object2IntMaps.fastIterator((Object2IntMap) map);
            while (objectIteratorFastIterator.hasNext()) {
                Object2IntMap.Entry entry = (Object2IntMap.Entry) objectIteratorFastIterator.next();
                put(entry.getKey(), entry.getIntValue());
            }
            return;
        }
        int size = map.size();
        Iterator<Map.Entry<? extends K, ? extends Integer>> it = map.entrySet().iterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return;
            }
            Map.Entry<? extends K, ? extends Integer> next = it.next();
            put((Object) next.getKey(), next.getValue());
            size = i;
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int size = size();
        ObjectIterator objectIteratorFastIterator = Object2IntMaps.fastIterator(this);
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += ((Object2IntMap.Entry) objectIteratorFastIterator.next()).hashCode();
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
        return object2IntEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ObjectIterator objectIteratorFastIterator = Object2IntMaps.fastIterator(this);
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
                Object2IntMap.Entry entry = (Object2IntMap.Entry) objectIteratorFastIterator.next();
                if (this == entry.getKey()) {
                    sb.append("(this map)");
                } else {
                    sb.append(String.valueOf(entry.getKey()));
                }
                sb.append("=>");
                sb.append(String.valueOf(entry.getIntValue()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
