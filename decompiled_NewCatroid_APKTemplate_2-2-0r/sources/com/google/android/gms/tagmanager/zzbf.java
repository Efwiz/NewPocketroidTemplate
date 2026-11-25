package com.google.android.gms.tagmanager;

import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbf extends zzft {
    private static final String zza = com.google.android.gms.internal.gtm.zza.DATA_LAYER_WRITE.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.VALUE.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    private final DataLayer zzd;

    public zzbf(DataLayer dataLayer) {
        super(zza, zzb);
        this.zzd = dataLayer;
    }

    @Override // com.google.android.gms.tagmanager.zzft
    public final void zzc(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String strZzn;
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzb);
        if (zzakVar != null) {
            Object objZzl = zzfv.zzl(zzakVar);
            if (objZzl instanceof List) {
                for (Object obj : (List) objZzl) {
                    if (obj instanceof Map) {
                        this.zzd.push((Map) obj);
                    }
                }
            }
        }
        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzc);
        if (zzakVar2 == null || (strZzn = zzfv.zzn(zzfv.zzl(zzakVar2))) == zzfv.zzm()) {
            return;
        }
        this.zzd.zzd(strZzn);
    }
}
