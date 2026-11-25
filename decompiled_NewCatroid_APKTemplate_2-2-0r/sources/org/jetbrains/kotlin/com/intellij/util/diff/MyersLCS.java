package org.jetbrains.kotlin.com.intellij.util.diff;

import java.util.BitSet;

/* JADX INFO: loaded from: classes3.dex */
class MyersLCS {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int[] VBackward;
    private final int[] VForward;
    private final BitSet myChanges1;
    private final BitSet myChanges2;
    private final int myCount1;
    private final int myCount2;
    private final int[] myFirst;
    private final int[] mySecond;
    private final int myStart1;
    private final int myStart2;

    MyersLCS(int[] iArr, int[] iArr2) {
        this(iArr, iArr2, 0, iArr.length, 0, iArr2.length, new BitSet(iArr.length), new BitSet(iArr2.length));
    }

    MyersLCS(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4, BitSet bitSet, BitSet bitSet2) {
        this.myFirst = iArr;
        this.mySecond = iArr2;
        this.myStart1 = i;
        this.myStart2 = i3;
        this.myCount1 = i2;
        this.myCount2 = i4;
        this.myChanges1 = bitSet;
        this.myChanges2 = bitSet2;
        bitSet.set(i, i + i2);
        bitSet2.set(i3, i3 + i4);
        int i5 = i2 + i4 + 1;
        this.VForward = new int[i5];
        this.VBackward = new int[i5];
    }

    public void executeLinear() {
        try {
            execute((((int) Math.sqrt(this.myCount1 + this.myCount2)) * 10) + 20000, false);
        } catch (FilesTooBigForDiffException e) {
            throw new IllegalStateException(e);
        }
    }

    public void executeWithThreshold() throws FilesTooBigForDiffException {
        execute(Math.max((((int) Math.sqrt(this.myCount1 + this.myCount2)) * 10) + 20000, FilesTooBigForDiffException.DELTA_THRESHOLD_SIZE), true);
    }

