package com.huawei.hms.ml.common.imgseg;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class ImageSegmentationOptionsParcel implements Parcelable {
    public static final Parcelable.Creator<ImageSegmentationOptionsParcel> CREATOR = new Parcelable.Creator<ImageSegmentationOptionsParcel>() { // from class: com.huawei.hms.ml.common.imgseg.ImageSegmentationOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageSegmentationOptionsParcel createFromParcel(Parcel parcel) {
            return new ImageSegmentationOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageSegmentationOptionsParcel[] newArray(int i) {
            return new ImageSegmentationOptionsParcel[i];
        }
    };
    public final Bundle bundle;
    public final int detectorMode;
    public final boolean isFineDetection;
    public final int scene;
    public final int updateVer;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImageSegmentationOptionsParcel(int i, boolean z, int i2, Bundle bundle, int i3) {
        this.detectorMode = i;
        this.isFineDetection = z;
        this.scene = i2;
        this.bundle = bundle;
        this.updateVer = i3;
    }

    public ImageSegmentationOptionsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.detectorMode = parcelReader.readInt(1, 0);
        this.isFineDetection = parcelReader.readBoolean(2, false);
        this.scene = parcelReader.readInt(3, 0);
        this.bundle = parcelReader.readBundle(4, null);
        this.updateVer = parcelReader.readInt(5, 0);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeInt(1, this.detectorMode);
        parcelWriter.writeBoolean(2, this.isFineDetection);
        parcelWriter.writeInt(3, this.scene);
        parcelWriter.writeBundle(4, this.bundle, true);
        parcelWriter.writeInt(5, this.updateVer);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
