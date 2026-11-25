package com.huawei.hms.mlsdk.internal.client.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.hms.feature.dynamic.b;
import com.huawei.hms.ml.common.utils.ActivityMgr;
import com.huawei.hms.ml.common.utils.SmartLog;

/* JADX INFO: loaded from: classes10.dex */
public class ModuleAdapter {
    private static final String TAG = "ModuleAdapter";
    private boolean isUpdatePopupShown = false;

    public void release(Context context) {
    }

    public boolean notifyDownloadModuleIfNeeded(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        int i = bundle.getInt(b.h, -1);
        Intent intent = (Intent) bundle.getParcelable("resolution");
        Activity currentActivity = ActivityMgr.INST.getCurrentActivity();
        if (i == 2 && intent != null && currentActivity != null && !currentActivity.isDestroyed()) {
            try {
                currentActivity.startActivityForResult(intent, 2);
                return true;
            } catch (ActivityNotFoundException e) {
                SmartLog.e(TAG, "notifyDownloadModuleIfNeeded e: " + e);
            } catch (Exception e2) {
                SmartLog.e(TAG, "notifyDownloadModuleIfNeeded e: " + e2);
            }
        }
        return false;
    }
}
