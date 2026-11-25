package com.huawei.hms.mlsdk.asr.engine.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import com.huawei.hms.mlsdk.common.MLApplication;

/* JADX INFO: compiled from: NetworkHelper.java */
/* JADX INFO: loaded from: classes10.dex */
public class d {
    public static int a() {
        Context appContext = MLApplication.getInstance().getAppContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService("connectivity");
        TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService("phone");
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
                int type = activeNetworkInfo.getType();
                if (type == 1) {
                    int rssi = ((WifiManager) appContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getRssi();
                    SmartLogger.i("NetworkHelper", "wifi rssi ===" + rssi);
                    if (rssi >= -75 || rssi < -89) {
                        return rssi < -89 ? 2 : 4;
                    }
                    return 3;
                }
                if (type == 0) {
                    switch (activeNetworkInfo.getSubtype()) {
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                        case 17:
                            if (!telephonyManager.isNetworkRoaming()) {
                                return 3;
                            }
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                        case 16:
                            return 2;
                        case 13:
                        default:
                            String subtypeName = activeNetworkInfo.getSubtypeName();
                            if ("WCDMA".equalsIgnoreCase(subtypeName) || "CDMA2000".equalsIgnoreCase(subtypeName) || "TD-SCDMA".equalsIgnoreCase(subtypeName)) {
                                return !telephonyManager.isNetworkRoaming() ? 3 : 2;
                            }
                            break;
                    }
                }
                return 4;
            }
            return 1;
        } catch (Exception e) {
            SmartLogger.e("NetworkHelper", e.getMessage());
        }
    }
}
