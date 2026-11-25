package com.huawei.hms.mlsdk.face.internal.client;

import android.graphics.PointF;
import com.huawei.hms.ml.common.face.FaceContourParcel;
import com.huawei.hms.ml.common.face.FaceDetectorOptionsParcel;
import com.huawei.hms.ml.common.face.FaceParcel;
import com.huawei.hms.ml.common.face.LandmarkParcel;
import com.huawei.hms.ml.common.face.MLVisionPointParcel;
import com.huawei.hms.ml.common.face.Point3dF;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLPosition;
import com.huawei.hms.mlsdk.face.MLFace;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.MLFaceEmotion;
import com.huawei.hms.mlsdk.face.MLFaceFeature;
import com.huawei.hms.mlsdk.face.MLFaceKeyPoint;
import com.huawei.hms.mlsdk.face.MLFaceShape;
import com.huawei.hms.mlsdk.face.face3d.ML3DFace;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.mlvision.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class FaceHelper {
    public static FaceDetectorOptionsParcel create(MLFaceAnalyzerSetting mLFaceAnalyzerSetting) {
        FaceDetectorOptionsParcel faceDetectorOptionsParcel = new FaceDetectorOptionsParcel();
        if (mLFaceAnalyzerSetting != null) {
            faceDetectorOptionsParcel.landmarkMode = mLFaceAnalyzerSetting.getKeyPointType();
            faceDetectorOptionsParcel.contourMode = mLFaceAnalyzerSetting.getShapeType();
            faceDetectorOptionsParcel.classificationMode = mLFaceAnalyzerSetting.getFeatureType();
            faceDetectorOptionsParcel.performanceMode = mLFaceAnalyzerSetting.getPerformanceType();
            faceDetectorOptionsParcel.face3dEnabled = false;
            faceDetectorOptionsParcel.poseDisabled = mLFaceAnalyzerSetting.isPoseDisabled();
            faceDetectorOptionsParcel.trackingEnabled = mLFaceAnalyzerSetting.isTracingAllowed();
            faceDetectorOptionsParcel.trackingMode = mLFaceAnalyzerSetting.getTracingMode();
            faceDetectorOptionsParcel.bProminentFaceOnly = mLFaceAnalyzerSetting.isMaxSizeFaceOnly();
            faceDetectorOptionsParcel.minFaceSize = mLFaceAnalyzerSetting.getMinFaceProportion();
        }
        return faceDetectorOptionsParcel;
    }

    public static ML3DFace toML3DFace(FaceParcel faceParcel, String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        float[] fArr = new float[faceParcel.face3DParams.size() * 2];
        List<Point3dF> list = faceParcel.face3DVertices;
        if (list != null) {
            for (Point3dF point3dF : list) {
                arrayList.add(new MLPosition(Float.valueOf(point3dF.x), Float.valueOf(point3dF.y), Float.valueOf(point3dF.z)));
            }
            int i = 0;
            for (PointF pointF : faceParcel.face3DParams) {
                int i2 = i + 1;
                fArr[i] = pointF.x;
                i += 2;
                fArr[i2] = pointF.y;
            }
            for (Point3dF point3dF2 : faceParcel.face3DKeypoints) {
                arrayList2.add(new MLPosition(Float.valueOf(point3dF2.x), Float.valueOf(point3dF2.y), Float.valueOf(point3dF2.z)));
            }
        }
        return new ML3DFace(faceParcel.mId, faceParcel.position, faceParcel.width, faceParcel.height, faceParcel.face3DEulerAngleX, faceParcel.face3DEulerAngleY, faceParcel.face3DEulerAngleZ, arrayList, arrayList2, fArr, str);
    }

    public static MLFace toMLFace(FaceParcel faceParcel) {
        ArrayList arrayList = new ArrayList();
        for (LandmarkParcel landmarkParcel : faceParcel.landmarks) {
            if (landmarkParcel != null) {
                arrayList.add(toMLFaceKeyPoint(landmarkParcel));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        try {
            Iterator<FaceContourParcel> it = faceParcel.contours.iterator();
            while (it.hasNext()) {
                arrayList2.add(toMLFaceShape(it.next()));
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            SmartLog.e("FaceHelper", a.a("failed to translate Parcel to MLFaceShape:").append(e2.getMessage()).toString());
        }
        ArrayList arrayList3 = new ArrayList();
        List<PointF> list = faceParcel.allPoints;
        if (list != null) {
            for (PointF pointF : list) {
                arrayList3.add(new MLPosition(Float.valueOf(pointF.x), Float.valueOf(pointF.y)));
            }
        }
        return new MLFace(faceParcel.mId, faceParcel.position, faceParcel.width, faceParcel.height, faceParcel.headEulerAngleX, faceParcel.headEulerAngleY, faceParcel.headEulerAngleZ, arrayList, arrayList2, new MLFaceEmotion(faceParcel.smilingProbability, faceParcel.neutralProbability, faceParcel.angryProbability, faceParcel.disgustProbability, faceParcel.fearProbability, faceParcel.sadProbability, faceParcel.surpriseProbability), new MLFaceFeature(faceParcel.leftEyeOpenProbability, faceParcel.rightEyeOpenProbability, faceParcel.sunGlassProbability, faceParcel.sexProbability, faceParcel.hatProbability, faceParcel.moustacheProbability, faceParcel.age), arrayList3);
    }

    static MLFaceKeyPoint toMLFaceKeyPoint(LandmarkParcel landmarkParcel) {
        return new MLFaceKeyPoint(toMLPoint(landmarkParcel.point), landmarkParcel.landmarkType);
    }

    static MLFaceShape toMLFaceShape(FaceContourParcel faceContourParcel) {
        ArrayList arrayList = new ArrayList();
        Iterator<MLVisionPointParcel> it = faceContourParcel.getPoints().iterator();
        while (it.hasNext()) {
            arrayList.add(toMLPoint(it.next()));
        }
        return new MLFaceShape(faceContourParcel.getFaceContourType(), arrayList);
    }

    static MLPosition toMLPoint(MLVisionPointParcel mLVisionPointParcel) {
        return new MLPosition(mLVisionPointParcel.getX(), mLVisionPointParcel.getY(), mLVisionPointParcel.getY());
    }

    public static FaceDetectorOptionsParcel create(ML3DFaceAnalyzerSetting mL3DFaceAnalyzerSetting) {
        FaceDetectorOptionsParcel faceDetectorOptionsParcel = new FaceDetectorOptionsParcel();
        if (mL3DFaceAnalyzerSetting != null) {
            faceDetectorOptionsParcel.landmarkMode = 0;
            faceDetectorOptionsParcel.contourMode = 3;
            faceDetectorOptionsParcel.classificationMode = 1;
            faceDetectorOptionsParcel.performanceMode = mL3DFaceAnalyzerSetting.getPerformanceType();
            faceDetectorOptionsParcel.face3dEnabled = true;
            faceDetectorOptionsParcel.poseDisabled = true;
            faceDetectorOptionsParcel.trackingEnabled = mL3DFaceAnalyzerSetting.isTracingAllowed();
            faceDetectorOptionsParcel.trackingMode = 1;
        }
        return faceDetectorOptionsParcel;
    }
}
