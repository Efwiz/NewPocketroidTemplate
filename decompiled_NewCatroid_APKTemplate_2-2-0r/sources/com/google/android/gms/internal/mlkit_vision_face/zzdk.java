package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdk {
    private zzdm zza;
    private Integer zzb;
    private zzhw zzc;

    public final zzdk zza(zzdm zzdmVar) {
        this.zza = zzdmVar;
        return this;
    }

    public final zzdk zzb(Integer num) {
        this.zzb = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzdk zzc(zzhw zzhwVar) {
        this.zzc = zzhwVar;
        return this;
    }

    public final zzdn zzd() {
        return new zzdn(this, null);
    }
}
