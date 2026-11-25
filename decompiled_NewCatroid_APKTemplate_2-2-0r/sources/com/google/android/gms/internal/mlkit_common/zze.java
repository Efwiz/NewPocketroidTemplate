package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zze {
    private final zzak<zzq> zza = zzan.zzg();
    private Boolean zzb;

    private zze() {
    }

    public final zze zza() {
        zzad.zzd(this.zzb == null, "A SourcePolicy can only set internal() or external() once.");
        this.zzb = false;
        return this;
    }

    public final zze zzb() {
        zzad.zzd(this.zzb == null, "A SourcePolicy can only set internal() or external() once.");
        this.zzb = true;
        return this;
    }

    public final zzg zzc() {
        Boolean bool = this.zzb;
        if (bool == null) {
            throw new NullPointerException("Must call internal() or external() when building a SourcePolicy.");
        }
        return new zzg(bool.booleanValue(), false, this.zza.zzc(), null);
    }

    /* synthetic */ zze(zzd zzdVar) {
    }
}
