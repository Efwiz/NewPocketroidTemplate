package com.google.android.gms.cast.framework.media;

import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbc extends zzbk {
    final /* synthetic */ boolean zza;
    final /* synthetic */ JSONObject zzb;
    final /* synthetic */ RemoteMediaClient zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbc(RemoteMediaClient remoteMediaClient, boolean z, JSONObject jSONObject) {
        super(remoteMediaClient, false);
        this.zzc = remoteMediaClient;
        this.zza = z;
        this.zzb = jSONObject;
    }

    @Override // com.google.android.gms.cast.framework.media.zzbk
    protected final void zza() throws com.google.android.gms.cast.internal.zzao {
        this.zzc.zzd.zzF(zzb(), this.zza, this.zzb);
    }
}
