package com.google.android.gms.internal.mlkit_vision_text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzea {
    private zzec zza;
    private Integer zzb;
    private zzia zzc;

    public final zzea zza(zzec zzecVar) {
        this.zza = zzecVar;
        return this;
    }

    public final zzea zzb(Integer num) {
        this.zzb = Integer.valueOf(num.intValue() & Integer.MAX_VALUE);
        return this;
    }

    public final zzea zzc(zzia zziaVar) {
        this.zzc = zziaVar;
        return this;
    }

    public final zzed zzd() {
        return new zzed(this, null);
    }
}
