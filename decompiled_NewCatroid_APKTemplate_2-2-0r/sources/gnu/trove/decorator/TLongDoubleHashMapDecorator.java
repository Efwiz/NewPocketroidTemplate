package gnu.trove.decorator;

import gnu.trove.TLongDoubleHashMap;
import gnu.trove.TLongDoubleIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TLongDoubleHashMapDecorator extends AbstractMap<Long, Double> {
    protected final TLongDoubleHashMap _map;

    public TLongDoubleHashMapDecorator(TLongDoubleHashMap tLongDoubleHashMap) {
        this._map = tLongDoubleHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Double put(Long l, Double d) {
        return wrapValue(this._map.put(unwrapKey(l), unwrapValue(d)));
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
            if (!(key instanceof Long) || !(value instanceof Double)) {
                break;
            }
            long jUnwrapKey = unwrapKey(key);
            double dUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(jUnwrapKey) || dUnwrapValue != this._map.get(jUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Double get(Object obj) {
        return get((Long) obj);
    }

    public Double get(Long l) {
        long jUnwrapKey = unwrapKey(l);
        double d = this._map.get(jUnwrapKey);
        if (d == 0.0d) {
            if (this._map.containsKey(jUnwrapKey)) {
                return wrapValue(d);
            }
            return null;
        }
        return wrapValue(d);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._map.clear();
    }

    public Double remove(Long l) {
        return wrapValue(this._map.remove(unwrapKey(l)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Double remove(Object obj) {
        return remove((Long) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TLongDoubleHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Long, Double>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TLongDoubleHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TLongDoubleHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TLongDoubleHashMapDecorator.this.containsKey(key) && TLongDoubleHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Long, Double>> iterator() {
            return new Iterator<Map.Entry<Long, Double>>() { // from class: gnu.trove.decorator.TLongDoubleHashMapDecorator.1.1
                private final TLongDoubleIterator it;

                {
                    this.it = TLongDoubleHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Long, Double> next() {
                    this.it.advance();
                    return new Map.Entry<Long, Double>(TLongDoubleHashMapDecorator.this.wrapValue(this.it.value()), TLongDoubleHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TLongDoubleHashMapDecorator.1.1.1
                        private Double val;
                        final /* synthetic */ Long val$key;
                        final /* synthetic */ Double val$v;

                        {
                            this.val$v = d;
                            this.val$key = l;
                            this.val = d;
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
                        public Double getValue() {
                            return this.val;
                        }

                        @Override // java.util.Map.Entry
                        public int hashCode() {
                            return this.val$key.hashCode() + this.val.hashCode();
                        }

                        @Override // java.util.Map.Entry
                        public Double setValue(Double d) {
                            this.val = d;
                            return TLongDoubleHashMapDecorator.this.put(this.val$key, d);
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
        public boolean add(Map.Entry<Long, Double> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Long, Double>> collection) {
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
            TLongDoubleHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Long, Double>> entrySet() {
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
    public void putAll(Map<? extends Long, ? extends Double> map) {
        Iterator<Map.Entry<? extends Long, ? extends Double>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Long, ? extends Double> next = it.next();
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

    protected Double wrapValue(double d) {
        return new Double(d);
    }

    protected double unwrapValue(Object obj) {
        return ((Double) obj).doubleValue();
    }
}
