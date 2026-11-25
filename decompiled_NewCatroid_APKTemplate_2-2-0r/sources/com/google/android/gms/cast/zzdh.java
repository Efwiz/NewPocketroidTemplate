package com.google.android.gms.cast;

import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdh extends zzdp {
    final /* synthetic */ long zza;
    final /* synthetic */ int zzb;
    final /* synthetic */ JSONObject zzc;
    final /* synthetic */ RemoteMediaPlayer zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdh(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, long j, int i, JSONObject jSONObject) {
        super(remoteMediaPlayer, googleApiClient);
        this.zzd = remoteMediaPlayer;
        this.zza = j;
        this.zzb = i;
        this.zzc = jSONObject;
    }

    @Override // com.google.android.gms.cast.zzdp
    protected final void zza(com.google.android.gms.cast.internal.zzw zzwVar) throws com.google.android.gms.cast.internal.zzao {
        com.google.android.gms.cast.internal.zzaq zzaqVar = this.zzd.zzb;
        com.google.android.gms.cast.internal.zzas zzasVarZzb = zzb();
        MediaSeekOptions.Builder builder = new MediaSeekOptions.Builder();
        builder.setPosition(this.zza);
        builder.setResumeState(this.zzb);
        builder.setCustomData(this.zzc);
        zzaqVar.zzC(zzasVarZzb, builder.build());
    }
}
