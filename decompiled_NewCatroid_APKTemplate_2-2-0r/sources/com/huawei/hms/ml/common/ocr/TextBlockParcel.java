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
public class TextBlockParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TextBlockParcel> CREATOR = new Parcelable.Creator<TextBlockParcel>() { // from class: com.huawei.hms.ml.common.ocr.TextBlockParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextBlockParcel createFromParcel(Parcel parcel) {
            return new TextBlockParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextBlockParcel[] newArray(int i) {
            return new TextBlockParcel[i];
        }
    };
    private Rect blockRect;
    public float confidence;
    private List<Point> cornerPoints;
    private List<LineParcel> lines;
    public String text;

    public String getText() {
        return this.text;
    }

    public float getConfidence() {
        return this.confidence;
    }

    public Rect getBlockRect() {
        return this.blockRect;
    }

    public List<Point> getCornerPoints() {
        return this.cornerPoints;
    }

    public List<LineParcel> getLines() {
        return this.lines;
    }

    public TextBlockParcel(String str, float f, Rect rect, List<Point> list, List<LineParcel> list2) {
        this.text = str;
        this.confidence = f;
        this.blockRect = rect;
        this.cornerPoints = list;
        this.lines = list2;
    }

    public TextBlockParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.text = parcelReader.createString(2, null);
        this.confidence = parcelReader.readFloat(3, 0.0f);
        this.blockRect = (Rect) parcelReader.readParcelable(4, Rect.CREATOR, null);
        this.cornerPoints = parcelReader.createTypedList(5, Point.CREATOR, null);
        this.lines = parcelReader.createTypedList(6, LineParcel.CREATOR, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.text, false);
        parcelWriter.writeFloat(3, this.confidence);
        parcelWriter.writeParcelable(4, this.blockRect, i, false);
        parcelWriter.writeTypedList(5, this.cornerPoints, false);
        parcelWriter.writeTypedList(6, this.lines, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
