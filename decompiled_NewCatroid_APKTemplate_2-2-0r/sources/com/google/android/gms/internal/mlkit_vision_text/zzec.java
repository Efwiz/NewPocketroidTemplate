package com.google.android.gms.internal.mlkit_vision_text;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzec {
    private final zzin zza;
    private final Boolean zzb = null;
    private final Boolean zzc;
    private final zzif zzd;

    /* synthetic */ zzec(zzeb zzebVar, zzdz zzdzVar) {
        this.zza = zzebVar.zza;
        this.zzc = zzebVar.zzb;
        this.zzd = zzebVar.zzc;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzec)) {
            return false;
        }
        zzec zzecVar = (zzec) obj;
        if (Objects.equal(this.zza, zzecVar.zza)) {
            Boolean bool = zzecVar.zzb;
            if (Objects.equal(null, null) && Objects.equal(this.zzc, zzecVar.zzc) && Objects.equal(this.zzd, zzecVar.zzd)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, null, this.zzc, this.zzd);
    }

    public final zzin zza() {
        return this.zza;
    }

    public final Boolean zzb() {
        return this.zzc;
    }

    public final zzif zzc() {
        return this.zzd;
    }
}
