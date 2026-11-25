package com.google.android.gms.internal.cast;

import android.hardware.display.DisplayManager;
import android.os.RemoteException;
import android.view.Display;
import android.view.Surface;
import com.google.android.gms.common.api.Status;
import com.hivemq.client.mqtt.MqttProxyConfig;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdj extends zzdi {
    final /* synthetic */ zzdl zza;
    private final zzdq zzb;

    public zzdj(zzdl zzdlVar, zzdq zzdqVar) {
        this.zza = zzdlVar;
        this.zzb = zzdqVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.cast.zzdi, com.google.android.gms.internal.cast.zzds
    public final void zzb(int i, int i2, Surface surface) {
        zzdn.zza.d("onConnected", new Object[0]);
        DisplayManager displayManager = (DisplayManager) this.zzb.getContext().getSystemService("display");
        if (displayManager == null) {
            zzdn.zza.e("Unable to get the display manager", new Object[0]);
            this.zza.setResult(new zzdm(Status.RESULT_INTERNAL_ERROR));
            return;
        }
        zzdn.zzf(this.zza.zzc);
        this.zza.zzc.zzc = displayManager.createVirtualDisplay("private_display", i, i2, ((i < i2 ? i : i2) * 320) / MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT, surface, 2);
        zzdn zzdnVar = this.zza.zzc;
        if (zzdnVar.zzc == null) {
            zzdn.zza.e("Unable to create virtual display", new Object[0]);
            this.zza.setResult(new zzdm(Status.RESULT_INTERNAL_ERROR));
        } else {
            if (zzdnVar.zzc.getDisplay() == null) {
                zzdn.zza.e("Virtual display does not have a display", new Object[0]);
                this.zza.setResult(new zzdm(Status.RESULT_INTERNAL_ERROR));
                return;
            }
            try {
                ((zzdt) this.zzb.getService()).zzf(this, this.zza.zzc.zzc.getDisplay().getDisplayId());
            } catch (RemoteException | IllegalStateException unused) {
                zzdn.zza.e("Unable to provision the route's new virtual Display", new Object[0]);
                this.zza.setResult(new zzdm(Status.RESULT_INTERNAL_ERROR));
            }
        }
    }

    @Override // com.google.android.gms.internal.cast.zzdi, com.google.android.gms.internal.cast.zzds
    public final void zzc() {
        zzdn.zza.d("onConnectedWithDisplay", new Object[0]);
        zzdn zzdnVar = this.zza.zzc;
        if (zzdnVar.zzc == null) {
            zzdn.zza.e("There is no virtual display", new Object[0]);
            this.zza.setResult(new zzdm(Status.RESULT_INTERNAL_ERROR));
            return;
        }
        Display display = zzdnVar.zzc.getDisplay();
        if (display != null) {
            this.zza.setResult(new zzdm(display));
        } else {
            zzdn.zza.e("Virtual display no longer has a display", new Object[0]);
            this.zza.setResult(new zzdm(Status.RESULT_INTERNAL_ERROR));
        }
    }

    @Override // com.google.android.gms.internal.cast.zzdi, com.google.android.gms.internal.cast.zzds
    public final void zzd(int i) throws RemoteException {
        zzdn.zza.d("onError: %d", Integer.valueOf(i));
        zzdn.zzf(this.zza.zzc);
        this.zza.setResult(new zzdm(Status.RESULT_INTERNAL_ERROR));
    }
}
