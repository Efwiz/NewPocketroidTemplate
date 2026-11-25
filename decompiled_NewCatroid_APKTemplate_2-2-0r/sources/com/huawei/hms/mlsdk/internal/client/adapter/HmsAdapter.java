package com.huawei.hms.mlsdk.internal.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import com.huawei.hms.adapter.AvailableAdapter;
import com.huawei.hms.ml.common.utils.ActivityMgr;
import com.huawei.hms.ml.common.utils.SmartLog;

/* JADX INFO: loaded from: classes10.dex */
public class HmsAdapter {
    private static final String HMS_PACKAGE_NAME = "com.huawei.hwid";
    private static final String HSF_PACKAGE_NAME = "com.huawei.android.hsf";
    private static final String TAG = "HmsAdapter";
    private boolean isHmsInstalled = false;
    private boolean isUpdatePopupShown = false;
    private final AvailableAdapter.AvailableCallBack callBack = new AvailableAdapter.AvailableCallBack() { // from class: com.huawei.hms.mlsdk.internal.client.adapter.HmsAdapter.1
        @Override // com.huawei.hms.adapter.AvailableAdapter.AvailableCallBack
        public void onComplete(int i) {
            HmsAdapter.this.isUpdatePopupShown = false;
            HmsAdapter.this.isHmsInstalled = i == 0;
        }
    };

    public void release(Context context) {
    }

    public boolean isHmsAvailable(Context context, int i) {
        return getAvailableCode(context, i) == 0;
    }

    public boolean isHmsNeededUpdate(Context context, int i) {
        return getAvailableCode(context, i) == 2;
    }

    protected int getAvailableCode(Context context, int i) {
        return new AvailableAdapter(i).isHuaweiMobileServicesAvailable(context);
    }

    public void notifyDownloadHmsIfNeeded(Context context, int i) {
        AvailableAdapter availableAdapter;
        int iIsHuaweiMobileServicesAvailable;
        Activity currentActivity;
        if (this.isUpdatePopupShown || (iIsHuaweiMobileServicesAvailable = (availableAdapter = new AvailableAdapter(i)).isHuaweiMobileServicesAvailable(context)) == 0 || !availableAdapter.isUserResolvableError(iIsHuaweiMobileServicesAvailable) || (currentActivity = ActivityMgr.INST.getCurrentActivity()) == null || currentActivity.isDestroyed()) {
            return;
        }
        this.isUpdatePopupShown = true;
        availableAdapter.startResolution(currentActivity, this.callBack);
    }

    public boolean isHmsInstalled() {
        return this.isHmsInstalled;
    }

    public void resetStatus() {
        this.isHmsInstalled = false;
    }

    private static Activity findActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static boolean isHmsInternal(Context context) {
        return isPackageInternal(context, "com.huawei.hwid");
    }

    public static boolean isHsfInternal(Context context) {
        return isPackageInternal(context, HSF_PACKAGE_NAME);
    }

    public static final boolean isPackageInternal(Context context, String str) {
        try {
            return (context.getPackageManager().getPackageInfo(str, 0).applicationInfo.flags & 1) != 0;
        } catch (Exception e) {
            SmartLog.e(TAG, "isPackageInternal Exception e: " + e);
            return false;
        }
    }
}
