package com.huawei.hms.feature.dynamic.e;

import android.content.Context;
import android.os.Bundle;
import com.huawei.hms.common.util.Logger;
import com.huawei.hms.feature.dynamic.DynamicModule;

/* JADX INFO: loaded from: classes10.dex */
public class e implements DynamicModule.VersionPolicy {
    public static final String a = "e";

    @Override // com.huawei.hms.feature.dynamic.DynamicModule.VersionPolicy
    public Bundle getModuleInfo(Context context, String str) throws DynamicModule.LoadingException {
        Bundle remoteModuleInfo = DynamicModule.getRemoteModuleInfo(context, str);
        if (remoteModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.k) > 0) {
            Logger.i(a, "Prefer remote: The version of remote module " + str + ":" + remoteModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.k));
            return remoteModuleInfo;
        }
        Bundle localModuleInfo = DynamicModule.getLocalModuleInfo(context, str);
        if (localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.l) > 0) {
            Logger.i(a, "Choose local: The version of local module " + str + ":" + localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.l));
            return localModuleInfo;
        }
        Logger.i(a, "Cannot get module info in remote or local.");
        return new Bundle();
    }
}
