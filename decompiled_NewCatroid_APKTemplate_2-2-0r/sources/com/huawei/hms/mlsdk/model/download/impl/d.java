package com.huawei.hms.mlsdk.model.download.impl;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadStrategy;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: ModelDownloadRequestManager.java */
/* JADX INFO: loaded from: classes10.dex */
public class d {
    private WeakReference<Context> a;

    public d(WeakReference<Context> weakReference) {
        this.a = weakReference;
    }

    public boolean a(MLModelDownloadStrategy mLModelDownloadStrategy) {
        int intExtra;
        boolean zIsConnected;
        NetworkInfo networkInfo;
        if (mLModelDownloadStrategy == null) {
            return true;
        }
        PowerManager powerManager = null;
        if (mLModelDownloadStrategy.isWifiNeed()) {
            Context context = this.a.get();
            if (context != null) {
                if (a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                    Object systemService = context.getSystemService("connectivity");
                    ConnectivityManager connectivityManager = systemService instanceof ConnectivityManager ? (ConnectivityManager) systemService : null;
                    if (connectivityManager != null && (networkInfo = connectivityManager.getNetworkInfo(1)) != null) {
                        zIsConnected = networkInfo.isConnected();
                    }
                } else {
                    SmartLog.w("MLSDK_MODEL_ModelDownloadRequestManager", "isWifiStatus miss ACCESS_NETWORK_STATE permission");
                }
                zIsConnected = false;
            } else {
                zIsConnected = false;
            }
            if (!zIsConnected) {
                return false;
            }
        }
        if (mLModelDownloadStrategy.isChargingNeed()) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
            Context context2 = this.a.get();
            Intent intentRegisterReceiver = context2 != null ? context2.registerReceiver(null, intentFilter) : null;
            if (!(intentRegisterReceiver != null && ((intExtra = intentRegisterReceiver.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1)) == 2 || intExtra == 5))) {
                return false;
            }
        }
        if (mLModelDownloadStrategy.isDeviceIdleNeed()) {
            Context context3 = this.a.get();
            if (context3 != null) {
                Object systemService2 = context3.getSystemService("power");
                if (systemService2 instanceof PowerManager) {
                    powerManager = (PowerManager) systemService2;
                }
            }
            if (!(powerManager == null ? false : powerManager.isDeviceIdleMode())) {
                return false;
            }
        }
        return true;
    }

    private boolean a(Context context, String str) {
        return context.checkSelfPermission(str) == 0;
    }
}
