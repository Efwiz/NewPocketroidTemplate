package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public final class BitUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/BitUtil", "invalidMaskError"));
    }

    public static void assertOneBitMask(int i) {
    }

    private static void assertOneBitMask(long j) {
    }

    public static boolean isSet(byte b, byte b2) {
        assertOneBitMask(b2);
        return (b & b2) == b2;
    }

    public static boolean isSet(int i, int i2) {
        assertOneBitMask(i2);
        return (i & i2) == i2;
    }

    public static byte set(byte b, byte b2, boolean z) {
        assertOneBitMask(b2);
        return (byte) (z ? b | b2 : b & (~b2));
    }

    public static int set(int i, int i2, boolean z) {
        assertOneBitMask(i2);
        return z ? i | i2 : i & (~i2);
    }

    public static int clear(int i, int i2) {
        return set(i, i2, false);
    }

    private static void assertOneBitMask(byte b) {
        assertOneBitMask(((long) b) & 255);
    }

    private static String invalidMaskError(long j) {
        String str = "Mask must have only one bit set, but got: " + Long.toBinaryString(j);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return str;
    }
}
