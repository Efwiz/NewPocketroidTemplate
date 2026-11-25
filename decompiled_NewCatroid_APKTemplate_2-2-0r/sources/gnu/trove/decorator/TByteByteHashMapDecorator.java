package gnu.trove.decorator;

import gnu.trove.TByteByteHashMap;
import gnu.trove.TByteByteIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TByteByteHashMapDecorator extends AbstractMap<Byte, Byte> {
    protected final TByteByteHashMap _map;

    public TByteByteHashMapDecorator(TByteByteHashMap tByteByteHashMap) {
        this._map = tByteByteHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Byte put(Byte b, Byte b2) {
        return wrapValue(this._map.put(unwrapKey(b), unwrapValue(b2)));
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
            if (!(key instanceof Byte) || !(value instanceof Byte)) {
                break;
            }
            byte bUnwrapKey = unwrapKey(key);
            byte bUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(bUnwrapKey) || bUnwrapValue != this._map.get(bUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Byte get(Object obj) {
        return get((Byte) obj);
    }

    public Byte get(Byte b) {
        byte bUnwrapKey = unwrapKey(b);
        byte b2 = this._map.get(bUnwrapKey);
        if (b2 == 0) {
            if (this._map.containsKey(bUnwrapKey)) {
                return wrapValue(b2);
            }
            return null;
        }
        return wrapValue(b2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._map.clear();
    }

    public Byte remove(Byte b) {
        return wrapValue(this._map.remove(unwrapKey(b)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Byte remove(Object obj) {
        return remove((Byte) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TByteByteHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Byte, Byte>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TByteByteHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TByteByteHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TByteByteHashMapDecorator.this.containsKey(key) && TByteByteHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Byte, Byte>> iterator() {
            return new Iterator<Map.Entry<Byte, Byte>>() { // from class: gnu.trove.decorator.TByteByteHashMapDecorator.1.1
                private final TByteByteIterator it;

                {
                    this.it = TByteByteHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Byte, Byte> next() {
                    this.it.advance();
                    return new Map.Entry<Byte, Byte>(TByteByteHashMapDecorator.this.wrapValue(this.it.value()), TByteByteHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TByteByteHashMapDecorator.1.1.1
                        private Byte val;
                        final /* synthetic */ Byte val$key;
                        final /* synthetic */ Byte val$v;

                        {
                            this.val$v = b;
                            this.val$key = b;
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

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.util.Map.Entry
                        public Byte getKey() {
                            return this.val$key;
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

                        @Override // java.util.Map.Entry
                        public Byte setValue(Byte b) {
                            this.val = b;
                            return TByteByteHashMapDecorator.this.put(this.val$key, b);
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
        public boolean add(Map.Entry<Byte, Byte> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Byte, Byte>> collection) {
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
            TByteByteHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Byte, Byte>> entrySet() {
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
    public void putAll(Map<? extends Byte, ? extends Byte> map) {
        Iterator<Map.Entry<? extends Byte, ? extends Byte>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Byte, ? extends Byte> next = it.next();
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

    protected Byte wrapValue(byte b) {
        return new Byte(b);
    }

    protected byte unwrapValue(Object obj) {
        return ((Byte) obj).byteValue();
    }
}
