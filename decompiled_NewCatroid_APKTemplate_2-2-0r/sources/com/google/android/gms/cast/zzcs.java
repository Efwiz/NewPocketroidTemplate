package com.google.android.gms.cast;

import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcs extends zzdp {
    final /* synthetic */ MediaQueueItem zza;
    final /* synthetic */ int zzb;
    final /* synthetic */ long zzc;
    final /* synthetic */ JSONObject zzd;
    final /* synthetic */ RemoteMediaPlayer zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcs(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, MediaQueueItem mediaQueueItem, int i, long j, JSONObject jSONObject) {
        super(remoteMediaPlayer, googleApiClient);
        this.zze = remoteMediaPlayer;
        this.zza = mediaQueueItem;
        this.zzb = i;
        this.zzc = j;
        this.zzd = jSONObject;
    }

    @Override // com.google.android.gms.cast.zzdp
    protected final void zza(com.google.android.gms.cast.internal.zzw zzwVar) throws com.google.android.gms.cast.internal.zzao {
        this.zze.zzb.zzw(zzb(), new MediaQueueItem[]{this.zza}, this.zzb, 0, 0, this.zzc, this.zzd);
    }
}
