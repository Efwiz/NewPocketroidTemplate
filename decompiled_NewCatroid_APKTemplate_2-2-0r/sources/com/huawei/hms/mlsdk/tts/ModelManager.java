package com.huawei.hms.mlsdk.tts;

import com.huawei.hms.mlsdk.model.download.impl.RemoteModelManagerInterface;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public class ModelManager implements RemoteModelManagerInterface<MLTtsLocalModel> {
    @Override // com.huawei.hms.mlsdk.model.download.impl.RemoteModelManagerInterface
    public Set<MLTtsLocalModel> getDownloadedModels(Map<String, String> map) {
        HashSet hashSet = new HashSet();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            if (!key.contains("-hash") && !key.contains("-model-version")) {
                hashSet.add(new MLTtsLocalModel.Factory(key).create());
            }
        }
        return hashSet;
    }
}
