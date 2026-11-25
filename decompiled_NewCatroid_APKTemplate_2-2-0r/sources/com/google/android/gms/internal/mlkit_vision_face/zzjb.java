package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjb {
    private zzik zza;
    private zzih zzb;
    private zzid zzc;
    private Integer zzd;
    private Integer zze;

    public final zzjb zza(zzik zzikVar) {
        this.zza = zzikVar;
        return this;
    }

    public final zzjb zzb(zzih zzihVar) {
        this.zzb = zzihVar;
        return this;
    }

    public final zzjb zzc(zzid zzidVar) {
        this.zzc = zzidVar;
        return this;
    }

    public final zzjb zzd(Integer num) {
        this.zzd = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzjb zze(Integer num) {
        this.zze = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzjc zzf() {
        return new zzjc(this, null);
    }
}
