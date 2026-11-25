package com.google.android.gms.cast;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdl implements ResultCallback {
    final /* synthetic */ zzdm zza;
    private final long zzb;

    zzdl(zzdm zzdmVar, long j) {
        this.zza = zzdmVar;
        this.zzb = j;
    }

    @Override // com.google.android.gms.common.api.ResultCallback
    public final /* bridge */ /* synthetic */ void onResult(Result result) {
        Status status = (Status) result;
        if (status.isSuccess()) {
            return;
        }
        zzdm zzdmVar = this.zza;
        zzdmVar.zza.zzb.zzP(this.zzb, status.getStatusCode());
    }
}
