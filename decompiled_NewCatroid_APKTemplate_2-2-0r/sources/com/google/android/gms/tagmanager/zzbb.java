package com.google.android.gms.tagmanager;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbb implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ zzbe zzb;

    zzbb(zzbe zzbeVar, String str) {
        this.zzb = zzbeVar;
        this.zza = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzbe.zzg(this.zzb, this.zza);
    }
}
