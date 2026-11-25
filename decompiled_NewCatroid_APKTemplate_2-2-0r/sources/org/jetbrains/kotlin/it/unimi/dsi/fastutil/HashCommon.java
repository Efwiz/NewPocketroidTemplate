package org.jetbrains.kotlin.it.unimi.dsi.fastutil;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes5.dex */
public class HashCommon {
    public static int long2int(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static int mix(int i) {
        int i2 = i * (-1640531527);
        return i2 ^ (i2 >>> 16);
    }

    public static long mix(long j) {
        long j2 = j * (-7046029254386353131L);
        long j3 = j2 ^ (j2 >>> 32);
        return j3 ^ (j3 >>> 16);
    }

    public static int nextPowerOfTwo(int i) {
        if (i == 0) {
            return 1;
        }
        int i2 = i - 1;
        int i3 = i2 | (i2 >> 1);
        int i4 = i3 | (i3 >> 2);
        int i5 = i4 | (i4 >> 4);
        int i6 = i5 | (i5 >> 8);
        return (i6 | (i6 >> 16)) + 1;
    }

    public static long nextPowerOfTwo(long j) {
        if (j == 0) {
            return 1L;
        }
        long j2 = j - 1;
        long j3 = j2 | (j2 >> 1);
        long j4 = j3 | (j3 >> 2);
        long j5 = j4 | (j4 >> 4);
        long j6 = j5 | (j5 >> 8);
        long j7 = j6 | (j6 >> 16);
        return (j7 | (j7 >> 32)) + 1;
    }

    public static int float2int(float f) {
        return Float.floatToRawIntBits(f);
    }

    public static int double2int(double d) {
        long jDoubleToRawLongBits = Double.doubleToRawLongBits(d);
        return (int) (jDoubleToRawLongBits ^ (jDoubleToRawLongBits >>> 32));
    }

    public static int maxFill(int i, float f) {
        return Math.min((int) Math.ceil(i * f), i - 1);
    }

    public static int arraySize(int i, float f) {
        long jMax = Math.max(2L, nextPowerOfTwo((long) Math.ceil(i / f)));
        if (jMax <= 1073741824) {
            return (int) jMax;
        }
        throw new IllegalArgumentException("Too large (" + i + " expected elements with load factor " + f + Constant.AFTER_QUTO);
    }
}
