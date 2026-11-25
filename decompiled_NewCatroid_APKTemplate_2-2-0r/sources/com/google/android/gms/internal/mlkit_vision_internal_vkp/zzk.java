package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzk {
    private final zzkw<zzw> zza = zzkz.zzg();
    private Boolean zzb;

    private zzk() {
    }

    public final zzk zza() {
        zzkk.zzg(this.zzb == null, "A SourcePolicy can only set internal() or external() once.");
        this.zzb = false;
        return this;
    }

    public final zzk zzb() {
        zzkk.zzg(this.zzb == null, "A SourcePolicy can only set internal() or external() once.");
        this.zzb = true;
        return this;
    }

    public final zzm zzc() {
        zzkk.zzc(this.zzb, "Must call internal() or external() when building a SourcePolicy.");
        return new zzm(this.zzb.booleanValue(), false, this.zza.zzc(), null);
    }

    /* synthetic */ zzk(zzj zzjVar) {
    }
}
