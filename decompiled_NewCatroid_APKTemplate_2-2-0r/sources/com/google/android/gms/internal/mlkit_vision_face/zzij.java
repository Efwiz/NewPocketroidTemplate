package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzij {
    private Long zza;
    private zzip zzb;
    private Boolean zzc;
    private Boolean zzd;
    private Boolean zze;

    public final zzij zza(Long l) {
        this.zza = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzij zzb(zzip zzipVar) {
        this.zzb = zzipVar;
        return this;
    }

    public final zzij zzc(Boolean bool) {
        this.zzc = bool;
        return this;
    }

    public final zzij zzd(Boolean bool) {
        this.zzd = bool;
        return this;
    }

    public final zzij zze(Boolean bool) {
        this.zze = bool;
        return this;
    }

    public final zzik zzf() {
        return new zzik(this, null);
    }
}
