package org.jetbrains.kotlin.com.intellij.util.diff;

import java.util.BitSet;

/* JADX INFO: loaded from: classes3.dex */
class PatienceIntLCS {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final BitSet myChanges1;
    private final BitSet myChanges2;
    private final int myCount1;
    private final int myCount2;
    private final int[] myFirst;
    private final int[] mySecond;
    private final int myStart1;
    private final int myStart2;

    PatienceIntLCS(int[] iArr, int[] iArr2) {
        this(iArr, iArr2, 0, iArr.length, 0, iArr2.length, new BitSet(iArr.length), new BitSet(iArr2.length));
    }

    PatienceIntLCS(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4, BitSet bitSet, BitSet bitSet2) {
        this.myFirst = iArr;
        this.mySecond = iArr2;
        this.myStart1 = i;
        this.myStart2 = i3;
        this.myCount1 = i2;
        this.myCount2 = i4;
        this.myChanges1 = bitSet;
        this.myChanges2 = bitSet2;
    }

    public void execute() throws FilesTooBigForDiffException {
        execute(false);
    }

    public void execute(boolean z) throws FilesTooBigForDiffException {
        execute(this.myStart1, this.myCount1, this.myStart2, this.myCount2, z ? 2 : -1);
    }

    private void execute(int i, int i2, int i3, int i4, int i5) throws FilesTooBigForDiffException {
        int[] iArr;
        int i6;
        if (i2 == 0 && i4 == 0) {
            return;
        }
        if (i2 == 0 || i4 == 0) {
            addChange(i, i2, i3, i4);
            return;
        }
        int iMatchForward = matchForward(i, i2, i3, i4);
        int i7 = i + iMatchForward;
        int i8 = i3 + iMatchForward;
        int i9 = i2 - iMatchForward;
        int i10 = i4 - iMatchForward;
        int iMatchBackward = matchBackward(i7, i9, i8, i10);
        int i11 = i9 - iMatchBackward;
        int i12 = i10 - iMatchBackward;
        if (i11 == 0 || i12 == 0) {
            addChange(i7, i11, i8, i12);
            return;
        }
        if (i5 == 0) {
            checkReduction(i11, i12);
        }
        int iMax = Math.max(-1, i5 - 1);
        int[][] iArrExecute = new UniqueLCS(this.myFirst, this.mySecond, i7, i11, i8, i12).execute();
        if (iArrExecute == null) {
            if (iMax >= 0) {
                checkReduction(i11, i12);
            }
            new MyersLCS(this.myFirst, this.mySecond, i7, i11, i8, i12, this.myChanges1, this.myChanges2).executeLinear();
            return;
        }
        int i13 = 0;
        int[] iArr2 = iArrExecute[0];
        int length = iArr2.length;
        execute(i7, iArr2[0], i8, iArrExecute[1][0], iMax);
        int i14 = 1;
        while (true) {
            iArr = iArrExecute[0];
            if (i14 >= iArr.length) {
                break;
            }
            int i15 = i14 - 1;
            int i16 = iArr[i15] + 1;
            int[] iArr3 = iArrExecute[1];
            int i17 = iArr3[i15] + 1;
            int i18 = iArr[i14] - i16;
            int i19 = iArr3[i14] - i17;
            if (i18 > 0 || i19 > 0) {
                execute(i16 + i7, i18, i8 + i17, i19, iMax);
            }
            i14++;
        }
        int i20 = length - 1;
        int i21 = iArr[i20];
        int i22 = i11 - 1;
        if (i21 == i22) {
            i6 = 0;
        } else {
            i22 = i21 + 1;
            i6 = i11 - i22;
        }
        int i23 = iArrExecute[1][i20];
        int i24 = i12 - 1;
        if (i23 != i24) {
            i24 = i23 + 1;
            i13 = i12 - i24;
        }
        execute(i7 + i22, i6, i8 + i24, i13, iMax);
    }

    private int matchForward(int i, int i2, int i3, int i4) {
        int iMin = Math.min(i2, i4);
        int i5 = 0;
        for (int i6 = 0; i6 < iMin && this.myFirst[i + i6] == this.mySecond[i3 + i6]; i6++) {
            i5++;
        }
        return i5;
    }

    private int matchBackward(int i, int i2, int i3, int i4) {
        int iMin = Math.min(i2, i4);
        int i5 = 0;
        for (int i6 = 1; i6 <= iMin && this.myFirst[(i + i2) - i6] == this.mySecond[(i3 + i4) - i6]; i6++) {
            i5++;
        }
        return i5;
    }

    private void addChange(int i, int i2, int i3, int i4) {
        this.myChanges1.set(i, i2 + i);
        this.myChanges2.set(i3, i4 + i3);
    }

    public BitSet[] getChanges() {
        return new BitSet[]{this.myChanges1, this.myChanges2};
    }

    private void checkReduction(int i, int i2) throws FilesTooBigForDiffException {
        if (i * 2 >= this.myCount1 && i2 * 2 >= this.myCount2) {
            throw new FilesTooBigForDiffException();
        }
    }
}
