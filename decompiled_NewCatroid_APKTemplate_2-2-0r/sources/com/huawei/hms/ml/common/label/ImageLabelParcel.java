package com.huawei.hms.ml.common.label;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class ImageLabelParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ImageLabelParcel> CREATOR = new Parcelable.Creator<ImageLabelParcel>() { // from class: com.huawei.hms.ml.common.label.ImageLabelParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageLabelParcel createFromParcel(Parcel parcel) {
            return new ImageLabelParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageLabelParcel[] newArray(int i) {
            return new ImageLabelParcel[i];
        }
    };
    public final Float confidence;
    public final String entityId;
    public final String text;

    public ImageLabelParcel(String str, String str2, Float f) {
        this.entityId = str;
        this.text = str2;
        this.confidence = f;
    }

    public ImageLabelParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.entityId = parcelReader.createString(2, null);
        this.text = parcelReader.createString(3, null);
        this.confidence = parcelReader.readFloatObject(4, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.entityId, false);
        parcelWriter.writeString(3, this.text, false);
        parcelWriter.writeFloatObject(4, this.confidence, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
