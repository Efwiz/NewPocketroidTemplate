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
public class ElementParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ElementParcel> CREATOR = new Parcelable.Creator<ElementParcel>() { // from class: com.huawei.hms.ml.common.ocr.ElementParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ElementParcel createFromParcel(Parcel parcel) {
            return new ElementParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ElementParcel[] newArray(int i) {
            return new ElementParcel[i];
        }
    };
    public float confidence;
    private List<Point> cornerPoints;
    private Rect elementRect;
    public String language;
    public String text;

    public String getText() {
        return this.text;
    }

    public float getConfidence() {
        return this.confidence;
    }

    public Rect getElementRect() {
        return this.elementRect;
    }

    public List<Point> getCornerPoints() {
        return this.cornerPoints;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setText(String str) {
        this.text = str;
    }

    public void setConfidence(float f) {
        this.confidence = f;
    }

    public void setElementRect(Rect rect) {
        this.elementRect = rect;
    }

    public void setCornerPoints(List<Point> list) {
        this.cornerPoints = list;
    }

    public void setsLanguage(String str) {
        this.language = str;
    }

    public ElementParcel(String str, float f, Rect rect, List<Point> list, String str2) {
        this.text = str;
        this.confidence = f;
        this.elementRect = rect;
        this.cornerPoints = list;
        this.language = str2;
    }

    public ElementParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.text = parcelReader.createString(2, null);
        this.confidence = parcelReader.readFloat(3, 0.0f);
        this.elementRect = (Rect) parcelReader.readParcelable(4, Rect.CREATOR, null);
        this.cornerPoints = parcelReader.createTypedList(5, Point.CREATOR, null);
        this.language = parcelReader.createString(6, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.text, false);
        parcelWriter.writeFloat(3, this.confidence);
        parcelWriter.writeParcelable(4, this.elementRect, i, false);
        parcelWriter.writeTypedList(5, this.cornerPoints, false);
        parcelWriter.writeString(6, this.language, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
