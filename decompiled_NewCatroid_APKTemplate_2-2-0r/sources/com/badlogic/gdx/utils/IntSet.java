package com.badlogic.gdx.utils;

import java.util.Arrays;
import java.util.NoSuchElementException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IntSet.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class IntSet {
    boolean hasZeroValue;
    private transient IntSetIterator iterator1;
    private transient IntSetIterator iterator2;
    int[] keyTable;
    private final float loadFactor;
    protected int mask;
    protected int shift;
    public int size;
    private int threshold;

    public IntSet() {
        this(51, 0.8f);
    }

    public IntSet(int i) {
        this(i, 0.8f);
    }

    public IntSet(int i, float f) {
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
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public IntSet(IntSet intSet) {
        float length = intSet.keyTable.length;
        float f = intSet.loadFactor;
        this((int) (length * f), f);
        int[] iArr = intSet.keyTable;
        System.arraycopy(iArr, 0, this.keyTable, 0, iArr.length);
        this.size = intSet.size;
        this.hasZeroValue = intSet.hasZeroValue;
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

    public boolean add(int i) {
        if (i == 0) {
            if (this.hasZeroValue) {
                return false;
            }
            this.hasZeroValue = true;
            this.size++;
            return true;
        }
        int iLocateKey = locateKey(i);
        if (iLocateKey >= 0) {
            return false;
        }
        int i2 = -(iLocateKey + 1);
        int[] iArr = this.keyTable;
        iArr[i2] = i;
        int i3 = this.size + 1;
        this.size = i3;
        if (i3 >= this.threshold) {
            resize(iArr.length << 1);
        }
        return true;
    }

    public void addAll(IntArray intArray) {
        addAll(intArray.items, 0, intArray.size);
    }

    public void addAll(IntArray intArray, int i, int i2) {
        if (i + i2 > intArray.size) {
            throw new IllegalArgumentException("offset + length must be <= size: " + i + " + " + i2 + " <= " + intArray.size);
        }
        addAll(intArray.items, i, i2);
    }

    public void addAll(int... iArr) {
        addAll(iArr, 0, iArr.length);
    }

    public void addAll(int[] iArr, int i, int i2) {
        ensureCapacity(i2);
        int i3 = i2 + i;
        while (i < i3) {
            add(iArr[i]);
            i++;
        }
    }

    public void addAll(IntSet intSet) {
        ensureCapacity(intSet.size);
        if (intSet.hasZeroValue) {
            add(0);
        }
        for (int i : intSet.keyTable) {
            if (i != 0) {
                add(i);
            }
        }
    }

    private void addResize(int i) {
        int[] iArr = this.keyTable;
        int iPlace = place(i);
        while (iArr[iPlace] != 0) {
            iPlace = (iPlace + 1) & this.mask;
        }
        iArr[iPlace] = i;
    }

    public boolean remove(int i) {
        if (i == 0) {
            if (!this.hasZeroValue) {
                return false;
            }
            this.hasZeroValue = false;
            this.size--;
            return true;
        }
        int iLocateKey = locateKey(i);
        if (iLocateKey < 0) {
            return false;
        }
        int[] iArr = this.keyTable;
        int i2 = this.mask;
        int i3 = iLocateKey + 1;
        while (true) {
            int i4 = i3 & i2;
            int i5 = iArr[i4];
            if (i5 != 0) {
                int iPlace = place(i5);
                if (((i4 - iPlace) & i2) > ((iLocateKey - iPlace) & i2)) {
                    iArr[iLocateKey] = i5;
                    iLocateKey = i4;
                }
                i3 = i4 + 1;
            } else {
                iArr[iLocateKey] = 0;
                this.size--;
                return true;
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
        this.size = 0;
        Arrays.fill(this.keyTable, 0);
        this.hasZeroValue = false;
    }

    public boolean contains(int i) {
        if (i == 0) {
            return this.hasZeroValue;
        }
        return locateKey(i) >= 0;
    }

    public int first() {
        if (this.hasZeroValue) {
            return 0;
        }
        for (int i : this.keyTable) {
            if (i != 0) {
                return i;
            }
        }
        throw new IllegalStateException("IntSet is empty.");
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
        this.keyTable = new int[i];
        if (this.size > 0) {
            for (int i3 = 0; i3 < length; i3++) {
                int i4 = iArr[i3];
                if (i4 != 0) {
                    addResize(i4);
                }
            }
        }
    }

    public int hashCode() {
        int i = this.size;
        for (int i2 : this.keyTable) {
            if (i2 != 0) {
                i += i2;
            }
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IntSet)) {
            return false;
        }
        IntSet intSet = (IntSet) obj;
        if (intSet.size != this.size || intSet.hasZeroValue != this.hasZeroValue) {
            return false;
        }
        for (int i : this.keyTable) {
            if (i != 0 && !intSet.contains(i)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0032  */
    /* JADX WARN: Code duplicated, block: B:21:0x0037 A[EDGE_INSN: B:21:0x0037->B:15:0x002d BREAK  A[LOOP:0: B:9:0x0020->B:13:0x0028]] */
    /* JADX WARN: Code duplicated, block: B:22:0x0040  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x002d -> B:16:0x002e). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public java.lang.String toString() {
        /*
            r5 = this;
            int r0 = r5.size
            if (r0 != 0) goto L7
            java.lang.String r0 = "[]"
            return r0
        L7:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 32
            r0.<init>(r1)
            r1 = 91
            r0.append(r1)
            int[] r1 = r5.keyTable
            int r2 = r1.length
            boolean r3 = r5.hasZeroValue
            if (r3 == 0) goto L20
            java.lang.String r3 = "0"
            r0.append(r3)
            goto L2e
        L20:
            int r3 = r2 + (-1)
            if (r2 <= 0) goto L2d
            r2 = r1[r3]
            if (r2 != 0) goto L2a
            r2 = r3
            goto L20
        L2a:
            r0.append(r2)
        L2d:
            r2 = r3
        L2e:
            int r3 = r2 + (-1)
            if (r2 <= 0) goto L40
            r2 = r1[r3]
            if (r2 != 0) goto L37
            goto L2d
        L37:
            java.lang.String r4 = ", "
            r0.append(r4)
            r0.append(r2)
            goto L2d
        L40:
            r1 = 93
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.IntSet.toString():java.lang.String");
    }

    public IntSetIterator iterator() {
        if (Collections.allocateIterators) {
            return new IntSetIterator(this);
        }
        if (this.iterator1 == null) {
            this.iterator1 = new IntSetIterator(this);
            this.iterator2 = new IntSetIterator(this);
        }
        if (!this.iterator1.valid) {
            this.iterator1.reset();
            this.iterator1.valid = true;
            this.iterator2.valid = false;
            return this.iterator1;
        }
        this.iterator2.reset();
        this.iterator2.valid = true;
        this.iterator1.valid = false;
        return this.iterator2;
    }

    public static IntSet with(int... iArr) {
        IntSet intSet = new IntSet();
        intSet.addAll(iArr);
        return intSet;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IntSet$IntSetIterator.class
      classes8.dex
     */
    public static class IntSetIterator {
        private static final int INDEX_ILLEGAL = -2;
        private static final int INDEX_ZERO = -1;
        int currentIndex;
        public boolean hasNext;
        int nextIndex;
        final IntSet set;
        boolean valid = true;

        public IntSetIterator(IntSet intSet) {
            this.set = intSet;
            reset();
        }

        public void reset() {
            this.currentIndex = -2;
            this.nextIndex = -1;
            if (this.set.hasZeroValue) {
                this.hasNext = true;
            } else {
                findNextIndex();
            }
        }

        void findNextIndex() {
            int i;
            int[] iArr = this.set.keyTable;
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
            if (i == -1 && this.set.hasZeroValue) {
                this.set.hasZeroValue = false;
            } else {
                if (i < 0) {
                    throw new IllegalStateException("next must be called before remove.");
                }
                int[] iArr = this.set.keyTable;
                int i2 = this.set.mask;
                int i3 = i + 1;
                while (true) {
                    int i4 = i3 & i2;
                    int i5 = iArr[i4];
                    if (i5 == 0) {
                        break;
                    }
                    int iPlace = this.set.place(i5);
                    if (((i4 - iPlace) & i2) > ((i - iPlace) & i2)) {
                        iArr[i] = i5;
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
            this.set.size--;
        }

        public int next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            int i = this.nextIndex == -1 ? 0 : this.set.keyTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return i;
        }

        public IntArray toArray() {
            IntArray intArray = new IntArray(true, this.set.size);
            while (this.hasNext) {
                intArray.add(next());
            }
            return intArray;
        }
    }
}
