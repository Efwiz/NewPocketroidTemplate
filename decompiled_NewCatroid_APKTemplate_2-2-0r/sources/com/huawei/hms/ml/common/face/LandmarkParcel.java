package com.huawei.hms.ml.common.face;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class LandmarkParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LandmarkParcel> CREATOR = new Parcelable.Creator<LandmarkParcel>() { // from class: com.huawei.hms.ml.common.face.LandmarkParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LandmarkParcel createFromParcel(Parcel parcel) {
            return new LandmarkParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LandmarkParcel[] newArray(int i) {
            return new LandmarkParcel[i];
        }
    };
    public int landmarkType;
    public MLVisionPointParcel point;
    public PointF position;

    public LandmarkParcel() {
    }

    public LandmarkParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.point = (MLVisionPointParcel) parcelReader.readParcelable(2, MLVisionPointParcel.CREATOR, null);
        this.position = (PointF) parcelReader.readParcelable(3, PointF.CREATOR, null);
        this.landmarkType = parcelReader.readInt(4, 0);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeParcelable(2, this.point, i, false);
        parcelWriter.writeParcelable(3, this.position, i, false);
        parcelWriter.writeInt(4, this.landmarkType);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
