package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.math.RoundingMode;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1112Jr {
    static int[] a = {Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    public static boolean a(int i) {
        return (i > 0) & ((i & (i + (-1))) == 0);
    }

    public static int b(int i, RoundingMode roundingMode) {
        int i2;
        WD.a(i, "x");
        int iSqrt = (int) Math.sqrt(i);
        switch (AbstractC1083Ir.a[roundingMode.ordinal()]) {
            case 1:
                if (iSqrt * iSqrt != i) {
                    throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
                }
            case 2:
            case 3:
                return iSqrt;
            case 4:
            case 5:
                i2 = iSqrt * iSqrt;
                return iSqrt + ((~(~(i2 - i))) >>> 31);
            case 6:
            case 7:
            case 8:
                i2 = (iSqrt * iSqrt) + iSqrt;
                return iSqrt + ((~(~(i2 - i))) >>> 31);
            default:
                throw new AssertionError();
        }
    }

    public static int a(int i, RoundingMode roundingMode) {
        if (i <= 0) {
            throw new IllegalArgumentException(new StringBuilder(27).append("x (").append(i).append(") must be > 0").toString());
        }
        switch (AbstractC1083Ir.a[roundingMode.ordinal()]) {
            case 1:
                if (!a(i)) {
                    throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
                }
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 32 - Integer.numberOfLeadingZeros(i - 1);
            case 6:
            case 7:
            case 8:
                int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(i);
                return (31 - iNumberOfLeadingZeros) + ((~(~(((-1257966797) >>> iNumberOfLeadingZeros) - i))) >>> 31);
            default:
                throw new AssertionError();
        }
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    public static int b(int i, int i2) {
        long j = ((long) i) + ((long) i2);
        int i3 = (int) j;
        if (j == ((long) i3)) {
            return i3;
        }
        throw new ArithmeticException(new StringBuilder("checkedAdd".length() + 36).append("overflow: checkedAdd(").append(i).append(", ").append(i2).append(Constant.AFTER_QUTO).toString());
    }

    public static int a(int i, int i2, RoundingMode roundingMode) {
        int i3 = AbstractC3123uM.a;
        roundingMode.getClass();
        if (i2 != 0) {
            int i4 = i / i2;
            int i5 = i - (i2 * i4);
            if (i5 == 0) {
                return i4;
            }
            int i6 = ((i ^ i2) >> 31) | 1;
            switch (AbstractC1083Ir.a[roundingMode.ordinal()]) {
                case 1:
                    if (i5 == 0) {
                        return i4;
                    }
                    throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
                case 2:
                    return i4;
                case 3:
                    if (i6 >= 0) {
                        return i4;
                    }
                    break;
                case 4:
                    break;
                case 5:
                    if (i6 <= 0) {
                        return i4;
                    }
                    break;
                case 6:
                case 7:
                case 8:
                    int iAbs = Math.abs(i5);
                    int iAbs2 = iAbs - (Math.abs(i2) - iAbs);
                    if (iAbs2 == 0) {
                        if (roundingMode != RoundingMode.HALF_UP) {
                            if (!((roundingMode == RoundingMode.HALF_EVEN) & ((i4 & 1) != 0))) {
                                return i4;
                            }
                        }
                    } else if (iAbs2 <= 0) {
                        return i4;
                    }
                default:
                    throw new AssertionError();
            }
            return i4 + i6;
        }
        throw new ArithmeticException("/ by zero");
    }

    public static int a(int i, int i2) {
        WD.a(i, "n");
        WD.a(i2, JvmAnnotationNames.KIND_FIELD_NAME);
        int i3 = 0;
        boolean z = i2 <= i;
        int i4 = AbstractC3123uM.a;
        if (z) {
            if (i2 > (i >> 1)) {
                i2 = i - i2;
            }
            int[] iArr = a;
            if (i2 >= 17 || i > iArr[i2]) {
                return Integer.MAX_VALUE;
            }
            if (i2 == 0) {
                return 1;
            }
            if (i2 == 1) {
                return i;
            }
            long j = 1;
            while (i3 < i2) {
                long j2 = j * ((long) (i - i3));
                i3++;
                j = j2 / ((long) i3);
            }
            return (int) j;
        }
        throw new IllegalArgumentException(AbstractC2619nY.a("k (%s) > n (%s)", Integer.valueOf(i2), Integer.valueOf(i)));
    }
}
