package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zziu {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private zzan<String> zze;
    private String zzf;
    private Boolean zzg;
    private Boolean zzh;
    private Boolean zzi;
    private Integer zzj;

    public final zziu zzb(String str) {
        this.zza = str;
        return this;
    }

    public final zziu zzc(String str) {
        this.zzb = str;
        return this;
    }

    public final zziu zzd(Integer num) {
        this.zzj = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zziu zze(Boolean bool) {
        this.zzg = bool;
        return this;
    }

    public final zziu zzf(Boolean bool) {
        this.zzi = bool;
        return this;
    }

    public final zziu zzg(Boolean bool) {
        this.zzh = bool;
        return this;
    }

    public final zziu zzh(zzan<String> zzanVar) {
        this.zze = zzanVar;
        return this;
    }

    public final zziu zzi(String str) {
        this.zzf = str;
        return this;
    }

    public final zziu zzj(String str) {
        this.zzc = str;
        return this;
    }

    public final zziu zzk(String str) {
        this.zzd = str;
        return this;
    }

    public final zziw zzl() {
        return new zziw(this, null);
    }
}
