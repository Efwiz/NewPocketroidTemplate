package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public final class MathUtil {
    public static int nonNegativeAbs(int i) {
        if (i >= 0) {
            return i;
        }
        if (i == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return -i;
    }

    public static int clamp(int i, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException(i2 + ">" + i3);
        }
        return Math.min(i3, Math.max(i, i2));
    }
}
