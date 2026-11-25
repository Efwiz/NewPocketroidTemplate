package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterators;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractObject2ShortMap<K> extends AbstractObject2ShortFunction<K> implements Object2ShortMap<K>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2ShortMap() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public boolean containsKey(Object obj) {
        ObjectIterator<Object2ShortMap.Entry<K>> it = object2ShortEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey() == obj) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap
    public boolean containsValue(short s) {
        ObjectIterator<Object2ShortMap.Entry<K>> it = object2ShortEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getShortValue() == s) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    public static class BasicEntry<K> implements Object2ShortMap.Entry<K> {
        protected K key;
        protected short value;

        public BasicEntry() {
        }

        public BasicEntry(K k, short s) {
            this.key = k;
            this.value = s;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry
        public short getShortValue() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry
        public short setValue(short s) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            if (obj instanceof Object2ShortMap.Entry) {
                Object2ShortMap.Entry entry = (Object2ShortMap.Entry) obj;
                return Objects.equals(this.key, entry.getKey()) && this.value == entry.getShortValue();
            }
            Map.Entry entry2 = (Map.Entry) obj;
            Object key = entry2.getKey();
            Object value = entry2.getValue();
            return value != null && (value instanceof Short) && Objects.equals(this.key, key) && this.value == ((Short) value).shortValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K k = this.key;
            return (k == null ? 0 : k.hashCode()) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + ((int) this.value);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap, java.util.Map
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return AbstractObject2ShortMap.this.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractObject2ShortMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractObject2ShortMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap$1$1, reason: invalid class name and collision with other inner class name */
            class C03231 implements ObjectIterator<K> {
                private final ObjectIterator<Object2ShortMap.Entry<K>> i;

                C03231() {
                    this.i = Object2ShortMaps.fastIterator(AbstractObject2ShortMap.this);
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
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap$1$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            consumer.accept(((Object2ShortMap.Entry) obj).getKey());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
            public ObjectIterator<K> iterator() {
                return new C03231();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2ShortMap.this), 65);
            }
        };
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap, java.util.Map
    public Collection<Short> values() {
        return new AbstractShortCollection() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap.2
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
            public boolean contains(short s) {
                return AbstractObject2ShortMap.this.containsValue(s);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractObject2ShortMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractObject2ShortMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap$2$1, reason: invalid class name */
            class AnonymousClass1 implements ShortIterator {
                private final ObjectIterator<Object2ShortMap.Entry<K>> i;

                AnonymousClass1() {
                    this.i = Object2ShortMaps.fastIterator(AbstractObject2ShortMap.this);
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator
                public short nextShort() {
                    return this.i.next().getShortValue();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.i.remove();
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator, java.util.PrimitiveIterator
                public void forEachRemaining(final ShortConsumer shortConsumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap$2$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            shortConsumer.accept(((Object2ShortMap.Entry) obj).getShortValue());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
            public ShortIterator iterator() {
                return new AnonymousClass1();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
            public ShortSpliterator spliterator() {
                return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2ShortMap.this), 320);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends Short> map) {
        if (map instanceof Object2ShortMap) {
            ObjectIterator objectIteratorFastIterator = Object2ShortMaps.fastIterator((Object2ShortMap) map);
            while (objectIteratorFastIterator.hasNext()) {
                Object2ShortMap.Entry entry = (Object2ShortMap.Entry) objectIteratorFastIterator.next();
                put(entry.getKey(), entry.getShortValue());
            }
            return;
        }
        int size = map.size();
        Iterator<Map.Entry<? extends K, ? extends Short>> it = map.entrySet().iterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return;
            }
            Map.Entry<? extends K, ? extends Short> next = it.next();
            put((Object) next.getKey(), next.getValue());
            size = i;
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int size = size();
        ObjectIterator objectIteratorFastIterator = Object2ShortMaps.fastIterator(this);
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += ((Object2ShortMap.Entry) objectIteratorFastIterator.next()).hashCode();
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
        return object2ShortEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ObjectIterator objectIteratorFastIterator = Object2ShortMaps.fastIterator(this);
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
                Object2ShortMap.Entry entry = (Object2ShortMap.Entry) objectIteratorFastIterator.next();
                if (this == entry.getKey()) {
                    sb.append("(this map)");
                } else {
                    sb.append(String.valueOf(entry.getKey()));
                }
                sb.append("=>");
                sb.append(String.valueOf((int) entry.getShortValue()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
