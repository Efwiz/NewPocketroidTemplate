package com.android.tools.r8.internal;

import com.esotericsoftware.kryo.util.Util;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class V5 {
    static {
        new U5();
    }

    public static void a(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        int i = AbstractC3123uM.a;
        inputStream.getClass();
        byte[] bArr = new byte[8192];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 == -1) {
                return;
            } else {
                fileOutputStream.write(bArr, 0, i2);
            }
        }
    }

    public static byte[] a(InputStream inputStream) throws IOException {
        byte[] bArr;
        int i = AbstractC3123uM.a;
        inputStream.getClass();
        ArrayDeque arrayDeque = new ArrayDeque(20);
        int iA = 8192;
        int i2 = 0;
        while (i2 < 2147483639) {
            int iMin = Math.min(iA, Util.maxArraySize - i2);
            byte[] bArr2 = new byte[iMin];
            arrayDeque.add(bArr2);
            int i3 = 0;
            while (i3 < iMin) {
                int i4 = inputStream.read(bArr2, i3, iMin - i3);
                if (i4 == -1) {
                    bArr = new byte[i2];
                    int i5 = i2;
                    while (i5 > 0) {
                        byte[] bArr3 = (byte[]) arrayDeque.remove();
                        int iMin2 = Math.min(i5, bArr3.length);
                        System.arraycopy(bArr3, 0, bArr, i2 - i5, iMin2);
                        i5 -= iMin2;
                    }
                    return bArr;
                }
                i3 += i4;
                i2 += i4;
            }
            iA = AbstractC0968Es.a(((long) iA) * ((long) 2));
        }
        if (inputStream.read() == -1) {
            bArr = new byte[Util.maxArraySize];
            int i6 = 2147483639;
            while (i6 > 0) {
                byte[] bArr4 = (byte[]) arrayDeque.remove();
                int iMin3 = Math.min(i6, bArr4.length);
                System.arraycopy(bArr4, 0, bArr, Util.maxArraySize - i6, iMin3);
                i6 -= iMin3;
            }
            return bArr;
        }
        throw new OutOfMemoryError("input is too large to fit in a byte array");
    }
}
