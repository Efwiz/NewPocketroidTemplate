package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbk implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ Runnable zzb;
    final /* synthetic */ zzbq zzc;

    zzbk(zzbq zzbqVar, String str, Runnable runnable) {
        this.zzc = zzbqVar;
        this.zza = str;
        this.zzb = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        this.zzc.zza.zzn(this.zza);
        this.zzb.run();
    }
}
