package com.huawei.hms.mlsdk.text.internal.client.bo;

import com.huawei.hms.mlsdk.cloud.Coord;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class RemoteText {
    private double confidence;
    private List<Coord> coords;
    private String language;
    private String text;

    public double getConfidence() {
        return this.confidence;
    }

    public List<Coord> getCoords() {
        return this.coords;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getText() {
        return this.text;
    }

    public void setConfidence(double d) {
        this.confidence = d;
    }

    public void setCoords(List<Coord> list) {
        this.coords = list;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setText(String str) {
        this.text = str;
    }
}
