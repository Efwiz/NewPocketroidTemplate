package com.google.android.gms.tagmanager;

import android.content.Context;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbh {
    private static zzbh zza;
    private static final Object zzb = new Object();
    private final zzec zzc;
    private final zzcc zzd;

    private zzbh(Context context) {
        zzcc zzccVarZzb = zzcc.zzb(context);
        zzex zzexVar = new zzex();
        this.zzd = zzccVarZzb;
        this.zzc = zzexVar;
    }

    public static zzbh zzb(Context context) {
        zzbh zzbhVar;
        synchronized (zzb) {
            if (zza == null) {
                zza = new zzbh(context);
            }
            zzbhVar = zza;
        }
        return zzbhVar;
    }

    public final boolean zza(String str) {
        if (this.zzc.zza()) {
            this.zzd.zzf(str, System.currentTimeMillis());
            return true;
        }
        zzdh.zzc("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
        return false;
    }
}
