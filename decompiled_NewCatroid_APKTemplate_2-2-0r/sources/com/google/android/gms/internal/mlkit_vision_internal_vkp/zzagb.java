package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.os.SystemClock;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzagb {
    private final zzkw<zzyf> zza;
    private final zzkw<zzyf> zzb;
    private final zzkw<zzyf> zzc;
    private final String zzd;
    private final zzye zze;
    private long zzf;
    private long zzg;
    private final zzaga zzh;

    public zzagb(String str, int i) {
        zzaga zzagaVar = new zzaga(null);
        this.zza = new zzkw<>();
        this.zzb = new zzkw<>();
        this.zzc = new zzkw<>();
        this.zzd = "com.google.perception";
        this.zze = zzye.NNAPI;
        this.zzh = zzagaVar;
    }

    public final zzyd zza() {
        zzkk.zzf(this.zzf != 0);
        zzkk.zzf(this.zzg != 0);
        long j = this.zzg;
        long j2 = this.zzf;
        zzyd zzydVar = new zzyd();
        zzydVar.zze(Long.valueOf(j - j2));
        zzydVar.zzg(this.zzd);
        zzydVar.zzd(this.zze);
        zzydVar.zzi(this.zza.zzc());
        zzydVar.zzh(this.zzb.zzc());
        zzydVar.zzf(this.zzc.zzc());
        return zzydVar;
    }

    public final void zzb(zzyf zzyfVar) {
        this.zzc.zzb(zzyfVar);
    }

    public final void zzc(zzyf zzyfVar) {
        this.zzb.zzb(zzyfVar);
    }

    public final void zzd(zzyf zzyfVar) {
        this.zza.zzb(zzyfVar);
    }

    public final void zze() {
        zzkk.zzg(this.zzg == 0, "Method endTiming is not supposed to be called more than once.");
        this.zzg = SystemClock.elapsedRealtime();
    }

    public final void zzf() {
        zzkk.zzg(this.zzf == 0, "Method startTiming is not supposed to be called more than once.");
        this.zzf = SystemClock.elapsedRealtime();
    }
}
