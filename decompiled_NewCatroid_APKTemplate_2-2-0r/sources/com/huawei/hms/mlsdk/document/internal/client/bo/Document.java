package com.huawei.hms.mlsdk.document.internal.client.bo;

import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class Document {
    private String doc;
    private int height;
    private List<Language> languages;
    private List<Region> regions;
    private int width;

    public Document(List<Language> list, List<Region> list2, int i, int i2, String str) {
        this.languages = list;
        this.regions = list2;
        this.width = i;
        this.height = i2;
        this.doc = str;
    }

    public String getDoc() {
        return this.doc;
    }

    public int getHeight() {
        return this.height;
    }

    public List<Language> getLanguages() {
        return this.languages;
    }

    public List<Region> getRegions() {
        return this.regions;
    }

    public int getWidth() {
        return this.width;
    }
}
