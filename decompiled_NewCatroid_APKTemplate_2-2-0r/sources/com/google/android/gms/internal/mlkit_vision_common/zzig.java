package com.google.android.gms.internal.mlkit_vision_common;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzig {
    private static zzif zza;

    public static synchronized zzhx zza(zzhr zzhrVar) {
        if (zza == null) {
            zza = new zzif(null);
        }
        return zza.get(zzhrVar);
    }

    public static synchronized zzhx zzb(String str) {
        return zza(zzhr.zzd("vision-common").zzd());
    }
}
