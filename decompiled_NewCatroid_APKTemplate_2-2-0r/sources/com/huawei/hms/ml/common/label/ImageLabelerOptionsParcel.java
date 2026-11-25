package com.huawei.hms.ml.common.label;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class ImageLabelerOptionsParcel implements Parcelable {
    public static final Parcelable.Creator<ImageLabelerOptionsParcel> CREATOR = new Parcelable.Creator<ImageLabelerOptionsParcel>() { // from class: com.huawei.hms.ml.common.label.ImageLabelerOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageLabelerOptionsParcel createFromParcel(Parcel parcel) {
            return new ImageLabelerOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageLabelerOptionsParcel[] newArray(int i) {
            return new ImageLabelerOptionsParcel[i];
        }
    };
    public Bundle bundle;
    public float confidenceThreshold;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImageLabelerOptionsParcel() {
    }

    public ImageLabelerOptionsParcel(float f, Bundle bundle) {
        this.confidenceThreshold = f;
        this.bundle = bundle;
    }

    public ImageLabelerOptionsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.confidenceThreshold = parcelReader.readFloat(2, 0.0f);
        this.bundle = parcelReader.readBundle(3, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeFloat(2, this.confidenceThreshold);
        parcelWriter.writeBundle(3, this.bundle, true);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
