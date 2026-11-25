package org.jetbrains.kotlin.com.intellij.util.lang.fastutil;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
public final class StrippedIntOpenHashSet {
    private transient boolean containsNull;
    private final float f;
    private transient int[] key;
    private transient int mask;
    private transient int maxFill;
    private final transient int minN;
    private transient int n;
    private int size;

    public StrippedIntOpenHashSet(int i, float f) {
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
        this.key = new int[this.n + 1];
    }

    public StrippedIntOpenHashSet() {
        this(16, 0.75f);
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    public boolean add(int i) {
        int i2;
        if (i == 0) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        } else {
            int[] iArr = this.key;
            int iMix = Hash.mix(i) & this.mask;
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
            rehash(Hash.arraySize(i4 + 2, this.f));
        }
        return true;
    }

    public int[] toArray() {
        int[] iArr = new int[this.size];
        SetIterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.nextInt();
            i++;
        }
        return iArr;
    }

    public final class SetIterator {
        int c;
        boolean mustReturnNull;
        int pos;

        public SetIterator() {
            this.pos = StrippedIntOpenHashSet.this.n;
            this.c = StrippedIntOpenHashSet.this.size;
            this.mustReturnNull = StrippedIntOpenHashSet.this.containsNull;
        }

        public boolean hasNext() {
            return this.c != 0;
        }

        public int nextInt() {
            int i;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.c--;
            if (!this.mustReturnNull) {
                int[] iArr = StrippedIntOpenHashSet.this.key;
                do {
                    int i2 = this.pos - 1;
                    this.pos = i2;
                    i = iArr[i2];
                } while (i == 0);
                return i;
            }
            this.mustReturnNull = false;
            return StrippedIntOpenHashSet.this.key[StrippedIntOpenHashSet.this.n];
        }
    }

    public SetIterator iterator() {
        return new SetIterator();
    }

    private void rehash(int i) {
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
                int iMix = Hash.mix(i2) & i3;
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
                this.maxFill = Hash.maxFill(i, this.f);
                this.key = iArr2;
                return;
            }
        }
    }

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
}
