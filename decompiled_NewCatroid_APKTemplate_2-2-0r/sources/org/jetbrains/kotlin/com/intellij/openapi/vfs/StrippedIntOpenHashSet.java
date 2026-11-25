package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
final class StrippedIntOpenHashSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private transient boolean containsNull;
    private final float f;
    private transient int[] key;
    private transient int mask;
    private transient int maxFill;
    private final transient int minN;
    private transient int n;
    private int size;

    private StrippedIntOpenHashSet(int i, float f) {
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

    StrippedIntOpenHashSet(int i) {
        this(i, 0.75f);
    }

    StrippedIntOpenHashSet() {
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

    private void shiftKeys(int i) {
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
                int iMix = Hash.mix(i2);
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

    public boolean remove(int i) {
        int i2;
        if (i == 0) {
            if (this.containsNull) {
                return removeNullEntry();
            }
            return false;
        }
        int[] iArr = this.key;
        int iMix = Hash.mix(i) & this.mask;
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

    public boolean contains(int i) {
        int i2;
        if (i == 0) {
            return this.containsNull;
        }
        int[] iArr = this.key;
        int iMix = Hash.mix(i) & this.mask;
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

    public int size() {
        return this.size;
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
            if (iRealSize == 0) {
                break;
            }
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
        }
        this.n = i;
        this.mask = i3;
        this.maxFill = Hash.maxFill(i, this.f);
        this.key = iArr2;
        for (int i6 : iArr) {
        }
    }
}
