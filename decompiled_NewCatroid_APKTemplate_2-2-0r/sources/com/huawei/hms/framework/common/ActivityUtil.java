package com.huawei.hms.framework.common;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ActivityUtil {
    private static final String TAG = "ActivityUtil";

    public static boolean isForeground(Context context) {
        ActivityManager activityManager;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (context == null || (activityManager = (ActivityManager) ContextCompat.getSystemService(context, "activity")) == null) {
            return false;
        }
        try {
            runningAppProcesses = activityManager.getRunningAppProcesses();
        } catch (RuntimeException e) {
            Logger.w(TAG, "activityManager getRunningAppProcesses occur exception: ", e);
            runningAppProcesses = null;
        }
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.processName != null && runningAppProcessInfo.processName.equals(context.getPackageName()) && runningAppProcessInfo.importance == 100) {
                Logger.v(TAG, "isForeground true");
                return true;
            }
        }
        return false;
    }

    public static PendingIntent getActivities(Context context, int i, Intent[] intentArr, int i2) {
        if (context == null) {
            Logger.w(TAG, "context is null");
            return null;
        }
        try {
            return PendingIntent.getActivities(context, i, intentArr, i2);
        } catch (RuntimeException e) {
            Logger.e(TAG, "dealType rethrowFromSystemServer:", e);
            return null;
        }
    }
}
