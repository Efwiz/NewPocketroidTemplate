package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdg extends zzdl {
    final /* synthetic */ String zza;
    final /* synthetic */ zzdn zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdg(zzdn zzdnVar, GoogleApiClient googleApiClient, String str) {
        super(zzdnVar, googleApiClient);
        this.zzb = zzdnVar;
        this.zza = str;
    }

    @Override // com.google.android.gms.internal.cast.zzdl, com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    public final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        doExecute((zzdq) anyClient);
    }

    @Override // com.google.android.gms.internal.cast.zzdl
    /* JADX INFO: renamed from: zza */
    public final void doExecute(zzdq zzdqVar) throws RemoteException {
        zzdqVar.zzr(new zzdj(this, zzdqVar), this.zzb.zzd, this.zza);
    }
}
