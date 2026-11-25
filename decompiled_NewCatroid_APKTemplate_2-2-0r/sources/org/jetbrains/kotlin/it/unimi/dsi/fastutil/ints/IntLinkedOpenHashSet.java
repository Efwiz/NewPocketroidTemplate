package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public class IntLinkedOpenHashSet extends AbstractIntSortedSet implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNull;
    protected final float f;
    protected transient int first;
    protected transient int[] key;
    protected transient int last;
    protected transient long[] link;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSortedSet, java.util.SortedSet
    public IntComparator comparator() {
        return null;
    }

    public IntLinkedOpenHashSet(int i, float f) {
        this.first = -1;
        this.last = -1;
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        int iArraySize = HashCommon.arraySize(i, f);
        this.n = iArraySize;
        this.minN = iArraySize;
        this.mask = iArraySize - 1;
        this.maxFill = HashCommon.maxFill(iArraySize, f);
        int i2 = this.n;
        this.key = new int[i2 + 1];
        this.link = new long[i2 + 1];
    }

    public IntLinkedOpenHashSet() {
        this(16, 0.75f);
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int i) {
        int iArraySize = HashCommon.arraySize(i, this.f);
        if (iArraySize > this.n) {
            rehash(iArraySize);
        }
    }

    private void tryCapacity(long j) {
        int iMin = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(j / this.f))));
        if (iMin > this.n) {
            rehash(iMin);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean addAll(IntCollection intCollection) {
        if (this.f <= 0.5d) {
            ensureCapacity(intCollection.size());
        } else {
            tryCapacity(size() + intCollection.size());
        }
        return super.addAll(intCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Integer> collection) {
        if (this.f <= 0.5d) {
            ensureCapacity(collection.size());
        } else {
            tryCapacity(size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean add(int i) {
        int i2;
        int i3;
        if (i == 0) {
            if (this.containsNull) {
                return false;
            }
            i2 = this.n;
            this.containsNull = true;
        } else {
            int[] iArr = this.key;
            int iMix = HashCommon.mix(i) & this.mask;
            int i4 = iArr[iMix];
            if (i4 != 0) {
                if (i4 == i) {
                    return false;
                }
                do {
                    iMix = (iMix + 1) & this.mask;
                    i3 = iArr[iMix];
                    if (i3 != 0) {
                    }
                } while (i3 != i);
                return false;
            }
            iArr[iMix] = i;
            i2 = iMix;
        }
        int i5 = this.size;
        if (i5 == 0) {
            this.last = i2;
            this.first = i2;
            this.link[i2] = -1;
        } else {
            long[] jArr = this.link;
            int i6 = this.last;
            long j = jArr[i6];
            jArr[i6] = j ^ (((((long) i2) & 4294967295L) ^ j) & 4294967295L);
            jArr[i2] = ((((long) i6) & 4294967295L) << 32) | 4294967295L;
            this.last = i2;
        }
        this.size = i5 + 1;
        if (i5 >= this.maxFill) {
            rehash(HashCommon.arraySize(i5 + 2, this.f));
        }
        return true;
    }

    protected final void shiftKeys(int i) {
        int i2;
        int[] iArr = this.key;
        while (true) {
            int i3 = (i + 1) & this.mask;
            while (true) {
                i2 = iArr[i3];
                if (i2 == 0) {
                    iArr[i] = 0;
                    return;
                }
                int iMix = HashCommon.mix(i2);
                int i4 = this.mask;
                int i5 = iMix & i4;
                if (i > i3) {
                    if (i >= i5 && i5 > i3) {
                        break;
                    } else {
                        i3 = (i3 + 1) & i4;
                    }
                } else if (i >= i5 || i5 > i3) {
                    break;
                } else {
                    i3 = (i3 + 1) & i4;
                }
            }
            iArr[i] = i2;
            fixPointers(i3, i);
            i = i3;
        }
    }

    private boolean removeEntry(int i) {
        this.size--;
        fixPointers(i);
        shiftKeys(i);
        int i2 = this.n;
        if (i2 > this.minN && this.size < this.maxFill / 4 && i2 > 16) {
            rehash(i2 / 2);
        }
        return true;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        int[] iArr = this.key;
        int i = this.n;
        iArr[i] = 0;
        this.size--;
        fixPointers(i);
        int i2 = this.n;
        if (i2 > this.minN && this.size < this.maxFill / 4 && i2 > 16) {
            rehash(i2 / 2);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet
    public boolean remove(int i) {
        int i2;
        if (i == 0) {
            if (this.containsNull) {
                return removeNullEntry();
            }
            return false;
        }
        int[] iArr = this.key;
        int iMix = HashCommon.mix(i) & this.mask;
        int i3 = iArr[iMix];
        if (i3 == 0) {
            return false;
        }
        if (i == i3) {
            return removeEntry(iMix);
        }
        do {
            iMix = (iMix + 1) & this.mask;
            i2 = iArr[iMix];
            if (i2 == 0) {
                return false;
            }
        } while (i != i2);
        return removeEntry(iMix);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean contains(int i) {
        int i2;
        if (i == 0) {
            return this.containsNull;
        }
        int[] iArr = this.key;
        int iMix = HashCommon.mix(i) & this.mask;
        int i3 = iArr[iMix];
        if (i3 == 0) {
            return false;
        }
        if (i == i3) {
            return true;
        }
        do {
            iMix = (iMix + 1) & this.mask;
            i2 = iArr[iMix];
            if (i2 == 0) {
                return false;
            }
        } while (i != i2);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0);
        this.last = -1;
        this.first = -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    protected void fixPointers(int i) {
        if (this.size == 0) {
            this.last = -1;
            this.first = -1;
            return;
        }
        if (this.first == i) {
            long[] jArr = this.link;
            int i2 = (int) jArr[i];
            this.first = i2;
            if (i2 >= 0) {
                jArr[i2] = (-4294967296L) | jArr[i2];
                return;
            }
            return;
        }
        if (this.last == i) {
            long[] jArr2 = this.link;
            int i3 = (int) (jArr2[i] >>> 32);
            this.last = i3;
            if (i3 >= 0) {
                jArr2[i3] = jArr2[i3] | 4294967295L;
                return;
            }
            return;
        }
        long[] jArr3 = this.link;
        long j = jArr3[i];
        int i4 = (int) (j >>> 32);
        int i5 = (int) j;
        long j2 = jArr3[i4];
        jArr3[i4] = (4294967295L & ((j & 4294967295L) ^ j2)) ^ j2;
        long j3 = jArr3[i5];
        jArr3[i5] = ((-4294967296L) & ((j & (-4294967296L)) ^ j3)) ^ j3;
    }

    protected void fixPointers(int i, int i2) {
        if (this.size == 1) {
            this.last = i2;
            this.first = i2;
            this.link[i2] = -1;
            return;
        }
        if (this.first == i) {
            this.first = i2;
            long[] jArr = this.link;
            long j = jArr[i];
            int i3 = (int) j;
            jArr[i3] = ((-4294967296L) & (((4294967295L & ((long) i2)) << 32) ^ jArr[(int) j])) ^ jArr[i3];
            jArr[i2] = jArr[i];
            return;
        }
        if (this.last == i) {
            this.last = i2;
            long[] jArr2 = this.link;
            long j2 = jArr2[i];
            int i4 = (int) (j2 >>> 32);
            jArr2[i4] = ((jArr2[(int) (j2 >>> 32)] ^ (((long) i2) & 4294967295L)) & 4294967295L) ^ jArr2[i4];
            jArr2[i2] = jArr2[i];
            return;
        }
        long[] jArr3 = this.link;
        long j3 = jArr3[i];
        int i5 = (int) (j3 >>> 32);
        int i6 = (int) j3;
        long j4 = jArr3[i5];
        long j5 = ((long) i2) & 4294967295L;
        jArr3[i5] = ((j4 ^ j5) & 4294967295L) ^ j4;
        long j6 = jArr3[i6];
        jArr3[i6] = ((-4294967296L) & ((j5 << 32) ^ j6)) ^ j6;
        jArr3[i2] = j3;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSortedSet
    public int firstInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSortedSet
    public int lastInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSortedSet
    public IntSortedSet tailSet(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSortedSet
    public IntSortedSet headSet(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSortedSet
    public IntSortedSet subSet(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    private final class SetIterator implements IntListIterator {
        int index;
        int next;
        int prev = -1;
        int curr = -1;

        SetIterator() {
            this.next = -1;
            this.index = -1;
            this.next = IntLinkedOpenHashSet.this.first;
            this.index = 0;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return this.next != -1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
        public boolean hasPrevious() {
            return this.prev != -1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            long[] jArr = IntLinkedOpenHashSet.this.link;
            int i = this.curr;
            this.next = (int) jArr[i];
            this.prev = i;
            int i2 = this.index;
            if (i2 >= 0) {
                this.index = i2 + 1;
            }
            return IntLinkedOpenHashSet.this.key[this.curr];
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            long[] jArr = IntLinkedOpenHashSet.this.link;
            int i = this.curr;
            this.prev = (int) (jArr[i] >>> 32);
            this.next = i;
            int i2 = this.index;
            if (i2 >= 0) {
                this.index = i2 - 1;
            }
            return IntLinkedOpenHashSet.this.key[this.curr];
        }

        @Override // java.util.PrimitiveIterator
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            int[] iArr = IntLinkedOpenHashSet.this.key;
            long[] jArr = IntLinkedOpenHashSet.this.link;
            while (true) {
                int i = this.next;
                if (i == -1) {
                    return;
                }
                this.curr = i;
                this.next = (int) jArr[i];
                this.prev = i;
                int i2 = this.index;
                if (i2 >= 0) {
                    this.index = i2 + 1;
                }
                intConsumer.accept(iArr[i]);
            }
        }

        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
                return;
            }
            if (this.next == -1) {
                this.index = IntLinkedOpenHashSet.this.size;
                return;
            }
            int i = IntLinkedOpenHashSet.this.first;
            this.index = 1;
            while (i != this.prev) {
                i = (int) IntLinkedOpenHashSet.this.link[i];
                this.index++;
            }
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            ensureIndexKnown();
            return this.index;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            ensureIndexKnown();
            return this.index - 1;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            int i;
            int i2;
            ensureIndexKnown();
            int i3 = this.curr;
            if (i3 == -1) {
                throw new IllegalStateException();
            }
            if (i3 == this.prev) {
                this.index--;
                this.prev = (int) (IntLinkedOpenHashSet.this.link[this.curr] >>> 32);
            } else {
                this.next = (int) IntLinkedOpenHashSet.this.link[this.curr];
            }
            IntLinkedOpenHashSet.this.size--;
            if (this.prev == -1) {
                IntLinkedOpenHashSet.this.first = this.next;
            } else {
                long[] jArr = IntLinkedOpenHashSet.this.link;
                int i4 = this.prev;
                jArr[i4] = jArr[i4] ^ ((IntLinkedOpenHashSet.this.link[this.prev] ^ (((long) this.next) & 4294967295L)) & 4294967295L);
            }
            if (this.next == -1) {
                IntLinkedOpenHashSet.this.last = this.prev;
            } else {
                long[] jArr2 = IntLinkedOpenHashSet.this.link;
                int i5 = this.next;
                jArr2[i5] = ((((4294967295L & ((long) this.prev)) << 32) ^ IntLinkedOpenHashSet.this.link[this.next]) & (-4294967296L)) ^ jArr2[i5];
            }
            int i6 = this.curr;
            this.curr = -1;
            if (i6 == IntLinkedOpenHashSet.this.n) {
                IntLinkedOpenHashSet.this.containsNull = false;
                IntLinkedOpenHashSet.this.key[IntLinkedOpenHashSet.this.n] = 0;
                return;
            }
            int[] iArr = IntLinkedOpenHashSet.this.key;
            while (true) {
                int i7 = i6 + 1;
                int i8 = IntLinkedOpenHashSet.this.mask;
                while (true) {
                    i = i7 & i8;
                    i2 = iArr[i];
                    if (i2 == 0) {
                        iArr[i6] = 0;
                        return;
                    }
                    int iMix = HashCommon.mix(i2) & IntLinkedOpenHashSet.this.mask;
                    if (i6 > i) {
                        if (i6 >= iMix && iMix > i) {
                            break;
                        }
                        i7 = i + 1;
                        i8 = IntLinkedOpenHashSet.this.mask;
                    } else {
                        if (i6 >= iMix || iMix > i) {
                            break;
                        }
                        i7 = i + 1;
                        i8 = IntLinkedOpenHashSet.this.mask;
                    }
                }
                iArr[i6] = i2;
                if (this.next == i) {
                    this.next = i6;
                }
                if (this.prev == i) {
                    this.prev = i6;
                }
                IntLinkedOpenHashSet.this.fixPointers(i, i6);
                i6 = i;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSortedSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public IntListIterator iterator() {
        return new SetIterator();
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public IntSpliterator spliterator() {
        return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 337);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer intConsumer) {
        int i = this.first;
        while (i != -1) {
            int i2 = (int) this.link[i];
            intConsumer.accept(this.key[i]);
            i = i2;
        }
    }

    protected void rehash(int i) {
        int i2;
        int[] iArr = this.key;
        int i3 = i - 1;
        int i4 = i + 1;
        int[] iArr2 = new int[i4];
        int i5 = this.first;
        long[] jArr = this.link;
        long[] jArr2 = new long[i4];
        int i6 = -1;
        this.first = -1;
        int i7 = this.size;
        int i8 = -1;
        int i9 = -1;
        while (true) {
            int i10 = i7 - 1;
            if (i7 == 0) {
                break;
            }
            int i11 = iArr[i5];
            if (i11 != 0) {
                int iMix = HashCommon.mix(i11);
                while (true) {
                    i2 = iMix & i3;
                    if (iArr2[i2] == 0) {
                        break;
                    } else {
                        iMix = i2 + 1;
                    }
                }
            } else {
                i2 = i;
            }
            iArr2[i2] = iArr[i5];
            if (i9 != i6) {
                long j = jArr2[i8];
                jArr2[i8] = (((((long) i2) & 4294967295L) ^ j) & 4294967295L) ^ j;
                long j2 = jArr2[i2];
                int i12 = i2;
                jArr2[i12] = ((((((long) i8) & 4294967295L) << 32) ^ j2) & (-4294967296L)) ^ j2;
                i2 = i12;
            } else {
                this.first = i2;
                jArr2[i2] = -1;
            }
            i9 = i5;
            i5 = (int) jArr[i5];
            i8 = i2;
            i7 = i10;
            i6 = -1;
        }
        this.link = jArr2;
        this.last = i8;
        if (i8 != -1) {
            jArr2[i8] = jArr2[i8] | 4294967295L;
        }
        this.n = i;
        this.mask = i3;
        this.maxFill = HashCommon.maxFill(i, this.f);
        this.key = iArr2;
    }

    public IntLinkedOpenHashSet clone() {
        try {
            IntLinkedOpenHashSet intLinkedOpenHashSet = (IntLinkedOpenHashSet) super.clone();
            intLinkedOpenHashSet.key = (int[]) this.key.clone();
            intLinkedOpenHashSet.containsNull = this.containsNull;
            intLinkedOpenHashSet.link = (long[]) this.link.clone();
            return intLinkedOpenHashSet;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, java.util.Collection, java.util.Set
    public int hashCode() {
        int i;
        int iRealSize = realSize();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = iRealSize - 1;
            if (iRealSize == 0) {
                return i2;
            }
            while (true) {
                i = this.key[i3];
                if (i == 0) {
                    i3++;
                }
            }
            i2 += i;
            i3++;
            iRealSize = i4;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        IntListIterator it = iterator();
        objectOutputStream.defaultWriteObject();
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return;
            }
            objectOutputStream.writeInt(it.nextInt());
            i = i2;
        }
    }

    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v4 */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        int iMix;
        objectInputStream.defaultReadObject();
        int iArraySize = HashCommon.arraySize(this.size, this.f);
        this.n = iArraySize;
        this.maxFill = HashCommon.maxFill(iArraySize, this.f);
        int i = this.n;
        this.mask = i - 1;
        int[] iArr = new int[i + 1];
        this.key = iArr;
        ?? r3 = 1;
        long[] jArr = new long[i + 1];
        this.link = jArr;
        int i2 = -1;
        this.last = -1;
        this.first = -1;
        int i3 = this.size;
        int i4 = -1;
        while (true) {
            int i5 = i3 - 1;
            if (i3 == 0) {
                break;
            }
            int i6 = objectInputStream.readInt();
            if (i6 == 0) {
                iMix = this.n;
                this.containsNull = r3;
            } else {
                iMix = HashCommon.mix(i6) & this.mask;
                if (iArr[iMix] != 0) {
                    do {
                        iMix = (iMix + r3) & this.mask;
                    } while (iArr[iMix] != 0);
                }
            }
            iArr[iMix] = i6;
            if (this.first != i2) {
                long j = jArr[i4];
                jArr[i4] = (((((long) iMix) & 4294967295L) ^ j) & 4294967295L) ^ j;
                long j2 = jArr[iMix];
                jArr[iMix] = j2 ^ ((((4294967295L & ((long) i4)) << 32) ^ j2) & (-4294967296L));
            } else {
                this.first = iMix;
                jArr[iMix] = jArr[iMix] | (-4294967296L);
            }
            i3 = i5;
            i4 = iMix;
            r3 = 1;
            i2 = -1;
        }
        this.last = i4;
        if (i4 != -1) {
            jArr[i4] = jArr[i4] | 4294967295L;
        }
    }
}
