package com.huawei.hms.ml.language.common.langdetect;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class LangDetectFrameParcel implements Parcelable {
    public static final Parcelable.Creator<LangDetectFrameParcel> CREATOR = new Parcelable.Creator<LangDetectFrameParcel>() { // from class: com.huawei.hms.ml.language.common.langdetect.LangDetectFrameParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LangDetectFrameParcel createFromParcel(Parcel parcel) {
            return new LangDetectFrameParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LangDetectFrameParcel[] newArray(int i) {
            return new LangDetectFrameParcel[i];
        }
    };
    private final int count;
    private final String sourceText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LangDetectFrameParcel(String str, int i) {
        this.sourceText = str;
        this.count = i;
    }

    protected LangDetectFrameParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.sourceText = parcelReader.createString(2, null);
        this.count = parcelReader.readInt(3, -1);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.sourceText, false);
        parcelWriter.writeInt(3, this.count);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }

    public String getSourceText() {
        return this.sourceText;
    }

    public int getCount() {
        return this.count;
    }
}
