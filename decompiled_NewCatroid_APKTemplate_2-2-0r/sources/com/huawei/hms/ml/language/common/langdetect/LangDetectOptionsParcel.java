package com.huawei.hms.ml.language.common.langdetect;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class LangDetectOptionsParcel implements Parcelable {
    public static final Parcelable.Creator<LangDetectOptionsParcel> CREATOR = new Parcelable.Creator<LangDetectOptionsParcel>() { // from class: com.huawei.hms.ml.language.common.langdetect.LangDetectOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LangDetectOptionsParcel createFromParcel(Parcel parcel) {
            return new LangDetectOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LangDetectOptionsParcel[] newArray(int i) {
            return new LangDetectOptionsParcel[i];
        }
    };
    private final Bundle bundle;
    private final float threshold;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LangDetectOptionsParcel(float f, Bundle bundle) {
        this.threshold = f;
        this.bundle = bundle;
    }

    protected LangDetectOptionsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.threshold = parcelReader.readFloat(2, 0.0f);
        this.bundle = parcelReader.readBundle(3, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeFloat(2, this.threshold);
        parcelWriter.writeBundle(3, this.bundle, true);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }

    public float getThreshold() {
        return this.threshold;
    }

    public Bundle getBundle() {
        return this.bundle;
    }
}
