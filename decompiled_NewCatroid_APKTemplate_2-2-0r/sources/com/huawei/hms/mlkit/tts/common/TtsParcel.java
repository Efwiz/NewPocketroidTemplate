package com.huawei.hms.mlkit.tts.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class TtsParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TtsParcel> CREATOR = new Parcelable.Creator<TtsParcel>() { // from class: com.huawei.hms.mlkit.tts.common.TtsParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsParcel createFromParcel(Parcel parcel) {
            return new TtsParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsParcel[] newArray(int i) {
            return new TtsParcel[i];
        }
    };
    public final boolean isEnd;
    public final byte[] pcm;
    public final int rangeEnd;
    public final int rangeStart;

    public TtsParcel(byte[] bArr, int i, int i2, boolean z) {
        this.pcm = bArr;
        this.rangeStart = i;
        this.rangeEnd = i2;
        this.isEnd = z;
    }

    public TtsParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.pcm = parcelReader.createByteArray(2, null);
        this.rangeStart = parcelReader.readInt(3, 0);
        this.rangeEnd = parcelReader.readInt(4, 0);
        this.isEnd = parcelReader.readBoolean(5, false);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeByteArray(2, this.pcm, false);
        parcelWriter.writeInt(3, this.rangeStart);
        parcelWriter.writeInt(4, this.rangeEnd);
        parcelWriter.writeBoolean(5, this.isEnd);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
