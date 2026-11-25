package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import com.google.android.gms.cast.MediaError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;

/* JADX INFO: loaded from: classes5.dex */
public class IntOpenHashSet extends AbstractIntSet implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNull;
    protected final float f;
    protected transient int[] key;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;

    public IntOpenHashSet(int i, float f) {
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
        this.key = new int[this.n + 1];
    }

    public IntOpenHashSet(int i) {
        this(i, 0.75f);
    }

    public IntOpenHashSet() {
        this(16, 0.75f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int realSize() {
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
        if (i == 0) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        } else {
            int[] iArr = this.key;
            int iMix = HashCommon.mix(i) & this.mask;
            int i3 = iArr[iMix];
            if (i3 != 0) {
                if (i3 == i) {
                    return false;
                }
                do {
                    iMix = (iMix + 1) & this.mask;
                    i2 = iArr[iMix];
                    if (i2 != 0) {
                    }
                } while (i2 != i);
                return false;
            }
            iArr[iMix] = i;
        }
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.maxFill) {
            rehash(HashCommon.arraySize(i4 + 2, this.f));
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
            i = i3;
        }
    }

    private boolean removeEntry(int i) {
        this.size--;
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
        int i2 = this.size - 1;
        this.size = i2;
        if (i > this.minN && i2 < this.maxFill / 4 && i > 16) {
            rehash(i / 2);
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
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    private final class SetIterator implements IntIterator {
        int c;
        int last;
        boolean mustReturnNull;
        int pos;
        IntArrayList wrapped;

        private SetIterator() {
            this.pos = IntOpenHashSet.this.n;
            this.last = -1;
            this.c = IntOpenHashSet.this.size;
            this.mustReturnNull = IntOpenHashSet.this.containsNull;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c != 0;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            int i;
            int i2;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.c--;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = IntOpenHashSet.this.n;
                return IntOpenHashSet.this.key[IntOpenHashSet.this.n];
            }
            int[] iArr = IntOpenHashSet.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    return this.wrapped.getInt((-i) - 1);
                }
                i2 = iArr[i];
            } while (i2 == 0);
            this.last = i;
            return i2;
        }

        private final void shiftKeys(int i) {
            int i2;
            int i3;
            int[] iArr = IntOpenHashSet.this.key;
            while (true) {
                int i4 = i + 1;
                int i5 = IntOpenHashSet.this.mask;
                while (true) {
                    i2 = i4 & i5;
                    i3 = iArr[i2];
                    if (i3 == 0) {
                        iArr[i] = 0;
                        return;
                    }
                    int iMix = HashCommon.mix(i3) & IntOpenHashSet.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i4 = i2 + 1;
                        i5 = IntOpenHashSet.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i4 = i2 + 1;
                        i5 = IntOpenHashSet.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(iArr[i2]);
                }
                iArr[i] = i3;
                i = i2;
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == IntOpenHashSet.this.n) {
                IntOpenHashSet.this.containsNull = false;
                IntOpenHashSet.this.key[IntOpenHashSet.this.n] = 0;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    IntOpenHashSet.this.remove(this.wrapped.getInt((-i2) - 1));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            IntOpenHashSet.this.size--;
            this.last = -1;
        }

        @Override // java.util.PrimitiveIterator
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            int[] iArr = IntOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = IntOpenHashSet.this.n;
                intConsumer.accept(iArr[IntOpenHashSet.this.n]);
                this.c--;
            }
            while (this.c != 0) {
                int i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    intConsumer.accept(this.wrapped.getInt((-i) - 1));
                    this.c--;
                } else {
                    int i2 = iArr[i];
                    if (i2 != 0) {
                        this.last = i;
                        intConsumer.accept(i2);
                        this.c--;
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public IntIterator iterator() {
        return new SetIterator();
    }

    private final class SetSpliterator implements IntSpliterator {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        SetSpliterator() {
            this.pos = 0;
            this.max = IntOpenHashSet.this.n;
            this.c = 0;
            this.mustReturnNull = IntOpenHashSet.this.containsNull;
            this.hasSplit = false;
        }

        SetSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = IntOpenHashSet.this.n;
            this.c = 0;
            boolean z3 = IntOpenHashSet.this.containsNull;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                intConsumer.accept(IntOpenHashSet.this.key[IntOpenHashSet.this.n]);
                return true;
            }
            int[] iArr = IntOpenHashSet.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return false;
                }
                int i2 = iArr[i];
                if (i2 != 0) {
                    this.c++;
                    this.pos = i + 1;
                    intConsumer.accept(i2);
                    return true;
                }
                this.pos = i + 1;
            }
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            int[] iArr = IntOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                intConsumer.accept(iArr[IntOpenHashSet.this.n]);
                this.c++;
            }
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return;
                }
                int i2 = iArr[i];
                if (i2 != 0) {
                    intConsumer.accept(i2);
                    this.c++;
                }
                this.pos++;
            }
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            if (this.hasSplit) {
                return 257;
            }
            return MediaError.DetailedErrorCode.DASH_NETWORK;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            if (this.hasSplit) {
                return Math.min(IntOpenHashSet.this.size - this.c, ((long) ((((double) IntOpenHashSet.this.realSize()) / ((double) IntOpenHashSet.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return IntOpenHashSet.this.size - this.c;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public SetSpliterator trySplit() {
            int i;
            int i2 = this.pos;
            int i3 = this.max;
            if (i2 >= i3 - 1 || (i = (i3 - i2) >> 1) <= 1) {
                return null;
            }
            int i4 = i2 + i;
            SetSpliterator setSpliterator = IntOpenHashSet.this.new SetSpliterator(i2, i4, this.mustReturnNull, true);
            this.pos = i4;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return setSpliterator;
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public IntSpliterator spliterator() {
        return new SetSpliterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer intConsumer) {
        if (this.containsNull) {
            intConsumer.accept(this.key[this.n]);
        }
        int[] iArr = this.key;
        int i = this.n;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return;
            }
            int i3 = iArr[i2];
            if (i3 != 0) {
                intConsumer.accept(i3);
            }
            i = i2;
        }
    }

    protected void rehash(int i) {
        int i2;
        int[] iArr = this.key;
        int i3 = i - 1;
        int[] iArr2 = new int[i + 1];
        int i4 = this.n;
        int iRealSize = realSize();
        while (true) {
            int i5 = iRealSize - 1;
            if (iRealSize != 0) {
                do {
                    i4--;
                    i2 = iArr[i4];
                } while (i2 == 0);
                int iMix = HashCommon.mix(i2) & i3;
                if (iArr2[iMix] != 0) {
                    do {
                        iMix = (iMix + 1) & i3;
                    } while (iArr2[iMix] != 0);
                }
                iArr2[iMix] = iArr[i4];
                iRealSize = i5;
            } else {
                this.n = i;
                this.mask = i3;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = iArr2;
                return;
            }
        }
    }

    public IntOpenHashSet clone() {
        try {
            IntOpenHashSet intOpenHashSet = (IntOpenHashSet) super.clone();
            intOpenHashSet.key = (int[]) this.key.clone();
            intOpenHashSet.containsNull = this.containsNull;
            return intOpenHashSet;
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
        IntIterator it = iterator();
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
        int i2 = this.size;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return;
            }
            int i4 = objectInputStream.readInt();
            if (i4 == 0) {
                iMix = this.n;
                this.containsNull = true;
            } else {
                iMix = HashCommon.mix(i4) & this.mask;
                if (iArr[iMix] != 0) {
                    do {
                        iMix = (iMix + 1) & this.mask;
                    } while (iArr[iMix] != 0);
                }
            }
            iArr[iMix] = i4;
            i2 = i3;
        }
    }
}
