package com.huawei.hms.ml.common.object;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class ObjectDetectorOptionsParcel implements Parcelable {
    public static final Parcelable.Creator<ObjectDetectorOptionsParcel> CREATOR = new Parcelable.Creator<ObjectDetectorOptionsParcel>() { // from class: com.huawei.hms.ml.common.object.ObjectDetectorOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObjectDetectorOptionsParcel createFromParcel(Parcel parcel) {
            return new ObjectDetectorOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObjectDetectorOptionsParcel[] newArray(int i) {
            return new ObjectDetectorOptionsParcel[i];
        }
    };
    public final Bundle bundle;
    public final int detectorMode;
    public final boolean isClassification;
    public final boolean isMultipleObjects;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ObjectDetectorOptionsParcel(int i, boolean z, boolean z2, Bundle bundle) {
        this.detectorMode = i;
        this.isMultipleObjects = z;
        this.isClassification = z2;
        this.bundle = bundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeInt(2, this.detectorMode);
        parcelWriter.writeBoolean(3, this.isMultipleObjects);
        parcelWriter.writeBoolean(4, this.isClassification);
        parcelWriter.writeBundle(5, this.bundle, true);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }

    protected ObjectDetectorOptionsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.detectorMode = parcelReader.readInt(2, 0);
        this.isMultipleObjects = parcelReader.readBoolean(3, false);
        this.isClassification = parcelReader.readBoolean(4, false);
        this.bundle = parcelReader.readBundle(5, null);
        parcelReader.finish();
    }
}
