package org.antlr.runtime.misc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes12.dex */
public class DoubleKeyMap<Key1, Key2, Value> {
    Map<Key1, Map<Key2, Value>> data = new LinkedHashMap();

    public Value put(Key1 key1, Key2 key2, Value value) {
        Value value2;
        Map<Key2, Value> linkedHashMap = this.data.get(key1);
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap<>();
            this.data.put(key1, linkedHashMap);
            value2 = null;
        } else {
            value2 = linkedHashMap.get(key2);
        }
        linkedHashMap.put(key2, value);
        return value2;
    }

    public Value get(Key1 key1, Key2 key2) {
        Map<Key2, Value> map = this.data.get(key1);
        if (map == null) {
            return null;
        }
        return map.get(key2);
    }

    public Map<Key2, Value> get(Key1 key1) {
        return this.data.get(key1);
    }

    public Collection<Value> values(Key1 key1) {
        Map<Key2, Value> map = this.data.get(key1);
        if (map == null) {
            return null;
        }
        return map.values();
    }

    public Set<Key1> keySet() {
        return this.data.keySet();
    }

    public Set<Key2> keySet(Key1 key1) {
        Map<Key2, Value> map = this.data.get(key1);
        if (map == null) {
            return null;
        }
        return map.keySet();
    }

    public Collection<Value> values() {
        HashSet hashSet = new HashSet();
        Iterator<Map<Key2, Value>> it = this.data.values().iterator();
        while (it.hasNext()) {
            Iterator<Value> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                hashSet.add(it2.next());
            }
        }
        return hashSet;
    }
}
