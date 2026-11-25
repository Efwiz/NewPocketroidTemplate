package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbl implements Runnable {
    final /* synthetic */ int zza;
    final /* synthetic */ zzbq zzb;

    zzbl(zzbq zzbqVar, int i) {
        this.zzb = zzbqVar;
        this.zza = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.zzZ(((long) this.zza) * 1000);
    }
}
