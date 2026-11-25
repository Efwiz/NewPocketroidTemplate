package com.google.android.gms.internal.mlkit_vision_text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzih {
    private Long zza;
    private zzin zzb;
    private Boolean zzc;
    private Boolean zzd;
    private Boolean zze;

    public final zzih zza(Long l) {
        this.zza = Long.valueOf(l.longValue() & Long.MAX_VALUE);
        return this;
    }

    public final zzih zzb(zzin zzinVar) {
        this.zzb = zzinVar;
        return this;
    }

    public final zzih zzc(Boolean bool) {
        this.zzc = bool;
        return this;
    }

    public final zzih zzd(Boolean bool) {
        this.zzd = bool;
        return this;
    }

    public final zzih zze(Boolean bool) {
        this.zze = bool;
        return this;
    }

    public final zzii zzf() {
        return new zzii(this, null);
    }
}
