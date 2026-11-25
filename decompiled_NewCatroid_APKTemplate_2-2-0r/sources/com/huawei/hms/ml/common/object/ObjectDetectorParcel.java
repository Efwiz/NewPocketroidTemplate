package com.huawei.hms.ml.common.object;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class ObjectDetectorParcel implements Parcelable {
    public static final Parcelable.Creator<ObjectDetectorParcel> CREATOR = new Parcelable.Creator<ObjectDetectorParcel>() { // from class: com.huawei.hms.ml.common.object.ObjectDetectorParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObjectDetectorParcel createFromParcel(Parcel parcel) {
            return new ObjectDetectorParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObjectDetectorParcel[] newArray(int i) {
            return new ObjectDetectorParcel[i];
        }
    };
    public final int category;
    public final Float confidence;
    public Rect rect;
    public final Integer trackingId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ObjectDetectorParcel(Rect rect, Integer num, Float f, int i) {
        this.rect = rect;
        this.trackingId = num;
        this.confidence = f;
        this.category = i;
    }

    public ObjectDetectorParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.rect = (Rect) parcelReader.readParcelable(2, Rect.CREATOR, null);
        this.trackingId = parcelReader.readIntegerObject(3, null);
        this.confidence = parcelReader.readFloatObject(4, null);
        this.category = parcelReader.readInt(5, 0);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeParcelable(2, this.rect, i, false);
        parcelWriter.writeIntegerObject(3, this.trackingId, false);
        parcelWriter.writeFloatObject(4, this.confidence, false);
        parcelWriter.writeInt(5, this.category);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
