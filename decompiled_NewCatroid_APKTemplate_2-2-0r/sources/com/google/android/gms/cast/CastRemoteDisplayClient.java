package com.google.android.gms.cast;

import android.app.PendingIntent;
import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Display;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
@Deprecated
public class CastRemoteDisplayClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    private static final Api.AbstractClientBuilder zza;
    private static final Api zzb;
    private final Logger zzc;
    private VirtualDisplay zzd;

    static {
        zzab zzabVar = new zzab();
        zza = zzabVar;
        zzb = new Api("CastRemoteDisplay.API", zzabVar, com.google.android.gms.cast.internal.zzak.zzd);
    }

    CastRemoteDisplayClient(Context context) {
        super(context, (Api<Api.ApiOptions.NoOptions>) zzb, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
        this.zzc = new Logger("CastRemoteDisplay");
    }

    static /* bridge */ /* synthetic */ void zzd(CastRemoteDisplayClient castRemoteDisplayClient) {
        VirtualDisplay virtualDisplay = castRemoteDisplayClient.zzd;
        if (virtualDisplay != null) {
            if (virtualDisplay.getDisplay() != null) {
                castRemoteDisplayClient.zzc.d("releasing virtual display: " + castRemoteDisplayClient.zzd.getDisplay().getDisplayId(), new Object[0]);
            }
            VirtualDisplay virtualDisplay2 = castRemoteDisplayClient.zzd;
            if (virtualDisplay2 != null) {
                virtualDisplay2.release();
                castRemoteDisplayClient.zzd = null;
            }
        }
    }

    public Task<Display> startRemoteDisplay(CastDevice castDevice, String str, int i, PendingIntent pendingIntent) {
        return zze(castDevice, str, i, pendingIntent, null);
    }

    public Task<Void> stopRemoteDisplay() {
        return doWrite(TaskApiCall.builder().setMethodKey(8402).run(new RemoteCall() { // from class: com.google.android.gms.cast.zzaa
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                ((com.google.android.gms.internal.cast.zzdt) ((com.google.android.gms.internal.cast.zzdo) obj).getService()).zzi(new zzad(this.zza, (TaskCompletionSource) obj2));
            }
        }).build());
    }

    public final Task zze(final CastDevice castDevice, final String str, final int i, final PendingIntent pendingIntent, final zzal zzalVar) {
        return doWrite(TaskApiCall.builder().setMethodKey(8401).run(new RemoteCall() { // from class: com.google.android.gms.cast.zzz
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                com.google.android.gms.internal.cast.zzdo zzdoVar = (com.google.android.gms.internal.cast.zzdo) obj;
                Bundle bundle = new Bundle();
                bundle.putInt("configuration", i);
                ((com.google.android.gms.internal.cast.zzdt) zzdoVar.getService()).zzh(new zzac(this.zza, (TaskCompletionSource) obj2, zzdoVar, zzalVar), pendingIntent, castDevice.getDeviceId(), str, bundle);
            }
        }).build());
    }
}
