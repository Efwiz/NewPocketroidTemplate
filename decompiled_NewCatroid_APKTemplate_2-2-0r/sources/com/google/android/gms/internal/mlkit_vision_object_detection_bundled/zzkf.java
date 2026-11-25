package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkf {
    private Long zza;
    private zzko zzb;
    private Boolean zzc;
    private Boolean zzd;
    private Boolean zze;
    private zzau<zzmo> zzf;
    private Boolean zzg;
    private Boolean zzh;

    public final zzkf zzb(Boolean bool) {
        this.zzd = bool;
        return this;
    }

    public final zzkf zzc(Boolean bool) {
        this.zze = bool;
        return this;
    }

    public final zzkf zzd(Long l) {
        this.zza = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzkf zze(zzko zzkoVar) {
        this.zzb = zzkoVar;
        return this;
    }

    public final zzkf zzf(Boolean bool) {
        this.zzh = bool;
        return this;
    }

    public final zzkf zzg(Boolean bool) {
        this.zzc = bool;
        return this;
    }

    public final zzkf zzh(zzau<zzmo> zzauVar) {
        this.zzf = zzauVar;
        return this;
    }

    public final zzkf zzi(Boolean bool) {
        this.zzg = bool;
        return this;
    }

    public final zzkh zzj() {
        return new zzkh(this, null);
    }
}
