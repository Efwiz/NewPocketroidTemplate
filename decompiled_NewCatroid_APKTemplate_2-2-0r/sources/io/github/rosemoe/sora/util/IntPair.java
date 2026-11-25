package io.github.rosemoe.sora.util;

/* JADX INFO: loaded from: classes11.dex */
public class IntPair {
    public static int getFirst(long j) {
        return (int) (j >> 32);
    }

    public static int getSecond(long j) {
        return (int) (j & 4294967295L);
    }

    private static long toUnsignedLong(int i) {
        return ((long) i) & 4294967295L;
    }

    public static long pack(int i, int i2) {
        return toUnsignedLong(i2) | (toUnsignedLong(i) << 32);
    }
}
