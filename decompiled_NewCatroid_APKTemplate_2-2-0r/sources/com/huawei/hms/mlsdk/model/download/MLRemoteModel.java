package com.huawei.hms.mlsdk.model.download;

import com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy;
import com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy;
import com.huawei.hms.mlsdk.model.download.strategy.ModelFileManagerStrategy;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MLRemoteModel {
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public abstract ModelConfigManagerStrategy getConfigStrategy();

    public abstract ModelDownloadStrategy getDownloadStrategy();

    public abstract ModelFileManagerStrategy getFileStrategy();

    public abstract String getModelName();

    public int hashCode() {
        return super.hashCode();
    }
}
