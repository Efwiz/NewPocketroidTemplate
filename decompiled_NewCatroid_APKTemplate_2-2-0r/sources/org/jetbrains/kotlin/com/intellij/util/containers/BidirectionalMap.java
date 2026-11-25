package org.jetbrains.kotlin.com.intellij.util.containers;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class BidirectionalMap<K, V> implements Map<K, V> {
    private final Map<K, V> myKeyToValueMap = new HashMap();
    private final Map<V, List<K>> myValueToKeysMap = new HashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/BidirectionalMap";
        } else {
            objArr[0] = "t";
        }
        if (i == 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/BidirectionalMap";
        } else if (i == 2) {
            objArr[1] = CompoundEntry.NAME_values;
        } else if (i != 3) {
            objArr[1] = "keySet";
        } else {
            objArr[1] = "entrySet";
        }
        if (i == 1) {
            objArr[2] = "putAll";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Override // java.util.Map
    public final V put(K k, V v) {
        V vPut = this.myKeyToValueMap.put(k, v);
        if (vPut != null) {
            if (vPut.equals(v)) {
                return vPut;
            }
            List<K> list = this.myValueToKeysMap.get(vPut);
            list.remove(k);
            if (list.isEmpty()) {
                this.myValueToKeysMap.remove(vPut);
            }
        }
        this.myValueToKeysMap.computeIfAbsent(v, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.BidirectionalMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BidirectionalMap.lambda$put$0(obj);
            }
        }).add(k);
        return vPut;
    }

    static /* synthetic */ List lambda$put$0(Object obj) {
        return new SmartList();
    }

    @Override // java.util.Map
    public final void clear() {
        this.myKeyToValueMap.clear();
        this.myValueToKeysMap.clear();
    }

    public final List<K> getKeysByValue(V v) {
        return this.myValueToKeysMap.get(v);
    }

    @Override // java.util.Map
    public final Set<K> keySet() {
        Set<K> setKeySet = this.myKeyToValueMap.keySet();
        if (setKeySet == null) {
            $$$reportNull$$$0(0);
        }
        return setKeySet;
    }

    @Override // java.util.Map
    public final int size() {
        return this.myKeyToValueMap.size();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.myKeyToValueMap.isEmpty();
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.myKeyToValueMap.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.myValueToKeysMap.containsKey(obj);
    }

    @Override // java.util.Map
    public final V get(Object obj) {
        return this.myKeyToValueMap.get(obj);
    }

    @Override // java.util.Map
    public final V remove(Object obj) {
        V vRemove = this.myKeyToValueMap.remove(obj);
        List<K> list = this.myValueToKeysMap.get(vRemove);
        if (list != null) {
            if (list.size() > 1) {
                list.remove(obj);
            } else {
                this.myValueToKeysMap.remove(vRemove);
            }
        }
        return vRemove;
    }

    @Override // java.util.Map
    public final void putAll(Map<? extends K, ? extends V> map) {
        if (map == null) {
            $$$reportNull$$$0(1);
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public final Collection<V> values() {
        Set<V> setKeySet = this.myValueToKeysMap.keySet();
        if (setKeySet == null) {
            $$$reportNull$$$0(2);
        }
        return setKeySet;
    }

    @Override // java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> setEntrySet = this.myKeyToValueMap.entrySet();
        if (setEntrySet == null) {
            $$$reportNull$$$0(3);
        }
        return setEntrySet;
    }

    public final String toString() {
        return this.myKeyToValueMap.toString();
    }
}
