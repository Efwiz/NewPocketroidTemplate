package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.huawei.hms.ml.language.common.utils.Constant;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzkl<T> extends zzkg<T> {
    private final T zza;

    zzkl(T t) {
        this.zza = t;
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof zzkl) {
            return this.zza.equals(((zzkl) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode() + 1502476572;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 13);
        sb.append("Optional.of(");
        sb.append(strValueOf);
        sb.append(Constant.AFTER_QUTO);
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg
    public final T zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg
    public final T zzb(T t) {
        zzkk.zzc(t, "use Optional.orNull() instead of Optional.or(null)");
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg
    public final boolean zzc() {
        return true;
    }
}
