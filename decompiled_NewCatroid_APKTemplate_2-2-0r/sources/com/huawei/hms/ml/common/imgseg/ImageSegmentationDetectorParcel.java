package com.huawei.hms.ml.common.imgseg;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class ImageSegmentationDetectorParcel implements Parcelable {
    public static final Parcelable.Creator<ImageSegmentationDetectorParcel> CREATOR = new Parcelable.Creator<ImageSegmentationDetectorParcel>() { // from class: com.huawei.hms.ml.common.imgseg.ImageSegmentationDetectorParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageSegmentationDetectorParcel createFromParcel(Parcel parcel) {
            return new ImageSegmentationDetectorParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageSegmentationDetectorParcel[] newArray(int i) {
            return new ImageSegmentationDetectorParcel[i];
        }
    };
    public Bitmap foreground;
    public Bitmap grayscale;
    public byte[] masks;
    public Bitmap original;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImageSegmentationDetectorParcel(byte[] bArr, Bitmap bitmap, Bitmap bitmap2) {
        this(bArr, bitmap, bitmap2, null);
    }

    public ImageSegmentationDetectorParcel(byte[] bArr, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3) {
        this.masks = bArr;
        this.foreground = bitmap;
        this.grayscale = bitmap2;
        this.original = bitmap3;
    }

    public ImageSegmentationDetectorParcel() {
    }

    public ImageSegmentationDetectorParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.masks = parcelReader.createByteArray(2, null);
        this.foreground = (Bitmap) parcelReader.readParcelable(3, Bitmap.CREATOR, null);
        this.grayscale = (Bitmap) parcelReader.readParcelable(4, Bitmap.CREATOR, null);
        this.original = (Bitmap) parcelReader.readParcelable(5, Bitmap.CREATOR, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeByteArray(2, this.masks, false);
        parcelWriter.writeParcelable(3, this.foreground, i, false);
        parcelWriter.writeParcelable(4, this.grayscale, i, false);
        parcelWriter.writeParcelable(5, this.original, i, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
