package com.huawei.hms.mlsdk.objects;

import android.graphics.Rect;

/* JADX INFO: loaded from: classes10.dex */
public class MLObject {
    public static final int TYPE_FACE = 6;
    public static final int TYPE_FOOD = 2;
    public static final int TYPE_FURNITURE = 3;
    public static final int TYPE_GOODS = 1;
    public static final int TYPE_OTHER = 0;
    public static final int TYPE_PLACE = 5;
    public static final int TYPE_PLANT = 4;
    private Rect border;
    private Float possibility;
    private Integer tracingIdentity;
    private int type;

    public MLObject(Rect rect, Integer num, Float f, int i) {
        this.border = new Rect(rect);
        this.tracingIdentity = num;
        this.possibility = f;
        this.type = i;
    }

    public Rect getBorder() {
        return this.border;
    }

    public Integer getTracingIdentity() {
        return this.tracingIdentity;
    }

    public int getTypeIdentity() {
        return this.type;
    }

    public Float getTypePossibility() {
        return this.possibility;
    }

    public void setBorder(Rect rect) {
        this.border = rect;
    }

    public void setTracingIdentity(Integer num) {
        this.tracingIdentity = num;
    }

    public void setTypeIdentity(int i) {
        this.type = i;
    }

    public void setTypePossibility(Float f) {
        this.possibility = f;
    }
}
