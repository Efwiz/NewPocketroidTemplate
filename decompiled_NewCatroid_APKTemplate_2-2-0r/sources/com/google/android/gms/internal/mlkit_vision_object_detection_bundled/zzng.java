package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.os.SystemClock;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzng {
    boolean zza;
    private long zzb;
    private long zzc;
    private final zzar<zzmq> zzd = new zzar<>();
    private final zzar<zzmq> zze = new zzar<>();
    private final zzar<zzmq> zzf = new zzar<>();
    private int zzg;

    public final zzmp zza() {
        zzh.zzd(this.zzb != 0);
        zzh.zzd(this.zzc != 0);
        long j = this.zzc;
        long j2 = this.zzb;
        zzmp zzmpVar = new zzmp();
        zzmpVar.zzd(Long.valueOf(j - j2));
        zzmpVar.zzh(this.zzd.zzd());
        zzmpVar.zzg(this.zze.zzd());
        zzmpVar.zze(this.zzf.zzd());
        int i = this.zzg;
        if (i != 0) {
            zzmpVar.zzf(Integer.valueOf(i));
        }
        return zzmpVar;
    }

    public final void zzb(zzmq zzmqVar) {
        this.zzf.zzc(zzmqVar);
    }

    public final void zzc(zzmq zzmqVar) {
        this.zze.zzc(zzmqVar);
    }

    public final void zzd(zzmq zzmqVar) {
        if (this.zza) {
            this.zze.zzc(zzmqVar);
        } else {
            this.zzd.zzc(zzmqVar);
        }
    }

    public final void zze() {
        this.zzc = SystemClock.elapsedRealtime();
    }

    public final void zzf(int i) {
        this.zzg = i;
    }

    public final void zzg() {
        this.zzb = SystemClock.elapsedRealtime();
    }

    public final void zzh() {
        this.zza = true;
    }
}
