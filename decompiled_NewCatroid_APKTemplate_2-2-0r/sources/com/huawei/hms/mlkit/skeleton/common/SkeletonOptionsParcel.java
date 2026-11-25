package com.huawei.hms.mlkit.skeleton.common;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class SkeletonOptionsParcel implements Parcelable {
    public static final Parcelable.Creator<SkeletonOptionsParcel> CREATOR = new Parcelable.Creator<SkeletonOptionsParcel>() { // from class: com.huawei.hms.mlkit.skeleton.common.SkeletonOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SkeletonOptionsParcel createFromParcel(Parcel parcel) {
            return new SkeletonOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SkeletonOptionsParcel[] newArray(int i) {
            return new SkeletonOptionsParcel[i];
        }
    };
    public int analyzerType;
    public Bundle bundle;
    public boolean isStablefiltering;
    public int similarityStrategy;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SkeletonOptionsParcel() {
    }

    public SkeletonOptionsParcel(int i, boolean z, Bundle bundle, int i2) {
        this.similarityStrategy = i;
        this.isStablefiltering = z;
        this.bundle = bundle;
        this.analyzerType = i2;
    }

    public SkeletonOptionsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.similarityStrategy = parcelReader.readInt(2, 0);
        this.isStablefiltering = parcelReader.readBoolean(3, false);
        this.bundle = parcelReader.readBundle(4, null);
        this.analyzerType = parcelReader.readInt(5, 0);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeInt(2, this.similarityStrategy);
        parcelWriter.writeBoolean(3, this.isStablefiltering);
        parcelWriter.writeBundle(4, this.bundle, true);
        parcelWriter.writeInt(5, this.analyzerType);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
