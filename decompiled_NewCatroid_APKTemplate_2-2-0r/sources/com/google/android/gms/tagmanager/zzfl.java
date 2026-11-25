package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzfl extends zzdy {
    public zzfl(String str) {
        super(str);
    }

    protected abstract boolean zzc(String str, String str2, Map<String, com.google.android.gms.internal.gtm.zzak> map);

    @Override // com.google.android.gms.tagmanager.zzdy
    protected final boolean zzd(com.google.android.gms.internal.gtm.zzak zzakVar, com.google.android.gms.internal.gtm.zzak zzakVar2, Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String strZzn = zzfv.zzn(zzfv.zzl(zzakVar));
        String strZzn2 = zzfv.zzn(zzfv.zzl(zzakVar2));
        if (strZzn == zzfv.zzm() || strZzn2 == zzfv.zzm()) {
            return false;
        }
        return zzc(strZzn, strZzn2, map);
    }
}
