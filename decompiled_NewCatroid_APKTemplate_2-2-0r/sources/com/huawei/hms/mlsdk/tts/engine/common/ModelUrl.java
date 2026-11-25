package com.huawei.hms.mlsdk.tts.engine.common;

/* JADX INFO: loaded from: classes10.dex */
public class ModelUrl {
    private String contentSha256;
    private String downloadUrl;
    private String language;
    private String modelAccuracyLevel;
    private String modelName;
    private String modelSize;
    private String modelSource;
    private String modelVersion;
    private String signature;

    public ModelUrl(String str, String str2) {
        this.modelName = str;
        this.modelVersion = str2;
    }

    public String getContentSha256() {
        return this.contentSha256;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getModelAccuracyLevel() {
        return this.modelAccuracyLevel;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getModelSize() {
        return this.modelSize;
    }

    public String getModelSource() {
        return this.modelSource;
    }

    public String getModelVersion() {
        return this.modelVersion;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setContentSha256(String str) {
        this.contentSha256 = str;
    }

    public void setDownloadUrl(String str) {
        this.downloadUrl = str;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setModelAccuracyLevel(String str) {
        this.modelAccuracyLevel = str;
    }

    public void setModelName(String str) {
        this.modelName = str;
    }

    public void setModelSize(String str) {
        this.modelSize = str;
    }

    public void setModelSource(String str) {
        this.modelSource = str;
    }

    public void setModelVersion(String str) {
        this.modelVersion = str;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public String toString() {
        return com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a(com.huawei.hms.mlsdk.t.a.a("ModelUrl{modelName='"), this.modelName, '\'', ", language='"), this.language, '\'', ", modelAccuracyLevel='"), this.modelAccuracyLevel, '\'', ", modelVersion='"), this.modelVersion, '\'', ", downloadUrl='"), this.downloadUrl, '\'', ", signature='"), this.signature, '\'', ", contentSha256='"), this.contentSha256, '\'', ", modelSource='"), this.modelSource, '\'', ", modelSize='").append(this.modelSize).append('\'').append('}').toString();
    }
}
