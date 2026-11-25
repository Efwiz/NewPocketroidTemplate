package com.google.android.gms.cast;

import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdo implements RemoteMediaPlayer.MediaChannelResult {
    final /* synthetic */ Status zza;

    zzdo(zzdp zzdpVar, Status status) {
        this.zza = status;
    }

    @Override // com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult
    public final JSONObject getCustomData() {
        return null;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zza;
    }
}
