package io.netty.handler.codec.compression;

import androidx.mediarouter.media.GlobalMediaRouter;

/* JADX INFO: loaded from: classes11.dex */
final class FastLz {
    static final byte BLOCK_TYPE_COMPRESSED = 1;
    static final byte BLOCK_TYPE_NON_COMPRESSED = 0;
    static final byte BLOCK_WITHOUT_CHECKSUM = 0;
    static final byte BLOCK_WITH_CHECKSUM = 16;
    static final int CHECKSUM_OFFSET = 4;
    private static final int HASH_LOG = 13;
    private static final int HASH_MASK = 8191;
    private static final int HASH_SIZE = 8192;
    static final int LEVEL_1 = 1;
    static final int LEVEL_2 = 2;
    static final int LEVEL_AUTO = 0;
    static final int MAGIC_NUMBER = 4607066;
    static final int MAX_CHUNK_LENGTH = 65535;
    private static final int MAX_COPY = 32;
    private static final int MAX_DISTANCE = 8191;
    private static final int MAX_FARDISTANCE = 73725;
    private static final int MAX_LEN = 264;
    static final int MIN_LENGTH_TO_COMPRESSION = 32;
    private static final int MIN_RECOMENDED_LENGTH_FOR_LEVEL_2 = 65536;
    static final int OPTIONS_OFFSET = 3;

    static int calculateOutputBufferLength(int i) {
        return Math.max((int) (((double) i) * 1.06d), 66);
    }

