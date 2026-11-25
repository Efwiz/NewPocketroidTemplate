package gnu.trove.decorator;

import gnu.trove.TByteFloatHashMap;
import gnu.trove.TByteFloatIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TByteFloatHashMapDecorator extends AbstractMap<Byte, Float> {
    protected final TByteFloatHashMap _map;

    public TByteFloatHashMapDecorator(TByteFloatHashMap tByteFloatHashMap) {
        this._map = tByteFloatHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float put(Byte b, Float f) {
        return wrapValue(this._map.put(unwrapKey(b), unwrapValue(f)));
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
            if (!(key instanceof Byte) || !(value instanceof Float)) {
                break;
            }
            byte bUnwrapKey = unwrapKey(key);
            float fUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(bUnwrapKey) || fUnwrapValue != this._map.get(bUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float get(Object obj) {
        return get((Byte) obj);
    }

    public Float get(Byte b) {
        byte bUnwrapKey = unwrapKey(b);
        float f = this._map.get(bUnwrapKey);
        if (f == 0.0f) {
            if (this._map.containsKey(bUnwrapKey)) {
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

    public Float remove(Byte b) {
        return wrapValue(this._map.remove(unwrapKey(b)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Float remove(Object obj) {
        return remove((Byte) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TByteFloatHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Byte, Float>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TByteFloatHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TByteFloatHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TByteFloatHashMapDecorator.this.containsKey(key) && TByteFloatHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Byte, Float>> iterator() {
            return new Iterator<Map.Entry<Byte, Float>>() { // from class: gnu.trove.decorator.TByteFloatHashMapDecorator.1.1
                private final TByteFloatIterator it;

                {
                    this.it = TByteFloatHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Byte, Float> next() {
                    this.it.advance();
                    return new Map.Entry<Byte, Float>(TByteFloatHashMapDecorator.this.wrapValue(this.it.value()), TByteFloatHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TByteFloatHashMapDecorator.1.1.1
                        private Float val;
                        final /* synthetic */ Byte val$key;
                        final /* synthetic */ Float val$v;

                        {
                            this.val$v = f;
                            this.val$key = b;
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
                        public Byte getKey() {
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
                            return TByteFloatHashMapDecorator.this.put(this.val$key, f);
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
        public boolean add(Map.Entry<Byte, Float> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Byte, Float>> collection) {
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
            TByteFloatHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Byte, Float>> entrySet() {
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
    public void putAll(Map<? extends Byte, ? extends Float> map) {
        Iterator<Map.Entry<? extends Byte, ? extends Float>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Byte, ? extends Float> next = it.next();
            put(next.getKey(), next.getValue());
            size = i;
        }
    }

    protected Byte wrapKey(byte b) {
        return new Byte(b);
    }

    protected byte unwrapKey(Object obj) {
        return ((Byte) obj).byteValue();
    }

    protected Float wrapValue(float f) {
        return new Float(f);
    }

    protected float unwrapValue(Object obj) {
        return ((Float) obj).floatValue();
    }
}
