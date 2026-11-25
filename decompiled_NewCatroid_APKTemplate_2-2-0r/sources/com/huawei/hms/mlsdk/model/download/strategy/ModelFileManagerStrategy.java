package com.huawei.hms.mlsdk.model.download.strategy;

import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ModelFileManagerStrategy {
    public abstract String getModelFilePath(MLRemoteModel mLRemoteModel, String str);

    public abstract String getModelFilePath(MLRemoteModel mLRemoteModel, Map<String, String> map);

    public abstract String getModelFolderPath(MLRemoteModel mLRemoteModel);

    public abstract boolean isNeedUnCompress();
}
