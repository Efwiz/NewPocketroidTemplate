package com.huawei.hms.mlkit.tts.common;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class TtsOptionsParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TtsOptionsParcel> CREATOR = new Parcelable.Creator<TtsOptionsParcel>() { // from class: com.huawei.hms.mlkit.tts.common.TtsOptionsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsOptionsParcel createFromParcel(Parcel parcel) {
            return new TtsOptionsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsOptionsParcel[] newArray(int i) {
            return new TtsOptionsParcel[i];
        }
    };
    public String backPath;
    public Bundle bundle;
    public String frontPath;
    public String language;
    public String person;
    public float pitch;
    public float speed;
    public float volume;

    public TtsOptionsParcel(Bundle bundle, String str, String str2, float f, float f2, float f3, String str3, String str4) {
        this.bundle = bundle;
        this.person = str;
        this.language = str2;
        this.speed = f;
        this.volume = f2;
        this.pitch = f3;
        this.frontPath = str3;
        this.backPath = str4;
    }

    public TtsOptionsParcel(Parcel parcel) {
        this.person = null;
        this.language = null;
        this.speed = 5.0f;
        this.volume = 5.0f;
        this.pitch = 5.0f;
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.bundle = parcelReader.readBundle(2, null);
        this.person = parcelReader.createString(3, null);
        this.language = parcelReader.createString(4, null);
        this.speed = parcelReader.readFloat(5, 5.0f);
        this.volume = parcelReader.readFloat(6, 5.0f);
        this.pitch = parcelReader.readFloat(7, 5.0f);
        this.frontPath = parcelReader.createString(8, null);
        this.backPath = parcelReader.createString(9, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeBundle(2, this.bundle, true);
        parcelWriter.writeString(3, this.person, false);
        parcelWriter.writeString(4, this.language, false);
        parcelWriter.writeFloat(5, this.speed);
        parcelWriter.writeFloat(6, this.volume);
        parcelWriter.writeFloat(7, this.pitch);
        parcelWriter.writeString(8, this.frontPath, false);
        parcelWriter.writeString(9, this.backPath, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
