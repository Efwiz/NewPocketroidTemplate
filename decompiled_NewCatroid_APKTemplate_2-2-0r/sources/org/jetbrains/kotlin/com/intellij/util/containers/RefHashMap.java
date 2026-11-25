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
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtilsRt;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
abstract class RefHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {
    private Set<Map.Entry<K, V>> entrySet;
    private final RefHashMap<K, V>.HardKey myHardKeyInstance;
    private final RefHashMap<K, V>.MyMap myMap;
    private final ReferenceQueue<K> myReferenceQueue;
    private final HashingStrategy<? super K> myStrategy;

    @FunctionalInterface
    interface Key<T> {
        T get();

        int hashCode();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "hashingStrategy";
                break;
            case 2:
            default:
                objArr[0] = "strategy";
                break;
            case 3:
            case 4:
            case 7:
            case 8:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 5:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/RefHashMap";
                break;
            case 6:
                objArr[0] = "weakKey";
                break;
        }
        if (i == 5) {
            objArr[1] = "createKey";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/RefHashMap";
        } else {
            objArr[1] = "entrySet";
        }
        switch (i) {
            case 2:
                objArr[2] = "keysEqual";
                break;
            case 3:
                objArr[2] = "removeKey";
                break;
            case 4:
                objArr[2] = "createKey";
                break;
            case 5:
            case 9:
                break;
            case 6:
                objArr[2] = "putKey";
                break;
            case 7:
                objArr[2] = "put";
                break;
            case 8:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract <T> Key<T> createKey(T t, HashingStrategy<? super T> hashingStrategy, ReferenceQueue<? super T> referenceQueue);

    RefHashMap(int i, float f, HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(0);
        }
        this.myReferenceQueue = new ReferenceQueue<>();
        this.myHardKeyInstance = new HardKey();
        this.myStrategy = hashingStrategy;
        this.myMap = new MyMap(i, f);
    }

    private RefHashMap(int i, float f) {
        this(i, f, HashingStrategy.canonical());
    }

    RefHashMap(int i) {
        this(i, 0.8f);
    }

    RefHashMap() {
        this(4);
    }

