package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzafc {
    private Long zza;
    private zzkz<zzafd> zzb;
    private zzkz<zzafd> zzc;
    private zzkz<zzafd> zzd;
    private Integer zze;

    public final zzafc zzd(Long l) {
        this.zza = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzafc zze(zzkz<zzafd> zzkzVar) {
        this.zzb = zzkzVar;
        return this;
    }

    public final zzafc zzf(Integer num) {
        this.zze = num;
        return this;
    }

    public final zzafc zzg(zzkz<zzafd> zzkzVar) {
        this.zzc = zzkzVar;
        return this;
    }

    public final zzafc zzh(zzkz<zzafd> zzkzVar) {
        this.zzd = zzkzVar;
        return this;
    }

    public final zzaff zzi() {
        return new zzaff(this, null);
    }
}
