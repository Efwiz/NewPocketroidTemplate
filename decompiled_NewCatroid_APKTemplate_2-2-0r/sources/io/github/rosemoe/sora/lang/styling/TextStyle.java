package io.github.rosemoe.sora.lang.styling;

/* JADX INFO: loaded from: classes11.dex */
public class TextStyle {
    public static final long BACKGROUND_BITS = 274877382656L;
    public static final long BOLD_BIT = 274877906944L;
    public static final int COLOR_ID_BIT_COUNT = 19;
    public static final long FOREGROUND_BITS = 524287;
    public static final long ITALICS_BIT = 549755813888L;
    public static final long NO_COMPLETION_BIT = 2199023255552L;
    public static final long STRIKETHROUGH_BIT = 1099511627776L;

    public static int getBackgroundColorId(long j) {
        return (int) ((j & BACKGROUND_BITS) >> 19);
    }

    public static int getForegroundColorId(long j) {
        return (int) (j & FOREGROUND_BITS);
    }

    public static long getStyleBits(long j) {
        return j & 1924145348608L;
    }

    public static boolean isBold(long j) {
        return (j & 274877906944L) != 0;
    }

    public static boolean isItalics(long j) {
        return (j & 549755813888L) != 0;
    }

    public static boolean isNoCompletion(long j) {
        return (j & 2199023255552L) != 0;
    }

    public static boolean isStrikeThrough(long j) {
        return (j & 1099511627776L) != 0;
    }

    public static long makeStyle(int i) {
        checkColorId(i);
        return i;
    }

    public static long makeStyle(int i, boolean z) {
        checkColorId(i);
        return (z ? 2199023255552L : 0L) | ((long) i);
    }

    public static long makeStyle(int i, int i2, boolean z, boolean z2, boolean z3) {
        return makeStyle(i, i2, z, z2, z3, false);
    }

    public static long makeStyle(int i, int i2, boolean z, boolean z2, boolean z3, boolean z4) {
        checkColorId(i);
        checkColorId(i2);
        return (z4 ? 2199023255552L : 0L) | (z2 ? 549755813888L : 0L) | (((long) i) + (((long) i2) << 19)) | (z ? 274877906944L : 0L) | (z3 ? 1099511627776L : 0L);
    }

    public static void checkColorId(int i) {
        if (i > 524287 || i < 0) {
            throw new IllegalArgumentException("color id must be positive and bit count is less than 19");
        }
    }
}
