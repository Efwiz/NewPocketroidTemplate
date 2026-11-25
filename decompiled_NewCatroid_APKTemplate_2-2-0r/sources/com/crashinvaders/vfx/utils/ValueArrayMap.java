package com.crashinvaders.vfx.utils;

import com.badlogic.gdx.utils.Array;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class ValueArrayMap<K, V> {
    private final Map<K, V> map;
    private final Array<K> tmpKeyArray;
    private final Array<V> values;

    public ValueArrayMap() {
        this(16);
    }

    public ValueArrayMap(int i) {
        this.map = new HashMap(i);
        this.values = new Array<>(true, i);
        this.tmpKeyArray = new Array<>(i);
    }

    public void put(K k, V v) {
        this.map.put(k, v);
        this.values.add(v);
    }

    public V get(K k) {
        return this.map.get(k);
    }

    public V getValueAt(int i) {
        return this.values.get(i);
    }

    public V remove(K k) {
        V vRemove = this.map.remove(k);
        if (vRemove != null) {
            this.values.removeValue(vRemove, true);
        }
        return vRemove;
    }

    public V removeByValue(V v) {
        return remove(findKey(v));
    }

    public K findKey(V v) {
        for (Map.Entry<K, V> entry : this.map.entrySet()) {
            if (entry.getValue() == v) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void clear() {
        this.map.clear();
        this.values.clear();
    }

    public boolean contains(K k) {
        return this.map.containsKey(k);
    }

    public int size() {
        return this.map.size();
    }

    public void sort(Comparator<V> comparator) {
        this.values.sort(comparator);
    }

    public Array<V> getValues() {
        return this.values;
    }

    public Array<K> getKeys() {
        Array<K> array = this.tmpKeyArray;
        array.clear();
        Iterator<K> it = this.map.keySet().iterator();
        while (it.hasNext()) {
            array.add(it.next());
        }
        return array;
    }

    public String toString() {
        return this.values.toString();
    }

    public String toString(String str) {
        return this.values.toString(str);
    }
}
