package com.huawei.hms.mlsdk.cloud;

import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class RemoteImageRequest {
    private String coordType;
    private String imgBase64;
    private String[] languages;

    public RemoteImageRequest(String str, String str2, String[] strArr) {
        this.imgBase64 = str;
        this.coordType = str2;
        this.languages = strArr == null ? null : (String[]) Arrays.copyOf(strArr, strArr.length);
    }

    public String getCoordType() {
        return this.coordType;
    }

    public String getImgBase64() {
        return this.imgBase64;
    }

    public String[] getLanguages() {
        String[] strArr = this.languages;
        return strArr != null ? (String[]) Arrays.copyOf(strArr, strArr.length) : new String[0];
    }

    public void setCoordType(String str) {
        this.coordType = str;
    }

    public void setImgBase64(String str) {
        this.imgBase64 = str;
    }

    public void setLanguages(String[] strArr) {
        if (strArr != null) {
            this.languages = (String[]) Arrays.copyOf(strArr, strArr.length);
        } else {
            this.languages = null;
        }
    }
}
