package com.badlogic.gdx.utils;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;
import java.util.Comparator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/TimSort.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
class TimSort<T> {
    private static final boolean DEBUG = false;
    private static final int INITIAL_TMP_STORAGE_LENGTH = 256;
    private static final int MIN_GALLOP = 7;
    private static final int MIN_MERGE = 32;
    private T[] a;
    private Comparator<? super T> c;
    private int minGallop;
    private final int[] runBase;
    private final int[] runLen;
    private int stackSize;
    private T[] tmp;
    private int tmpCount;

    private static int minRunLength(int i) {
        int i2 = 0;
        while (i >= 32) {
            i2 |= i & 1;
            i >>= 1;
        }
        return i + i2;
    }

    TimSort() {
        this.minGallop = 7;
        this.stackSize = 0;
        this.tmp = (T[]) new Object[256];
        this.runBase = new int[40];
        this.runLen = new int[40];
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void doSort(T[] tArr, Comparator<T> comparator, int i, int i2) {
        this.stackSize = 0;
        rangeCheck(tArr.length, i, i2);
        int i3 = i2 - i;
        if (i3 < 2) {
            return;
        }
        if (i3 < 32) {
            binarySort(tArr, i, i2, countRunAndMakeAscending(tArr, i, i2, comparator) + i, comparator);
            return;
        }
        this.a = tArr;
        this.c = comparator;
        this.tmpCount = 0;
        int iMinRunLength = minRunLength(i3);
        do {
            int iCountRunAndMakeAscending = countRunAndMakeAscending(tArr, i, i2, comparator);
            if (iCountRunAndMakeAscending < iMinRunLength) {
                int i4 = i3 <= iMinRunLength ? i3 : iMinRunLength;
                binarySort(tArr, i, i + i4, iCountRunAndMakeAscending + i, comparator);
                iCountRunAndMakeAscending = i4;
            }
            pushRun(i, iCountRunAndMakeAscending);
            mergeCollapse();
            i += iCountRunAndMakeAscending;
            i3 -= iCountRunAndMakeAscending;
        } while (i3 != 0);
        mergeForceCollapse();
        this.a = null;
        this.c = null;
        T[] tArr2 = this.tmp;
        int i5 = this.tmpCount;
        for (int i6 = 0; i6 < i5; i6++) {
            tArr2[i6] = null;
        }
    }

    private TimSort(T[] tArr, Comparator<? super T> comparator) {
        this.minGallop = 7;
        this.stackSize = 0;
        this.a = tArr;
        this.c = comparator;
        int length = tArr.length;
        this.tmp = (T[]) new Object[length < 512 ? length >>> 1 : 256];
        int i = length < 120 ? 5 : length < 1542 ? 10 : length < 119151 ? 19 : 40;
        this.runBase = new int[i];
        this.runLen = new int[i];
    }

    static <T> void sort(T[] tArr, Comparator<? super T> comparator) {
        sort(tArr, 0, tArr.length, comparator);
    }

    static <T> void sort(T[] tArr, int i, int i2, Comparator<? super T> comparator) {
        if (comparator == null) {
            Arrays.sort(tArr, i, i2);
            return;
        }
        rangeCheck(tArr.length, i, i2);
        int i3 = i2 - i;
        if (i3 < 2) {
            return;
        }
        if (i3 < 32) {
            binarySort(tArr, i, i2, countRunAndMakeAscending(tArr, i, i2, comparator) + i, comparator);
            return;
        }
        TimSort timSort = new TimSort(tArr, comparator);
        int iMinRunLength = minRunLength(i3);
        do {
            int iCountRunAndMakeAscending = countRunAndMakeAscending(tArr, i, i2, comparator);
            if (iCountRunAndMakeAscending < iMinRunLength) {
                int i4 = i3 <= iMinRunLength ? i3 : iMinRunLength;
                binarySort(tArr, i, i + i4, iCountRunAndMakeAscending + i, comparator);
                iCountRunAndMakeAscending = i4;
            }
            timSort.pushRun(i, iCountRunAndMakeAscending);
            timSort.mergeCollapse();
            i += iCountRunAndMakeAscending;
            i3 -= iCountRunAndMakeAscending;
        } while (i3 != 0);
        timSort.mergeForceCollapse();
    }

    private static <T> void binarySort(T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        if (i3 == i) {
            i3++;
        }
        while (i3 < i2) {
            T t = tArr[i3];
            int i4 = i;
            int i5 = i3;
            while (i4 < i5) {
                int i6 = (i4 + i5) >>> 1;
                if (comparator.compare(t, tArr[i6]) < 0) {
                    i5 = i6;
                } else {
                    i4 = i6 + 1;
                }
            }
            int i7 = i3 - i4;
            if (i7 == 1) {
                tArr[i4 + 1] = tArr[i4];
            } else if (i7 == 2) {
                tArr[i4 + 2] = tArr[i4 + 1];
                tArr[i4 + 1] = tArr[i4];
            } else {
                System.arraycopy(tArr, i4, tArr, i4 + 1, i7);
            }
            tArr[i4] = t;
            i3++;
        }
    }

    private static <T> int countRunAndMakeAscending(T[] tArr, int i, int i2, Comparator<? super T> comparator) {
        int i3 = i + 1;
        if (i3 == i2) {
            return 1;
        }
        int i4 = i + 2;
        if (comparator.compare(tArr[i3], tArr[i]) < 0) {
            while (i4 < i2 && comparator.compare(tArr[i4], tArr[i4 - 1]) < 0) {
                i4++;
            }
            reverseRange(tArr, i, i4);
        } else {
            while (i4 < i2 && comparator.compare(tArr[i4], tArr[i4 - 1]) >= 0) {
                i4++;
            }
        }
        return i4 - i;
    }

    private static void reverseRange(Object[] objArr, int i, int i2) {
        int i3 = i2 - 1;
        while (i < i3) {
            Object obj = objArr[i];
            objArr[i] = objArr[i3];
            objArr[i3] = obj;
            i3--;
            i++;
        }
    }

    private void pushRun(int i, int i2) {
        int[] iArr = this.runBase;
        int i3 = this.stackSize;
        iArr[i3] = i;
        this.runLen[i3] = i2;
        this.stackSize = i3 + 1;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001b  */
    /* JADX WARN: Code duplicated, block: B:12:0x002a  */
    /* JADX WARN: Code duplicated, block: B:14:0x0036  */
    /* JADX WARN: Code duplicated, block: B:21:0x0048 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:22:0x0044 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:24:0x0044 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:8:0x0018  */
    private void mergeCollapse() {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        while (true) {
            int i = this.stackSize;
            if (i <= 1) {
                return;
            }
            int i2 = i - 2;
            if (i2 >= 1) {
                int[] iArr4 = this.runLen;
                if (iArr4[i - 3] <= iArr4[i2] + iArr4[i - 1]) {
                    iArr3 = this.runLen;
                    if (iArr3[i - 3] < iArr3[i - 1]) {
                        i2 = i - 3;
                    }
                } else {
                    if (i2 >= 2) {
                        iArr2 = this.runLen;
                        if (iArr2[i - 4] <= iArr2[i2] + iArr2[i - 3]) {
                            iArr3 = this.runLen;
                            if (iArr3[i - 3] < iArr3[i - 1]) {
                                i2 = i - 3;
                            }
                        }
                    }
                    iArr = this.runLen;
                    if (iArr[i2] > iArr[i - 1]) {
                        return;
                    }
                }
            } else {
                if (i2 >= 2) {
                    iArr2 = this.runLen;
                    if (iArr2[i - 4] <= iArr2[i2] + iArr2[i - 3]) {
                        iArr3 = this.runLen;
                        if (iArr3[i - 3] < iArr3[i - 1]) {
                            i2 = i - 3;
                        }
                    }
                }
                iArr = this.runLen;
                if (iArr[i2] > iArr[i - 1]) {
                    return;
                }
            }
            mergeAt(i2);
        }
    }

    private void mergeForceCollapse() {
        while (true) {
            int i = this.stackSize;
            if (i <= 1) {
                return;
            }
            int i2 = i - 2;
            if (i2 > 0) {
                int[] iArr = this.runLen;
                if (iArr[i - 3] < iArr[i - 1]) {
                    i2 = i - 3;
                }
            }
            mergeAt(i2);
        }
    }

    private void mergeAt(int i) {
        int[] iArr = this.runBase;
        int i2 = iArr[i];
        int[] iArr2 = this.runLen;
        int i3 = iArr2[i];
        int i4 = i + 1;
        int i5 = iArr[i4];
        int i6 = iArr2[i4];
        iArr2[i] = i3 + i6;
        int i7 = this.stackSize;
        if (i == i7 - 3) {
            int i8 = i + 2;
            iArr[i4] = iArr[i8];
            iArr2[i4] = iArr2[i8];
        }
        this.stackSize = i7 - 1;
        T[] tArr = this.a;
        int iGallopRight = gallopRight(tArr[i5], tArr, i2, i3, 0, this.c);
        int i9 = i2 + iGallopRight;
        int i10 = i3 - iGallopRight;
        if (i10 == 0) {
            return;
        }
        T[] tArr2 = this.a;
        int iGallopLeft = gallopLeft(tArr2[(i9 + i10) - 1], tArr2, i5, i6, i6 - 1, this.c);
        if (iGallopLeft == 0) {
            return;
        }
        if (i10 <= iGallopLeft) {
            mergeLo(i9, i10, i5, iGallopLeft);
        } else {
            mergeHi(i9, i10, i5, iGallopLeft);
        }
    }

    private static <T> int gallopLeft(T t, T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        int i4;
        int i5;
        int i6 = i + i3;
        if (comparator.compare(t, tArr[i6]) > 0) {
            int i7 = i2 - i3;
            int i8 = 0;
            int i9 = 1;
            while (i9 < i7 && comparator.compare(t, tArr[i6 + i9]) > 0) {
                int i10 = (i9 << 1) + 1;
                if (i10 <= 0) {
                    i8 = i9;
                    i9 = i7;
                } else {
                    int i11 = i9;
                    i9 = i10;
                    i8 = i11;
                }
            }
            if (i9 <= i7) {
                i7 = i9;
            }
            i4 = i8 + i3;
            i5 = i7 + i3;
        } else {
            int i12 = i3 + 1;
            int i13 = 0;
            int i14 = 1;
            while (i14 < i12 && comparator.compare(t, tArr[i6 - i14]) <= 0) {
                int i15 = (i14 << 1) + 1;
                if (i15 <= 0) {
                    i13 = i14;
                    i14 = i12;
                } else {
                    int i16 = i14;
                    i14 = i15;
                    i13 = i16;
                }
            }
            if (i14 <= i12) {
                i12 = i14;
            }
            int i17 = i3 - i12;
            int i18 = i3 - i13;
            i4 = i17;
            i5 = i18;
        }
        int i19 = i4 + 1;
        while (i19 < i5) {
            int i20 = ((i5 - i19) >>> 1) + i19;
            if (comparator.compare(t, tArr[i + i20]) > 0) {
                i19 = i20 + 1;
            } else {
                i5 = i20;
            }
        }
        return i5;
    }

    private static <T> int gallopRight(T t, T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        int i4;
        int i5;
        int i6 = i + i3;
        if (comparator.compare(t, tArr[i6]) < 0) {
            int i7 = i3 + 1;
            int i8 = 0;
            int i9 = 1;
            while (i9 < i7 && comparator.compare(t, tArr[i6 - i9]) < 0) {
                int i10 = (i9 << 1) + 1;
                if (i10 <= 0) {
                    i8 = i9;
                    i9 = i7;
                } else {
                    int i11 = i9;
                    i9 = i10;
                    i8 = i11;
                }
            }
            if (i9 <= i7) {
                i7 = i9;
            }
            i5 = i3 - i7;
            i4 = i3 - i8;
        } else {
            int i12 = i2 - i3;
            int i13 = 0;
            int i14 = 1;
            while (i14 < i12 && comparator.compare(t, tArr[i6 + i14]) >= 0) {
                int i15 = (i14 << 1) + 1;
                if (i15 <= 0) {
                    i13 = i14;
                    i14 = i12;
                } else {
                    int i16 = i14;
                    i14 = i15;
                    i13 = i16;
                }
            }
            if (i14 <= i12) {
                i12 = i14;
            }
            int i17 = i13 + i3;
            i4 = i3 + i12;
            i5 = i17;
        }
        int i18 = i5 + 1;
        while (i18 < i4) {
            int i19 = ((i4 - i18) >>> 1) + i18;
            if (comparator.compare(t, tArr[i + i19]) < 0) {
                i4 = i19;
            } else {
                i18 = i19 + 1;
            }
        }
        return i4;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:36:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:39:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:40:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:54:0x0108  */
    /* JADX WARN: Code duplicated, block: B:56:0x010d  */
    /* JADX WARN: Code duplicated, block: B:57:0x010f  */
    /* JADX WARN: Code duplicated, block: B:59:0x0112  */
    /* JADX WARN: Code duplicated, block: B:60:0x0114  */
    /* JADX WARN: Code duplicated, block: B:66:0x0126 A[LOOP:2: B:25:0x0071->B:66:0x0126, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:67:0x012b A[LOOP:1: B:12:0x0033->B:67:0x012b, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:71:0x00a6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x00ca A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:0x00e4 A[SYNTHETIC] */
    private void mergeLo(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int iGallopLeft;
        int i8;
        int i9;
        boolean z;
        boolean z2;
        int i10;
        int i11;
        int i12 = i2;
        T[] tArr = this.a;
        T[] tArrEnsureCapacity = ensureCapacity(i12);
        int i13 = 0;
        System.arraycopy(tArr, i, tArrEnsureCapacity, 0, i12);
        int i14 = i + 1;
        int i15 = i3 + 1;
        tArr[i] = tArr[i3];
        int i16 = i4 - 1;
        if (i16 == 0) {
            System.arraycopy(tArrEnsureCapacity, 0, tArr, i14, i12);
            return;
        }
        int i17 = 1;
        if (i12 == 1) {
            System.arraycopy(tArr, i15, tArr, i14, i16);
            tArr[i14 + i16] = tArrEnsureCapacity[0];
            return;
        }
        Comparator<? super T> comparator = this.c;
        int i18 = this.minGallop;
        int i19 = 0;
        loop0: while (true) {
            int i20 = i13;
            int i21 = i20;
            while (true) {
                if (comparator.compare(tArr[i15], tArrEnsureCapacity[i19]) < 0) {
                    i6 = i14 + 1;
                    int i22 = i15 + 1;
                    tArr[i14] = tArr[i15];
                    i21 += i17;
                    i16--;
                    if (i16 == 0) {
                        i5 = i17;
                        i15 = i22;
                        break loop0;
                    }
                    i14 = i6;
                    i15 = i22;
                    i20 = i13;
                    if ((i20 | i21) >= i18) {
                        break;
                    } else {
                        i13 = 0;
                    }
                } else {
                    int i23 = i14 + 1;
                    int i24 = i19 + 1;
                    tArr[i14] = tArrEnsureCapacity[i19];
                    i20 += i17;
                    i12--;
                    if (i12 == i17) {
                        i5 = i17;
                        i6 = i23;
                        i19 = i24;
                        break loop0;
                    } else {
                        i14 = i23;
                        i19 = i24;
                        i21 = i13;
                        if ((i20 | i21) >= i18) {
                            break;
                        } else {
                            i13 = 0;
                        }
                    }
                }
            }
            int i25 = i16;
            int i26 = i12;
            int i27 = i14;
            int i28 = i15;
            int i29 = i18;
            while (true) {
                int i30 = i19;
                int iGallopRight = gallopRight(tArr[i28], tArrEnsureCapacity, i19, i26, 0, comparator);
                if (iGallopRight != 0) {
                    System.arraycopy(tArrEnsureCapacity, i30, tArr, i27, iGallopRight);
                    int i31 = i27 + iGallopRight;
                    int i32 = i30 + iGallopRight;
                    int i33 = i26 - iGallopRight;
                    if (i33 <= i17) {
                        i19 = i32;
                        i12 = i33;
                        i5 = i17;
                        i15 = i28;
                        i18 = i29;
                        int i34 = i25;
                        i6 = i31;
                        i16 = i34;
                        break loop0;
                    }
                    i27 = i31;
                    i30 = i32;
                    i26 = i33;
                    i7 = i27 + 1;
                    i15 = i28 + 1;
                    tArr[i27] = tArr[i28];
                    i25--;
                    if (i25 == 0) {
                        i19 = i30;
                        i5 = i17;
                        i16 = i25;
                        i12 = i26;
                        i6 = i7;
                        i18 = i29;
                        break loop0;
                    }
                    i28 = i15;
                    iGallopLeft = gallopLeft(tArrEnsureCapacity[i30], tArr, i15, i25, 0, comparator);
                    if (iGallopLeft != 0) {
                        System.arraycopy(tArr, i28, tArr, i7, iGallopLeft);
                        i10 = i7 + iGallopLeft;
                        i15 = i28 + iGallopLeft;
                        i11 = i25 - iGallopLeft;
                        if (i11 == 0) {
                            i6 = i10;
                            i16 = i11;
                            i19 = i30;
                            i12 = i26;
                            i18 = i29;
                            i5 = 1;
                            break loop0;
                        }
                        i8 = i10;
                        i25 = i11;
                        i28 = i15;
                    } else {
                        i8 = i7;
                    }
                    i9 = i8 + 1;
                    i19 = i30 + 1;
                    tArr[i8] = tArrEnsureCapacity[i30];
                    i26--;
                    i5 = 1;
                    if (i26 == 1) {
                        i16 = i25;
                        i15 = i28;
                        i18 = i29;
                        i6 = i9;
                        i12 = i26;
                        break loop0;
                    }
                    i29--;
                    if (iGallopRight >= 7) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (iGallopLeft >= 7) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (!z2 && !z) {
                        break;
                    }
                    i27 = i9;
                    i17 = 1;
                } else {
                    i7 = i27 + 1;
                    i15 = i28 + 1;
                    tArr[i27] = tArr[i28];
                    i25--;
                    if (i25 == 0) {
                        i19 = i30;
                        i5 = i17;
                        i16 = i25;
                        i12 = i26;
                        i6 = i7;
                        i18 = i29;
                        break loop0;
                    }
                    i28 = i15;
                    iGallopLeft = gallopLeft(tArrEnsureCapacity[i30], tArr, i15, i25, 0, comparator);
                    if (iGallopLeft != 0) {
                        System.arraycopy(tArr, i28, tArr, i7, iGallopLeft);
                        i10 = i7 + iGallopLeft;
                        i15 = i28 + iGallopLeft;
                        i11 = i25 - iGallopLeft;
                        if (i11 == 0) {
                            i6 = i10;
                            i16 = i11;
                            i19 = i30;
                            i12 = i26;
                            i18 = i29;
                            i5 = 1;
                            break loop0;
                        }
                        i8 = i10;
                        i25 = i11;
                        i28 = i15;
                    } else {
                        i8 = i7;
                    }
                    i9 = i8 + 1;
                    i19 = i30 + 1;
                    tArr[i8] = tArrEnsureCapacity[i30];
                    i26--;
                    i5 = 1;
                    if (i26 == 1) {
                        i16 = i25;
                        i15 = i28;
                        i18 = i29;
                        i6 = i9;
                        i12 = i26;
                        break loop0;
                    }
                    i29--;
                    if (iGallopRight >= 7) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (iGallopLeft >= 7) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (!z2 && !z) {
                        break;
                    }
                    i27 = i9;
                    i17 = 1;
                }
            }
            if (i29 < 0) {
                i29 = 0;
            }
            i18 = i29 + 2;
            i14 = i9;
            i17 = 1;
            i16 = i25;
            i12 = i26;
            i15 = i28;
            i13 = 0;
        }
        if (i18 < i5) {
            i18 = i5;
        }
        this.minGallop = i18;
        if (i12 == i5) {
            System.arraycopy(tArr, i15, tArr, i6, i16);
            tArr[i6 + i16] = tArrEnsureCapacity[i19];
        } else {
            if (i12 == 0) {
                throw new IllegalArgumentException("Comparison method violates its general contract!");
            }
            System.arraycopy(tArrEnsureCapacity, i19, tArr, i6, i12);
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00bc  */
    /* JADX WARN: Code duplicated, block: B:37:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:40:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:41:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:55:0x0124  */
    /* JADX WARN: Code duplicated, block: B:57:0x0129  */
    /* JADX WARN: Code duplicated, block: B:58:0x012b  */
    /* JADX WARN: Code duplicated, block: B:60:0x012e  */
    /* JADX WARN: Code duplicated, block: B:61:0x0130  */
    /* JADX WARN: Code duplicated, block: B:65:0x0136  */
    /* JADX WARN: Code duplicated, block: B:67:0x0141 A[LOOP:2: B:25:0x007e->B:67:0x0141, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:71:0x00b2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x00dc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:0x00f5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:75:0x0138 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x0134 A[SYNTHETIC] */
    private void mergeHi(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int iGallopLeft;
        int i9;
        boolean z;
        boolean z2;
        int i10;
        int i11;
        int i12 = i4;
        T[] tArr = this.a;
        T[] tArrEnsureCapacity = ensureCapacity(i12);
        System.arraycopy(tArr, i3, tArrEnsureCapacity, 0, i12);
        int i13 = i + i2;
        int i14 = i13 - 1;
        int i15 = i12 - 1;
        int i16 = i3 + i12;
        int i17 = i16 - 1;
        int i18 = i16 - 2;
        int i19 = i13 - 2;
        tArr[i17] = tArr[i14];
        int i20 = i2 - 1;
        if (i20 == 0) {
            System.arraycopy(tArrEnsureCapacity, 0, tArr, i18 - i15, i12);
            return;
        }
        if (i12 == 1) {
            int i21 = i18 - i20;
            System.arraycopy(tArr, (i19 - i20) + 1, tArr, i21 + 1, i20);
            tArr[i21] = tArrEnsureCapacity[i15];
            return;
        }
        Comparator<? super T> comparator = this.c;
        int i22 = this.minGallop;
        loop0: while (true) {
            int i23 = 0;
            int i24 = 0;
            do {
                if (comparator.compare(tArrEnsureCapacity[i15], tArr[i19]) < 0) {
                    i5 = i18 - 1;
                    int i25 = i19 - 1;
                    tArr[i18] = tArr[i19];
                    i24++;
                    i20--;
                    if (i20 == 0) {
                        i19 = i25;
                        break loop0;
                    } else {
                        i18 = i5;
                        i19 = i25;
                        i23 = 0;
                    }
                } else {
                    int i26 = i18 - 1;
                    int i27 = i15 - 1;
                    tArr[i18] = tArrEnsureCapacity[i15];
                    i23++;
                    i12--;
                    if (i12 == 1) {
                        i5 = i26;
                        i15 = i27;
                        break loop0;
                    } else {
                        i18 = i26;
                        i15 = i27;
                        i24 = 0;
                    }
                }
            } while ((i24 | i23) < i22);
            i5 = i18;
            int i28 = i12;
            int i29 = i19;
            int i30 = i20;
            int i31 = i15;
            int i32 = i22;
            while (true) {
                int iGallopRight = i30 - gallopRight(tArrEnsureCapacity[i31], tArr, i, i30, i30 - 1, comparator);
                if (iGallopRight != 0) {
                    i5 -= iGallopRight;
                    i19 = i29 - iGallopRight;
                    i20 = i30 - iGallopRight;
                    System.arraycopy(tArr, i19 + 1, tArr, i5 + 1, iGallopRight);
                    if (i20 == 0) {
                        i12 = i28;
                        i15 = i31;
                    } else {
                        i29 = i19;
                        i30 = i20;
                        i6 = i5 - 1;
                        i7 = i31 - 1;
                        tArr[i5] = tArrEnsureCapacity[i31];
                        i8 = i28 - 1;
                        if (i8 == 1) {
                            i12 = i8;
                            i19 = i29;
                            i20 = i30;
                            i22 = i32;
                            i5 = i6;
                            i15 = i7;
                            break loop0;
                        }
                        iGallopLeft = i8 - gallopLeft(tArr[i29], tArrEnsureCapacity, 0, i8, i28 - 2, comparator);
                        if (iGallopLeft != 0) {
                            i10 = i6 - iGallopLeft;
                            i15 = i7 - iGallopLeft;
                            i11 = i8 - iGallopLeft;
                            System.arraycopy(tArrEnsureCapacity, i15 + 1, tArr, i10 + 1, iGallopLeft);
                            if (i11 <= 1) {
                                i5 = i10;
                                i12 = i11;
                                i19 = i29;
                                i20 = i30;
                            } else {
                                i6 = i10;
                                i8 = i11;
                                i31 = i15;
                            }
                        } else {
                            i31 = i7;
                        }
                        i9 = i6 - 1;
                        i19 = i29 - 1;
                        tArr[i6] = tArr[i29];
                        i30--;
                        if (i30 == 0) {
                            i20 = i30;
                            i15 = i31;
                            i22 = i32;
                            int i33 = i8;
                            i5 = i9;
                            i12 = i33;
                            break loop0;
                        }
                        i32--;
                        if (iGallopRight >= 7) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (iGallopLeft >= 7) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z2 && !z) {
                            if (i32 < 0) {
                                i32 = 0;
                            }
                            i22 = i32 + 2;
                            i18 = i9;
                            i12 = i8;
                            i20 = i30;
                            i15 = i31;
                        } else {
                            i29 = i19;
                            i28 = i8;
                            i5 = i9;
                        }
                    }
                    i22 = i32;
                    break loop0;
                }
                i6 = i5 - 1;
                i7 = i31 - 1;
                tArr[i5] = tArrEnsureCapacity[i31];
                i8 = i28 - 1;
                if (i8 == 1) {
                    i12 = i8;
                    i19 = i29;
                    i20 = i30;
                    i22 = i32;
                    i5 = i6;
                    i15 = i7;
                    break loop0;
                }
                iGallopLeft = i8 - gallopLeft(tArr[i29], tArrEnsureCapacity, 0, i8, i28 - 2, comparator);
                if (iGallopLeft != 0) {
                    i10 = i6 - iGallopLeft;
                    i15 = i7 - iGallopLeft;
                    i11 = i8 - iGallopLeft;
                    System.arraycopy(tArrEnsureCapacity, i15 + 1, tArr, i10 + 1, iGallopLeft);
                    if (i11 <= 1) {
                        i5 = i10;
                        i12 = i11;
                        i19 = i29;
                        i20 = i30;
                        i22 = i32;
                        break loop0;
                    }
                    i6 = i10;
                    i8 = i11;
                    i31 = i15;
                } else {
                    i31 = i7;
                }
                i9 = i6 - 1;
                i19 = i29 - 1;
                tArr[i6] = tArr[i29];
                i30--;
                if (i30 == 0) {
                    i20 = i30;
                    i15 = i31;
                    i22 = i32;
                    int i34 = i8;
                    i5 = i9;
                    i12 = i34;
                    break loop0;
                }
                i32--;
                if (iGallopRight >= 7) {
                    z = true;
                } else {
                    z = false;
                }
                if (iGallopLeft >= 7) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2 && !z) {
                    if (i32 < 0) {
                        i32 = 0;
                    }
                    i22 = i32 + 2;
                    i18 = i9;
                    i12 = i8;
                    i20 = i30;
                    i15 = i31;
                } else {
                    i29 = i19;
                    i28 = i8;
                    i5 = i9;
                }
            }
        }
        if (i22 < 1) {
            i22 = 1;
        }
        this.minGallop = i22;
        if (i12 == 1) {
            int i35 = i5 - i20;
            System.arraycopy(tArr, (i19 - i20) + 1, tArr, i35 + 1, i20);
            tArr[i35] = tArrEnsureCapacity[i15];
        } else {
            if (i12 == 0) {
                throw new IllegalArgumentException("Comparison method violates its general contract!");
            }
            System.arraycopy(tArrEnsureCapacity, 0, tArr, i5 - (i12 - 1), i12);
        }
    }

    private T[] ensureCapacity(int i) {
        this.tmpCount = Math.max(this.tmpCount, i);
        if (this.tmp.length < i) {
            int i2 = (i >> 1) | i;
            int i3 = i2 | (i2 >> 2);
            int i4 = i3 | (i3 >> 4);
            int i5 = i4 | (i4 >> 8);
            int i6 = (i5 | (i5 >> 16)) + 1;
            if (i6 >= 0) {
                i = Math.min(i6, this.a.length >>> 1);
            }
            this.tmp = (T[]) new Object[i];
        }
        return this.tmp;
    }

    private static void rangeCheck(int i, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex(" + i2 + ") > toIndex(" + i3 + Constant.AFTER_QUTO);
        }
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException(i2);
        }
        if (i3 > i) {
            throw new ArrayIndexOutOfBoundsException(i3);
        }
    }
}
