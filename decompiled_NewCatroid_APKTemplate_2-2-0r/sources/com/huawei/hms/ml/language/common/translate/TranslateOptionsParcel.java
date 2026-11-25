package com.huawei.hms.ml.language.common.translate;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class TranslateOptionsParcel implements Parcelable {
    public static final Parcelable.Creator<TranslateOptionsParcel> CREATOR = new Parcelable.Creator<TranslateOptionsParcel>() { // from class: com.huawei.hms.ml.language.common.translate.TranslateOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslateOptionsParcel createFromParcel(Parcel parcel) {
            return new TranslateOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslateOptionsParcel[] newArray(int i) {
            return new TranslateOptionsParcel[i];
        }
    };
    public final Bundle bundle;
    public final String sourceLanguage;
    public final String targetLanguage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TranslateOptionsParcel(String str, String str2, Bundle bundle) {
        this.sourceLanguage = str;
        this.targetLanguage = str2;
        this.bundle = bundle;
    }

    protected TranslateOptionsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.sourceLanguage = parcelReader.createString(2, null);
        this.targetLanguage = parcelReader.createString(3, null);
        this.bundle = parcelReader.readBundle(4, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.sourceLanguage, false);
        parcelWriter.writeString(3, this.targetLanguage, false);
        parcelWriter.writeBundle(4, this.bundle, true);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
