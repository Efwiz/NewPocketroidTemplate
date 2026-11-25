package com.huawei.hms.mlsdk.document.internal.client.bo;

import com.huawei.hms.mlsdk.cloud.Coord;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class Section {
    private double confidence;
    private List<Coord> coords;
    private List<Line> lines;

    public Section(List<Coord> list, List<Line> list2, double d) {
        this.coords = list;
        this.lines = list2;
        this.confidence = d;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public List<Coord> getCoords() {
        return this.coords;
    }

    public List<Line> getLines() {
        return this.lines;
    }
}
