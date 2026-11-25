package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.esotericsoftware.kryo.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Queue;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcl {
    private static final OutputStream zza = new zzck();

    private static byte[] zza(Queue<byte[]> queue, int i) {
        byte[] bArr = new byte[i];
        int i2 = i;
        while (i2 > 0) {
            byte[] bArrRemove = queue.remove();
            int iMin = Math.min(i2, bArrRemove.length);
            System.arraycopy(bArrRemove, 0, bArr, i - i2, iMin);
            i2 -= iMin;
        }
        return bArr;
    }

    public static byte[] zza(InputStream inputStream) throws IOException {
        zza.zza(inputStream);
        ArrayDeque arrayDeque = new ArrayDeque(20);
        int iZza = 8192;
        int i = 0;
        while (i < 2147483639) {
            int iMin = Math.min(iZza, Util.maxArraySize - i);
            byte[] bArr = new byte[iMin];
            arrayDeque.add(bArr);
            int i2 = 0;
            while (i2 < iMin) {
                int i3 = inputStream.read(bArr, i2, iMin - i2);
                if (i3 == -1) {
                    return zza(arrayDeque, i);
                }
                i2 += i3;
                i += i3;
            }
            iZza = zzcn.zza(iZza, 2);
        }
        if (inputStream.read() == -1) {
            return zza(arrayDeque, Util.maxArraySize);
        }
        throw new OutOfMemoryError("input is too large to fit in a byte array");
    }
}
