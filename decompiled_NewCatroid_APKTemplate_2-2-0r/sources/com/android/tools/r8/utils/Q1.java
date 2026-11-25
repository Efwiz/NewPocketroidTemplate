package com.android.tools.r8.utils;

import com.android.tools.r8.dex.AbstractC0575m;
import com.android.tools.r8.dex.C0586x;
import java.util.Arrays;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class Q1 {
    static final /* synthetic */ boolean a = true;

    public static int a(AbstractC0575m abstractC0575m) {
        int i;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte b = abstractC0575m.b();
            i2 |= (b & 127) << i3;
            i = i3 + 7;
            if ((b & (-128)) != -128) {
                break;
            }
            i3 = i;
        }
        int i4 = 1 << (i3 + 6);
        if (a || i <= 35) {
            return (i2 ^ i4) - i4;
        }
        throw new AssertionError();
    }

    public static int b(AbstractC0575m abstractC0575m) {
        byte b;
        int i = 0;
        int i2 = 0;
        do {
            b = abstractC0575m.b();
            i |= (b & 127) << i2;
            i2 += 7;
        } while ((b & (-128)) == -128);
        boolean z = a;
        if (!z && i2 > 35) {
            throw new AssertionError();
        }
        if (z || i >= 0) {
            return i;
        }
        throw new AssertionError();
    }

    public static int c(int i) {
        if (i < 0) {
            i = ~i;
        }
        return (39 - Integer.numberOfLeadingZeros(i)) / 7;
    }

    public static int d(int i) {
        return Math.max(1, (38 - Integer.numberOfLeadingZeros(i)) / 7);
    }

    public static byte[] a(int i) {
        byte[] bArr = new byte[5];
        int i2 = i >> 7;
        int i3 = i >= 0 ? 0 : -1;
        int i4 = 0;
        boolean z = true;
        while (true) {
            int i5 = i2;
            int i6 = i;
            i = i5;
            if (z) {
                z = (i == i3 && (i & 1) == ((i6 >> 6) & 1)) ? false : true;
                int i7 = i4 + 1;
                bArr[i4] = (byte) ((i6 & 127) | (z ? 128 : 0));
                i2 = i >> 7;
                i4 = i7;
            } else {
                return Arrays.copyOf(bArr, i4);
            }
        }
    }

    public static byte[] b(int i) {
        byte[] bArr = new byte[5];
        int i2 = i >>> 7;
        int i3 = 0;
        while (true) {
            int i4 = i2;
            int i5 = i;
            i = i4;
            if (i != 0) {
                bArr[i3] = (byte) ((i5 & 127) | 128);
                i2 = i >>> 7;
                i3++;
            } else {
                bArr[i3] = (byte) (i5 & 127);
                return Arrays.copyOf(bArr, i3 + 1);
            }
        }
    }

    public static void b(C0586x c0586x, int i) {
        while (true) {
            int i2 = i;
            i >>>= 7;
            if (i != 0) {
                c0586x.a((byte) ((i2 & 127) | 128));
            } else {
                c0586x.a((byte) (i2 & 127));
                return;
            }
        }
    }

    public static void a(C0586x c0586x, int i) {
        int i2 = i >> 7;
        int i3 = (Integer.MIN_VALUE & i) == 0 ? 0 : -1;
        boolean z = true;
        while (true) {
            int i4 = i2;
            int i5 = i;
            i = i4;
            if (!z) {
                return;
            }
            z = (i == i3 && (i & 1) == ((i5 >> 6) & 1)) ? false : true;
            c0586x.a((byte) ((i5 & 127) | (z ? 128 : 0)));
            i2 = i >> 7;
        }
    }
}
