package org.jetbrains.kotlin.com.intellij.util.diff;

import java.util.Arrays;
import java.util.BitSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;

/* JADX INFO: loaded from: classes3.dex */
final class Reindexer {
    private final int[][] myOldIndices = new int[2][];
    private final int[] myOriginalLengths = {-1, -1};
    private final int[] myDiscardedLengths = {-1, -1};

    Reindexer() {
    }

    public int[][] discardUnique(int[] iArr, int[] iArr2) {
        int[] iArrDiscard = discard(iArr2, iArr, 0);
        return new int[][]{iArrDiscard, discard(iArrDiscard, iArr2, 1)};
    }

    private int[] discard(int[] iArr, int[] iArr2, int i) {
        this.myOriginalLengths[i] = iArr2.length;
        int[] iArrCreateSorted = createSorted(iArr);
        IntArrayList intArrayList = new IntArrayList(iArr2.length);
        IntArrayList intArrayList2 = new IntArrayList(iArr2.length);
        for (int i2 = 0; i2 < iArr2.length; i2++) {
            int i3 = iArr2[i2];
            if (Arrays.binarySearch(iArrCreateSorted, i3) >= 0) {
                intArrayList.add(i3);
                intArrayList2.add(i2);
            }
        }
        this.myOldIndices[i] = intArrayList2.toIntArray();
        this.myDiscardedLengths[i] = intArrayList.size();
        return intArrayList.toIntArray();
    }

    private static int[] createSorted(int[] iArr) {
        int[] iArr2 = (int[]) iArr.clone();
        Arrays.sort(iArr2);
        return iArr2;
    }

    public void reindex(BitSet[] bitSetArr, LCSBuilder lCSBuilder) {
        BitSet bitSet;
        int i;
        BitSet bitSet2;
        int[] iArr;
        int i2;
        int[] iArr2 = this.myDiscardedLengths;
        int i3 = iArr2[0];
        int[] iArr3 = this.myOriginalLengths;
        if (i3 == iArr3[0] && iArr2[1] == iArr3[1]) {
            bitSet = bitSetArr[0];
            bitSet2 = bitSetArr[1];
        } else {
            bitSet = new BitSet(this.myOriginalLengths[0]);
            BitSet bitSet3 = new BitSet(this.myOriginalLengths[1]);
            int iIncrement = 0;
            int iIncrement2 = 0;
            while (true) {
                int[] iArr4 = this.myDiscardedLengths;
                i = iArr4[0];
                if (iIncrement >= i && iIncrement2 >= iArr4[1]) {
                    break;
                }
                if (iIncrement < i && iIncrement2 < iArr4[1] && !bitSetArr[0].get(iIncrement) && !bitSetArr[1].get(iIncrement2)) {
                    iIncrement = increment(this.myOldIndices[0], iIncrement, bitSet, this.myOriginalLengths[0]);
                    iIncrement2 = increment(this.myOldIndices[1], iIncrement2, bitSet3, this.myOriginalLengths[1]);
                } else if (bitSetArr[0].get(iIncrement)) {
                    bitSet.set(getOriginal(this.myOldIndices[0], iIncrement));
                    iIncrement = increment(this.myOldIndices[0], iIncrement, bitSet, this.myOriginalLengths[0]);
                } else if (bitSetArr[1].get(iIncrement2)) {
                    bitSet3.set(getOriginal(this.myOldIndices[1], iIncrement2));
                    iIncrement2 = increment(this.myOldIndices[1], iIncrement2, bitSet3, this.myOriginalLengths[1]);
                }
            }
            if (i == 0) {
                bitSet.set(0, this.myOriginalLengths[0]);
            } else {
                bitSet.set(0, this.myOldIndices[0][0]);
            }
            if (this.myDiscardedLengths[1] == 0) {
                bitSet3.set(0, this.myOriginalLengths[1]);
            } else {
                bitSet3.set(0, this.myOldIndices[1][0]);
            }
            bitSet2 = bitSet3;
        }
        int i4 = 0;
        int i5 = 0;
        while (true) {
            iArr = this.myOriginalLengths;
            i2 = iArr[0];
            if (i4 >= i2 || i5 >= iArr[1]) {
                break;
            }
            int i6 = i4;
            while (true) {
                int[] iArr5 = this.myOriginalLengths;
                if (i6 >= iArr5[0] || i5 >= iArr5[1] || bitSet.get(i6) || bitSet2.get(i5)) {
                    break;
                }
                i6++;
                i5++;
            }
            if (i6 > i4) {
                lCSBuilder.addEqual(i6 - i4);
            }
            i4 = i6;
            int i7 = 0;
            while (i4 < this.myOriginalLengths[0] && bitSet.get(i4)) {
                i7++;
                i4++;
            }
            int i8 = 0;
            while (i5 < this.myOriginalLengths[1] && bitSet2.get(i5)) {
                i8++;
                i5++;
            }
            if (i7 != 0 || i8 != 0) {
                lCSBuilder.addChange(i7, i8);
            }
        }
        if (i4 == i2 && i5 == iArr[1]) {
            return;
        }
        lCSBuilder.addChange(i2 - i4, iArr[1] - i5);
    }

    private static int getOriginal(int[] iArr, int i) {
        return iArr[i];
    }

    private static int increment(int[] iArr, int i, BitSet bitSet, int i2) {
        int i3 = i + 1;
        if (i3 < iArr.length) {
            bitSet.set(iArr[i] + 1, iArr[i3]);
        } else {
            bitSet.set(iArr[i] + 1, i2);
        }
        return i3;
    }
}
