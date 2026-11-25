package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbm implements Runnable {
    final /* synthetic */ zzex zza;
    final /* synthetic */ zzbq zzb;

    zzbm(zzbq zzbqVar, zzex zzexVar) {
        this.zzb = zzbqVar;
        this.zza = zzexVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        this.zzb.zza.zzj(this.zza);
    }
}
