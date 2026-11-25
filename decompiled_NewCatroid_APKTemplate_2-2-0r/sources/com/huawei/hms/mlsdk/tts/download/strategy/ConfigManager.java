package com.huawei.hms.mlsdk.tts.download.strategy;

import com.beust.jcommander.Parameters;
import com.google.gson.Gson;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.impl.ModelResponse;
import com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy;
import com.huawei.hms.mlsdk.t.i0;
import com.huawei.hms.mlsdk.tts.download.utils.ModelLevelUtils;
import com.huawei.hms.mlsdk.tts.engine.common.ModelUrl;
import com.huawei.hms.mlsdk.tts.engine.common.ModelUrlQueryResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class ConfigManager extends ModelConfigManagerStrategy {
    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy
    public Map<String, String> getAddConfigItem(MLRemoteModel mLRemoteModel, String str, ModelResponse modelResponse) {
        ModelUrlQueryResponse modelUrlQueryResponse = (ModelUrlQueryResponse) new Gson().fromJson(modelResponse.getResponseBody(), ModelUrlQueryResponse.class);
        ModelUrl modelUrl = modelUrlQueryResponse.getData().get(0);
        if (modelUrlQueryResponse.getData() == null || modelUrlQueryResponse.getData().isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap map = new HashMap();
        map.put(mLRemoteModel.getModelName() + "-model-version", modelUrl.getModelVersion());
        map.put(mLRemoteModel.getModelName() + "-hash", str);
        map.put(mLRemoteModel.getModelName(), modelUrl.getModelName() + Parameters.DEFAULT_OPTION_PREFIXES + modelUrl.getModelVersion());
        return map;
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy
    public String getConfigFileName(MLRemoteModel mLRemoteModel) {
        return "tts";
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy
    public List<String> getDeleteConfigItem(MLRemoteModel mLRemoteModel) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(mLRemoteModel.getModelName());
        arrayList.add(mLRemoteModel.getModelName() + "-model-version");
        arrayList.add(mLRemoteModel.getModelName() + "-hash");
        return arrayList;
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy
    public String getOriginHash(MLRemoteModel mLRemoteModel, ModelResponse modelResponse) {
        ModelUrlQueryResponse modelUrlQueryResponse = (ModelUrlQueryResponse) new Gson().fromJson(modelResponse.getResponseBody(), ModelUrlQueryResponse.class);
        if (modelUrlQueryResponse.getData() == null || modelUrlQueryResponse.getData().isEmpty()) {
            return null;
        }
        return modelUrlQueryResponse.getData().get(0).getContentSha256();
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy
    public boolean isModelFileNeedUpdate(MLRemoteModel mLRemoteModel, Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            i0.c("ConfigManager", "isModelFileNeedUpdate configItem invalid");
            return true;
        }
        String str = map.get(ModelConfigManagerStrategy.CURRENT_HASH);
        if (str == null || str.isEmpty()) {
            i0.c("ConfigManager", "isModelFileNeedUpdate currentHash invalid");
            return true;
        }
        String str2 = map.get(mLRemoteModel.getModelName() + "-hash");
        if (str2 != null && !str2.isEmpty()) {
            return !str2.equals(str) || map.get(new StringBuilder().append(mLRemoteModel.getModelName()).append("-model-version").toString()).compareTo(String.valueOf(ModelLevelUtils.getModelLevel(mLRemoteModel.getModelName()))) < 0;
        }
        i0.c("ConfigManager", "isModelFileNeedUpdate originHash invalid");
        return true;
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy
    public boolean isModelVersionNeedUpdated(MLRemoteModel mLRemoteModel, ModelResponse modelResponse, Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            i0.c("ConfigManager", "isModelVersionNeedUpdated configItem invalid");
            return true;
        }
        ModelUrlQueryResponse modelUrlQueryResponse = (ModelUrlQueryResponse) new Gson().fromJson(modelResponse.getResponseBody(), ModelUrlQueryResponse.class);
        if (modelUrlQueryResponse.getData() == null || modelUrlQueryResponse.getData().isEmpty()) {
            i0.c("ConfigManager", "isModelVersionNeedUpdated fromJson invalid");
            return true;
        }
        return !modelUrlQueryResponse.getData().get(0).getModelVersion().equals(map.get(mLRemoteModel.getModelName() + "-model-version"));
    }
}
