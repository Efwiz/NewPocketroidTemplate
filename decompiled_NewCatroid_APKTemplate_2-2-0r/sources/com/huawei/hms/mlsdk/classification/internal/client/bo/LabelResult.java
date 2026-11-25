package com.huawei.hms.mlsdk.classification.internal.client.bo;

/* JADX INFO: loaded from: classes10.dex */
public class LabelResult {
    private String description;
    private double relativity;
    private double score;

    public String getDescription() {
        return this.description;
    }

    public double getRelativity() {
        return this.relativity;
    }

    public double getScore() {
        return this.score;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setRelativity(double d) {
        this.relativity = d;
    }

    public void setScore(double d) {
        this.score = d;
    }
}
