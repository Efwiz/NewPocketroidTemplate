package com.huawei.hms.ml.language.common.translate;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class TranslateParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TranslateParcel> CREATOR = new Parcelable.Creator<TranslateParcel>() { // from class: com.huawei.hms.ml.language.common.translate.TranslateParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslateParcel createFromParcel(Parcel parcel) {
            return new TranslateParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslateParcel[] newArray(int i) {
            return new TranslateParcel[i];
        }
    };
    public String result;

    public TranslateParcel(String str) {
        this.result = str;
    }

    protected TranslateParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.result = parcelReader.createString(2, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.result, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
