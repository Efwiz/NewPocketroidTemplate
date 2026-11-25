package gnu.trove.decorator;

import gnu.trove.TObjectFloatHashMap;
import gnu.trove.TObjectFloatIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectFloatHashMapDecorator<V> extends AbstractMap<V, Float> implements Map<V, Float> {
    protected final TObjectFloatHashMap<V> _map;

    /* JADX WARN: Multi-variable type inference failed */
    protected final V unwrapKey(Object obj) {
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final V wrapKey(Object obj) {
        return obj;
    }

    public TObjectFloatHashMapDecorator(TObjectFloatHashMap<V> tObjectFloatHashMap) {
        this._map = tObjectFloatHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float put(V v, Float f) {
        return wrapValue(this._map.put(unwrapKey(v), unwrapValue(f)));
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
            if (!(value instanceof Float)) {
                break;
            }
            V vUnwrapKey = unwrapKey(key);
            float fUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(vUnwrapKey) || fUnwrapValue != this._map.get(vUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.AbstractMap, java.util.Map
    public Float get(Object obj) {
        V vUnwrapKey = unwrapKey(obj);
        float f = this._map.get(vUnwrapKey);
        if (f == 0.0f) {
            if (this._map.containsKey(vUnwrapKey)) {
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

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.AbstractMap, java.util.Map
    public Float remove(Object obj) {
        return wrapValue(this._map.remove(unwrapKey(obj)));
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TObjectFloatHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<V, Float>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TObjectFloatHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TObjectFloatHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TObjectFloatHashMapDecorator.this.containsKey(key) && TObjectFloatHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<V, Float>> iterator() {
            return new Iterator<Map.Entry<V, Float>>() { // from class: gnu.trove.decorator.TObjectFloatHashMapDecorator.1.1
                private final TObjectFloatIterator<V> it;

                {
                    this.it = TObjectFloatHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<V, Float> next() {
                    this.it.advance();
                    return new Map.Entry<V, Float>(TObjectFloatHashMapDecorator.this.wrapValue(this.it.value()), TObjectFloatHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TObjectFloatHashMapDecorator.1.1.1
                        private Float val;
                        final /* synthetic */ Object val$key;
                        final /* synthetic */ Float val$v;

                        {
                            this.val$v = f;
                            this.val$key = obj;
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

                        @Override // java.util.Map.Entry
                        public V getKey() {
                            return (V) this.val$key;
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

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Map.Entry
                        public Float setValue(Float f) {
                            this.val = f;
                            return TObjectFloatHashMapDecorator.this.put(this.val$key, f);
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
        public boolean add(Map.Entry<V, Float> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<V, Float>> collection) {
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
            TObjectFloatHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<V, Float>> entrySet() {
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
    public void putAll(Map<? extends V, ? extends Float> map) {
        Iterator<Map.Entry<? extends V, ? extends Float>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends V, ? extends Float> next = it.next();
            put((Object) next.getKey(), next.getValue());
            size = i;
        }
    }

    protected Float wrapValue(float f) {
        return new Float(f);
    }

    protected float unwrapValue(Object obj) {
        return ((Float) obj).floatValue();
    }
}