    /* JADX WARN: Code duplicated, block: B:102:0x01e2 A[ADDED_TO_REGION, LOOP:7: B:102:0x01e2->B:106:0x01f4, LOOP_START, PHI: r7 r10
      0x01e2: PHI (r7v6 int) = (r7v5 int), (r7v10 int) binds: [B:100:0x01df, B:106:0x01f4] A[DONT_GENERATE, DONT_INLINE]
      0x01e2: PHI (r10v6 int) = (r10v5 int), (r10v8 int) binds: [B:100:0x01df, B:106:0x01f4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:103:0x01e4  */
    /* JADX WARN: Code duplicated, block: B:106:0x01f4 A[LOOP:7: B:102:0x01e2->B:106:0x01f4, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:108:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:109:0x0204  */
    /* JADX WARN: Code duplicated, block: B:112:0x0210  */
    /* JADX WARN: Code duplicated, block: B:114:0x0215 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:115:0x0217  */
    /* JADX WARN: Code duplicated, block: B:116:0x0233  */
    /* JADX WARN: Code duplicated, block: B:119:0x0246 A[LOOP:4: B:117:0x0242->B:119:0x0246, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:121:0x0264  */
    /* JADX WARN: Code duplicated, block: B:123:0x0268  */
    /* JADX WARN: Code duplicated, block: B:124:0x0294  */
    /* JADX WARN: Code duplicated, block: B:127:0x02a2 A[LOOP:5: B:125:0x029e->B:127:0x02a2, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:129:0x02d0  */
    /* JADX WARN: Code duplicated, block: B:131:0x02d4 A[ADDED_TO_REGION, LOOP:6: B:131:0x02d4->B:132:0x02d6, LOOP_START, PHI: r9 r10 r12 r13
      0x02d4: PHI (r9v31 int) = (r9v22 int), (r9v32 int) binds: [B:130:0x02d2, B:132:0x02d6] A[DONT_GENERATE, DONT_INLINE]
      0x02d4: PHI (r10v17 int) = (r10v10 int), (r10v23 int) binds: [B:130:0x02d2, B:132:0x02d6] A[DONT_GENERATE, DONT_INLINE]
      0x02d4: PHI (r12v13 int) = (r12v9 int), (r12v14 int) binds: [B:130:0x02d2, B:132:0x02d6] A[DONT_GENERATE, DONT_INLINE]
      0x02d4: PHI (r13v15 int) = (r13v7 int), (r13v19 int) binds: [B:130:0x02d2, B:132:0x02d6] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:132:0x02d6 A[LOOP:6: B:131:0x02d4->B:132:0x02d6, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:135:0x0302  */
    /* JADX WARN: Code duplicated, block: B:136:0x031c  */
    /* JADX WARN: Code duplicated, block: B:157:0x0116 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:158:0x0116 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:159:0x0116 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:175:0x0160 A[EDGE_INSN: B:175:0x0160->B:77:0x0160 BREAK  A[LOOP:3: B:72:0x0151->B:76:0x015c], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:180:0x01f2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:0x007e  */
    /* JADX WARN: Code duplicated, block: B:42:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:44:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:46:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:49:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:71:0x014b  */
    /* JADX WARN: Code duplicated, block: B:73:0x0153  */
    /* JADX WARN: Code duplicated, block: B:76:0x015c A[LOOP:3: B:72:0x0151->B:76:0x015c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:78:0x0164  */
    /* JADX WARN: Code duplicated, block: B:80:0x0174 A[PHI: r15
      0x0174: PHI (r15v12 int) = (r15v2 int), (r15v5 int), (r15v8 int), (r15v11 int) binds: [B:79:0x0172, B:85:0x0194, B:91:0x01b2, B:97:0x01d0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:81:0x0177  */
    /* JADX WARN: Code duplicated, block: B:83:0x0185 A[PHI: r14
      0x0185: PHI (r14v11 int) = (r14v2 int), (r14v5 int), (r14v8 int) binds: [B:82:0x0183, B:88:0x01a3, B:94:0x01c1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:84:0x0188  */
    /* JADX WARN: Code duplicated, block: B:87:0x0197  */
    /* JADX WARN: Code duplicated, block: B:90:0x01a6  */
    /* JADX WARN: Code duplicated, block: B:93:0x01b5  */
    /* JADX WARN: Code duplicated, block: B:96:0x01c4  */
    /* JADX WARN: Code duplicated, block: B:99:0x01d3  */
    static int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int i5;
        long j;
        int i6;
        int i7;
        int i8;
        int i9;
        long j2;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        byte b;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        byte b2;
        int i31;
        int i32;
        int i33;
        char c;
        int i34;
        int i35;
        int i36;
        int i37;
        int i38;
        int i39 = 2;
        int i40 = 1;
        int i41 = i4 == 0 ? i2 < 65536 ? 1 : 2 : i4;
        int i42 = i2 - 2;
        int i43 = i2 - 12;
        int[] iArr = new int[8192];
        if (i2 < 4) {
            if (i2 == 0) {
                return 0;
            }
            bArr2[i3] = (byte) (i2 - 1);
            int i44 = i2 - 1;
            int i45 = 1;
            for (int i46 = 0; i46 <= i44; i46++) {
                bArr2[i3 + i45] = bArr[i + i46];
                i45++;
            }
            return i2 + 1;
        }
        for (int i47 = 0; i47 < 8192; i47++) {
            iArr[i47] = 0;
        }
        bArr2[i3] = 31;
        bArr2[i3 + 1] = bArr[i];
        bArr2[i3 + 2] = bArr[i + 1];
        int i48 = 2;
        int i49 = 2;
        int i50 = 3;
        while (i48 < i43) {
            if (i41 == i39) {
                int i51 = i + i48;
                int i52 = i51 - 1;
                if (bArr[i51] == bArr[i52] && readU16(bArr, i52) == readU16(bArr, i51 + 1)) {
                    i5 = i48 + 3;
                    i6 = i48 + 2;
                    i7 = i40;
                    j = 1;
                } else {
                    i5 = i48;
                    j = 0;
                    i6 = 0;
                    i7 = 0;
                }
            } else {
                i5 = i48;
                j = 0;
                i6 = 0;
                i7 = 0;
            }
            if (i7 == 0) {
                int i53 = i + i5;
                int iHashFunction = hashFunction(bArr, i53);
                int i54 = iArr[iHashFunction];
                long j3 = i48 - i54;
                iArr[iHashFunction] = i48;
                if (j3 != 0) {
                    if (i41 == 1) {
                        if (j3 < 8191) {
                            i34 = i54 + 1;
                            i35 = i5 + 1;
                            if (bArr[i + i54] == bArr[i53]) {
                                i36 = i54 + 2;
                                i37 = i5 + 2;
                                if (bArr[i + i34] == bArr[i + i35]) {
                                    i38 = i54 + 3;
                                    int i55 = i5 + 3;
                                    if (bArr[i + i36] != bArr[i + i37]) {
                                        if (i41 == 2 || j3 < 8191) {
                                            j = j3;
                                            i6 = i38;
                                        } else {
                                            int i56 = i5 + 4;
                                            byte b3 = bArr[i + i55];
                                            int i57 = i54 + 4;
                                            if (b3 == bArr[i + i38]) {
                                                i6 = i54 + 5;
                                                if (bArr[i + i56] == bArr[i + i57]) {
                                                    i8 = 5;
                                                    j = j3;
                                                }
                                            }
                                            i32 = i50 + 1;
                                            i33 = i48 + 1;
                                            bArr2[i3 + i50] = bArr[i + i48];
                                            i49++;
                                            if (i49 == 32) {
                                                i50 += 2;
                                                c = 31;
                                                bArr2[i3 + i32] = 31;
                                                i48 = i33;
                                                i39 = 2;
                                                i49 = 0;
                                                i40 = 1;
                                            } else {
                                                i50 = i32;
                                                i48 = i33;
                                                i39 = 2;
                                                i40 = 1;
                                            }
                                        }
                                        i9 = i8 + i48;
                                        j2 = j - 1;
                                        if (j2 == 0) {
                                            b2 = bArr[(i + i9) - 1];
                                            while (i9 < i42) {
                                                i31 = i6 + 1;
                                                if (bArr[i + i6] != b2) {
                                                    break;
                                                }
                                                i9++;
                                                i6 = i31;
                                            }
                                            i12 = i43;
                                        } else {
                                            i10 = i6 + 1;
                                            i11 = i9 + 1;
                                            i12 = i43;
                                            if (bArr[i + i6] != bArr[i + i9]) {
                                                i9 = i11;
                                            } else {
                                                i13 = i6 + 2;
                                                i14 = i9 + 2;
                                                if (bArr[i + i10] != bArr[i + i11]) {
                                                    i9 = i14;
                                                } else {
                                                    i15 = i6 + 3;
                                                    i11 = i9 + 3;
                                                    if (bArr[i + i13] != bArr[i + i14]) {
                                                        i9 = i11;
                                                    } else {
                                                        i16 = i6 + 4;
                                                        i14 = i9 + 4;
                                                        if (bArr[i + i15] != bArr[i + i11]) {
                                                            i9 = i14;
                                                        } else {
                                                            i17 = i6 + 5;
                                                            i11 = i9 + 5;
                                                            if (bArr[i + i16] != bArr[i + i14]) {
                                                                i9 = i11;
                                                            } else {
                                                                i18 = i6 + 6;
                                                                i14 = i9 + 6;
                                                                if (bArr[i + i17] != bArr[i + i11]) {
                                                                    i9 = i14;
                                                                } else {
                                                                    i19 = i6 + 7;
                                                                    i11 = i9 + 7;
                                                                    if (bArr[i + i18] != bArr[i + i14]) {
                                                                        i9 = i11;
                                                                    } else {
                                                                        i20 = i6 + 8;
                                                                        i9 += 8;
                                                                        if (bArr[i + i19] == bArr[i + i11]) {
                                                                            while (i9 < i42) {
                                                                                i21 = i20 + 1;
                                                                                b = bArr[i + i20];
                                                                                i22 = i9 + 1;
                                                                                if (b != bArr[i + i9]) {
                                                                                    i9 = i22;
                                                                                    break;
                                                                                }
                                                                                i9 = i22;
                                                                                i20 = i21;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (i49 != 0) {
                                            bArr2[((i3 + i50) - i49) - 1] = (byte) (i49 - 1);
                                        } else {
                                            i50--;
                                        }
                                        int i58 = i9 - 3;
                                        i23 = i58 - i48;
                                        i24 = 7;
                                        if (i41 == 2) {
                                            i25 = GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED;
                                            if (i23 > 262) {
                                                while (i23 > i25) {
                                                    bArr2[i3 + i50] = (byte) ((j2 >>> 8) + 224);
                                                    int i59 = i50 + 2;
                                                    bArr2[i3 + i50 + 1] = -3;
                                                    i50 += 3;
                                                    bArr2[i3 + i59] = (byte) (j2 & 255);
                                                    i23 -= 262;
                                                    i25 = GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED;
                                                    i24 = 7;
                                                }
                                            }
                                            if (i23 < i24) {
                                                int i60 = i50 + 1;
                                                bArr2[i3 + i50] = (byte) (((long) (i23 << 5)) + (j2 >>> 8));
                                                i26 = i50 + 2;
                                                bArr2[i3 + i60] = (byte) (j2 & 255);
                                            } else {
                                                bArr2[i3 + i50] = (byte) ((j2 >>> 8) + 224);
                                                int i61 = i50 + 2;
                                                bArr2[i3 + i50 + 1] = (byte) (i23 - 7);
                                                i26 = i50 + 3;
                                                bArr2[i3 + i61] = (byte) (j2 & 255);
                                            }
                                        } else if (j2 < 8191) {
                                            if (i23 < 7) {
                                                int i62 = i50 + 1;
                                                bArr2[i3 + i50] = (byte) (((long) (i23 << 5)) + (j2 >>> 8));
                                                i26 = i50 + 2;
                                                bArr2[i3 + i62] = (byte) (j2 & 255);
                                            } else {
                                                i29 = i50 + 1;
                                                bArr2[i3 + i50] = (byte) ((j2 >>> 8) + 224);
                                                i30 = i23 - 7;
                                                while (i30 >= 255) {
                                                    bArr2[i3 + i29] = -1;
                                                    i30 -= 255;
                                                    i29++;
                                                }
                                                bArr2[i3 + i29] = (byte) i30;
                                                i26 = i29 + 2;
                                                bArr2[i3 + i29 + 1] = (byte) (j2 & 255);
                                            }
                                        } else if (i23 < 7) {
                                            long j4 = j - 8192;
                                            bArr2[i3 + i50] = (byte) ((i23 << 5) + 31);
                                            bArr2[i3 + i50 + 1] = -1;
                                            int i63 = i50 + 3;
                                            bArr2[i3 + i50 + 2] = (byte) (j4 >>> 8);
                                            i26 = i50 + 4;
                                            bArr2[i3 + i63] = (byte) (j4 & 255);
                                        } else {
                                            long j5 = j - 8192;
                                            i27 = i50 + 1;
                                            bArr2[i3 + i50] = -1;
                                            i28 = i23 - 7;
                                            while (i28 >= 255) {
                                                bArr2[i3 + i27] = -1;
                                                i28 -= 255;
                                                i27++;
                                            }
                                            bArr2[i3 + i27] = (byte) i28;
                                            bArr2[i3 + i27 + 1] = -1;
                                            bArr2[i3 + i27 + 2] = (byte) (j5 >>> 8);
                                            i26 = i27 + 4;
                                            bArr2[i3 + i27 + 3] = (byte) (j5 & 255);
                                        }
                                        int i64 = i9 - 2;
                                        iArr[hashFunction(bArr, i + i58)] = i58;
                                        i48 = i9 - 1;
                                        iArr[hashFunction(bArr, i + i64)] = i64;
                                        bArr2[i3 + i26] = 31;
                                        i50 = i26 + 1;
                                        i43 = i12;
                                        i39 = 2;
                                        i40 = 1;
                                        i49 = 0;
                                    }
                                }
                            }
                        }
                    } else if (j3 < 73725) {
                        i34 = i54 + 1;
                        i35 = i5 + 1;
                        if (bArr[i + i54] == bArr[i53]) {
                            i36 = i54 + 2;
                            i37 = i5 + 2;
                            if (bArr[i + i34] == bArr[i + i35]) {
                                i38 = i54 + 3;
                                int i510 = i5 + 3;
                                if (bArr[i + i36] != bArr[i + i37]) {
                                    if (i41 == 2) {
                                    }
                                    j = j3;
                                    i6 = i38;
                                }
                            }
                        }
                    }
                }
                i32 = i50 + 1;
                i33 = i48 + 1;
                bArr2[i3 + i50] = bArr[i + i48];
                i49++;
                if (i49 == 32) {
                    i50 += 2;
                    c = 31;
                    bArr2[i3 + i32] = 31;
                    i48 = i33;
                    i39 = 2;
                    i49 = 0;
                    i40 = 1;
                } else {
                    i50 = i32;
                    i48 = i33;
                    i39 = 2;
                    i40 = 1;
                }
            }
            i8 = 3;
            i9 = i8 + i48;
            j2 = j - 1;
            if (j2 == 0) {
                b2 = bArr[(i + i9) - 1];
                while (i9 < i42) {
                    i31 = i6 + 1;
                    if (bArr[i + i6] != b2) {
                        break;
                        break;
                    }
                    i9++;
                    i6 = i31;
                }
                i12 = i43;
            } else {
                i10 = i6 + 1;
                i11 = i9 + 1;
                i12 = i43;
                if (bArr[i + i6] != bArr[i + i9]) {
                    i9 = i11;
                } else {
                    i13 = i6 + 2;
                    i14 = i9 + 2;
                    if (bArr[i + i10] != bArr[i + i11]) {
                        i9 = i14;
                    } else {
                        i15 = i6 + 3;
                        i11 = i9 + 3;
                        if (bArr[i + i13] != bArr[i + i14]) {
                            i9 = i11;
                        } else {
                            i16 = i6 + 4;
                            i14 = i9 + 4;
                            if (bArr[i + i15] != bArr[i + i11]) {
                                i9 = i14;
                            } else {
                                i17 = i6 + 5;
                                i11 = i9 + 5;
                                if (bArr[i + i16] != bArr[i + i14]) {
                                    i9 = i11;
                                } else {
                                    i18 = i6 + 6;
                                    i14 = i9 + 6;
                                    if (bArr[i + i17] != bArr[i + i11]) {
                                        i9 = i14;
                                    } else {
                                        i19 = i6 + 7;
                                        i11 = i9 + 7;
                                        if (bArr[i + i18] != bArr[i + i14]) {
                                            i9 = i11;
                                        } else {
                                            i20 = i6 + 8;
                                            i9 += 8;
                                            if (bArr[i + i19] == bArr[i + i11]) {
                                                while (i9 < i42) {
                                                    i21 = i20 + 1;
                                                    b = bArr[i + i20];
                                                    i22 = i9 + 1;
                                                    if (b != bArr[i + i9]) {
                                                        i9 = i22;
                                                        break;
                                                    }
                                                    i9 = i22;
                                                    i20 = i21;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (i49 != 0) {
                bArr2[((i3 + i50) - i49) - 1] = (byte) (i49 - 1);
            } else {
                i50--;
            }
            int i511 = i9 - 3;
            i23 = i511 - i48;
            i24 = 7;
            if (i41 == 2) {
                i25 = GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED;
                if (i23 > 262) {
                    while (i23 > i25) {
                        bArr2[i3 + i50] = (byte) ((j2 >>> 8) + 224);
                        int i512 = i50 + 2;
                        bArr2[i3 + i50 + 1] = -3;
                        i50 += 3;
                        bArr2[i3 + i512] = (byte) (j2 & 255);
                        i23 -= 262;
                        i25 = GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED;
                        i24 = 7;
                    }
                }
                if (i23 < i24) {
                    int i65 = i50 + 1;
                    bArr2[i3 + i50] = (byte) (((long) (i23 << 5)) + (j2 >>> 8));
                    i26 = i50 + 2;
                    bArr2[i3 + i65] = (byte) (j2 & 255);
                } else {
                    bArr2[i3 + i50] = (byte) ((j2 >>> 8) + 224);
                    int i66 = i50 + 2;
                    bArr2[i3 + i50 + 1] = (byte) (i23 - 7);
                    i26 = i50 + 3;
                    bArr2[i3 + i66] = (byte) (j2 & 255);
                }
            } else if (j2 < 8191) {
                if (i23 < 7) {
                    int i67 = i50 + 1;
                    bArr2[i3 + i50] = (byte) (((long) (i23 << 5)) + (j2 >>> 8));
                    i26 = i50 + 2;
                    bArr2[i3 + i67] = (byte) (j2 & 255);
                } else {
                    i29 = i50 + 1;
                    bArr2[i3 + i50] = (byte) ((j2 >>> 8) + 224);
                    i30 = i23 - 7;
                    while (i30 >= 255) {
                        bArr2[i3 + i29] = -1;
                        i30 -= 255;
                        i29++;
                    }
                    bArr2[i3 + i29] = (byte) i30;
                    i26 = i29 + 2;
                    bArr2[i3 + i29 + 1] = (byte) (j2 & 255);
                }
            } else if (i23 < 7) {
                long j6 = j - 8192;
                bArr2[i3 + i50] = (byte) ((i23 << 5) + 31);
                bArr2[i3 + i50 + 1] = -1;
                int i68 = i50 + 3;
                bArr2[i3 + i50 + 2] = (byte) (j6 >>> 8);
                i26 = i50 + 4;
                bArr2[i3 + i68] = (byte) (j6 & 255);
            } else {
                long j7 = j - 8192;
                i27 = i50 + 1;
                bArr2[i3 + i50] = -1;
                i28 = i23 - 7;
                while (i28 >= 255) {
                    bArr2[i3 + i27] = -1;
                    i28 -= 255;
                    i27++;
                }
                bArr2[i3 + i27] = (byte) i28;
                bArr2[i3 + i27 + 1] = -1;
                bArr2[i3 + i27 + 2] = (byte) (j7 >>> 8);
                i26 = i27 + 4;
                bArr2[i3 + i27 + 3] = (byte) (j7 & 255);
            }
            int i69 = i9 - 2;
            iArr[hashFunction(bArr, i + i511)] = i511;
            i48 = i9 - 1;
            iArr[hashFunction(bArr, i + i69)] = i69;
            bArr2[i3 + i26] = 31;
            i50 = i26 + 1;
            i43 = i12;
            i39 = 2;
            i40 = 1;
            i49 = 0;
        }
        int i70 = i2 - i40;
        while (i48 <= i70) {
            int i71 = i50 + 1;
            i48++;
            bArr2[i3 + i50] = bArr[i + i48];
            i49++;
            if (i49 == 32) {
                i50 += 2;
                bArr2[i3 + i71] = 31;
                i49 = 0;
            } else {
                i50 = i71;
            }
        }
        if (i49 != 0) {
            bArr2[((i3 + i50) - i49) - 1] = (byte) (i49 - 1);
        } else {
            i50--;
        }
        if (i41 == 2) {
            bArr2[i3] = (byte) (bArr2[i3] | 32);
        }
        return i50;
    }

    static int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        boolean z;
        int i5;
        long j;
        byte b;
        int i6;
        int i7;
        int i8;
        char c = 5;
        boolean z2 = true;
        int i9 = (bArr[i] >> 5) + 1;
        if (i9 != 1 && i9 != 2) {
            throw new DecompressionException(String.format("invalid level: %d (expected: %d or %d)", Integer.valueOf(i9), 1, 2));
        }
        long j2 = bArr[i] & 31;
        int i10 = 1;
        boolean z3 = true;
        int i11 = 0;
        while (true) {
            long j3 = j2 >> c;
            long j4 = (31 & j2) << 8;
            if (j2 >= 32) {
                long j5 = j3 - 1;
                long j6 = i11;
                int i12 = i11;
                int i13 = (int) (j6 - j4);
                if (j5 != 6) {
                    j = j2;
                    b = 255;
                } else if (i9 == 1) {
                    b = 255;
                    j5 += (long) (bArr[i + i10] & 255);
                    j = j2;
                    i10++;
                } else {
                    b = 255;
                    while (true) {
                        i8 = i10 + 1;
                        int i14 = bArr[i + i10] & 255;
                        j = j2;
                        j5 += (long) i14;
                        if (i14 != 255) {
                            break;
                        }
                        i10 = i8;
                        j2 = j;
                    }
                    i10 = i8;
                }
                if (i9 == 1) {
                    i6 = i10 + 1;
                    i7 = i13 - (bArr[i + i10] & b);
                } else {
                    i6 = i10 + 1;
                    int i15 = bArr[i + i10] & b;
                    i7 = i13 - i15;
                    if (i15 == b && j4 == 7936) {
                        long j7 = (bArr[i + i6] & b) << 8;
                        i6 = i10 + 3;
                        i7 = (int) ((j6 - (j7 + ((long) (bArr[i + (i10 + 2)] & b)))) - 8191);
                    }
                }
                if (j6 + j5 + 3 > i4 || i7 - 1 < 0) {
                    return 0;
                }
                if (i6 < i2) {
                    j = bArr[i + i6] & 255;
                    i6++;
                } else {
                    z3 = false;
                }
                if (i7 == i12) {
                    z = true;
                    byte b2 = bArr2[(i3 + i7) - 1];
                    bArr2[i3 + i12] = b2;
                    bArr2[i3 + i12 + 1] = b2;
                    i5 = i12 + 3;
                    bArr2[i3 + i12 + 2] = b2;
                    while (j5 != 0) {
                        bArr2[i3 + i5] = b2;
                        j5--;
                        i5++;
                    }
                } else {
                    z = true;
                    bArr2[i3 + i12] = bArr2[i3 + (i7 - 1)];
                    int i16 = i7 + 1;
                    bArr2[i3 + i12 + 1] = bArr2[i3 + i7];
                    i5 = i12 + 3;
                    int i17 = i7 + 2;
                    bArr2[i3 + i12 + 2] = bArr2[i3 + i16];
                    while (j5 != 0) {
                        bArr2[i3 + i5] = bArr2[i3 + i17];
                        j5--;
                        i5++;
                        i17++;
                    }
                }
                i10 = i6;
                j2 = j;
            } else {
                long j8 = j2;
                int i18 = i11;
                z = z2;
                long j9 = j8 + 1;
                if (((long) i18) + j9 > i4 || ((long) i10) + j9 > i2) {
                    return 0;
                }
                bArr2[i3 + i18] = bArr[i + i10];
                i5 = i18 + 1;
                int i19 = i10 + 1;
                j2 = j8;
                while (j2 != 0) {
                    bArr2[i3 + i5] = bArr[i + i19];
                    j2--;
                    i5++;
                    i19++;
                }
                boolean z4 = i19 < i2 ? z : false;
                if (z4) {
                    z3 = z4;
                    i10 = i19 + 1;
                    j2 = bArr[i + i19] & 255;
                } else {
                    z3 = z4;
                    i10 = i19;
                }
            }
            if (!z3) {
                return i5;
            }
            z2 = z;
            i11 = i5;
            c = 5;
        }
    }

    private static int hashFunction(byte[] bArr, int i) {
        int u16 = readU16(bArr, i);
        return ((readU16(bArr, i + 1) ^ (u16 >> 3)) ^ u16) & 8191;
    }

    private static int readU16(byte[] bArr, int i) {
        int i2 = i + 1;
        if (i2 >= bArr.length) {
            return bArr[i] & 255;
        }
        return (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
    }

    private FastLz() {
    }
}
