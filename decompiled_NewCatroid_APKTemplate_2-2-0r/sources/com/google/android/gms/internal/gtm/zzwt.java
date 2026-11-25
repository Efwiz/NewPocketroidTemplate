package com.google.android.gms.internal.gtm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwt {
    private static final zzwt zza = new zzwt();
    private final ConcurrentMap<Class<?>, zzwx<?>> zzc = new ConcurrentHashMap();
    private final zzwy zzb = new zzwc();

    private zzwt() {
    }

    public static zzwt zza() {
        return zza;
    }

    public final <T> zzwx<T> zzb(Class<T> cls) {
        zzvi.zzf(cls, "messageType");
        zzwx<T> zzwxVarZza = (zzwx) this.zzc.get(cls);
        if (zzwxVarZza == null) {
            zzwxVarZza = this.zzb.zza(cls);
            zzvi.zzf(cls, "messageType");
            zzvi.zzf(zzwxVarZza, "schema");
            zzwx<T> zzwxVar = (zzwx) this.zzc.putIfAbsent(cls, zzwxVarZza);
            if (zzwxVar != null) {
                return zzwxVar;
            }
        }
        return zzwxVarZza;
    }
}
