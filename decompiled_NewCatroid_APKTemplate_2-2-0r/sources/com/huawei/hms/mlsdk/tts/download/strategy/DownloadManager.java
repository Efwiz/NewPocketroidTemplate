package com.huawei.hms.mlsdk.tts.download.strategy;

import com.beust.jcommander.Parameters;
import com.google.gson.Gson;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.impl.ModelResponse;
import com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy;
import com.huawei.hms.mlsdk.t.a;
import com.huawei.hms.mlsdk.t.h0;
import com.huawei.hms.mlsdk.tts.download.data.ModelQueryRequest;
import com.huawei.hms.mlsdk.tts.download.utils.ModelLevelUtils;
import com.huawei.hms.mlsdk.tts.engine.common.ModelUrl;
import com.huawei.hms.mlsdk.tts.engine.common.ModelUrlQueryResponse;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes10.dex */
public class DownloadManager extends ModelDownloadStrategy {
    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy
    public String getDownloadUrl(ModelResponse modelResponse) {
        ModelUrlQueryResponse modelUrlQueryResponse = (ModelUrlQueryResponse) new Gson().fromJson(modelResponse.getResponseBody(), ModelUrlQueryResponse.class);
        if (modelUrlQueryResponse.getData() == null || modelUrlQueryResponse.getData().isEmpty() || modelUrlQueryResponse.getData().get(0).getDownloadUrl().isEmpty()) {
            return null;
        }
        return modelUrlQueryResponse.getData().get(0).getDownloadUrl();
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy
    public String getHaField(ModelResponse modelResponse) {
        ModelUrlQueryResponse modelUrlQueryResponse = (ModelUrlQueryResponse) new Gson().fromJson(modelResponse.getResponseBody(), ModelUrlQueryResponse.class);
        if (modelUrlQueryResponse.getData() == null || modelUrlQueryResponse.getData().size() == 0) {
            return null;
        }
        ModelUrl modelUrl = modelUrlQueryResponse.getData().get(0);
        return a.a("MLKit-TTS-ModuleDownload-").append(modelUrl.getModelName()).append(Parameters.DEFAULT_OPTION_PREFIXES).append(ModelLevelUtils.getModelLevel(modelUrl.getModelName())).toString();
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy
    public String getModelFileName(ModelResponse modelResponse) {
        ModelUrlQueryResponse modelUrlQueryResponse = (ModelUrlQueryResponse) new Gson().fromJson(modelResponse.getResponseBody(), ModelUrlQueryResponse.class);
        if (modelUrlQueryResponse.getData() == null || modelUrlQueryResponse.getData().isEmpty()) {
            return null;
        }
        ModelUrl modelUrl = modelUrlQueryResponse.getData().get(0);
        return modelUrl.getModelName() + Parameters.DEFAULT_OPTION_PREFIXES + modelUrl.getModelVersion() + Constants.ZIP_EXTENSION;
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy
    public String getRequestBody(MLRemoteModel mLRemoteModel) {
        return new Gson().toJson(new ModelQueryRequest(mLRemoteModel.getModelName(), "", h0.a(), ModelLevelUtils.getModelLevel(mLRemoteModel.getModelName()), 1));
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy
    public String getRequestUrl() {
        return "/v1/model/queryModel";
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelDownloadStrategy
    public void handleRequestFail(ModelResponse modelResponse) throws MLException {
        if (!"001001".equals(((ModelUrlQueryResponse) new Gson().fromJson(modelResponse.getResponseBody(), ModelUrlQueryResponse.class)).getRetCode())) {
            throw new MLException("Download model failed", 2);
        }
        throw new MLException("Token is invalid or expired", 19);
    }
}
