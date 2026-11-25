package com.huawei.hms.mlsdk.common.lens;

import android.hardware.Camera;

/* JADX INFO: loaded from: classes10.dex */
public class LensResponse {
    public Camera lens;
    public int lensId;
    public int quadrant;

    public Camera getLens() {
        return this.lens;
    }

    public int getLensId() {
        return this.lensId;
    }

    public int getQuadrant() {
        return this.quadrant;
    }

    public void setLens(Camera camera) {
        this.lens = camera;
    }

    public void setLensId(int i) {
        this.lensId = i;
    }

    public void setQuadrant(int i) {
        this.quadrant = i;
    }
}
