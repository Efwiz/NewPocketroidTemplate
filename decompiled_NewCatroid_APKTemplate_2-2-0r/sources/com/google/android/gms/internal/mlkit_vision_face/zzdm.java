package com.google.android.gms.internal.mlkit_vision_face;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdm {
    private final zzip zza;
    private final Boolean zzb;
    private final zzih zzc;
    private final zzid zzd;
    private final Integer zze;
    private final Integer zzf;

    /* synthetic */ zzdm(zzdl zzdlVar, zzdj zzdjVar) {
        this.zza = zzdlVar.zza;
        this.zzb = zzdlVar.zzb;
        this.zzc = zzdlVar.zzc;
        this.zzd = zzdlVar.zzd;
        this.zze = zzdlVar.zze;
        this.zzf = zzdlVar.zzf;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdm)) {
            return false;
        }
        zzdm zzdmVar = (zzdm) obj;
        return Objects.equal(this.zza, zzdmVar.zza) && Objects.equal(this.zzb, zzdmVar.zzb) && Objects.equal(this.zzc, zzdmVar.zzc) && Objects.equal(this.zzd, zzdmVar.zzd) && Objects.equal(this.zze, zzdmVar.zze) && Objects.equal(this.zzf, zzdmVar.zzf);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf);
    }

    public final zzip zza() {
        return this.zza;
    }

    public final Boolean zzb() {
        return this.zzb;
    }

    public final zzih zzc() {
        return this.zzc;
    }

    public final zzid zzd() {
        return this.zzd;
    }

    public final Integer zze() {
        return this.zze;
    }

    public final Integer zzf() {
        return this.zzf;
    }
}
