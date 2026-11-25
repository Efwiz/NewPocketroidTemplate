package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzmp {
    private Long zza;
    private zzau<zzmq> zzb;
    private zzau<zzmq> zzc;
    private zzau<zzmq> zzd;
    private Integer zze;

    public final zzmp zzd(Long l) {
        this.zza = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzmp zze(zzau<zzmq> zzauVar) {
        this.zzb = zzauVar;
        return this;
    }

    public final zzmp zzf(Integer num) {
        this.zze = num;
        return this;
    }

    public final zzmp zzg(zzau<zzmq> zzauVar) {
        this.zzc = zzauVar;
        return this;
    }

    public final zzmp zzh(zzau<zzmq> zzauVar) {
        this.zzd = zzauVar;
        return this;
    }

    public final zzms zzi() {
        return new zzms(this, null);
    }
}
