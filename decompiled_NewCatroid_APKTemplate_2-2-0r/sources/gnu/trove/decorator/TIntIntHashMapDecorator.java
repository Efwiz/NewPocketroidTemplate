package gnu.trove.decorator;

import gnu.trove.TIntIntHashMap;
import gnu.trove.TIntIntIterator;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TIntIntHashMapDecorator extends AbstractMap<Integer, Integer> {
    protected final TIntIntHashMap _map;

    public TIntIntHashMapDecorator(TIntIntHashMap tIntIntHashMap) {
        this._map = tIntIntHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Integer put(Integer num, Integer num2) {
        return wrapValue(this._map.put(unwrapKey(num), unwrapValue(num2)));
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
            if (!(key instanceof Integer) || !(value instanceof Integer)) {
                break;
            }
            int iUnwrapKey = unwrapKey(key);
            int iUnwrapValue = unwrapValue(value);
            if (!this._map.containsKey(iUnwrapKey) || iUnwrapValue != this._map.get(iUnwrapKey)) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Integer get(Object obj) {
        return get((Integer) obj);
    }

    public Integer get(Integer num) {
        int iUnwrapKey = unwrapKey(num);
        int i = this._map.get(iUnwrapKey);
        if (i == 0) {
            if (this._map.containsKey(iUnwrapKey)) {
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

    public Integer remove(Integer num) {
        return wrapValue(this._map.remove(unwrapKey(num)));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Integer remove(Object obj) {
        return remove((Integer) obj);
    }

    /* JADX INFO: renamed from: gnu.trove.decorator.TIntIntHashMapDecorator$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Map.Entry<Integer, Integer>> {
        AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TIntIntHashMapDecorator.this._map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TIntIntHashMapDecorator.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            return TIntIntHashMapDecorator.this.containsKey(key) && TIntIntHashMapDecorator.this.get(key).equals(entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Integer, Integer>> iterator() {
            return new Iterator<Map.Entry<Integer, Integer>>() { // from class: gnu.trove.decorator.TIntIntHashMapDecorator.1.1
                private final TIntIntIterator it;

                {
                    this.it = TIntIntHashMapDecorator.this._map.iterator();
                }

                @Override // java.util.Iterator
                public Map.Entry<Integer, Integer> next() {
                    this.it.advance();
                    return new Map.Entry<Integer, Integer>(TIntIntHashMapDecorator.this.wrapValue(this.it.value()), TIntIntHashMapDecorator.this.wrapKey(this.it.key())) { // from class: gnu.trove.decorator.TIntIntHashMapDecorator.1.1.1
                        private Integer val;
                        final /* synthetic */ Integer val$key;
                        final /* synthetic */ Integer val$v;

                        {
                            this.val$v = num;
                            this.val$key = num;
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
                        public Integer getKey() {
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
                            return TIntIntHashMapDecorator.this.put(this.val$key, num);
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
        public boolean add(Map.Entry<Integer, Integer> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<Integer, Integer>> collection) {
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
            TIntIntHashMapDecorator.this.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<Integer, Integer>> entrySet() {
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
    public void putAll(Map<? extends Integer, ? extends Integer> map) {
        Iterator<Map.Entry<? extends Integer, ? extends Integer>> it = map.entrySet().iterator();
        int size = map.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            Map.Entry<? extends Integer, ? extends Integer> next = it.next();
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

    protected Integer wrapValue(int i) {
        return new Integer(i);
    }

    protected int unwrapValue(Object obj) {
        return ((Integer) obj).intValue();
    }
}
