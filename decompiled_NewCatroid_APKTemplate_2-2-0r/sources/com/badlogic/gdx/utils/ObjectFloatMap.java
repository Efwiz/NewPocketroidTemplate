package com.badlogic.gdx.utils;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectFloatMap.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ObjectFloatMap<K> implements Iterable<Entry<K>> {
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
    float[] valueTable;
    transient Values values1;
    transient Values values2;

    public ObjectFloatMap() {
        this(51, 0.8f);
    }

    public ObjectFloatMap(int i) {
        this(i, 0.8f);
    }

    public ObjectFloatMap(int i, float f) {
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
        this.valueTable = new float[iTableSize];
    }

    public ObjectFloatMap(ObjectFloatMap<? extends K> objectFloatMap) {
        this((int) Math.floor(objectFloatMap.keyTable.length * objectFloatMap.loadFactor), objectFloatMap.loadFactor);
        Object[] objArr = objectFloatMap.keyTable;
        System.arraycopy(objArr, 0, this.keyTable, 0, objArr.length);
        float[] fArr = objectFloatMap.valueTable;
        System.arraycopy(fArr, 0, this.valueTable, 0, fArr.length);
        this.size = objectFloatMap.size;
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

    public void put(K k, float f) {
        int iLocateKey = locateKey(k);
        if (iLocateKey >= 0) {
            this.valueTable[iLocateKey] = f;
            return;
        }
        int i = -(iLocateKey + 1);
        K[] kArr = this.keyTable;
        kArr[i] = k;
        this.valueTable[i] = f;
        int i2 = this.size + 1;
        this.size = i2;
        if (i2 >= this.threshold) {
            resize(kArr.length << 1);
        }
    }

    public float put(K k, float f, float f2) {
        int iLocateKey = locateKey(k);
        if (iLocateKey >= 0) {
            float[] fArr = this.valueTable;
            float f3 = fArr[iLocateKey];
            fArr[iLocateKey] = f;
            return f3;
        }
        int i = -(iLocateKey + 1);
        K[] kArr = this.keyTable;
        kArr[i] = k;
        this.valueTable[i] = f;
        int i2 = this.size + 1;
        this.size = i2;
        if (i2 >= this.threshold) {
            resize(kArr.length << 1);
        }
        return f2;
    }

    public void putAll(ObjectFloatMap<? extends K> objectFloatMap) {
        ensureCapacity(objectFloatMap.size);
        K[] kArr = objectFloatMap.keyTable;
        float[] fArr = objectFloatMap.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                put(k, fArr[i]);
            }
        }
    }

    private void putResize(K k, float f) {
        K[] kArr = this.keyTable;
        int iPlace = place(k);
        while (kArr[iPlace] != null) {
            iPlace = (iPlace + 1) & this.mask;
        }
        kArr[iPlace] = k;
        this.valueTable[iPlace] = f;
    }

    public float get(K k, float f) {
        int iLocateKey = locateKey(k);
        return iLocateKey < 0 ? f : this.valueTable[iLocateKey];
    }

    public float getAndIncrement(K k, float f, float f2) {
        int iLocateKey = locateKey(k);
        if (iLocateKey >= 0) {
            float[] fArr = this.valueTable;
            float f3 = fArr[iLocateKey];
            fArr[iLocateKey] = f2 + f3;
            return f3;
        }
        int i = -(iLocateKey + 1);
        K[] kArr = this.keyTable;
        kArr[i] = k;
        this.valueTable[i] = f2 + f;
        int i2 = this.size + 1;
        this.size = i2;
        if (i2 >= this.threshold) {
            resize(kArr.length << 1);
        }
        return f;
    }

    public float remove(K k, float f) {
        int iLocateKey = locateKey(k);
        if (iLocateKey < 0) {
            return f;
        }
        K[] kArr = this.keyTable;
        float[] fArr = this.valueTable;
        float f2 = fArr[iLocateKey];
        int i = this.mask;
        int i2 = iLocateKey + 1;
        while (true) {
            int i3 = i2 & i;
            K k2 = kArr[i3];
            if (k2 != null) {
                int iPlace = place(k2);
                if (((i3 - iPlace) & i) > ((iLocateKey - iPlace) & i)) {
                    kArr[iLocateKey] = k2;
                    fArr[iLocateKey] = fArr[i3];
                    iLocateKey = i3;
                }
                i2 = i3 + 1;
            } else {
                kArr[iLocateKey] = null;
                this.size--;
                return f2;
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

    public boolean containsValue(float f) {
        K[] kArr = this.keyTable;
        float[] fArr = this.valueTable;
        for (int length = fArr.length - 1; length >= 0; length--) {
            if (kArr[length] != null && fArr[length] == f) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(float f, float f2) {
        K[] kArr = this.keyTable;
        float[] fArr = this.valueTable;
        for (int length = fArr.length - 1; length >= 0; length--) {
            if (kArr[length] != null && Math.abs(fArr[length] - f) <= f2) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(K k) {
        return locateKey(k) >= 0;
    }

    public K findKey(float f) {
        K[] kArr = this.keyTable;
        float[] fArr = this.valueTable;
        for (int length = fArr.length - 1; length >= 0; length--) {
            K k = kArr[length];
            if (k != null && fArr[length] == f) {
                return k;
            }
        }
        return null;
    }

    public K findKey(float f, float f2) {
        K[] kArr = this.keyTable;
        float[] fArr = this.valueTable;
        for (int length = fArr.length - 1; length >= 0; length--) {
            K k = kArr[length];
            if (k != null && Math.abs(fArr[length] - f) <= f2) {
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
        float[] fArr = this.valueTable;
        this.keyTable = (K[]) new Object[i];
        this.valueTable = new float[i];
        if (this.size > 0) {
            for (int i3 = 0; i3 < length; i3++) {
                K k = kArr[i3];
                if (k != null) {
                    putResize(k, fArr[i3]);
                }
            }
        }
    }

    public int hashCode() {
        int iHashCode = this.size;
        K[] kArr = this.keyTable;
        float[] fArr = this.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                iHashCode += k.hashCode() + NumberUtils.floatToRawIntBits(fArr[i]);
            }
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ObjectFloatMap)) {
            return false;
        }
        ObjectFloatMap objectFloatMap = (ObjectFloatMap) obj;
        if (objectFloatMap.size != this.size) {
            return false;
        }
        K[] kArr = this.keyTable;
        float[] fArr = this.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                float f = objectFloatMap.get(k, 0.0f);
                if ((f == 0.0f && !objectFloatMap.containsKey(k)) || f != fArr[i]) {
                    return false;
                }
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
        float[] fArr = this.valueTable;
        int length = kArr.length;
        while (true) {
            i = length - 1;
            if (length > 0) {
                K k = kArr[i];
                if (k != null) {
                    sb.append(k);
                    sb.append('=');
                    sb.append(fArr[i]);
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
                sb.append(fArr[i2]);
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectFloatMap$Entry.class
      classes8.dex
     */
    public static class Entry<K> {
        public K key;
        public float value;

        public String toString() {
            return this.key + ContainerUtils.KEY_VALUE_DELIMITER + this.value;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectFloatMap$MapIterator.class
      classes8.dex
     */
    private static class MapIterator<K> {
        int currentIndex;
        public boolean hasNext;
        final ObjectFloatMap<K> map;
        int nextIndex;
        boolean valid = true;

        public MapIterator(ObjectFloatMap<K> objectFloatMap) {
            this.map = objectFloatMap;
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
            float[] fArr = this.map.valueTable;
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
                    fArr[i] = fArr[i4];
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectFloatMap$Entries.class
      classes8.dex
     */
    public static class Entries<K> extends MapIterator<K> implements Iterable<Entry<K>>, Iterator<Entry<K>> {
        Entry<K> entry;

        @Override // java.lang.Iterable
        public Entries<K> iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.ObjectFloatMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.ObjectFloatMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(ObjectFloatMap<K> objectFloatMap) {
            super(objectFloatMap);
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectFloatMap$Values.class
      classes8.dex
     */
    public static class Values extends MapIterator<Object> {
        public Values iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.ObjectFloatMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.ObjectFloatMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(ObjectFloatMap<?> objectFloatMap) {
            super(objectFloatMap);
        }

        public boolean hasNext() {
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            return this.hasNext;
        }

        public float next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            float f = this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return f;
        }

        public FloatArray toArray() {
            FloatArray floatArray = new FloatArray(true, this.map.size);
            while (this.hasNext) {
                floatArray.add(next());
            }
            return floatArray;
        }

        public FloatArray toArray(FloatArray floatArray) {
            while (this.hasNext) {
                floatArray.add(next());
            }
            return floatArray;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/ObjectFloatMap$Keys.class
      classes8.dex
     */
    public static class Keys<K> extends MapIterator<K> implements Iterable<K>, Iterator<K> {
        @Override // java.lang.Iterable
        public Keys<K> iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.ObjectFloatMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.ObjectFloatMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(ObjectFloatMap<K> objectFloatMap) {
            super(objectFloatMap);
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
