package com.huawei.hms.ml.language.common.translate;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class TranslateFrameParcel implements Parcelable {
    public static final Parcelable.Creator<TranslateFrameParcel> CREATOR = new Parcelable.Creator<TranslateFrameParcel>() { // from class: com.huawei.hms.ml.language.common.translate.TranslateFrameParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslateFrameParcel createFromParcel(Parcel parcel) {
            return new TranslateFrameParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslateFrameParcel[] newArray(int i) {
            return new TranslateFrameParcel[i];
        }
    };
    public final String modelName;
    public final String modelPath;
    public final String sourceText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TranslateFrameParcel(String str, String str2, String str3) {
        this.sourceText = str;
        this.modelPath = str2;
        this.modelName = str3;
    }

    protected TranslateFrameParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.sourceText = parcelReader.createString(2, null);
        this.modelPath = parcelReader.createString(3, null);
        this.modelName = parcelReader.createString(4, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.sourceText, false);
        parcelWriter.writeString(3, this.modelPath, false);
        parcelWriter.writeString(4, this.modelName, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
