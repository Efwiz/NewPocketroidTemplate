package gnu.trove.decorator;

import gnu.trove.TFloatFloatHashMap;
import gnu.trove.TFloatFloatIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatFloatHashMapDecorator extends AbstractMap<Float, Float> {
    protected final TFloatFloatHashMap _map;

    public TFloatFloatHashMapDecorator(TFloatFloatHashMap tFloatFloatHashMap) {
        this._map = tFloatFloatHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float put(Float f, Float f2) {
        return wrapValue(this._map.put(unwrapKey(f), unwrapValue(f2)));
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
            if (!(key instanceof Float) || !(value instanceof Float)) {
                break;
            }
            float fUnwrapKey = unwrapKey(key);
            float fUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(fUnwrapKey) || fUnwrapValue != this._map.get(fUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float get(Object obj) {
        return get((Float) obj);
    }

    public Float get(Float f) {
        float fUnwrapKey = unwrapKey(f);
        float f2 = this._map.get(fUnwrapKey);
        if (f2 == 0.0f) {
            if (this._map.containsKey(fUnwrapKey)) {
                return wrapValue(f2);
            }
            return null;
        }
        return wrapValue(f2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._map.clear();
    }

    public Float remove(Float f) {
        return wrapValue(this._map.remove(unwrapKey(f)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float remove(Object obj) {
        return remove((Float) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TFloatFloatHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Float, Float>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TFloatFloatHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TFloatFloatHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TFloatFloatHashMapDecorator.this.containsKey(key) && TFloatFloatHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Float, Float>> iterator() {
            return new Iterator<Map.Entry<Float, Float>>() { // from class: gnu.trove.decorator.TFloatFloatHashMapDecorator.1.1
                private final TFloatFloatIterator it;

                {
                    this.it = TFloatFloatHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Float, Float> next() {
                    this.it.advance();
                    return new Map.Entry<Float, Float>(TFloatFloatHashMapDecorator.this.wrapValue(this.it.value()), TFloatFloatHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TFloatFloatHashMapDecorator.1.1.1
                        private Float val;
                        final /* synthetic */ Float val$key;
                        final /* synthetic */ Float val$v;

                        {
                            this.val$v = f;
                            this.val$key = f;
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
                        public Float getKey() {
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
                            return TFloatFloatHashMapDecorator.this.put(this.val$key, f);
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
        public boolean add(Map.Entry<Float, Float> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Float, Float>> collection) {
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
            TFloatFloatHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Float, Float>> entrySet() {
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
    public void putAll(Map<? extends Float, ? extends Float> map) {
        Iterator<Map.Entry<? extends Float, ? extends Float>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Float, ? extends Float> next = it.next();
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

    protected Float wrapValue(float f) {
        return new Float(f);
    }

    protected float unwrapValue(Object obj) {
        return ((Float) obj).floatValue();
    }
}
