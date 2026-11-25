package com.huawei.hms.mlsdk.dynamic.policy;

import android.content.Context;
import com.huawei.hms.feature.dynamic.DynamicModule;
import com.huawei.hms.ml.common.utils.SmartLog;

/* JADX INFO: loaded from: classes10.dex */
public class PreferHighestOrLocal extends AbstractContextPolicy {
    private static final String TAG = "PreferHighestOrLocal";

    @Override // com.huawei.hms.mlsdk.dynamic.policy.AbstractContextPolicy
    public Context getPolicyContext(Context context, String str) {
        int localVersion = getLocalVersion(context, str);
        int remoteVersion = getRemoteVersion(context, str);
        if (localVersion > 0 && localVersion >= remoteVersion) {
            return context;
        }
        if (remoteVersion > 0 && remoteVersion > localVersion) {
            try {
                DynamicModule.enable3rdPhone(str, true);
                DynamicModule.enableLowEMUI(str, true);
                return DynamicModule.load(context, DynamicModule.PREFER_REMOTE, str).getModuleContext();
            } catch (Exception e) {
                SmartLog.e(TAG, "getDynamicContext Exception e: " + e);
            }
        }
        if (localVersion > 0) {
            return context;
        }
        return null;
    }
}
