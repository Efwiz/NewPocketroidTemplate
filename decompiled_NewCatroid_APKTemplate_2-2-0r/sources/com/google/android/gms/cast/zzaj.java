package com.google.android.gms.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaj implements Runnable {
    final /* synthetic */ boolean zza;
    final /* synthetic */ CastRemoteDisplayLocalService zzb;

    zzaj(CastRemoteDisplayLocalService castRemoteDisplayLocalService, boolean z) {
        this.zzb = castRemoteDisplayLocalService;
        this.zza = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzx(this.zza);
    }
}
