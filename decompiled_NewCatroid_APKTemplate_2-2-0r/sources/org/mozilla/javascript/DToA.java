package org.mozilla.javascript;

import java.math.BigInteger;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes4.dex */
class DToA {
    private static final int Bias = 1023;
    private static final int Bletch = 16;
    private static final int Bndry_mask = 1048575;
    static final int DTOSTR_EXPONENTIAL = 3;
    static final int DTOSTR_FIXED = 2;
    static final int DTOSTR_PRECISION = 4;
    static final int DTOSTR_STANDARD = 0;
    static final int DTOSTR_STANDARD_EXPONENTIAL = 1;
    private static final int Exp_11 = 1072693248;
    private static final int Exp_mask = 2146435072;
    private static final int Exp_mask_shifted = 2047;
    private static final int Exp_msk1 = 1048576;
    private static final long Exp_msk1L = 4503599627370496L;
    private static final int Exp_shift = 20;
    private static final int Exp_shift1 = 20;
    private static final int Exp_shiftL = 52;
    private static final int Frac_mask = 1048575;
    private static final int Frac_mask1 = 1048575;
    private static final long Frac_maskL = 4503599627370495L;
    private static final int Int_max = 14;
    private static final int Log2P = 1;
    private static final int P = 53;
    private static final int Quick_max = 14;
    private static final int Sign_bit = Integer.MIN_VALUE;
    private static final int Ten_pmax = 22;
    private static final int n_bigtens = 5;
    private static final double[] tens = {1.0d, 10.0d, 100.0d, 1000.0d, 10000.0d, 100000.0d, 1000000.0d, 1.0E7d, 1.0E8d, 1.0E9d, 1.0E10d, 1.0E11d, 1.0E12d, 1.0E13d, 1.0E14d, 1.0E15d, 1.0E16d, 1.0E17d, 1.0E18d, 1.0E19d, 1.0E20d, 1.0E21d, 1.0E22d};
    private static final double[] bigtens = {1.0E16d, 1.0E32d, 1.0E64d, 1.0E128d, 1.0E256d};
    private static final int[] dtoaModes = {0, 0, 3, 2, 2};

    private static char BASEDIGIT(int i) {
        return (char) (i >= 10 ? i + 87 : i + 48);
    }

    private static int hi0bits(int i) {
        int i2;
        if (((-65536) & i) == 0) {
            i <<= 16;
            i2 = 16;
        } else {
            i2 = 0;
        }
        if (((-16777216) & i) == 0) {
            i2 += 8;
            i <<= 8;
        }
        if (((-268435456) & i) == 0) {
            i2 += 4;
            i <<= 4;
        }
        if (((-1073741824) & i) == 0) {
            i2 += 2;
            i <<= 2;
        }
        if ((Integer.MIN_VALUE & i) == 0) {
            i2++;
            if ((i & 1073741824) == 0) {
                return 32;
            }
        }
        return i2;
    }

    private static int lo0bits(int i) {
        int i2 = 0;
        if ((i & 7) != 0) {
            if ((i & 1) != 0) {
                return 0;
            }
            return (i & 2) != 0 ? 1 : 2;
        }
        if ((65535 & i) == 0) {
            i >>>= 16;
            i2 = 16;
        }
        if ((i & 255) == 0) {
            i2 += 8;
            i >>>= 8;
        }
        if ((i & 15) == 0) {
            i2 += 4;
            i >>>= 4;
        }
        if ((i & 3) == 0) {
            i2 += 2;
            i >>>= 2;
        }
        if ((i & 1) == 0) {
            i2++;
            if (((i >>> 1) & 1) == 0) {
                return 32;
            }
        }
        return i2;
    }

    DToA() {
    }

