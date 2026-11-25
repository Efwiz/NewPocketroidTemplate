package com.android.tools.r8.utils;

import com.android.tools.r8.dex.C0586x;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class P0 {
    static final /* synthetic */ boolean a = true;

    public static int a(C0586x c0586x, long j, int i) {
        int iNumberOfTrailingZeros = ((63 - Long.numberOfTrailingZeros(j)) / 8) + 1;
        if (!a && (iNumberOfTrailingZeros <= 0 || iNumberOfTrailingZeros > i)) {
            throw new AssertionError();
        }
        long j2 = j >> (64 - (iNumberOfTrailingZeros * 8));
        for (int i2 = 0; i2 < iNumberOfTrailingZeros; i2++) {
            c0586x.a((byte) j2);
            j2 >>= 8;
        }
        return iNumberOfTrailingZeros;
    }

    public static int b(C0586x c0586x, long j, int i) {
        int iMax = Math.max((72 - Long.numberOfLeadingZeros((j >> 63) ^ j)) / 8, 1);
        if (!a && (iMax <= 0 || iMax > i)) {
            throw new AssertionError();
        }
        for (int i2 = 0; i2 < iMax; i2++) {
            c0586x.a((byte) j);
            j >>= 8;
        }
        return iMax;
    }

    public static long c(com.android.tools.r8.dex.C c, int i) {
        if (!a && i <= 0) {
            throw new AssertionError();
        }
        long jB = 0;
        int i2 = 0;
        for (int i3 = 1; i3 < i; i3++) {
            jB |= ((long) (c.b() & 255)) << i2;
            i2 += 8;
        }
        return (((long) c.b()) << i2) | jB;
    }

    public static long d(com.android.tools.r8.dex.C c, int i) {
        if (!a && i <= 0) {
            throw new AssertionError();
        }
        long jB = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            jB |= ((long) (c.b() & 255)) << i2;
            i2 += 8;
        }
        return jB;
    }

    public static float b(com.android.tools.r8.dex.C c, int i) {
        return Float.intBitsToFloat((int) (d(c, i) << ((4 - i) * 8)));
    }

    public static int a(C0586x c0586x, float f) {
        return a(c0586x, ((long) Float.floatToIntBits(f)) << 32, 4);
    }

    public static byte[] a(float f) {
        long jFloatToIntBits = ((long) Float.floatToIntBits(f)) << 32;
        int iNumberOfTrailingZeros = ((63 - Long.numberOfTrailingZeros(jFloatToIntBits)) / 8) + 1;
        long j = jFloatToIntBits >> (64 - (iNumberOfTrailingZeros * 8));
        byte[] bArr = new byte[iNumberOfTrailingZeros];
        for (int i = 0; i < iNumberOfTrailingZeros; i++) {
            bArr[i] = (byte) j;
            j >>= 8;
        }
        if (a || iNumberOfTrailingZeros <= 4) {
            return bArr;
        }
        throw new AssertionError();
    }

    public static int c(C0586x c0586x, long j, int i) {
        int iMax = Math.max((71 - Long.numberOfLeadingZeros(j)) / 8, 1);
        if (!a && (iMax <= 0 || iMax > i)) {
            throw new AssertionError();
        }
        for (int i2 = 0; i2 < iMax; i2++) {
            c0586x.a((byte) j);
            j >>= 8;
        }
        return iMax;
    }

    public static double a(com.android.tools.r8.dex.C c, int i) {
        return Double.longBitsToDouble(d(c, i) << ((8 - i) * 8));
    }

    public static int a(C0586x c0586x, double d) {
        return a(c0586x, Double.doubleToLongBits(d), 8);
    }

    public static byte[] a(double d) {
        long jDoubleToLongBits = Double.doubleToLongBits(d);
        int iNumberOfTrailingZeros = ((63 - Long.numberOfTrailingZeros(jDoubleToLongBits)) / 8) + 1;
        long j = jDoubleToLongBits >> (64 - (iNumberOfTrailingZeros * 8));
        byte[] bArr = new byte[iNumberOfTrailingZeros];
        for (int i = 0; i < iNumberOfTrailingZeros; i++) {
            bArr[i] = (byte) j;
            j >>= 8;
        }
        if (a || iNumberOfTrailingZeros <= 8) {
            return bArr;
        }
        throw new AssertionError();
    }
}
