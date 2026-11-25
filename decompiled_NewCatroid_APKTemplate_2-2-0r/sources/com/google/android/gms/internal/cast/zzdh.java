package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdh extends zzdl {
    zzdh(zzdn zzdnVar, GoogleApiClient googleApiClient) {
        super(zzdnVar, googleApiClient);
    }

    @Override // com.google.android.gms.internal.cast.zzdl, com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    public final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        doExecute((zzdq) anyClient);
    }

    @Override // com.google.android.gms.internal.cast.zzdl
    /* JADX INFO: renamed from: zza */
    public final void doExecute(zzdq zzdqVar) throws RemoteException {
        zzdqVar.zzs(new zzdk(this));
    }
}