    static <K> boolean keysEqual(K k, K k2, HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(2);
        }
        return k == k2 || hashingStrategy.equals(k, k2);
    }

    private class MyMap extends Object2ObjectOpenHashMap<Key<K>, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private MyMap(int i, float f) {
            super(i, f);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
        protected void rehash(int i) {
            K[] kArr = this.key;
            V[] vArr = this.value;
            int i2 = i - 1;
            int i3 = i + 1;
            K[] kArr2 = (K[]) new Key[i3];
            V[] vArr2 = (V[]) new Object[i3];
            int i4 = this.size;
            for (int i5 = this.n; i5 >= 0 && i4 > 0; i5--) {
                Key key = (Key) kArr[i5];
                if (key != null) {
                    i4--;
                    Object obj = key.get();
                    if (obj == null) {
                        this.size--;
                    } else {
                        int iMix = HashCommon.mix(key.hashCode()) & i2;
                        if (kArr2[iMix] != 0) {
                            do {
                                iMix = (iMix + 1) & i2;
                            } while (kArr2[iMix] != 0);
                        }
                        kArr2[iMix] = key;
                        vArr2[iMix] = vArr[i5];
                        ObjectUtilsRt.reachabilityFence(obj);
                    }
                }
            }
            vArr2[i] = vArr[this.n];
            this.n = i;
            this.mask = i2;
            this.maxFill = HashCommon.maxFill(this.n, this.f);
            this.key = kArr2;
            this.value = vArr2;
        }
    }

    private class HardKey implements Key<K> {
        private int myHash;
        private K myObject;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "object", "org/jetbrains/kotlin/com/intellij/util/containers/RefHashMap$HardKey", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX));
        }

        private HardKey() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.RefHashMap.Key
        public K get() {
            return this.myObject;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void set(K k) {
            if (k == null) {
                $$$reportNull$$$0(0);
            }
            this.myObject = k;
            this.myHash = RefHashMap.this.myStrategy.hashCode(k);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clear() {
            this.myObject = null;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Key) {
                return RefHashMap.keysEqual(this.myObject, ((Key) obj).get(), RefHashMap.this.myStrategy);
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.RefHashMap.Key
        public int hashCode() {
            return this.myHash;
        }
    }

    boolean processQueue() {
        boolean z = false;
        while (true) {
            Key<K> key = (Key) this.myReferenceQueue.poll();
            if (key == null) {
                return z;
            }
            removeKey(key);
            z = true;
        }
    }

    V removeKey(Key<K> key) {
        if (key == null) {
            $$$reportNull$$$0(3);
        }
        return this.myMap.remove(key);
    }

    Key<K> createKey(K k) {
        if (k == null) {
            $$$reportNull$$$0(4);
        }
        Key<K> key = (Key<K>) createKey(k, this.myStrategy, this.myReferenceQueue);
        if (key == null) {
            $$$reportNull$$$0(5);
        }
        return key;
    }

    V putKey(Key<K> key, V v) {
        if (key == null) {
            $$$reportNull$$$0(6);
        }
        return this.myMap.put(key, v);
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
        if (obj == null) {
            return false;
        }
        this.myHardKeyInstance.set(obj);
        try {
            return this.myMap.containsKey(this.myHardKeyInstance);
        } finally {
            this.myHardKeyInstance.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object obj) {
        throw RefValueHashMapUtil.pointlessContainsValue();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        if (obj == null) {
            return null;
        }
        this.myHardKeyInstance.set(obj);
        try {
            return this.myMap.get(this.myHardKeyInstance);
        } finally {
            this.myHardKeyInstance.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        if (k == null) {
            $$$reportNull$$$0(7);
        }
        processQueue();
        return putKey(createKey(k), v);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(8);
        }
        processQueue();
        this.myHardKeyInstance.set(obj);
        try {
            return this.myMap.remove(this.myHardKeyInstance);
        } finally {
            this.myHardKeyInstance.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        processQueue();
        this.myMap.clear();
    }

    private static final class MyEntry<K, V> implements Map.Entry<K, V> {
        private final Map.Entry<?, V> ent;
        private final K key;
        private final int myKeyHashCode;
        private final HashingStrategy<? super K> myStrategy;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
            } else if (i != 2) {
                objArr[0] = "ent";
            } else {
                objArr[0] = "strategy";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/RefHashMap$MyEntry";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private MyEntry(Map.Entry<?, V> entry, K k, int i, HashingStrategy<? super K> hashingStrategy) {
            if (entry == null) {
                $$$reportNull$$$0(0);
            }
            if (k == null) {
                $$$reportNull$$$0(1);
            }
            if (hashingStrategy == null) {
                $$$reportNull$$$0(2);
            }
            this.ent = entry;
            this.key = k;
            this.myKeyHashCode = i;
            this.myStrategy = hashingStrategy;
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
            return this.ent.setValue(v);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return RefHashMap.keysEqual(this.key, entry.getKey(), this.myStrategy) && Objects.equals(getValue(), entry.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int i = this.myKeyHashCode;
            V value = getValue();
            return i ^ (value == null ? 0 : value.hashCode());
        }
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        private final Set<Map.Entry<Key<K>, V>> hashEntrySet;

        private EntrySet() {
            this.hashEntrySet = RefHashMap.this.myMap.entrySet();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new Iterator<Map.Entry<K, V>>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.RefHashMap.EntrySet.1
                private final Iterator<Map.Entry<Key<K>, V>> hashIterator;
                private MyEntry<K, V> next;

                {
                    this.hashIterator = EntrySet.this.hashEntrySet.iterator();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    while (this.hashIterator.hasNext()) {
                        Map.Entry<Key<K>, V> next = this.hashIterator.next();
                        Key<K> key = next.getKey();
                        K k = key.get();
                        if (k != null) {
                            this.next = new MyEntry<>(next, k, key.hashCode(), RefHashMap.this.myStrategy);
                            return true;
                        }
                    }
                    return false;
                }

                @Override // java.util.Iterator
                public Map.Entry<K, V> next() {
                    if (this.next == null && !hasNext()) {
                        throw new NoSuchElementException();
                    }
                    MyEntry<K, V> myEntry = this.next;
                    this.next = null;
                    return myEntry;
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
            RefHashMap.this.processQueue();
            boolean zEquals = false;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object value = entry.getValue();
            HardKey hardKey = RefHashMap.this.myHardKeyInstance;
            try {
                hardKey.set(entry.getKey());
                V v = RefHashMap.this.myMap.get(hardKey);
                if (v != null) {
                    zEquals = v.equals(value);
                } else if (value == null && RefHashMap.this.myMap.containsKey(hardKey)) {
                    zEquals = true;
                }
                if (zEquals) {
                    RefHashMap.this.myMap.remove(hardKey);
                }
                return zEquals;
            } finally {
                hardKey.clear();
            }
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public int hashCode() {
            int iHashCode = 0;
            for (Map.Entry<Key<K>, V> entry : this.hashEntrySet) {
                Key<K> key = entry.getKey();
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
            $$$reportNull$$$0(9);
        }
        return entrySet;
    }
}
