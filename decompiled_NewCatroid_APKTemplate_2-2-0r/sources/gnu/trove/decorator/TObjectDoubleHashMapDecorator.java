package gnu.trove.decorator;

import gnu.trove.TObjectDoubleHashMap;
import gnu.trove.TObjectDoubleIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectDoubleHashMapDecorator<V> extends AbstractMap<V, Double> implements Map<V, Double> {
    protected final TObjectDoubleHashMap<V> _map;

    /* JADX WARN: Multi-variable type inference failed */
    protected final V unwrapKey(Object obj) {
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final V wrapKey(Object obj) {
        return obj;
    }

    public TObjectDoubleHashMapDecorator(TObjectDoubleHashMap<V> tObjectDoubleHashMap) {
        this._map = tObjectDoubleHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Double put(V v, Double d) {
        return wrapValue(this._map.put(unwrapKey(v), unwrapValue(d)));
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
            if (!(value instanceof Double)) {
                break;
            }
            V vUnwrapKey = unwrapKey(key);
            double dUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(vUnwrapKey) || dUnwrapValue != this._map.get(vUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.AbstractMap, java.util.Map
    public Double get(Object obj) {
        V vUnwrapKey = unwrapKey(obj);
        double d = this._map.get(vUnwrapKey);
        if (d == 0.0d) {
            if (this._map.containsKey(vUnwrapKey)) {
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

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.AbstractMap, java.util.Map
    public Double remove(Object obj) {
        return wrapValue(this._map.remove(unwrapKey(obj)));
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TObjectDoubleHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<V, Double>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TObjectDoubleHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TObjectDoubleHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TObjectDoubleHashMapDecorator.this.containsKey(key) && TObjectDoubleHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<V, Double>> iterator() {
            return new Iterator<Map.Entry<V, Double>>() { // from class: gnu.trove.decorator.TObjectDoubleHashMapDecorator.1.1
                private final TObjectDoubleIterator<V> it;

                {
                    this.it = TObjectDoubleHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<V, Double> next() {
                    this.it.advance();
                    return new Map.Entry<V, Double>(TObjectDoubleHashMapDecorator.this.wrapValue(this.it.value()), TObjectDoubleHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TObjectDoubleHashMapDecorator.1.1.1
                        private Double val;
                        final /* synthetic */ Object val$key;
                        final /* synthetic */ Double val$v;

                        {
                            this.val$v = d;
                            this.val$key = obj;
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

                        @Override // java.util.Map.Entry
                        public V getKey() {
                            return (V) this.val$key;
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

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Map.Entry
                        public Double setValue(Double d) {
                            this.val = d;
                            return TObjectDoubleHashMapDecorator.this.put(this.val$key, d);
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
        public boolean add(Map.Entry<V, Double> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<V, Double>> collection) {
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
            TObjectDoubleHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<V, Double>> entrySet() {
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
    public void putAll(Map<? extends V, ? extends Double> map) {
        Iterator<Map.Entry<? extends V, ? extends Double>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends V, ? extends Double> next = it.next();
            put((Object) next.getKey(), next.getValue());
            size = i;
        }
    }

    protected Double wrapValue(double d) {
        return new Double(d);
    }

    protected double unwrapValue(Object obj) {
        return ((Double) obj).doubleValue();
    }
}
