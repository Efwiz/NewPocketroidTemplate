package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zze extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.ADVERTISER_ID.toString();
    private final zzd zzb;

    zze(zzd zzdVar) {
        super(zza, new String[0]);
        this.zzb = zzdVar;
        zzdVar.zzc();
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String strZzc = this.zzb.zzc();
        return strZzc == null ? zzfv.zzb() : zzfv.zzc(strZzc);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return false;
    }
}
