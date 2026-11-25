package com.huawei.hms.mlsdk.text.internal.client.bo;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class RemoteOcrTextResponse {
    private String requestId;
    private String retCode;
    private String retMsg;
    private List<RemoteText> texts = new ArrayList();

    public String getRequestId() {
        return this.requestId;
    }

    public List<RemoteText> getResponses() {
        return this.texts;
    }

    public String getRetCode() {
        return this.retCode;
    }

    public String getRetMsg() {
        return this.retMsg;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public void setResponses(List<RemoteText> list) {
        this.texts = list;
    }

    public void setRetCode(String str) {
        this.retCode = str;
    }

    public void setRetMsg(String str) {
        this.retMsg = str;
    }
}
