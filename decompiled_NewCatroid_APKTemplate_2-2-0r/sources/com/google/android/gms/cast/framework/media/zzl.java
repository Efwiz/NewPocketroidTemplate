package com.google.android.gms.cast.framework.media;

import android.graphics.Bitmap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzl implements com.google.android.gms.cast.framework.media.internal.zza {
    final /* synthetic */ zzn zza;
    final /* synthetic */ MediaNotificationService zzb;

    zzl(MediaNotificationService mediaNotificationService, zzn zznVar) {
        this.zzb = mediaNotificationService;
        this.zza = zznVar;
    }

    @Override // com.google.android.gms.cast.framework.media.internal.zza
    public final void zza(Bitmap bitmap) {
        zzn zznVar = this.zza;
        zznVar.zzb = bitmap;
        this.zzb.zzn = zznVar;
        this.zzb.zzd();
    }
}
