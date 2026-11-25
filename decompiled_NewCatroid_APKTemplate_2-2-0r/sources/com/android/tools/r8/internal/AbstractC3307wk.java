package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3307wk {
    public static final int a(int i) {
        int i2 = i * (-1640531527);
        return i2 ^ (i2 >>> 16);
    }

    public static final int a(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static final long b(long j) {
        long j2 = j * (-7046029254386353131L);
        long j3 = j2 ^ (j2 >>> 32);
        return j3 ^ (j3 >>> 16);
    }

    public static long c(long j) {
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

    public static int b(int i, float f) {
        return Math.min((int) Math.ceil(i * f), i - 1);
    }

    public static int a(int i, float f) {
        long jMax = Math.max(2L, c((long) Math.ceil(i / f)));
        if (jMax <= 1073741824) {
            return (int) jMax;
        }
        throw new IllegalArgumentException("Too large (" + i + " expected elements with load factor " + f + Constant.AFTER_QUTO);
    }
}
