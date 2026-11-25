package com.huawei.hms.mlsdk.dynamic.policy;

import android.content.Context;
import com.huawei.hms.feature.dynamic.DynamicModule;
import com.huawei.hms.ml.common.utils.SmartLog;

/* JADX INFO: loaded from: classes10.dex */
public class PreferHighestOrRemote extends AbstractContextPolicy {
    private static final String TAG = "PreferHighestOrRemote";

    @Override // com.huawei.hms.mlsdk.dynamic.policy.AbstractContextPolicy
    public Context getPolicyContext(Context context, String str) {
        int localVersion = getLocalVersion(context, str);
        int remoteVersion = getRemoteVersion(context, str);
        String str2 = TAG;
        SmartLog.i(str2, "localVersion is: " + localVersion);
        SmartLog.i(str2, "remoteVersion is: " + remoteVersion);
        if (localVersion > 0 && localVersion > remoteVersion) {
            SmartLog.i(str2, "use local ML ability, the localVersion is: " + localVersion);
            return context;
        }
        try {
            DynamicModule.enable3rdPhone(str, true);
            DynamicModule.enableLowEMUI(str, true);
            Context moduleContext = DynamicModule.load(context, DynamicModule.PREFER_REMOTE, str).getModuleContext();
            SmartLog.i(str2, "use remote ML ability, the remoteVersion is: " + remoteVersion);
            return moduleContext;
        } catch (Exception e) {
            SmartLog.e(TAG, "getDynamicContext Exception e: " + e);
            if (localVersion > 0) {
                return context;
            }
            return null;
        }
    }
}
