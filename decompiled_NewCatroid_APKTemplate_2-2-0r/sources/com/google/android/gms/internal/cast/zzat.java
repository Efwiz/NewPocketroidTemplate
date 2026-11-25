package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import android.text.TextUtils;
import androidx.mediarouter.media.MediaRouter;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzat extends MediaRouter.Callback {
    private static final Logger zza = new Logger("MediaRouterCallback");
    private final zzao zzb;

    public zzat(zzao zzaoVar) {
        this.zzb = (zzao) Preconditions.checkNotNull(zzaoVar);
    }

    @Override // androidx.mediarouter.media.MediaRouter.Callback
    public final void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        try {
            this.zzb.zzf(routeInfo.getId(), routeInfo.getExtras());
        } catch (RemoteException e) {
            zza.d(e, "Unable to call %s on %s.", "onRouteAdded", "zzao");
        }
    }

    @Override // androidx.mediarouter.media.MediaRouter.Callback
    public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        try {
            this.zzb.zzg(routeInfo.getId(), routeInfo.getExtras());
        } catch (RemoteException e) {
            zza.d(e, "Unable to call %s on %s.", "onRouteChanged", "zzao");
        }
    }

    @Override // androidx.mediarouter.media.MediaRouter.Callback
    public final void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        try {
            this.zzb.zzh(routeInfo.getId(), routeInfo.getExtras());
        } catch (RemoteException e) {
            zza.d(e, "Unable to call %s on %s.", "onRouteRemoved", "zzao");
        }
    }

    @Override // androidx.mediarouter.media.MediaRouter.Callback
    public final void onRouteSelected(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, int i) {
        CastDevice fromBundle;
        CastDevice fromBundle2;
        zza.i("onRouteSelected with reason = %d, routeId = %s", Integer.valueOf(i), routeInfo.getId());
        if (routeInfo.getPlaybackType() != 1) {
            return;
        }
        try {
            String id2 = routeInfo.getId();
            String id3 = routeInfo.getId();
            if (id3 != null && id3.endsWith("-groupRoute") && (fromBundle = CastDevice.getFromBundle(routeInfo.getExtras())) != null) {
                String deviceId = fromBundle.getDeviceId();
                for (MediaRouter.RouteInfo routeInfo2 : mediaRouter.getRoutes()) {
                    String id4 = routeInfo2.getId();
                    if (id4 != null && !id4.endsWith("-groupRoute") && (fromBundle2 = CastDevice.getFromBundle(routeInfo2.getExtras())) != null && TextUtils.equals(fromBundle2.getDeviceId(), deviceId)) {
                        zza.d("routeId is changed from %s to %s", id3, routeInfo2.getId());
                        id3 = routeInfo2.getId();
                        break;
                    }
                }
            }
            if (this.zzb.zze() >= 220400000) {
                this.zzb.zzj(id3, id2, routeInfo.getExtras());
            } else {
                this.zzb.zzi(id3, routeInfo.getExtras());
            }
        } catch (RemoteException e) {
            zza.d(e, "Unable to call %s on %s.", "onRouteSelected", "zzao");
        }
    }

    @Override // androidx.mediarouter.media.MediaRouter.Callback
    public final void onRouteUnselected(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, int i) {
        Logger logger = zza;
        logger.i("onRouteUnselected with reason = %d, routeId = %s", Integer.valueOf(i), routeInfo.getId());
        if (routeInfo.getPlaybackType() != 1) {
            logger.d("skip route unselection for non-cast route", new Object[0]);
            return;
        }
        try {
            this.zzb.zzk(routeInfo.getId(), routeInfo.getExtras(), i);
        } catch (RemoteException e) {
            zza.d(e, "Unable to call %s on %s.", "onRouteUnselected", "zzao");
        }
    }
}
