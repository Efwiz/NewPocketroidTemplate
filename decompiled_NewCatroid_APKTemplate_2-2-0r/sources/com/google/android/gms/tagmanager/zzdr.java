package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzdr extends zzdy {
    public zzdr(String str) {
        super(str);
    }

    protected abstract boolean zzc(zzfu zzfuVar, zzfu zzfuVar2, Map<String, com.google.android.gms.internal.gtm.zzak> map);

    @Override // com.google.android.gms.tagmanager.zzdy
    protected final boolean zzd(com.google.android.gms.internal.gtm.zzak zzakVar, com.google.android.gms.internal.gtm.zzak zzakVar2, Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        zzfu zzfuVarZze = zzfv.zze(zzfv.zzl(zzakVar));
        zzfu zzfuVarZze2 = zzfv.zze(zzfv.zzl(zzakVar2));
        if (zzfuVarZze == zzfv.zzd() || zzfuVarZze2 == zzfv.zzd()) {
            return false;
        }
        return zzc(zzfuVarZze, zzfuVarZze2, map);
    }
}
