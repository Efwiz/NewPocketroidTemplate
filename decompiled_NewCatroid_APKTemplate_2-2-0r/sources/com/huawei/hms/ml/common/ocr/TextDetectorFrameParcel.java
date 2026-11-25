package com.huawei.hms.ml.common.ocr;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class TextDetectorFrameParcel implements Parcelable {
    public static final Parcelable.Creator<TextDetectorFrameParcel> CREATOR = new Parcelable.Creator<TextDetectorFrameParcel>() { // from class: com.huawei.hms.ml.common.ocr.TextDetectorFrameParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextDetectorFrameParcel createFromParcel(Parcel parcel) {
            return new TextDetectorFrameParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextDetectorFrameParcel[] newArray(int i) {
            return new TextDetectorFrameParcel[i];
        }
    };
    public Bitmap bitmap;
    public byte[] bytes;
    public int format;
    public int height;
    public int rotation;
    public int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TextDetectorFrameParcel() {
    }

    protected TextDetectorFrameParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.width = parcelReader.readInt(2, 0);
        this.height = parcelReader.readInt(3, 0);
        this.bitmap = (Bitmap) parcelReader.readParcelable(4, Bitmap.CREATOR, null);
        this.format = parcelReader.readInt(5, 0);
        this.rotation = parcelReader.readInt(6, 0);
        this.bytes = parcelReader.createByteArray(7, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeInt(2, this.width);
        parcelWriter.writeInt(3, this.height);
        parcelWriter.writeParcelable(4, this.bitmap, i, false);
        parcelWriter.writeInt(5, this.format);
        parcelWriter.writeInt(6, this.rotation);
        parcelWriter.writeByteArray(7, this.bytes, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
