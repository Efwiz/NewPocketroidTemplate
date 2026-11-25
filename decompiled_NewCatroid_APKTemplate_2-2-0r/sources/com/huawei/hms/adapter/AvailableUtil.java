package com.huawei.hms.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.huawei.hms.support.log.HMSLog;

/* JADX INFO: loaded from: classes10.dex */
public class AvailableUtil {
    public static final Object a = new Object();
    public static boolean b = false;
    public static boolean c = false;

    public static boolean isInstallerLibExist(Context context) {
        Object obj;
        boolean z;
        if (b) {
            HMSLog.i("AvailableUtil", "installerInit exist: " + c);
            return c;
        }
        synchronized (a) {
            if (!b) {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    HMSLog.e("AvailableUtil", "In isAvailableLibExist, Failed to get 'PackageManager' instance.");
                    try {
                        Class.forName("com.huawei.hms.update.manager.UpdateManager");
                        z = true;
                    } catch (ClassNotFoundException unused) {
                        HMSLog.e("AvailableUtil", "In isInstallerLibExist, Failed to find class UpdateManager.");
                        z = false;
                    }
                } else {
                    try {
                        ApplicationInfo applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 128).applicationInfo;
                        if (applicationInfo == null || applicationInfo.metaData == null || (obj = applicationInfo.metaData.get("availableHMSCoreInstaller")) == null || !String.valueOf(obj).equalsIgnoreCase("yes")) {
                            z = false;
                        } else {
                            HMSLog.i("AvailableUtil", "available exist: true");
                            z = true;
                        }
                    } catch (PackageManager.NameNotFoundException unused2) {
                        HMSLog.e("AvailableUtil", "In isInstallerLibExist, Failed to read meta data for the availableHMSCoreInstaller.");
                    } catch (RuntimeException e) {
                        HMSLog.e("AvailableUtil", "In isInstallerLibExist, Failed to read meta data for the availableHMSCoreInstaller.", e);
                    }
                }
                c = z;
                b = true;
            }
        }
        HMSLog.i("AvailableUtil", "available exist: " + c);
        return c;
    }
}
