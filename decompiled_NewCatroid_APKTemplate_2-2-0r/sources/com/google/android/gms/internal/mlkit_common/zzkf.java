package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkf {
    private static zzkf zza;

    private zzkf() {
    }

    public static synchronized zzkf zza() {
        if (zza == null) {
            zza = new zzkf();
        }
        return zza;
    }

    public static void zzb() {
        zzke.zza();
    }
}
