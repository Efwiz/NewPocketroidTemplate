package com.google.android.gms.cast;

import android.hardware.display.DisplayManager;
import android.os.RemoteException;
import android.view.Display;
import android.view.Surface;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.hivemq.client.mqtt.MqttProxyConfig;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzac extends zzaf {
    final /* synthetic */ TaskCompletionSource zza;
    final /* synthetic */ com.google.android.gms.internal.cast.zzdo zzb;
    final /* synthetic */ CastRemoteDisplayClient zzc;
    final /* synthetic */ zzal zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzac(CastRemoteDisplayClient castRemoteDisplayClient, TaskCompletionSource taskCompletionSource, com.google.android.gms.internal.cast.zzdo zzdoVar, zzal zzalVar) {
        super(null);
        this.zzc = castRemoteDisplayClient;
        this.zza = taskCompletionSource;
        this.zzb = zzdoVar;
        this.zzd = zzalVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.cast.zzaf, com.google.android.gms.internal.cast.zzds
    public final void zzb(int i, int i2, Surface surface) throws RemoteException {
        this.zzc.zzc.d("onConnected", new Object[0]);
        DisplayManager displayManager = (DisplayManager) this.zzc.getApplicationContext().getSystemService("display");
        if (displayManager == null) {
            this.zzc.zzc.e("Unable to get the display manager", new Object[0]);
            TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zza);
            return;
        }
        CastRemoteDisplayClient.zzd(this.zzc);
        int iMin = Math.min(i, i2) * 320;
        this.zzc.zzd = displayManager.createVirtualDisplay("private_display", i, i2, iMin / MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT, surface, 2);
        CastRemoteDisplayClient castRemoteDisplayClient = this.zzc;
        if (castRemoteDisplayClient.zzd == null) {
            castRemoteDisplayClient.zzc.e("Unable to create virtual display", new Object[0]);
            TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zza);
            return;
        }
        Display display = castRemoteDisplayClient.zzd.getDisplay();
        if (display == null) {
            this.zzc.zzc.e("Virtual display does not have a display", new Object[0]);
            TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zza);
        } else {
            try {
                ((com.google.android.gms.internal.cast.zzdt) this.zzb.getService()).zzf(this, display.getDisplayId());
            } catch (RemoteException | IllegalStateException unused) {
                this.zzc.zzc.e("Unable to provision the route's new virtual Display", new Object[0]);
                TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zza);
            }
        }
    }

    @Override // com.google.android.gms.cast.zzaf, com.google.android.gms.internal.cast.zzds
    public final void zzc() {
        this.zzc.zzc.d("onConnectedWithDisplay", new Object[0]);
        CastRemoteDisplayClient castRemoteDisplayClient = this.zzc;
        if (castRemoteDisplayClient.zzd == null) {
            castRemoteDisplayClient.zzc.e("There is no virtual display", new Object[0]);
            TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zza);
            return;
        }
        Display display = castRemoteDisplayClient.zzd.getDisplay();
        if (display != null) {
            TaskUtil.setResultOrApiException(Status.RESULT_SUCCESS, display, this.zza);
        } else {
            this.zzc.zzc.e("Virtual display no longer has a display", new Object[0]);
            TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zza);
        }
    }

    @Override // com.google.android.gms.cast.zzaf, com.google.android.gms.internal.cast.zzds
    public final void zzd(int i) throws RemoteException {
        this.zzc.zzc.d("onError: %d", Integer.valueOf(i));
        CastRemoteDisplayClient.zzd(this.zzc);
        TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zza);
    }

    @Override // com.google.android.gms.cast.zzaf, com.google.android.gms.internal.cast.zzds
    public final void zze(boolean z) {
        this.zzc.zzc.d("onRemoteDisplayMuteStateChanged: %b", Boolean.valueOf(z));
        zzal zzalVar = this.zzd;
        if (zzalVar != null) {
            zzalVar.zza.zzv("onRemoteDisplayMuteStateChanged: " + z);
            CastRemoteDisplayLocalService.Callbacks callbacks = (CastRemoteDisplayLocalService.Callbacks) zzalVar.zza.zzg.get();
            if (callbacks != null) {
                callbacks.onRemoteDisplayMuteStateChanged(z);
            }
        }
    }
}
