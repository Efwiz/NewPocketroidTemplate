package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.os.SystemClock;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzagm {
    private long zza;
    private long zzb;
    private final zzkw<zzafd> zzc = new zzkw<>();
    private final zzkw<zzafd> zzd = new zzkw<>();
    private final zzkw<zzafd> zze = new zzkw<>();
    private int zzf;

    public final zzafc zza() {
        zzkk.zzf(this.zza != 0);
        zzkk.zzf(this.zzb != 0);
        long j = this.zzb;
        long j2 = this.zza;
        zzafc zzafcVar = new zzafc();
        zzafcVar.zzd(Long.valueOf(j - j2));
        zzafcVar.zzh(this.zzc.zzc());
        zzafcVar.zzg(this.zzd.zzc());
        zzafcVar.zze(this.zze.zzc());
        int i = this.zzf;
        if (i != 0) {
            zzafcVar.zzf(Integer.valueOf(i));
        }
        return zzafcVar;
    }

    public final void zzb(zzafd zzafdVar) {
        this.zze.zzb(zzafdVar);
    }

    public final void zzc(zzafd zzafdVar) {
        this.zzd.zzb(zzafdVar);
    }

    public final void zzd(zzafd zzafdVar) {
        this.zzc.zzb(zzafdVar);
    }

    public final void zze() {
        this.zzb = SystemClock.elapsedRealtime();
    }

    public final void zzf(int i) {
        this.zzf = i;
    }

    public final void zzg() {
        this.zza = SystemClock.elapsedRealtime();
    }
}
