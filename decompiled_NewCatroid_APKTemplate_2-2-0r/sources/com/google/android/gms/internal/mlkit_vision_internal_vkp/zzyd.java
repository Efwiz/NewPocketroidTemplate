package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzyd {
    private Long zza;
    private zzkz<zzyf> zzb;
    private zzkz<zzyf> zzc;
    private zzkz<zzyf> zzd;
    private String zze;
    private zzye zzf;

    public final zzyd zzd(zzye zzyeVar) {
        this.zzf = zzyeVar;
        return this;
    }

    public final zzyd zze(Long l) {
        this.zza = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzyd zzf(zzkz<zzyf> zzkzVar) {
        this.zzb = zzkzVar;
        return this;
    }

    public final zzyd zzg(String str) {
        this.zze = "com.google.perception";
        return this;
    }

    public final zzyd zzh(zzkz<zzyf> zzkzVar) {
        this.zzc = zzkzVar;
        return this;
    }

    public final zzyd zzi(zzkz<zzyf> zzkzVar) {
        this.zzd = zzkzVar;
        return this;
    }

    public final zzyh zzk() {
        return new zzyh(this, null);
    }
}
