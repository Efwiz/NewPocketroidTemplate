package com.huawei.hms.ml.common.face;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class MLVisionPointParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<MLVisionPointParcel> CREATOR = new Parcelable.Creator<MLVisionPointParcel>() { // from class: com.huawei.hms.ml.common.face.MLVisionPointParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MLVisionPointParcel createFromParcel(Parcel parcel) {
            return new MLVisionPointParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MLVisionPointParcel[] newArray(int i) {
            return new MLVisionPointParcel[i];
        }
    };
    private Float x;
    private Float y;
    private Float z;

    public Float getX() {
        return this.x;
    }

    public Float getY() {
        return this.y;
    }

    public Float getZ() {
        return this.z;
    }

    public MLVisionPointParcel(Float f, Float f2, Float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public MLVisionPointParcel(Float f, Float f2) {
        this(f, f2, (Float) null);
    }

    public MLVisionPointParcel() {
    }

    public MLVisionPointParcel(float f, float f2, float f3) {
        this.x = Float.valueOf(f);
        this.y = Float.valueOf(f2);
        this.z = Float.valueOf(f3);
    }

    public MLVisionPointParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        Float fValueOf = Float.valueOf(0.0f);
        this.x = parcelReader.readFloatObject(2, fValueOf);
        this.y = parcelReader.readFloatObject(3, fValueOf);
        this.z = parcelReader.readFloatObject(4, fValueOf);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeFloatObject(2, this.x, false);
        parcelWriter.writeFloatObject(3, this.y, false);
        parcelWriter.writeFloatObject(4, this.z, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
