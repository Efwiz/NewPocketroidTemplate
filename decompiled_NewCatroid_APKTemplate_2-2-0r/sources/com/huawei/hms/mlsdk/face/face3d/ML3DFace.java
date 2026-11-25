package com.huawei.hms.mlsdk.face.face3d;

import android.graphics.PointF;
import android.graphics.Rect;
import com.huawei.hms.mlsdk.common.MLPosition;
import com.huawei.hms.mlsdk.internal.client.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ML3DFace {
    private static final int[] FIVE_KEY_POINTS_INDEX = {36, 39, 42, 45, 30, 48, 54};
    public static final int LANDMARK_FIVE = 0;
    private List<MLPosition> all3dvertexs;
    private Rect border;
    private PointF coordinatePoint;
    private float face3DEulerX;
    private float face3DEulerY;
    private float face3DEulerZ;
    private List<MLPosition> face3DKeypoints;
    private float[] face3DParams;
    private float height;
    private String mBaseObj;
    private int trackingId;
    private float width;

    public ML3DFace(int i, PointF pointF, float f, float f2, float f3, float f4, float f5, List<MLPosition> list, List<MLPosition> list2, float[] fArr, String str) {
        this.trackingId = i;
        this.coordinatePoint = pointF;
        this.width = f;
        this.height = f2;
        this.face3DEulerX = f3;
        this.face3DEulerY = f4;
        this.face3DEulerZ = f5;
        this.all3dvertexs = list;
        this.face3DKeypoints = list2;
        this.face3DParams = fArr;
        this.mBaseObj = str;
        PointF pointF2 = this.coordinatePoint;
        float f6 = pointF2.x;
        float f7 = pointF2.y;
        this.border = new Rect((int) f6, (int) f7, (int) (f6 + f), (int) (f7 + f2));
    }

    private List<MLPosition> getFiveKeypoint() {
        if (this.face3DKeypoints.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        List<MLPosition> list = this.face3DKeypoints;
        int[] iArr = FIVE_KEY_POINTS_INDEX;
        MLPosition mLPosition = list.get(iArr[0]);
        MLPosition mLPosition2 = this.face3DKeypoints.get(iArr[1]);
        arrayList.add(new MLPosition(Float.valueOf((mLPosition2.getX().floatValue() + mLPosition.getX().floatValue()) / 2.0f), Float.valueOf((mLPosition2.getY().floatValue() + mLPosition.getY().floatValue()) / 2.0f), Float.valueOf((mLPosition2.getZ().floatValue() + mLPosition.getZ().floatValue()) / 2.0f)));
        MLPosition mLPosition3 = this.face3DKeypoints.get(iArr[2]);
        MLPosition mLPosition4 = this.face3DKeypoints.get(iArr[3]);
        arrayList.add(new MLPosition(Float.valueOf((mLPosition4.getX().floatValue() + mLPosition3.getX().floatValue()) / 2.0f), Float.valueOf((mLPosition4.getY().floatValue() + mLPosition3.getY().floatValue()) / 2.0f), Float.valueOf((mLPosition4.getZ().floatValue() + mLPosition3.getZ().floatValue()) / 2.0f)));
        arrayList.add(this.face3DKeypoints.get(iArr[4]));
        arrayList.add(this.face3DKeypoints.get(iArr[5]));
        arrayList.add(this.face3DKeypoints.get(iArr[6]));
        return arrayList;
    }

    public List<MLPosition> get3DAllVertexs() {
        List<MLPosition> list = this.all3dvertexs;
        return list == null ? new ArrayList() : list;
    }

    public float get3DFaceEulerX() {
        return this.face3DEulerX;
    }

    public float get3DFaceEulerY() {
        return this.face3DEulerY;
    }

    public float get3DFaceEulerZ() {
        return this.face3DEulerZ;
    }

    public List<MLPosition> get3DKeyPoints(int i) {
        return i != 0 ? Collections.emptyList() : getFiveKeypoint();
    }

    public void get3DProjectionMatrix(float[] fArr, float f, float f2) {
        double d = 1.0d / ((double) (f - f2));
        float[] fArr2 = this.face3DParams;
        fArr[0] = fArr2[16];
        fArr[1] = fArr2[17];
        fArr[2] = fArr2[18];
        fArr[3] = 0.0f;
        fArr[4] = fArr2[19];
        fArr[5] = fArr2[20];
        fArr[6] = fArr2[21];
        fArr[7] = 0.0f;
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        fArr[10] = (float) (((double) (f2 + f)) * d);
        fArr[11] = (float) (((double) f2) * 2.0d * ((double) f) * d);
        fArr[12] = 0.0f;
        fArr[13] = 0.0f;
        fArr[14] = -1.0f;
        fArr[15] = 0.0f;
    }

    public void get3DViewMatrix(float[] fArr) {
        float[] fArr2 = this.face3DParams;
        fArr[0] = fArr2[25];
        fArr[1] = fArr2[26];
        fArr[2] = fArr2[27];
        fArr[3] = fArr2[34];
        fArr[4] = fArr2[28];
        fArr[5] = fArr2[29];
        fArr[6] = fArr2[30];
        fArr[7] = fArr2[35];
        fArr[8] = fArr2[31];
        fArr[9] = fArr2[32];
        fArr[10] = fArr2[33];
        fArr[11] = fArr2[36];
        fArr[12] = 0.0f;
        fArr[13] = 0.0f;
        fArr[14] = 0.0f;
        fArr[15] = 1.0f;
    }

    public String toString() {
        return Objects.toStringHelper(this).attribute("boundingBox", this.border).attribute("trackingId", Integer.valueOf(this.trackingId)).attribute("face3DEulerX", Float.valueOf(this.face3DEulerX)).attribute("face3DEulerY", Float.valueOf(this.face3DEulerY)).attribute("face3DEulerZ", Float.valueOf(this.face3DEulerZ)).attribute("all3dvertexs", this.all3dvertexs).attribute("face3DKeypoints", this.face3DKeypoints).toString();
    }
}