    private static void stuffBits(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >> 24);
        bArr[i + 1] = (byte) (i2 >> 16);
        bArr[i + 2] = (byte) (i2 >> 8);
        bArr[i + 3] = (byte) i2;
    }

    private static BigInteger d2b(double d, int[] iArr, int[] iArr2) {
        byte[] bArr;
        int iLo0bits;
        long jDoubleToLongBits = Double.doubleToLongBits(d);
        int i = (int) (jDoubleToLongBits >>> 32);
        int i2 = (int) jDoubleToLongBits;
        int i3 = 1048575 & i;
        int i4 = (i & Integer.MAX_VALUE) >>> 20;
        if (i4 != 0) {
            i3 |= 1048576;
        }
        int i5 = 1;
        if (i2 != 0) {
            bArr = new byte[8];
            iLo0bits = lo0bits(i2);
            int i6 = i2 >>> iLo0bits;
            if (iLo0bits != 0) {
                stuffBits(bArr, 4, i6 | (i3 << (32 - iLo0bits)));
                i3 >>= iLo0bits;
            } else {
                stuffBits(bArr, 4, i6);
            }
            stuffBits(bArr, 0, i3);
            if (i3 != 0) {
                i5 = 2;
            }
        } else {
            bArr = new byte[4];
            int iLo0bits2 = lo0bits(i3);
            i3 >>>= iLo0bits2;
            stuffBits(bArr, 0, i3);
            iLo0bits = iLo0bits2 + 32;
        }
        if (i4 != 0) {
            iArr[0] = (i4 - 1075) + iLo0bits;
            iArr2[0] = 53 - iLo0bits;
        } else {
            iArr[0] = (i4 - 1074) + iLo0bits;
            iArr2[0] = (i5 * 32) - hi0bits(i3);
        }
        return new BigInteger(bArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x0122, code lost:
    
        if (r8 > 0) goto L76;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static java.lang.String JS_dtobasestr(int r12, double r13) {
        /*
            Method dump skipped, instruction units count: 346
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.DToA.JS_dtobasestr(int, double):java.lang.String");
    }

    static int word0(double d) {
        return (int) (Double.doubleToLongBits(d) >> 32);
    }

    static double setWord0(double d, int i) {
        return Double.longBitsToDouble((Double.doubleToLongBits(d) & 4294967295L) | (((long) i) << 32));
    }

    static int word1(double d) {
        return (int) Double.doubleToLongBits(d);
    }

    static BigInteger pow5mult(BigInteger bigInteger, int i) {
        return bigInteger.multiply(BigInteger.valueOf(5L).pow(i));
    }

    static boolean roundOff(StringBuilder sb) {
        int length = sb.length();
        while (length != 0) {
            int i = length - 1;
            char cCharAt = sb.charAt(i);
            if (cCharAt != '9') {
                sb.setCharAt(i, (char) (cCharAt + 1));
                sb.setLength(length);
                return false;
            }
            length = i;
        }
        sb.setLength(0);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:113:0x0217  */
    /* JADX WARN: Code duplicated, block: B:115:0x021f  */
    /* JADX WARN: Code duplicated, block: B:117:0x0228  */
    /* JADX WARN: Code duplicated, block: B:119:0x0230  */
    /* JADX WARN: Code duplicated, block: B:121:0x023b  */
    /* JADX WARN: Code duplicated, block: B:122:0x023e  */
    /* JADX WARN: Code duplicated, block: B:124:0x0243  */
    /* JADX WARN: Code duplicated, block: B:126:0x0247  */
    /* JADX WARN: Code duplicated, block: B:131:0x0270  */
    /* JADX WARN: Code duplicated, block: B:136:0x0290  */
    /* JADX WARN: Code duplicated, block: B:141:0x02a2  */
    /* JADX WARN: Code duplicated, block: B:144:0x02a8 A[LOOP:1: B:127:0x0259->B:144:0x02a8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:145:0x02b1  */
    /* JADX WARN: Code duplicated, block: B:150:0x02db A[LOOP:9: B:150:0x02db->B:422:?, LOOP_START] */
    /* JADX WARN: Code duplicated, block: B:153:0x02f3  */
    /* JADX WARN: Code duplicated, block: B:158:0x0305  */
    /* JADX WARN: Code duplicated, block: B:160:0x030e  */
    /* JADX WARN: Code duplicated, block: B:163:0x0317 A[LOOP:8: B:146:0x02c5->B:163:0x0317, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:164:0x031f  */
    /* JADX WARN: Code duplicated, block: B:167:0x032d  */
    /* JADX WARN: Code duplicated, block: B:168:0x0331  */
    /* JADX WARN: Code duplicated, block: B:169:0x0336  */
    /* JADX WARN: Code duplicated, block: B:173:0x0350  */
    /* JADX WARN: Code duplicated, block: B:213:0x03dc  */
    /* JADX WARN: Code duplicated, block: B:215:0x03df A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:216:0x03e1  */
    /* JADX WARN: Code duplicated, block: B:217:0x03e5  */
    /* JADX WARN: Code duplicated, block: B:219:0x03f1  */
    /* JADX WARN: Code duplicated, block: B:221:0x03f5  */
    /* JADX WARN: Code duplicated, block: B:222:0x03fa  */
    /* JADX WARN: Code duplicated, block: B:224:0x0401  */
    /* JADX WARN: Code duplicated, block: B:225:0x0405  */
    /* JADX WARN: Code duplicated, block: B:228:0x0418  */
    /* JADX WARN: Code duplicated, block: B:230:0x0422 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:232:0x0426  */
    /* JADX WARN: Code duplicated, block: B:233:0x0428  */
    /* JADX WARN: Code duplicated, block: B:236:0x042f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:237:0x0431 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:238:0x0433  */
    /* JADX WARN: Code duplicated, block: B:241:0x043e  */
    /* JADX WARN: Code duplicated, block: B:242:0x0443  */
    /* JADX WARN: Code duplicated, block: B:245:0x044d  */
    /* JADX WARN: Code duplicated, block: B:248:0x0454  */
    /* JADX WARN: Code duplicated, block: B:255:0x0473  */
    /* JADX WARN: Code duplicated, block: B:259:0x047d  */
    /* JADX WARN: Code duplicated, block: B:261:0x0482  */
    /* JADX WARN: Code duplicated, block: B:264:0x048c  */
    /* JADX WARN: Code duplicated, block: B:265:0x0495  */
    /* JADX WARN: Code duplicated, block: B:268:0x049b  */
    /* JADX WARN: Code duplicated, block: B:271:0x04a0  */
    /* JADX WARN: Code duplicated, block: B:273:0x04a7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:274:0x04a9  */
    /* JADX WARN: Code duplicated, block: B:277:0x04b0  */
    /* JADX WARN: Code duplicated, block: B:279:0x04b6  */
    /* JADX WARN: Code duplicated, block: B:282:0x04be  */
    /* JADX WARN: Code duplicated, block: B:286:0x04d0  */
    /* JADX WARN: Code duplicated, block: B:289:0x04dc  */
    /* JADX WARN: Code duplicated, block: B:303:0x050c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:304:0x050e  */
    /* JADX WARN: Code duplicated, block: B:306:0x0514  */
    /* JADX WARN: Code duplicated, block: B:307:0x0519  */
    /* JADX WARN: Code duplicated, block: B:311:0x053a  */
    /* JADX WARN: Code duplicated, block: B:312:0x053c  */
    /* JADX WARN: Code duplicated, block: B:314:0x0542 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:328:0x056c  */
    /* JADX WARN: Code duplicated, block: B:330:0x056f A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:354:0x05cf  */
    /* JADX WARN: Code duplicated, block: B:356:0x05da A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:357:0x05dc  */
    /* JADX WARN: Code duplicated, block: B:359:0x05e0 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:361:0x05e3  */
    /* JADX WARN: Code duplicated, block: B:362:0x05e5  */
    /* JADX WARN: Code duplicated, block: B:364:0x05ec  */
    /* JADX WARN: Code duplicated, block: B:366:0x05f5  */
    /* JADX WARN: Code duplicated, block: B:369:0x05ff  */
    /* JADX WARN: Code duplicated, block: B:370:0x0602  */
    /* JADX WARN: Code duplicated, block: B:373:0x0608  */
    /* JADX WARN: Code duplicated, block: B:379:0x062c A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:385:0x0639  */
    /* JADX WARN: Code duplicated, block: B:387:0x063f  */
    /* JADX WARN: Code duplicated, block: B:391:0x064a A[LOOP:6: B:374:0x060a->B:391:0x064a, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:396:0x026c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:397:0x0279 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:398:0x02a5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:407:0x0487 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:408:0x05cd A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:415:0x0620 A[EDGE_INSN: B:415:0x0620->B:376:0x0620 BREAK  A[LOOP:6: B:374:0x060a->B:391:0x064a], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:416:0x028e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:419:0x02d3 A[EDGE_INSN: B:419:0x02d3->B:148:0x02d3 BREAK  A[LOOP:8: B:146:0x02c5->B:163:0x0317], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:420:0x02f1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x0161 A[ADDED_TO_REGION] */
    static int JS_dtoa(double d, int i, boolean z, int i2, boolean[] zArr, StringBuilder sb) {
        double word0;
        long jWord1;
        double word1;
        int i3;
        boolean z2;
        boolean z3;
        int i4;
        int i5;
        int i6;
        int i7;
        boolean z4;
        int i8;
        int i9;
        int i10;
        boolean z5;
        int i11;
        int i12;
        char c;
        int[] iArr;
        int i13;
        int i14;
        BigInteger bigIntegerMultiply;
        int i15;
        int i16;
        int i17;
        int i18;
        BigInteger bigIntegerValueOf;
        boolean z6;
        byte[] byteArray;
        int i19;
        int i20;
        int iHi0bits;
        int i21;
        int i22;
        int i23;
        char c2;
        int i24;
        int i25;
        BigInteger bigInteger;
        char cIntValue;
        char cIntValue2;
        BigInteger bigInteger2;
        int iCompareTo;
        BigInteger bigIntegerMultiply2;
        int i26;
        int iCompareTo2;
        BigInteger bigIntegerSubtract;
        int iCompareTo3;
        int i27;
        int i28;
        int iCompareTo4;
        char c3;
        int iCompareTo5;
        int i29;
        int i30;
        int i31;
        int i32;
        int i33;
        int i34;
        int i35;
        char c4;
        double d2;
        int i36;
        boolean z7;
        double word2;
        double d3;
        double d4;
        int i37;
        char cCharAt;
        char c5;
        double d5;
        double d6;
        int i38;
        char cCharAt2;
        char c6;
        double d7;
        int i39;
        int i40;
        int i41;
        boolean z8;
        boolean z9;
        int i42 = i;
        int[] iArr2 = new int[1];
        int[] iArr3 = new int[1];
        if ((word0(d) & Integer.MIN_VALUE) != 0) {
            zArr[0] = true;
            word0 = setWord0(d, word0(d) & Integer.MAX_VALUE);
        } else {
            word0 = d;
            zArr[0] = false;
        }
        if ((word0(word0) & Exp_mask) == Exp_mask) {
            sb.append((word1(word0) == 0 && (word0(word0) & 1048575) == 0) ? "Infinity" : "NaN");
            return 9999;
        }
        if (word0 == 0.0d) {
            sb.setLength(0);
            sb.append(ExternalAnnotationProvider.NULLABLE);
            return 1;
        }
        BigInteger bigIntegerD2b = d2b(word0, iArr2, iArr3);
        int iWord0 = (word0(word0) >>> 20) & Exp_mask_shifted;
        if (iWord0 != 0) {
            i3 = iWord0 - 1023;
            z2 = false;
            iArr2 = iArr2;
            word1 = setWord0(word0, (word0(word0) & 1048575) | Exp_11);
        } else {
            int i43 = iArr3[0] + iArr2[0];
            int i44 = i43 + 1074;
            if (i44 > 32) {
                jWord1 = (((long) word0(word0)) << (64 - i44)) | ((long) (word1(word0) >>> (i43 + 1042)));
            } else {
                jWord1 = ((long) word1(word0)) << (32 - i44);
            }
            double d8 = jWord1;
            word1 = setWord0(d8, word0(d8) - 32505856);
            i3 = i43 - 1;
            z2 = true;
        }
        double d9 = ((word1 - 1.5d) * 0.289529654602168d) + 0.1760912590558d + (((double) i3) * 0.301029995663981d);
        int i45 = (int) d9;
        if (d9 < 0.0d && d9 != i45) {
            i45--;
        }
        if (i45 < 0 || i45 > 22) {
            z3 = true;
        } else {
            if (word0 < tens[i45]) {
                i45--;
            }
            z3 = false;
        }
        int i46 = (iArr3[0] - i3) - 1;
        if (i46 >= 0) {
            i5 = i46;
            i4 = 0;
        } else {
            i4 = -i46;
            i5 = 0;
        }
        if (i45 >= 0) {
            i5 += i45;
            i6 = 0;
            i7 = i45;
        } else {
            i4 -= i45;
            i6 = -i45;
            i7 = 0;
        }
        if (i42 < 0 || i42 > 9) {
            i42 = 0;
        }
        if (i42 > 5) {
            i42 -= 4;
            z4 = false;
        } else {
            z4 = true;
        }
        if (i42 != 0 && i42 != 1) {
            if (i42 != 2) {
                if (i42 == 3) {
                    z9 = false;
                } else if (i42 == 4) {
                    z8 = true;
                } else if (i42 != 5) {
                    i9 = i2;
                    i8 = 0;
                    i10 = 0;
                } else {
                    z9 = true;
                }
                int i47 = i2 + i45;
                z5 = z9;
                i8 = i47 + 1;
                i10 = i47;
                i9 = i2;
                if (i8 < 0 && i8 <= 14 && z4) {
                    if (i45 > 0) {
                        double d10 = tens[i45 & 15];
                        int i48 = i45 >> 4;
                        if ((i48 & 16) != 0) {
                            d7 = word0 / bigtens[4];
                            i39 = 0;
                            i40 = i48 & 15;
                            i41 = 3;
                        } else {
                            d7 = word0;
                            i39 = 0;
                            i40 = i48;
                            i41 = 2;
                        }
                        while (i40 != 0) {
                            if ((i40 & 1) != 0) {
                                i41++;
                                d10 *= bigtens[i39];
                            }
                            i40 >>= 1;
                            i39++;
                        }
                        d2 = d7 / d10;
                        i36 = i41;
                    } else {
                        int i49 = -i45;
                        if (i49 != 0) {
                            int i50 = i49 >> 4;
                            d2 = tens[i49 & 15] * word0;
                            i36 = 2;
                            int i51 = 0;
                            while (i50 != 0) {
                                if ((i50 & 1) != 0) {
                                    i36++;
                                    d2 *= bigtens[i51];
                                }
                                i50 >>= 1;
                                i51++;
                            }
                        } else {
                            d2 = word0;
                            i36 = 2;
                        }
                    }
                    if (z3 && d2 < 1.0d && i8 > 0) {
                        if (i10 <= 0) {
                            z7 = true;
                        } else {
                            d2 *= 10.0d;
                            i36++;
                            word0 = word0;
                            i45--;
                            i8 = i10;
                            z7 = false;
                        }
                        double d11 = (((double) i36) * d2) + 7.0d;
                        word2 = setWord0(d11, word0(d11) - 54525952);
                        if (i8 == 0) {
                            d2 -= 5.0d;
                            if (d2 > word2) {
                                sb.append(ExternalAnnotationProvider.NONNULL);
                                return i45 + 2;
                            }
                            if (d2 < (-word2)) {
                                sb.setLength(0);
                                sb.append(ExternalAnnotationProvider.NULLABLE);
                                return 1;
                            }
                            z7 = true;
                        }
                        if (z7) {
                            z3 = z3;
                            i4 = i4;
                            i8 = i8;
                            i12 = i7;
                            i11 = i45;
                            iArr = iArr3;
                        } else {
                            if (z5) {
                                d5 = (0.5d / tens[i8 - 1]) - word2;
                                d6 = d2;
                                i38 = 0;
                                while (true) {
                                    long j = (long) d6;
                                    i12 = i7;
                                    i11 = i45;
                                    d2 = d6 - j;
                                    sb.append((char) (j + 48));
                                    if (d2 < d5) {
                                        return i45 + 1;
                                    }
                                    if (1.0d - d2 < d5) {
                                        do {
                                            cCharAt2 = sb.charAt(sb.length() - 1);
                                            sb.setLength(sb.length() - 1);
                                            if (cCharAt2 != '9') {
                                                c6 = cCharAt2;
                                            }
                                            sb.append((char) (c6 + 1));
                                            return i45 + 1;
                                        } while (sb.length() != 0);
                                        i45++;
                                        c6 = ExternalAnnotationProvider.NULLABLE;
                                        sb.append((char) (c6 + 1));
                                        return i45 + 1;
                                    }
                                    i38++;
                                    if (i38 >= i8) {
                                        iArr = iArr3;
                                        break;
                                    }
                                    d5 *= 10.0d;
                                    d6 = d2 * 10.0d;
                                    i7 = i12;
                                    i45 = i11;
                                }
                            } else {
                                i12 = i7;
                                i11 = i45;
                                d3 = word2 * tens[i8 - 1];
                                d4 = d2;
                                i37 = 1;
                                while (true) {
                                    long j2 = (long) d4;
                                    iArr = iArr3;
                                    d2 = d4 - j2;
                                    sb.append((char) (j2 + 48));
                                    if (i37 == i8) {
                                        break;
                                    }
                                    i37++;
                                    d4 = d2 * 10.0d;
                                    iArr3 = iArr;
                                }
                                if (d2 > d3 + 0.5d) {
                                    do {
                                        cCharAt = sb.charAt(sb.length() - 1);
                                        sb.setLength(sb.length() - 1);
                                        if (cCharAt != '9') {
                                            c5 = cCharAt;
                                        }
                                        sb.append((char) (c5 + 1));
                                        return i45 + 1;
                                    } while (sb.length() != 0);
                                    i45++;
                                    c5 = ExternalAnnotationProvider.NULLABLE;
                                    sb.append((char) (c5 + 1));
                                    return i45 + 1;
                                }
                                if (d2 < 0.5d - d3) {
                                    stripTrailingZeroes(sb);
                                    return i45 + 1;
                                }
                            }
                            z7 = true;
                        }
                        c = 0;
                        if (z7) {
                            sb.setLength(0);
                        } else {
                            i13 = i45;
                            word0 = d2;
                        }
                        i14 = iArr2[c];
                        if (i14 < 0 && i13 <= 14) {
                            double d12 = tens[i13];
                            if (i9 < 0 && i8 <= 0) {
                                if (i8 >= 0) {
                                    double d13 = d12 * 5.0d;
                                    if (word0 >= d13 && (z || word0 != d13)) {
                                        sb.append(ExternalAnnotationProvider.NONNULL);
                                        return i13 + 2;
                                    }
                                }
                                sb.setLength(0);
                                sb.append(ExternalAnnotationProvider.NULLABLE);
                                return 1;
                            }
                            int i52 = 1;
                            while (true) {
                                long j3 = (long) (word0 / d12);
                                double d14 = word0 - (j3 * d12);
                                sb.append((char) (j3 + 48));
                                if (i52 == i8) {
                                    double d15 = d14 + d14;
                                    if (d15 <= d12 && (d15 != d12 || ((j3 & 1) == 0 && !z))) {
                                        i35 = 1;
                                        break;
                                    }
                                    while (true) {
                                        i35 = 1;
                                        char cCharAt3 = sb.charAt(sb.length() - 1);
                                        sb.setLength(sb.length() - 1);
                                        if (cCharAt3 != '9') {
                                            c4 = cCharAt3;
                                            break;
                                        }
                                        if (sb.length() == 0) {
                                            i13++;
                                            c4 = ExternalAnnotationProvider.NULLABLE;
                                            break;
                                        }
                                    }
                                    sb.append((char) (c4 + 1));
                                    break;
                                }
                                i35 = 1;
                                word0 = d14 * 10.0d;
                                if (word0 == 0.0d) {
                                    break;
                                }
                                i52++;
                            }
                            return i13 + i35;
                        }
                        if (z5) {
                            if (i42 < 2) {
                                if (z2) {
                                    i34 = i14 + 1075;
                                } else {
                                    i34 = 54 - iArr[0];
                                }
                                i33 = i34;
                                i15 = i6;
                                i17 = i4;
                                i18 = i12;
                            } else {
                                i31 = i8 - 1;
                                if (i6 >= i31) {
                                    i32 = i6 - i31;
                                    i18 = i12;
                                } else {
                                    int i53 = i31 - i6;
                                    i18 = i12 + i53;
                                    i6 += i53;
                                    i32 = 0;
                                }
                                if (i8 < 0) {
                                    i17 = i4 - i8;
                                    i33 = 0;
                                } else {
                                    i33 = i8;
                                    i17 = i4;
                                }
                                int i54 = i6;
                                i6 = i32;
                                i15 = i54;
                            }
                            int i55 = i4 + i33;
                            i16 = i5 + i33;
                            bigIntegerMultiply = BigInteger.valueOf(1L);
                            i4 = i55;
                        } else {
                            bigIntegerMultiply = null;
                            i15 = i6;
                            i16 = i5;
                            i17 = i4;
                            i18 = i12;
                        }
                        if (i17 > 0 && i16 > 0) {
                            if (i17 < i16) {
                                i30 = i17;
                            } else {
                                i30 = i16;
                            }
                            i4 -= i30;
                            i17 -= i30;
                            i16 -= i30;
                        }
                        if (i15 > 0) {
                            if (z5) {
                                if (i6 > 0) {
                                    bigIntegerMultiply = pow5mult(bigIntegerMultiply, i6);
                                    bigIntegerD2b = bigIntegerMultiply.multiply(bigIntegerD2b);
                                }
                                i29 = i15 - i6;
                                if (i29 != 0) {
                                    bigIntegerD2b = pow5mult(bigIntegerD2b, i29);
                                }
                            } else {
                                bigIntegerD2b = pow5mult(bigIntegerD2b, i15);
                            }
                        }
                        bigIntegerValueOf = BigInteger.valueOf(1L);
                        if (i18 > 0) {
                            bigIntegerValueOf = pow5mult(bigIntegerValueOf, i18);
                        }
                        if (i42 < 2 || word1(word0) != 0 || (word0(word0) & 1048575) != 0 || (word0(word0) & 2145386496) == 0) {
                            z6 = false;
                        } else {
                            i4++;
                            i16++;
                            z6 = true;
                        }
                        byteArray = bigIntegerValueOf.toByteArray();
                        i20 = 0;
                        for (i19 = 0; i19 < 4; i19++) {
                            i20 <<= 8;
                            if (i19 < byteArray.length) {
                                i20 |= byteArray[i19] & 255;
                            }
                        }
                        if (i18 != 0) {
                            iHi0bits = 32 - hi0bits(i20);
                        } else {
                            iHi0bits = 1;
                        }
                        i21 = (iHi0bits + i16) & 31;
                        if (i21 != 0) {
                            i21 = 32 - i21;
                        }
                        if (i21 <= 4) {
                            if (i21 < 4) {
                                i22 = i21 + 28;
                            }
                            i23 = i4;
                            if (i23 > 0) {
                                bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
                            }
                            if (i16 > 0) {
                                bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
                            }
                            if (z3 && bigIntegerD2b.compareTo(bigIntegerValueOf) < 0) {
                                i13--;
                                bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                                if (z5) {
                                    bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                                }
                                i8 = i10;
                            }
                            if (i8 > 0 && i42 > 2) {
                                if (i8 < 0 || (iCompareTo5 = bigIntegerD2b.compareTo(bigIntegerValueOf.multiply(BigInteger.valueOf(5L)))) < 0 || (iCompareTo5 == 0 && !z)) {
                                    sb.setLength(0);
                                    sb.append(ExternalAnnotationProvider.NULLABLE);
                                    return 1;
                                }
                                sb.append(ExternalAnnotationProvider.NONNULL);
                                return i13 + 2;
                            }
                            c2 = 1;
                            if (z5) {
                                if (i17 > 0) {
                                    bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                                }
                                if (z6) {
                                    bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                                } else {
                                    bigIntegerMultiply2 = bigIntegerMultiply;
                                }
                                i26 = 1;
                                while (true) {
                                    BigInteger[] bigIntegerArrDivideAndRemainder = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                                    bigInteger2 = bigIntegerArrDivideAndRemainder[c2];
                                    cIntValue2 = (char) (bigIntegerArrDivideAndRemainder[0].intValue() + 48);
                                    iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                                    bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                                    if (bigIntegerSubtract.signum() <= 0) {
                                        iCompareTo3 = 1;
                                    } else {
                                        iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                                    }
                                    if (iCompareTo3 == 0 || i42 != 0) {
                                        i27 = 1;
                                    } else {
                                        i27 = 1;
                                        if ((word1(word0) & 1) == 0) {
                                            if (cIntValue2 == '9') {
                                                sb.append('9');
                                                if (roundOff(sb)) {
                                                    i13++;
                                                    sb.append(ExternalAnnotationProvider.NONNULL);
                                                }
                                                return i13 + 1;
                                            }
                                            if (iCompareTo2 > 0) {
                                                cIntValue2 = (char) (cIntValue2 + 1);
                                            }
                                            sb.append(cIntValue2);
                                            return i13 + 1;
                                        }
                                    }
                                    if (iCompareTo2 >= 0 || (iCompareTo2 == 0 && i42 == 0 && (word1(word0) & i27) == 0)) {
                                        if (iCompareTo3 <= 0) {
                                            i28 = 1;
                                        } else {
                                            iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                                            if (iCompareTo4 > 0) {
                                                c3 = (char) (cIntValue2 + 1);
                                                if (cIntValue2 == '9') {
                                                    sb.append('9');
                                                    if (roundOff(sb)) {
                                                        i13++;
                                                        sb.append(ExternalAnnotationProvider.NONNULL);
                                                    }
                                                    return i13 + 1;
                                                }
                                                i28 = 1;
                                                cIntValue2 = c3;
                                            } else if (iCompareTo4 != 0) {
                                                i28 = 1;
                                            } else if ((cIntValue2 & 1) != 1 || z) {
                                                c3 = (char) (cIntValue2 + 1);
                                                if (cIntValue2 == '9') {
                                                    sb.append('9');
                                                    if (roundOff(sb)) {
                                                        i13++;
                                                        sb.append(ExternalAnnotationProvider.NONNULL);
                                                    }
                                                    return i13 + 1;
                                                }
                                                i28 = 1;
                                                cIntValue2 = c3;
                                            } else {
                                                i28 = 1;
                                            }
                                        }
                                        sb.append(cIntValue2);
                                        return i13 + i28;
                                    }
                                    if (iCompareTo3 > 0) {
                                        if (cIntValue2 == '9') {
                                            sb.append('9');
                                            if (roundOff(sb)) {
                                                i13++;
                                                sb.append(ExternalAnnotationProvider.NONNULL);
                                            }
                                            return i13 + 1;
                                        }
                                        sb.append((char) (cIntValue2 + 1));
                                        return i13 + 1;
                                    }
                                    sb.append(cIntValue2);
                                    if (i26 == i8) {
                                        i24 = 1;
                                        break;
                                    }
                                    bigIntegerD2b = bigInteger2.multiply(BigInteger.valueOf(10L));
                                    if (bigIntegerMultiply == bigIntegerMultiply2) {
                                        bigIntegerMultiply2 = bigIntegerMultiply2.multiply(BigInteger.valueOf(10L));
                                        bigIntegerMultiply = bigIntegerMultiply2;
                                    } else {
                                        bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                                        bigIntegerMultiply2 = bigIntegerMultiply2.multiply(BigInteger.valueOf(10L));
                                    }
                                    i26++;
                                    c2 = 1;
                                }
                            } else {
                                i24 = 1;
                                i25 = 1;
                                while (true) {
                                    BigInteger[] bigIntegerArrDivideAndRemainder2 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                                    bigInteger = bigIntegerArrDivideAndRemainder2[i24];
                                    cIntValue = (char) (bigIntegerArrDivideAndRemainder2[0].intValue() + 48);
                                    sb.append(cIntValue);
                                    if (i25 >= i8) {
                                        break;
                                    }
                                    i25++;
                                    bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                                    i24 = i24;
                                }
                                cIntValue2 = cIntValue;
                                bigInteger2 = bigInteger;
                            }
                            iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
                            if (iCompareTo <= 0 || (iCompareTo == 0 && ((cIntValue2 & 1) == i24 || z))) {
                                if (roundOff(sb)) {
                                    sb.append(ExternalAnnotationProvider.NONNULL);
                                    return i13 + 2;
                                }
                            } else {
                                stripTrailingZeroes(sb);
                            }
                            return i13 + 1;
                        }
                        i22 = i21 - 4;
                        i4 += i22;
                        i17 += i22;
                        i16 += i22;
                        i23 = i4;
                        if (i23 > 0) {
                            bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
                        }
                        if (i16 > 0) {
                            bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
                        }
                        if (z3) {
                            i13--;
                            bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                            if (z5) {
                                bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                            }
                            i8 = i10;
                        }
                        if (i8 > 0) {
                        }
                        c2 = 1;
                        if (z5) {
                            if (i17 > 0) {
                                bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                            }
                            if (z6) {
                                bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                            } else {
                                bigIntegerMultiply2 = bigIntegerMultiply;
                            }
                            i26 = 1;
                            while (true) {
                                BigInteger[] bigIntegerArrDivideAndRemainder3 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                                bigInteger2 = bigIntegerArrDivideAndRemainder3[c2];
                                cIntValue2 = (char) (bigIntegerArrDivideAndRemainder3[0].intValue() + 48);
                                iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                                bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                                if (bigIntegerSubtract.signum() <= 0) {
                                    iCompareTo3 = 1;
                                } else {
                                    iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                                }
                                if (iCompareTo3 == 0) {
                                    i27 = 1;
                                } else {
                                    i27 = 1;
                                }
                                if (iCompareTo2 >= 0) {
                                }
                                if (iCompareTo3 <= 0) {
                                    i28 = 1;
                                } else {
                                    iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                                    if (iCompareTo4 > 0) {
                                        c3 = (char) (cIntValue2 + 1);
                                        if (cIntValue2 == '9') {
                                            sb.append('9');
                                            if (roundOff(sb)) {
                                                i13++;
                                                sb.append(ExternalAnnotationProvider.NONNULL);
                                            }
                                            return i13 + 1;
                                        }
                                        i28 = 1;
                                        cIntValue2 = c3;
                                    } else if (iCompareTo4 != 0) {
                                        if ((cIntValue2 & 1) != 1) {
                                        }
                                        c3 = (char) (cIntValue2 + 1);
                                        if (cIntValue2 == '9') {
                                            sb.append('9');
                                            if (roundOff(sb)) {
                                                i13++;
                                                sb.append(ExternalAnnotationProvider.NONNULL);
                                            }
                                            return i13 + 1;
                                        }
                                        i28 = 1;
                                        cIntValue2 = c3;
                                    } else {
                                        i28 = 1;
                                    }
                                }
                                sb.append(cIntValue2);
                                return i13 + i28;
                                i26++;
                                c2 = 1;
                            }
                        } else {
                            i24 = 1;
                            i25 = 1;
                            while (true) {
                                BigInteger[] bigIntegerArrDivideAndRemainder4 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                                bigInteger = bigIntegerArrDivideAndRemainder4[i24];
                                cIntValue = (char) (bigIntegerArrDivideAndRemainder4[0].intValue() + 48);
                                sb.append(cIntValue);
                                if (i25 >= i8) {
                                    break;
                                    break;
                                }
                                i25++;
                                bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                                i24 = i24;
                            }
                            cIntValue2 = cIntValue;
                            bigInteger2 = bigInteger;
                        }
                        iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
                        if (iCompareTo <= 0) {
                            if (roundOff(sb)) {
                                sb.append(ExternalAnnotationProvider.NONNULL);
                                return i13 + 2;
                            }
                        } else if (roundOff(sb)) {
                            sb.append(ExternalAnnotationProvider.NONNULL);
                            return i13 + 2;
                        }
                        return i13 + 1;
                    }
                    z7 = false;
                    double d16 = (((double) i36) * d2) + 7.0d;
                    word2 = setWord0(d16, word0(d16) - 54525952);
                    if (i8 == 0) {
                        d2 -= 5.0d;
                        if (d2 > word2) {
                            sb.append(ExternalAnnotationProvider.NONNULL);
                            return i45 + 2;
                        }
                        if (d2 < (-word2)) {
                            sb.setLength(0);
                            sb.append(ExternalAnnotationProvider.NULLABLE);
                            return 1;
                        }
                        z7 = true;
                    }
                    if (z7) {
                        if (z5) {
                            d5 = (0.5d / tens[i8 - 1]) - word2;
                            d6 = d2;
                            i38 = 0;
                            while (true) {
                                long j4 = (long) d6;
                                i12 = i7;
                                i11 = i45;
                                d2 = d6 - j4;
                                sb.append((char) (j4 + 48));
                                if (d2 < d5) {
                                    return i45 + 1;
                                }
                                if (1.0d - d2 < d5) {
                                    do {
                                        cCharAt2 = sb.charAt(sb.length() - 1);
                                        sb.setLength(sb.length() - 1);
                                        if (cCharAt2 != '9') {
                                            c6 = cCharAt2;
                                        }
                                        sb.append((char) (c6 + 1));
                                        return i45 + 1;
                                    } while (sb.length() != 0);
                                    i45++;
                                    c6 = ExternalAnnotationProvider.NULLABLE;
                                    sb.append((char) (c6 + 1));
                                    return i45 + 1;
                                }
                                i38++;
                                if (i38 >= i8) {
                                    iArr = iArr3;
                                    break;
                                }
                                d5 *= 10.0d;
                                d6 = d2 * 10.0d;
                                i7 = i12;
                                i45 = i11;
                            }
                        } else {
                            i12 = i7;
                            i11 = i45;
                            d3 = word2 * tens[i8 - 1];
                            d4 = d2;
                            i37 = 1;
                            while (true) {
                                long j5 = (long) d4;
                                iArr = iArr3;
                                d2 = d4 - j5;
                                sb.append((char) (j5 + 48));
                                if (i37 == i8) {
                                    break;
                                    break;
                                }
                                i37++;
                                d4 = d2 * 10.0d;
                                iArr3 = iArr;
                            }
                            if (d2 > d3 + 0.5d) {
                                do {
                                    cCharAt = sb.charAt(sb.length() - 1);
                                    sb.setLength(sb.length() - 1);
                                    if (cCharAt != '9') {
                                        c5 = cCharAt;
                                    }
                                    sb.append((char) (c5 + 1));
                                    return i45 + 1;
                                } while (sb.length() != 0);
                                i45++;
                                c5 = ExternalAnnotationProvider.NULLABLE;
                                sb.append((char) (c5 + 1));
                                return i45 + 1;
                            }
                            if (d2 < 0.5d - d3) {
                                stripTrailingZeroes(sb);
                                return i45 + 1;
                            }
                        }
                        z7 = true;
                    } else {
                        z3 = z3;
                        i4 = i4;
                        i8 = i8;
                        i12 = i7;
                        i11 = i45;
                        iArr = iArr3;
                    }
                    c = 0;
                    if (z7) {
                        sb.setLength(0);
                    } else {
                        i13 = i45;
                        word0 = d2;
                    }
                    i14 = iArr2[c];
                    if (i14 < 0) {
                    }
                    if (z5) {
                        if (i42 < 2) {
                            if (z2) {
                                i34 = i14 + 1075;
                            } else {
                                i34 = 54 - iArr[0];
                            }
                            i33 = i34;
                            i15 = i6;
                            i17 = i4;
                            i18 = i12;
                        } else {
                            i31 = i8 - 1;
                            if (i6 >= i31) {
                                i32 = i6 - i31;
                                i18 = i12;
                            } else {
                                int i56 = i31 - i6;
                                i18 = i12 + i56;
                                i6 += i56;
                                i32 = 0;
                            }
                            if (i8 < 0) {
                                i17 = i4 - i8;
                                i33 = 0;
                            } else {
                                i33 = i8;
                                i17 = i4;
                            }
                            int i57 = i6;
                            i6 = i32;
                            i15 = i57;
                        }
                        int i58 = i4 + i33;
                        i16 = i5 + i33;
                        bigIntegerMultiply = BigInteger.valueOf(1L);
                        i4 = i58;
                    } else {
                        bigIntegerMultiply = null;
                        i15 = i6;
                        i16 = i5;
                        i17 = i4;
                        i18 = i12;
                    }
                    if (i17 > 0) {
                        if (i17 < i16) {
                            i30 = i17;
                        } else {
                            i30 = i16;
                        }
                        i4 -= i30;
                        i17 -= i30;
                        i16 -= i30;
                    }
                    if (i15 > 0) {
                        if (z5) {
                            if (i6 > 0) {
                                bigIntegerMultiply = pow5mult(bigIntegerMultiply, i6);
                                bigIntegerD2b = bigIntegerMultiply.multiply(bigIntegerD2b);
                            }
                            i29 = i15 - i6;
                            if (i29 != 0) {
                                bigIntegerD2b = pow5mult(bigIntegerD2b, i29);
                            }
                        } else {
                            bigIntegerD2b = pow5mult(bigIntegerD2b, i15);
                        }
                    }
                    bigIntegerValueOf = BigInteger.valueOf(1L);
                    if (i18 > 0) {
                        bigIntegerValueOf = pow5mult(bigIntegerValueOf, i18);
                    }
                    if (i42 < 2) {
                        z6 = false;
                    } else {
                        z6 = false;
                    }
                    byteArray = bigIntegerValueOf.toByteArray();
                    i20 = 0;
                    while (i19 < 4) {
                        i20 <<= 8;
                        if (i19 < byteArray.length) {
                            i20 |= byteArray[i19] & 255;
                        }
                    }
                    if (i18 != 0) {
                        iHi0bits = 32 - hi0bits(i20);
                    } else {
                        iHi0bits = 1;
                    }
                    i21 = (iHi0bits + i16) & 31;
                    if (i21 != 0) {
                        i21 = 32 - i21;
                    }
                    if (i21 <= 4) {
                        if (i21 < 4) {
                            i22 = i21 + 28;
                        }
                        i23 = i4;
                        if (i23 > 0) {
                            bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
                        }
                        if (i16 > 0) {
                            bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
                        }
                        if (z3) {
                            i13--;
                            bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                            if (z5) {
                                bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                            }
                            i8 = i10;
                        }
                        if (i8 > 0) {
                        }
                        c2 = 1;
                        if (z5) {
                            if (i17 > 0) {
                                bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                            }
                            if (z6) {
                                bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                            } else {
                                bigIntegerMultiply2 = bigIntegerMultiply;
                            }
                            i26 = 1;
                            while (true) {
                                BigInteger[] bigIntegerArrDivideAndRemainder5 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                                bigInteger2 = bigIntegerArrDivideAndRemainder5[c2];
                                cIntValue2 = (char) (bigIntegerArrDivideAndRemainder5[0].intValue() + 48);
                                iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                                bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                                if (bigIntegerSubtract.signum() <= 0) {
                                    iCompareTo3 = 1;
                                } else {
                                    iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                                }
                                if (iCompareTo3 == 0) {
                                    i27 = 1;
                                } else {
                                    i27 = 1;
                                }
                                if (iCompareTo2 >= 0) {
                                }
                                if (iCompareTo3 <= 0) {
                                    i28 = 1;
                                } else {
                                    iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                                    if (iCompareTo4 > 0) {
                                        c3 = (char) (cIntValue2 + 1);
                                        if (cIntValue2 == '9') {
                                            sb.append('9');
                                            if (roundOff(sb)) {
                                                i13++;
                                                sb.append(ExternalAnnotationProvider.NONNULL);
                                            }
                                            return i13 + 1;
                                        }
                                        i28 = 1;
                                        cIntValue2 = c3;
                                    } else if (iCompareTo4 != 0) {
                                        if ((cIntValue2 & 1) != 1) {
                                        }
                                        c3 = (char) (cIntValue2 + 1);
                                        if (cIntValue2 == '9') {
                                            sb.append('9');
                                            if (roundOff(sb)) {
                                                i13++;
                                                sb.append(ExternalAnnotationProvider.NONNULL);
                                            }
                                            return i13 + 1;
                                        }
                                        i28 = 1;
                                        cIntValue2 = c3;
                                    } else {
                                        i28 = 1;
                                    }
                                }
                                sb.append(cIntValue2);
                                return i13 + i28;
                                i26++;
                                c2 = 1;
                            }
                        } else {
                            i24 = 1;
                            i25 = 1;
                            while (true) {
                                BigInteger[] bigIntegerArrDivideAndRemainder6 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                                bigInteger = bigIntegerArrDivideAndRemainder6[i24];
                                cIntValue = (char) (bigIntegerArrDivideAndRemainder6[0].intValue() + 48);
                                sb.append(cIntValue);
                                if (i25 >= i8) {
                                    break;
                                    break;
                                }
                                i25++;
                                bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                                i24 = i24;
                            }
                            cIntValue2 = cIntValue;
                            bigInteger2 = bigInteger;
                        }
                        iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
                        if (iCompareTo <= 0) {
                            if (roundOff(sb)) {
                                sb.append(ExternalAnnotationProvider.NONNULL);
                                return i13 + 2;
                            }
                        } else if (roundOff(sb)) {
                            sb.append(ExternalAnnotationProvider.NONNULL);
                            return i13 + 2;
                        }
                        return i13 + 1;
                    }
                    i22 = i21 - 4;
                    i4 += i22;
                    i17 += i22;
                    i16 += i22;
                    i23 = i4;
                    if (i23 > 0) {
                        bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
                    }
                    if (i16 > 0) {
                        bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
                    }
                    if (z3) {
                        i13--;
                        bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                        if (z5) {
                            bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                        }
                        i8 = i10;
                    }
                    if (i8 > 0) {
                    }
                    c2 = 1;
                    if (z5) {
                        if (i17 > 0) {
                            bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                        }
                        if (z6) {
                            bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                        } else {
                            bigIntegerMultiply2 = bigIntegerMultiply;
                        }
                        i26 = 1;
                        while (true) {
                            BigInteger[] bigIntegerArrDivideAndRemainder7 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                            bigInteger2 = bigIntegerArrDivideAndRemainder7[c2];
                            cIntValue2 = (char) (bigIntegerArrDivideAndRemainder7[0].intValue() + 48);
                            iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                            bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                            if (bigIntegerSubtract.signum() <= 0) {
                                iCompareTo3 = 1;
                            } else {
                                iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                            }
                            if (iCompareTo3 == 0) {
                                i27 = 1;
                            } else {
                                i27 = 1;
                            }
                            if (iCompareTo2 >= 0) {
                            }
                            if (iCompareTo3 <= 0) {
                                i28 = 1;
                            } else {
                                iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                                if (iCompareTo4 > 0) {
                                    c3 = (char) (cIntValue2 + 1);
                                    if (cIntValue2 == '9') {
                                        sb.append('9');
                                        if (roundOff(sb)) {
                                            i13++;
                                            sb.append(ExternalAnnotationProvider.NONNULL);
                                        }
                                        return i13 + 1;
                                    }
                                    i28 = 1;
                                    cIntValue2 = c3;
                                } else if (iCompareTo4 != 0) {
                                    if ((cIntValue2 & 1) != 1) {
                                    }
                                    c3 = (char) (cIntValue2 + 1);
                                    if (cIntValue2 == '9') {
                                        sb.append('9');
                                        if (roundOff(sb)) {
                                            i13++;
                                            sb.append(ExternalAnnotationProvider.NONNULL);
                                        }
                                        return i13 + 1;
                                    }
                                    i28 = 1;
                                    cIntValue2 = c3;
                                } else {
                                    i28 = 1;
                                }
                            }
                            sb.append(cIntValue2);
                            return i13 + i28;
                            i26++;
                            c2 = 1;
                        }
                    } else {
                        i24 = 1;
                        i25 = 1;
                        while (true) {
                            BigInteger[] bigIntegerArrDivideAndRemainder8 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                            bigInteger = bigIntegerArrDivideAndRemainder8[i24];
                            cIntValue = (char) (bigIntegerArrDivideAndRemainder8[0].intValue() + 48);
                            sb.append(cIntValue);
                            if (i25 >= i8) {
                                break;
                                break;
                            }
                            i25++;
                            bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                            i24 = i24;
                        }
                        cIntValue2 = cIntValue;
                        bigInteger2 = bigInteger;
                    }
                    iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
                    if (iCompareTo <= 0) {
                        if (roundOff(sb)) {
                            sb.append(ExternalAnnotationProvider.NONNULL);
                            return i13 + 2;
                        }
                    } else if (roundOff(sb)) {
                        sb.append(ExternalAnnotationProvider.NONNULL);
                        return i13 + 2;
                    }
                    return i13 + 1;
                }
                z3 = z3;
                i4 = i4;
                i8 = i8;
                word0 = word0;
                i11 = i45;
                i5 = i5;
                i12 = i7;
                c = 0;
                iArr = iArr3;
                i8 = i8;
                i13 = i11;
                i14 = iArr2[c];
                if (i14 < 0) {
                }
                if (z5) {
                    if (i42 < 2) {
                        if (z2) {
                            i34 = i14 + 1075;
                        } else {
                            i34 = 54 - iArr[0];
                        }
                        i33 = i34;
                        i15 = i6;
                        i17 = i4;
                        i18 = i12;
                    } else {
                        i31 = i8 - 1;
                        if (i6 >= i31) {
                            i32 = i6 - i31;
                            i18 = i12;
                        } else {
                            int i59 = i31 - i6;
                            i18 = i12 + i59;
                            i6 += i59;
                            i32 = 0;
                        }
                        if (i8 < 0) {
                            i17 = i4 - i8;
                            i33 = 0;
                        } else {
                            i33 = i8;
                            i17 = i4;
                        }
                        int i510 = i6;
                        i6 = i32;
                        i15 = i510;
                    }
                    int i511 = i4 + i33;
                    i16 = i5 + i33;
                    bigIntegerMultiply = BigInteger.valueOf(1L);
                    i4 = i511;
                } else {
                    bigIntegerMultiply = null;
                    i15 = i6;
                    i16 = i5;
                    i17 = i4;
                    i18 = i12;
                }
                if (i17 > 0) {
                    if (i17 < i16) {
                        i30 = i17;
                    } else {
                        i30 = i16;
                    }
                    i4 -= i30;
                    i17 -= i30;
                    i16 -= i30;
                }
                if (i15 > 0) {
                    if (z5) {
                        if (i6 > 0) {
                            bigIntegerMultiply = pow5mult(bigIntegerMultiply, i6);
                            bigIntegerD2b = bigIntegerMultiply.multiply(bigIntegerD2b);
                        }
                        i29 = i15 - i6;
                        if (i29 != 0) {
                            bigIntegerD2b = pow5mult(bigIntegerD2b, i29);
                        }
                    } else {
                        bigIntegerD2b = pow5mult(bigIntegerD2b, i15);
                    }
                }
                bigIntegerValueOf = BigInteger.valueOf(1L);
                if (i18 > 0) {
                    bigIntegerValueOf = pow5mult(bigIntegerValueOf, i18);
                }
                if (i42 < 2) {
                    z6 = false;
                } else {
                    z6 = false;
                }
                byteArray = bigIntegerValueOf.toByteArray();
                i20 = 0;
                while (i19 < 4) {
                    i20 <<= 8;
                    if (i19 < byteArray.length) {
                        i20 |= byteArray[i19] & 255;
                    }
                }
                if (i18 != 0) {
                    iHi0bits = 32 - hi0bits(i20);
                } else {
                    iHi0bits = 1;
                }
                i21 = (iHi0bits + i16) & 31;
                if (i21 != 0) {
                    i21 = 32 - i21;
                }
                if (i21 <= 4) {
                    if (i21 < 4) {
                        i22 = i21 + 28;
                    }
                    i23 = i4;
                    if (i23 > 0) {
                        bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
                    }
                    if (i16 > 0) {
                        bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
                    }
                    if (z3) {
                        i13--;
                        bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                        if (z5) {
                            bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                        }
                        i8 = i10;
                    }
                    if (i8 > 0) {
                    }
                    c2 = 1;
                    if (z5) {
                        if (i17 > 0) {
                            bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                        }
                        if (z6) {
                            bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                        } else {
                            bigIntegerMultiply2 = bigIntegerMultiply;
                        }
                        i26 = 1;
                        while (true) {
                            BigInteger[] bigIntegerArrDivideAndRemainder9 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                            bigInteger2 = bigIntegerArrDivideAndRemainder9[c2];
                            cIntValue2 = (char) (bigIntegerArrDivideAndRemainder9[0].intValue() + 48);
                            iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                            bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                            if (bigIntegerSubtract.signum() <= 0) {
                                iCompareTo3 = 1;
                            } else {
                                iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                            }
                            if (iCompareTo3 == 0) {
                                i27 = 1;
                            } else {
                                i27 = 1;
                            }
                            if (iCompareTo2 >= 0) {
                            }
                            if (iCompareTo3 <= 0) {
                                i28 = 1;
                            } else {
                                iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                                if (iCompareTo4 > 0) {
                                    c3 = (char) (cIntValue2 + 1);
                                    if (cIntValue2 == '9') {
                                        sb.append('9');
                                        if (roundOff(sb)) {
                                            i13++;
                                            sb.append(ExternalAnnotationProvider.NONNULL);
                                        }
                                        return i13 + 1;
                                    }
                                    i28 = 1;
                                    cIntValue2 = c3;
                                } else if (iCompareTo4 != 0) {
                                    if ((cIntValue2 & 1) != 1) {
                                    }
                                    c3 = (char) (cIntValue2 + 1);
                                    if (cIntValue2 == '9') {
                                        sb.append('9');
                                        if (roundOff(sb)) {
                                            i13++;
                                            sb.append(ExternalAnnotationProvider.NONNULL);
                                        }
                                        return i13 + 1;
                                    }
                                    i28 = 1;
                                    cIntValue2 = c3;
                                } else {
                                    i28 = 1;
                                }
                            }
                            sb.append(cIntValue2);
                            return i13 + i28;
                            i26++;
                            c2 = 1;
                        }
                    } else {
                        i24 = 1;
                        i25 = 1;
                        while (true) {
                            BigInteger[] bigIntegerArrDivideAndRemainder10 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                            bigInteger = bigIntegerArrDivideAndRemainder10[i24];
                            cIntValue = (char) (bigIntegerArrDivideAndRemainder10[0].intValue() + 48);
                            sb.append(cIntValue);
                            if (i25 >= i8) {
                                break;
                                break;
                            }
                            i25++;
                            bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                            i24 = i24;
                        }
                        cIntValue2 = cIntValue;
                        bigInteger2 = bigInteger;
                    }
                    iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
                    if (iCompareTo <= 0) {
                        if (roundOff(sb)) {
                            sb.append(ExternalAnnotationProvider.NONNULL);
                            return i13 + 2;
                        }
                    } else if (roundOff(sb)) {
                        sb.append(ExternalAnnotationProvider.NONNULL);
                        return i13 + 2;
                    }
                    return i13 + 1;
                }
                i22 = i21 - 4;
                i4 += i22;
                i17 += i22;
                i16 += i22;
                i23 = i4;
                if (i23 > 0) {
                    bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
                }
                if (i16 > 0) {
                    bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
                }
                if (z3) {
                    i13--;
                    bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                    if (z5) {
                        bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                    }
                    i8 = i10;
                }
                if (i8 > 0) {
                }
                c2 = 1;
                if (z5) {
                    if (i17 > 0) {
                        bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                    }
                    if (z6) {
                        bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                    } else {
                        bigIntegerMultiply2 = bigIntegerMultiply;
                    }
                    i26 = 1;
                    while (true) {
                        BigInteger[] bigIntegerArrDivideAndRemainder11 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                        bigInteger2 = bigIntegerArrDivideAndRemainder11[c2];
                        cIntValue2 = (char) (bigIntegerArrDivideAndRemainder11[0].intValue() + 48);
                        iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                        bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                        if (bigIntegerSubtract.signum() <= 0) {
                            iCompareTo3 = 1;
                        } else {
                            iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                        }
                        if (iCompareTo3 == 0) {
                            i27 = 1;
                        } else {
                            i27 = 1;
                        }
                        if (iCompareTo2 >= 0) {
                        }
                        if (iCompareTo3 <= 0) {
                            i28 = 1;
                        } else {
                            iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                            if (iCompareTo4 > 0) {
                                c3 = (char) (cIntValue2 + 1);
                                if (cIntValue2 == '9') {
                                    sb.append('9');
                                    if (roundOff(sb)) {
                                        i13++;
                                        sb.append(ExternalAnnotationProvider.NONNULL);
                                    }
                                    return i13 + 1;
                                }
                                i28 = 1;
                                cIntValue2 = c3;
                            } else if (iCompareTo4 != 0) {
                                if ((cIntValue2 & 1) != 1) {
                                }
                                c3 = (char) (cIntValue2 + 1);
                                if (cIntValue2 == '9') {
                                    sb.append('9');
                                    if (roundOff(sb)) {
                                        i13++;
                                        sb.append(ExternalAnnotationProvider.NONNULL);
                                    }
                                    return i13 + 1;
                                }
                                i28 = 1;
                                cIntValue2 = c3;
                            } else {
                                i28 = 1;
                            }
                        }
                        sb.append(cIntValue2);
                        return i13 + i28;
                        i26++;
                        c2 = 1;
                    }
                } else {
                    i24 = 1;
                    i25 = 1;
                    while (true) {
                        BigInteger[] bigIntegerArrDivideAndRemainder12 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                        bigInteger = bigIntegerArrDivideAndRemainder12[i24];
                        cIntValue = (char) (bigIntegerArrDivideAndRemainder12[0].intValue() + 48);
                        sb.append(cIntValue);
                        if (i25 >= i8) {
                            break;
                            break;
                        }
                        i25++;
                        bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                        i24 = i24;
                    }
                    cIntValue2 = cIntValue;
                    bigInteger2 = bigInteger;
                }
                iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
                if (iCompareTo <= 0) {
                    if (roundOff(sb)) {
                        sb.append(ExternalAnnotationProvider.NONNULL);
                        return i13 + 2;
                    }
                } else if (roundOff(sb)) {
                    sb.append(ExternalAnnotationProvider.NONNULL);
                    return i13 + 2;
                }
                return i13 + 1;
            }
            z8 = false;
            i9 = i2 <= 0 ? 1 : i2;
            z5 = z8;
            i8 = i9;
            i10 = i8;
            if (i8 < 0) {
                z3 = z3;
                i4 = i4;
                i8 = i8;
                word0 = word0;
                i11 = i45;
                i5 = i5;
                i12 = i7;
                c = 0;
                iArr = iArr3;
                i8 = i8;
                i13 = i11;
            } else {
                z3 = z3;
                i4 = i4;
                i8 = i8;
                word0 = word0;
                i11 = i45;
                i5 = i5;
                i12 = i7;
                c = 0;
                iArr = iArr3;
                i8 = i8;
                i13 = i11;
            }
            i14 = iArr2[c];
            if (i14 < 0) {
            }
            if (z5) {
                if (i42 < 2) {
                    if (z2) {
                        i34 = i14 + 1075;
                    } else {
                        i34 = 54 - iArr[0];
                    }
                    i33 = i34;
                    i15 = i6;
                    i17 = i4;
                    i18 = i12;
                } else {
                    i31 = i8 - 1;
                    if (i6 >= i31) {
                        i32 = i6 - i31;
                        i18 = i12;
                    } else {
                        int i512 = i31 - i6;
                        i18 = i12 + i512;
                        i6 += i512;
                        i32 = 0;
                    }
                    if (i8 < 0) {
                        i17 = i4 - i8;
                        i33 = 0;
                    } else {
                        i33 = i8;
                        i17 = i4;
                    }
                    int i513 = i6;
                    i6 = i32;
                    i15 = i513;
                }
                int i514 = i4 + i33;
                i16 = i5 + i33;
                bigIntegerMultiply = BigInteger.valueOf(1L);
                i4 = i514;
            } else {
                bigIntegerMultiply = null;
                i15 = i6;
                i16 = i5;
                i17 = i4;
                i18 = i12;
            }
            if (i17 > 0) {
                if (i17 < i16) {
                    i30 = i17;
                } else {
                    i30 = i16;
                }
                i4 -= i30;
                i17 -= i30;
                i16 -= i30;
            }
            if (i15 > 0) {
                if (z5) {
                    if (i6 > 0) {
                        bigIntegerMultiply = pow5mult(bigIntegerMultiply, i6);
                        bigIntegerD2b = bigIntegerMultiply.multiply(bigIntegerD2b);
                    }
                    i29 = i15 - i6;
                    if (i29 != 0) {
                        bigIntegerD2b = pow5mult(bigIntegerD2b, i29);
                    }
                } else {
                    bigIntegerD2b = pow5mult(bigIntegerD2b, i15);
                }
            }
            bigIntegerValueOf = BigInteger.valueOf(1L);
            if (i18 > 0) {
                bigIntegerValueOf = pow5mult(bigIntegerValueOf, i18);
            }
            if (i42 < 2) {
                z6 = false;
            } else {
                z6 = false;
            }
            byteArray = bigIntegerValueOf.toByteArray();
            i20 = 0;
            while (i19 < 4) {
                i20 <<= 8;
                if (i19 < byteArray.length) {
                    i20 |= byteArray[i19] & 255;
                }
            }
            if (i18 != 0) {
                iHi0bits = 32 - hi0bits(i20);
            } else {
                iHi0bits = 1;
            }
            i21 = (iHi0bits + i16) & 31;
            if (i21 != 0) {
                i21 = 32 - i21;
            }
            if (i21 <= 4) {
                if (i21 < 4) {
                    i22 = i21 + 28;
                }
                i23 = i4;
                if (i23 > 0) {
                    bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
                }
                if (i16 > 0) {
                    bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
                }
                if (z3) {
                    i13--;
                    bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                    if (z5) {
                        bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                    }
                    i8 = i10;
                }
                if (i8 > 0) {
                }
                c2 = 1;
                if (z5) {
                    if (i17 > 0) {
                        bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                    }
                    if (z6) {
                        bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                    } else {
                        bigIntegerMultiply2 = bigIntegerMultiply;
                    }
                    i26 = 1;
                    while (true) {
                        BigInteger[] bigIntegerArrDivideAndRemainder13 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                        bigInteger2 = bigIntegerArrDivideAndRemainder13[c2];
                        cIntValue2 = (char) (bigIntegerArrDivideAndRemainder13[0].intValue() + 48);
                        iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                        bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                        if (bigIntegerSubtract.signum() <= 0) {
                            iCompareTo3 = 1;
                        } else {
                            iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                        }
                        if (iCompareTo3 == 0) {
                            i27 = 1;
                        } else {
                            i27 = 1;
                        }
                        if (iCompareTo2 >= 0) {
                        }
                        if (iCompareTo3 <= 0) {
                            i28 = 1;
                        } else {
                            iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                            if (iCompareTo4 > 0) {
                                c3 = (char) (cIntValue2 + 1);
                                if (cIntValue2 == '9') {
                                    sb.append('9');
                                    if (roundOff(sb)) {
                                        i13++;
                                        sb.append(ExternalAnnotationProvider.NONNULL);
                                    }
                                    return i13 + 1;
                                }
                                i28 = 1;
                                cIntValue2 = c3;
                            } else if (iCompareTo4 != 0) {
                                if ((cIntValue2 & 1) != 1) {
                                }
                                c3 = (char) (cIntValue2 + 1);
                                if (cIntValue2 == '9') {
                                    sb.append('9');
                                    if (roundOff(sb)) {
                                        i13++;
                                        sb.append(ExternalAnnotationProvider.NONNULL);
                                    }
                                    return i13 + 1;
                                }
                                i28 = 1;
                                cIntValue2 = c3;
                            } else {
                                i28 = 1;
                            }
                        }
                        sb.append(cIntValue2);
                        return i13 + i28;
                        i26++;
                        c2 = 1;
                    }
                } else {
                    i24 = 1;
                    i25 = 1;
                    while (true) {
                        BigInteger[] bigIntegerArrDivideAndRemainder14 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                        bigInteger = bigIntegerArrDivideAndRemainder14[i24];
                        cIntValue = (char) (bigIntegerArrDivideAndRemainder14[0].intValue() + 48);
                        sb.append(cIntValue);
                        if (i25 >= i8) {
                            break;
                            break;
                        }
                        i25++;
                        bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                        i24 = i24;
                    }
                    cIntValue2 = cIntValue;
                    bigInteger2 = bigInteger;
                }
                iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
                if (iCompareTo <= 0) {
                    if (roundOff(sb)) {
                        sb.append(ExternalAnnotationProvider.NONNULL);
                        return i13 + 2;
                    }
                } else if (roundOff(sb)) {
                    sb.append(ExternalAnnotationProvider.NONNULL);
                    return i13 + 2;
                }
                return i13 + 1;
            }
            i22 = i21 - 4;
            i4 += i22;
            i17 += i22;
            i16 += i22;
            i23 = i4;
            if (i23 > 0) {
                bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
            }
            if (i16 > 0) {
                bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
            }
            if (z3) {
                i13--;
                bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                if (z5) {
                    bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                }
                i8 = i10;
            }
            if (i8 > 0) {
            }
            c2 = 1;
            if (z5) {
                if (i17 > 0) {
                    bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                }
                if (z6) {
                    bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                } else {
                    bigIntegerMultiply2 = bigIntegerMultiply;
                }
                i26 = 1;
                while (true) {
                    BigInteger[] bigIntegerArrDivideAndRemainder15 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                    bigInteger2 = bigIntegerArrDivideAndRemainder15[c2];
                    cIntValue2 = (char) (bigIntegerArrDivideAndRemainder15[0].intValue() + 48);
                    iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                    bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                    if (bigIntegerSubtract.signum() <= 0) {
                        iCompareTo3 = 1;
                    } else {
                        iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                    }
                    if (iCompareTo3 == 0) {
                        i27 = 1;
                    } else {
                        i27 = 1;
                    }
                    if (iCompareTo2 >= 0) {
                    }
                    if (iCompareTo3 <= 0) {
                        i28 = 1;
                    } else {
                        iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                        if (iCompareTo4 > 0) {
                            c3 = (char) (cIntValue2 + 1);
                            if (cIntValue2 == '9') {
                                sb.append('9');
                                if (roundOff(sb)) {
                                    i13++;
                                    sb.append(ExternalAnnotationProvider.NONNULL);
                                }
                                return i13 + 1;
                            }
                            i28 = 1;
                            cIntValue2 = c3;
                        } else if (iCompareTo4 != 0) {
                            if ((cIntValue2 & 1) != 1) {
                            }
                            c3 = (char) (cIntValue2 + 1);
                            if (cIntValue2 == '9') {
                                sb.append('9');
                                if (roundOff(sb)) {
                                    i13++;
                                    sb.append(ExternalAnnotationProvider.NONNULL);
                                }
                                return i13 + 1;
                            }
                            i28 = 1;
                            cIntValue2 = c3;
                        } else {
                            i28 = 1;
                        }
                    }
                    sb.append(cIntValue2);
                    return i13 + i28;
                    i26++;
                    c2 = 1;
                }
            } else {
                i24 = 1;
                i25 = 1;
                while (true) {
                    BigInteger[] bigIntegerArrDivideAndRemainder16 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                    bigInteger = bigIntegerArrDivideAndRemainder16[i24];
                    cIntValue = (char) (bigIntegerArrDivideAndRemainder16[0].intValue() + 48);
                    sb.append(cIntValue);
                    if (i25 >= i8) {
                        break;
                        break;
                    }
                    i25++;
                    bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                    i24 = i24;
                }
                cIntValue2 = cIntValue;
                bigInteger2 = bigInteger;
            }
            iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
            if (iCompareTo <= 0) {
                if (roundOff(sb)) {
                    sb.append(ExternalAnnotationProvider.NONNULL);
                    return i13 + 2;
                }
            } else if (roundOff(sb)) {
                sb.append(ExternalAnnotationProvider.NONNULL);
                return i13 + 2;
            }
            return i13 + 1;
        }
        i8 = -1;
        i9 = 0;
        i10 = -1;
        z5 = true;
        if (i8 < 0) {
            z3 = z3;
            i4 = i4;
            i8 = i8;
            word0 = word0;
            i11 = i45;
            i5 = i5;
            i12 = i7;
            c = 0;
            iArr = iArr3;
            i8 = i8;
            i13 = i11;
        } else {
            z3 = z3;
            i4 = i4;
            i8 = i8;
            word0 = word0;
            i11 = i45;
            i5 = i5;
            i12 = i7;
            c = 0;
            iArr = iArr3;
            i8 = i8;
            i13 = i11;
        }
        i14 = iArr2[c];
        if (i14 < 0) {
        }
        if (z5) {
            if (i42 < 2) {
                if (z2) {
                    i34 = i14 + 1075;
                } else {
                    i34 = 54 - iArr[0];
                }
                i33 = i34;
                i15 = i6;
                i17 = i4;
                i18 = i12;
            } else {
                i31 = i8 - 1;
                if (i6 >= i31) {
                    i32 = i6 - i31;
                    i18 = i12;
                } else {
                    int i515 = i31 - i6;
                    i18 = i12 + i515;
                    i6 += i515;
                    i32 = 0;
                }
                if (i8 < 0) {
                    i17 = i4 - i8;
                    i33 = 0;
                } else {
                    i33 = i8;
                    i17 = i4;
                }
                int i516 = i6;
                i6 = i32;
                i15 = i516;
            }
            int i517 = i4 + i33;
            i16 = i5 + i33;
            bigIntegerMultiply = BigInteger.valueOf(1L);
            i4 = i517;
        } else {
            bigIntegerMultiply = null;
            i15 = i6;
            i16 = i5;
            i17 = i4;
            i18 = i12;
        }
        if (i17 > 0) {
            if (i17 < i16) {
                i30 = i17;
            } else {
                i30 = i16;
            }
            i4 -= i30;
            i17 -= i30;
            i16 -= i30;
        }
        if (i15 > 0) {
            if (z5) {
                if (i6 > 0) {
                    bigIntegerMultiply = pow5mult(bigIntegerMultiply, i6);
                    bigIntegerD2b = bigIntegerMultiply.multiply(bigIntegerD2b);
                }
                i29 = i15 - i6;
                if (i29 != 0) {
                    bigIntegerD2b = pow5mult(bigIntegerD2b, i29);
                }
            } else {
                bigIntegerD2b = pow5mult(bigIntegerD2b, i15);
            }
        }
        bigIntegerValueOf = BigInteger.valueOf(1L);
        if (i18 > 0) {
            bigIntegerValueOf = pow5mult(bigIntegerValueOf, i18);
        }
        if (i42 < 2) {
            z6 = false;
        } else {
            z6 = false;
        }
        byteArray = bigIntegerValueOf.toByteArray();
        i20 = 0;
        while (i19 < 4) {
            i20 <<= 8;
            if (i19 < byteArray.length) {
                i20 |= byteArray[i19] & 255;
            }
        }
        if (i18 != 0) {
            iHi0bits = 32 - hi0bits(i20);
        } else {
            iHi0bits = 1;
        }
        i21 = (iHi0bits + i16) & 31;
        if (i21 != 0) {
            i21 = 32 - i21;
        }
        if (i21 <= 4) {
            if (i21 < 4) {
                i22 = i21 + 28;
            }
            i23 = i4;
            if (i23 > 0) {
                bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
            }
            if (i16 > 0) {
                bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
            }
            if (z3) {
                i13--;
                bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
                if (z5) {
                    bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
                }
                i8 = i10;
            }
            if (i8 > 0) {
            }
            c2 = 1;
            if (z5) {
                if (i17 > 0) {
                    bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
                }
                if (z6) {
                    bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
                } else {
                    bigIntegerMultiply2 = bigIntegerMultiply;
                }
                i26 = 1;
                while (true) {
                    BigInteger[] bigIntegerArrDivideAndRemainder17 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                    bigInteger2 = bigIntegerArrDivideAndRemainder17[c2];
                    cIntValue2 = (char) (bigIntegerArrDivideAndRemainder17[0].intValue() + 48);
                    iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                    bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                    if (bigIntegerSubtract.signum() <= 0) {
                        iCompareTo3 = 1;
                    } else {
                        iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                    }
                    if (iCompareTo3 == 0) {
                        i27 = 1;
                    } else {
                        i27 = 1;
                    }
                    if (iCompareTo2 >= 0) {
                    }
                    if (iCompareTo3 <= 0) {
                        i28 = 1;
                    } else {
                        iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                        if (iCompareTo4 > 0) {
                            c3 = (char) (cIntValue2 + 1);
                            if (cIntValue2 == '9') {
                                sb.append('9');
                                if (roundOff(sb)) {
                                    i13++;
                                    sb.append(ExternalAnnotationProvider.NONNULL);
                                }
                                return i13 + 1;
                            }
                            i28 = 1;
                            cIntValue2 = c3;
                        } else if (iCompareTo4 != 0) {
                            if ((cIntValue2 & 1) != 1) {
                            }
                            c3 = (char) (cIntValue2 + 1);
                            if (cIntValue2 == '9') {
                                sb.append('9');
                                if (roundOff(sb)) {
                                    i13++;
                                    sb.append(ExternalAnnotationProvider.NONNULL);
                                }
                                return i13 + 1;
                            }
                            i28 = 1;
                            cIntValue2 = c3;
                        } else {
                            i28 = 1;
                        }
                    }
                    sb.append(cIntValue2);
                    return i13 + i28;
                    i26++;
                    c2 = 1;
                }
            } else {
                i24 = 1;
                i25 = 1;
                while (true) {
                    BigInteger[] bigIntegerArrDivideAndRemainder18 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                    bigInteger = bigIntegerArrDivideAndRemainder18[i24];
                    cIntValue = (char) (bigIntegerArrDivideAndRemainder18[0].intValue() + 48);
                    sb.append(cIntValue);
                    if (i25 >= i8) {
                        break;
                        break;
                    }
                    i25++;
                    bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                    i24 = i24;
                }
                cIntValue2 = cIntValue;
                bigInteger2 = bigInteger;
            }
            iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
            if (iCompareTo <= 0) {
                if (roundOff(sb)) {
                    sb.append(ExternalAnnotationProvider.NONNULL);
                    return i13 + 2;
                }
            } else if (roundOff(sb)) {
                sb.append(ExternalAnnotationProvider.NONNULL);
                return i13 + 2;
            }
            return i13 + 1;
        }
        i22 = i21 - 4;
        i4 += i22;
        i17 += i22;
        i16 += i22;
        i23 = i4;
        if (i23 > 0) {
            bigIntegerD2b = bigIntegerD2b.shiftLeft(i23);
        }
        if (i16 > 0) {
            bigIntegerValueOf = bigIntegerValueOf.shiftLeft(i16);
        }
        if (z3) {
            i13--;
            bigIntegerD2b = bigIntegerD2b.multiply(BigInteger.valueOf(10L));
            if (z5) {
                bigIntegerMultiply = bigIntegerMultiply.multiply(BigInteger.valueOf(10L));
            }
            i8 = i10;
        }
        if (i8 > 0) {
        }
        c2 = 1;
        if (z5) {
            if (i17 > 0) {
                bigIntegerMultiply = bigIntegerMultiply.shiftLeft(i17);
            }
            if (z6) {
                bigIntegerMultiply2 = bigIntegerMultiply.shiftLeft(1);
            } else {
                bigIntegerMultiply2 = bigIntegerMultiply;
            }
            i26 = 1;
            while (true) {
                BigInteger[] bigIntegerArrDivideAndRemainder19 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                bigInteger2 = bigIntegerArrDivideAndRemainder19[c2];
                cIntValue2 = (char) (bigIntegerArrDivideAndRemainder19[0].intValue() + 48);
                iCompareTo2 = bigInteger2.compareTo(bigIntegerMultiply);
                bigIntegerSubtract = bigIntegerValueOf.subtract(bigIntegerMultiply2);
                if (bigIntegerSubtract.signum() <= 0) {
                    iCompareTo3 = 1;
                } else {
                    iCompareTo3 = bigInteger2.compareTo(bigIntegerSubtract);
                }
                if (iCompareTo3 == 0) {
                    i27 = 1;
                } else {
                    i27 = 1;
                }
                if (iCompareTo2 >= 0) {
                }
                if (iCompareTo3 <= 0) {
                    i28 = 1;
                } else {
                    iCompareTo4 = bigInteger2.shiftLeft(1).compareTo(bigIntegerValueOf);
                    if (iCompareTo4 > 0) {
                        c3 = (char) (cIntValue2 + 1);
                        if (cIntValue2 == '9') {
                            sb.append('9');
                            if (roundOff(sb)) {
                                i13++;
                                sb.append(ExternalAnnotationProvider.NONNULL);
                            }
                            return i13 + 1;
                        }
                        i28 = 1;
                        cIntValue2 = c3;
                    } else if (iCompareTo4 != 0) {
                        if ((cIntValue2 & 1) != 1) {
                        }
                        c3 = (char) (cIntValue2 + 1);
                        if (cIntValue2 == '9') {
                            sb.append('9');
                            if (roundOff(sb)) {
                                i13++;
                                sb.append(ExternalAnnotationProvider.NONNULL);
                            }
                            return i13 + 1;
                        }
                        i28 = 1;
                        cIntValue2 = c3;
                    } else {
                        i28 = 1;
                    }
                }
                sb.append(cIntValue2);
                return i13 + i28;
                i26++;
                c2 = 1;
            }
        } else {
            i24 = 1;
            i25 = 1;
            while (true) {
                BigInteger[] bigIntegerArrDivideAndRemainder110 = bigIntegerD2b.divideAndRemainder(bigIntegerValueOf);
                bigInteger = bigIntegerArrDivideAndRemainder110[i24];
                cIntValue = (char) (bigIntegerArrDivideAndRemainder110[0].intValue() + 48);
                sb.append(cIntValue);
                if (i25 >= i8) {
                    break;
                    break;
                }
                i25++;
                bigIntegerD2b = bigInteger.multiply(BigInteger.valueOf(10L));
                i24 = i24;
            }
            cIntValue2 = cIntValue;
            bigInteger2 = bigInteger;
        }
        iCompareTo = bigInteger2.shiftLeft(i24).compareTo(bigIntegerValueOf);
        if (iCompareTo <= 0) {
            if (roundOff(sb)) {
                sb.append(ExternalAnnotationProvider.NONNULL);
                return i13 + 2;
            }
        } else if (roundOff(sb)) {
            sb.append(ExternalAnnotationProvider.NONNULL);
            return i13 + 2;
        }
        return i13 + 1;
    }

    private static void stripTrailingZeroes(StringBuilder sb) {
        int length = sb.length();
        while (true) {
            int i = length - 1;
            if (length <= 0 || sb.charAt(i) != '0') {
                break;
            } else {
                length = i;
            }
        }
        sb.setLength(length);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0056  */
    static void JS_dtostr(StringBuilder sb, int i, int i2, double d) {
        boolean z;
        boolean[] zArr = new boolean[1];
        if (i == 2 && (d >= 1.0E21d || d <= -1.0E21d)) {
            i = 0;
        }
        int iJS_dtoa = JS_dtoa(d, dtoaModes[i], i >= 2, i2, zArr, sb);
        int length = sb.length();
        if (iJS_dtoa != 9999) {
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i != 4) {
                                z = false;
                                i2 = 0;
                            } else if (iJS_dtoa < -5 || iJS_dtoa > i2) {
                            }
                        }
                    } else if (i2 >= 0) {
                        i2 += iJS_dtoa;
                    } else {
                        i2 = iJS_dtoa;
                    }
                    z = false;
                } else {
                    i2 = 0;
                }
                z = true;
            } else if (iJS_dtoa < -5 || iJS_dtoa > 21) {
                z = true;
                i2 = 0;
            } else {
                i2 = iJS_dtoa;
                z = false;
            }
            if (length < i2) {
                do {
                    sb.append(ExternalAnnotationProvider.NULLABLE);
                } while (sb.length() != i2);
                length = i2;
            }
            if (z) {
                if (length != 1) {
                    sb.insert(1, '.');
                }
                sb.append('e');
                int i3 = iJS_dtoa - 1;
                if (i3 >= 0) {
                    sb.append('+');
                }
                sb.append(i3);
            } else if (iJS_dtoa != length) {
                if (iJS_dtoa > 0) {
                    sb.insert(iJS_dtoa, '.');
                } else {
                    for (int i4 = 0; i4 < 1 - iJS_dtoa; i4++) {
                        sb.insert(0, ExternalAnnotationProvider.NULLABLE);
                    }
                    sb.insert(1, '.');
                }
            }
        }
        if (zArr[0]) {
            if (word0(d) == Integer.MIN_VALUE && word1(d) == 0) {
                return;
            }
            if ((word0(d) & Exp_mask) != Exp_mask || (word1(d) == 0 && (word0(d) & 1048575) == 0)) {
                sb.insert(0, '-');
            }
        }
    }
}
