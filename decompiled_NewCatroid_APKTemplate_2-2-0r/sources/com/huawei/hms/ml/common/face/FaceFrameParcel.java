package com.huawei.hms.ml.common.face;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;
import java.util.Locale;

/* JADX INFO: loaded from: classes10.dex */
public class FaceFrameParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<FaceFrameParcel> CREATOR = new Parcelable.Creator<FaceFrameParcel>() { // from class: com.huawei.hms.ml.common.face.FaceFrameParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceFrameParcel createFromParcel(Parcel parcel) {
            return new FaceFrameParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceFrameParcel[] newArray(int i) {
            return new FaceFrameParcel[i];
        }
    };
    private Bitmap bitmap;
    private byte[] bytes;
    private int continuousMode;
    private int format;
    private int frameId;
    private int height;
    private int rotation;
    private long timestampMillis;
    private int width;

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setContinuousMode(int i) {
        this.continuousMode = i;
    }

    public int getContinuousMode() {
        return this.continuousMode;
    }

    public void setFrameId(int i) {
        this.frameId = i;
    }

    public int getFrameId() {
        return this.frameId;
    }

    public void setFormat(int i) {
        this.format = i;
    }

    public int getFormat() {
        return this.format;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setRotation(int i) {
        this.rotation = i;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void setTimestampMillis(long j) {
        this.timestampMillis = j;
    }

    public long getTimestampMillis() {
        return this.timestampMillis;
    }

    public void setBytes(byte[] bArr) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        this.bytes = bArr;
    }

    public byte[] getBytes() {
        byte[] bArr = this.bytes;
        return bArr != null ? bArr : new byte[0];
    }

    public FaceFrameParcel() {
    }

    public FaceFrameParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.frameId = parcelReader.readInt(2, 0);
        this.format = parcelReader.readInt(3, 0);
        this.height = parcelReader.readInt(4, 0);
        this.width = parcelReader.readInt(5, 0);
        this.rotation = parcelReader.readInt(6, 0);
        this.timestampMillis = parcelReader.readLong(7, 0L);
        this.bytes = parcelReader.createByteArray(8, null);
        this.continuousMode = parcelReader.readInt(9, 0);
        this.bitmap = (Bitmap) parcelReader.readParcelable(10, Bitmap.CREATOR, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeInt(2, this.frameId);
        parcelWriter.writeInt(3, this.format);
        parcelWriter.writeInt(4, this.height);
        parcelWriter.writeInt(5, this.width);
        parcelWriter.writeInt(6, this.rotation);
        parcelWriter.writeLong(7, this.timestampMillis);
        parcelWriter.writeByteArray(8, this.bytes, false);
        parcelWriter.writeInt(9, this.continuousMode);
        parcelWriter.writeParcelable(10, this.bitmap, i, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }

    public String toString() {
        return String.format(Locale.ROOT, "frameId=%d, format=%d, height=%d, width=%d, rotation=%d, timestampMillis=%d", Integer.valueOf(this.frameId), Integer.valueOf(this.format), Integer.valueOf(this.height), Integer.valueOf(this.width), Integer.valueOf(this.rotation), Long.valueOf(this.timestampMillis));
    }
}
