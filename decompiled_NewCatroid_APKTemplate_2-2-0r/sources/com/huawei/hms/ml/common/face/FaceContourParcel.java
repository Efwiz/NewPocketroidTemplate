package com.huawei.hms.ml.common.face;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class FaceContourParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<FaceContourParcel> CREATOR = new Parcelable.Creator<FaceContourParcel>() { // from class: com.huawei.hms.ml.common.face.FaceContourParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceContourParcel createFromParcel(Parcel parcel) {
            return new FaceContourParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceContourParcel[] newArray(int i) {
            return new FaceContourParcel[i];
        }
    };
    private int faceContourType;
    private List<MLVisionPointParcel> points;
    private PointF[] positions;

    public int getFaceContourType() {
        return this.faceContourType;
    }

    public void setFaceContourType(int i) {
        this.faceContourType = i;
    }

    public PointF[] getPositions() {
        PointF[] pointFArr = this.positions;
        if (pointFArr != null) {
            return (PointF[]) Arrays.copyOf(pointFArr, pointFArr.length);
        }
        return new PointF[0];
    }

    public void setPositions(PointF[] pointFArr) {
        if (pointFArr != null) {
            this.positions = (PointF[]) Arrays.copyOf(pointFArr, pointFArr.length);
        } else {
            this.positions = null;
        }
    }

    public List<MLVisionPointParcel> getPoints() {
        return this.points;
    }

    public void setPoints(List<MLVisionPointParcel> list) {
        this.points = list;
    }

    public FaceContourParcel() {
    }

    public FaceContourParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.faceContourType = parcelReader.readInt(2, 0);
        this.positions = (PointF[]) parcelReader.createTypedArray(3, PointF.CREATOR, null);
        this.points = parcelReader.createTypedList(4, MLVisionPointParcel.CREATOR, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeInt(2, this.faceContourType);
        parcelWriter.writeTypedArray(3, this.positions, i, false);
        parcelWriter.writeTypedList(4, this.points, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
