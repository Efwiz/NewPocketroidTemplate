package com.huawei.hms.mlsdk.document.internal.client.bo;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class RemoteOcrDocumentResponse {
    private List<Document> docs = new ArrayList();
    private String requestId;
    private String retCode;
    private String retMsg;

    public List<Document> getDocs() {
        return this.docs;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getRetCode() {
        return this.retCode;
    }

    public String getRetMsg() {
        return this.retMsg;
    }

    public void setDocs(List<Document> list) {
        this.docs = list;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public void setRetCode(String str) {
        this.retCode = str;
    }

    public void setRetMsg(String str) {
        this.retMsg = str;
    }
}
