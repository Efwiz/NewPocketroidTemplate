package com.huawei.hms.ml.common.face;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class FaceParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<FaceParcel> CREATOR = new Parcelable.Creator<FaceParcel>() { // from class: com.huawei.hms.ml.common.face.FaceParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceParcel createFromParcel(Parcel parcel) {
            return new FaceParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceParcel[] newArray(int i) {
            return new FaceParcel[i];
        }
    };
    public int age;
    public List<PointF> allPoints;
    public float angryProbability;
    public List<FaceContourParcel> contours;
    public float disgustProbability;
    public float face3DEulerAngleX;
    public float face3DEulerAngleY;
    public float face3DEulerAngleZ;
    public List<Point3dF> face3DKeypoints;
    public List<PointF> face3DParams;
    public List<Point3dF> face3DVertices;
    public float fearProbability;
    public float hatProbability;
    public float headEulerAngleX;
    public float headEulerAngleY;
    public float headEulerAngleZ;
    public float height;
    public List<LandmarkParcel> landmarks;
    public float leftEyeOpenProbability;
    public int mId;
    public float moustacheProbability;
    public float neutralProbability;
    public PointF position;
    public float rightEyeOpenProbability;
    public float sadProbability;
    public float sexProbability;
    public float smilingProbability;
    public float sunGlassProbability;
    public float surpriseProbability;
    public int trackingId;
    public float width;

    public FaceParcel() {
        this.trackingId = -1;
        this.leftEyeOpenProbability = -1.0f;
        this.rightEyeOpenProbability = -1.0f;
        this.smilingProbability = -1.0f;
    }

    public FaceParcel(Parcel parcel) {
        this.trackingId = -1;
        this.leftEyeOpenProbability = -1.0f;
        this.rightEyeOpenProbability = -1.0f;
        this.smilingProbability = -1.0f;
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.trackingId = parcelReader.readInt(2, -1);
        this.mId = parcelReader.readInt(3, 0);
        this.width = parcelReader.readFloat(4, 0.0f);
        this.height = parcelReader.readFloat(5, 0.0f);
        this.headEulerAngleX = parcelReader.readFloat(6, 0.0f);
        this.headEulerAngleY = parcelReader.readFloat(7, 0.0f);
        this.headEulerAngleZ = parcelReader.readFloat(8, 0.0f);
        this.leftEyeOpenProbability = parcelReader.readFloat(9, -1.0f);
        this.rightEyeOpenProbability = parcelReader.readFloat(10, -1.0f);
        this.smilingProbability = parcelReader.readFloat(11, -1.0f);
        this.neutralProbability = parcelReader.readFloat(12, 0.0f);
        this.angryProbability = parcelReader.readFloat(13, 0.0f);
        this.disgustProbability = parcelReader.readFloat(14, 0.0f);
        this.fearProbability = parcelReader.readFloat(15, 0.0f);
        this.sadProbability = parcelReader.readFloat(16, 0.0f);
        this.surpriseProbability = parcelReader.readFloat(17, 0.0f);
        this.sunGlassProbability = parcelReader.readFloat(18, 0.0f);
        this.hatProbability = parcelReader.readFloat(19, 0.0f);
        this.age = parcelReader.readInt(20, 0);
        this.position = (PointF) parcelReader.readParcelable(21, PointF.CREATOR, null);
        this.landmarks = parcelReader.createTypedList(22, LandmarkParcel.CREATOR, null);
        this.contours = parcelReader.createTypedList(23, FaceContourParcel.CREATOR, null);
        this.moustacheProbability = parcelReader.readFloat(24, 0.0f);
        this.sexProbability = parcelReader.readFloat(25, 0.0f);
        this.allPoints = parcelReader.createTypedList(26, PointF.CREATOR, null);
        this.face3DEulerAngleX = parcelReader.readFloat(27, 0.0f);
        this.face3DEulerAngleY = parcelReader.readFloat(28, 0.0f);
        this.face3DEulerAngleZ = parcelReader.readFloat(29, 0.0f);
        this.face3DVertices = parcelReader.createTypedList(30, Point3dF.CREATOR, null);
        this.face3DKeypoints = parcelReader.createTypedList(31, Point3dF.CREATOR, null);
        this.face3DParams = parcelReader.createTypedList(32, PointF.CREATOR, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeInt(2, this.trackingId);
        parcelWriter.writeInt(3, this.mId);
        parcelWriter.writeFloat(4, this.width);
        parcelWriter.writeFloat(5, this.height);
        parcelWriter.writeFloat(6, this.headEulerAngleX);
        parcelWriter.writeFloat(7, this.headEulerAngleY);
        parcelWriter.writeFloat(8, this.headEulerAngleZ);
        parcelWriter.writeFloat(9, this.leftEyeOpenProbability);
        parcelWriter.writeFloat(10, this.rightEyeOpenProbability);
        parcelWriter.writeFloat(11, this.smilingProbability);
        parcelWriter.writeFloat(12, this.neutralProbability);
        parcelWriter.writeFloat(13, this.angryProbability);
        parcelWriter.writeFloat(14, this.disgustProbability);
        parcelWriter.writeFloat(15, this.fearProbability);
        parcelWriter.writeFloat(16, this.sadProbability);
        parcelWriter.writeFloat(17, this.surpriseProbability);
        parcelWriter.writeFloat(18, this.sunGlassProbability);
        parcelWriter.writeFloat(19, this.hatProbability);
        parcelWriter.writeInt(20, this.age);
        parcelWriter.writeParcelable(21, this.position, i, false);
        parcelWriter.writeTypedList(22, this.landmarks, false);
        parcelWriter.writeTypedList(23, this.contours, false);
        parcelWriter.writeFloat(24, this.moustacheProbability);
        parcelWriter.writeFloat(25, this.sexProbability);
        parcelWriter.writeTypedList(26, this.allPoints, false);
        parcelWriter.writeFloat(27, this.face3DEulerAngleX);
        parcelWriter.writeFloat(28, this.face3DEulerAngleY);
        parcelWriter.writeFloat(29, this.face3DEulerAngleZ);
        parcelWriter.writeTypedList(30, this.face3DVertices, false);
        parcelWriter.writeTypedList(31, this.face3DKeypoints, false);
        parcelWriter.writeTypedList(32, this.face3DParams, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
