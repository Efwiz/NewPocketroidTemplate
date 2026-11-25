package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzf extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.ADVERTISING_TRACKING_ENABLED.toString();
    private final zzd zzb;

    zzf(zzd zzdVar) {
        super(zza, new String[0]);
        this.zzb = zzdVar;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        return zzfv.zzc(Boolean.valueOf(!this.zzb.zzf()));
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return false;
    }
}
