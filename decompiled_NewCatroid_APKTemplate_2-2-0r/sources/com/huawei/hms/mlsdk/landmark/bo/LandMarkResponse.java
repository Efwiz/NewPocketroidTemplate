package com.huawei.hms.mlsdk.landmark.bo;

import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class LandMarkResponse {
    private List<LandMarkResult> landMarkResults;
    private String retCode;
    private String retMsg;

    public List<LandMarkResult> getLandMarkResults() {
        return this.landMarkResults;
    }

    public String getRetCode() {
        return this.retCode;
    }

    public String getRetMsg() {
        return this.retMsg;
    }

    public void setLandMarkResults(List<LandMarkResult> list) {
        this.landMarkResults = list;
    }

    public void setRetCode(String str) {
        this.retCode = str;
    }

    public void setRetMsg(String str) {
        this.retMsg = str;
    }
}
