package gnu.trove.decorator;

import gnu.trove.TLongFloatHashMap;
import gnu.trove.TLongFloatIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TLongFloatHashMapDecorator extends AbstractMap<Long, Float> {
    protected final TLongFloatHashMap _map;

    public TLongFloatHashMapDecorator(TLongFloatHashMap tLongFloatHashMap) {
        this._map = tLongFloatHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float put(Long l, Float f) {
        return wrapValue(this._map.put(unwrapKey(l), unwrapValue(f)));
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
            if (!(key instanceof Long) || !(value instanceof Float)) {
                break;
            }
            long jUnwrapKey = unwrapKey(key);
            float fUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(jUnwrapKey) || fUnwrapValue != this._map.get(jUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float get(Object obj) {
        return get((Long) obj);
    }

    public Float get(Long l) {
        long jUnwrapKey = unwrapKey(l);
        float f = this._map.get(jUnwrapKey);
        if (f == 0.0f) {
            if (this._map.containsKey(jUnwrapKey)) {
                return wrapValue(f);
            }
            return null;
        }
        return wrapValue(f);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._map.clear();
    }

    public Float remove(Long l) {
        return wrapValue(this._map.remove(unwrapKey(l)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float remove(Object obj) {
        return remove((Long) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TLongFloatHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Long, Float>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TLongFloatHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TLongFloatHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TLongFloatHashMapDecorator.this.containsKey(key) && TLongFloatHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Long, Float>> iterator() {
            return new Iterator<Map.Entry<Long, Float>>() { // from class: gnu.trove.decorator.TLongFloatHashMapDecorator.1.1
                private final TLongFloatIterator it;

                {
                    this.it = TLongFloatHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Long, Float> next() {
                    this.it.advance();
                    return new Map.Entry<Long, Float>(TLongFloatHashMapDecorator.this.wrapValue(this.it.value()), TLongFloatHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TLongFloatHashMapDecorator.1.1.1
                        private Float val;
                        final /* synthetic */ Long val$key;
                        final /* synthetic */ Float val$v;

                        {
                            this.val$v = f;
                            this.val$key = l;
                            this.val = f;
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

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.util.Map.Entry
                        public Long getKey() {
                            return this.val$key;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.util.Map.Entry
                        public Float getValue() {
                            return this.val;
                        }

                        @Override // java.util.Map.Entry
                        public int hashCode() {
                            return this.val$key.hashCode() + this.val.hashCode();
                        }

                        @Override // java.util.Map.Entry
                        public Float setValue(Float f) {
                            this.val = f;
                            return TLongFloatHashMapDecorator.this.put(this.val$key, f);
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
        public boolean add(Map.Entry<Long, Float> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Long, Float>> collection) {
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
            TLongFloatHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Long, Float>> entrySet() {
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
    public void putAll(Map<? extends Long, ? extends Float> map) {
        Iterator<Map.Entry<? extends Long, ? extends Float>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Long, ? extends Float> next = it.next();
            put(next.getKey(), next.getValue());
            size = i;
        }
    }

    protected Long wrapKey(long j) {
        return new Long(j);
    }

    protected long unwrapKey(Object obj) {
        return ((Long) obj).longValue();
    }

    protected Float wrapValue(float f) {
        return new Float(f);
    }

    protected float unwrapValue(Object obj) {
        return ((Float) obj).floatValue();
    }
}
