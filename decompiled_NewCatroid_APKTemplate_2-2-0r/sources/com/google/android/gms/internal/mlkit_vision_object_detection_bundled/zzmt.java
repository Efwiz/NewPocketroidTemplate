package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzmt {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private zzau<String> zze;
    private String zzf;
    private Boolean zzg;
    private Boolean zzh;
    private Boolean zzi;
    private Integer zzj;

    public final zzmt zzb(String str) {
        this.zza = str;
        return this;
    }

    public final zzmt zzc(String str) {
        this.zzb = str;
        return this;
    }

    public final zzmt zzd(Integer num) {
        this.zzj = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzmt zze(Boolean bool) {
        this.zzg = bool;
        return this;
    }

    public final zzmt zzf(Boolean bool) {
        this.zzi = bool;
        return this;
    }

    public final zzmt zzg(Boolean bool) {
        this.zzh = bool;
        return this;
    }

    public final zzmt zzh(zzau<String> zzauVar) {
        this.zze = zzauVar;
        return this;
    }

    public final zzmt zzi(String str) {
        this.zzf = str;
        return this;
    }

    public final zzmt zzj(String str) {
        this.zzc = str;
        return this;
    }

    public final zzmt zzk(String str) {
        this.zzd = str;
        return this;
    }

    public final zzmv zzl() {
        return new zzmv(this, null);
    }
}
