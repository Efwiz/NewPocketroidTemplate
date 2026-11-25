package org.eclipse.jdt.internal.compiler.util;

import org.eclipse.jdt.internal.compiler.lookup.TagBits;

/* JADX INFO: loaded from: classes14.dex */
public class FloatUtil {
    private static final int DOUBLE_EXPONENT_BIAS = 1023;
    private static final int DOUBLE_EXPONENT_SHIFT = 52;
    private static final int DOUBLE_FRACTION_WIDTH = 52;
    private static final int DOUBLE_PRECISION = 53;
    private static final int MAX_DOUBLE_EXPONENT = 1023;
    private static final int MAX_SINGLE_EXPONENT = 127;
    private static final int MIN_NORMALIZED_DOUBLE_EXPONENT = -1022;
    private static final int MIN_NORMALIZED_SINGLE_EXPONENT = -126;
    private static final int MIN_UNNORMALIZED_DOUBLE_EXPONENT = -1075;
    private static final int MIN_UNNORMALIZED_SINGLE_EXPONENT = -150;
    private static final int SINGLE_EXPONENT_BIAS = 127;
    private static final int SINGLE_EXPONENT_SHIFT = 23;
    private static final int SINGLE_FRACTION_WIDTH = 23;
    private static final int SINGLE_PRECISION = 24;

    public static float valueOfHexFloatLiteral(char[] cArr) {
        return Float.intBitsToFloat((int) convertHexFloatingPointLiteralToBits(cArr));
    }

