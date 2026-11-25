package com.huawei.hms.mlsdk.document.internal.client.bo;

import com.huawei.hms.mlsdk.cloud.Coord;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class Char {
    private String breakType;
    private double confidence;
    private List<Coord> coords;
    private List<Language> languages;
    private String value;

    public Char() {
        this.languages = new ArrayList();
        this.coords = new ArrayList();
    }

    public String getBreakType() {
        return this.breakType;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public List<Coord> getCoords() {
        return this.coords;
    }

    public List<Language> getLanguages() {
        return this.languages;
    }

    public String getValue() {
        String str = this.value;
        return str == null ? "" : str;
    }

    public Char(List<Language> list, List<Coord> list2, String str, double d, String str2) {
        this.languages = new ArrayList();
        new ArrayList();
        this.languages = list;
        this.coords = list2;
        this.value = str;
        this.confidence = d;
        this.breakType = str2;
    }
}
