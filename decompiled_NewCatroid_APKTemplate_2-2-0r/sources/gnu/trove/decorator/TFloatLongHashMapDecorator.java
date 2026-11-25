package gnu.trove.decorator;

import gnu.trove.TFloatLongHashMap;
import gnu.trove.TFloatLongIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatLongHashMapDecorator extends AbstractMap<Float, Long> {
    protected final TFloatLongHashMap _map;

    public TFloatLongHashMapDecorator(TFloatLongHashMap tFloatLongHashMap) {
        this._map = tFloatLongHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Long put(Float f, Long l) {
        return wrapValue(this._map.put(unwrapKey(f), unwrapValue(l)));
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
            if (!(key instanceof Float) || !(value instanceof Long)) {
                break;
            }
            float fUnwrapKey = unwrapKey(key);
            long jUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(fUnwrapKey) || jUnwrapValue != this._map.get(fUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Long get(Object obj) {
        return get((Float) obj);
    }

    public Long get(Float f) {
        float fUnwrapKey = unwrapKey(f);
        long j = this._map.get(fUnwrapKey);
        if (j == 0) {
            if (this._map.containsKey(fUnwrapKey)) {
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

    public Long remove(Float f) {
        return wrapValue(this._map.remove(unwrapKey(f)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Long remove(Object obj) {
        return remove((Float) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TFloatLongHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Float, Long>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TFloatLongHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TFloatLongHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TFloatLongHashMapDecorator.this.containsKey(key) && TFloatLongHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Float, Long>> iterator() {
            return new Iterator<Map.Entry<Float, Long>>() { // from class: gnu.trove.decorator.TFloatLongHashMapDecorator.1.1
                private final TFloatLongIterator it;

                {
                    this.it = TFloatLongHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Float, Long> next() {
                    this.it.advance();
                    return new Map.Entry<Float, Long>(TFloatLongHashMapDecorator.this.wrapValue(this.it.value()), TFloatLongHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TFloatLongHashMapDecorator.1.1.1
                        private Long val;
                        final /* synthetic */ Float val$key;
                        final /* synthetic */ Long val$v;

                        {
                            this.val$v = l;
                            this.val$key = f;
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

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.util.Map.Entry
                        public Float getKey() {
                            return this.val$key;
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

                        @Override // java.util.Map.Entry
                        public Long setValue(Long l) {
                            this.val = l;
                            return TFloatLongHashMapDecorator.this.put(this.val$key, l);
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
        public boolean add(Map.Entry<Float, Long> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Float, Long>> collection) {
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
            TFloatLongHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Float, Long>> entrySet() {
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
    public void putAll(Map<? extends Float, ? extends Long> map) {
        Iterator<Map.Entry<? extends Float, ? extends Long>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Float, ? extends Long> next = it.next();
            put(next.getKey(), next.getValue());
            size = i;
        }
    }

    protected Float wrapKey(float f) {
        return new Float(f);
    }

    protected float unwrapKey(Object obj) {
        return ((Float) obj).floatValue();
    }

    protected Long wrapValue(long j) {
        return new Long(j);
    }

    protected long unwrapValue(Object obj) {
        return ((Long) obj).longValue();
    }
}