    public static double valueOfHexDoubleLiteral(char[] cArr) {
        return Double.longBitsToDouble(convertHexFloatingPointLiteralToBits(cArr));
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0135  */
    /* JADX WARN: Code duplicated, block: B:101:0x013c  */
    /* JADX WARN: Code duplicated, block: B:104:0x0145  */
    /* JADX WARN: Code duplicated, block: B:106:0x014d  */
    /* JADX WARN: Code duplicated, block: B:108:0x0151  */
    /* JADX WARN: Code duplicated, block: B:109:0x015c  */
    /* JADX WARN: Code duplicated, block: B:111:0x0160  */
    /* JADX WARN: Code duplicated, block: B:112:0x0165  */
    /* JADX WARN: Code duplicated, block: B:52:0x007d  */
    /* JADX WARN: Code duplicated, block: B:55:0x0082 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:56:0x0083  */
    /* JADX WARN: Code duplicated, block: B:58:0x008e  */
    /* JADX WARN: Code duplicated, block: B:60:0x0098  */
    /* JADX WARN: Code duplicated, block: B:62:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:63:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:64:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:66:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:69:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:71:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:73:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:74:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:76:0x00ce A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:77:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:78:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:79:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:82:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:83:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:85:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:86:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:88:0x0102  */
    /* JADX WARN: Code duplicated, block: B:89:0x0107  */
    /* JADX WARN: Code duplicated, block: B:91:0x010e  */
    /* JADX WARN: Code duplicated, block: B:93:0x0112  */
    /* JADX WARN: Code duplicated, block: B:95:0x0122  */
    /* JADX WARN: Code duplicated, block: B:96:0x0127  */
    /* JADX WARN: Code duplicated, block: B:98:0x012d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:99:0x012f  */
    private static long convertHexFloatingPointLiteralToBits(char[] cArr) {
        int i;
        boolean z;
        long j;
        int i2;
        long j2;
        int i3;
        int i4;
        int i5;
        int iFloatToIntBits;
        long j3;
        long j4;
        int i6;
        int i7;
        int i8;
        long j5;
        int i9;
        char c;
        int length = cArr.length;
        int i10 = 0;
        if (cArr[0] != '0') {
            throw new NumberFormatException();
        }
        char c2 = cArr[1];
        if (c2 != 'X' && c2 != 'x') {
            throw new NumberFormatException();
        }
        int i11 = 2;
        int i12 = 2;
        int i13 = -1;
        while (true) {
            char c3 = cArr[i12];
            if (c3 == '.') {
                i13 = i12;
                i10 = 0;
                i12++;
            } else {
                if (c3 != '0') {
                    break;
                }
                i12++;
                i10 = 0;
            }
            i11 = 2;
        }
        int i14 = i10;
        long j6 = 0;
        int i15 = -1;
        while (true) {
            char c4 = cArr[i12];
            if (c4 != '.') {
                switch (c4) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        i = c4 - '0';
                        break;
                    default:
                        switch (c4) {
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                i = c4 - '7';
                                break;
                            default:
                                switch (c4) {
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                        i = c4 - 'W';
                                        break;
                                    default:
                                        if (i13 < 0) {
                                            i13 = i12;
                                        }
                                        if (c4 != 'P' && c4 != 'p') {
                                            throw new NumberFormatException();
                                        }
                                        int i16 = i12 + 1;
                                        int i17 = i10;
                                        int i18 = 1;
                                        while (i16 < length) {
                                            char c5 = cArr[i16];
                                            if (c5 == '+') {
                                                i16++;
                                                i18 = 1;
                                            } else if (c5 != '-') {
                                                switch (c5) {
                                                    case '0':
                                                    case '1':
                                                    case '2':
                                                    case '3':
                                                    case '4':
                                                    case '5':
                                                    case '6':
                                                    case '7':
                                                    case '8':
                                                    case '9':
                                                        i17 = (i17 * 10) + (c5 - '0');
                                                        i16++;
                                                        break;
                                                    default:
                                                        if (i16 < length || (c = cArr[i16]) == 'D') {
                                                            z = true;
                                                        } else {
                                                            if (c != 'F') {
                                                                if (c == 'd') {
                                                                    z = true;
                                                                } else if (c != 'f') {
                                                                    throw new NumberFormatException();
                                                                }
                                                            }
                                                            z = false;
                                                        }
                                                        if (j6 == 0) {
                                                            return 0L;
                                                        }
                                                        j = j6 >>> (i14 - 4);
                                                        if ((8 & j) == 0) {
                                                            i9 = i14 - 1;
                                                            if ((j & 4) == 0) {
                                                                i9 = i14 - 2;
                                                                if ((j & 2) == 0) {
                                                                    i14 -= 3;
                                                                    i2 = 3;
                                                                } else {
                                                                    i2 = i11;
                                                                }
                                                            } else {
                                                                i2 = 1;
                                                            }
                                                            i14 = i9;
                                                        } else {
                                                            i2 = 0;
                                                        }
                                                        if (z) {
                                                            if (i14 > 53) {
                                                                long j7 = j6 >>> (i14 - 54);
                                                                j5 = j7 + (1 & j7);
                                                                j4 = j5 >>> 1;
                                                                if ((j4 & 9007199254740992L) != 0) {
                                                                    j4 = j5 >>> i11;
                                                                    i2--;
                                                                }
                                                            } else {
                                                                j4 = j6 << (53 - i14);
                                                            }
                                                            if (i14 > 0) {
                                                                i6 = 0;
                                                            } else if (i15 < i13) {
                                                                i6 = ((i13 - i15) * 4) - i2;
                                                            } else {
                                                                i6 = (((i15 - i13) - 1) * (-4)) - i2;
                                                            }
                                                            i7 = (i18 * i17) + i6;
                                                            i8 = i7 - 1;
                                                            if (i8 > 1023) {
                                                                return Double.doubleToLongBits(Double.POSITIVE_INFINITY);
                                                            }
                                                            if (i8 >= MIN_NORMALIZED_DOUBLE_EXPONENT) {
                                                                return (((long) (i7 - MIN_NORMALIZED_DOUBLE_EXPONENT)) << 52) | ((-4503599627370497L) & j4);
                                                            }
                                                            return i8 > MIN_UNNORMALIZED_DOUBLE_EXPONENT ? j4 >>> ((-1021) - i7) : Double.doubleToLongBits(Double.NaN);
                                                        }
                                                        if (i14 > 24) {
                                                            long j8 = j6 >>> (i14 - 25);
                                                            j3 = j8 + (1 & j8);
                                                            j2 = j3 >>> 1;
                                                            if ((j2 & TagBits.HasUnresolvedTypeVariables) != 0) {
                                                                j2 = j3 >>> i11;
                                                                i2--;
                                                            }
                                                        } else {
                                                            j2 = j6 << (24 - i14);
                                                        }
                                                        if (i14 > 0) {
                                                            i3 = 0;
                                                        } else if (i15 < i13) {
                                                            i3 = ((i13 - i15) * 4) - i2;
                                                        } else {
                                                            i3 = (((i15 - i13) - 1) * (-4)) - i2;
                                                        }
                                                        i4 = (i18 * i17) + i3;
                                                        i5 = i4 - 1;
                                                        if (i5 > 127) {
                                                            iFloatToIntBits = Float.floatToIntBits(Float.POSITIVE_INFINITY);
                                                        } else {
                                                            if (i5 >= MIN_NORMALIZED_SINGLE_EXPONENT) {
                                                                return (((long) (i4 - MIN_NORMALIZED_SINGLE_EXPONENT)) << 23) | ((-8388609) & j2);
                                                            }
                                                            if (i5 > MIN_UNNORMALIZED_SINGLE_EXPONENT) {
                                                                return j2 >>> ((-125) - i4);
                                                            }
                                                            iFloatToIntBits = Float.floatToIntBits(Float.NaN);
                                                        }
                                                        return iFloatToIntBits;
                                                }
                                            } else {
                                                i16++;
                                                i18 = -1;
                                            }
                                        }
                                        if (i16 < length) {
                                            z = true;
                                        } else {
                                            z = true;
                                        }
                                        if (j6 == 0) {
                                            return 0L;
                                        }
                                        j = j6 >>> (i14 - 4);
                                        if ((8 & j) == 0) {
                                            i9 = i14 - 1;
                                            if ((j & 4) == 0) {
                                                i9 = i14 - 2;
                                                if ((j & 2) == 0) {
                                                    i14 -= 3;
                                                    i2 = 3;
                                                } else {
                                                    i2 = i11;
                                                }
                                            } else {
                                                i2 = 1;
                                            }
                                            i14 = i9;
                                        } else {
                                            i2 = 0;
                                        }
                                        if (z) {
                                            if (i14 > 53) {
                                                long j9 = j6 >>> (i14 - 54);
                                                j5 = j9 + (1 & j9);
                                                j4 = j5 >>> 1;
                                                if ((j4 & 9007199254740992L) != 0) {
                                                    j4 = j5 >>> i11;
                                                    i2--;
                                                }
                                            } else {
                                                j4 = j6 << (53 - i14);
                                            }
                                            if (i14 > 0) {
                                                i6 = 0;
                                            } else if (i15 < i13) {
                                                i6 = ((i13 - i15) * 4) - i2;
                                            } else {
                                                i6 = (((i15 - i13) - 1) * (-4)) - i2;
                                            }
                                            i7 = (i18 * i17) + i6;
                                            i8 = i7 - 1;
                                            if (i8 > 1023) {
                                                return Double.doubleToLongBits(Double.POSITIVE_INFINITY);
                                            }
                                            if (i8 >= MIN_NORMALIZED_DOUBLE_EXPONENT) {
                                                return (((long) (i7 - MIN_NORMALIZED_DOUBLE_EXPONENT)) << 52) | ((-4503599627370497L) & j4);
                                            }
                                            if (i8 > MIN_UNNORMALIZED_DOUBLE_EXPONENT) {
                                            }
                                        }
                                        if (i14 > 24) {
                                            long j10 = j6 >>> (i14 - 25);
                                            j3 = j10 + (1 & j10);
                                            j2 = j3 >>> 1;
                                            if ((j2 & TagBits.HasUnresolvedTypeVariables) != 0) {
                                                j2 = j3 >>> i11;
                                                i2--;
                                            }
                                        } else {
                                            j2 = j6 << (24 - i14);
                                        }
                                        if (i14 > 0) {
                                            i3 = 0;
                                        } else if (i15 < i13) {
                                            i3 = ((i13 - i15) * 4) - i2;
                                        } else {
                                            i3 = (((i15 - i13) - 1) * (-4)) - i2;
                                        }
                                        i4 = (i18 * i17) + i3;
                                        i5 = i4 - 1;
                                        if (i5 > 127) {
                                            iFloatToIntBits = Float.floatToIntBits(Float.POSITIVE_INFINITY);
                                        } else {
                                            if (i5 >= MIN_NORMALIZED_SINGLE_EXPONENT) {
                                                return (((long) (i4 - MIN_NORMALIZED_SINGLE_EXPONENT)) << 23) | ((-8388609) & j2);
                                            }
                                            if (i5 > MIN_UNNORMALIZED_SINGLE_EXPONENT) {
                                                return j2 >>> ((-125) - i4);
                                            }
                                            iFloatToIntBits = Float.floatToIntBits(Float.NaN);
                                        }
                                        return iFloatToIntBits;
                                }
                                break;
                        }
                        break;
                }
                if (i14 == 0) {
                    j6 = i;
                    i15 = i12;
                    i14 = 4;
                } else if (i14 < 60) {
                    i14 += 4;
                    j6 = ((long) i) | (j6 << 4);
                }
                i12++;
                i10 = 0;
            } else {
                i13 = i12;
                i10 = 0;
                i12++;
            }
            i11 = 2;
        }
    }
}
