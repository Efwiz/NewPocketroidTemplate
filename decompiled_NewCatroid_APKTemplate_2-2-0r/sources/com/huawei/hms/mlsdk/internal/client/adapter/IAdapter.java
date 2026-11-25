package com.huawei.hms.mlsdk.internal.client.adapter;

import android.content.Context;
import com.huawei.hms.mlsdk.dynamic.IInitializer;

/* JADX INFO: loaded from: classes10.dex */
public interface IAdapter {
    boolean isAvailable(Context context, IInitializer iInitializer);

    void notifyDownloadIfNeeded(Context context, IInitializer iInitializer);

    void release(Context context);
}
