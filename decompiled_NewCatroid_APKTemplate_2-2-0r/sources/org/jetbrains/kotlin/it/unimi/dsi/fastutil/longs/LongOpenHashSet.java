package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

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
public class LongOpenHashSet extends AbstractLongSet implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNull;
    protected final float f;
    protected transient long[] key;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;

    public LongOpenHashSet(int i, float f) {
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
        this.key = new long[this.n + 1];
    }

    public LongOpenHashSet(int i) {
        this(i, 0.75f);
    }

    public LongOpenHashSet() {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
    public boolean addAll(LongCollection longCollection) {
        if (this.f <= 0.5d) {
            ensureCapacity(longCollection.size());
        } else {
            tryCapacity(size() + longCollection.size());
        }
        return super.addAll(longCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Long> collection) {
        if (this.f <= 0.5d) {
            ensureCapacity(collection.size());
        } else {
            tryCapacity(size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean add(long j) {
        long j2;
        if (j == 0) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        } else {
            long[] jArr = this.key;
            int iMix = ((int) HashCommon.mix(j)) & this.mask;
            long j3 = jArr[iMix];
            if (j3 != 0) {
                if (j3 == j) {
                    return false;
                }
                do {
                    iMix = (iMix + 1) & this.mask;
                    j2 = jArr[iMix];
                    if (j2 != 0) {
                    }
                } while (j2 != j);
                return false;
            }
            jArr[iMix] = j;
        }
        int i = this.size;
        this.size = i + 1;
        if (i >= this.maxFill) {
            rehash(HashCommon.arraySize(i + 2, this.f));
        }
        return true;
    }

    protected final void shiftKeys(int i) {
        long j;
        long[] jArr = this.key;
        while (true) {
            int i2 = (i + 1) & this.mask;
            while (true) {
                j = jArr[i2];
                if (j == 0) {
                    jArr[i] = 0;
                    return;
                }
                int iMix = (int) HashCommon.mix(j);
                int i3 = this.mask;
                int i4 = iMix & i3;
                if (i > i2) {
                    if (i >= i4 && i4 > i2) {
                        break;
                    } else {
                        i2 = (i2 + 1) & i3;
                    }
                } else if (i >= i4 || i4 > i2) {
                    break;
                } else {
                    i2 = (i2 + 1) & i3;
                }
            }
            jArr[i] = j;
            i = i2;
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
        long[] jArr = this.key;
        int i = this.n;
        jArr[i] = 0;
        int i2 = this.size - 1;
        this.size = i2;
        if (i > this.minN && i2 < this.maxFill / 4 && i > 16) {
            rehash(i / 2);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSet
    public boolean remove(long j) {
        long j2;
        if (j == 0) {
            if (this.containsNull) {
                return removeNullEntry();
            }
            return false;
        }
        long[] jArr = this.key;
        int iMix = ((int) HashCommon.mix(j)) & this.mask;
        long j3 = jArr[iMix];
        if (j3 == 0) {
            return false;
        }
        if (j == j3) {
            return removeEntry(iMix);
        }
        do {
            iMix = (iMix + 1) & this.mask;
            j2 = jArr[iMix];
            if (j2 == 0) {
                return false;
            }
        } while (j != j2);
        return removeEntry(iMix);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean contains(long j) {
        long j2;
        if (j == 0) {
            return this.containsNull;
        }
        long[] jArr = this.key;
        int iMix = ((int) HashCommon.mix(j)) & this.mask;
        long j3 = jArr[iMix];
        if (j3 == 0) {
            return false;
        }
        if (j == j3) {
            return true;
        }
        do {
            iMix = (iMix + 1) & this.mask;
            j2 = jArr[iMix];
            if (j2 == 0) {
                return false;
            }
        } while (j != j2);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0L);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    private final class SetIterator implements LongIterator {
        int c;
        int last;
        boolean mustReturnNull;
        int pos;
        LongArrayList wrapped;

        private SetIterator() {
            this.pos = LongOpenHashSet.this.n;
            this.last = -1;
            this.c = LongOpenHashSet.this.size;
            this.mustReturnNull = LongOpenHashSet.this.containsNull;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c != 0;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator, java.util.PrimitiveIterator.OfLong
        public long nextLong() {
            int i;
            long j;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.c--;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = LongOpenHashSet.this.n;
                return LongOpenHashSet.this.key[LongOpenHashSet.this.n];
            }
            long[] jArr = LongOpenHashSet.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    return this.wrapped.getLong((-i) - 1);
                }
                j = jArr[i];
            } while (j == 0);
            this.last = i;
            return j;
        }

        private final void shiftKeys(int i) {
            int i2;
            long j;
            long[] jArr = LongOpenHashSet.this.key;
            while (true) {
                int i3 = i + 1;
                int i4 = LongOpenHashSet.this.mask;
                while (true) {
                    i2 = i3 & i4;
                    j = jArr[i2];
                    if (j == 0) {
                        jArr[i] = 0;
                        return;
                    }
                    int iMix = ((int) HashCommon.mix(j)) & LongOpenHashSet.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = LongOpenHashSet.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = LongOpenHashSet.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new LongArrayList(2);
                    }
                    this.wrapped.add(jArr[i2]);
                }
                jArr[i] = j;
                i = i2;
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == LongOpenHashSet.this.n) {
                LongOpenHashSet.this.containsNull = false;
                LongOpenHashSet.this.key[LongOpenHashSet.this.n] = 0;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    LongOpenHashSet.this.remove(this.wrapped.getLong((-i2) - 1));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            LongOpenHashSet.this.size--;
            this.last = -1;
        }

        @Override // java.util.PrimitiveIterator
        public void forEachRemaining(java.util.function.LongConsumer longConsumer) {
            long[] jArr = LongOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = LongOpenHashSet.this.n;
                longConsumer.accept(jArr[LongOpenHashSet.this.n]);
                this.c--;
            }
            while (this.c != 0) {
                int i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    longConsumer.accept(this.wrapped.getLong((-i) - 1));
                    this.c--;
                } else {
                    long j = jArr[i];
                    if (j != 0) {
                        this.last = i;
                        longConsumer.accept(j);
                        this.c--;
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    public LongIterator iterator() {
        return new SetIterator();
    }

    private final class SetSpliterator implements LongSpliterator {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        SetSpliterator() {
            this.pos = 0;
            this.max = LongOpenHashSet.this.n;
            this.c = 0;
            this.mustReturnNull = LongOpenHashSet.this.containsNull;
            this.hasSplit = false;
        }

        SetSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = LongOpenHashSet.this.n;
            this.c = 0;
            boolean z3 = LongOpenHashSet.this.containsNull;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.LongConsumer longConsumer) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                longConsumer.accept(LongOpenHashSet.this.key[LongOpenHashSet.this.n]);
                return true;
            }
            long[] jArr = LongOpenHashSet.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return false;
                }
                long j = jArr[i];
                if (j != 0) {
                    this.c++;
                    this.pos = i + 1;
                    longConsumer.accept(j);
                    return true;
                }
                this.pos = i + 1;
            }
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.LongConsumer longConsumer) {
            long[] jArr = LongOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                longConsumer.accept(jArr[LongOpenHashSet.this.n]);
                this.c++;
            }
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return;
                }
                long j = jArr[i];
                if (j != 0) {
                    longConsumer.accept(j);
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
                return Math.min(LongOpenHashSet.this.size - this.c, ((long) ((((double) LongOpenHashSet.this.realSize()) / ((double) LongOpenHashSet.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return LongOpenHashSet.this.size - this.c;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator, java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public SetSpliterator trySplit() {
            int i;
            int i2 = this.pos;
            int i3 = this.max;
            if (i2 >= i3 - 1 || (i = (i3 - i2) >> 1) <= 1) {
                return null;
            }
            int i4 = i2 + i;
            SetSpliterator setSpliterator = LongOpenHashSet.this.new SetSpliterator(i2, i4, this.mustReturnNull, true);
            this.pos = i4;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return setSpliterator;
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    public LongSpliterator spliterator() {
        return new SetSpliterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    public void forEach(java.util.function.LongConsumer longConsumer) {
        if (this.containsNull) {
            longConsumer.accept(this.key[this.n]);
        }
        long[] jArr = this.key;
        int i = this.n;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return;
            }
            long j = jArr[i2];
            if (j != 0) {
                longConsumer.accept(j);
            }
            i = i2;
        }
    }

    protected void rehash(int i) {
        long j;
        long[] jArr = this.key;
        int i2 = i - 1;
        long[] jArr2 = new long[i + 1];
        int i3 = this.n;
        int iRealSize = realSize();
        while (true) {
            int i4 = iRealSize - 1;
            if (iRealSize != 0) {
                do {
                    i3--;
                    j = jArr[i3];
                } while (j == 0);
                int iMix = ((int) HashCommon.mix(j)) & i2;
                if (jArr2[iMix] != 0) {
                    do {
                        iMix = (iMix + 1) & i2;
                    } while (jArr2[iMix] != 0);
                }
                jArr2[iMix] = jArr[i3];
                iRealSize = i4;
            } else {
                this.n = i;
                this.mask = i2;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = jArr2;
                return;
            }
        }
    }

    public LongOpenHashSet clone() {
        try {
            LongOpenHashSet longOpenHashSet = (LongOpenHashSet) super.clone();
            longOpenHashSet.key = (long[]) this.key.clone();
            longOpenHashSet.containsNull = this.containsNull;
            return longOpenHashSet;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongSet, java.util.Collection, java.util.Set
    public int hashCode() {
        long j;
        int iRealSize = realSize();
        int iLong2int = 0;
        int i = 0;
        while (true) {
            int i2 = iRealSize - 1;
            if (iRealSize == 0) {
                return iLong2int;
            }
            while (true) {
                j = this.key[i];
                if (j == 0) {
                    i++;
                }
            }
            iLong2int += HashCommon.long2int(j);
            i++;
            iRealSize = i2;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        LongIterator it = iterator();
        objectOutputStream.defaultWriteObject();
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return;
            }
            objectOutputStream.writeLong(it.nextLong());
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
        long[] jArr = new long[i + 1];
        this.key = jArr;
        int i2 = this.size;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return;
            }
            long j = objectInputStream.readLong();
            if (j == 0) {
                iMix = this.n;
                this.containsNull = true;
            } else {
                iMix = ((int) HashCommon.mix(j)) & this.mask;
                if (jArr[iMix] != 0) {
                    do {
                        iMix = (iMix + 1) & this.mask;
                    } while (jArr[iMix] != 0);
                }
            }
            jArr[iMix] = j;
            i2 = i3;
        }
    }
}
