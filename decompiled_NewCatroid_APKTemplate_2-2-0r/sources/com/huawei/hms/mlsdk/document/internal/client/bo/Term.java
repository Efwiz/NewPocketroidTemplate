package com.huawei.hms.mlsdk.document.internal.client.bo;

import com.huawei.hms.mlsdk.cloud.Coord;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class Term {
    private List<Char> chars;
    private double confidence;
    private List<Coord> coords;
    private List<Language> languages;

    public Term(List<Language> list, List<Coord> list2, List<Char> list3, double d) {
        this.languages = list;
        this.coords = list2;
        this.chars = list3;
        this.confidence = d;
    }

    public List<Char> getChars() {
        return this.chars;
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
}
