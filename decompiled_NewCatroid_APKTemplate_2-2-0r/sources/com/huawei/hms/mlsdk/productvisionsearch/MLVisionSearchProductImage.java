package com.huawei.hms.mlsdk.productvisionsearch;

/* JADX INFO: loaded from: classes10.dex */
public class MLVisionSearchProductImage {
    private String a;
    private String b;
    private float c;
    private String d;

    public MLVisionSearchProductImage() {
    }

    public String getImageId() {
        return this.b;
    }

    public String getInnerUrl() {
        return this.d;
    }

    public float getPossibility() {
        return this.c;
    }

    public String getProductId() {
        return this.a;
    }

    public void setImageId(String str) {
        this.b = str;
    }

    public void setInnerUrl(String str) {
        this.d = str;
    }

    public void setPossibility(float f) {
        this.c = f;
    }

    public void setProductId(String str) {
        this.a = str;
    }

    public MLVisionSearchProductImage(String str, String str2, float f) {
        this.a = str;
        this.b = str2;
        this.c = f;
    }
}
