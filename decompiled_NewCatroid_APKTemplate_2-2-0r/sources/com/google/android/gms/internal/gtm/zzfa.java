package com.google.android.gms.internal.gtm;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
@Deprecated
public final class zzfa {
    private static volatile Logger zza = new zzcu();

    public static Logger zza() {
        return zza;
    }

    public static void zzb(String str, Object obj) {
        String string;
        zzfb zzfbVarZza = zzfb.zza();
        if (zzfbVarZza != null) {
            zzfbVarZza.zzK(str, obj);
        } else if (zzf(3)) {
            if (obj != null) {
                String str2 = (String) obj;
                StringBuilder sb = new StringBuilder(str.length() + 1 + str2.length());
                sb.append(str);
                sb.append(":");
                sb.append(str2);
                string = sb.toString();
            } else {
                string = str;
            }
            Log.e(zzeu.zzc.zzb(), string);
        }
        Logger logger = zza;
        if (logger != null) {
            logger.error(str);
        }
    }

    public static void zzc(Logger logger) {
        zza = logger;
    }

    public static void zzd(String str) {
        zzfb zzfbVarZza = zzfb.zza();
        if (zzfbVarZza != null) {
            zzfbVarZza.zzO(str);
        } else if (zzf(0)) {
            Log.v(zzeu.zzc.zzb(), str);
        }
        Logger logger = zza;
        if (logger != null) {
            logger.verbose(str);
        }
    }

    public static void zze(String str) {
        zzfb zzfbVarZza = zzfb.zza();
        if (zzfbVarZza != null) {
            zzfbVarZza.zzR(str);
        } else if (zzf(2)) {
            Log.w(zzeu.zzc.zzb(), str);
        }
        Logger logger = zza;
        if (logger != null) {
            logger.warn(str);
        }
    }

    public static boolean zzf(int i) {
        return zza != null && zza.getLogLevel() <= i;
    }
}
