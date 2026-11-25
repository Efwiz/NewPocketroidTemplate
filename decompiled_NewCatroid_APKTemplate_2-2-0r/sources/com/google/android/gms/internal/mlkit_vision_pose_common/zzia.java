package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzia {
    private static final zzia zza = new zzia();
    private final ConcurrentMap<Class<?>, zzib<?>> zzc = new ConcurrentHashMap();
    private final zzie zzb = new zzhc();

    public static zzia zza() {
        return zza;
    }

    public final <T> zzib<T> zza(Class<T> cls) {
        zzgg.zza(cls, "messageType");
        zzib<T> zzibVar = (zzib) this.zzc.get(cls);
        if (zzibVar != null) {
            return zzibVar;
        }
        zzib<T> zzibVarZza = this.zzb.zza(cls);
        zzgg.zza(cls, "messageType");
        zzgg.zza(zzibVarZza, "schema");
        zzib<T> zzibVar2 = (zzib) this.zzc.putIfAbsent(cls, zzibVarZza);
        return zzibVar2 != null ? zzibVar2 : zzibVarZza;
    }

    public final <T> zzib<T> zza(T t) {
        return zza((Class) t.getClass());
    }

    private zzia() {
    }
}
