package com.huawei.hms.mlsdk.model.download.strategy;

import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.impl.ModelResponse;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ModelDownloadStrategy {
    public abstract String getDownloadUrl(ModelResponse modelResponse);

    public abstract String getHaField(ModelResponse modelResponse);

    public abstract String getModelFileName(ModelResponse modelResponse);

    public abstract String getRequestBody(MLRemoteModel mLRemoteModel);

    public abstract String getRequestUrl();

    public abstract void handleRequestFail(ModelResponse modelResponse) throws MLException;
}
