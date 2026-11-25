package com.huawei.hms.ml.language.common.utils;

import com.huawei.hms.ml.common.utils.SmartLog;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class RuleVerificationUtil {
    private static final String TAG = "RuleVerificationUtil";

    public static boolean isHeaderInvalidate(Map<String, String> map) {
        String str = map.get("appId");
        if (str == null || str.isEmpty()) {
            SmartLog.e(TAG, "header app_id is empty");
            return true;
        }
        if (map.get("Authorization").replace("Bearer ", "").isEmpty()) {
            SmartLog.e(TAG, "header file api_key is empty");
            return true;
        }
        String str2 = map.get("X-Package-Name");
        if (str2 != null && !str2.isEmpty()) {
            return false;
        }
        SmartLog.e(TAG, "header file package_name is empty");
        return true;
    }
}
