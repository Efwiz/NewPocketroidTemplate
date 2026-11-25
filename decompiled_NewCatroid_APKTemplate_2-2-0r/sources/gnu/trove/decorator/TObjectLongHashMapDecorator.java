package gnu.trove.decorator;

import gnu.trove.TObjectLongHashMap;
import gnu.trove.TObjectLongIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectLongHashMapDecorator<V> extends AbstractMap<V, Long> implements Map<V, Long> {
    protected final TObjectLongHashMap<V> _map;

    /* JADX WARN: Multi-variable type inference failed */
    protected final V unwrapKey(Object obj) {
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final V wrapKey(Object obj) {
        return obj;
    }

    public TObjectLongHashMapDecorator(TObjectLongHashMap<V> tObjectLongHashMap) {
        this._map = tObjectLongHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Long put(V v, Long l) {
        return wrapValue(this._map.put(unwrapKey(v), unwrapValue(l)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this._map.equals(obj)) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (map.size() != this._map.size()) {
            return false;
        }
        Iterator it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return true;
            }
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (!(value instanceof Long)) {
                break;
            }
            V vUnwrapKey = unwrapKey(key);
            long jUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(vUnwrapKey) || jUnwrapValue != this._map.get(vUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.AbstractMap, java.util.Map
    public Long get(Object obj) {
        V vUnwrapKey = unwrapKey(obj);
        long j = this._map.get(vUnwrapKey);
        if (j == 0) {
            if (this._map.containsKey(vUnwrapKey)) {
                return wrapValue(j);
            }
            return null;
        }
        return wrapValue(j);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._map.clear();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.AbstractMap, java.util.Map
    public Long remove(Object obj) {
        return wrapValue(this._map.remove(unwrapKey(obj)));
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TObjectLongHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<V, Long>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TObjectLongHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TObjectLongHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TObjectLongHashMapDecorator.this.containsKey(key) && TObjectLongHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<V, Long>> iterator() {
            return new Iterator<Map.Entry<V, Long>>() { // from class: gnu.trove.decorator.TObjectLongHashMapDecorator.1.1
                private final TObjectLongIterator<V> it;

                {
                    this.it = TObjectLongHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<V, Long> next() {
                    this.it.advance();
                    return new Map.Entry<V, Long>(TObjectLongHashMapDecorator.this.wrapValue(this.it.value()), TObjectLongHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TObjectLongHashMapDecorator.1.1.1
                        private Long val;
                        final /* synthetic */ Object val$key;
                        final /* synthetic */ Long val$v;

                        {
                            this.val$v = l;
                            this.val$key = obj;
                            this.val = l;
                        }

                        @Override // java.util.Map.Entry
                        public boolean equals(Object obj) {
                            if (obj instanceof Map.Entry) {
                                Map.Entry entry = (Map.Entry) obj;
                                if (entry.getKey().equals(this.val$key) && entry.getValue().equals(this.val)) {
                                    return true;
                                }
                            }
                            return false;
                        }

                        @Override // java.util.Map.Entry
                        public V getKey() {
                            return (V) this.val$key;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.util.Map.Entry
                        public Long getValue() {
                            return this.val;
                        }

                        @Override // java.util.Map.Entry
                        public int hashCode() {
                            return this.val$key.hashCode() + this.val.hashCode();
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Map.Entry
                        public Long setValue(Long l) {
                            this.val = l;
                            return TObjectLongHashMapDecorator.this.put(this.val$key, l);
                        }
                    };
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.it.hasNext();
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.it.remove();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean add(Map.Entry<V, Long> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<V, Long>> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            TObjectLongHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<V, Long>> entrySet() {
        return new AnonymousClass1();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object obj) {
        return this._map.containsValue(unwrapValue(obj));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return this._map.containsKey(unwrapKey(obj));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this._map.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends V, ? extends Long> map) {
        Iterator<Map.Entry<? extends V, ? extends Long>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends V, ? extends Long> next = it.next();
            put((Object) next.getKey(), next.getValue());
            size = i;
        }
    }

    protected Long wrapValue(long j) {
        return new Long(j);
    }

    protected long unwrapValue(Object obj) {
        return ((Long) obj).longValue();
    }
}
