package org.eclipse.jgit.util;

/* JADX INFO: loaded from: classes14.dex */
public class Paths {
    private static int lastPathChar(int i) {
        return (i & 61440) == 16384 ? 47 : 0;
    }

    public static String stripTrailingSeparator(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        int length = str.length();
        if (str.charAt(str.length() - 1) != '/') {
            return str;
        }
        while (true) {
            int i = length - 1;
            if (str.charAt(length - 2) != '/') {
                return str.substring(0, i);
            }
            length = i;
        }
    }

    public static int compare(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4, int i5, int i6) {
        int iCoreCompare = coreCompare(bArr, i, i2, i3, bArr2, i4, i5, i6);
        return iCoreCompare == 0 ? lastPathChar(i3) - lastPathChar(i6) : iCoreCompare;
    }

    public static int compareSameName(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4, int i5) {
        return coreCompare(bArr, i, i2, 16384, bArr2, i3, i4, i5);
    }

    private static int coreCompare(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4, int i5, int i6) {
        int iLastPathChar;
        int iLastPathChar2;
        while (i < i2 && i4 < i5) {
            int i7 = i + 1;
            int i8 = i4 + 1;
            int i9 = (bArr[i] & 255) - (bArr2[i4] & 255);
            if (i9 != 0) {
                return i9;
            }
            i = i7;
            i4 = i8;
        }
        if (i < i2) {
            iLastPathChar = bArr[i] & 255;
            iLastPathChar2 = lastPathChar(i6);
        } else {
            if (i4 >= i5) {
                return 0;
            }
            iLastPathChar = lastPathChar(i3);
            iLastPathChar2 = bArr2[i4] & 255;
        }
        return iLastPathChar - iLastPathChar2;
    }

    private Paths() {
    }
}
