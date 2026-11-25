package com.google.android.gms.internal.mlkit_vision_face;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzid {
    private final zzib zza;
    private final zzhz zzb;
    private final zzic zzc;
    private final zzia zzd;
    private final Boolean zze;
    private final Float zzf;

    /* synthetic */ zzid(zzhy zzhyVar, zzhx zzhxVar) {
        this.zza = zzhyVar.zza;
        this.zzb = zzhyVar.zzb;
        this.zzc = zzhyVar.zzc;
        this.zzd = zzhyVar.zzd;
        this.zze = zzhyVar.zze;
        this.zzf = zzhyVar.zzf;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzid)) {
            return false;
        }
        zzid zzidVar = (zzid) obj;
        return Objects.equal(this.zza, zzidVar.zza) && Objects.equal(this.zzb, zzidVar.zzb) && Objects.equal(this.zzc, zzidVar.zzc) && Objects.equal(this.zzd, zzidVar.zzd) && Objects.equal(this.zze, zzidVar.zze) && Objects.equal(this.zzf, zzidVar.zzf);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf);
    }

    public final zzib zza() {
        return this.zza;
    }

    public final zzhz zzb() {
        return this.zzb;
    }

    public final zzic zzc() {
        return this.zzc;
    }

    public final zzia zzd() {
        return this.zzd;
    }

    public final Boolean zze() {
        return this.zze;
    }

    public final Float zzf() {
        return this.zzf;
    }
}
