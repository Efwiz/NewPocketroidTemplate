package org.jetbrains.kotlin.com.intellij.util.diff;

import java.util.Arrays;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
final class UniqueLCS {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int myCount1;
    private final int myCount2;
    private final int[] myFirst;
    private final int[] mySecond;
    private final int myStart1;
    private final int myStart2;

    UniqueLCS(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4) {
        this.myFirst = iArr;
        this.mySecond = iArr2;
        this.myStart1 = i;
        this.myStart2 = i3;
        this.myCount1 = i2;
        this.myCount2 = i4;
    }

    public int[][] execute() {
        int iBinarySearch;
        Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap(this.myCount1 + this.myCount2);
        int[] iArr = new int[this.myCount1];
        for (int i = 0; i < this.myCount1; i++) {
            int i2 = this.myStart1 + i;
            int i3 = int2IntOpenHashMap.get(this.myFirst[i2]);
            if (i3 != -1) {
                if (i3 == 0) {
                    int2IntOpenHashMap.put(this.myFirst[i2], i + 1);
                } else {
                    int2IntOpenHashMap.put(this.myFirst[i2], -1);
                }
            }
        }
        int i4 = 0;
        for (int i5 = 0; i5 < this.myCount2; i5++) {
            int i6 = this.myStart2 + i5;
            int i7 = int2IntOpenHashMap.get(this.mySecond[i6]);
            if (i7 != 0 && i7 != -1) {
                int i8 = i7 - 1;
                if (iArr[i8] == 0) {
                    iArr[i8] = i5 + 1;
                    i4++;
                } else {
                    iArr[i8] = 0;
                    int2IntOpenHashMap.put(this.mySecond[i6], -1);
                    i4--;
                }
            }
        }
        if (i4 == 0) {
            return null;
        }
        int[] iArr2 = new int[i4];
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[this.myCount1];
        int i9 = 0;
        for (int i10 = 0; i10 < this.myCount1; i10++) {
            int i11 = iArr[i10];
            if (i11 != 0 && ((iBinarySearch = binarySearch(iArr2, i11, i9)) == i9 || iArr[i10] < iArr2[iBinarySearch])) {
                iArr2[iBinarySearch] = iArr[i10];
                iArr3[iBinarySearch] = i10;
                iArr4[i10] = iBinarySearch > 0 ? iArr3[iBinarySearch - 1] : -1;
                if (iBinarySearch == i9) {
                    i9++;
                }
            }
        }
        int[][] iArr5 = {new int[i9], new int[i9]};
        int i12 = i9 - 1;
        for (int i13 = iArr3[i12]; i13 != -1; i13 = iArr4[i13]) {
            iArr5[0][i12] = i13;
            iArr5[1][i12] = iArr[i13] - 1;
            i12--;
        }
        return iArr5;
    }

    private static int binarySearch(int[] iArr, int i, int i2) {
        return (-Arrays.binarySearch(iArr, 0, i2, i)) - 1;
    }
}
