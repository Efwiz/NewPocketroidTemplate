package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlz {
    private zzlv zza;
    private zzko zzb;
    private Long zzc;
    private zzau<zzmo> zzd;

    public final zzlz zzd(zzlv zzlvVar) {
        this.zza = zzlvVar;
        return this;
    }

    public final zzlz zze(zzko zzkoVar) {
        this.zzb = zzkoVar;
        return this;
    }

    public final zzlz zzf(zzau<zzmo> zzauVar) {
        this.zzd = zzauVar;
        return this;
    }

    public final zzlz zzg(Long l) {
        this.zzc = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzmb zzh() {
        return new zzmb(this, null);
    }
}
