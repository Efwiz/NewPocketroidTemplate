package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdl {
    private zzip zza;
    private Boolean zzb;
    private zzih zzc;
    private zzid zzd;
    private Integer zze;
    private Integer zzf;

    public final zzdl zza(zzip zzipVar) {
        this.zza = zzipVar;
        return this;
    }

    public final zzdl zzb(Boolean bool) {
        this.zzb = bool;
        return this;
    }

    public final zzdl zzc(zzih zzihVar) {
        this.zzc = zzihVar;
        return this;
    }

    public final zzdl zzd(zzid zzidVar) {
        this.zzd = zzidVar;
        return this;
    }

    public final zzdl zze(Integer num) {
        this.zze = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzdl zzf(Integer num) {
        this.zzf = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzdm zzg() {
        return new zzdm(this, null);
    }
}
