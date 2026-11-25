package com.google.android.gms.internal.mlkit_vision_text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzid {
    private zzie zza;
    private Integer zzb;

    public final zzid zza(zzie zzieVar) {
        this.zza = zzieVar;
        return this;
    }

    public final zzid zzb(Integer num) {
        this.zzb = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzif zzc() {
        return new zzif(this, null);
    }
}
