package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzahn {
    private static zzahm zza;

    public static synchronized zzahd zza(zzagx zzagxVar) {
        if (zza == null) {
            zza = new zzahm(null);
        }
        return zza.get(zzagxVar);
    }

    public static synchronized zzahd zzb(String str) {
        return zza(zzagx.zzd(str).zzd());
    }
}