    private void execute(int i, boolean z) throws FilesTooBigForDiffException {
        int i2;
        int i3 = this.myCount1;
        if (i3 == 0 || (i2 = this.myCount2) == 0) {
            return;
        }
        execute(0, i3, 0, i2, Math.min(i, i3 + i2), z);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0055  */
    /* JADX WARN: Code duplicated, block: B:32:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:33:0x00ad A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:34:0x00af  */
    /* JADX WARN: Code duplicated, block: B:38:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:42:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:44:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:81:0x0114 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:82:0x0114 A[SYNTHETIC] */
    private void execute(int i, int i2, int i3, int i4, int i5, boolean z) throws FilesTooBigForDiffException {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int[] iArr;
        int i16;
        int i17 = i;
        int i18 = i3;
        if (i17 >= i2 || i18 >= i4) {
            return;
        }
        int i19 = i2 - i17;
        int i20 = i4 - i18;
        int i21 = i20 + 1;
        int i22 = 0;
        this.VForward[i21] = 0;
        this.VBackward[i21] = 0;
        int i23 = 1;
        int i24 = (i5 + 1) / 2;
        loop0: while (true) {
            if (i22 > i24) {
                i6 = -1;
                i7 = -1;
                i8 = -1;
                i9 = 1;
                break;
            }
            int iMax = Math.max(-i22, (-i20) + ((i22 ^ i20) & i23)) + i20;
            int iMin = Math.min(i22, i19 - ((i22 ^ i19) & i23)) + i20;
            int i25 = iMax;
            while (i25 <= iMin) {
                if (i25 == iMax) {
                    i16 = this.VForward[i25 + 1];
                } else {
                    if (i25 != iMin) {
                        int[] iArr2 = this.VForward;
                        if (iArr2[i25 - 1] < iArr2[i25 + 1]) {
                            i16 = this.VForward[i25 + 1];
                        }
                    }
                    i16 = this.VForward[i25 - 1] + i23;
                }
                int i26 = (i16 - i25) + i20;
                this.VForward[i25] = i16 + commonSubsequenceLengthForward(i17 + i16, i18 + i26, Math.min(i19 - i16, i20 - i26));
                i25 += 2;
                i24 = i24;
                i23 = 1;
            }
            int i27 = i24;
            int i28 = (i19 - i20) % 2;
            if (i28 != 0) {
                i6 = iMax;
                while (true) {
                    if (i6 <= iMin) {
                        int i29 = i22 - 1;
                        if (i19 - i29 <= i6 && i6 <= i29 + i19) {
                            int i30 = this.VForward[i6];
                            if (this.VBackward[(i20 + i19) - i6] + i30 >= i19) {
                                i9 = 1;
                                i14 = (i22 * 2) - 1;
                                i8 = i30;
                            }
                        }
                        i6 += 2;
                    } else {
                        i11 = iMax;
                        while (i11 <= iMin) {
                            if (i11 == iMax) {
                                i15 = this.VBackward[i11 + 1];
                            } else {
                                if (i11 != iMin) {
                                    iArr = this.VBackward;
                                    if (iArr[i11 - 1] < iArr[i11 + 1]) {
                                        i15 = this.VBackward[i11 + 1];
                                    }
                                }
                                i15 = this.VBackward[i11 - 1] + 1;
                            }
                            int i31 = (i15 - i11) + i20;
                            this.VBackward[i11] = i15 + commonSubsequenceLengthBackward((i2 - 1) - i15, (i4 - 1) - i31, Math.min(i19 - i15, i20 - i31));
                            i11 += 2;
                            iMax = iMax;
                        }
                        i12 = iMax;
                        if (i28 == 0) {
                            i13 = i12;
                            while (true) {
                                if (i13 <= iMin) {
                                    if (i19 - i22 > i13 && i13 <= i19 + i22) {
                                        i6 = (i19 + i20) - i13;
                                        int i32 = this.VForward[i6];
                                        int i33 = this.VBackward[i13];
                                        if (i32 + i33 >= i19) {
                                            i14 = i22 * 2;
                                            i8 = i19 - i33;
                                            i9 = 1;
                                        }
                                    }
                                    i13 += 2;
                                } else {
                                    continue;
                                }
                            }
                        }
                        i22++;
                        i24 = i27;
                        i23 = 1;
                    }
                }
            } else {
                i11 = iMax;
                while (i11 <= iMin) {
                    if (i11 == iMax) {
                        i15 = this.VBackward[i11 + 1];
                    } else {
                        if (i11 != iMin) {
                            iArr = this.VBackward;
                            if (iArr[i11 - 1] < iArr[i11 + 1]) {
                                i15 = this.VBackward[i11 + 1];
                            }
                        }
                        i15 = this.VBackward[i11 - 1] + 1;
                    }
                    int i34 = (i15 - i11) + i20;
                    this.VBackward[i11] = i15 + commonSubsequenceLengthBackward((i2 - 1) - i15, (i4 - 1) - i34, Math.min(i19 - i15, i20 - i34));
                    i11 += 2;
                    iMax = iMax;
                }
                i12 = iMax;
                if (i28 == 0) {
                    i13 = i12;
                    while (true) {
                        if (i13 <= iMin) {
                            if (i19 - i22 > i13) {
                            }
                            i13 += 2;
                        } else {
                            continue;
                        }
                    }
                }
                i22++;
                i24 = i27;
                i23 = 1;
            }
            i7 = i14;
            break loop0;
        }
        if (i7 > i9) {
            int i35 = (i8 - i6) + i20;
            int i36 = (i7 + 1) / 2;
            if (i8 > 0 && i35 > 0) {
                execute(i, i17 + i8, i3, i18 + i35, i36, z);
            }
            int i37 = i17 + i8;
            if (i37 >= i2 || (i10 = i18 + i35) >= i4) {
                return;
            }
            execute(i37, i2, i10, i4, i7 - i36, z);
            return;
        }
        if (i7 < 0) {
            if (z) {
                throw new FilesTooBigForDiffException();
            }
            return;
        }
        while (i17 < i2 && i18 < i4) {
            int iCommonSubsequenceLengthForward = commonSubsequenceLengthForward(i17, i18, Math.min(i2 - i17, i4 - i18));
            if (iCommonSubsequenceLengthForward > 0) {
                addUnchanged(i17, i18, iCommonSubsequenceLengthForward);
                i17 += iCommonSubsequenceLengthForward;
                i18 += iCommonSubsequenceLengthForward;
            } else if (i19 > i20) {
                i17++;
            } else {
                i18++;
            }
        }
    }

    private void addUnchanged(int i, int i2, int i3) {
        BitSet bitSet = this.myChanges1;
        int i4 = this.myStart1;
        bitSet.set(i4 + i, i4 + i + i3, false);
        BitSet bitSet2 = this.myChanges2;
        int i5 = this.myStart2;
        bitSet2.set(i5 + i2, i5 + i2 + i3, false);
    }

    private int commonSubsequenceLengthForward(int i, int i2, int i3) {
        int i4;
        int iMin = Math.min(i3, Math.min(this.myCount1 - i, this.myCount2 - i2));
        int i5 = i;
        while (true) {
            i4 = i5 - i;
            if (i4 >= iMin || this.myFirst[this.myStart1 + i5] != this.mySecond[this.myStart2 + i2]) {
                break;
            }
            i5++;
            i2++;
        }
        return i4;
    }

    private int commonSubsequenceLengthBackward(int i, int i2, int i3) {
        int i4;
        int iMin = Math.min(i3, Math.min(i, i2) + 1);
        int i5 = i;
        while (true) {
            i4 = i - i5;
            if (i4 >= iMin || this.myFirst[this.myStart1 + i5] != this.mySecond[this.myStart2 + i2]) {
                break;
            }
            i5--;
            i2--;
        }
        return i4;
    }

    public BitSet[] getChanges() {
        return new BitSet[]{this.myChanges1, this.myChanges2};
    }
}
