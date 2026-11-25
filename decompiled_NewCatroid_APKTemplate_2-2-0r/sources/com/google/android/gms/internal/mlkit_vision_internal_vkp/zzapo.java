package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzapo {
    public static final /* synthetic */ int zza = 0;
    private static final zzapo zzb = new zzapo();
    private final ConcurrentMap<Class<?>, zzapz<?>> zzd = new ConcurrentHashMap();
    private final zzaqa zzc = new zzaow();

    private zzapo() {
    }

    public static zzapo zza() {
        return zzb;
    }

    public final <T> zzapz<T> zzb(Class<T> cls) {
        zzaoc.zzf(cls, "messageType");
        zzapz<T> zzapzVarZza = (zzapz) this.zzd.get(cls);
        if (zzapzVarZza == null) {
            zzapzVarZza = this.zzc.zza(cls);
            zzaoc.zzf(cls, "messageType");
            zzaoc.zzf(zzapzVarZza, "schema");
            zzapz<T> zzapzVar = (zzapz) this.zzd.putIfAbsent(cls, zzapzVarZza);
            if (zzapzVar != null) {
                return zzapzVar;
            }
        }
        return zzapzVarZza;
    }
}
