package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.util.Base64;
import android.util.Log;
import java.security.SecureRandom;
import java.util.Random;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zznf {
    static final Random zza = new SecureRandom();

    public static String zza() {
        byte[] bArr = new byte[17];
        zza.nextBytes(bArr);
        bArr[0] = (byte) ((bArr[0] & 15) | 112);
        String strSubstring = Base64.encodeToString(bArr, 11).substring(0, 22);
        String strValueOf = String.valueOf(strSubstring);
        Log.d("MLKitInstallationIdGenerator", strValueOf.length() != 0 ? "Generated installation id: ".concat(strValueOf) : new String("Generated installation id: "));
        return strSubstring;
    }
}
