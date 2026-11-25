package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractObject2LongMap<K> extends AbstractObject2LongFunction<K> implements Object2LongMap<K>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2LongMap() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public boolean containsKey(Object obj) {
        ObjectIterator<Object2LongMap.Entry<K>> it = object2LongEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey() == obj) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap
    public boolean containsValue(long j) {
        ObjectIterator<Object2LongMap.Entry<K>> it = object2LongEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getLongValue() == j) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    public static class BasicEntry<K> implements Object2LongMap.Entry<K> {
        protected K key;
        protected long value;

        public BasicEntry() {
        }

        public BasicEntry(K k, long j) {
            this.key = k;
            this.value = j;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap.Entry
        public long getLongValue() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap.Entry
        public long setValue(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            if (obj instanceof Object2LongMap.Entry) {
                Object2LongMap.Entry entry = (Object2LongMap.Entry) obj;
                return Objects.equals(this.key, entry.getKey()) && this.value == entry.getLongValue();
            }
            Map.Entry entry2 = (Map.Entry) obj;
            Object key = entry2.getKey();
            Object value = entry2.getValue();
            return value != null && (value instanceof Long) && Objects.equals(this.key, key) && this.value == ((Long) value).longValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K k = this.key;
            return (k == null ? 0 : k.hashCode()) ^ HashCommon.long2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return AbstractObject2LongMap.this.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractObject2LongMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractObject2LongMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap$1$1, reason: invalid class name and collision with other inner class name */
            class C03211 implements ObjectIterator<K> {
                private final ObjectIterator<Object2LongMap.Entry<K>> i;

                C03211() {
                    this.i = Object2LongMaps.fastIterator(AbstractObject2LongMap.this);
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
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap$1$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            consumer.accept(((Object2LongMap.Entry) obj).getKey());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
            public ObjectIterator<K> iterator() {
                return new C03211();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2LongMap.this), 65);
            }
        };
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
    public Collection<Long> values() {
        return new AbstractLongCollection() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap.2
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
            public boolean contains(long j) {
                return AbstractObject2LongMap.this.containsValue(j);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractObject2LongMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractObject2LongMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap$2$1, reason: invalid class name */
            class AnonymousClass1 implements LongIterator {
                private final ObjectIterator<Object2LongMap.Entry<K>> i;

                AnonymousClass1() {
                    this.i = Object2LongMaps.fastIterator(AbstractObject2LongMap.this);
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator, java.util.PrimitiveIterator.OfLong
                public long nextLong() {
                    return this.i.next().getLongValue();
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
                public void forEachRemaining(final LongConsumer longConsumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap$2$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            longConsumer.accept(((Object2LongMap.Entry) obj).getLongValue());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
            public LongIterator iterator() {
                return new AnonymousClass1();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
            public LongSpliterator spliterator() {
                return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2LongMap.this), 320);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends Long> map) {
        if (map instanceof Object2LongMap) {
            ObjectIterator objectIteratorFastIterator = Object2LongMaps.fastIterator((Object2LongMap) map);
            while (objectIteratorFastIterator.hasNext()) {
                Object2LongMap.Entry entry = (Object2LongMap.Entry) objectIteratorFastIterator.next();
                put(entry.getKey(), entry.getLongValue());
            }
            return;
        }
        int size = map.size();
        Iterator<Map.Entry<? extends K, ? extends Long>> it = map.entrySet().iterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return;
            }
            Map.Entry<? extends K, ? extends Long> next = it.next();
            put((Object) next.getKey(), next.getValue());
            size = i;
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int size = size();
        ObjectIterator objectIteratorFastIterator = Object2LongMaps.fastIterator(this);
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += ((Object2LongMap.Entry) objectIteratorFastIterator.next()).hashCode();
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
        return object2LongEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ObjectIterator objectIteratorFastIterator = Object2LongMaps.fastIterator(this);
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
                Object2LongMap.Entry entry = (Object2LongMap.Entry) objectIteratorFastIterator.next();
                if (this == entry.getKey()) {
                    sb.append("(this map)");
                } else {
                    sb.append(String.valueOf(entry.getKey()));
                }
                sb.append("=>");
                sb.append(String.valueOf(entry.getLongValue()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
