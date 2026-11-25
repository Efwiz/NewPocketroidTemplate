package com.google.android.gms.cast.framework.media.internal;

import android.graphics.Bitmap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzl implements zza {
    final /* synthetic */ zzn zza;
    final /* synthetic */ zzo zzb;

    zzl(zzo zzoVar, zzn zznVar) {
        this.zzb = zzoVar;
        this.zza = zznVar;
    }

    @Override // com.google.android.gms.cast.framework.media.internal.zza
    public final void zza(Bitmap bitmap) {
        zzn zznVar = this.zza;
        zznVar.zzb = bitmap;
        this.zzb.zzp = zznVar;
        this.zzb.zzg();
    }
}
