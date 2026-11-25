package com.huawei.hms.ml.common.face;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class FaceDetectorOptionsParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<FaceDetectorOptionsParcel> CREATOR = new Parcelable.Creator<FaceDetectorOptionsParcel>() { // from class: com.huawei.hms.ml.common.face.FaceDetectorOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceDetectorOptionsParcel createFromParcel(Parcel parcel) {
            return new FaceDetectorOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceDetectorOptionsParcel[] newArray(int i) {
            return new FaceDetectorOptionsParcel[i];
        }
    };
    public boolean bProminentFaceOnly;
    public Bundle bundle;
    public int classificationMode;
    public int contourMode;
    public boolean face3dEnabled;
    public int landmarkMode;
    public float minFaceSize;
    public int performanceMode;
    public boolean poseDisabled;
    public boolean trackingEnabled;
    public int trackingMode;

    public FaceDetectorOptionsParcel() {
    }

    public FaceDetectorOptionsParcel(int i, int i2, int i3, int i4, boolean z, boolean z2, float f, Bundle bundle) {
        this(i, i2, i3, i4, false, false, z, 0, z2, f, bundle);
    }

    public FaceDetectorOptionsParcel(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, int i5, boolean z4, float f, Bundle bundle) {
        this.landmarkMode = i;
        this.contourMode = i2;
        this.classificationMode = i3;
        this.performanceMode = i4;
        this.face3dEnabled = z;
        this.poseDisabled = z2;
        this.trackingEnabled = z3;
        this.trackingMode = i5;
        this.bProminentFaceOnly = z4;
        this.minFaceSize = f;
        this.bundle = bundle;
    }

    public FaceDetectorOptionsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.landmarkMode = parcelReader.readInt(2, 0);
        this.contourMode = parcelReader.readInt(3, 0);
        this.classificationMode = parcelReader.readInt(4, 0);
        this.performanceMode = parcelReader.readInt(5, 0);
        this.trackingEnabled = parcelReader.readBoolean(6, false);
        this.bProminentFaceOnly = parcelReader.readBoolean(7, false);
        this.minFaceSize = parcelReader.readFloat(8, 0.0f);
        this.bundle = parcelReader.readBundle(9, null);
        this.poseDisabled = parcelReader.readBoolean(10, true);
        this.trackingMode = parcelReader.readInt(11, 0);
        this.face3dEnabled = parcelReader.readBoolean(12, false);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeInt(2, this.landmarkMode);
        parcelWriter.writeInt(3, this.contourMode);
        parcelWriter.writeInt(4, this.classificationMode);
        parcelWriter.writeInt(5, this.performanceMode);
        parcelWriter.writeBoolean(6, this.trackingEnabled);
        parcelWriter.writeBoolean(7, this.bProminentFaceOnly);
        parcelWriter.writeFloat(8, this.minFaceSize);
        parcelWriter.writeBundle(9, this.bundle, true);
        parcelWriter.writeBoolean(10, this.poseDisabled);
        parcelWriter.writeInt(11, this.trackingMode);
        parcelWriter.writeBoolean(12, this.face3dEnabled);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
