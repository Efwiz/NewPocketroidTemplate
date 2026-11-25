package com.huawei.hms.ml.common.imgseg;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class ImageSegmentationDetectorFrameParcel implements Parcelable {
    public static final Parcelable.Creator<ImageSegmentationDetectorFrameParcel> CREATOR = new Parcelable.Creator<ImageSegmentationDetectorFrameParcel>() { // from class: com.huawei.hms.ml.common.imgseg.ImageSegmentationDetectorFrameParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageSegmentationDetectorFrameParcel createFromParcel(Parcel parcel) {
            return new ImageSegmentationDetectorFrameParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageSegmentationDetectorFrameParcel[] newArray(int i) {
            return new ImageSegmentationDetectorFrameParcel[i];
        }
    };
    public Bitmap bitmap;
    public byte[] bytes;
    private int format;
    private int frameId;
    public int height;
    public int rotation;
    private int visibleHeight;
    private int visibleWidth;
    public int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImageSegmentationDetectorFrameParcel(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, Bitmap bitmap, int i7) {
        this.bytes = bArr;
        this.width = i;
        this.height = i2;
        this.rotation = i3;
        this.format = i4;
        this.visibleWidth = i5;
        this.visibleHeight = i6;
        this.bitmap = bitmap;
        this.frameId = i7;
    }

    public ImageSegmentationDetectorFrameParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.bytes = parcelReader.createByteArray(2, null);
        this.width = parcelReader.readInt(3, 0);
        this.height = parcelReader.readInt(4, 0);
        this.rotation = parcelReader.readInt(5, 0);
        this.format = parcelReader.readInt(6, 0);
        this.visibleWidth = parcelReader.readInt(7, 0);
        this.visibleHeight = parcelReader.readInt(8, 0);
        this.bitmap = (Bitmap) parcelReader.readParcelable(9, Bitmap.CREATOR, null);
        this.frameId = parcelReader.readInt(10, 0);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeByteArray(2, this.bytes, false);
        parcelWriter.writeInt(3, this.width);
        parcelWriter.writeInt(4, this.height);
        parcelWriter.writeInt(5, this.rotation);
        parcelWriter.writeInt(6, this.format);
        parcelWriter.writeInt(7, this.visibleWidth);
        parcelWriter.writeInt(8, this.visibleHeight);
        parcelWriter.writeParcelable(9, this.bitmap, i, false);
        parcelWriter.writeInt(10, this.frameId);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
