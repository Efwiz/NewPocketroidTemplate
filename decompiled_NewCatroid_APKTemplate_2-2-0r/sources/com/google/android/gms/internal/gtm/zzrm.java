package com.google.android.gms.internal.gtm;

import android.content.Context;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzrm {
    final Map<String, Object> zza;
    private final Context zzb;
    private final zzsb zzc;
    private final Clock zzd;
    private final Map<String, Object> zze;

    public zzrm(Context context) {
        HashMap map = new HashMap();
        zzsb zzsbVar = new zzsb(context);
        Clock defaultClock = DefaultClock.getInstance();
        this.zza = new HashMap();
        this.zzb = context;
        this.zzd = defaultClock;
        this.zzc = zzsbVar;
        this.zze = map;
    }
}
