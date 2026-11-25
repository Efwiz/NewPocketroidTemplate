package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.Serializable;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzkg<T> implements Serializable {
    zzkg() {
    }

    public static <T> zzkg<T> zzd() {
        return zzjw.zza;
    }

    public abstract T zza();

    public abstract T zzb(T t);

    public abstract boolean zzc();

    public static <T> zzkg<T> zze(T t) {
        t.getClass();
        return new zzkl(t);
    }
}
