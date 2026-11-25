package com.google.android.gms.tagmanager;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzdy extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.ARG1.toString();

    public zzdy(String str) {
        super(str, zza, zzb);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        Iterator<com.google.android.gms.internal.gtm.zzak> it = map.values().iterator();
        do {
            boolean zZzd = false;
            if (!it.hasNext()) {
                com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zza);
                com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzb);
                if (zzakVar != null && zzakVar2 != null) {
                    zZzd = zzd(zzakVar, zzakVar2, map);
                }
                return zzfv.zzc(Boolean.valueOf(zZzd));
            }
        } while (it.next() != zzfv.zzb());
        return zzfv.zzc(false);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }

    protected abstract boolean zzd(com.google.android.gms.internal.gtm.zzak zzakVar, com.google.android.gms.internal.gtm.zzak zzakVar2, Map<String, com.google.android.gms.internal.gtm.zzak> map);
}
