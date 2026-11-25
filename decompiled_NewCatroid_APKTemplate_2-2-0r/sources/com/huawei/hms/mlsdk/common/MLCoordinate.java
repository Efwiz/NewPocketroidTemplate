package com.huawei.hms.mlsdk.common;

/* JADX INFO: loaded from: classes10.dex */
public class MLCoordinate {
    public final double a;
    public final double b;

    public MLCoordinate(double d, double d2) {
        this.a = d;
        this.b = d2;
    }

    public double getLat() {
        return this.a;
    }

    public double getLng() {
        return this.b;
    }
}
