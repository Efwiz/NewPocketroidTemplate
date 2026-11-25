package com.huawei.hms.ml.language.common.base;

import android.content.Context;
import com.huawei.hms.mlsdk.common.MLApplication;

/* JADX INFO: loaded from: classes10.dex */
public class ContextApplication {
    private static Context mContext = MLApplication.getInstance().getAppContext();

    public static Context getContext() {
        return mContext;
    }
}
