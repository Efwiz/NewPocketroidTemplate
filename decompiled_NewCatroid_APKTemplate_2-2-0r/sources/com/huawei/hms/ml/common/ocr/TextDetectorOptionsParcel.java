package com.huawei.hms.ml.common.ocr;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class TextDetectorOptionsParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TextDetectorOptionsParcel> CREATOR = new Parcelable.Creator<TextDetectorOptionsParcel>() { // from class: com.huawei.hms.ml.common.ocr.TextDetectorOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextDetectorOptionsParcel createFromParcel(Parcel parcel) {
            return new TextDetectorOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextDetectorOptionsParcel[] newArray(int i) {
            return new TextDetectorOptionsParcel[i];
        }
    };
    public Bundle bundle;
    public int detectMode;
    public String hintedLanguage;

    public TextDetectorOptionsParcel() {
    }

    public TextDetectorOptionsParcel(String str, int i, Bundle bundle) {
        this.hintedLanguage = str;
        this.detectMode = i;
        this.bundle = bundle;
    }

    public TextDetectorOptionsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.hintedLanguage = parcelReader.createString(2, null);
        this.detectMode = parcelReader.readInt(3, 0);
        this.bundle = parcelReader.readBundle(4, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.hintedLanguage, false);
        parcelWriter.writeInt(3, this.detectMode);
        parcelWriter.writeBundle(4, this.bundle, true);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
