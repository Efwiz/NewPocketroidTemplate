package com.google.android.gms.internal.mlkit_language_id;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgk {
    private static final zzgk zza = new zzgk();
    private final ConcurrentMap<Class<?>, zzgp<?>> zzc = new ConcurrentHashMap();
    private final zzgo zzb = new zzfm();

    public static zzgk zza() {
        return zza;
    }

    public final <T> zzgp<T> zza(Class<T> cls) {
        zzeq.zza(cls, "messageType");
        zzgp<T> zzgpVar = (zzgp) this.zzc.get(cls);
        if (zzgpVar != null) {
            return zzgpVar;
        }
        zzgp<T> zzgpVarZza = this.zzb.zza(cls);
        zzeq.zza(cls, "messageType");
        zzeq.zza(zzgpVarZza, "schema");
        zzgp<T> zzgpVar2 = (zzgp) this.zzc.putIfAbsent(cls, zzgpVarZza);
        return zzgpVar2 != null ? zzgpVar2 : zzgpVarZza;
    }

    public final <T> zzgp<T> zza(T t) {
        return zza((Class) t.getClass());
    }

    private zzgk() {
    }
}
