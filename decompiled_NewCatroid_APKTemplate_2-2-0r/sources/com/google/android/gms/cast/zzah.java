package com.google.android.gms.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzah implements Runnable {
    final /* synthetic */ CastRemoteDisplayLocalService zza;

    zzah(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zza = castRemoteDisplayLocalService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.zzv("onCreate after delay. The local service been started: " + this.zza.zzs);
        CastRemoteDisplayLocalService castRemoteDisplayLocalService = this.zza;
        if (castRemoteDisplayLocalService.zzs) {
            return;
        }
        CastRemoteDisplayLocalService.zza.e("[Instance: %s] %s", castRemoteDisplayLocalService, "The local service has not been been started, stopping it");
        this.zza.stopSelf();
    }
}
