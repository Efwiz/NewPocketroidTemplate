package com.hivemq.client.internal.util;

/* JADX INFO: loaded from: classes10.dex */
public final class Utf8Util {
    private static final long INVALID_CODE_POINTS = -1152921504606846976L;
    private static final long NON_SHORTEST_FORM = Long.MIN_VALUE;
    private static final long NO_FOLLOWING_BYTE = -4611686018427387904L;
    private static final long UTF_16_SURROGATES = -2305843009213693952L;

    public static long isWellFormed(byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                i = i2;
            } else if (b < -32) {
                if (i2 == length) {
                    return ((long) i2) + NO_FOLLOWING_BYTE;
                }
                if (b < -62) {
                    return ((long) i2) - Long.MIN_VALUE;
                }
                i += 2;
                if (bArr[i2] > -65) {
                    return ((long) i) + NO_FOLLOWING_BYTE;
                }
            } else if (b < -16) {
                int i3 = i + 2;
                if (i3 >= length) {
                    return ((long) i2) + NO_FOLLOWING_BYTE;
                }
                byte b2 = bArr[i2];
                if (b2 > -65) {
                    return ((long) i3) + NO_FOLLOWING_BYTE;
                }
                if (b == -32 && b2 < -96) {
                    return ((long) i3) - Long.MIN_VALUE;
                }
                if (b == -19 && b2 >= -96) {
                    return ((long) i3) + UTF_16_SURROGATES;
                }
                i += 3;
                if (bArr[i3] > -65) {
                    return ((long) i) + NO_FOLLOWING_BYTE;
                }
            } else {
                if (i + 3 >= length) {
                    return ((long) i2) + NO_FOLLOWING_BYTE;
                }
                int i4 = i + 2;
                byte b3 = bArr[i2];
                if (b3 > -65) {
                    return ((long) i4) + NO_FOLLOWING_BYTE;
                }
                if (b == -16 && b3 < -112) {
                    return ((long) i4) - Long.MIN_VALUE;
                }
                if ((b == -12 && b3 > -113) || b > -12) {
                    return ((long) i4) + INVALID_CODE_POINTS;
                }
                int i5 = i + 3;
                if (bArr[i4] > -65) {
                    return ((long) i5) + NO_FOLLOWING_BYTE;
                }
                i += 4;
                if (bArr[i5] > -65) {
                    return ((long) i) + NO_FOLLOWING_BYTE;
                }
            }
        }
        return 0L;
    }

    public static int encodedLength(String str) {
        int length = str.length();
        int i = 0;
        int i2 = length;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt > 127) {
                int i3 = i2 + 1;
                if (cCharAt > 2047) {
                    i2 += 2;
                    if (Character.isHighSurrogate(cCharAt)) {
                        i++;
                    }
                } else {
                    i2 = i3;
                }
            }
            i++;
        }
        return i2;
    }

    private Utf8Util() {
    }
}
