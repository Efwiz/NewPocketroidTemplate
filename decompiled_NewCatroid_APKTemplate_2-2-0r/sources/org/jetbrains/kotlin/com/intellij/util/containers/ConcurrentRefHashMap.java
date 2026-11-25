package org.jetbrains.kotlin.com.intellij.util.containers;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.lang.ref.ReferenceQueue;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes3.dex */
abstract class ConcurrentRefHashMap<K, V> extends AbstractMap<K, V> implements HashingStrategy<K>, ConcurrentMap<K, V> {
    private Set<Map.Entry<K, V>> entrySet;
    private final HashingStrategy<? super K> myHashingStrategy;
    private final ConcurrentMap<KeyReference<K>, V> myMap;
    final ReferenceQueue<K> myReferenceQueue;
    static final int DEFAULT_CONCURRENCY_LEVEL = Math.min(Runtime.getRuntime().availableProcessors(), 4);
    private static final HardKey<?> NULL_KEY = new HardKey<Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.1
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.HardKey, org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.KeyReference
        public Object get() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.HardKey
        void setKey(Object obj, int i) {
        }
    };
    private static final HashingStrategy<?> THIS = new HashingStrategy<Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.2
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public int hashCode(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public boolean equals(Object obj, Object obj2) {
            throw new UnsupportedOperationException();
        }
    };
    private static final ThreadLocal<HardKey<?>> HARD_KEY = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return ConcurrentRefHashMap.lambda$static$0();
        }
    });

    @FunctionalInterface
    interface KeyReference<K> {
        K get();

        int hashCode();
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0017  */
    /* JADX WARN: Code duplicated, block: B:20:0x0031  */
    /* JADX WARN: Code duplicated, block: B:7:0x000c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 5) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 2 && i != 5) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 2) {
            objArr[0] = "hashingStrategy";
        } else if (i != 5) {
            switch (i) {
                case 7:
                case 8:
                case 11:
                    objArr[0] = "value";
                    break;
                case 9:
                    objArr[0] = "oldValue";
                    break;
                case 10:
                    objArr[0] = "newValue";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentRefHashMap";
                    break;
            }
        } else {
            objArr[0] = "value";
        }
        switch (i) {
            case 2:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentRefHashMap";
                break;
            case 3:
            case 4:
                objArr[1] = "createHardKey";
                break;
            case 6:
                objArr[1] = "entrySet";
                break;
            default:
                objArr[1] = "createKeyReference";
                break;
        }
        if (i == 2) {
            objArr[2] = "<init>";
        } else if (i != 5) {
            switch (i) {
                case 7:
                    objArr[2] = "putIfAbsent";
                    break;
                case 8:
                    objArr[2] = ProductAction.ACTION_REMOVE;
                    break;
                case 9:
                case 10:
                case 11:
                    objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                    break;
            }
        } else {
            objArr[2] = "put";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    abstract KeyReference<K> createKeyReference(K k, HashingStrategy<? super K> hashingStrategy);

    private KeyReference<K> createKeyReference(K k) {
        if (k == null) {
            HardKey<?> hardKey = NULL_KEY;
            if (hardKey == null) {
                $$$reportNull$$$0(0);
            }
            return hardKey;
        }
        KeyReference<K> keyReferenceCreateKeyReference = createKeyReference(k, this.myHashingStrategy);
        if (keyReferenceCreateKeyReference == null) {
            $$$reportNull$$$0(1);
        }
        return keyReferenceCreateKeyReference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean processQueue() {
        boolean z = false;
        while (true) {
            KeyReference keyReference = (KeyReference) this.myReferenceQueue.poll();
            if (keyReference == null) {
                return z;
            }
            this.myMap.remove(keyReference);
            z = true;
        }
    }

    ConcurrentRefHashMap() {
        this(16);
    }

    private ConcurrentRefHashMap(int i) {
        this(i, 0.75f);
    }

    private ConcurrentRefHashMap(int i, float f) {
        this(i, f, DEFAULT_CONCURRENCY_LEVEL, THIS);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    ConcurrentRefHashMap(HashingStrategy<? super K> hashingStrategy) {
        this(16, 0.75f, DEFAULT_CONCURRENCY_LEVEL, hashingStrategy);
        if (hashingStrategy == null) {
            $$$reportNull$$$0(2);
        }
    }

    ConcurrentRefHashMap(int i, float f, int i2, HashingStrategy<? super K> hashingStrategy) {
        this.myReferenceQueue = new ReferenceQueue<>();
        if (hashingStrategy == THIS) {
            hashingStrategy = this;
        } else if (hashingStrategy == null) {
            hashingStrategy = HashingStrategy.canonical();
        }
        this.myHashingStrategy = hashingStrategy;
        this.myMap = new ConcurrentHashMap(i, f, i2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return entrySet().size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this.myMap.isEmpty() || entrySet().isEmpty();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        HardKey<K> hardKeyCreateHardKey = createHardKey(obj);
        try {
            return this.myMap.containsKey(hardKeyCreateHardKey);
        } finally {
            hardKeyCreateHardKey.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object obj) {
        throw RefValueHashMapUtil.pointlessContainsValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class HardKey<K> implements KeyReference<K> {
        private int myHash;
        private K myKey;

        private HardKey() {
        }

        void setKey(K k, int i) {
            this.myKey = k;
            this.myHash = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.KeyReference
        public K get() {
            return this.myKey;
        }

        public boolean equals(Object obj) {
            return obj.equals(this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.KeyReference
        public int hashCode() {
            return this.myHash;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clear() {
            setKey(null, 0);
        }
    }

    static /* synthetic */ HardKey lambda$static$0() {
        return new HardKey();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HardKey<K> createHardKey(Object obj) {
        if (obj == null) {
            HardKey<K> hardKey = (HardKey<K>) NULL_KEY;
            if (hardKey == null) {
                $$$reportNull$$$0(3);
            }
            return hardKey;
        }
        HardKey<K> hardKey2 = (HardKey) HARD_KEY.get();
        hardKey2.setKey(obj, this.myHashingStrategy.hashCode(obj));
        if (hardKey2 == null) {
            $$$reportNull$$$0(4);
        }
        return hardKey2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        HardKey<K> hardKeyCreateHardKey = createHardKey(obj);
        try {
            return this.myMap.get(hardKeyCreateHardKey);
        } finally {
            hardKeyCreateHardKey.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        if (v == null) {
            $$$reportNull$$$0(5);
        }
        V vPut = this.myMap.put(createKeyReference(k), v);
        processQueue();
        return vPut;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        HardKey<K> hardKeyCreateHardKey = createHardKey(obj);
        try {
            return this.myMap.remove(hardKeyCreateHardKey);
        } finally {
            processQueue();
            hardKeyCreateHardKey.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.myMap.clear();
        processQueue();
    }

    private static final class RefEntry<K, V> implements Map.Entry<K, V> {
        private final Map.Entry<?, V> ent;
        private final K key;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "ent";
            } else {
                objArr[0] = "value";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentRefHashMap$RefEntry";
            if (i != 1) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "setValue";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        RefEntry(Map.Entry<?, V> entry, K k) {
            if (entry == null) {
                $$$reportNull$$$0(0);
            }
            this.ent = entry;
            this.key = k;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.ent.getValue();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (v == null) {
                $$$reportNull$$$0(1);
            }
            return this.ent.setValue(v);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return Objects.equals(this.key, entry.getKey()) && Objects.equals(getValue(), entry.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K k = this.key;
            int iHashCode = k == null ? 0 : k.hashCode();
            V value = getValue();
            return iHashCode ^ (value != null ? value.hashCode() : 0);
        }
    }

    private final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        private final Set<Map.Entry<KeyReference<K>, V>> hashEntrySet;

        private EntrySet() {
            this.hashEntrySet = ConcurrentRefHashMap.this.myMap.entrySet();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new Iterator<Map.Entry<K, V>>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.EntrySet.1
                private final Iterator<Map.Entry<KeyReference<K>, V>> hashIterator;
                private RefEntry<K, V> next;

                {
                    this.hashIterator = EntrySet.this.hashEntrySet.iterator();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    K k;
                    while (this.hashIterator.hasNext()) {
                        Map.Entry<KeyReference<K>, V> next = this.hashIterator.next();
                        KeyReference<K> key = next.getKey();
                        if (key != null) {
                            k = key.get();
                            if (k != null || key == ConcurrentRefHashMap.NULL_KEY) {
                            }
                        } else {
                            k = null;
                        }
                        this.next = new RefEntry<>(next, k);
                        return true;
                    }
                    return false;
                }

                @Override // java.util.Iterator
                public Map.Entry<K, V> next() {
                    if (this.next == null && !hasNext()) {
                        throw new NoSuchElementException();
                    }
                    RefEntry<K, V> refEntry = this.next;
                    this.next = null;
                    return refEntry;
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.hashIterator.remove();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return !iterator().hasNext();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            Iterator<Map.Entry<K, V>> it = iterator();
            int i = 0;
            while (it.hasNext()) {
                i++;
                it.next();
            }
            return i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            boolean zEquals = false;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object value = entry.getValue();
            HardKey hardKeyCreateHardKey = ConcurrentRefHashMap.this.createHardKey(entry.getKey());
            try {
                Object obj2 = ConcurrentRefHashMap.this.myMap.get(hardKeyCreateHardKey);
                if (obj2 != null) {
                    zEquals = obj2.equals(value);
                } else if (value == null && ConcurrentRefHashMap.this.myMap.containsKey(hardKeyCreateHardKey)) {
                    zEquals = true;
                }
                if (zEquals) {
                    ConcurrentRefHashMap.this.myMap.remove(hardKeyCreateHardKey);
                }
                hardKeyCreateHardKey.clear();
                ConcurrentRefHashMap.this.processQueue();
                return zEquals;
            } catch (Throwable th) {
                hardKeyCreateHardKey.clear();
                throw th;
            }
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public int hashCode() {
            int iHashCode = 0;
            for (Map.Entry<KeyReference<K>, V> entry : this.hashEntrySet) {
                KeyReference<K> key = entry.getKey();
                if (key != null) {
                    int iHashCode2 = key.hashCode();
                    V value = entry.getValue();
                    iHashCode += (value == null ? 0 : value.hashCode()) ^ iHashCode2;
                }
            }
            return iHashCode;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = this.entrySet;
        if (entrySet == null) {
            entrySet = new EntrySet();
            this.entrySet = entrySet;
        }
        if (entrySet == null) {
            $$$reportNull$$$0(6);
        }
        return entrySet;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V putIfAbsent(K k, V v) {
        if (v == null) {
            $$$reportNull$$$0(7);
        }
        V vPutIfAbsent = this.myMap.putIfAbsent(createKeyReference(k), v);
        processQueue();
        return vPutIfAbsent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean remove(Object obj, Object obj2) {
        if (obj2 == null) {
            $$$reportNull$$$0(8);
        }
        boolean zRemove = this.myMap.remove(createKeyReference(obj), obj2);
        processQueue();
        return zRemove;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean replace(K k, V v, V v2) {
        if (v == null) {
            $$$reportNull$$$0(9);
        }
        if (v2 == null) {
            $$$reportNull$$$0(10);
        }
        boolean zReplace = this.myMap.replace(createKeyReference(k), v, v2);
        processQueue();
        return zReplace;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V replace(K k, V v) {
        if (v == null) {
            $$$reportNull$$$0(11);
        }
        V vReplace = this.myMap.replace(createKeyReference(k), v);
        processQueue();
        return vReplace;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
    public int hashCode(K k) {
        int iHashCode = k == null ? 0 : k.hashCode();
        int i = iHashCode + (~(iHashCode << 9));
        int i2 = i ^ (i >>> 14);
        int i3 = i2 + (i2 << 4);
        return i3 ^ (i3 >>> 10);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
    public boolean equals(K k, K k2) {
        return Objects.equals(k, k2);
    }
}
