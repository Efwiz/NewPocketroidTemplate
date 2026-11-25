package org.jetbrains.kotlin.com.intellij.util.lang.fastutil;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
public final class StrippedLongOpenHashSet {
    private transient boolean containsNull;
    private final float f;
    private transient long[] key;
    private transient int mask;
    private transient int maxFill;
    private final transient int minN;
    private transient int n;
    private int size;

    public StrippedLongOpenHashSet(int i, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        int iArraySize = Hash.arraySize(i, f);
        this.n = iArraySize;
        this.minN = iArraySize;
        this.mask = iArraySize - 1;
        this.maxFill = Hash.maxFill(iArraySize, f);
        this.key = new long[this.n + 1];
    }

    public StrippedLongOpenHashSet() {
        this(16, 0.75f);
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    public boolean add(long j) {
        long j2;
        if (j == 0) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        } else {
            long[] jArr = this.key;
            int iMix = ((int) Hash.mix(j)) & this.mask;
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
            rehash(Hash.arraySize(i + 2, this.f));
        }
        return true;
    }

    public int size() {
        return this.size;
    }

    public final class SetIterator {
        int c;
        boolean mustReturnNull;
        int pos;

        public SetIterator() {
            this.pos = StrippedLongOpenHashSet.this.n;
            this.c = StrippedLongOpenHashSet.this.size;
            this.mustReturnNull = StrippedLongOpenHashSet.this.containsNull;
        }

        public boolean hasNext() {
            return this.c != 0;
        }

        public long nextLong() {
            long j;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.c--;
            if (!this.mustReturnNull) {
                long[] jArr = StrippedLongOpenHashSet.this.key;
                do {
                    int i = this.pos - 1;
                    this.pos = i;
                    j = jArr[i];
                } while (j == 0);
                return j;
            }
            this.mustReturnNull = false;
            return StrippedLongOpenHashSet.this.key[StrippedLongOpenHashSet.this.n];
        }
    }

    public SetIterator iterator() {
        return new SetIterator();
    }

    private void rehash(int i) {
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
                int iMix = ((int) Hash.mix(j)) & i2;
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
                this.maxFill = Hash.maxFill(i, this.f);
                this.key = jArr2;
                return;
            }
        }
    }

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
            iLong2int += Hash.long2int(j);
            i++;
            iRealSize = i2;
        }
    }
}
