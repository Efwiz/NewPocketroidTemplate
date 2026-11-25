package com.huawei.hms.mlsdk.document.internal.client.bo;

import com.huawei.hms.mlsdk.cloud.Coord;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class Region {
    private double confidence;
    private List<Coord> coords;
    private List<Section> sections;
    private String type;

    public Region() {
    }

    public double getConfidence() {
        return this.confidence;
    }

    public List<Coord> getCoords() {
        return this.coords;
    }

    public List<Section> getSections() {
        return this.sections;
    }

    public String getType() {
        return this.type;
    }

    public Region(List<Coord> list, List<Section> list2, String str, double d) {
        this.coords = list;
        this.sections = list2;
        this.type = str;
        this.confidence = d;
    }
}
