package com.huawei.hms.mlsdk.dynamic.policy;

import android.content.Context;
import com.huawei.hms.feature.dynamic.DynamicModule;
import com.huawei.hms.ml.common.utils.SmartLog;

/* JADX INFO: loaded from: classes10.dex */
public class RemotePolicy extends AbstractContextPolicy {
    private static final String TAG = "RemotePolicy";

    @Override // com.huawei.hms.mlsdk.dynamic.policy.AbstractContextPolicy
    public Context getPolicyContext(Context context, String str) {
        if (getRemoteVersion(context, str) <= 0) {
            return null;
        }
        try {
            DynamicModule.enable3rdPhone(str, true);
            DynamicModule.enableLowEMUI(str, true);
            return DynamicModule.load(context, DynamicModule.PREFER_REMOTE, str).getModuleContext();
        } catch (Exception e) {
            SmartLog.e(TAG, "getDynamicContext Exception e: " + e);
            return null;
        }
    }
}
