package com.huawei.hms.mlkit.tts.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class TtsTextParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TtsTextParcel> CREATOR = new Parcelable.Creator<TtsTextParcel>() { // from class: com.huawei.hms.mlkit.tts.common.TtsTextParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsTextParcel createFromParcel(Parcel parcel) {
            return new TtsTextParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsTextParcel[] newArray(int i) {
            return new TtsTextParcel[i];
        }
    };
    public String language;
    public String speaker;
    public String taskId;
    public String text;

    public TtsTextParcel(String str, String str2, String str3, String str4) {
        this.taskId = str;
        this.language = str2;
        this.speaker = str3;
        this.text = str4;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String str) {
        this.taskId = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getSpeaker() {
        return this.speaker;
    }

    public void setSpeaker(String str) {
        this.speaker = str;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public TtsTextParcel() {
    }

    protected TtsTextParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.taskId = parcelReader.createString(2, null);
        this.language = parcelReader.createString(3, null);
        this.speaker = parcelReader.createString(4, null);
        this.text = parcelReader.createString(5, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.taskId, false);
        parcelWriter.writeString(3, this.language, false);
        parcelWriter.writeString(4, this.speaker, false);
        parcelWriter.writeString(5, this.text, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
