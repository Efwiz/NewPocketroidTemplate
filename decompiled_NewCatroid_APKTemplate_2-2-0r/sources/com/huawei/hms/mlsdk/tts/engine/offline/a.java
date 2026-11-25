package com.huawei.hms.mlsdk.tts.engine.offline;

import com.huawei.hms.mlsdk.tts.engine.common.ModelUrl;

/* JADX INFO: compiled from: ModelSpeakerInfo.java */
/* JADX INFO: loaded from: classes10.dex */
public class a {
    private String a;
    private ModelUrl b;

    public String a() {
        return this.a;
    }

    public ModelUrl b() {
        return this.b;
    }

    public String toString() {
        return com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a("SpeakerModelInfo{modelName='"), this.a, '\'', ", modelUrl=").append(this.b).append('}').toString();
    }

    public void a(String str) {
        this.a = str;
    }

    public void a(ModelUrl modelUrl) {
        this.b = modelUrl;
    }
}
