package com.badlogic.gdx.utils;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/LongMap.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class LongMap<V> implements Iterable<Entry<V>> {
    private transient Entries entries1;
    private transient Entries entries2;
    boolean hasZeroValue;
    long[] keyTable;
    private transient Keys keys1;
    private transient Keys keys2;
    private final float loadFactor;
    protected int mask;
    protected int shift;
    public int size;
    private int threshold;
    V[] valueTable;
    private transient Values values1;
    private transient Values values2;
    V zeroValue;

    public LongMap() {
        this(51, 0.8f);
    }

    public LongMap(int i) {
        this(i, 0.8f);
    }

    public LongMap(int i, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + f);
        }
        this.loadFactor = f;
        int iTableSize = ObjectSet.tableSize(i, f);
        this.threshold = (int) (iTableSize * f);
        int i2 = iTableSize - 1;
        this.mask = i2;
        this.shift = Long.numberOfLeadingZeros(i2);
        this.keyTable = new long[iTableSize];
        this.valueTable = (V[]) new Object[iTableSize];
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public LongMap(LongMap<? extends V> longMap) {
        float length = longMap.keyTable.length;
        float f = longMap.loadFactor;
        this((int) (length * f), f);
        long[] jArr = longMap.keyTable;
        System.arraycopy(jArr, 0, this.keyTable, 0, jArr.length);
        Object[] objArr = longMap.valueTable;
        System.arraycopy(objArr, 0, this.valueTable, 0, objArr.length);
        this.size = longMap.size;
        this.zeroValue = longMap.zeroValue;
        this.hasZeroValue = longMap.hasZeroValue;
    }

    protected int place(long j) {
        return (int) (((j ^ (j >>> 32)) * (-7046029254386353131L)) >>> this.shift);
    }

    private int locateKey(long j) {
        long[] jArr = this.keyTable;
        int iPlace = place(j);
        while (true) {
            long j2 = jArr[iPlace];
            if (j2 == 0) {
                return -(iPlace + 1);
            }
            if (j2 == j) {
                return iPlace;
            }
            iPlace = (iPlace + 1) & this.mask;
        }
    }

    public V put(long j, V v) {
        if (j == 0) {
            V v2 = this.zeroValue;
            this.zeroValue = v;
            if (!this.hasZeroValue) {
                this.hasZeroValue = true;
                this.size++;
            }
            return v2;
        }
        int iLocateKey = locateKey(j);
        if (iLocateKey >= 0) {
            V[] vArr = this.valueTable;
            V v3 = vArr[iLocateKey];
            vArr[iLocateKey] = v;
            return v3;
        }
        int i = -(iLocateKey + 1);
        long[] jArr = this.keyTable;
        jArr[i] = j;
        this.valueTable[i] = v;
        int i2 = this.size + 1;
        this.size = i2;
        if (i2 < this.threshold) {
            return null;
        }
        resize(jArr.length << 1);
        return null;
    }

    public void putAll(LongMap<? extends V> longMap) {
        ensureCapacity(longMap.size);
        if (longMap.hasZeroValue) {
            put(0L, longMap.zeroValue);
        }
        long[] jArr = longMap.keyTable;
        V[] vArr = longMap.valueTable;
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            long j = jArr[i];
            if (j != 0) {
                put(j, vArr[i]);
            }
        }
    }

    private void putResize(long j, V v) {
        long[] jArr = this.keyTable;
        int iPlace = place(j);
        while (jArr[iPlace] != 0) {
            iPlace = (iPlace + 1) & this.mask;
        }
        jArr[iPlace] = j;
        this.valueTable[iPlace] = v;
    }

    public V get(long j) {
        if (j == 0) {
            if (this.hasZeroValue) {
                return this.zeroValue;
            }
            return null;
        }
        int iLocateKey = locateKey(j);
        if (iLocateKey >= 0) {
            return this.valueTable[iLocateKey];
        }
        return null;
    }

    public V get(long j, V v) {
        if (j == 0) {
            return this.hasZeroValue ? this.zeroValue : v;
        }
        int iLocateKey = locateKey(j);
        return iLocateKey >= 0 ? this.valueTable[iLocateKey] : v;
    }

    public V remove(long j) {
        if (j == 0) {
            if (!this.hasZeroValue) {
                return null;
            }
            this.hasZeroValue = false;
            V v = this.zeroValue;
            this.zeroValue = null;
            this.size--;
            return v;
        }
        int iLocateKey = locateKey(j);
        if (iLocateKey < 0) {
            return null;
        }
        long[] jArr = this.keyTable;
        V[] vArr = this.valueTable;
        V v2 = vArr[iLocateKey];
        int i = this.mask;
        int i2 = iLocateKey + 1;
        while (true) {
            int i3 = i2 & i;
            long j2 = jArr[i3];
            if (j2 != 0) {
                int iPlace = place(j2);
                if (((i3 - iPlace) & i) > ((iLocateKey - iPlace) & i)) {
                    jArr[iLocateKey] = j2;
                    vArr[iLocateKey] = vArr[i3];
                    iLocateKey = i3;
                }
                i2 = i3 + 1;
            } else {
                jArr[iLocateKey] = 0;
                vArr[iLocateKey] = null;
                this.size--;
                return v2;
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
        this.zeroValue = null;
        resize(iTableSize);
    }

    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        Arrays.fill(this.keyTable, 0L);
        Arrays.fill(this.valueTable, (Object) null);
        this.zeroValue = null;
        this.hasZeroValue = false;
    }

    public boolean containsValue(Object obj, boolean z) {
        V[] vArr = this.valueTable;
        if (obj == null) {
            if (this.hasZeroValue && this.zeroValue == null) {
                return true;
            }
            long[] jArr = this.keyTable;
            for (int length = vArr.length - 1; length >= 0; length--) {
                if (jArr[length] != 0 && vArr[length] == null) {
                    return true;
                }
            }
            return false;
        }
        if (z) {
            if (obj == this.zeroValue) {
                return true;
            }
            for (int length2 = vArr.length - 1; length2 >= 0; length2--) {
                if (vArr[length2] == obj) {
                    return true;
                }
            }
            return false;
        }
        if (this.hasZeroValue && obj.equals(this.zeroValue)) {
            return true;
        }
        for (int length3 = vArr.length - 1; length3 >= 0; length3--) {
            if (obj.equals(vArr[length3])) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(long j) {
        if (j == 0) {
            return this.hasZeroValue;
        }
        return locateKey(j) >= 0;
    }

    public long findKey(Object obj, boolean z, long j) {
        V[] vArr = this.valueTable;
        if (obj == null) {
            if (this.hasZeroValue && this.zeroValue == null) {
                return 0L;
            }
            long[] jArr = this.keyTable;
            for (int length = vArr.length - 1; length >= 0; length--) {
                long j2 = jArr[length];
                if (j2 != 0 && vArr[length] == null) {
                    return j2;
                }
            }
        } else if (z) {
            if (obj == this.zeroValue) {
                return 0L;
            }
            for (int length2 = vArr.length - 1; length2 >= 0; length2--) {
                if (vArr[length2] == obj) {
                    return this.keyTable[length2];
                }
            }
        } else {
            if (this.hasZeroValue && obj.equals(this.zeroValue)) {
                return 0L;
            }
            for (int length3 = vArr.length - 1; length3 >= 0; length3--) {
                if (obj.equals(vArr[length3])) {
                    return this.keyTable[length3];
                }
            }
        }
        return j;
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
        long[] jArr = this.keyTable;
        V[] vArr = this.valueTable;
        this.keyTable = new long[i];
        this.valueTable = (V[]) new Object[i];
        if (this.size > 0) {
            for (int i3 = 0; i3 < length; i3++) {
                long j = jArr[i3];
                if (j != 0) {
                    putResize(j, vArr[i3]);
                }
            }
        }
    }

    public int hashCode() {
        V v;
        int iHashCode = this.size;
        if (this.hasZeroValue && (v = this.zeroValue) != null) {
            iHashCode += v.hashCode();
        }
        long[] jArr = this.keyTable;
        V[] vArr = this.valueTable;
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            long j = jArr[i];
            if (j != 0) {
                iHashCode = (int) (((long) iHashCode) + (j * 31));
                V v2 = vArr[i];
                if (v2 != null) {
                    iHashCode += v2.hashCode();
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
        if (!(obj instanceof LongMap)) {
            return false;
        }
        LongMap longMap = (LongMap) obj;
        if (longMap.size != this.size) {
            return false;
        }
        boolean z = longMap.hasZeroValue;
        boolean z2 = this.hasZeroValue;
        if (z != z2) {
            return false;
        }
        if (z2) {
            V v = longMap.zeroValue;
            if (v == null) {
                if (this.zeroValue != null) {
                    return false;
                }
            } else if (!v.equals(this.zeroValue)) {
                return false;
            }
        }
        long[] jArr = this.keyTable;
        V[] vArr = this.valueTable;
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            long j = jArr[i];
            if (j != 0) {
                V v2 = vArr[i];
                if (v2 == null) {
                    if (longMap.get(j, ObjectMap.dummy) != null) {
                        return false;
                    }
                } else if (!v2.equals(longMap.get(j))) {
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
        if (!(obj instanceof LongMap)) {
            return false;
        }
        LongMap longMap = (LongMap) obj;
        if (longMap.size != this.size) {
            return false;
        }
        boolean z = longMap.hasZeroValue;
        boolean z2 = this.hasZeroValue;
        if (z != z2) {
            return false;
        }
        if (z2 && this.zeroValue != longMap.zeroValue) {
            return false;
        }
        long[] jArr = this.keyTable;
        V[] vArr = this.valueTable;
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            long j = jArr[i];
            if (j != 0 && vArr[i] != longMap.get(j, ObjectMap.dummy)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0047  */
    /* JADX WARN: Code duplicated, block: B:21:0x004e A[EDGE_INSN: B:21:0x004e->B:15:0x0042 BREAK  A[LOOP:0: B:9:0x002b->B:13:0x0035]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0042 -> B:16:0x0043). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public java.lang.String toString() {
        /*
            r10 = this;
            int r0 = r10.size
            if (r0 != 0) goto L7
            java.lang.String r0 = "[]"
            return r0
        L7:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 32
            r0.<init>(r1)
            r1 = 91
            r0.append(r1)
            long[] r1 = r10.keyTable
            V[] r2 = r10.valueTable
            int r3 = r1.length
            boolean r4 = r10.hasZeroValue
            r5 = 61
            r6 = 0
            if (r4 == 0) goto L2b
            java.lang.String r4 = "0="
            r0.append(r4)
            V r4 = r10.zeroValue
            r0.append(r4)
            goto L43
        L2b:
            int r4 = r3 + (-1)
            if (r3 <= 0) goto L42
            r8 = r1[r4]
            int r3 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r3 != 0) goto L37
            r3 = r4
            goto L2b
        L37:
            r0.append(r8)
            r0.append(r5)
            r3 = r2[r4]
            r0.append(r3)
        L42:
            r3 = r4
        L43:
            int r4 = r3 + (-1)
            if (r3 <= 0) goto L5f
            r8 = r1[r4]
            int r3 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r3 != 0) goto L4e
            goto L42
        L4e:
            java.lang.String r3 = ", "
            r0.append(r3)
            r0.append(r8)
            r0.append(r5)
            r3 = r2[r4]
            r0.append(r3)
            goto L42
        L5f:
            r1 = 93
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.LongMap.toString():java.lang.String");
    }

    @Override // java.lang.Iterable
    public Iterator<Entry<V>> iterator() {
        return entries();
    }

    public Entries<V> entries() {
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

    public Values<V> values() {
        if (Collections.allocateIterators) {
            return new Values<>(this);
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/LongMap$Entry.class
      classes8.dex
     */
    public static class Entry<V> {
        public long key;
        public V value;

        public String toString() {
            return this.key + ContainerUtils.KEY_VALUE_DELIMITER + this.value;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/LongMap$MapIterator.class
      classes8.dex
     */
    private static class MapIterator<V> {
        private static final int INDEX_ILLEGAL = -2;
        static final int INDEX_ZERO = -1;
        int currentIndex;
        public boolean hasNext;
        final LongMap<V> map;
        int nextIndex;
        boolean valid = true;

        public MapIterator(LongMap<V> longMap) {
            this.map = longMap;
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
            long[] jArr = this.map.keyTable;
            int length = jArr.length;
            do {
                i = this.nextIndex + 1;
                this.nextIndex = i;
                if (i >= length) {
                    this.hasNext = false;
                    return;
                }
            } while (jArr[i] == 0);
            this.hasNext = true;
        }

        public void remove() {
            int i = this.currentIndex;
            if (i == -1 && this.map.hasZeroValue) {
                this.map.hasZeroValue = false;
                this.map.zeroValue = null;
            } else {
                if (i < 0) {
                    throw new IllegalStateException("next must be called before remove.");
                }
                long[] jArr = this.map.keyTable;
                V[] vArr = this.map.valueTable;
                int i2 = this.map.mask;
                int i3 = i + 1;
                while (true) {
                    int i4 = i3 & i2;
                    long j = jArr[i4];
                    if (j == 0) {
                        break;
                    }
                    int iPlace = this.map.place(j);
                    if (((i4 - iPlace) & i2) > ((i - iPlace) & i2)) {
                        jArr[i] = j;
                        vArr[i] = vArr[i4];
                        i = i4;
                    }
                    i3 = i4 + 1;
                }
                jArr[i] = 0;
                vArr[i] = null;
                if (i != this.currentIndex) {
                    this.nextIndex--;
                }
            }
            this.currentIndex = -2;
            this.map.size--;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/LongMap$Entries.class
      classes8.dex
     */
    public static class Entries<V> extends MapIterator<V> implements Iterable<Entry<V>>, Iterator<Entry<V>> {
        private final Entry<V> entry;

        @Override // java.lang.Iterable
        public Iterator<Entry<V>> iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.LongMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.LongMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(LongMap longMap) {
            super(longMap);
            this.entry = new Entry<>();
        }

        @Override // java.util.Iterator
        public Entry<V> next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            long[] jArr = this.map.keyTable;
            if (this.nextIndex == -1) {
                this.entry.key = 0L;
                this.entry.value = this.map.zeroValue;
            } else {
                this.entry.key = jArr[this.nextIndex];
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/LongMap$Values.class
      classes8.dex
     */
    public static class Values<V> extends MapIterator<V> implements Iterable<V>, Iterator<V> {
        @Override // java.lang.Iterable
        public Iterator<V> iterator() {
            return this;
        }

        @Override // com.badlogic.gdx.utils.LongMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.LongMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(LongMap<V> longMap) {
            super(longMap);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            return this.hasNext;
        }

        @Override // java.util.Iterator
        public V next() {
            V v;
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            if (this.nextIndex == -1) {
                v = this.map.zeroValue;
            } else {
                v = this.map.valueTable[this.nextIndex];
            }
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return v;
        }

        public Array<V> toArray() {
            Array<V> array = new Array<>(true, this.map.size);
            while (this.hasNext) {
                array.add(next());
            }
            return array;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/LongMap$Keys.class
      classes8.dex
     */
    public static class Keys extends MapIterator {
        @Override // com.badlogic.gdx.utils.LongMap.MapIterator, java.util.Iterator
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        @Override // com.badlogic.gdx.utils.LongMap.MapIterator
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(LongMap longMap) {
            super(longMap);
        }

        public long next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            long j = this.nextIndex == -1 ? 0L : this.map.keyTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return j;
        }

        public LongArray toArray() {
            LongArray longArray = new LongArray(true, this.map.size);
            while (this.hasNext) {
                longArray.add(next());
            }
            return longArray;
        }

        public LongArray toArray(LongArray longArray) {
            while (this.hasNext) {
                longArray.add(next());
            }
            return longArray;
        }
    }
}
