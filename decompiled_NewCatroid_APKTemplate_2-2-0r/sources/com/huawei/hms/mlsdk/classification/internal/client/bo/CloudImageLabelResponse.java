package com.huawei.hms.mlsdk.classification.internal.client.bo;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class CloudImageLabelResponse {
    private List<LabelResult> labelResults = new ArrayList();
    private String retCode;
    private String retMsg;

    public List<LabelResult> getResponses() {
        return this.labelResults;
    }

    public String getRetCode() {
        return this.retCode;
    }

    public String getRetMsg() {
        return this.retMsg;
    }

    public void setResponses(List<LabelResult> list) {
        this.labelResults = list;
    }

    public void setRetCode(String str) {
        this.retCode = str;
    }

    public void setRetMsg(String str) {
        this.retMsg = str;
    }
}
