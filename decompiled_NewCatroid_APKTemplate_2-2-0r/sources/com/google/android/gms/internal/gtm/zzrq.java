package com.google.android.gms.internal.gtm;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzrq {
    private final Map<String, zzak> zza = new HashMap();
    private zzak zzb;

    private zzrq() {
    }

    public final zzro zza() {
        return new zzro(this.zza, this.zzb, null);
    }

    public final zzrq zzb(String str, zzak zzakVar) {
        this.zza.put(str, zzakVar);
        return this;
    }

    public final zzrq zzc(zzak zzakVar) {
        this.zzb = zzakVar;
        return this;
    }

    /* synthetic */ zzrq(zzrp zzrpVar) {
    }
}
