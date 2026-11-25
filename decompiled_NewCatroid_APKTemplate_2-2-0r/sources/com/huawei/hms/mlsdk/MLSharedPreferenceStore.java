package com.huawei.hms.mlsdk;

import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public final class MLSharedPreferenceStore {
    private static Map<String, MLSharedPreferenceStore> storeMap = new HashMap();
    private final MLApplication application;

    private MLSharedPreferenceStore(MLApplication mLApplication) {
        this.application = mLApplication;
    }

    public static MLSharedPreferenceStore getAppStore(MLApplication mLApplication) {
        MLSharedPreferenceStore mLSharedPreferenceStore;
        String uniqueKey = mLApplication.getUniqueKey();
        synchronized (storeMap) {
            if (!storeMap.containsKey(uniqueKey)) {
                storeMap.put(uniqueKey, new MLSharedPreferenceStore(mLApplication));
            }
            mLSharedPreferenceStore = storeMap.get(uniqueKey);
        }
        return mLSharedPreferenceStore;
    }

    public final synchronized boolean isStatisticsAllowed() {
        return this.application.getAppContext().getSharedPreferences("com.huawei.hms.mlsdk", 0).getBoolean(String.format(Locale.ENGLISH, "statistics allowed %s", this.application.getUniqueKey()), true);
    }

    public final synchronized void setStatisticsAllowed(boolean z) {
        this.application.getAppContext().getSharedPreferences("com.huawei.hms.mlsdk", 0).edit().putBoolean(String.format(Locale.ENGLISH, "statistics allowed %s", this.application.getUniqueKey()), z).apply();
    }
}
