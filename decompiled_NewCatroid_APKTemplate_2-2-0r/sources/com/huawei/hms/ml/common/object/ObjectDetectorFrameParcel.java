package com.huawei.hms.ml.common.object;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class ObjectDetectorFrameParcel implements Parcelable {
    public static final Parcelable.Creator<ObjectDetectorFrameParcel> CREATOR = new Parcelable.Creator<ObjectDetectorFrameParcel>() { // from class: com.huawei.hms.ml.common.object.ObjectDetectorFrameParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObjectDetectorFrameParcel createFromParcel(Parcel parcel) {
            return new ObjectDetectorFrameParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObjectDetectorFrameParcel[] newArray(int i) {
            return new ObjectDetectorFrameParcel[i];
        }
    };
    public Bitmap bitmap;
    public byte[] bytes;
    public int format;
    public int height;
    public int rotation;
    public int visibleHeight;
    public int visibleWidth;
    public int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ObjectDetectorFrameParcel(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, Bitmap bitmap) {
        this.bytes = bArr;
        this.width = i;
        this.height = i2;
        this.rotation = i3;
        this.format = i4;
        this.visibleWidth = i5;
        this.visibleHeight = i6;
        this.bitmap = bitmap;
    }

    protected ObjectDetectorFrameParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.bytes = parcelReader.createByteArray(2, null);
        this.width = parcelReader.readInt(3, 0);
        this.height = parcelReader.readInt(4, 0);
        this.rotation = parcelReader.readInt(5, 0);
        this.format = parcelReader.readInt(6, 0);
        this.visibleWidth = parcelReader.readInt(7, 0);
        this.visibleHeight = parcelReader.readInt(8, 0);
        this.bitmap = (Bitmap) parcelReader.readParcelable(9, Bitmap.CREATOR, null);
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
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }

    public static class Builder {
        private Bitmap bitmap;
        private byte[] bytes;
        private int format;
        private int height;
        private int rotation;
        private int visibleHeight;
        private int visibleWidth;
        private int width;

        public Builder setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public Builder setBytes(byte[] bArr) {
            if (bArr != null) {
                this.bytes = Arrays.copyOf(bArr, bArr.length);
            } else {
                this.bytes = null;
            }
            return this;
        }

        public Builder setWidth(int i) {
            this.width = i;
            return this;
        }

        public Builder setHeight(int i) {
            this.height = i;
            return this;
        }

        public Builder setRotation(int i) {
            this.rotation = i;
            return this;
        }

        public Builder setFormat(int i) {
            this.format = i;
            return this;
        }

        public Builder setVisibleWidth(int i) {
            this.visibleWidth = i;
            return this;
        }

        public Builder setVisibleHeight(int i) {
            this.visibleHeight = i;
            return this;
        }

        public ObjectDetectorFrameParcel build() {
            return new ObjectDetectorFrameParcel(this.bytes, this.width, this.height, this.rotation, this.format, this.visibleWidth, this.visibleHeight, this.bitmap);
        }
    }
}
