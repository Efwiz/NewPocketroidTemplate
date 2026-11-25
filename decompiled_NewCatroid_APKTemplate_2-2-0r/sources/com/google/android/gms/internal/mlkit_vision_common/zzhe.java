package com.google.android.gms.internal.mlkit_vision_common;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhe {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private zzo<String> zze;
    private String zzf;
    private Boolean zzg;
    private Boolean zzh;
    private Boolean zzi;
    private Integer zzj;

    public final zzhe zzb(String str) {
        this.zza = str;
        return this;
    }

    public final zzhe zzc(String str) {
        this.zzb = str;
        return this;
    }

    public final zzhe zzd(Integer num) {
        this.zzj = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzhe zze(Boolean bool) {
        this.zzg = bool;
        return this;
    }

    public final zzhe zzf(Boolean bool) {
        this.zzi = bool;
        return this;
    }

    public final zzhe zzg(Boolean bool) {
        this.zzh = bool;
        return this;
    }

    public final zzhe zzh(zzo<String> zzoVar) {
        this.zze = zzoVar;
        return this;
    }

    public final zzhe zzi(String str) {
        this.zzf = str;
        return this;
    }

    public final zzhe zzj(String str) {
        this.zzc = str;
        return this;
    }

    public final zzhe zzk(String str) {
        this.zzd = str;
        return this;
    }

    public final zzhg zzl() {
        return new zzhg(this, null);
    }
}
