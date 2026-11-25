package com.huawei.hms.ml.common.ocr;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class LineParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LineParcel> CREATOR = new Parcelable.Creator<LineParcel>() { // from class: com.huawei.hms.ml.common.ocr.LineParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LineParcel createFromParcel(Parcel parcel) {
            return new LineParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LineParcel[] newArray(int i) {
            return new LineParcel[i];
        }
    };
    public float angle;
    private int blockId;
    public float confidence;
    private List<Point> cornerPoints;
    private List<ElementParcel> elements;
    public String language;
    public int lineNum;
    private Rect lineRect;
    public String text;
    public boolean vertical;

    public String getText() {
        return this.text;
    }

    public float getConfidence() {
        return this.confidence;
    }

    public Rect getLineRect() {
        return this.lineRect;
    }

    public List<Point> getCornerPoints() {
        return this.cornerPoints;
    }

    public String getLanguage() {
        return this.language;
    }

    public List<ElementParcel> getElements() {
        return this.elements;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int getLineNum() {
        return this.lineNum;
    }

    public float getAngle() {
        return this.angle;
    }

    public LineParcel(String str, float f, Rect rect, List<Point> list, String str2, List<ElementParcel> list2, int i, int i2, boolean z, float f2) {
        this.text = str;
        this.confidence = f;
        this.lineRect = rect;
        this.cornerPoints = list;
        this.language = str2;
        this.elements = list2;
        this.blockId = i;
        this.lineNum = i2;
        this.vertical = z;
        this.angle = f2;
    }

    public LineParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.text = parcelReader.createString(2, null);
        this.confidence = parcelReader.readFloat(3, 0.0f);
        this.lineRect = (Rect) parcelReader.readParcelable(4, Rect.CREATOR, null);
        this.cornerPoints = parcelReader.createTypedList(5, Point.CREATOR, null);
        this.language = parcelReader.createString(6, null);
        this.elements = parcelReader.createTypedList(7, ElementParcel.CREATOR, null);
        this.blockId = parcelReader.readInt(8, 0);
        this.lineNum = parcelReader.readInt(9, 0);
        this.vertical = parcelReader.readBoolean(10, false);
        this.angle = parcelReader.readFloat(11, 0.0f);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.text, false);
        parcelWriter.writeFloat(3, this.confidence);
        parcelWriter.writeParcelable(4, this.lineRect, i, false);
        parcelWriter.writeTypedList(5, this.cornerPoints, false);
        parcelWriter.writeString(6, this.language, false);
        parcelWriter.writeTypedList(7, this.elements, false);
        parcelWriter.writeInt(8, this.blockId);
        parcelWriter.writeInt(9, this.lineNum);
        parcelWriter.writeBoolean(10, this.vertical);
        parcelWriter.writeFloat(11, this.angle);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
