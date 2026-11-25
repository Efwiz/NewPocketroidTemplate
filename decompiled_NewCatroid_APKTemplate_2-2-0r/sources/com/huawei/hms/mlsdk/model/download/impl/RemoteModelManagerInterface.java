package com.huawei.hms.mlsdk.model.download.impl;

import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public interface RemoteModelManagerInterface<T extends MLRemoteModel> {
    Set<T> getDownloadedModels(Map<String, String> map);
}
