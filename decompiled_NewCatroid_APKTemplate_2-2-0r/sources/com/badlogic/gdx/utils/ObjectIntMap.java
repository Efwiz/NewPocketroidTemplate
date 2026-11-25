package com.badlogic.gdx.utils;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectIntMap.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ObjectIntMap<K> implements Iterable<Entry<K>> {
    transient Entries entries1;
    transient Entries entries2;
    K[] keyTable;
    transient Keys keys1;
    transient Keys keys2;
    float loadFactor;
    protected int mask;
    protected int shift;
    public int size;
    int threshold;
    int[] valueTable;
    transient Values values1;
    transient Values values2;

    public ObjectIntMap() {
        this(51, 0.8f);
    }

    public ObjectIntMap(int i) {
        this(i, 0.8f);
    }

    public ObjectIntMap(int i, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + f);
        }
        this.loadFactor = f;
        int iTableSize = ObjectSet.tableSize(i, f);
        this.threshold = (int) (iTableSize * f);
        int i2 = iTableSize - 1;
        this.mask = i2;
        this.shift = Long.numberOfLeadingZeros(i2);
        this.keyTable = (K[]) new Object[iTableSize];
        this.valueTable = new int[iTableSize];
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ObjectIntMap(ObjectIntMap<? extends K> objectIntMap) {
        float length = objectIntMap.keyTable.length;
        float f = objectIntMap.loadFactor;
        this((int) (length * f), f);
        Object[] objArr = objectIntMap.keyTable;
        System.arraycopy(objArr, 0, this.keyTable, 0, objArr.length);
        int[] iArr = objectIntMap.valueTable;
        System.arraycopy(iArr, 0, this.valueTable, 0, iArr.length);
        this.size = objectIntMap.size;
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

    public void put(K k, int i) {
        int iLocateKey = locateKey(k);
        if (iLocateKey >= 0) {
            this.valueTable[iLocateKey] = i;
            return;
        }
        int i2 = -(iLocateKey + 1);
        K[] kArr = this.keyTable;
        kArr[i2] = k;
        this.valueTable[i2] = i;
        int i3 = this.size + 1;
        this.size = i3;
        if (i3 >= this.threshold) {
            resize(kArr.length << 1);
        }
    }

    public int put(K k, int i, int i2) {
        int iLocateKey = locateKey(k);
        if (iLocateKey >= 0) {
            int[] iArr = this.valueTable;
            int i3 = iArr[iLocateKey];
            iArr[iLocateKey] = i;
            return i3;
        }
        int i4 = -(iLocateKey + 1);
        K[] kArr = this.keyTable;
        kArr[i4] = k;
        this.valueTable[i4] = i;
        int i5 = this.size + 1;
        this.size = i5;
        if (i5 >= this.threshold) {
            resize(kArr.length << 1);
        }
        return i2;
    }

    public void putAll(ObjectIntMap<? extends K> objectIntMap) {
        ensureCapacity(objectIntMap.size);
        K[] kArr = objectIntMap.keyTable;
        int[] iArr = objectIntMap.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                put(k, iArr[i]);
            }
        }
    }

    private void putResize(K k, int i) {
        K[] kArr = this.keyTable;
        int iPlace = place(k);
        while (kArr[iPlace] != null) {
            iPlace = (iPlace + 1) & this.mask;
        }
        kArr[iPlace] = k;
        this.valueTable[iPlace] = i;
    }

    public int get(K k, int i) {
        int iLocateKey = locateKey(k);
        return iLocateKey < 0 ? i : this.valueTable[iLocateKey];
    }

    public int getAndIncrement(K k, int i, int i2) {
        int iLocateKey = locateKey(k);
        if (iLocateKey >= 0) {
            int[] iArr = this.valueTable;
            int i3 = iArr[iLocateKey];
            iArr[iLocateKey] = i2 + i3;
            return i3;
        }
        int i4 = -(iLocateKey + 1);
        K[] kArr = this.keyTable;
        kArr[i4] = k;
        this.valueTable[i4] = i2 + i;
        int i5 = this.size + 1;
        this.size = i5;
        if (i5 >= this.threshold) {
            resize(kArr.length << 1);
        }
        return i;
    }

    public int remove(K k, int i) {
        int iLocateKey = locateKey(k);
        if (iLocateKey < 0) {
            return i;
        }
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        int i2 = iArr[iLocateKey];
        int i3 = this.mask;
        int i4 = iLocateKey + 1;
        while (true) {
            int i5 = i4 & i3;
            K k2 = kArr[i5];
            if (k2 != null) {
                int iPlace = place(k2);
                if (((i5 - iPlace) & i3) > ((iLocateKey - iPlace) & i3)) {
                    kArr[iLocateKey] = k2;
                    iArr[iLocateKey] = iArr[i5];
                    iLocateKey = i5;
                }
                i4 = i5 + 1;
            } else {
                kArr[iLocateKey] = null;
                this.size--;
                return i2;
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
        int iTableSize = ObjectSet.tableSize(i, this.loadFactor);
        if (this.keyTable.length > iTableSize) {
            resize(iTableSize);
        }
    }

    public void clear(int i) {
        int iTableSize = ObjectSet.tableSize(i, this.loadFactor);
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
    }

    public boolean containsValue(int i) {
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (kArr[length] != null && iArr[length] == i) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(K k) {
        return locateKey(k) >= 0;
    }

    public K findKey(int i) {
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        for (int length = iArr.length - 1; length >= 0; length--) {
            K k = kArr[length];
            if (k != null && iArr[length] == i) {
                return k;
            }
        }
        return null;
    }

    public void ensureCapacity(int i) {
        int iTableSize = ObjectSet.tableSize(this.size + i, this.loadFactor);
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
        int[] iArr = this.valueTable;
        this.keyTable = (K[]) new Object[i];
        this.valueTable = new int[i];
        if (this.size > 0) {
            for (int i3 = 0; i3 < length; i3++) {
                K k = kArr[i3];
                if (k != null) {
                    putResize(k, iArr[i3]);
                }
            }
        }
    }

    public int hashCode() {
        int iHashCode = this.size;
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                iHashCode += k.hashCode() + iArr[i];
            }
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        int i;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ObjectIntMap)) {
            return false;
        }
        ObjectIntMap objectIntMap = (ObjectIntMap) obj;
        if (objectIntMap.size != this.size) {
            return false;
        }
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        int length = kArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            K k = kArr[i2];
            if (k != null && (((i = objectIntMap.get(k, 0)) == 0 && !objectIntMap.containsKey(k)) || i != iArr[i2])) {
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
        java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
        if (z) {
            sb.append('{');
        }
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        int length = kArr.length;
        while (true) {
            i = length - 1;
            if (length > 0) {
                K k = kArr[i];
                if (k != null) {
                    sb.append(k);
                    sb.append('=');
                    sb.append(iArr[i]);
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
            K k2 = kArr[i2];
            if (k2 != null) {
                sb.append(str);
                sb.append(k2);
                sb.append('=');
                sb.append(iArr[i2]);
            }
            i = i2;
        }
        if (z) {
            sb.append('}');
        }
        return sb.toString();
    }

    @Override // java.lang.Iterable
    public Entries<K> iterator() {
        return entries();
    }

    public Entries<K> entries() {
        if (Collections.allocateIterators) {
            return new Entries<>(this);
        }
        if (this.entries1 == null) {
            this.entries1 = new Entries(this);
            this.entries2 = new Entries(this);
        }
        if (!this.entries1.valid) {
            this.entries1.reset();
            this.entries1.valid = true;
            this.entries2.valid = false;
            return this.entries1;
        }
        this.entries2.reset();
        this.entries2.valid = true;
        this.entries1.valid = false;
        return this.entries2;
    }

    public Values values() {
        if (Collections.allocateIterators) {
            return new Values(this);
        }
        if (this.values1 == null) {
            this.values1 = new Values(this);
            this.values2 = new Values(this);
        }
        if (!this.values1.valid) {
            this.values1.reset();
            this.values1.valid = true;
            this.values2.valid = false;
            return this.values1;
        }
        this.values2.reset();
        this.values2.valid = true;
        this.values1.valid = false;
        return this.values2;
    }

    public Keys<K> keys() {
        if (Collections.allocateIterators) {
            return new Keys<>(this);
        }
        if (this.keys1 == null) {
            this.keys1 = new Keys(this);
            this.keys2 = new Keys(this);
        }
        if (!this.keys1.valid) {
            this.keys1.reset();
            this.keys1.valid = true;
            this.keys2.valid = false;
            return this.keys1;
        }
        this.keys2.reset();
        this.keys2.valid = true;
        this.keys1.valid = false;
        return this.keys2;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectIntMap$Entry.class
      classes8.dex
     */
    public static class Entry<K> {
        public K key;
        public int value;

        public String toString() {
            return this.key + ContainerUtils.KEY_VALUE_DELIMITER + this.value;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectIntMap$MapIterator.class
      classes8.dex
     */
    private static class MapIterator<K> {
        int currentIndex;
        public boolean hasNext;
        final ObjectIntMap<K> map;
        int nextIndex;
        boolean valid = true;

        public MapIterator(ObjectIntMap<K> objectIntMap) {
            this.map = objectIntMap;
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
            int[] iArr = this.map.valueTable;
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
                    iArr[i] = iArr[i4];
                    i = i4;
                }
                i3 = i4 + 1;
            }
            kArr[i] = null;
            this.map.size--;
            if (i != this.currentIndex) {
                this.nextIndex--;
            }
            this.currentIndex = -1;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectIntMap$Entries.class
      classes8.dex
     */
    public static class Entries<K> extends MapIterator<K> implements Iterable<Entry<K>>, Iterator<Entry<K>> {
        Entry<K> entry;

        @Override // java.lang.Iterable
        public Entries<K> iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.ObjectIntMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.ObjectIntMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(ObjectIntMap<K> objectIntMap) {
            super(objectIntMap);
            this.entry = new Entry<>();
        }

        @Override // java.util.Iterator
        public Entry<K> next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
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
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            return this.hasNext;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectIntMap$Values.class
      classes8.dex
     */
    public static class Values extends MapIterator<Object> {
        public Values iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.ObjectIntMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.ObjectIntMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(ObjectIntMap<?> objectIntMap) {
            super(objectIntMap);
        }

        public boolean hasNext() {
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            return this.hasNext;
        }

        public int next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            int i = this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return i;
        }

        public IntArray toArray() {
            IntArray intArray = new IntArray(true, this.map.size);
            while (this.hasNext) {
                intArray.add(next());
            }
            return intArray;
        }

        public IntArray toArray(IntArray intArray) {
            while (this.hasNext) {
                intArray.add(next());
            }
            return intArray;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectIntMap$Keys.class
      classes8.dex
     */
    public static class Keys<K> extends MapIterator<K> implements Iterable<K>, Iterator<K> {
        @Override // java.lang.Iterable
        public Keys<K> iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.ObjectIntMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.ObjectIntMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(ObjectIntMap<K> objectIntMap) {
            super(objectIntMap);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            return this.hasNext;
        }

        @Override // java.util.Iterator
        public K next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            K k = this.map.keyTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return k;
        }

        public Array<K> toArray() {
            return toArray(new Array<>(true, this.map.size));
        }

        public Array<K> toArray(Array<K> array) {
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }
    }
}
