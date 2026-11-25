package com.google.android.gms.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzak implements Runnable {
    final /* synthetic */ CastRemoteDisplayLocalService.NotificationSettings zza;
    final /* synthetic */ CastRemoteDisplayLocalService zzb;

    zzak(CastRemoteDisplayLocalService castRemoteDisplayLocalService, CastRemoteDisplayLocalService.NotificationSettings notificationSettings) {
        this.zzb = castRemoteDisplayLocalService;
        this.zza = notificationSettings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CastRemoteDisplayLocalService.zzq(this.zzb, this.zza);
    }
}
