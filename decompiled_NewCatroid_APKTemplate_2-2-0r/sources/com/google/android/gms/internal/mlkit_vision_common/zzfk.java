package com.google.android.gms.internal.mlkit_vision_common;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfk {
    private Long zza;
    private zzfl zzb;
    private zzfg zzc;
    private Integer zzd;
    private Integer zze;
    private Integer zzf;
    private Integer zzg;

    public final zzfk zzb(Long l) {
        this.zza = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzfk zzc(Integer num) {
        this.zzd = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzfk zzd(zzfg zzfgVar) {
        this.zzc = zzfgVar;
        return this;
    }

    public final zzfk zze(Integer num) {
        this.zzf = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzfk zzf(zzfl zzflVar) {
        this.zzb = zzflVar;
        return this;
    }

    public final zzfk zzg(Integer num) {
        this.zze = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzfk zzh(Integer num) {
        this.zzg = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzfn zzj() {
        return new zzfn(this, null);
    }
}
