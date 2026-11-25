package com.badlogic.gdx.utils;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IntIntMap.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class IntIntMap implements Iterable<Entry> {
    private transient Entries entries1;
    private transient Entries entries2;
    boolean hasZeroValue;
    int[] keyTable;
    private transient Keys keys1;
    private transient Keys keys2;
    private final float loadFactor;
    protected int mask;
    protected int shift;
    public int size;
    private int threshold;
    int[] valueTable;
    private transient Values values1;
    private transient Values values2;
    int zeroValue;

    public IntIntMap() {
        this(51, 0.8f);
    }

    public IntIntMap(int i) {
        this(i, 0.8f);
    }

    public IntIntMap(int i, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + f);
        }
        this.loadFactor = f;
        int iTableSize = ObjectSet.tableSize(i, f);
        this.threshold = (int) (iTableSize * f);
        int i2 = iTableSize - 1;
        this.mask = i2;
        this.shift = Long.numberOfLeadingZeros(i2);
        this.keyTable = new int[iTableSize];
        this.valueTable = new int[iTableSize];
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public IntIntMap(IntIntMap intIntMap) {
        float length = intIntMap.keyTable.length;
        float f = intIntMap.loadFactor;
        this((int) (length * f), f);
        int[] iArr = intIntMap.keyTable;
        System.arraycopy(iArr, 0, this.keyTable, 0, iArr.length);
        int[] iArr2 = intIntMap.valueTable;
        System.arraycopy(iArr2, 0, this.valueTable, 0, iArr2.length);
        this.size = intIntMap.size;
        this.zeroValue = intIntMap.zeroValue;
        this.hasZeroValue = intIntMap.hasZeroValue;
    }

    protected int place(int i) {
        return (int) ((((long) i) * (-7046029254386353131L)) >>> this.shift);
    }

    private int locateKey(int i) {
        int[] iArr = this.keyTable;
        int iPlace = place(i);
        while (true) {
            int i2 = iArr[iPlace];
            if (i2 == 0) {
                return -(iPlace + 1);
            }
            if (i2 == i) {
                return iPlace;
            }
            iPlace = (iPlace + 1) & this.mask;
        }
    }

    public void put(int i, int i2) {
        if (i == 0) {
            this.zeroValue = i2;
            if (this.hasZeroValue) {
                return;
            }
            this.hasZeroValue = true;
            this.size++;
            return;
        }
        int iLocateKey = locateKey(i);
        if (iLocateKey >= 0) {
            this.valueTable[iLocateKey] = i2;
            return;
        }
        int i3 = -(iLocateKey + 1);
        int[] iArr = this.keyTable;
        iArr[i3] = i;
        this.valueTable[i3] = i2;
        int i4 = this.size + 1;
        this.size = i4;
        if (i4 >= this.threshold) {
            resize(iArr.length << 1);
        }
    }

    public int put(int i, int i2, int i3) {
        if (i == 0) {
            int i4 = this.zeroValue;
            this.zeroValue = i2;
            if (this.hasZeroValue) {
                return i4;
            }
            this.hasZeroValue = true;
            this.size++;
            return i3;
        }
        int iLocateKey = locateKey(i);
        if (iLocateKey >= 0) {
            int[] iArr = this.valueTable;
            int i5 = iArr[iLocateKey];
            iArr[iLocateKey] = i2;
            return i5;
        }
        int i6 = -(iLocateKey + 1);
        int[] iArr2 = this.keyTable;
        iArr2[i6] = i;
        this.valueTable[i6] = i2;
        int i7 = this.size + 1;
        this.size = i7;
        if (i7 >= this.threshold) {
            resize(iArr2.length << 1);
        }
        return i3;
    }

    public void putAll(IntIntMap intIntMap) {
        ensureCapacity(intIntMap.size);
        if (intIntMap.hasZeroValue) {
            put(0, intIntMap.zeroValue);
        }
        int[] iArr = intIntMap.keyTable;
        int[] iArr2 = intIntMap.valueTable;
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            if (i2 != 0) {
                put(i2, iArr2[i]);
            }
        }
    }

    private void putResize(int i, int i2) {
        int[] iArr = this.keyTable;
        int iPlace = place(i);
        while (iArr[iPlace] != 0) {
            iPlace = (iPlace + 1) & this.mask;
        }
        iArr[iPlace] = i;
        this.valueTable[iPlace] = i2;
    }

    public int get(int i, int i2) {
        if (i == 0) {
            return this.hasZeroValue ? this.zeroValue : i2;
        }
        int iLocateKey = locateKey(i);
        return iLocateKey >= 0 ? this.valueTable[iLocateKey] : i2;
    }

    public int getAndIncrement(int i, int i2, int i3) {
        if (i == 0) {
            if (!this.hasZeroValue) {
                this.hasZeroValue = true;
                this.zeroValue = i3 + i2;
                this.size++;
                return i2;
            }
            int i4 = this.zeroValue;
            this.zeroValue = i3 + i4;
            return i4;
        }
        int iLocateKey = locateKey(i);
        if (iLocateKey >= 0) {
            int[] iArr = this.valueTable;
            int i5 = iArr[iLocateKey];
            iArr[iLocateKey] = i3 + i5;
            return i5;
        }
        int i6 = -(iLocateKey + 1);
        int[] iArr2 = this.keyTable;
        iArr2[i6] = i;
        this.valueTable[i6] = i3 + i2;
        int i7 = this.size + 1;
        this.size = i7;
        if (i7 >= this.threshold) {
            resize(iArr2.length << 1);
        }
        return i2;
    }

    public int remove(int i, int i2) {
        if (i == 0) {
            if (!this.hasZeroValue) {
                return i2;
            }
            this.hasZeroValue = false;
            this.size--;
            return this.zeroValue;
        }
        int iLocateKey = locateKey(i);
        if (iLocateKey < 0) {
            return i2;
        }
        int[] iArr = this.keyTable;
        int[] iArr2 = this.valueTable;
        int i3 = iArr2[iLocateKey];
        int i4 = this.mask;
        int i5 = iLocateKey + 1;
        while (true) {
            int i6 = i5 & i4;
            int i7 = iArr[i6];
            if (i7 != 0) {
                int iPlace = place(i7);
                if (((i6 - iPlace) & i4) > ((iLocateKey - iPlace) & i4)) {
                    iArr[iLocateKey] = i7;
                    iArr2[iLocateKey] = iArr2[i6];
                    iLocateKey = i6;
                }
                i5 = i6 + 1;
            } else {
                iArr[iLocateKey] = 0;
                this.size--;
                return i3;
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
            return;
        }
        this.size = 0;
        this.hasZeroValue = false;
        resize(iTableSize);
    }

    public void clear() {
        if (this.size == 0) {
            return;
        }
        Arrays.fill(this.keyTable, 0);
        this.size = 0;
        this.hasZeroValue = false;
    }

    public boolean containsValue(int i) {
        if (this.hasZeroValue && this.zeroValue == i) {
            return true;
        }
        int[] iArr = this.keyTable;
        int[] iArr2 = this.valueTable;
        for (int length = iArr2.length - 1; length >= 0; length--) {
            if (iArr[length] != 0 && iArr2[length] == i) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(int i) {
        if (i == 0) {
            return this.hasZeroValue;
        }
        return locateKey(i) >= 0;
    }

    public int findKey(int i, int i2) {
        if (this.hasZeroValue && this.zeroValue == i) {
            return 0;
        }
        int[] iArr = this.keyTable;
        int[] iArr2 = this.valueTable;
        for (int length = iArr2.length - 1; length >= 0; length--) {
            int i3 = iArr[length];
            if (i3 != 0 && iArr2[length] == i) {
                return i3;
            }
        }
        return i2;
    }

    public void ensureCapacity(int i) {
        int iTableSize = ObjectSet.tableSize(this.size + i, this.loadFactor);
        if (this.keyTable.length < iTableSize) {
            resize(iTableSize);
        }
    }

    private void resize(int i) {
        int length = this.keyTable.length;
        this.threshold = (int) (i * this.loadFactor);
        int i2 = i - 1;
        this.mask = i2;
        this.shift = Long.numberOfLeadingZeros(i2);
        int[] iArr = this.keyTable;
        int[] iArr2 = this.valueTable;
        this.keyTable = new int[i];
        this.valueTable = new int[i];
        if (this.size > 0) {
            for (int i3 = 0; i3 < length; i3++) {
                int i4 = iArr[i3];
                if (i4 != 0) {
                    putResize(i4, iArr2[i3]);
                }
            }
        }
    }

    public int hashCode() {
        int i = this.size;
        if (this.hasZeroValue) {
            i += this.zeroValue;
        }
        int[] iArr = this.keyTable;
        int[] iArr2 = this.valueTable;
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            if (i3 != 0) {
                i += (i3 * 31) + iArr2[i2];
            }
        }
        return i;
    }

    public boolean equals(Object obj) {
        int i;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntIntMap)) {
            return false;
        }
        IntIntMap intIntMap = (IntIntMap) obj;
        if (intIntMap.size != this.size) {
            return false;
        }
        boolean z = intIntMap.hasZeroValue;
        boolean z2 = this.hasZeroValue;
        if (z != z2) {
            return false;
        }
        if (z2 && intIntMap.zeroValue != this.zeroValue) {
            return false;
        }
        int[] iArr = this.keyTable;
        int[] iArr2 = this.valueTable;
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            if (i3 != 0 && (((i = intIntMap.get(i3, 0)) == 0 && !intIntMap.containsKey(i3)) || i != iArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0043  */
    /* JADX WARN: Code duplicated, block: B:21:0x0048 A[EDGE_INSN: B:21:0x0048->B:15:0x003e BREAK  A[LOOP:0: B:9:0x0029->B:13:0x0031]] */
    /* JADX WARN: Code duplicated, block: B:22:0x0059  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x003e -> B:16:0x003f). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public java.lang.String toString() {
        /*
            r7 = this;
            int r0 = r7.size
            if (r0 != 0) goto L7
            java.lang.String r0 = "[]"
            return r0
        L7:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 32
            r0.<init>(r1)
            r1 = 91
            r0.append(r1)
            int[] r1 = r7.keyTable
            int[] r2 = r7.valueTable
            int r3 = r1.length
            boolean r4 = r7.hasZeroValue
            r5 = 61
            if (r4 == 0) goto L29
            java.lang.String r4 = "0="
            r0.append(r4)
            int r4 = r7.zeroValue
            r0.append(r4)
            goto L3f
        L29:
            int r4 = r3 + (-1)
            if (r3 <= 0) goto L3e
            r3 = r1[r4]
            if (r3 != 0) goto L33
            r3 = r4
            goto L29
        L33:
            r0.append(r3)
            r0.append(r5)
            r3 = r2[r4]
            r0.append(r3)
        L3e:
            r3 = r4
        L3f:
            int r4 = r3 + (-1)
            if (r3 <= 0) goto L59
            r3 = r1[r4]
            if (r3 != 0) goto L48
            goto L3e
        L48:
            java.lang.String r6 = ", "
            r0.append(r6)
            r0.append(r3)
            r0.append(r5)
            r3 = r2[r4]
            r0.append(r3)
            goto L3e
        L59:
            r1 = 93
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.IntIntMap.toString():java.lang.String");
    }

    @Override // java.lang.Iterable
    public Iterator<Entry> iterator() {
        return entries();
    }

    public Entries entries() {
        if (Collections.allocateIterators) {
            return new Entries(this);
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

    public Keys keys() {
        if (Collections.allocateIterators) {
            return new Keys(this);
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IntIntMap$Entry.class
      classes8.dex
     */
    public static class Entry {
        public int key;
        public int value;

        public String toString() {
            return this.key + ContainerUtils.KEY_VALUE_DELIMITER + this.value;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IntIntMap$MapIterator.class
      classes8.dex
     */
    private static class MapIterator {
        private static final int INDEX_ILLEGAL = -2;
        static final int INDEX_ZERO = -1;
        int currentIndex;
        public boolean hasNext;
        final IntIntMap map;
        int nextIndex;
        boolean valid = true;

        public MapIterator(IntIntMap intIntMap) {
            this.map = intIntMap;
            reset();
        }

        public void reset() {
            this.currentIndex = -2;
            this.nextIndex = -1;
            if (this.map.hasZeroValue) {
                this.hasNext = true;
            } else {
                findNextIndex();
            }
        }

        void findNextIndex() {
            int i;
            int[] iArr = this.map.keyTable;
            int length = iArr.length;
            do {
                i = this.nextIndex + 1;
                this.nextIndex = i;
                if (i >= length) {
                    this.hasNext = false;
                    return;
                }
            } while (iArr[i] == 0);
            this.hasNext = true;
        }

        public void remove() {
            int i = this.currentIndex;
            if (i == -1 && this.map.hasZeroValue) {
                this.map.hasZeroValue = false;
            } else {
                if (i < 0) {
                    throw new IllegalStateException("next must be called before remove.");
                }
                int[] iArr = this.map.keyTable;
                int[] iArr2 = this.map.valueTable;
                int i2 = this.map.mask;
                int i3 = i + 1;
                while (true) {
                    int i4 = i3 & i2;
                    int i5 = iArr[i4];
                    if (i5 == 0) {
                        break;
                    }
                    int iPlace = this.map.place(i5);
                    if (((i4 - iPlace) & i2) > ((i - iPlace) & i2)) {
                        iArr[i] = i5;
                        iArr2[i] = iArr2[i4];
                        i = i4;
                    }
                    i3 = i4 + 1;
                }
                iArr[i] = 0;
                if (i != this.currentIndex) {
                    this.nextIndex--;
                }
            }
            this.currentIndex = -2;
            this.map.size--;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IntIntMap$Entries.class
      classes8.dex
     */
    public static class Entries extends MapIterator implements Iterable<Entry>, Iterator<Entry> {
        private final Entry entry;

        @Override // java.lang.Iterable
        public Iterator<Entry> iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.IntIntMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.IntIntMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(IntIntMap intIntMap) {
            super(intIntMap);
            this.entry = new Entry();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public Entry next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            int[] iArr = this.map.keyTable;
            if (this.nextIndex == -1) {
                this.entry.key = 0;
                this.entry.value = this.map.zeroValue;
            } else {
                this.entry.key = iArr[this.nextIndex];
                this.entry.value = this.map.valueTable[this.nextIndex];
            }
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IntIntMap$Values.class
      classes8.dex
     */
    public static class Values extends MapIterator {
        public Values iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.IntIntMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.IntIntMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(IntIntMap intIntMap) {
            super(intIntMap);
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
            int i = this.nextIndex == -1 ? this.map.zeroValue : this.map.valueTable[this.nextIndex];
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IntIntMap$Keys.class
      classes8.dex
     */
    public static class Keys extends MapIterator {
        @Override // com.badlogic.gdx.utils.IntIntMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.IntIntMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(IntIntMap intIntMap) {
            super(intIntMap);
        }

        public int next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            int i = this.nextIndex == -1 ? 0 : this.map.keyTable[this.nextIndex];
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
}
