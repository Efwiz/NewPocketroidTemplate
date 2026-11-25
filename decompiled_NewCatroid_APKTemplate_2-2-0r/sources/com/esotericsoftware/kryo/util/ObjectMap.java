package com.esotericsoftware.kryo.util;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes8.dex */
public class ObjectMap<K, V> implements Iterable<Entry<K, V>> {
    static final Object dummy = new Object();
    K[] keyTable;
    float loadFactor;
    protected int mask;
    protected int shift;
    public int size;
    int threshold;
    V[] valueTable;

    public static int nextPowerOfTwo(int i) {
        if (i == 0) {
            return 1;
        }
        int i2 = i - 1;
        int i3 = i2 | (i2 >> 1);
        int i4 = i3 | (i3 >> 2);
        int i5 = i4 | (i4 >> 4);
        int i6 = i5 | (i5 >> 8);
        return (i6 | (i6 >> 16)) + 1;
    }

    public ObjectMap() {
        this(51, 0.8f);
    }

    public ObjectMap(int i) {
        this(i, 0.8f);
    }

    public ObjectMap(int i, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + f);
        }
        this.loadFactor = f;
        int iTableSize = tableSize(i, f);
        this.threshold = (int) (iTableSize * f);
        int i2 = iTableSize - 1;
        this.mask = i2;
        this.shift = Long.numberOfLeadingZeros(i2);
        this.keyTable = (K[]) new Object[iTableSize];
        this.valueTable = (V[]) new Object[iTableSize];
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ObjectMap(ObjectMap<? extends K, ? extends V> objectMap) {
        float length = objectMap.keyTable.length;
        float f = objectMap.loadFactor;
        this((int) (length * f), f);
        Object[] objArr = objectMap.keyTable;
        System.arraycopy(objArr, 0, this.keyTable, 0, objArr.length);
        Object[] objArr2 = objectMap.valueTable;
        System.arraycopy(objArr2, 0, this.valueTable, 0, objArr2.length);
        this.size = objectMap.size;
    }

    protected int place(K k) {
        return (int) ((((long) k.hashCode()) * (-7046029254386353131L)) >>> this.shift);
    }

    int locateKey(K k) {
        if (k == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }
        K[] kArr = this.keyTable;
        int iPlace = place(k);
        while (true) {
            K k2 = kArr[iPlace];
            if (k2 == null) {
                return -(iPlace + 1);
            }
            if (k2.equals(k)) {
                return iPlace;
            }
            iPlace = (iPlace + 1) & this.mask;
        }
    }

    public V put(K k, V v) {
        int iLocateKey = locateKey(k);
        if (iLocateKey >= 0) {
            V[] vArr = this.valueTable;
            V v2 = vArr[iLocateKey];
            vArr[iLocateKey] = v;
            return v2;
        }
        int i = -(iLocateKey + 1);
        K[] kArr = this.keyTable;
        kArr[i] = k;
        this.valueTable[i] = v;
        int i2 = this.size + 1;
        this.size = i2;
        if (i2 < this.threshold) {
            return null;
        }
        resize(kArr.length << 1);
        return null;
    }

    public void putAll(ObjectMap<? extends K, ? extends V> objectMap) {
        ensureCapacity(objectMap.size);
        K[] kArr = objectMap.keyTable;
        V[] vArr = objectMap.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                put(k, vArr[i]);
            }
        }
    }

    private void putResize(K k, V v) {
        K[] kArr = this.keyTable;
        int iPlace = place(k);
        while (kArr[iPlace] != null) {
            iPlace = (iPlace + 1) & this.mask;
        }
        kArr[iPlace] = k;
        this.valueTable[iPlace] = v;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends K> V get(T t) {
        int iLocateKey = locateKey(t);
        if (iLocateKey < 0) {
            return null;
        }
        return this.valueTable[iLocateKey];
    }

    public V get(K k, V v) {
        int iLocateKey = locateKey(k);
        return iLocateKey < 0 ? v : this.valueTable[iLocateKey];
    }

    public V remove(K k) {
        int iLocateKey = locateKey(k);
        if (iLocateKey < 0) {
            return null;
        }
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        V v = vArr[iLocateKey];
        int i = this.mask;
        int i2 = iLocateKey + 1;
        while (true) {
            int i3 = i2 & i;
            K k2 = kArr[i3];
            if (k2 != null) {
                int iPlace = place(k2);
                if (((i3 - iPlace) & i) > ((iLocateKey - iPlace) & i)) {
                    kArr[iLocateKey] = k2;
                    vArr[iLocateKey] = vArr[i3];
                    iLocateKey = i3;
                }
                i2 = i3 + 1;
            } else {
                kArr[iLocateKey] = null;
                vArr[iLocateKey] = null;
                this.size--;
                return v;
            }
        }
    }

    public boolean notEmpty() {
        return this.size > 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void shrink(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("maximumCapacity must be >= 0: " + i);
        }
        int iTableSize = tableSize(i, this.loadFactor);
        if (this.keyTable.length > iTableSize) {
            resize(iTableSize);
        }
    }

    public void clear(int i) {
        int iTableSize = tableSize(i, this.loadFactor);
        if (this.keyTable.length <= iTableSize) {
            clear();
        } else {
            this.size = 0;
            resize(iTableSize);
        }
    }

    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        Arrays.fill(this.keyTable, (Object) null);
        Arrays.fill(this.valueTable, (Object) null);
    }

    public boolean containsValue(Object obj, boolean z) {
        V[] vArr = this.valueTable;
        if (obj == null) {
            K[] kArr = this.keyTable;
            for (int length = vArr.length - 1; length >= 0; length--) {
                if (kArr[length] != null && vArr[length] == null) {
                    return true;
                }
            }
            return false;
        }
        if (z) {
            for (int length2 = vArr.length - 1; length2 >= 0; length2--) {
                if (vArr[length2] == obj) {
                    return true;
                }
            }
            return false;
        }
        for (int length3 = vArr.length - 1; length3 >= 0; length3--) {
            if (obj.equals(vArr[length3])) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(K k) {
        return locateKey(k) >= 0;
    }

    public K findKey(Object obj, boolean z) {
        V[] vArr = this.valueTable;
        if (obj == null) {
            K[] kArr = this.keyTable;
            for (int length = vArr.length - 1; length >= 0; length--) {
                K k = kArr[length];
                if (k != null && vArr[length] == null) {
                    return k;
                }
            }
            return null;
        }
        if (z) {
            for (int length2 = vArr.length - 1; length2 >= 0; length2--) {
                if (vArr[length2] == obj) {
                    return this.keyTable[length2];
                }
            }
            return null;
        }
        for (int length3 = vArr.length - 1; length3 >= 0; length3--) {
            if (obj.equals(vArr[length3])) {
                return this.keyTable[length3];
            }
        }
        return null;
    }

    public void ensureCapacity(int i) {
        int iTableSize = tableSize(this.size + i, this.loadFactor);
        if (this.keyTable.length < iTableSize) {
            resize(iTableSize);
        }
    }

    final void resize(int i) {
        int length = this.keyTable.length;
        this.threshold = (int) (i * this.loadFactor);
        int i2 = i - 1;
        this.mask = i2;
        this.shift = Long.numberOfLeadingZeros(i2);
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        this.keyTable = (K[]) new Object[i];
        this.valueTable = (V[]) new Object[i];
        if (this.size > 0) {
            for (int i3 = 0; i3 < length; i3++) {
                K k = kArr[i3];
                if (k != null) {
                    putResize(k, vArr[i3]);
                }
            }
        }
    }

    public int hashCode() {
        int iHashCode = this.size;
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                iHashCode += k.hashCode();
                V v = vArr[i];
                if (v != null) {
                    iHashCode += v.hashCode();
                }
            }
        }
        return iHashCode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ObjectMap)) {
            return false;
        }
        ObjectMap objectMap = (ObjectMap) obj;
        if (objectMap.size != this.size) {
            return false;
        }
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                V v = vArr[i];
                if (v == null) {
                    if (objectMap.get(k, dummy) != null) {
                        return false;
                    }
                } else if (!v.equals(objectMap.get(k))) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean equalsIdentity(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ObjectMap)) {
            return false;
        }
        ObjectMap objectMap = (ObjectMap) obj;
        if (objectMap.size != this.size) {
            return false;
        }
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null && vArr[i] != objectMap.get(k, dummy)) {
                return false;
            }
        }
        return true;
    }

    public String toString(String str) {
        return toString(str, false);
    }

    public String toString() {
        return toString(", ", true);
    }

    private String toString(String str, boolean z) {
        int i;
        if (this.size == 0) {
            return z ? "{}" : "";
        }
        StringBuilder sb = new StringBuilder(32);
        if (z) {
            sb.append('{');
        }
        Object[] objArr = this.keyTable;
        Object[] objArr2 = this.valueTable;
        int length = objArr.length;
        while (true) {
            i = length - 1;
            if (length > 0) {
                Object obj = objArr[i];
                if (obj != null) {
                    if (obj == this) {
                        obj = "(this)";
                    }
                    sb.append(obj);
                    sb.append('=');
                    Object obj2 = objArr2[i];
                    if (obj2 == this) {
                        obj2 = "(this)";
                    }
                    sb.append(obj2);
                    break;
                }
                length = i;
            } else {
                break;
            }
        }
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            Object obj3 = objArr[i2];
            if (obj3 != null) {
                sb.append(str);
                if (obj3 == this) {
                    obj3 = "(this)";
                }
                sb.append(obj3);
                sb.append('=');
                Object obj4 = objArr2[i2];
                if (obj4 == this) {
                    obj4 = "(this)";
                }
                sb.append(obj4);
            }
            i = i2;
        }
        if (z) {
            sb.append('}');
        }
        return sb.toString();
    }

    @Override // java.lang.Iterable
    public Entries<K, V> iterator() {
        return entries();
    }

    public Entries<K, V> entries() {
        return new Entries<>(this);
    }

    public Values<V> values() {
        return new Values<>(this);
    }

    public Keys<K> keys() {
        return new Keys<>(this);
    }

    public static int tableSize(int i, float f) {
        if (i < 0) {
            throw new IllegalArgumentException("capacity must be >= 0: " + i);
        }
        int iNextPowerOfTwo = nextPowerOfTwo(Math.max(2, (int) Math.ceil(i / f)));
        if (iNextPowerOfTwo <= 1073741824) {
            return iNextPowerOfTwo;
        }
        throw new IllegalArgumentException("The required capacity is too large: " + i);
    }

    public static class Entry<K, V> {
        public K key;
        public V value;

        public String toString() {
            return this.key + ContainerUtils.KEY_VALUE_DELIMITER + this.value;
        }
    }

    private static abstract class MapIterator<K, V, I> implements Iterable<I>, Iterator<I> {
        int currentIndex;
        public boolean hasNext;
        final ObjectMap<K, V> map;
        int nextIndex;
        boolean valid = true;

        public MapIterator(ObjectMap<K, V> objectMap) {
            this.map = objectMap;
            reset();
        }

        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            findNextIndex();
        }

        void findNextIndex() {
            int i;
            K[] kArr = this.map.keyTable;
            int length = kArr.length;
            do {
                i = this.nextIndex + 1;
                this.nextIndex = i;
                if (i >= length) {
                    this.hasNext = false;
                    return;
                }
            } while (kArr[i] == null);
            this.hasNext = true;
        }

        public void remove() {
            int i = this.currentIndex;
            if (i < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }
            K[] kArr = this.map.keyTable;
            V[] vArr = this.map.valueTable;
            int i2 = this.map.mask;
            int i3 = i + 1;
            while (true) {
                int i4 = i3 & i2;
                K k = kArr[i4];
                if (k == null) {
                    break;
                }
                int iPlace = this.map.place(k);
                if (((i4 - iPlace) & i2) > ((i - iPlace) & i2)) {
                    kArr[i] = k;
                    vArr[i] = vArr[i4];
                    i = i4;
                }
                i3 = i4 + 1;
            }
            kArr[i] = null;
            vArr[i] = null;
            this.map.size--;
            if (i != this.currentIndex) {
                this.nextIndex--;
            }
            this.currentIndex = -1;
        }
    }

    public static class Entries<K, V> extends MapIterator<K, V, Entry<K, V>> {
        Entry<K, V> entry;

        @Override // java.lang.Iterable
        public Entries<K, V> iterator() {
            return this;
        }

        @Override // com.esotericsoftware.kryo.util.ObjectMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.esotericsoftware.kryo.util.ObjectMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(ObjectMap<K, V> objectMap) {
            super(objectMap);
            this.entry = new Entry<>();
        }

        @Override // java.util.Iterator
        public Entry<K, V> next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            K[] kArr = this.map.keyTable;
            this.entry.key = kArr[this.nextIndex];
            this.entry.value = this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return this.entry;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasNext;
        }
    }

    public static class Values<V> extends MapIterator<Object, V, V> {
        @Override // java.lang.Iterable
        public Values<V> iterator() {
            return this;
        }

        @Override // com.esotericsoftware.kryo.util.ObjectMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.esotericsoftware.kryo.util.ObjectMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(ObjectMap<?, V> objectMap) {
            super(objectMap);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasNext;
        }

        @Override // java.util.Iterator
        public V next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            V v = this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return v;
        }

        public ArrayList<V> toList() {
            return (ArrayList) toList(new ArrayList(this.map.size));
        }

        public <T extends List<V>> T toList(T t) {
            while (this.hasNext) {
                t.add(next());
            }
            return t;
        }
    }

    public static class Keys<K> extends MapIterator<K, Object, K> {
        @Override // java.lang.Iterable
        public Keys<K> iterator() {
            return this;
        }

        @Override // com.esotericsoftware.kryo.util.ObjectMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.esotericsoftware.kryo.util.ObjectMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(ObjectMap<K, ?> objectMap) {
            super(objectMap);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasNext;
        }

        @Override // java.util.Iterator
        public K next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            K k = this.map.keyTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return k;
        }

        public ArrayList<K> toList() {
            return (ArrayList) toList(new ArrayList(this.map.size));
        }

        public <T extends List<K>> T toList(T t) {
            while (this.hasNext) {
                t.add(next());
            }
            return t;
        }
    }
}
