package com.hivemq.client.internal.util;

/* JADX INFO: loaded from: classes10.dex */
public final class ByteArrayUtil {
    public static boolean equals(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        if (i2 - i != i4 - i3) {
            return false;
        }
        if (bArr == bArr2 && i == i3) {
            return true;
        }
        while (i < i2) {
            if (bArr[i] != bArr2[i3]) {
                return false;
            }
            i++;
            i3++;
        }
        return true;
    }

    public static int hashCode(byte[] bArr, int i, int i2) {
        int i3 = 1;
        while (i < i2) {
            i3 = (i3 * 31) + bArr[i];
            i++;
        }
        return i3;
    }

    public static int indexOf(byte[] bArr, int i, byte b) {
        while (i < bArr.length) {
            if (bArr[i] == b) {
                return i;
            }
            i++;
        }
        return bArr.length;
    }

    private ByteArrayUtil() {
    }
}
