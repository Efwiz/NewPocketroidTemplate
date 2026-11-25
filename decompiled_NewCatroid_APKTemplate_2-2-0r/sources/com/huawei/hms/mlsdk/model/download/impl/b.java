package com.huawei.hms.mlsdk.model.download.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: ModelConfigManager.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    private WeakReference<Context> a;
    private com.huawei.hms.mlsdk.model.download.strategy.a b;

    public b(WeakReference<Context> weakReference, com.huawei.hms.mlsdk.model.download.strategy.a aVar) {
        this.a = weakReference;
        this.b = aVar;
    }

    public boolean a(MLRemoteModel mLRemoteModel, String str, ModelResponse modelResponse) {
        ModelConfigManagerStrategy modelConfigManagerStrategyA = this.b.a(mLRemoteModel);
        String configFileName = modelConfigManagerStrategyA.getConfigFileName(mLRemoteModel);
        SmartLog.d("MLSDK_MODEL_ModelConfigManager", "addConfig configFileName: com.huawei.hms.ml.model.download." + configFileName);
        Context context = this.a.get();
        if (context == null) {
            SmartLog.e("MLSDK_MODEL_ModelConfigManager", "addConfig context is invaild");
            return false;
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("com.huawei.hms.ml.model.download." + configFileName, 0).edit();
        for (Map.Entry<String, String> entry : modelConfigManagerStrategyA.getAddConfigItem(mLRemoteModel, str, modelResponse).entrySet()) {
            editorEdit.putString(entry.getKey(), entry.getValue());
            SmartLog.d("MLSDK_MODEL_ModelConfigManager", "key: " + entry.getKey() + " value: " + entry.getValue());
        }
        editorEdit.commit();
        return true;
    }

    public Map<String, String> b(MLRemoteModel mLRemoteModel) {
        String string = com.huawei.hms.mlsdk.model.download.p.a.a("com.huawei.hms.ml.model.download.").append(this.b.a(mLRemoteModel).getConfigFileName(mLRemoteModel)).toString();
        SmartLog.i("MLSDK_MODEL_ModelConfigManager", "getAllConfig: " + string);
        Context context = this.a.get();
        if (context != null) {
            return Collections.unmodifiableMap(context.getSharedPreferences(string, 0).getAll());
        }
        SmartLog.e("MLSDK_MODEL_ModelConfigManager", "getAllConfig context is invaild");
        return null;
    }

    public Map<String, String> c(MLRemoteModel mLRemoteModel) {
        ModelConfigManagerStrategy modelConfigManagerStrategyA = this.b.a(mLRemoteModel);
        String configFileName = modelConfigManagerStrategyA.getConfigFileName(mLRemoteModel);
        List<String> deleteConfigItem = modelConfigManagerStrategyA.getDeleteConfigItem(mLRemoteModel);
        Context context = this.a.get();
        if (context == null) {
            SmartLog.e("MLSDK_MODEL_ModelConfigManager", "isModelExist context is invaild");
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.huawei.hms.ml.model.download." + configFileName, 0);
        HashMap map = new HashMap();
        for (String str : deleteConfigItem) {
            String string = sharedPreferences.getString(str, "");
            if (string != null && !string.isEmpty()) {
                map.put(str, string);
            }
        }
        return map;
    }

    public boolean a(MLRemoteModel mLRemoteModel) {
        ModelConfigManagerStrategy modelConfigManagerStrategyA = this.b.a(mLRemoteModel);
        String configFileName = modelConfigManagerStrategyA.getConfigFileName(mLRemoteModel);
        SmartLog.d("MLSDK_MODEL_ModelConfigManager", "deleteConfig configFileName: com.huawei.hms.ml.model.download." + configFileName);
        Context context = this.a.get();
        if (context == null) {
            SmartLog.e("MLSDK_MODEL_ModelConfigManager", "deleteConfig context is invaild");
            return false;
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("com.huawei.hms.ml.model.download." + configFileName, 0).edit();
        for (String str : modelConfigManagerStrategyA.getDeleteConfigItem(mLRemoteModel)) {
            editorEdit.remove(str);
            SmartLog.d("MLSDK_MODEL_ModelConfigManager", "delete: " + str);
        }
        editorEdit.commit();
        return true;
    }

    public boolean a(MLRemoteModel mLRemoteModel, ModelResponse modelResponse, Map<String, String> map) {
        return this.b.a(mLRemoteModel).isModelVersionNeedUpdated(mLRemoteModel, modelResponse, map);
    }

    public boolean a(MLRemoteModel mLRemoteModel, Map<String, String> map) {
        return this.b.a(mLRemoteModel).isModelFileNeedUpdate(mLRemoteModel, map);
    }

    public String a(MLRemoteModel mLRemoteModel, ModelResponse modelResponse) {
        return this.b.a(mLRemoteModel).getOriginHash(mLRemoteModel, modelResponse);
    }
}
