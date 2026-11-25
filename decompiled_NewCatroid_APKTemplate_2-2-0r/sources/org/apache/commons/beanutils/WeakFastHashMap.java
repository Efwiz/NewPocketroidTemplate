package org.apache.commons.beanutils;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes12.dex */
class WeakFastHashMap<K, V> extends HashMap<K, V> {
    private boolean fast = false;
    private Map<K, V> map;

    public WeakFastHashMap() {
        this.map = null;
        this.map = createMap();
    }

    public WeakFastHashMap(int i) {
        this.map = null;
        this.map = createMap(i);
    }

    public WeakFastHashMap(int i, float f) {
        this.map = null;
        this.map = createMap(i, f);
    }

    public WeakFastHashMap(Map<? extends K, ? extends V> map) {
        this.map = null;
        this.map = createMap(map);
    }

    public boolean getFast() {
        return this.fast;
    }

    public void setFast(boolean z) {
        this.fast = z;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        V v;
        if (this.fast) {
            return this.map.get(obj);
        }
        synchronized (this.map) {
            v = this.map.get(obj);
        }
        return v;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public int size() {
        int size;
        if (this.fast) {
            return this.map.size();
        }
        synchronized (this.map) {
            size = this.map.size();
        }
        return size;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        boolean zIsEmpty;
        if (this.fast) {
            return this.map.isEmpty();
        }
        synchronized (this.map) {
            zIsEmpty = this.map.isEmpty();
        }
        return zIsEmpty;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        boolean zContainsKey;
        if (this.fast) {
            return this.map.containsKey(obj);
        }
        synchronized (this.map) {
            zContainsKey = this.map.containsKey(obj);
        }
        return zContainsKey;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object obj) {
        boolean zContainsValue;
        if (this.fast) {
            return this.map.containsValue(obj);
        }
        synchronized (this.map) {
            zContainsValue = this.map.containsValue(obj);
        }
        return zContainsValue;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        V vPut;
        V vPut2;
        if (this.fast) {
            synchronized (this) {
                Map<K, V> mapCloneMap = cloneMap(this.map);
                vPut2 = mapCloneMap.put(k, v);
                this.map = mapCloneMap;
            }
            return vPut2;
        }
        synchronized (this.map) {
            vPut = this.map.put(k, v);
        }
        return vPut;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        if (this.fast) {
            synchronized (this) {
                Map<K, V> mapCloneMap = cloneMap(this.map);
                mapCloneMap.putAll(map);
                this.map = mapCloneMap;
            }
            return;
        }
        synchronized (this.map) {
            this.map.putAll(map);
        }
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        V vRemove;
        V vRemove2;
        if (this.fast) {
            synchronized (this) {
                Map<K, V> mapCloneMap = cloneMap(this.map);
                vRemove2 = mapCloneMap.remove(obj);
                this.map = mapCloneMap;
            }
            return vRemove2;
        }
        synchronized (this.map) {
            vRemove = this.map.remove(obj);
        }
        return vRemove;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public void clear() {
        if (this.fast) {
            synchronized (this) {
                this.map = createMap();
            }
        } else {
            synchronized (this.map) {
                this.map.clear();
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (this.fast) {
            if (map.size() != this.map.size()) {
                return false;
            }
            for (Map.Entry<K, V> entry : this.map.entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                if (value == null) {
                    if (map.get(key) != null || !map.containsKey(key)) {
                        return false;
                    }
                } else if (!value.equals(map.get(key))) {
                    return false;
                }
            }
            return true;
        }
        synchronized (this.map) {
            if (map.size() != this.map.size()) {
                return false;
            }
            for (Map.Entry<K, V> entry2 : this.map.entrySet()) {
                K key2 = entry2.getKey();
                V value2 = entry2.getValue();
                if (value2 == null) {
                    if (map.get(key2) != null || !map.containsKey(key2)) {
                        return false;
                    }
                } else if (!value2.equals(map.get(key2))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iHashCode = 0;
        if (this.fast) {
            Iterator<Map.Entry<K, V>> it = this.map.entrySet().iterator();
            while (it.hasNext()) {
                iHashCode += it.next().hashCode();
            }
            return iHashCode;
        }
        synchronized (this.map) {
            Iterator<Map.Entry<K, V>> it2 = this.map.entrySet().iterator();
            while (it2.hasNext()) {
                iHashCode += it2.next().hashCode();
            }
        }
        return iHashCode;
    }

    @Override // java.util.HashMap, java.util.AbstractMap
    public Object clone() {
        WeakFastHashMap weakFastHashMap;
        WeakFastHashMap weakFastHashMap2;
        if (this.fast) {
            weakFastHashMap2 = new WeakFastHashMap(this.map);
        } else {
            synchronized (this.map) {
                weakFastHashMap = new WeakFastHashMap(this.map);
            }
            weakFastHashMap2 = weakFastHashMap;
        }
        weakFastHashMap2.setFast(getFast());
        return weakFastHashMap2;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        return new KeySet();
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        return new Values();
    }

    protected Map<K, V> createMap() {
        return new WeakHashMap();
    }

    protected Map<K, V> createMap(int i) {
        return new WeakHashMap(i);
    }

    protected Map<K, V> createMap(int i, float f) {
        return new WeakHashMap(i, f);
    }

    protected Map<K, V> createMap(Map<? extends K, ? extends V> map) {
        return new WeakHashMap(map);
    }

    protected Map<K, V> cloneMap(Map<? extends K, ? extends V> map) {
        return createMap(map);
    }

    private abstract class CollectionView<E> implements Collection<E> {
        protected abstract Collection<E> get(Map<K, V> map);

        protected abstract E iteratorNext(Map.Entry<K, V> entry);

        public CollectionView() {
        }

        @Override // java.util.Collection
        public void clear() {
            if (!WeakFastHashMap.this.fast) {
                synchronized (WeakFastHashMap.this.map) {
                    get(WeakFastHashMap.this.map).clear();
                }
            } else {
                synchronized (WeakFastHashMap.this) {
                    WeakFastHashMap weakFastHashMap = WeakFastHashMap.this;
                    weakFastHashMap.map = weakFastHashMap.createMap();
                }
            }
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            boolean zRemove;
            boolean zRemove2;
            if (!WeakFastHashMap.this.fast) {
                synchronized (WeakFastHashMap.this.map) {
                    zRemove = get(WeakFastHashMap.this.map).remove(obj);
                }
                return zRemove;
            }
            synchronized (WeakFastHashMap.this) {
                WeakFastHashMap weakFastHashMap = WeakFastHashMap.this;
                Map<K, V> mapCloneMap = weakFastHashMap.cloneMap(weakFastHashMap.map);
                zRemove2 = get(mapCloneMap).remove(obj);
                WeakFastHashMap.this.map = mapCloneMap;
            }
            return zRemove2;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            boolean zRemoveAll;
            boolean zRemoveAll2;
            if (!WeakFastHashMap.this.fast) {
                synchronized (WeakFastHashMap.this.map) {
                    zRemoveAll = get(WeakFastHashMap.this.map).removeAll(collection);
                }
                return zRemoveAll;
            }
            synchronized (WeakFastHashMap.this) {
                WeakFastHashMap weakFastHashMap = WeakFastHashMap.this;
                Map<K, V> mapCloneMap = weakFastHashMap.cloneMap(weakFastHashMap.map);
                zRemoveAll2 = get(mapCloneMap).removeAll(collection);
                WeakFastHashMap.this.map = mapCloneMap;
            }
            return zRemoveAll2;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            boolean zRetainAll;
            boolean zRetainAll2;
            if (!WeakFastHashMap.this.fast) {
                synchronized (WeakFastHashMap.this.map) {
                    zRetainAll = get(WeakFastHashMap.this.map).retainAll(collection);
                }
                return zRetainAll;
            }
            synchronized (WeakFastHashMap.this) {
                WeakFastHashMap weakFastHashMap = WeakFastHashMap.this;
                Map<K, V> mapCloneMap = weakFastHashMap.cloneMap(weakFastHashMap.map);
                zRetainAll2 = get(mapCloneMap).retainAll(collection);
                WeakFastHashMap.this.map = mapCloneMap;
            }
            return zRetainAll2;
        }

        @Override // java.util.Collection
        public int size() {
            int size;
            if (WeakFastHashMap.this.fast) {
                return get(WeakFastHashMap.this.map).size();
            }
            synchronized (WeakFastHashMap.this.map) {
                size = get(WeakFastHashMap.this.map).size();
            }
            return size;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            boolean zIsEmpty;
            if (WeakFastHashMap.this.fast) {
                return get(WeakFastHashMap.this.map).isEmpty();
            }
            synchronized (WeakFastHashMap.this.map) {
                zIsEmpty = get(WeakFastHashMap.this.map).isEmpty();
            }
            return zIsEmpty;
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            boolean zContains;
            if (WeakFastHashMap.this.fast) {
                return get(WeakFastHashMap.this.map).contains(obj);
            }
            synchronized (WeakFastHashMap.this.map) {
                zContains = get(WeakFastHashMap.this.map).contains(obj);
            }
            return zContains;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            boolean zContainsAll;
            if (WeakFastHashMap.this.fast) {
                return get(WeakFastHashMap.this.map).containsAll(collection);
            }
            synchronized (WeakFastHashMap.this.map) {
                zContainsAll = get(WeakFastHashMap.this.map).containsAll(collection);
            }
            return zContainsAll;
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            if (WeakFastHashMap.this.fast) {
                return (T[]) get(WeakFastHashMap.this.map).toArray(tArr);
            }
            synchronized (WeakFastHashMap.this.map) {
                tArr2 = (T[]) get(WeakFastHashMap.this.map).toArray(tArr);
            }
            return tArr2;
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            Object[] array;
            if (WeakFastHashMap.this.fast) {
                return get(WeakFastHashMap.this.map).toArray();
            }
            synchronized (WeakFastHashMap.this.map) {
                array = get(WeakFastHashMap.this.map).toArray();
            }
            return array;
        }

        @Override // java.util.Collection
        public boolean equals(Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            if (WeakFastHashMap.this.fast) {
                return get(WeakFastHashMap.this.map).equals(obj);
            }
            synchronized (WeakFastHashMap.this.map) {
                zEquals = get(WeakFastHashMap.this.map).equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.Collection
        public int hashCode() {
            int iHashCode;
            if (WeakFastHashMap.this.fast) {
                return get(WeakFastHashMap.this.map).hashCode();
            }
            synchronized (WeakFastHashMap.this.map) {
                iHashCode = get(WeakFastHashMap.this.map).hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Collection
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return new CollectionViewIterator();
        }

        private class CollectionViewIterator implements Iterator<E> {
            private Map<K, V> expected;
            private final Iterator<Map.Entry<K, V>> iterator;
            private Map.Entry<K, V> lastReturned = null;

            public CollectionViewIterator() {
                Map<K, V> map = WeakFastHashMap.this.map;
                this.expected = map;
                this.iterator = map.entrySet().iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.expected != WeakFastHashMap.this.map) {
                    throw new ConcurrentModificationException();
                }
                return this.iterator.hasNext();
            }

            @Override // java.util.Iterator
            public E next() {
                if (this.expected != WeakFastHashMap.this.map) {
                    throw new ConcurrentModificationException();
                }
                Map.Entry<K, V> next = this.iterator.next();
                this.lastReturned = next;
                return (E) CollectionView.this.iteratorNext(next);
            }

            @Override // java.util.Iterator
            public void remove() {
                if (this.lastReturned != null) {
                    if (WeakFastHashMap.this.fast) {
                        synchronized (WeakFastHashMap.this) {
                            if (this.expected != WeakFastHashMap.this.map) {
                                throw new ConcurrentModificationException();
                            }
                            WeakFastHashMap.this.remove(this.lastReturned.getKey());
                            this.lastReturned = null;
                            this.expected = WeakFastHashMap.this.map;
                        }
                        return;
                    }
                    this.iterator.remove();
                    this.lastReturned = null;
                    return;
                }
                throw new IllegalStateException();
            }
        }
    }

    private class KeySet extends WeakFastHashMap<K, V>.CollectionView<K> implements Set<K> {
        private KeySet() {
            super();
        }

        @Override // org.apache.commons.beanutils.WeakFastHashMap.CollectionView
        protected Collection<K> get(Map<K, V> map) {
            return map.keySet();
        }

        @Override // org.apache.commons.beanutils.WeakFastHashMap.CollectionView
        protected K iteratorNext(Map.Entry<K, V> entry) {
            return entry.getKey();
        }
    }

    private class Values extends WeakFastHashMap<K, V>.CollectionView<V> {
        private Values() {
            super();
        }

        @Override // org.apache.commons.beanutils.WeakFastHashMap.CollectionView
        protected Collection<V> get(Map<K, V> map) {
            return map.values();
        }

        @Override // org.apache.commons.beanutils.WeakFastHashMap.CollectionView
        protected V iteratorNext(Map.Entry<K, V> entry) {
            return entry.getValue();
        }
    }

    private class EntrySet extends WeakFastHashMap<K, V>.CollectionView<Map.Entry<K, V>> implements Set<Map.Entry<K, V>> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.apache.commons.beanutils.WeakFastHashMap.CollectionView
        public Map.Entry<K, V> iteratorNext(Map.Entry<K, V> entry) {
            return entry;
        }

        private EntrySet() {
            super();
        }

        @Override // org.apache.commons.beanutils.WeakFastHashMap.CollectionView
        protected Collection<Map.Entry<K, V>> get(Map<K, V> map) {
            return map.entrySet();
        }
    }
}
