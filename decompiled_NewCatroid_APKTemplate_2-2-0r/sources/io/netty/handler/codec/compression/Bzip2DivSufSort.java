package io.netty.handler.codec.compression;

/* JADX INFO: loaded from: classes11.dex */
final class Bzip2DivSufSort {
    private static final int BUCKET_A_SIZE = 256;
    private static final int BUCKET_B_SIZE = 65536;
    private static final int INSERTIONSORT_THRESHOLD = 8;
    private static final int[] LOG_2_TABLE = {-1, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
    private static final int SS_BLOCKSIZE = 1024;
    private static final int STACK_SIZE = 64;
    private final int[] SA;
    private final byte[] T;
    private final int n;

    private static int BUCKET_B(int i, int i2) {
        return i | (i2 << 8);
    }

    private static int BUCKET_BSTAR(int i, int i2) {
        return (i << 8) | i2;
    }

    private static int getIDX(int i) {
        return i >= 0 ? i : ~i;
    }

    Bzip2DivSufSort(byte[] bArr, int[] iArr, int i) {
        this.T = bArr;
        this.SA = iArr;
        this.n = i;
    }

    private static void swapElements(int[] iArr, int i, int[] iArr2, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr2[i2];
        iArr2[i2] = i3;
    }

    private int ssCompare(int i, int i2, int i3) {
        int[] iArr = this.SA;
        byte[] bArr = this.T;
        int i4 = iArr[i + 1] + 2;
        int i5 = iArr[i2 + 1] + 2;
        int i6 = iArr[i] + i3;
        int i7 = i3 + iArr[i2];
        while (i6 < i4 && i7 < i5 && bArr[i6] == bArr[i7]) {
            i6++;
            i7++;
        }
        if (i6 >= i4) {
            return i7 < i5 ? -1 : 0;
        }
        if (i7 < i5) {
            return (bArr[i6] & 255) - (bArr[i7] & 255);
        }
        return 1;
    }

    private int ssCompareLast(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.SA;
        byte[] bArr = this.T;
        int i6 = iArr[i2] + i4;
        int i7 = i4 + iArr[i3];
        int i8 = iArr[i3 + 1] + 2;
        while (i6 < i5 && i7 < i8 && bArr[i6] == bArr[i7]) {
            i6++;
            i7++;
        }
        if (i6 < i5) {
            if (i7 < i8) {
                return (bArr[i6] & 255) - (bArr[i7] & 255);
            }
            return 1;
        }
        if (i7 == i8) {
            return 1;
        }
        int i9 = i6 % i5;
        int i10 = iArr[i] + 2;
        while (i9 < i10 && i7 < i8 && bArr[i9] == bArr[i7]) {
            i9++;
            i7++;
        }
        if (i9 >= i10) {
            return i7 < i8 ? -1 : 0;
        }
        if (i7 < i8) {
            return (bArr[i9] & 255) - (bArr[i7] & 255);
        }
        return 1;
    }

    private void ssInsertionSort(int i, int i2, int i3, int i4) {
        int iSsCompare;
        int[] iArr = this.SA;
        for (int i5 = i3 - 2; i2 <= i5; i5--) {
            int i6 = iArr[i5];
            int i7 = i5 + 1;
            do {
                iSsCompare = ssCompare(i + i6, iArr[i7] + i, i4);
                if (iSsCompare <= 0) {
                    break;
                }
                do {
                    iArr[i7 - 1] = iArr[i7];
                    i7++;
                    if (i7 >= i3) {
                        break;
                    }
                } while (iArr[i7] < 0);
            } while (i3 > i7);
            if (iSsCompare == 0) {
                iArr[i7] = ~iArr[i7];
            }
            iArr[i7 - 1] = i6;
        }
    }

    private void ssFixdown(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.SA;
        byte[] bArr = this.T;
        int i6 = iArr[i3 + i4];
        int i7 = bArr[iArr[i2 + i6] + i] & 255;
        while (true) {
            int i8 = i4 * 2;
            int i9 = i8 + 1;
            if (i9 >= i5) {
                break;
            }
            int i10 = i8 + 2;
            int i11 = bArr[iArr[iArr[i3 + i9] + i2] + i] & 255;
            int i12 = bArr[iArr[iArr[i3 + i10] + i2] + i] & 255;
            if (i11 < i12) {
                i11 = i12;
            } else {
                i10 = i9;
            }
            if (i11 <= i7) {
                break;
            }
            iArr[i4 + i3] = iArr[i3 + i10];
            i4 = i10;
        }
        iArr[i3 + i4] = i6;
    }

    private void ssHeapSort(int i, int i2, int i3, int i4) {
        int i5;
        int[] iArr = this.SA;
        byte[] bArr = this.T;
        int i6 = i4 % 2;
        if (i6 == 0) {
            int i7 = i4 - 1;
            int i8 = (i7 / 2) + i3;
            int i9 = i3 + i7;
            if ((bArr[iArr[iArr[i8] + i2] + i] & 255) < (bArr[iArr[iArr[i9] + i2] + i] & 255)) {
                swapElements(iArr, i9, iArr, i8);
            }
            i5 = i7;
        } else {
            i5 = i4;
        }
        for (int i10 = (i5 / 2) - 1; i10 >= 0; i10--) {
            ssFixdown(i, i2, i3, i10, i5);
        }
        if (i6 == 0) {
            swapElements(iArr, i3, iArr, i3 + i5);
            ssFixdown(i, i2, i3, 0, i5);
        }
        for (int i11 = i5 - 1; i11 > 0; i11--) {
            int i12 = iArr[i3];
            int i13 = i3 + i11;
            iArr[i3] = iArr[i13];
            ssFixdown(i, i2, i3, 0, i11);
            iArr[i13] = i12;
        }
    }

    private int ssMedian3(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.SA;
        byte[] bArr = this.T;
        int i6 = bArr[iArr[iArr[i3] + i2] + i] & 255;
        int i7 = bArr[iArr[iArr[i4] + i2] + i] & 255;
        int i8 = bArr[i + iArr[i2 + iArr[i5]]] & 255;
        if (i6 <= i7) {
            i4 = i3;
            i3 = i4;
            i7 = i6;
            i6 = i7;
        }
        if (i6 > i8) {
            return i7 > i8 ? i4 : i5;
        }
        return i3;
    }

    private int ssMedian5(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int[] iArr = this.SA;
        byte[] bArr = this.T;
        int i17 = bArr[iArr[iArr[i3] + i2] + i] & 255;
        int i18 = bArr[iArr[iArr[i4] + i2] + i] & 255;
        int i19 = bArr[iArr[iArr[i5] + i2] + i] & 255;
        int i20 = bArr[iArr[iArr[i6] + i2] + i] & 255;
        int i21 = bArr[iArr[iArr[i7] + i2] + i] & 255;
        if (i18 > i19) {
            i8 = i5;
            i9 = i18;
            i10 = i4;
        } else {
            i8 = i4;
            i9 = i19;
            i19 = i18;
            i10 = i5;
        }
        if (i20 > i21) {
            i12 = i21;
            i11 = i20;
            i14 = i6;
            i13 = i7;
        } else {
            i11 = i21;
            i12 = i20;
            i13 = i6;
            i14 = i7;
        }
        if (i19 > i12) {
            int i22 = i10;
            i10 = i14;
            i14 = i22;
            int i23 = i11;
            i11 = i9;
            i9 = i23;
        } else {
            i8 = i13;
            i19 = i12;
        }
        if (i17 > i9) {
            i15 = i17;
            i16 = i3;
        } else {
            i15 = i9;
            i9 = i17;
            i16 = i10;
            i10 = i3;
        }
        if (i9 > i19) {
            i8 = i10;
            i19 = i9;
        } else {
            i14 = i16;
            i11 = i15;
        }
        return i11 > i19 ? i8 : i14;
    }

    private int ssPivot(int i, int i2, int i3, int i4) {
        int i5 = i4 - i3;
        int i6 = i3 + (i5 / 2);
        if (i5 <= 512) {
            if (i5 <= 32) {
                return ssMedian3(i, i2, i3, i6, i4 - 1);
            }
            int i7 = i5 >> 2;
            int i8 = i4 - 1;
            return ssMedian5(i, i2, i3, i3 + i7, i6, i8 - i7, i8);
        }
        int i9 = i5 >> 3;
        int i10 = i9 << 1;
        int i11 = i4 - 1;
        return ssMedian3(i, i2, ssMedian3(i, i2, i3, i3 + i9, i3 + i10), ssMedian3(i, i2, i6 - i9, i6, i6 + i9), ssMedian3(i, i2, i11 - i10, i11 - i9, i11));
    }

    private static int ssLog(int i) {
        return (65280 & i) != 0 ? LOG_2_TABLE[(i >> 8) & 255] + 8 : LOG_2_TABLE[i & 255];
    }

    private int ssSubstringPartition(int i, int i2, int i3, int i4) {
        int i5;
        int[] iArr = this.SA;
        int i6 = i2 - 1;
        while (true) {
            i6++;
            if (i6 < i3) {
                int i7 = iArr[i6];
                if (iArr[i + i7] + i4 >= iArr[i + i7 + 1] + 1) {
                    iArr[i6] = ~i7;
                }
            }
            do {
                i3--;
                if (i6 >= i3) {
                    break;
                }
                i5 = iArr[i3];
            } while (iArr[i + i5] + i4 < iArr[i5 + i + 1] + 1);
            if (i3 <= i6) {
                break;
            }
            int i8 = ~iArr[i3];
            iArr[i3] = iArr[i6];
            iArr[i6] = i8;
        }
        if (i2 < i6) {
            iArr[i2] = ~iArr[i2];
        }
        return i6;
    }

    private static class StackEntry {
        final int a;
        final int b;
        final int c;
        final int d;

        StackEntry(int i, int i2, int i3, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
        }
    }

    private void ssMultiKeyIntroSort(int i, int i2, int i3, int i4) {
        int iSsLog;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        Bzip2DivSufSort bzip2DivSufSort = this;
        int[] iArr = bzip2DivSufSort.SA;
        byte[] bArr = bzip2DivSufSort.T;
        StackEntry[] stackEntryArr = new StackEntry[64];
        int i10 = i4;
        int iSsLog2 = ssLog(i3 - i2);
        int i11 = 0;
        int i12 = 0;
        int iSsSubstringPartition = i2;
        int i13 = i3;
        while (true) {
            int i14 = i13 - iSsSubstringPartition;
            if (i14 <= 8) {
                if (1 < i14) {
                    bzip2DivSufSort.ssInsertionSort(i, iSsSubstringPartition, i13, i10);
                }
                if (i11 == 0) {
                    return;
                }
                i11--;
                StackEntry stackEntry = stackEntryArr[i11];
                int i15 = stackEntry.a;
                int i16 = stackEntry.b;
                int i17 = stackEntry.c;
                iSsLog2 = stackEntry.d;
                iSsSubstringPartition = i15;
                i13 = i16;
                i10 = i17;
            } else {
                int i18 = iSsLog2 - 1;
                if (iSsLog2 == 0) {
                    bzip2DivSufSort.ssHeapSort(i10, i, iSsSubstringPartition, i14);
                }
                if (i18 < 0) {
                    int i19 = bArr[iArr[iArr[iSsSubstringPartition] + i] + i10] & 255;
                    int iSsSubstringPartition2 = iSsSubstringPartition;
                    iSsSubstringPartition++;
                    while (iSsSubstringPartition < i13) {
                        i12 = bArr[iArr[iArr[iSsSubstringPartition] + i] + i10] & 255;
                        if (i12 != i19) {
                            if (1 < iSsSubstringPartition - iSsSubstringPartition2) {
                                break;
                            }
                            iSsSubstringPartition2 = iSsSubstringPartition;
                            i19 = i12;
                        }
                        iSsSubstringPartition++;
                    }
                    if ((bArr[(iArr[iArr[iSsSubstringPartition2] + i] + i10) - 1] & 255) < i19) {
                        iSsSubstringPartition2 = bzip2DivSufSort.ssSubstringPartition(i, iSsSubstringPartition2, iSsSubstringPartition, i10);
                    }
                    int i20 = iSsSubstringPartition - iSsSubstringPartition2;
                    int i21 = i13 - iSsSubstringPartition;
                    if (i20 <= i21) {
                        if (1 < i20) {
                            stackEntryArr[i11] = new StackEntry(iSsSubstringPartition, i13, i10, -1);
                            i10++;
                            iSsLog = ssLog(i20);
                            i11++;
                            int i22 = iSsLog;
                            i13 = iSsSubstringPartition;
                            iSsSubstringPartition = iSsSubstringPartition2;
                            iSsLog2 = i22;
                        } else {
                            iSsLog2 = -1;
                        }
                    } else if (1 < i21) {
                        stackEntryArr[i11] = new StackEntry(iSsSubstringPartition2, iSsSubstringPartition, i10 + 1, ssLog(i20));
                        iSsLog2 = -1;
                        i11++;
                    } else {
                        i10++;
                        iSsLog = ssLog(i20);
                        int i23 = iSsLog;
                        i13 = iSsSubstringPartition;
                        iSsSubstringPartition = iSsSubstringPartition2;
                        iSsLog2 = i23;
                    }
                } else {
                    int iSsPivot = bzip2DivSufSort.ssPivot(i10, i, iSsSubstringPartition, i13);
                    int i24 = bArr[iArr[iArr[iSsPivot] + i] + i10] & 255;
                    swapElements(iArr, iSsSubstringPartition, iArr, iSsPivot);
                    int i25 = iSsSubstringPartition + 1;
                    while (i25 < i13) {
                        i12 = bArr[iArr[iArr[i25] + i] + i10] & 255;
                        if (i12 != i24) {
                            break;
                        } else {
                            i25++;
                        }
                    }
                    if (i25 >= i13 || i12 >= i24) {
                        i5 = i25;
                    } else {
                        i5 = i25;
                        while (true) {
                            i25++;
                            if (i25 >= i13 || (i12 = bArr[iArr[iArr[i25] + i] + i10] & 255) > i24) {
                                break;
                            } else if (i12 == i24) {
                                swapElements(iArr, i25, iArr, i5);
                                i5++;
                            }
                        }
                    }
                    int i26 = i13 - 1;
                    while (i25 < i26) {
                        i12 = bArr[iArr[iArr[i26] + i] + i10] & 255;
                        if (i12 != i24) {
                            break;
                        } else {
                            i26--;
                        }
                    }
                    if (i25 >= i26 || i12 <= i24) {
                        i6 = iSsLog2;
                        i7 = i26;
                    } else {
                        i6 = iSsLog2;
                        i7 = i26;
                        while (true) {
                            i26--;
                            if (i25 >= i26 || (i12 = bArr[iArr[iArr[i26] + i] + i10] & 255) < i24) {
                                break;
                            } else if (i12 == i24) {
                                swapElements(iArr, i26, iArr, i7);
                                i7--;
                            }
                        }
                    }
                    while (i25 < i26) {
                        swapElements(iArr, i25, iArr, i26);
                        while (true) {
                            i25++;
                            if (i25 >= i26 || (i12 = bArr[iArr[iArr[i25] + i] + i10] & 255) > i24) {
                                break;
                            } else if (i12 == i24) {
                                swapElements(iArr, i25, iArr, i5);
                                i5++;
                            }
                        }
                        while (true) {
                            i26--;
                            if (i25 >= i26 || (i12 = bArr[iArr[iArr[i26] + i] + i10] & 255) < i24) {
                                break;
                            } else if (i12 == i24) {
                                swapElements(iArr, i26, iArr, i7);
                                i7--;
                            }
                        }
                    }
                    if (i5 <= i7) {
                        int i27 = i25 - 1;
                        i8 = i12;
                        int i28 = i5 - iSsSubstringPartition;
                        int i29 = i25 - i5;
                        if (i28 > i29) {
                            i28 = i29;
                        }
                        int i30 = iSsSubstringPartition;
                        int i31 = i25;
                        int i32 = i25 - i28;
                        while (i28 > 0) {
                            swapElements(iArr, i30, iArr, i32);
                            i28--;
                            i30++;
                            i32++;
                        }
                        int i33 = i7 - i27;
                        int i34 = (i13 - i7) - 1;
                        if (i33 <= i34) {
                            i34 = i33;
                        }
                        int i35 = i13 - i34;
                        int i36 = i31;
                        while (i34 > 0) {
                            swapElements(iArr, i36, iArr, i35);
                            i34--;
                            i36++;
                            i35++;
                        }
                        int i37 = iSsSubstringPartition + i29;
                        int i38 = i13 - i33;
                        int iSsSubstringPartition3 = i24 <= (bArr[(iArr[iArr[i37] + i] + i10) + (-1)] & 255) ? i37 : bzip2DivSufSort.ssSubstringPartition(i, i37, i38, i10);
                        int i39 = i37 - iSsSubstringPartition;
                        int i40 = i13 - i38;
                        if (i39 <= i40) {
                            int i41 = i38 - iSsSubstringPartition3;
                            if (i40 <= i41) {
                                int i42 = i11 + 1;
                                stackEntryArr[i11] = new StackEntry(iSsSubstringPartition3, i38, i10 + 1, ssLog(i41));
                                i11 += 2;
                                i9 = i18;
                                stackEntryArr[i42] = new StackEntry(i38, i13, i10, i9);
                            } else {
                                i9 = i18;
                                if (i39 <= i41) {
                                    int i43 = i11 + 1;
                                    stackEntryArr[i11] = new StackEntry(i38, i13, i10, i9);
                                    i11 += 2;
                                    stackEntryArr[i43] = new StackEntry(iSsSubstringPartition3, i38, i10 + 1, ssLog(i41));
                                } else {
                                    int i44 = i11 + 1;
                                    stackEntryArr[i11] = new StackEntry(i38, i13, i10, i9);
                                    i11 += 2;
                                    stackEntryArr[i44] = new StackEntry(iSsSubstringPartition, i37, i10, i9);
                                    i10++;
                                    iSsLog2 = ssLog(i41);
                                }
                            }
                            i12 = i8;
                            i13 = i37;
                            iSsLog2 = i9;
                        } else {
                            int i45 = i38 - iSsSubstringPartition3;
                            if (i39 <= i45) {
                                int i46 = i11 + 1;
                                stackEntryArr[i11] = new StackEntry(iSsSubstringPartition3, i38, i10 + 1, ssLog(i45));
                                i11 += 2;
                                stackEntryArr[i46] = new StackEntry(iSsSubstringPartition, i37, i10, i18);
                            } else if (i40 <= i45) {
                                int i47 = i11 + 1;
                                stackEntryArr[i11] = new StackEntry(iSsSubstringPartition, i37, i10, i18);
                                i11 += 2;
                                stackEntryArr[i47] = new StackEntry(iSsSubstringPartition3, i38, i10 + 1, ssLog(i45));
                            } else {
                                int i48 = i11 + 1;
                                stackEntryArr[i11] = new StackEntry(iSsSubstringPartition, i37, i10, i18);
                                i11 += 2;
                                stackEntryArr[i48] = new StackEntry(i38, i13, i10, i18);
                                i10++;
                                iSsLog2 = ssLog(i45);
                                bzip2DivSufSort = this;
                            }
                            bzip2DivSufSort = this;
                            iSsSubstringPartition = i38;
                            iSsLog2 = i18;
                        }
                        i13 = i38;
                        iSsSubstringPartition = iSsSubstringPartition3;
                    } else {
                        i8 = i12;
                        if ((bArr[(iArr[iArr[iSsSubstringPartition] + i] + i10) - 1] & 255) < i24) {
                            bzip2DivSufSort = this;
                            iSsSubstringPartition = bzip2DivSufSort.ssSubstringPartition(i, iSsSubstringPartition, i13, i10);
                            iSsLog2 = ssLog(i13 - iSsSubstringPartition);
                        } else {
                            bzip2DivSufSort = this;
                            iSsLog2 = i6;
                        }
                        i10++;
                    }
                    i12 = i8;
                }
            }
        }
    }

    private static void ssBlockSwap(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        while (i3 > 0) {
            swapElements(iArr, i, iArr2, i2);
            i3--;
            i++;
            i2++;
        }
    }

    private void ssMergeForward(int i, int[] iArr, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int[] iArr2 = this.SA;
        int i8 = i4 - i3;
        int i9 = (i2 + i8) - 1;
        ssBlockSwap(iArr, i2, iArr2, i3, i8);
        int i10 = iArr2[i3];
        while (true) {
            int iSsCompare = ssCompare(iArr[i2] + i, iArr2[i4] + i, i6);
            if (iSsCompare < 0) {
                while (true) {
                    i7 = i3 + 1;
                    iArr2[i3] = iArr[i2];
                    if (i9 <= i2) {
                        iArr[i2] = i10;
                        return;
                    }
                    int i11 = i2 + 1;
                    iArr[i2] = iArr2[i7];
                    if (iArr[i11] >= 0) {
                        i2 = i11;
                        break;
                    } else {
                        i2 = i11;
                        i3 = i7;
                    }
                }
            } else if (iSsCompare > 0) {
                while (true) {
                    i7 = i3 + 1;
                    iArr2[i3] = iArr2[i4];
                    int i12 = i4 + 1;
                    iArr2[i4] = iArr2[i7];
                    if (i5 <= i12) {
                        while (i2 < i9) {
                            int i13 = i7 + 1;
                            iArr2[i7] = iArr[i2];
                            iArr[i2] = iArr2[i13];
                            i7 = i13;
                            i2++;
                        }
                        iArr2[i7] = iArr[i2];
                        iArr[i2] = i10;
                        return;
                    }
                    if (iArr2[i12] >= 0) {
                        i4 = i12;
                        break;
                    } else {
                        i4 = i12;
                        i3 = i7;
                    }
                }
            } else {
                iArr2[i4] = ~iArr2[i4];
                while (true) {
                    int i14 = i3 + 1;
                    iArr2[i3] = iArr[i2];
                    if (i9 <= i2) {
                        iArr[i2] = i10;
                        return;
                    }
                    int i15 = i2 + 1;
                    iArr[i2] = iArr2[i14];
                    if (iArr[i15] >= 0) {
                        while (true) {
                            int i16 = i14 + 1;
                            iArr2[i14] = iArr2[i4];
                            int i17 = i4 + 1;
                            iArr2[i4] = iArr2[i16];
                            if (i5 <= i17) {
                                while (i15 < i9) {
                                    int i18 = i16 + 1;
                                    iArr2[i16] = iArr[i15];
                                    iArr[i15] = iArr2[i18];
                                    i15++;
                                    i16 = i18;
                                }
                                iArr2[i16] = iArr[i15];
                                iArr[i15] = i10;
                                return;
                            }
                            if (iArr2[i17] >= 0) {
                                i4 = i17;
                                i3 = i16;
                                i2 = i15;
                                break;
                            }
                            i4 = i17;
                            i14 = i16;
                        }
                    } else {
                        i2 = i15;
                        i3 = i14;
                    }
                }
            }
            i3 = i7;
        }
    }

    private void ssMergeBackward(int i, int[] iArr, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int[] iArr2 = this.SA;
        int i17 = i5 - i4;
        ssBlockSwap(iArr, i2, iArr2, i4, i17);
        int i18 = (i2 + i17) - 1;
        int i19 = iArr[i18];
        if (i19 < 0) {
            i7 = i + (~i19);
            i8 = 1;
        } else {
            i7 = i + i19;
            i8 = 0;
        }
        int i20 = i4 - 1;
        int i21 = iArr2[i20];
        if (i21 < 0) {
            i8 |= 2;
            i21 = ~i21;
        }
        int i22 = i + i21;
        int i23 = i5 - 1;
        int i24 = iArr2[i23];
        while (true) {
            int iSsCompare = ssCompare(i7, i22, i6);
            if (iSsCompare > 0) {
                if ((i8 & 1) != 0) {
                    while (true) {
                        i9 = i23 - 1;
                        iArr2[i23] = iArr[i18];
                        i10 = i18 - 1;
                        iArr[i18] = iArr2[i9];
                        if (iArr[i10] >= 0) {
                            break;
                        }
                        i18 = i10;
                        i23 = i9;
                    }
                    i8 ^= 1;
                    i18 = i10;
                    i23 = i9;
                }
                int i25 = i23 - 1;
                iArr2[i23] = iArr[i18];
                if (i18 <= i2) {
                    iArr[i18] = i24;
                    return;
                }
                int i26 = i18 - 1;
                iArr[i18] = iArr2[i25];
                int i27 = iArr[i26];
                if (i27 < 0) {
                    i8 |= 1;
                    i27 = ~i27;
                }
                int i28 = i + i27;
                i18 = i26;
                i23 = i25;
                i7 = i28;
            } else if (iSsCompare < 0) {
                if ((i8 & 2) != 0) {
                    while (true) {
                        i11 = i23 - 1;
                        iArr2[i23] = iArr2[i20];
                        i12 = i20 - 1;
                        iArr2[i20] = iArr2[i11];
                        if (iArr2[i12] >= 0) {
                            break;
                        }
                        i20 = i12;
                        i23 = i11;
                    }
                    i8 ^= 2;
                    i20 = i12;
                    i23 = i11;
                }
                int i29 = i23 - 1;
                iArr2[i23] = iArr2[i20];
                int i30 = i20 - 1;
                iArr2[i20] = iArr2[i29];
                if (i30 < i3) {
                    while (i2 < i18) {
                        int i31 = i29 - 1;
                        iArr2[i29] = iArr[i18];
                        iArr[i18] = iArr2[i31];
                        i29 = i31;
                        i18--;
                    }
                    iArr2[i29] = iArr[i18];
                    iArr[i18] = i24;
                    return;
                }
                int i32 = iArr2[i30];
                if (i32 < 0) {
                    i8 |= 2;
                    i32 = ~i32;
                }
                i22 = i + i32;
                i20 = i30;
                i23 = i29;
            } else {
                if ((i8 & 1) != 0) {
                    while (true) {
                        i15 = i23 - 1;
                        iArr2[i23] = iArr[i18];
                        i16 = i18 - 1;
                        iArr[i18] = iArr2[i15];
                        if (iArr[i16] >= 0) {
                            break;
                        }
                        i18 = i16;
                        i23 = i15;
                    }
                    i8 ^= 1;
                    i18 = i16;
                    i23 = i15;
                }
                int i33 = i23 - 1;
                iArr2[i23] = ~iArr[i18];
                if (i18 <= i2) {
                    iArr[i18] = i24;
                    return;
                }
                int i34 = i18 - 1;
                iArr[i18] = iArr2[i33];
                if ((i8 & 2) != 0) {
                    while (true) {
                        i13 = i33 - 1;
                        iArr2[i33] = iArr2[i20];
                        i14 = i20 - 1;
                        iArr2[i20] = iArr2[i13];
                        if (iArr2[i14] >= 0) {
                            break;
                        }
                        i20 = i14;
                        i33 = i13;
                    }
                    i8 ^= 2;
                    i20 = i14;
                    i33 = i13;
                }
                int i35 = i33 - 1;
                iArr2[i33] = iArr2[i20];
                int i36 = i20 - 1;
                iArr2[i20] = iArr2[i35];
                if (i36 < i3) {
                    while (i2 < i34) {
                        int i37 = i35 - 1;
                        iArr2[i35] = iArr[i34];
                        iArr[i34] = iArr2[i37];
                        i35 = i37;
                        i34--;
                    }
                    iArr2[i35] = iArr[i34];
                    iArr[i34] = i24;
                    return;
                }
                int i38 = iArr[i34];
                if (i38 < 0) {
                    i8 |= 1;
                    i38 = ~i38;
                }
                int i39 = i + i38;
                int i40 = iArr2[i36];
                if (i40 < 0) {
                    i8 |= 2;
                    i40 = ~i40;
                }
                i22 = i + i40;
                i7 = i39;
                i20 = i36;
                i18 = i34;
                i23 = i35;
            }
        }
    }

    private void ssMergeCheckEqual(int i, int i2, int i3) {
        int[] iArr = this.SA;
        if (iArr[i3] < 0 || ssCompare(getIDX(iArr[i3 - 1]) + i, i + iArr[i3], i2) != 0) {
            return;
        }
        iArr[i3] = ~iArr[i3];
    }

    private void ssMerge(int i, int i2, int i3, int i4, int[] iArr, int i5, int i6, int i7) {
        int i8;
        int i9;
        int i10;
        int i11;
        int[] iArr2 = this.SA;
        StackEntry[] stackEntryArr = new StackEntry[64];
        int i12 = i2;
        int i13 = i3;
        int i14 = i4;
        int i15 = 0;
        int i16 = 0;
        while (true) {
            int i17 = i14 - i13;
            if (i17 <= i6) {
                if (i12 < i13 && i13 < i14) {
                    ssMergeBackward(i, iArr, i5, i12, i13, i14, i7);
                }
                if ((i15 & 1) != 0) {
                    ssMergeCheckEqual(i, i7, i12);
                }
                if ((i15 & 2) != 0) {
                    ssMergeCheckEqual(i, i7, i14);
                }
                if (i16 == 0) {
                    return;
                }
                i16--;
                StackEntry stackEntry = stackEntryArr[i16];
                i12 = stackEntry.a;
                i13 = stackEntry.b;
                i14 = stackEntry.c;
                i8 = stackEntry.d;
            } else {
                int i18 = i14;
                int i19 = i13 - i12;
                if (i19 <= i6) {
                    if (i12 < i13) {
                        ssMergeForward(i, iArr, i5, i12, i13, i18, i7);
                    }
                    if ((i15 & 1) != 0) {
                        ssMergeCheckEqual(i, i7, i12);
                    }
                    if ((i15 & 2) != 0) {
                        ssMergeCheckEqual(i, i7, i18);
                    }
                    if (i16 == 0) {
                        return;
                    }
                    i16--;
                    StackEntry stackEntry2 = stackEntryArr[i16];
                    i12 = stackEntry2.a;
                    i13 = stackEntry2.b;
                    i14 = stackEntry2.c;
                    i8 = stackEntry2.d;
                } else {
                    int iMin = Math.min(i19, i17);
                    int i20 = iMin >> 1;
                    int i21 = 0;
                    while (iMin > 0) {
                        if (ssCompare(getIDX(iArr2[i13 + i21 + i20]) + i, getIDX(iArr2[((i13 - i21) - i20) - 1]) + i, i7) < 0) {
                            i21 += i20 + 1;
                            i20 -= (iMin & 1) ^ 1;
                        }
                        iMin = i20;
                        i20 = iMin >> 1;
                    }
                    if (i21 > 0) {
                        int i22 = i13 - i21;
                        ssBlockSwap(iArr2, i22, iArr2, i13, i21);
                        int i23 = i13 + i21;
                        if (i23 < i18) {
                            if (iArr2[i23] < 0) {
                                i11 = i13;
                                while (iArr2[i11 - 1] < 0) {
                                    i11--;
                                }
                                iArr2[i23] = ~iArr2[i23];
                            } else {
                                i11 = i13;
                            }
                            i9 = i13;
                            while (iArr2[i9] < 0) {
                                i9++;
                            }
                            i14 = i11;
                            i10 = 1;
                        } else {
                            i9 = i13;
                            i14 = i9;
                            i10 = 0;
                        }
                        if (i14 - i12 <= i18 - i9) {
                            stackEntryArr[i16] = new StackEntry(i9, i23, i18, i10 | (i15 & 2));
                            i15 &= 1;
                            i13 = i22;
                            i16++;
                        } else {
                            if (i14 == i13 && i13 == i9) {
                                i10 <<= 1;
                            }
                            stackEntryArr[i16] = new StackEntry(i12, i22, i14, (i15 & 1) | (i10 & 2));
                            i15 = (i15 & 2) | (i10 & 1);
                            i13 = i23;
                            i16++;
                            i12 = i9;
                            i14 = i18;
                        }
                    } else {
                        if ((i15 & 1) != 0) {
                            ssMergeCheckEqual(i, i7, i12);
                        }
                        ssMergeCheckEqual(i, i7, i13);
                        if ((i15 & 2) != 0) {
                            ssMergeCheckEqual(i, i7, i18);
                        }
                        if (i16 == 0) {
                            return;
                        }
                        i16--;
                        StackEntry stackEntry3 = stackEntryArr[i16];
                        i12 = stackEntry3.a;
                        i13 = stackEntry3.b;
                        i14 = stackEntry3.c;
                        i8 = stackEntry3.d;
                    }
                }
            }
            i15 = i8;
        }
    }

    private void subStringSort(int i, int i2, int i3, int[] iArr, int i4, int i5, int i6, boolean z, int i7) {
        int i8;
        int i9;
        int[] iArr2;
        int[] iArr3 = this.SA;
        int i10 = z ? i2 + 1 : i2;
        int i11 = 0;
        int i12 = i10;
        while (true) {
            int i13 = i12 + 1024;
            if (i13 >= i3) {
                break;
            }
            ssMultiKeyIntroSort(i, i12, i13, i6);
            int i14 = i3 - i13;
            if (i14 <= i5) {
                iArr2 = iArr;
                i9 = i4;
                i8 = i5;
            } else {
                i8 = i14;
                i9 = i13;
                iArr2 = iArr3;
            }
            int i15 = i12;
            int i16 = 1024;
            int i17 = i11;
            while ((i17 & 1) != 0) {
                int i18 = i15 - i16;
                ssMerge(i, i18, i15, i15 + i16, iArr2, i9, i8, i6);
                i16 <<= 1;
                i17 >>>= 1;
                i15 = i18;
                i13 = i13;
            }
            i11++;
            i12 = i13;
        }
        ssMultiKeyIntroSort(i, i12, i3, i6);
        int i19 = i12;
        int i20 = 1024;
        for (int i21 = i11; i21 != 0; i21 >>= 1) {
            if ((i21 & 1) != 0) {
                int i22 = i19 - i20;
                ssMerge(i, i22, i19, i3, iArr, i4, i5, i6);
                i19 = i22;
            }
            i20 <<= 1;
        }
        if (z) {
            int i23 = iArr3[i10 - 1];
            int iSsCompareLast = 1;
            while (i10 < i3) {
                int i24 = iArr3[i10];
                if (i24 >= 0 && (iSsCompareLast = ssCompareLast(i, i + i23, i + i24, i6, i7)) <= 0) {
                    break;
                }
                iArr3[i10 - 1] = iArr3[i10];
                i10++;
            }
            if (iSsCompareLast == 0) {
                iArr3[i10] = ~iArr3[i10];
            }
            iArr3[i10 - 1] = i23;
        }
    }

    private int trGetC(int i, int i2, int i3, int i4) {
        int i5 = i2 + i4;
        return i5 < i3 ? this.SA[i5] : this.SA[i + (((i2 - i) + i4) % (i3 - i))];
    }

    private void trFixdown(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = this.SA;
        int i7 = iArr[i4 + i5];
        int iTrGetC = trGetC(i, i2, i3, i7);
        while (true) {
            int i8 = i5 * 2;
            int i9 = i8 + 1;
            if (i9 >= i6) {
                break;
            }
            int i10 = i8 + 2;
            int iTrGetC2 = trGetC(i, i2, i3, iArr[i4 + i9]);
            int iTrGetC3 = trGetC(i, i2, i3, iArr[i4 + i10]);
            if (iTrGetC2 < iTrGetC3) {
                iTrGetC2 = iTrGetC3;
            } else {
                i10 = i9;
            }
            if (iTrGetC2 <= iTrGetC) {
                break;
            }
            iArr[i5 + i4] = iArr[i4 + i10];
            i5 = i10;
        }
        iArr[i4 + i5] = i7;
    }

    private void trHeapSort(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int[] iArr = this.SA;
        int i7 = i5 % 2;
        if (i7 == 0) {
            int i8 = i5 - 1;
            int i9 = (i8 / 2) + i4;
            int i10 = i4 + i8;
            if (trGetC(i, i2, i3, iArr[i9]) < trGetC(i, i2, i3, iArr[i10])) {
                swapElements(iArr, i10, iArr, i9);
            }
            i6 = i8;
        } else {
            i6 = i5;
        }
        for (int i11 = (i6 / 2) - 1; i11 >= 0; i11--) {
            trFixdown(i, i2, i3, i4, i11, i6);
        }
        if (i7 == 0) {
            swapElements(iArr, i4, iArr, i4 + i6);
            trFixdown(i, i2, i3, i4, 0, i6);
        }
        for (int i12 = i6 - 1; i12 > 0; i12--) {
            int i13 = iArr[i4];
            int i14 = i4 + i12;
            iArr[i4] = iArr[i14];
            trFixdown(i, i2, i3, i4, 0, i12);
            iArr[i14] = i13;
        }
    }

    private void trInsertionSort(int i, int i2, int i3, int i4, int i5) {
        int iTrGetC;
        int[] iArr = this.SA;
        for (int i6 = i4 + 1; i6 < i5; i6++) {
            int i7 = iArr[i6];
            int i8 = i6 - 1;
            do {
                iTrGetC = trGetC(i, i2, i3, i7) - trGetC(i, i2, i3, iArr[i8]);
                if (iTrGetC >= 0) {
                    break;
                }
                do {
                    iArr[i8 + 1] = iArr[i8];
                    i8--;
                    if (i4 > i8) {
                        break;
                    }
                } while (iArr[i8] < 0);
            } while (i8 >= i4);
            if (iTrGetC == 0) {
                iArr[i8] = ~iArr[i8];
            }
            iArr[i8 + 1] = i7;
        }
    }

    private static int trLog(int i) {
        if (((-65536) & i) != 0) {
            return ((-16777216) & i) != 0 ? LOG_2_TABLE[(i >> 24) & 255] + 24 : LOG_2_TABLE[(i >> 16) & 271];
        }
        return (65280 & i) != 0 ? LOG_2_TABLE[(i >> 8) & 255] + 8 : LOG_2_TABLE[i & 255];
    }

    private int trMedian3(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = this.SA;
        int iTrGetC = trGetC(i, i2, i3, iArr[i4]);
        int iTrGetC2 = trGetC(i, i2, i3, iArr[i5]);
        int iTrGetC3 = trGetC(i, i2, i3, iArr[i6]);
        if (iTrGetC <= iTrGetC2) {
            i5 = i4;
            i4 = i5;
            iTrGetC2 = iTrGetC;
            iTrGetC = iTrGetC2;
        }
        if (iTrGetC > iTrGetC3) {
            return iTrGetC2 > iTrGetC3 ? i5 : i6;
        }
        return i4;
    }

    private int trMedian5(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int[] iArr = this.SA;
        int iTrGetC = trGetC(i, i2, i3, iArr[i4]);
        int iTrGetC2 = trGetC(i, i2, i3, iArr[i5]);
        int iTrGetC3 = trGetC(i, i2, i3, iArr[i6]);
        int iTrGetC4 = trGetC(i, i2, i3, iArr[i7]);
        int iTrGetC5 = trGetC(i, i2, i3, iArr[i8]);
        if (iTrGetC2 > iTrGetC3) {
            i6 = i5;
            i5 = i6;
            iTrGetC3 = iTrGetC2;
            iTrGetC2 = iTrGetC3;
        }
        if (iTrGetC4 > iTrGetC5) {
            iTrGetC4 = iTrGetC5;
            iTrGetC5 = iTrGetC4;
        } else {
            i8 = i7;
            i7 = i8;
        }
        if (iTrGetC2 > iTrGetC4) {
            int i9 = iTrGetC3;
            iTrGetC3 = iTrGetC5;
            iTrGetC5 = i9;
            int i10 = i7;
            i7 = i6;
            i6 = i10;
        } else {
            i5 = i8;
            iTrGetC2 = iTrGetC4;
        }
        if (iTrGetC > iTrGetC3) {
            int i11 = i6;
            i6 = i4;
            i4 = i11;
            int i12 = iTrGetC3;
            iTrGetC3 = iTrGetC;
            iTrGetC = i12;
        }
        if (iTrGetC > iTrGetC2) {
            i5 = i4;
            iTrGetC2 = iTrGetC;
        } else {
            i7 = i6;
            iTrGetC5 = iTrGetC3;
        }
        return iTrGetC5 > iTrGetC2 ? i5 : i7;
    }

    private int trPivot(int i, int i2, int i3, int i4, int i5) {
        int i6 = i5 - i4;
        int i7 = i4 + (i6 / 2);
        if (i6 <= 512) {
            if (i6 <= 32) {
                return trMedian3(i, i2, i3, i4, i7, i5 - 1);
            }
            int i8 = i6 >> 2;
            int i9 = i5 - 1;
            return trMedian5(i, i2, i3, i4, i4 + i8, i7, i9 - i8, i9);
        }
        int i10 = i6 >> 3;
        int i11 = i10 << 1;
        int i12 = i5 - 1;
        return trMedian3(i, i2, i3, trMedian3(i, i2, i3, i4, i4 + i10, i4 + i11), trMedian3(i, i2, i3, i7 - i10, i7, i7 + i10), trMedian3(i, i2, i3, i12 - i11, i12 - i10, i12));
    }

    private void lsUpdateGroup(int i, int i2, int i3) {
        int i4;
        int[] iArr = this.SA;
        while (i2 < i3) {
            if (iArr[i2] >= 0) {
                int i5 = i2;
                do {
                    iArr[iArr[i5] + i] = i5;
                    i5++;
                    if (i5 >= i3) {
                        break;
                    }
                } while (iArr[i5] >= 0);
                iArr[i2] = i2 - i5;
                if (i3 <= i5) {
                    return;
                } else {
                    i2 = i5;
                }
            }
            int i6 = i2;
            while (true) {
                iArr[i6] = ~iArr[i6];
                i4 = i6 + 1;
                if (iArr[i4] >= 0) {
                    break;
                } else {
                    i6 = i4;
                }
            }
            do {
                iArr[iArr[i2] + i] = i4;
                i2++;
            } while (i2 <= i4);
            i2 = i6 + 2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:149:0x011e A[EDGE_INSN: B:149:0x011e->B:155:? BREAK  A[LOOP:6: B:60:0x0107->B:66:0x0119], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:150:0x011c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x0119 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:62:0x010a  */
    /* JADX WARN: Code duplicated, block: B:64:0x0112 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:65:0x0114  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:71:0x0128 -> B:56:0x00fc). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    private void lsIntroSort(int r21, int r22, int r23, int r24, int r25) {
        /*
            Method dump skipped, instruction units count: 462
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Bzip2DivSufSort.lsIntroSort(int, int, int, int, int):void");
    }

    private void lsSort(int i, int i2, int i3) {
        int i4;
        int[] iArr = this.SA;
        int i5 = i3 + i;
        while (true) {
            int i6 = 0;
            if ((-i2) >= iArr[0]) {
                return;
            }
            int i7 = 0;
            int i8 = 0;
            do {
                int i9 = iArr[i8];
                if (i9 < 0) {
                    i8 -= i9;
                    i7 += i9;
                } else {
                    if (i7 != 0) {
                        iArr[i8 + i7] = i7;
                        i4 = 0;
                    } else {
                        i4 = i7;
                    }
                    int i10 = iArr[i9 + i] + 1;
                    lsIntroSort(i, i5, i + i2, i8, i10);
                    i7 = i4;
                    i8 = i10;
                }
            } while (i8 < i2);
            if (i7 != 0) {
                iArr[i8 + i7] = i7;
            }
            int i11 = i5 - i;
            if (i2 < i11) {
                do {
                    int i12 = iArr[i6];
                    if (i12 < 0) {
                        i6 -= i12;
                    } else {
                        int i13 = iArr[i12 + i] + 1;
                        while (i6 < i13) {
                            iArr[iArr[i6] + i] = i6;
                            i6++;
                        }
                        i6 = i13;
                    }
                } while (i6 < i2);
                return;
            }
            i5 += i11;
        }
    }

    private static class PartitionResult {
        final int first;
        final int last;

        PartitionResult(int i, int i2) {
            this.first = i;
            this.last = i2;
        }
    }

    private PartitionResult trPartition(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int i8;
        int iTrGetC;
        int iTrGetC2;
        int iTrGetC3;
        int[] iArr = this.SA;
        int iTrGetC4 = 0;
        int i9 = i4;
        while (i9 < i5) {
            iTrGetC4 = trGetC(i, i2, i3, iArr[i9]);
            if (iTrGetC4 != i6) {
                break;
            }
            i9++;
        }
        if (i9 >= i5 || iTrGetC4 >= i6) {
            i7 = i9;
        } else {
            i7 = i9;
            while (true) {
                i9++;
                if (i9 >= i5 || (iTrGetC4 = trGetC(i, i2, i3, iArr[i9])) > i6) {
                    break;
                }
                if (iTrGetC4 == i6) {
                    swapElements(iArr, i9, iArr, i7);
                    i7++;
                }
            }
        }
        int i10 = i5 - 1;
        while (i9 < i10) {
            iTrGetC4 = trGetC(i, i2, i3, iArr[i10]);
            if (iTrGetC4 != i6) {
                break;
            }
            i10--;
        }
        if (i9 >= i10 || iTrGetC4 <= i6) {
            i8 = i10;
        } else {
            i8 = i10;
            while (true) {
                i10--;
                if (i9 >= i10 || (iTrGetC3 = trGetC(i, i2, i3, iArr[i10])) < i6) {
                    break;
                }
                if (iTrGetC3 == i6) {
                    swapElements(iArr, i10, iArr, i8);
                    i8--;
                }
            }
        }
        while (i9 < i10) {
            swapElements(iArr, i9, iArr, i10);
            while (true) {
                i9++;
                if (i9 >= i10 || (iTrGetC2 = trGetC(i, i2, i3, iArr[i9])) > i6) {
                    break;
                }
                if (iTrGetC2 == i6) {
                    swapElements(iArr, i9, iArr, i7);
                    i7++;
                }
            }
            while (true) {
                i10--;
                if (i9 >= i10 || (iTrGetC = trGetC(i, i2, i3, iArr[i10])) < i6) {
                    break;
                }
                if (iTrGetC == i6) {
                    swapElements(iArr, i10, iArr, i8);
                    i8--;
                }
            }
        }
        if (i7 <= i8) {
            int i11 = i9 - 1;
            int i12 = i7 - i4;
            int i13 = i9 - i7;
            if (i12 > i13) {
                i12 = i13;
            }
            int i14 = i9 - i12;
            int i15 = i4;
            while (i12 > 0) {
                swapElements(iArr, i15, iArr, i14);
                i12--;
                i15++;
                i14++;
            }
            int i16 = i8 - i11;
            int i17 = (i5 - i8) - 1;
            if (i16 <= i17) {
                i17 = i16;
            }
            int i18 = i5 - i17;
            while (i17 > 0) {
                swapElements(iArr, i9, iArr, i18);
                i17--;
                i9++;
                i18++;
            }
            i4 += i13;
            i5 -= i16;
        }
        return new PartitionResult(i4, i5);
    }

    private void trCopy(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int[] iArr = this.SA;
        int i8 = i5 - 1;
        int i9 = i4 - 1;
        while (i3 <= i9) {
            int i10 = iArr[i3] - i7;
            if (i10 < 0) {
                i10 += i2 - i;
            }
            int i11 = i + i10;
            if (iArr[i11] == i8) {
                i9++;
                iArr[i9] = i10;
                iArr[i11] = i9;
            }
            i3++;
        }
        int i12 = i6 - 1;
        int i13 = i9 + 1;
        while (i13 < i5) {
            int i14 = iArr[i12] - i7;
            if (i14 < 0) {
                i14 += i2 - i;
            }
            int i15 = i + i14;
            if (iArr[i15] == i8) {
                i5--;
                iArr[i5] = i14;
                iArr[i15] = i5;
            }
            i12--;
        }
    }

    private void trIntroSort(int i, int i2, int i3, int i4, int i5, TRBudget tRBudget, int i6) {
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int iTrGetC;
        int iTrGetC2;
        int i14;
        int i15;
        int i16;
        int[] iArr;
        int i17;
        int i18;
        int[] iArr2 = this.SA;
        StackEntry[] stackEntryArr = new StackEntry[64];
        int i19 = i2;
        int i20 = i4;
        i20 = i5;
        int iTrLog = trLog(i5 - i4);
        int i21 = 0;
        int i22 = 0;
        while (true) {
            if (iTrLog >= 0) {
                i7 = i21;
                int i23 = i20;
                i8 = 0;
                int i24 = i20;
                int i25 = i24 - i23;
                if (i25 > 8) {
                    int i26 = iTrLog - 1;
                    if (iTrLog == 0) {
                        if (!tRBudget.update(i6, i25)) {
                            break;
                        }
                        trHeapSort(i, i19, i3, i23, i25);
                        int i27 = i24 - 1;
                        while (i23 < i27) {
                            int i28 = i23;
                            int iTrGetC3 = trGetC(i, i19, i3, iArr2[i27]);
                            i27--;
                            while (i28 <= i27 && trGetC(i, i19, i3, iArr2[i27]) == iTrGetC3) {
                                iArr2[i27] = ~iArr2[i27];
                                i27--;
                            }
                            i22 = iTrGetC3;
                            i23 = i28;
                        }
                        i20 = i23;
                        i20 = i24;
                        i21 = i7;
                        iTrLog = -3;
                    } else {
                        int i29 = iTrLog;
                        int iTrPivot = trPivot(i, i19, i3, i23, i24);
                        i20 = i23;
                        swapElements(iArr2, i20, iArr2, iTrPivot);
                        int iTrGetC4 = trGetC(i, i19, i3, iArr2[i20]);
                        int i30 = i20 + 1;
                        while (true) {
                            if (i30 >= i24) {
                                iTrGetC = i22;
                                break;
                            }
                            iTrGetC = trGetC(i, i19, i3, iArr2[i30]);
                            if (iTrGetC != iTrGetC4) {
                                break;
                            }
                            i30++;
                            i22 = iTrGetC;
                        }
                        if (i30 >= i24 || iTrGetC >= iTrGetC4) {
                            iTrGetC2 = iTrGetC;
                            i14 = i30;
                        } else {
                            iTrGetC2 = iTrGetC;
                            int i31 = 1;
                            i14 = i30;
                            while (true) {
                                i30 += i31;
                                if (i30 >= i24 || (iTrGetC2 = trGetC(i, i19, i3, iArr2[i30])) > iTrGetC4) {
                                    break;
                                }
                                if (iTrGetC2 == iTrGetC4) {
                                    swapElements(iArr2, i30, iArr2, i14);
                                    i14++;
                                }
                                i31 = 1;
                            }
                        }
                        int i32 = i24 - 1;
                        while (i30 < i32) {
                            iTrGetC2 = trGetC(i, i19, i3, iArr2[i32]);
                            if (iTrGetC2 != iTrGetC4) {
                                break;
                            } else {
                                i32--;
                            }
                        }
                        if (i30 >= i32 || iTrGetC2 <= iTrGetC4) {
                            i15 = i14;
                            i22 = iTrGetC2;
                            i16 = i32;
                        } else {
                            int i33 = i14;
                            int i34 = i32;
                            while (true) {
                                i32--;
                                if (i30 >= i32 || (iTrGetC2 = trGetC(i, i19, i3, iArr2[i32])) < iTrGetC4) {
                                    break;
                                } else if (iTrGetC2 == iTrGetC4) {
                                    swapElements(iArr2, i32, iArr2, i34);
                                    i34--;
                                }
                            }
                            i22 = iTrGetC2;
                            i16 = i34;
                            i15 = i33;
                        }
                        while (i30 < i32) {
                            swapElements(iArr2, i30, iArr2, i32);
                            while (true) {
                                i30++;
                                int i35 = i32;
                                if (i30 >= i32) {
                                    break;
                                }
                                int iTrGetC5 = trGetC(i, i19, i3, iArr2[i30]);
                                if (iTrGetC5 > iTrGetC4) {
                                    i22 = iTrGetC5;
                                    i32 = i35;
                                    break;
                                } else {
                                    if (iTrGetC5 == iTrGetC4) {
                                        swapElements(iArr2, i30, iArr2, i15);
                                        i15++;
                                    }
                                    i22 = iTrGetC5;
                                    i32 = i35;
                                }
                            }
                            while (true) {
                                i32--;
                                int i36 = i30;
                                if (i30 >= i32) {
                                    break;
                                }
                                int iTrGetC6 = trGetC(i, i19, i3, iArr2[i32]);
                                if (iTrGetC6 < iTrGetC4) {
                                    i22 = iTrGetC6;
                                    i30 = i36;
                                    break;
                                } else {
                                    if (iTrGetC6 == iTrGetC4) {
                                        swapElements(iArr2, i32, iArr2, i16);
                                        i16--;
                                    }
                                    i22 = iTrGetC6;
                                    i30 = i36;
                                }
                            }
                        }
                        if (i15 <= i16) {
                            int i37 = i30 - 1;
                            int i38 = i15 - i20;
                            int i39 = i30 - i15;
                            if (i38 > i39) {
                                i38 = i39;
                            }
                            int i40 = i30;
                            int i41 = i30 - i38;
                            int i42 = i38;
                            int i43 = i20;
                            while (i42 > 0) {
                                swapElements(iArr2, i43, iArr2, i41);
                                i42--;
                                i43++;
                                i41++;
                            }
                            int i44 = i16 - i37;
                            int i45 = (i24 - i16) - 1;
                            if (i44 <= i45) {
                                i45 = i44;
                            }
                            int i46 = i45;
                            int i47 = i24 - i45;
                            int i48 = i40;
                            while (i46 > 0) {
                                swapElements(iArr2, i48, iArr2, i47);
                                i46--;
                                i48++;
                                i47++;
                            }
                            i20 += i39;
                            int i49 = i24 - i44;
                            iTrLog = iArr2[iArr2[i20] + i] != iTrGetC4 ? trLog(i49 - i20) : -1;
                            int i50 = i20 - 1;
                            for (int i51 = i20; i51 < i20; i51++) {
                                iArr2[iArr2[i51] + i] = i50;
                            }
                            if (i49 < i24) {
                                int i52 = i49 - 1;
                                for (int i53 = i20; i53 < i49; i53++) {
                                    iArr2[iArr2[i53] + i] = i52;
                                }
                            }
                            int i54 = i20 - i20;
                            int i55 = i24 - i49;
                            if (i54 <= i55) {
                                int i56 = i49 - i20;
                                if (i55 <= i56) {
                                    iArr = iArr2;
                                    if (1 < i54) {
                                        stackEntryArr[i7] = new StackEntry(i19 + 1, i20, i49, iTrLog);
                                        i17 = i26;
                                        stackEntryArr[i7 + 1] = new StackEntry(i19, i49, i24, i17);
                                        i21 = i7 + 2;
                                    } else {
                                        i17 = i26;
                                        if (1 < i55) {
                                            i21 = i7 + 1;
                                            stackEntryArr[i7] = new StackEntry(i19 + 1, i20, i49, iTrLog);
                                            i20 = i49;
                                            i20 = i24;
                                        } else if (1 < i56) {
                                            i19++;
                                            iTrLog = iTrLog;
                                            i21 = i7;
                                            iArr2 = iArr;
                                            i20 = i49;
                                        } else {
                                            if (i7 == 0) {
                                                return;
                                            }
                                            i21 = i7 - 1;
                                            StackEntry stackEntry = stackEntryArr[i21];
                                            i19 = stackEntry.a;
                                            i20 = stackEntry.b;
                                            i20 = stackEntry.c;
                                            iTrLog = stackEntry.d;
                                        }
                                    }
                                } else {
                                    iArr = iArr2;
                                    i17 = i26;
                                    if (i54 <= i56) {
                                        if (1 < i54) {
                                            stackEntryArr[i7] = new StackEntry(i19, i49, i24, i17);
                                            stackEntryArr[i7 + 1] = new StackEntry(i19 + 1, i20, i49, iTrLog);
                                            i21 = i7 + 2;
                                        } else if (1 < i56) {
                                            i21 = i7 + 1;
                                            stackEntryArr[i7] = new StackEntry(i19, i49, i24, i17);
                                            i19++;
                                            iArr2 = iArr;
                                            i20 = i49;
                                        } else {
                                            i20 = i49;
                                            i20 = i24;
                                            iTrLog = i17;
                                            i21 = i7;
                                        }
                                    } else if (1 < i56) {
                                        stackEntryArr[i7] = new StackEntry(i19, i49, i24, i17);
                                        i18 = i7 + 2;
                                        stackEntryArr[i7 + 1] = new StackEntry(i19, i20, i20, i17);
                                        i19++;
                                        i21 = i18;
                                        iArr2 = iArr;
                                        i20 = i49;
                                    } else {
                                        i21 = i7 + 1;
                                        stackEntryArr[i7] = new StackEntry(i19, i49, i24, i17);
                                    }
                                }
                                iTrLog = i17;
                            } else {
                                iArr = iArr2;
                                i17 = i26;
                                int i57 = i49 - i20;
                                if (i54 <= i57) {
                                    if (1 < i55) {
                                        stackEntryArr[i7] = new StackEntry(i19 + 1, i20, i49, iTrLog);
                                        stackEntryArr[i7 + 1] = new StackEntry(i19, i20, i20, i17);
                                        i21 = i7 + 2;
                                        i20 = i49;
                                    } else if (1 < i54) {
                                        i21 = i7 + 1;
                                        stackEntryArr[i7] = new StackEntry(i19 + 1, i20, i49, iTrLog);
                                        iTrLog = i17;
                                    } else if (1 < i57) {
                                        i19++;
                                        iTrLog = iTrLog;
                                        i21 = i7;
                                        iArr2 = iArr;
                                        i20 = i49;
                                    } else {
                                        i21 = i7 + 1;
                                        stackEntryArr[i7] = new StackEntry(i19, i20, i24, i17);
                                    }
                                } else if (i55 <= i57) {
                                    if (1 < i55) {
                                        stackEntryArr[i7] = new StackEntry(i19, i20, i20, i17);
                                        stackEntryArr[i7 + 1] = new StackEntry(i19 + 1, i20, i49, iTrLog);
                                        i20 = i49;
                                        i21 = i7 + 2;
                                    } else if (1 < i57) {
                                        i21 = i7 + 1;
                                        stackEntryArr[i7] = new StackEntry(i19, i20, i20, i17);
                                        i19++;
                                        iArr2 = iArr;
                                        i20 = i49;
                                    } else {
                                        iTrLog = i17;
                                        i21 = i7;
                                    }
                                } else if (1 < i57) {
                                    stackEntryArr[i7] = new StackEntry(i19, i20, i20, i17);
                                    i18 = i7 + 2;
                                    stackEntryArr[i7 + 1] = new StackEntry(i19, i49, i24, i17);
                                    i19++;
                                    i21 = i18;
                                    iArr2 = iArr;
                                    i20 = i49;
                                } else {
                                    i21 = i7 + 1;
                                    stackEntryArr[i7] = new StackEntry(i19, i20, i20, i17);
                                    i20 = i49;
                                }
                                i20 = i24;
                                iTrLog = i17;
                            }
                        } else {
                            iArr = iArr2;
                            if (!tRBudget.update(i6, i25)) {
                                break;
                            }
                            i19++;
                            i20 = i24;
                            i21 = i7;
                            iTrLog = i29;
                        }
                        iArr2 = iArr;
                    }
                } else {
                    if (!tRBudget.update(i6, i25)) {
                        break;
                    }
                    iTrLog = -3;
                    trInsertionSort(i, i19, i3, i23, i24);
                    i20 = i23;
                    i20 = i24;
                    i21 = i7;
                }
            } else if (iTrLog != -1) {
                int i58 = i21;
                i20 = i20;
                int i59 = i20;
                if (iTrLog == -2) {
                    int i60 = i58 - 1;
                    trCopy(i, i3, i20, stackEntryArr[i60].b, stackEntryArr[i60].c, i59, i19 - i);
                    if (i60 == 0) {
                        return;
                    }
                    i21 = i58 - 2;
                    StackEntry stackEntry2 = stackEntryArr[i21];
                    i19 = stackEntry2.a;
                    i20 = stackEntry2.b;
                    i20 = stackEntry2.c;
                    iTrLog = stackEntry2.d;
                } else {
                    if (iArr2[i20] >= 0) {
                        do {
                            iArr2[iArr2[i20] + i] = i20;
                            i20++;
                            if (i20 >= i59) {
                                break;
                            }
                        } while (iArr2[i20] >= 0);
                    }
                    if (i20 < i59) {
                        int i61 = i20;
                        while (true) {
                            iArr2[i61] = ~iArr2[i61];
                            i12 = i61 + 1;
                            i13 = iArr2[i12];
                            if (i13 >= 0) {
                                break;
                            } else {
                                i61 = i12;
                            }
                        }
                        iTrLog = iArr2[i + i13] != iArr2[i13 + i19] ? trLog((i12 - i20) + 1) : -1;
                        i20 = i61 + 2;
                        if (i20 < i59) {
                            int i62 = i61 + 1;
                            for (int i63 = i20; i63 < i20; i63++) {
                                iArr2[iArr2[i63] + i] = i62;
                            }
                        }
                        int i64 = i59 - i20;
                        if (i20 - i20 <= i64) {
                            i21 = i58 + 1;
                            stackEntryArr[i58] = new StackEntry(i19, i20, i59, -3);
                            i19++;
                        } else if (1 < i64) {
                            i21 = i58 + 1;
                            stackEntryArr[i58] = new StackEntry(i19 + 1, i20, i20, iTrLog);
                            iTrLog = -3;
                            i20 = i20;
                            i20 = i59;
                        } else {
                            i19++;
                            i21 = i58;
                        }
                    } else {
                        if (i58 == 0) {
                            return;
                        }
                        i21 = i58 - 1;
                        StackEntry stackEntry3 = stackEntryArr[i21];
                        i19 = stackEntry3.a;
                        i20 = stackEntry3.b;
                        i20 = stackEntry3.c;
                        iTrLog = stackEntry3.d;
                    }
                }
            } else {
                if (!tRBudget.update(i6, i20 - i20)) {
                    i7 = i21;
                    i8 = 0;
                    break;
                }
                int i65 = i19 - 1;
                int i66 = i21;
                int i67 = i20;
                int i68 = i20;
                PartitionResult partitionResultTrPartition = trPartition(i, i65, i3, i20, i67, i20 - 1);
                int i69 = partitionResultTrPartition.first;
                int i70 = partitionResultTrPartition.last;
                if (i68 < i69 || i70 < i67) {
                    if (i69 < i67) {
                        int i71 = i69 - 1;
                        for (int i72 = i68; i72 < i69; i72++) {
                            iArr2[iArr2[i72] + i] = i71;
                        }
                    }
                    if (i70 < i67) {
                        int i73 = i70 - 1;
                        for (int i74 = i69; i74 < i70; i74++) {
                            iArr2[iArr2[i74] + i] = i73;
                        }
                    }
                    stackEntryArr[i66] = new StackEntry(0, i69, i70, 0);
                    int i75 = i66 + 2;
                    stackEntryArr[i66 + 1] = new StackEntry(i65, i68, i67, -2);
                    int i76 = i69 - i68;
                    int i77 = i67 - i70;
                    if (i76 <= i77) {
                        if (1 < i76) {
                            stackEntryArr[i75] = new StackEntry(i19, i70, i67, trLog(i77));
                            int iTrLog2 = trLog(i76);
                            i20 = i69;
                            i21 = i66 + 3;
                            i20 = i68;
                            iTrLog = iTrLog2;
                        } else if (1 < i77) {
                            iTrLog = trLog(i77);
                            i21 = i75;
                            i20 = i67;
                            i20 = i70;
                        } else {
                            if (i75 == 0) {
                                return;
                            }
                            i21 = i66 + 1;
                            StackEntry stackEntry4 = stackEntryArr[i21];
                            i9 = stackEntry4.a;
                            i10 = stackEntry4.b;
                            i20 = stackEntry4.c;
                            i11 = stackEntry4.d;
                        }
                    } else if (1 < i77) {
                        stackEntryArr[i75] = new StackEntry(i19, i68, i69, trLog(i76));
                        int iTrLog3 = trLog(i77);
                        i20 = i67;
                        i21 = i66 + 3;
                        i20 = i70;
                        iTrLog = iTrLog3;
                    } else if (1 < i76) {
                        iTrLog = trLog(i76);
                        i20 = i69;
                        i21 = i75;
                        i20 = i68;
                    } else {
                        if (i75 == 0) {
                            return;
                        }
                        i21 = i66 + 1;
                        StackEntry stackEntry5 = stackEntryArr[i21];
                        i9 = stackEntry5.a;
                        i10 = stackEntry5.b;
                        i20 = stackEntry5.c;
                        i11 = stackEntry5.d;
                    }
                } else {
                    while (i68 < i67) {
                        iArr2[iArr2[i68] + i] = i68;
                        i68++;
                    }
                    if (i66 == 0) {
                        return;
                    }
                    i21 = i66 - 1;
                    StackEntry stackEntry6 = stackEntryArr[i21];
                    i9 = stackEntry6.a;
                    i10 = stackEntry6.b;
                    i20 = stackEntry6.c;
                    i11 = stackEntry6.d;
                }
                iTrLog = i11;
                i19 = i9;
                i20 = i10;
            }
        }
        for (int i78 = i8; i78 < i7; i78++) {
            if (stackEntryArr[i78].d == -3) {
                lsUpdateGroup(i, stackEntryArr[i78].b, stackEntryArr[i78].c);
            }
        }
    }

    private static class TRBudget {
        int budget;
        int chance;

        TRBudget(int i, int i2) {
            this.budget = i;
            this.chance = i2;
        }

        boolean update(int i, int i2) {
            int i3 = this.budget - i2;
            this.budget = i3;
            if (i3 <= 0) {
                int i4 = this.chance - 1;
                this.chance = i4;
                if (i4 == 0) {
                    return false;
                }
                this.budget = i3 + i;
            }
            return true;
        }
    }

    private void trSort(int i, int i2, int i3) {
        int[] iArr = this.SA;
        if ((-i2) < iArr[0]) {
            TRBudget tRBudget = new TRBudget(i2, ((trLog(i2) * 2) / 3) + 1);
            int i4 = 0;
            do {
                int i5 = iArr[i4];
                if (i5 < 0) {
                    i4 -= i5;
                } else {
                    int i6 = iArr[i + i5] + 1;
                    if (1 < i6 - i4) {
                        trIntroSort(i, i + i3, i + i2, i4, i6, tRBudget, i2);
                        if (tRBudget.chance == 0) {
                            if (i4 > 0) {
                                iArr[0] = -i4;
                            }
                            lsSort(i, i2, i3);
                            return;
                        }
                    }
                    i4 = i6;
                }
            } while (i4 < i2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:109:0x0213 A[LOOP:13: B:109:0x0213->B:167:?, LOOP_START, PHI: r13
      0x0213: PHI (r13v6 int) = (r13v5 int), (r13v7 int) binds: [B:108:0x0211, B:167:?] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:111:0x0217  */
    /* JADX WARN: Code duplicated, block: B:118:0x0230  */
    /* JADX WARN: Code duplicated, block: B:123:0x0240  */
    /* JADX WARN: Code duplicated, block: B:125:0x0243  */
    /* JADX WARN: Code duplicated, block: B:127:0x025d A[LOOP:17: B:126:0x025b->B:127:0x025d, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:131:0x027b  */
    /* JADX WARN: Code duplicated, block: B:158:0x0224 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:161:0x0211 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:162:0x0211 A[EDGE_INSN: B:162:0x0211->B:160:0x0211 BREAK  A[LOOP:14: B:116:0x022c->B:168:?], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:166:0x0222 A[EDGE_INSN: B:166:0x0222->B:114:0x0222 BREAK  A[LOOP:13: B:109:0x0213->B:167:?], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:171:0x0284 A[SYNTHETIC] */
    private int sortTypeBstar(int[] iArr, int[] iArr2) {
        boolean z;
        int i;
        boolean z2;
        int i2;
        int i3;
        int i4;
        int i5;
        int[] iArr3;
        int i6;
        int i7;
        byte b;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        byte[] bArr = this.T;
        int[] iArr4 = this.SA;
        int i16 = this.n;
        int[] iArr5 = new int[256];
        int i17 = 1;
        while (true) {
            z = false;
            i = 255;
            if (i17 < i16) {
                byte b2 = bArr[i17 - 1];
                byte b3 = bArr[i17];
                if (b2 != b3) {
                    if ((b2 & 255) > (b3 & 255)) {
                        z2 = false;
                        break;
                    }
                } else {
                    i17++;
                }
            }
            z2 = true;
            break;
        }
        int i18 = i16 - 1;
        byte b4 = bArr[i18];
        int i19 = b4 & 255;
        byte b5 = bArr[0];
        int i20 = b5 & 255;
        if (i19 >= i20 && (b4 != b5 || !z2)) {
            i2 = i16;
            i3 = i18;
            break;
        }
        if (!z2) {
            int iBUCKET_BSTAR = BUCKET_BSTAR(i19, i20);
            iArr2[iBUCKET_BSTAR] = iArr2[iBUCKET_BSTAR] + 1;
            i2 = i16 - 1;
            iArr4[i2] = i18;
        } else {
            int iBUCKET_B = BUCKET_B(i19, i20);
            iArr2[iBUCKET_B] = iArr2[iBUCKET_B] + 1;
            i2 = i16;
        }
        i3 = i16 - 2;
        while (i3 >= 0) {
            int i21 = bArr[i3] & 255;
            int i22 = bArr[i3 + 1] & 255;
            if (i21 > i22) {
                break;
            }
            int iBUCKET_B2 = BUCKET_B(i21, i22);
            iArr2[iBUCKET_B2] = iArr2[iBUCKET_B2] + 1;
            i3--;
        }
        while (i3 >= 0) {
            while (true) {
                int i23 = bArr[i3] & 255;
                iArr[i23] = iArr[i23] + 1;
                i15 = i3 - 1;
                if (i15 < 0 || (bArr[i15] & 255) < (bArr[i3] & 255)) {
                    break;
                }
                i3 = i15;
            }
            if (i15 >= 0) {
                int iBUCKET_BSTAR2 = BUCKET_BSTAR(bArr[i15] & 255, bArr[i3] & 255);
                iArr2[iBUCKET_BSTAR2] = iArr2[iBUCKET_BSTAR2] + 1;
                i2--;
                iArr4[i2] = i15;
                i3 -= 2;
                while (i3 >= 0) {
                    int i24 = bArr[i3] & 255;
                    int i25 = bArr[i3 + 1] & 255;
                    if (i24 > i25) {
                        break;
                    }
                    int iBUCKET_B3 = BUCKET_B(i24, i25);
                    iArr2[iBUCKET_B3] = iArr2[iBUCKET_B3] + 1;
                    i3--;
                }
            } else {
                i3 = i15;
            }
        }
        int i26 = i16 - i2;
        if (i26 == 0) {
            for (int i27 = 0; i27 < i16; i27++) {
                iArr4[i27] = i27;
            }
            return 0;
        }
        int i28 = -1;
        int i29 = 0;
        int i30 = 0;
        while (i29 < 256) {
            int i31 = iArr[i29] + i28;
            iArr[i29] = i28 + i30;
            int i32 = i31 + iArr2[BUCKET_B(i29, i29)];
            int i33 = i29 + 1;
            for (int i34 = i33; i34 < 256; i34++) {
                i30 += iArr2[BUCKET_BSTAR(i29, i34)];
                iArr2[(i29 << 8) | i34] = i30;
                i32 += iArr2[BUCKET_B(i29, i34)];
            }
            i29 = i33;
            i28 = i32;
        }
        int i35 = i16 - i26;
        for (int i36 = i26 - 2; i36 >= 0; i36--) {
            int i37 = iArr4[i35 + i36];
            int iBUCKET_BSTAR3 = BUCKET_BSTAR(bArr[i37] & 255, bArr[i37 + 1] & 255);
            int i38 = iArr2[iBUCKET_BSTAR3] - 1;
            iArr2[iBUCKET_BSTAR3] = i38;
            iArr4[i38] = i36;
        }
        int i39 = iArr4[(i35 + i26) - 1];
        int iBUCKET_BSTAR4 = BUCKET_BSTAR(bArr[i39] & 255, bArr[i39 + 1] & 255);
        int i40 = iArr2[iBUCKET_BSTAR4] - 1;
        iArr2[iBUCKET_BSTAR4] = i40;
        int i41 = i26 - 1;
        iArr4[i40] = i41;
        int i42 = i16 - (i26 * 2);
        if (i42 <= 256) {
            i4 = 256;
            iArr3 = iArr5;
            i5 = 0;
        } else {
            i4 = i42;
            i5 = i26;
            iArr3 = iArr4;
        }
        int i43 = i26;
        int i44 = 255;
        while (i43 > 0) {
            int i45 = i43;
            int i46 = i;
            while (i44 < i46) {
                int i47 = iArr2[BUCKET_BSTAR(i44, i46)];
                if (1 < i45 - i47) {
                    subStringSort(i35, i47, i45, iArr3, i5, i4, 2, iArr4[i47] == i41 ? true : z, i16);
                }
                i46--;
                i26 = i26;
                i45 = i47;
                i44 = i44;
                i41 = i41;
                z = false;
            }
            i44--;
            i43 = i45;
            i = 255;
            z = false;
        }
        int i48 = i41;
        int i49 = i26;
        while (i41 >= 0) {
            if (iArr4[i41] >= 0) {
                int i50 = i41;
                while (true) {
                    iArr4[i49 + iArr4[i50]] = i50;
                    i14 = i50 - 1;
                    if (i14 < 0 || iArr4[i14] < 0) {
                        break;
                    }
                    i50 = i14;
                }
                iArr4[i50] = i14 - i41;
                if (i14 <= 0) {
                    break;
                }
                i41 = i14;
            }
            int i51 = i41;
            while (true) {
                int i52 = ~iArr4[i51];
                iArr4[i51] = i52;
                iArr4[i49 + i52] = i41;
                int i53 = i51 - 1;
                i13 = iArr4[i53];
                if (i13 >= 0) {
                    break;
                }
                i51 = i53;
            }
            iArr4[i49 + i13] = i41;
            i41 = i51 - 2;
        }
        trSort(i49, i49, 1);
        byte b6 = bArr[i18];
        int i54 = b6 & 255;
        byte b7 = bArr[0];
        if (i54 < (b7 & 255) || (b6 == b7 && z2)) {
            if (z2) {
                i6 = i49;
            } else {
                i6 = i49 - 1;
                iArr4[iArr4[i49 + i6]] = i18;
            }
            i7 = i16 - 2;
            while (true) {
                if (i7 >= 0) {
                    b = 255;
                    if ((bArr[i7] & 255) > (bArr[i7 + 1] & 255)) {
                        break;
                    }
                    i7--;
                }
            }
            while (i7 >= 0) {
                do {
                    i7--;
                    if (i7 >= 0) {
                        break;
                    }
                } while ((bArr[i7] & b) >= (bArr[i7 + 1] & b));
                if (i7 >= 0) {
                    i6--;
                    iArr4[iArr4[i49 + i6]] = i7;
                    do {
                        i7--;
                        if (i7 >= 0) {
                            break;
                        }
                    } while ((bArr[i7] & b) <= (bArr[i7 + 1] & b));
                }
            }
            i9 = i48;
            for (i8 = b; i8 >= 0; i8--) {
                for (i10 = b; i8 < i10; i10--) {
                    int i55 = i18 - iArr2[BUCKET_B(i8, i10)];
                    iArr2[BUCKET_B(i8, i10)] = i18 + 1;
                    i12 = iArr2[BUCKET_BSTAR(i8, i10)];
                    i18 = i55;
                    while (i12 <= i9) {
                        iArr4[i18] = iArr4[i9];
                        i18--;
                        i9--;
                    }
                }
                i11 = i18 - iArr2[BUCKET_B(i8, i8)];
                iArr2[BUCKET_B(i8, i8)] = i18 + 1;
                if (i8 < b) {
                    iArr2[BUCKET_BSTAR(i8, i8 + 1)] = i11 + 1;
                }
                i18 = iArr[i8];
            }
            return i49;
        }
        i6 = i49;
        i7 = i18;
        b = 255;
        while (i7 >= 0) {
            do {
                i7--;
                if (i7 >= 0) {
                    break;
                    break;
                }
            } while ((bArr[i7] & b) >= (bArr[i7 + 1] & b));
            if (i7 >= 0) {
                i6--;
                iArr4[iArr4[i49 + i6]] = i7;
                do {
                    i7--;
                    if (i7 >= 0) {
                        break;
                        break;
                    }
                } while ((bArr[i7] & b) <= (bArr[i7 + 1] & b));
            }
        }
        i9 = i48;
        while (i8 >= 0) {
            while (i8 < i10) {
                int i56 = i18 - iArr2[BUCKET_B(i8, i10)];
                iArr2[BUCKET_B(i8, i10)] = i18 + 1;
                i12 = iArr2[BUCKET_BSTAR(i8, i10)];
                i18 = i56;
                while (i12 <= i9) {
                    iArr4[i18] = iArr4[i9];
                    i18--;
                    i9--;
                }
            }
            i11 = i18 - iArr2[BUCKET_B(i8, i8)];
            iArr2[BUCKET_B(i8, i8)] = i18 + 1;
            if (i8 < b) {
                iArr2[BUCKET_BSTAR(i8, i8 + 1)] = i11 + 1;
            }
            i18 = iArr[i8];
        }
        return i49;
    }

    private int constructBWT(int[] iArr, int[] iArr2) {
        byte[] bArr = this.T;
        int[] iArr3 = this.SA;
        int i = this.n;
        int i2 = 254;
        int i3 = 0;
        int i4 = 0;
        while (i2 >= 0) {
            int i5 = i2 + 1;
            int i6 = iArr2[BUCKET_BSTAR(i2, i5)];
            int i7 = -1;
            int i8 = 0;
            for (int i9 = iArr[i5]; i6 <= i9; i9--) {
                int i10 = iArr3[i9];
                if (i10 >= 0) {
                    int i11 = i10 - 1;
                    if (i11 < 0) {
                        i11 = i - 1;
                    }
                    int i12 = bArr[i11] & 255;
                    if (i12 <= i2) {
                        iArr3[i9] = ~i10;
                        if (i11 > 0 && (bArr[i11 - 1] & 255) > i12) {
                            i11 = ~i11;
                        }
                        if (i7 == i12) {
                            i8--;
                            iArr3[i8] = i11;
                        } else {
                            if (i7 >= 0) {
                                iArr2[BUCKET_B(i7, i2)] = i8;
                            }
                            i8 = iArr2[BUCKET_B(i12, i2)] - 1;
                            iArr3[i8] = i11;
                            i7 = i12;
                        }
                    }
                } else {
                    iArr3[i9] = ~i10;
                }
            }
            i2--;
            i3 = i8;
            i4 = i7;
        }
        int i13 = -1;
        for (int i14 = 0; i14 < i; i14++) {
            int i15 = iArr3[i14];
            if (i15 >= 0) {
                int i16 = i15 - 1;
                if (i16 < 0) {
                    i16 = i - 1;
                }
                int i17 = bArr[i16] & 255;
                if (i17 >= (bArr[i16 + 1] & 255)) {
                    if (i16 > 0 && (bArr[i16 - 1] & 255) < i17) {
                        i16 = ~i16;
                    }
                    if (i17 == i4) {
                        i3++;
                        iArr3[i3] = i16;
                    } else {
                        if (i4 != -1) {
                            iArr[i4] = i3;
                        }
                        i3 = iArr[i17] + 1;
                        iArr3[i3] = i16;
                        i4 = i17;
                    }
                }
            } else {
                i15 = ~i15;
            }
            if (i15 == 0) {
                iArr3[i14] = bArr[i - 1];
                i13 = i14;
            } else {
                iArr3[i14] = bArr[i15 - 1];
            }
        }
        return i13;
    }

    public int bwt() {
        int[] iArr = this.SA;
        byte[] bArr = this.T;
        int i = this.n;
        int[] iArr2 = new int[256];
        int[] iArr3 = new int[65536];
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            iArr[0] = bArr[0];
            return 0;
        }
        if (sortTypeBstar(iArr2, iArr3) > 0) {
            return constructBWT(iArr2, iArr3);
        }
        return 0;
    }
}
