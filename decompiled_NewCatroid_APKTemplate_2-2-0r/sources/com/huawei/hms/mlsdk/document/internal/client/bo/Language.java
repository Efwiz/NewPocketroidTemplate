package com.huawei.hms.mlsdk.document.internal.client.bo;

/* JADX INFO: loaded from: classes10.dex */
public class Language {
    private double confidence;
    private String language;

    public Language() {
    }

    public final double getConfidence() {
        return this.confidence;
    }

    public final String getLanguage() {
        return this.language;
    }

    public Language(String str, double d) {
        this.language = str;
        this.confidence = d;
    }
}
