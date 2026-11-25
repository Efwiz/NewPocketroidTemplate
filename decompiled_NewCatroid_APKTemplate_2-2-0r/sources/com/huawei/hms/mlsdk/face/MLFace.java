package com.huawei.hms.mlsdk.face;

import android.graphics.PointF;
import android.graphics.Rect;
import com.huawei.hms.mlsdk.common.MLPosition;
import com.huawei.hms.mlsdk.internal.client.Objects;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MLFace {
    public static final float UNANALYZED_POSSIBILITY = -1.0f;
    private List<MLPosition> allPoints;
    private Rect border;
    private PointF coordinatePoint;
    private MLFaceEmotion emotions;
    private List<MLFaceShape> faceShapes;
    private MLFaceFeature features;
    private float height;
    private List<MLFaceKeyPoint> keyPoints;
    private float rotationAngleX;
    private float rotationAngleY;
    private float rotationAngleZ;
    private int trackingId;
    private float width;

    public MLFace(int i, PointF pointF, float f, float f2, float f3, float f4, float f5, List<MLFaceKeyPoint> list, List<MLFaceShape> list2, MLFaceEmotion mLFaceEmotion, MLFaceFeature mLFaceFeature, List<MLPosition> list3) {
        this.trackingId = i;
        this.coordinatePoint = pointF;
        this.width = f;
        this.height = f2;
        this.rotationAngleX = f3;
        this.rotationAngleY = f4;
        this.rotationAngleZ = f5;
        this.keyPoints = list;
        this.faceShapes = list2;
        this.emotions = mLFaceEmotion;
        this.features = mLFaceFeature;
        this.allPoints = list3;
        PointF pointF2 = this.coordinatePoint;
        float f6 = pointF2.x;
        float f7 = pointF2.y;
        this.border = new Rect((int) f6, (int) f7, (int) (f6 + f), (int) (f7 + f2));
    }

    public List<MLPosition> getAllPoints() {
        List<MLPosition> list = this.allPoints;
        return list == null ? new ArrayList() : list;
    }

    public Rect getBorder() {
        return this.border;
    }

    public PointF getCoordinatePoint() {
        return this.coordinatePoint;
    }

    public MLFaceEmotion getEmotions() {
        return this.emotions;
    }

    public MLFaceKeyPoint getFaceKeyPoint(int i) {
        List<MLFaceKeyPoint> list = this.keyPoints;
        if (list == null || list.size() <= 0) {
            return null;
        }
        for (int i2 = 0; i2 < this.keyPoints.size(); i2++) {
            MLFaceKeyPoint mLFaceKeyPoint = this.keyPoints.get(i2);
            if (mLFaceKeyPoint != null && mLFaceKeyPoint.getType() == i) {
                return mLFaceKeyPoint;
            }
        }
        return null;
    }

    public List<MLFaceKeyPoint> getFaceKeyPoints() {
        List<MLFaceKeyPoint> list = this.keyPoints;
        return list == null ? new ArrayList() : list;
    }

    public MLFaceShape getFaceShape(int i) {
        List<MLFaceShape> list = this.faceShapes;
        if (list == null || list.size() == 0) {
            return new MLFaceShape(i, new ArrayList());
        }
        int i2 = 0;
        if (i != 0) {
            while (i2 < this.faceShapes.size()) {
                if (this.faceShapes.get(i2) != null && this.faceShapes.get(i2).getFaceShapeType() == i) {
                    return this.faceShapes.get(i2);
                }
                i2++;
            }
            return new MLFaceShape(i, new ArrayList());
        }
        ArrayList arrayList = new ArrayList();
        while (i2 < this.faceShapes.size()) {
            if (this.faceShapes.get(i2) != null && this.faceShapes.get(i2).getPoints() != null) {
                arrayList.addAll(this.faceShapes.get(i2).getPoints());
            }
            i2++;
        }
        return new MLFaceShape(i, arrayList);
    }

    public List<MLFaceShape> getFaceShapeList() {
        List<MLFaceShape> list = this.faceShapes;
        return list == null ? new ArrayList() : list;
    }

    public MLFaceFeature getFeatures() {
        return this.features;
    }

    public float getHeight() {
        return this.height;
    }

    public float getRotationAngleX() {
        return this.rotationAngleX;
    }

    public float getRotationAngleY() {
        return this.rotationAngleY;
    }

    public float getRotationAngleZ() {
        return this.rotationAngleZ;
    }

    public int getTracingIdentity() {
        return this.trackingId;
    }

    public float getWidth() {
        return this.width;
    }

    public float opennessOfLeftEye() {
        return this.features.getLeftEyeOpenProbability();
    }

    public float opennessOfRightEye() {
        return this.features.getRightEyeOpenProbability();
    }

    public float possibilityOfSmiling() {
        return this.emotions.getSmilingProbability();
    }

    public String toString() {
        return Objects.toStringHelper(this).attribute("boundingBox", this.border).attribute("trackingId", Integer.valueOf(this.trackingId)).attribute("emotions", this.emotions).attribute("features", this.features).attribute("eulerX", Float.valueOf(this.rotationAngleX)).attribute("eulerY", Float.valueOf(this.rotationAngleY)).attribute("eulerZ", Float.valueOf(this.rotationAngleZ)).attribute("keyPoints", this.keyPoints).attribute("faceShapes", this.faceShapes).toString();
    }
}
