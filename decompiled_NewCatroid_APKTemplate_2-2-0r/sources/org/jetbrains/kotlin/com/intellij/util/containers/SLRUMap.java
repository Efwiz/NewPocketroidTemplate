package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap;

/* JADX INFO: loaded from: classes3.dex */
public class SLRUMap<K, V> {
    private static final int FACTOR = Integer.getInteger("idea.slru.factor", 1).intValue();
    private int misses;
    private final LinkedHashMap<K, V> myProbationalQueue;
    private final int myProbationalQueueSize;
    private final LinkedHashMap<K, V> myProtectedQueue;
    private final int myProtectedQueueSize;
    private int probationalHits;
    private int protectedHits;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[0] = "value";
                break;
            case 4:
            case 5:
                objArr[0] = "condition";
                break;
            case 6:
                objArr[0] = "queue";
                break;
            default:
                objArr[0] = "hashingStrategy";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/SLRUMap";
        switch (i) {
            case 1:
                objArr[2] = "putToProtectedQueue";
                break;
            case 2:
                objArr[2] = "put";
                break;
            case 3:
                objArr[2] = "onDropFromCache";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "clearByCondition";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected void onDropFromCache(K k, V v) {
        if (v == null) {
            $$$reportNull$$$0(3);
        }
    }

    public SLRUMap(int i, int i2) {
        this(i, i2, EqualityPolicy.CANONICAL);
    }

    public SLRUMap(int i, int i2, EqualityPolicy<? super K> equalityPolicy) {
        if (equalityPolicy == null) {
            $$$reportNull$$$0(0);
        }
        int i3 = FACTOR;
        this.myProtectedQueueSize = i * i3;
        this.myProbationalQueueSize = i2 * i3;
        this.myProtectedQueue = new LinkedHashMap<K, V>(10, 0.6f, equalityPolicy, true) { // from class: org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap.1
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap
            protected boolean removeEldestEntry(Map.Entry<K, V> entry, K k, V v) {
                if (size() <= SLRUMap.this.myProtectedQueueSize) {
                    return false;
                }
                SLRUMap.this.myProbationalQueue.put(k, v);
                return true;
            }
        };
        this.myProbationalQueue = new LinkedHashMap<K, V>(10, 0.6f, equalityPolicy, true) { // from class: org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap.2
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap
            protected boolean removeEldestEntry(Map.Entry<K, V> entry, K k, V v) {
                if (size() <= SLRUMap.this.myProbationalQueueSize) {
                    return false;
                }
                SLRUMap.this.onDropFromCache(k, v);
                return true;
            }
        };
    }

    public V get(K k) {
        V v = this.myProtectedQueue.get(k);
        if (v != null) {
            this.protectedHits++;
            return v;
        }
        V vRemove = this.myProbationalQueue.remove(k);
        if (vRemove != null) {
            this.probationalHits++;
            putToProtectedQueue(k, vRemove);
            return vRemove;
        }
        this.misses++;
        return null;
    }

    protected void putToProtectedQueue(K k, V v) {
        if (v == null) {
            $$$reportNull$$$0(1);
        }
        this.myProtectedQueue.put(getStableKey(k), v);
    }

    public void put(K k, V v) {
        if (v == null) {
            $$$reportNull$$$0(2);
        }
        V vRemove = this.myProtectedQueue.remove(k);
        if (vRemove != null) {
            onDropFromCache(k, vRemove);
        }
        V vPut = this.myProbationalQueue.put(getStableKey(k), v);
        if (vPut != null) {
            onDropFromCache(k, vPut);
        }
    }

    public boolean remove(K k) {
        V vRemove = this.myProtectedQueue.remove(k);
        if (vRemove != null) {
            onDropFromCache(k, vRemove);
            return true;
        }
        V vRemove2 = this.myProbationalQueue.remove(k);
        if (vRemove2 == null) {
            return false;
        }
        onDropFromCache(k, vRemove2);
        return true;
    }

    public void iterateKeys(Consumer<? super K> consumer) {
        Iterator<K> it = this.myProtectedQueue.keySet().iterator();
        while (it.hasNext()) {
            consumer.consume(it.next());
        }
        Iterator<K> it2 = this.myProbationalQueue.keySet().iterator();
        while (it2.hasNext()) {
            consumer.consume(it2.next());
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        HashSet hashSet = new HashSet(this.myProtectedQueue.entrySet());
        hashSet.addAll(this.myProbationalQueue.entrySet());
        return hashSet;
    }

    public void clearByCondition(Condition<? super V> condition) {
        if (condition == null) {
            $$$reportNull$$$0(4);
        }
        clearByCondition(condition, this.myProtectedQueue);
        clearByCondition(condition, this.myProbationalQueue);
    }

    private void clearByCondition(Condition<? super V> condition, LinkedHashMap<K, V> linkedHashMap) {
        if (condition == null) {
            $$$reportNull$$$0(5);
        }
        if (linkedHashMap == null) {
            $$$reportNull$$$0(6);
        }
        Iterator<Map.Entry<K, V>> it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (condition.value(next.getValue())) {
                onDropFromCache(next.getKey(), next.getValue());
                it.remove();
            }
        }
    }

    public void clear() {
        try {
            if (!this.myProtectedQueue.isEmpty()) {
                for (Map.Entry<K, V> entry : this.myProtectedQueue.entrySet()) {
                    onDropFromCache(entry.getKey(), entry.getValue());
                }
            }
            if (!this.myProbationalQueue.isEmpty()) {
                for (Map.Entry<K, V> entry2 : this.myProbationalQueue.entrySet()) {
                    onDropFromCache(entry2.getKey(), entry2.getValue());
                }
            }
        } finally {
            this.myProtectedQueue.clear();
            this.myProbationalQueue.clear();
        }
    }

    private K getStableKey(K k) {
        return k instanceof ShareableKey ? (K) ((ShareableKey) k).getStableCopy() : k;
    }
}
