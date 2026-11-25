package com.huawei.hms.mlsdk.classification.internal.client.bo;

/* JADX INFO: loaded from: classes10.dex */
public class CloudImageLabelRequest {
    private String imgBase64;
    private int topNum;

    public CloudImageLabelRequest(String str, int i) {
        this.imgBase64 = str;
        this.topNum = i;
    }

    public String getImgBase64() {
        return this.imgBase64;
    }

    public int getTopNum() {
        return this.topNum;
    }

    public void setImgBase64(String str) {
        this.imgBase64 = str;
    }

    public void setTopNum(int i) {
        this.topNum = i;
    }
}
