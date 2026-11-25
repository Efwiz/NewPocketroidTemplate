package org.jetbrains.kotlin.com.intellij.psi.stubs;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes3.dex */
final class Hash {
    static int mix(int i) {
        int i2 = i * (-1640531527);
        return i2 ^ (i2 >>> 16);
    }

    private static long nextPowerOfTwo(long j) {
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

    Hash() {
    }

    static int arraySize(int i, float f) {
        long jMax = Math.max(2L, nextPowerOfTwo((long) Math.ceil(i / f)));
        if (jMax <= 1073741824) {
            return (int) jMax;
        }
        throw new IllegalArgumentException("Too large (" + i + " expected elements with load factor " + f + Constant.AFTER_QUTO);
    }

    static int maxFill(int i, float f) {
        return Math.min((int) Math.ceil(i * f), i - 1);
    }
}
