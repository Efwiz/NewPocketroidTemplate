package org.jetbrains.kotlin.com.intellij.psi.stubs;

/* JADX INFO: loaded from: classes3.dex */
final class StrippedIntOpenHashMap {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private transient boolean containsNull;
    private final float f;
    private transient int[] key;
    private transient int mask;
    private transient int maxFill;
    private final transient int minN;
    private transient int n;
    private transient int nullValue;
    private int size;
    private transient int[] value;

    private StrippedIntOpenHashMap(int i, float f) {
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
        int i2 = this.n;
        this.key = new int[i2 + 1];
        this.value = new int[i2 + 1];
    }

    StrippedIntOpenHashMap() {
        this(16, 0.75f);
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    public void put(int i, int i2) {
        int[] iArr = this.key;
        if (i == 0) {
            this.nullValue = i2;
            this.containsNull = true;
        } else {
            int iMix = Hash.mix(i) & this.mask;
            int i3 = iArr[iMix];
            if (i3 != 0) {
                if (i3 != i) {
                    while (true) {
                        iMix = (iMix + 1) & this.mask;
                        int i4 = iArr[iMix];
                        if (i4 == 0) {
                            break;
                        } else if (i4 == i) {
                            this.value[iMix] = i2;
                            return;
                        }
                    }
                } else {
                    this.value[iMix] = i2;
                    return;
                }
            }
            iArr[iMix] = i;
            this.value[iMix] = i2;
        }
        int i5 = this.size;
        this.size = i5 + 1;
        if (i5 >= this.maxFill) {
            rehash(Hash.arraySize(i5 + 2, this.f));
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    private void rehash(int i) {
        int i2;
        int[] iArr = this.key;
        int[] iArr2 = this.value;
        int i3 = i - 1;
        int i4 = i + 1;
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
        int i5 = this.n;
        int iRealSize = realSize();
        while (true) {
            int i6 = iRealSize - 1;
            if (iRealSize == 0) {
                break;
            }
            do {
                i5--;
                i2 = iArr[i5];
            } while (i2 == 0);
            int iMix = Hash.mix(i2) & i3;
            if (iArr3[iMix] != 0) {
                do {
                    iMix = (iMix + 1) & i3;
                } while (iArr3[iMix] != 0);
            }
            iArr3[iMix] = iArr[i5];
            iArr4[iMix] = iArr2[i5];
            iRealSize = i6;
        }
        this.n = i;
        this.mask = i3;
        this.maxFill = Hash.maxFill(i, this.f);
        this.key = iArr3;
        this.value = iArr4;
        for (int i7 = 0; i7 < iArr.length; i7++) {
            int i8 = iArr[i7];
            int i9 = iArr2[i7];
        }
    }

    public int get(int i, int i2) {
        int i3;
        if (i == 0) {
            return this.containsNull ? this.nullValue : i2;
        }
        int[] iArr = this.key;
        int[] iArr2 = this.value;
        int iMix = Hash.mix(i) & this.mask;
        int i4 = iArr2[iMix];
        if (i4 == 0) {
            return i2;
        }
        if (i == iArr[iMix]) {
            return i4;
        }
        do {
            int[] iArr3 = this.value;
            iMix = (iMix + 1) & this.mask;
            i3 = iArr3[iMix];
            if (i3 == 0) {
                return i2;
            }
        } while (i != iArr[iMix]);
        return i3;
    }
}
