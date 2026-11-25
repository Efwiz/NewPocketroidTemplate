package org.eclipse.jdt.internal.compiler.apt.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes14.dex */
public class ManyToMany<T1, T2> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Map<T1, Set<T2>> _forward = new HashMap();
    private final Map<T2, Set<T1>> _reverse = new HashMap();
    private boolean _dirty = false;

    public synchronized boolean clear() {
        boolean z;
        z = (this._forward.isEmpty() && this._reverse.isEmpty()) ? false : true;
        this._reverse.clear();
        this._forward.clear();
        this._dirty |= z;
        return z;
    }

    public synchronized void clearDirtyBit() {
        this._dirty = false;
    }

    public synchronized boolean containsKey(T1 t1) {
        return this._forward.containsKey(t1);
    }

    public synchronized boolean containsKeyValuePair(T1 t1, T2 t2) {
        Set<T2> set = this._forward.get(t1);
        if (set == null) {
            return false;
        }
        return set.contains(t2);
    }

    public synchronized boolean containsValue(T2 t2) {
        return this._reverse.containsKey(t2);
    }

    public synchronized Set<T1> getKeys(T2 t2) {
        Set<T1> set = this._reverse.get(t2);
        if (set == null) {
            return Collections.emptySet();
        }
        return new HashSet(set);
    }

    public synchronized Set<T2> getValues(T1 t1) {
        Set<T2> set = this._forward.get(t1);
        if (set == null) {
            return Collections.emptySet();
        }
        return new HashSet(set);
    }

    public synchronized Set<T1> getKeySet() {
        return new HashSet(this._forward.keySet());
    }

    public synchronized Set<T2> getValueSet() {
        return new HashSet(this._reverse.keySet());
    }

    public synchronized boolean isDirty() {
        return this._dirty;
    }

    public synchronized boolean keyHasOtherValues(T1 t1, T2 t2) {
        Set<T2> set = this._forward.get(t1);
        if (set == null) {
            return false;
        }
        int size = set.size();
        if (size == 0) {
            return false;
        }
        if (size > 1) {
            return true;
        }
        return !set.contains(t2);
    }

    public synchronized boolean put(T1 t1, T2 t2) {
        boolean zAdd;
        Set<T2> hashSet = this._forward.get(t1);
        if (hashSet == null) {
            hashSet = new HashSet<>();
            this._forward.put(t1, hashSet);
        }
        zAdd = hashSet.add(t2);
        this._dirty |= zAdd;
        Set<T1> hashSet2 = this._reverse.get(t2);
        if (hashSet2 == null) {
            hashSet2 = new HashSet<>();
            this._reverse.put(t2, hashSet2);
        }
        hashSet2.add(t1);
        return zAdd;
    }

    public synchronized boolean remove(T1 t1, T2 t2) {
        Set<T2> set = this._forward.get(t1);
        if (set == null) {
            return false;
        }
        boolean zRemove = set.remove(t2);
        if (set.isEmpty()) {
            this._forward.remove(t1);
        }
        if (zRemove) {
            this._dirty = true;
            Set<T1> set2 = this._reverse.get(t2);
            set2.remove(t1);
            if (set2.isEmpty()) {
                this._reverse.remove(t2);
            }
        }
        return zRemove;
    }

    public synchronized boolean removeKey(T1 t1) {
        Set<T2> set = this._forward.get(t1);
        if (set == null) {
            return false;
        }
        for (T2 t2 : set) {
            Set<T1> set2 = this._reverse.get(t2);
            if (set2 != null) {
                set2.remove(t1);
                if (set2.isEmpty()) {
                    this._reverse.remove(t2);
                }
            }
        }
        this._forward.remove(t1);
        this._dirty = true;
        return true;
    }

    public synchronized boolean removeValue(T2 t2) {
        Set<T1> set = this._reverse.get(t2);
        if (set == null) {
            return false;
        }
        for (T1 t1 : set) {
            Set<T2> set2 = this._forward.get(t1);
            if (set2 != null) {
                set2.remove(t2);
                if (set2.isEmpty()) {
                    this._forward.remove(t1);
                }
            }
        }
        this._reverse.remove(t2);
        this._dirty = true;
        return true;
    }

    public synchronized boolean valueHasOtherKeys(T2 t2, T1 t1) {
        Set<T1> set = this._reverse.get(t1);
        if (set == null) {
            return false;
        }
        int size = set.size();
        if (size == 0) {
            return false;
        }
        if (size > 1) {
            return true;
        }
        return !set.contains(t1);
    }

    private boolean checkIntegrity() {
        for (Map.Entry<T1, Set<T2>> entry : this._forward.entrySet()) {
            Set<T2> value = entry.getValue();
            if (value.isEmpty()) {
                throw new IllegalStateException("Integrity compromised: forward map contains an empty set");
            }
            for (T2 t2 : value) {
                Set<T1> set = this._reverse.get(t2);
                if (set == null || !set.contains(entry.getKey())) {
                    throw new IllegalStateException("Integrity compromised: forward map contains an entry missing from reverse map: " + t2);
                }
            }
        }
        for (Map.Entry<T2, Set<T1>> entry2 : this._reverse.entrySet()) {
            Set<T1> value2 = entry2.getValue();
            if (value2.isEmpty()) {
                throw new IllegalStateException("Integrity compromised: reverse map contains an empty set");
            }
            for (T1 t1 : value2) {
                Set<T2> set2 = this._forward.get(t1);
                if (set2 == null || !set2.contains(entry2.getKey())) {
                    throw new IllegalStateException("Integrity compromised: reverse map contains an entry missing from forward map: " + t1);
                }
            }
        }
        return true;
    }
}
