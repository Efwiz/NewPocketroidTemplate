package com.huawei.hms.mlsdk.tts.download.data;

import com.huawei.hms.mlsdk.t.a;

/* JADX INFO: loaded from: classes10.dex */
public class ModelQueryRequest {
    private String language;
    private String modelAccuracyLevel;
    private int modelLevel;
    private String modelName;
    private String modelSource = "tts";
    private int status;

    public ModelQueryRequest(String str, String str2, String str3, int i, int i2) {
        this.modelName = str;
        this.language = str2;
        this.modelAccuracyLevel = str3;
        this.modelLevel = i;
        this.status = i2;
    }

    public String toString() {
        return a.a(a.a(a.a(a.a(a.a("ModelQueryRequest{modelName='"), this.modelName, '\'', ", language='"), this.language, '\'', ", modelSource='"), this.modelSource, '\'', ", modelAccuracyLevel='"), this.modelAccuracyLevel, '\'', ", modelLevel=").append(this.modelLevel).append(", status=").append(this.status).append('}').toString();
    }
}
