package com.huawei.hms.mlsdk.landmark;

import android.graphics.Rect;
import com.huawei.hms.mlsdk.common.MLCoordinate;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MLRemoteLandmark {
    private Rect border;
    private String landmark;
    private String landmarkId;
    private List<MLCoordinate> positionInfos;
    private float possibility;

    public MLRemoteLandmark() {
        this.landmarkId = "";
    }

    public Rect getBorder() {
        return this.border;
    }

    public String getLandmark() {
        return this.landmark;
    }

    public String getLandmarkIdentity() {
        return this.landmarkId;
    }

    public List<MLCoordinate> getPositionInfos() {
        return this.positionInfos;
    }

    public float getPossibility() {
        return this.possibility;
    }

    public void setBorder(Rect rect) {
        this.border = rect;
    }

    public void setLandmark(String str) {
        this.landmark = str;
    }

    public void setLandmarkId(String str) {
        this.landmarkId = str;
    }

    public void setPositionInfos(List<MLCoordinate> list) {
        this.positionInfos = list;
    }

    public void setPossibility(float f) {
        this.possibility = f;
    }

    /* JADX WARN: Code duplicated, block: B:4:0x0012 A[PHI: r1
      0x0012: PHI (r1v3 float) = (r1v1 float), (r1v2 float) binds: [B:3:0x0010, B:6:0x001a] A[DONT_GENERATE, DONT_INLINE]] */
    private MLRemoteLandmark(String str, float f, Rect rect, String str2, List<MLCoordinate> list) {
        this.border = rect;
        this.landmark = str;
        this.landmarkId = str2;
        this.positionInfos = list;
        float f2 = 0.0f;
        if (Float.compare(f, 0.0f) >= 0) {
            f2 = 1.0f;
            f = Float.compare(f, 1.0f) > 0 ? f2 : f;
        }
        this.possibility = f;
    }
}
