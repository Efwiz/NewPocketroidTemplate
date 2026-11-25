package com.huawei.hms.mlsdk.dynamic;

import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;

/* JADX INFO: loaded from: classes10.dex */
public interface IInitializer {
    Context getDynamicContext();

    IInterface getDynamicDelegate();

    Bundle getLoadExceptionBundle();

    int getLocalVersion();

    int getRemoteVersion();

    void initial(Context context);

    boolean isLocalExisted();

    boolean isOperational();

    boolean isRemoteExisted();

    void release();

    boolean switchDynamicContext();
}
