package com.huawei.hms.mlsdk.dynamic.policy;

import android.content.Context;
import com.huawei.hms.feature.dynamic.DynamicModule;
import com.huawei.hms.ml.common.utils.SmartLog;

/* JADX INFO: loaded from: classes10.dex */
public abstract class AbstractContextPolicy implements IContextPolicy {
    private static final String TAG = "AbstractContextPolicy";

    public abstract Context getPolicyContext(Context context, String str);

    @Override // com.huawei.hms.mlsdk.dynamic.policy.IContextPolicy
    public Context getDynamicContext(Context context, String str) {
        return getPolicyContext(context, str);
    }

    public int getLocalVersion(Context context, String str) {
        return DynamicModule.getLocalVersion(context, str);
    }

    public int getRemoteVersion(Context context, String str) {
        try {
            return DynamicModule.getRemoteVersion(context, str);
        } catch (Exception e) {
            SmartLog.e(TAG, "getRemoteVersion Exception e: " + e);
            return -1;
        }
    }
}
