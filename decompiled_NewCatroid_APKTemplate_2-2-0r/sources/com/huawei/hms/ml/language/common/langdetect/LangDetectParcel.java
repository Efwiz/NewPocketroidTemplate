package com.huawei.hms.ml.language.common.langdetect;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class LangDetectParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LangDetectParcel> CREATOR = new Parcelable.Creator<LangDetectParcel>() { // from class: com.huawei.hms.ml.language.common.langdetect.LangDetectParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LangDetectParcel createFromParcel(Parcel parcel) {
            return new LangDetectParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LangDetectParcel[] newArray(int i) {
            return new LangDetectParcel[i];
        }
    };
    public String languageCode;
    public float prob;

    public LangDetectParcel(String str, float f) {
        this.languageCode = str;
        this.prob = f;
    }

    protected LangDetectParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.languageCode = parcelReader.createString(2, null);
        this.prob = parcelReader.readFloat(3, 0.0f);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.languageCode, false);
        parcelWriter.writeFloat(3, this.prob);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
