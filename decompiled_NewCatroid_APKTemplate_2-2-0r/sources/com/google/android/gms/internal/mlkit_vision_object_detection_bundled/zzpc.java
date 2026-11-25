package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.util.Log;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzpc {
    private static final int[] zzc = {0, 0, 1, 2, 4, 8, 16};
    public static final zzoz zzb = new Object() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzoz
    };
    public static final Random zza = new SecureRandom();

    /* JADX WARN: Code duplicated, block: B:18:0x003a  */
    /* JADX WARN: Code duplicated, block: B:19:0x003f  */
    public static boolean zza(zzpb zzpbVar) throws InterruptedException {
        String strValueOf;
        String str;
        int[] iArr = zzc;
        for (int i = 0; i < 7; i++) {
            int i2 = iArr[i];
            if (i2 > 0) {
                int i3 = i2 * 60000;
                Thread.sleep((i3 / 2) + zza.nextInt(i3));
            }
            try {
                if (zzpbVar.zza()) {
                    return true;
                }
            } catch (zzpa e) {
                e = e;
                strValueOf = String.valueOf(e.getMessage());
                if (strValueOf.length() != 0) {
                    str = "retryWithRandomizedExponentialBackoff: ".concat(strValueOf);
                } else {
                    str = new String("retryWithRandomizedExponentialBackoff: ");
                }
                Log.e("MLK ExponentialBackoff", str, e);
            } catch (IOException e2) {
                e = e2;
                strValueOf = String.valueOf(e.getMessage());
                if (strValueOf.length() != 0) {
                    str = "retryWithRandomizedExponentialBackoff: ".concat(strValueOf);
                } else {
                    str = new String("retryWithRandomizedExponentialBackoff: ");
                }
                Log.e("MLK ExponentialBackoff", str, e);
            } catch (InterruptedException e3) {
                Log.i("MLK ExponentialBackoff", "retryWithRandomizedExponentialBackoff: interrupted");
                throw e3;
            }
        }
        return false;
    }
}
