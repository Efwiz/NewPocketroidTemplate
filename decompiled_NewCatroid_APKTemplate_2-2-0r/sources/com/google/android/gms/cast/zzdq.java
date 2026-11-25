package com.google.android.gms.cast;

import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdq implements RemoteMediaPlayer.MediaChannelResult {
    private final Status zza;
    private final JSONObject zzb;

    zzdq(Status status, JSONObject jSONObject) {
        this.zza = status;
        this.zzb = jSONObject;
    }

    @Override // com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult
    public final JSONObject getCustomData() {
        return this.zzb;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zza;
    }
}
