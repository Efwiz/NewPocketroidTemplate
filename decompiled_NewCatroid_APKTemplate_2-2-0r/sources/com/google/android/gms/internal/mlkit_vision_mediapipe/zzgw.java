package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgw {
    private static final zzgw zza = new zzgw();
    private final ConcurrentMap<Class<?>, zzgx<?>> zzc = new ConcurrentHashMap();
    private final zzha zzb = new zzfv();

    public static zzgw zza() {
        return zza;
    }

    public final <T> zzgx<T> zza(Class<T> cls) {
        zzfe.zza(cls, "messageType");
        zzgx<T> zzgxVar = (zzgx) this.zzc.get(cls);
        if (zzgxVar != null) {
            return zzgxVar;
        }
        zzgx<T> zzgxVarZza = this.zzb.zza(cls);
        zzfe.zza(cls, "messageType");
        zzfe.zza(zzgxVarZza, "schema");
        zzgx<T> zzgxVar2 = (zzgx) this.zzc.putIfAbsent(cls, zzgxVarZza);
        return zzgxVar2 != null ? zzgxVar2 : zzgxVarZza;
    }

    public final <T> zzgx<T> zza(T t) {
        return zza((Class) t.getClass());
    }

    private zzgw() {
    }
}
