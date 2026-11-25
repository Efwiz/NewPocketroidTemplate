package com.huawei.hms.mlsdk.document.internal.client.bo;

import com.huawei.hms.mlsdk.cloud.Coord;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class Line {
    private double confidence;
    private List<Coord> coords;
    private List<Term> terms;

    public Line(List<Coord> list, List<Term> list2, double d) {
        this.coords = list;
        this.terms = list2;
        this.confidence = d;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public List<Coord> getCoords() {
        return this.coords;
    }

    public List<Term> getTerms() {
        return this.terms;
    }
}
