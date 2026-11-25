package com.reandroid.utils.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class GroupMap<K, V> {
    private final int initialSize;
    private Map<K, Object> itemsMap;

    public GroupMap(int i) {
        HashMap map;
        this.initialSize = i;
        if (i > 0) {
            map = new HashMap(i);
        } else {
            map = new HashMap();
        }
        this.itemsMap = map;
    }

    public GroupMap() {
        this(0);
    }

    public void clear() {
        HashMap map;
        Map<K, Object> map2 = this.itemsMap;
        if (map2.size() == 0) {
            return;
        }
        map2.clear();
        if (this.initialSize > 0) {
            map = new HashMap(this.initialSize);
        } else {
            map = new HashMap();
        }
        this.itemsMap = map;
    }

    private void initializeEmpty(int i) {
        HashMap map;
        if (this.itemsMap.size() != 0) {
            return;
        }
        int i2 = this.initialSize;
        if (i < i2) {
            i = i2;
        }
        if (i > 0) {
            map = new HashMap(i);
        } else {
            map = new HashMap();
        }
        this.itemsMap = map;
    }

    public int size() {
        return this.itemsMap.size();
    }

    public boolean contains(K k) {
        return this.itemsMap.containsKey(k);
    }

    public Iterator<V> getAll(K k) {
        Object obj = this.itemsMap.get(k);
        if (obj == null) {
            return EmptyIterator.of();
        }
        if (obj instanceof Groups) {
            return (Iterator<V>) ((Groups) obj).iterator();
        }
        return SingleIterator.of(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public V get(K k) {
        V v = (V) this.itemsMap.get(k);
        if (v == 0) {
            return null;
        }
        return v instanceof Groups ? (V) ((Groups) v).getFirst() : v;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void putAll(Collection<? extends V> collection, Function<? super V, K> function) {
        if (collection.isEmpty()) {
            return;
        }
        initializeEmpty(collection.size());
        for (V v : collection) {
            put(function.apply(v), v);
        }
    }

    public void put(K k, V v) {
        Groups groups;
        if (k == null || v == null) {
            return;
        }
        Map<K, Object> map = this.itemsMap;
        Object obj = map.get(k);
        if (obj == null) {
            map.put(k, v);
            return;
        }
        if (obj instanceof Groups) {
            groups = (Groups) obj;
        } else {
            Groups groups2 = new Groups();
            groups2.add(obj);
            map.remove(k);
            groups = groups2;
        }
        groups.addIfAbsent(v);
    }

    public void updateKey(K k, K k2) {
        Map<K, Object> map;
        Object objRemove;
        if (k == null || k2 == null || k == k2 || (objRemove = (map = this.itemsMap).remove(k)) == null) {
            return;
        }
        map.put(k2, objRemove);
    }

    public String toString() {
        return "size = " + size();
    }

    static class Groups<T> extends ArrayCollection<T> {
        Groups() {
        }

        void addIfAbsent(T t) {
            if (containsFast(t)) {
                return;
            }
            add(t);
        }
    }
}
