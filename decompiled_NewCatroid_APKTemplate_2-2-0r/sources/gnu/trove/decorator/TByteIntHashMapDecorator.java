package gnu.trove.decorator;

import gnu.trove.TByteIntHashMap;
import gnu.trove.TByteIntIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TByteIntHashMapDecorator extends AbstractMap<Byte, Integer> {
    protected final TByteIntHashMap _map;

    public TByteIntHashMapDecorator(TByteIntHashMap tByteIntHashMap) {
        this._map = tByteIntHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Integer put(Byte b, Integer num) {
        return wrapValue(this._map.put(unwrapKey(b), unwrapValue(num)));
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
            if (!(key instanceof Byte) || !(value instanceof Integer)) {
                break;
            }
            byte bUnwrapKey = unwrapKey(key);
            int iUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(bUnwrapKey) || iUnwrapValue != this._map.get(bUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Integer get(Object obj) {
        return get((Byte) obj);
    }

    public Integer get(Byte b) {
        byte bUnwrapKey = unwrapKey(b);
        int i = this._map.get(bUnwrapKey);
        if (i == 0) {
            if (this._map.containsKey(bUnwrapKey)) {
                return wrapValue(i);
            }
            return null;
        }
        return wrapValue(i);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._map.clear();
    }

    public Integer remove(Byte b) {
        return wrapValue(this._map.remove(unwrapKey(b)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Integer remove(Object obj) {
        return remove((Byte) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TByteIntHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Byte, Integer>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TByteIntHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TByteIntHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TByteIntHashMapDecorator.this.containsKey(key) && TByteIntHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Byte, Integer>> iterator() {
            return new Iterator<Map.Entry<Byte, Integer>>() { // from class: gnu.trove.decorator.TByteIntHashMapDecorator.1.1
                private final TByteIntIterator it;

                {
                    this.it = TByteIntHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Byte, Integer> next() {
                    this.it.advance();
                    return new Map.Entry<Byte, Integer>(TByteIntHashMapDecorator.this.wrapValue(this.it.value()), TByteIntHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TByteIntHashMapDecorator.1.1.1
                        private Integer val;
                        final /* synthetic */ Byte val$key;
                        final /* synthetic */ Integer val$v;

                        {
                            this.val$v = num;
                            this.val$key = b;
                            this.val = num;
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
                        public Integer getValue() {
                            return this.val;
                        }

                        @Override // java.util.Map.Entry
                        public int hashCode() {
                            return this.val$key.hashCode() + this.val.hashCode();
                        }

                        @Override // java.util.Map.Entry
                        public Integer setValue(Integer num) {
                            this.val = num;
                            return TByteIntHashMapDecorator.this.put(this.val$key, num);
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
        public boolean add(Map.Entry<Byte, Integer> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Byte, Integer>> collection) {
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
            TByteIntHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Byte, Integer>> entrySet() {
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
    public void putAll(Map<? extends Byte, ? extends Integer> map) {
        Iterator<Map.Entry<? extends Byte, ? extends Integer>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Byte, ? extends Integer> next = it.next();
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

    protected Integer wrapValue(int i) {
        return new Integer(i);
    }

    protected int unwrapValue(Object obj) {
        return ((Integer) obj).intValue();
    }
}
