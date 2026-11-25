package com.huawei.hms.mlsdk.model.download.strategy;

import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.impl.ModelResponse;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ModelConfigManagerStrategy {
    public static final String CURRENT_HASH = "currentHash";

    public abstract Map<String, String> getAddConfigItem(MLRemoteModel mLRemoteModel, String str, ModelResponse modelResponse);

    public abstract String getConfigFileName(MLRemoteModel mLRemoteModel);

    public abstract List<String> getDeleteConfigItem(MLRemoteModel mLRemoteModel);

    public abstract String getOriginHash(MLRemoteModel mLRemoteModel, ModelResponse modelResponse);

    public abstract boolean isModelFileNeedUpdate(MLRemoteModel mLRemoteModel, Map<String, String> map);

    public abstract boolean isModelVersionNeedUpdated(MLRemoteModel mLRemoteModel, ModelResponse modelResponse, Map<String, String> map);
}
