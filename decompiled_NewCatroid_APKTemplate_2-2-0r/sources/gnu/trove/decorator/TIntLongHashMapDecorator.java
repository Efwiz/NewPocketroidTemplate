package gnu.trove.decorator;

import gnu.trove.TIntLongHashMap;
import gnu.trove.TIntLongIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TIntLongHashMapDecorator extends AbstractMap<Integer, Long> {
    protected final TIntLongHashMap _map;

    public TIntLongHashMapDecorator(TIntLongHashMap tIntLongHashMap) {
        this._map = tIntLongHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Long put(Integer num, Long l) {
        return wrapValue(this._map.put(unwrapKey(num), unwrapValue(l)));
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
            if (!(key instanceof Integer) || !(value instanceof Long)) {
                break;
            }
            int iUnwrapKey = unwrapKey(key);
            long jUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(iUnwrapKey) || jUnwrapValue != this._map.get(iUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Long get(Object obj) {
        return get((Integer) obj);
    }

    public Long get(Integer num) {
        int iUnwrapKey = unwrapKey(num);
        long j = this._map.get(iUnwrapKey);
        if (j == 0) {
            if (this._map.containsKey(iUnwrapKey)) {
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

    public Long remove(Integer num) {
        return wrapValue(this._map.remove(unwrapKey(num)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Long remove(Object obj) {
        return remove((Integer) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TIntLongHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Integer, Long>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TIntLongHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TIntLongHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TIntLongHashMapDecorator.this.containsKey(key) && TIntLongHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Integer, Long>> iterator() {
            return new Iterator<Map.Entry<Integer, Long>>() { // from class: gnu.trove.decorator.TIntLongHashMapDecorator.1.1
                private final TIntLongIterator it;

                {
                    this.it = TIntLongHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Integer, Long> next() {
                    this.it.advance();
                    return new Map.Entry<Integer, Long>(TIntLongHashMapDecorator.this.wrapValue(this.it.value()), TIntLongHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TIntLongHashMapDecorator.1.1.1
                        private Long val;
                        final /* synthetic */ Integer val$key;
                        final /* synthetic */ Long val$v;

                        {
                            this.val$v = l;
                            this.val$key = num;
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
                        public Integer getKey() {
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
                            return TIntLongHashMapDecorator.this.put(this.val$key, l);
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
        public boolean add(Map.Entry<Integer, Long> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Integer, Long>> collection) {
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
            TIntLongHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Integer, Long>> entrySet() {
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
    public void putAll(Map<? extends Integer, ? extends Long> map) {
        Iterator<Map.Entry<? extends Integer, ? extends Long>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Integer, ? extends Long> next = it.next();
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

    protected Long wrapValue(long j) {
        return new Long(j);
    }

    protected long unwrapValue(Object obj) {
        return ((Long) obj).longValue();
    }
}
