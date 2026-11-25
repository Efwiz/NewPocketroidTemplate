package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractReference2ObjectMap<K, V> extends AbstractReference2ObjectFunction<K, V> implements Reference2ObjectMap<K, V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2ObjectMap() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public boolean containsKey(Object obj) {
        ObjectIterator<Reference2ObjectMap.Entry<K, V>> it = reference2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey() == obj) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        ObjectIterator<Reference2ObjectMap.Entry<K, V>> it = reference2ObjectEntrySet().iterator();
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

    public static class BasicEntry<K, V> implements Reference2ObjectMap.Entry<K, V> {
        protected K key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
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
            if (obj instanceof Reference2ObjectMap.Entry) {
                Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry) obj;
                return this.key == entry.getKey() && Objects.equals(this.value, entry.getValue());
            }
            Map.Entry entry2 = (Map.Entry) obj;
            return this.key == entry2.getKey() && Objects.equals(this.value, entry2.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int iIdentityHashCode = System.identityHashCode(this.key);
            V v = this.value;
            return iIdentityHashCode ^ (v == null ? 0 : v.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return AbstractReference2ObjectMap.this.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractReference2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractReference2ObjectMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap$1$1, reason: invalid class name and collision with other inner class name */
            class C03251 implements ObjectIterator<K> {
                private final ObjectIterator<Reference2ObjectMap.Entry<K, V>> i;

                C03251() {
                    this.i = Reference2ObjectMaps.fastIterator(AbstractReference2ObjectMap.this);
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
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap$1$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            consumer.accept(((Reference2ObjectMap.Entry) obj).getKey());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
            public ObjectIterator<K> iterator() {
                return new C03251();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2ObjectMap.this), 65);
            }
        };
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap.2
            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean contains(Object obj) {
                return AbstractReference2ObjectMap.this.containsValue(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractReference2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractReference2ObjectMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap$2$1, reason: invalid class name */
            class AnonymousClass1 implements ObjectIterator<V> {
                private final ObjectIterator<Reference2ObjectMap.Entry<K, V>> i;

                AnonymousClass1() {
                    this.i = Reference2ObjectMaps.fastIterator(AbstractReference2ObjectMap.this);
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
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap$2$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            consumer.accept(((Reference2ObjectMap.Entry) obj).getValue());
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
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2ObjectMap.this), 64);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map instanceof Reference2ObjectMap) {
            ObjectIterator objectIteratorFastIterator = Reference2ObjectMaps.fastIterator((Reference2ObjectMap) map);
            while (objectIteratorFastIterator.hasNext()) {
                Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry) objectIteratorFastIterator.next();
                put(entry.getKey(), entry.getValue());
            }
            return;
        }
        int size = map.size();
        Iterator<Map.Entry<? extends K, ? extends V>> it = map.entrySet().iterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return;
            }
            Map.Entry<? extends K, ? extends V> next = it.next();
            put(next.getKey(), next.getValue());
            size = i;
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int size = size();
        ObjectIterator objectIteratorFastIterator = Reference2ObjectMaps.fastIterator(this);
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += ((Reference2ObjectMap.Entry) objectIteratorFastIterator.next()).hashCode();
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
        return reference2ObjectEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ObjectIterator objectIteratorFastIterator = Reference2ObjectMaps.fastIterator(this);
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
                Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry) objectIteratorFastIterator.next();
                if (this == entry.getKey()) {
                    sb.append("(this map)");
                } else {
                    sb.append(String.valueOf(entry.getKey()));
                }
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
