package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzjw<T> extends zzkg<T> {
    static final zzjw<Object> zza = new zzjw<>();

    private zzjw() {
    }

    public final boolean equals(@CheckForNull Object obj) {
        return obj == this;
    }

    public final int hashCode() {
        return 2040732332;
    }

    public final String toString() {
        return "Optional.absent()";
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg
    public final T zza() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg
    public final T zzb(T t) {
        zzkk.zzc(t, "use Optional.orNull() instead of Optional.or(null)");
        return t;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg
    public final boolean zzc() {
        return false;
    }
}
