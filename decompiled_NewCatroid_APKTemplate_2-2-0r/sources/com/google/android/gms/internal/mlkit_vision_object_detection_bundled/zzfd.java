package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfd {
    private zzfg zza;
    private Integer zzb;
    private zzjx zzc;

    public final zzfd zza(Integer num) {
        this.zzb = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzfd zzb(zzjx zzjxVar) {
        this.zzc = zzjxVar;
        return this;
    }

    public final zzfd zzc(zzfg zzfgVar) {
        this.zza = zzfgVar;
        return this;
    }

    public final zzfi zze() {
        return new zzfi(this, null);
    }
}
