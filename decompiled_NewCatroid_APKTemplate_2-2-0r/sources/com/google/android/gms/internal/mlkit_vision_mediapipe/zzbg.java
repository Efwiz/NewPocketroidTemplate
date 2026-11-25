package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.logging.Level;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbg {
    public static zzbf zza(Level level) {
        int iIntValue = level.intValue();
        if (iIntValue >= Level.SEVERE.intValue()) {
            return zzbf.ERROR;
        }
        if (iIntValue >= Level.WARNING.intValue()) {
            return zzbf.WARN;
        }
        if (iIntValue >= Level.INFO.intValue()) {
            return zzbf.INFO;
        }
        return iIntValue >= Level.FINE.intValue() ? zzbf.DEBUG : zzbf.VERBOSE;
    }

    public static String zza(String str, String str2, boolean z) {
        if (str.length() + str2.length() > 23) {
            str2 = str2.substring(Math.max(str2.lastIndexOf(46), str2.lastIndexOf(36)) + 1);
        }
        String strValueOf = String.valueOf(str);
        String strValueOf2 = String.valueOf(str2);
        String strConcat = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
        return !z ? strConcat : strConcat.substring(0, Math.min(strConcat.length(), 23));
    }
}
