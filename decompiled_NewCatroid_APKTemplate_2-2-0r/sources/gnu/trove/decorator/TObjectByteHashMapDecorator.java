package gnu.trove.decorator;

import gnu.trove.TObjectByteHashMap;
import gnu.trove.TObjectByteIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectByteHashMapDecorator<V> extends AbstractMap<V, Byte> implements Map<V, Byte> {
    protected final TObjectByteHashMap<V> _map;

    /* JADX WARN: Multi-variable type inference failed */
    protected final V unwrapKey(Object obj) {
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final V wrapKey(Object obj) {
        return obj;
    }

    public TObjectByteHashMapDecorator(TObjectByteHashMap<V> tObjectByteHashMap) {
        this._map = tObjectByteHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Byte put(V v, Byte b) {
        return wrapValue(this._map.put(unwrapKey(v), unwrapValue(b)));
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
            if (!(value instanceof Byte)) {
                break;
            }
            V vUnwrapKey = unwrapKey(key);
            byte bUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(vUnwrapKey) || bUnwrapValue != this._map.get(vUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.AbstractMap, java.util.Map
    public Byte get(Object obj) {
        V vUnwrapKey = unwrapKey(obj);
        byte b = this._map.get(vUnwrapKey);
        if (b == 0) {
            if (this._map.containsKey(vUnwrapKey)) {
                return wrapValue(b);
            }
            return null;
        }
        return wrapValue(b);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._map.clear();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.AbstractMap, java.util.Map
    public Byte remove(Object obj) {
        return wrapValue(this._map.remove(unwrapKey(obj)));
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TObjectByteHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<V, Byte>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TObjectByteHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TObjectByteHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TObjectByteHashMapDecorator.this.containsKey(key) && TObjectByteHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<V, Byte>> iterator() {
            return new Iterator<Map.Entry<V, Byte>>() { // from class: gnu.trove.decorator.TObjectByteHashMapDecorator.1.1
                private final TObjectByteIterator<V> it;

                {
                    this.it = TObjectByteHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<V, Byte> next() {
                    this.it.advance();
                    return new Map.Entry<V, Byte>(TObjectByteHashMapDecorator.this.wrapValue(this.it.value()), TObjectByteHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TObjectByteHashMapDecorator.1.1.1
                        private Byte val;
                        final /* synthetic */ Object val$key;
                        final /* synthetic */ Byte val$v;

                        {
                            this.val$v = b;
                            this.val$key = obj;
                            this.val = b;
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
                        public Byte getValue() {
                            return this.val;
                        }

                        @Override // java.util.Map.Entry
                        public int hashCode() {
                            return this.val$key.hashCode() + this.val.hashCode();
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Map.Entry
                        public Byte setValue(Byte b) {
                            this.val = b;
                            return TObjectByteHashMapDecorator.this.put(this.val$key, b);
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
        public boolean add(Map.Entry<V, Byte> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<V, Byte>> collection) {
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
            TObjectByteHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<V, Byte>> entrySet() {
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
    public void putAll(Map<? extends V, ? extends Byte> map) {
        Iterator<Map.Entry<? extends V, ? extends Byte>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends V, ? extends Byte> next = it.next();
            put((Object) next.getKey(), next.getValue());
            size = i;
        }
    }

    protected Byte wrapValue(byte b) {
        return new Byte(b);
    }

    protected byte unwrapValue(Object obj) {
        return ((Byte) obj).byteValue();
    }
}
