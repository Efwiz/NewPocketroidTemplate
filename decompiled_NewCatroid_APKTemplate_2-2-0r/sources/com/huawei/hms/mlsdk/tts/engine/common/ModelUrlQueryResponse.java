package com.huawei.hms.mlsdk.tts.engine.common;

import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ModelUrlQueryResponse {
    private List<ModelUrl> data;
    private String retCode;
    private String retMsg;

    public List<ModelUrl> getData() {
        return this.data;
    }

    public String getRetCode() {
        return this.retCode;
    }

    public String getRetMsg() {
        return this.retMsg;
    }

    public void setData(List<ModelUrl> list) {
        this.data = list;
    }
}
