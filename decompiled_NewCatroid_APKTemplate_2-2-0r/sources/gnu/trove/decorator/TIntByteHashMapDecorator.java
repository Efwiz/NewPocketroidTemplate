package gnu.trove.decorator;

import gnu.trove.TIntByteHashMap;
import gnu.trove.TIntByteIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TIntByteHashMapDecorator extends AbstractMap<Integer, Byte> {
    protected final TIntByteHashMap _map;

    public TIntByteHashMapDecorator(TIntByteHashMap tIntByteHashMap) {
        this._map = tIntByteHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Byte put(Integer num, Byte b) {
        return wrapValue(this._map.put(unwrapKey(num), unwrapValue(b)));
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
            if (!(key instanceof Integer) || !(value instanceof Byte)) {
                break;
            }
            int iUnwrapKey = unwrapKey(key);
            byte bUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(iUnwrapKey) || bUnwrapValue != this._map.get(iUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Byte get(Object obj) {
        return get((Integer) obj);
    }

    public Byte get(Integer num) {
        int iUnwrapKey = unwrapKey(num);
        byte b = this._map.get(iUnwrapKey);
        if (b == 0) {
            if (this._map.containsKey(iUnwrapKey)) {
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

    public Byte remove(Integer num) {
        return wrapValue(this._map.remove(unwrapKey(num)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Byte remove(Object obj) {
        return remove((Integer) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TIntByteHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Integer, Byte>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TIntByteHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TIntByteHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TIntByteHashMapDecorator.this.containsKey(key) && TIntByteHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Integer, Byte>> iterator() {
            return new Iterator<Map.Entry<Integer, Byte>>() { // from class: gnu.trove.decorator.TIntByteHashMapDecorator.1.1
                private final TIntByteIterator it;

                {
                    this.it = TIntByteHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Integer, Byte> next() {
                    this.it.advance();
                    return new Map.Entry<Integer, Byte>(TIntByteHashMapDecorator.this.wrapValue(this.it.value()), TIntByteHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TIntByteHashMapDecorator.1.1.1
                        private Byte val;
                        final /* synthetic */ Integer val$key;
                        final /* synthetic */ Byte val$v;

                        {
                            this.val$v = b;
                            this.val$key = num;
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
                        public Integer getKey() {
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
                            return TIntByteHashMapDecorator.this.put(this.val$key, b);
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
        public boolean add(Map.Entry<Integer, Byte> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Integer, Byte>> collection) {
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
            TIntByteHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Integer, Byte>> entrySet() {
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
    public void putAll(Map<? extends Integer, ? extends Byte> map) {
        Iterator<Map.Entry<? extends Integer, ? extends Byte>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Integer, ? extends Byte> next = it.next();
            put(next.getKey(), next.getValue());
            size = i;
        }
    }

    protected Integer wrapKey(int i) {
        return new Integer(i);
    }

    protected int unwrapKey(Object obj) {
        return ((Integer) obj).intValue();
    }

    protected Byte wrapValue(byte b) {
        return new Byte(b);
    }

    protected byte unwrapValue(Object obj) {
        return ((Byte) obj).byteValue();
    }
}
