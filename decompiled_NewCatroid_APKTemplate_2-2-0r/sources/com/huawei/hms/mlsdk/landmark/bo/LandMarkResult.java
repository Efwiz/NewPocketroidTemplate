package com.huawei.hms.mlsdk.landmark.bo;

import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class LandMarkResult {
    private String description;
    private List<Location> locations;
    private double relativity;
    private double score;
    private List<Vertex> vertices;

    public String getDescription() {
        return this.description;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public double getRelativity() {
        return this.relativity;
    }

    public double getScore() {
        return this.score;
    }

    public List<Vertex> getVertices() {
        return this.vertices;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setLocations(List<Location> list) {
        this.locations = list;
    }

    public void setRelativity(double d) {
        this.relativity = d;
    }

    public void setScore(double d) {
        this.score = d;
    }

    public void setVertices(List<Vertex> list) {
        this.vertices = list;
    }
}
