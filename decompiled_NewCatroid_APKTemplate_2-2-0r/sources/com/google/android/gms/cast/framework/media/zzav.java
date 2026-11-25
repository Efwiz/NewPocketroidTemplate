package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.MediaLoadRequestData;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzav extends zzbk {
    final /* synthetic */ MediaLoadRequestData zza;
    final /* synthetic */ RemoteMediaClient zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzav(RemoteMediaClient remoteMediaClient, MediaLoadRequestData mediaLoadRequestData) {
        super(remoteMediaClient, false);
        this.zzb = remoteMediaClient;
        this.zza = mediaLoadRequestData;
    }

    @Override // com.google.android.gms.cast.framework.media.zzbk
    protected final void zza() throws com.google.android.gms.cast.internal.zzao {
        this.zzb.zzd.zzp(zzb(), this.zza);
    }
}
