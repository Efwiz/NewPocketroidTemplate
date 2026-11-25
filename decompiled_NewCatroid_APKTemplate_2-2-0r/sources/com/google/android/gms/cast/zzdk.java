package com.google.android.gms.cast;

import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdk extends zzdp {
    final /* synthetic */ RemoteMediaPlayer zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdk(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient) {
        super(remoteMediaPlayer, googleApiClient);
        this.zza = remoteMediaPlayer;
    }

    @Override // com.google.android.gms.cast.zzdp
    protected final void zza(com.google.android.gms.cast.internal.zzw zzwVar) {
        this.zza.zzb.zzB(zzb());
    }
}
