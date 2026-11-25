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
public class TextParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TextParcel> CREATOR = new Parcelable.Creator<TextParcel>() { // from class: com.huawei.hms.ml.common.ocr.TextParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextParcel createFromParcel(Parcel parcel) {
            return new TextParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextParcel[] newArray(int i) {
            return new TextParcel[i];
        }
    };
    private List<TextBlockParcel> blocks;
    public float confidence;
    private List<Point> cornerPoints;
    public String language;
    public String text;
    private Rect textRect;

    public String getText() {
        return this.text;
    }

    public float getConfidence() {
        return this.confidence;
    }

    public Rect getTextRect() {
        return this.textRect;
    }

    public List<Point> getCornerPoints() {
        return this.cornerPoints;
    }

    public String getLanguage() {
        return this.language;
    }

    public List<TextBlockParcel> getBlocks() {
        return this.blocks;
    }

    public TextParcel() {
    }

    public TextParcel(String str, float f, Rect rect, List<Point> list, String str2, List<TextBlockParcel> list2) {
        this.text = str;
        this.confidence = f;
        this.textRect = rect;
        this.cornerPoints = list;
        this.language = str2;
        this.blocks = list2;
    }

    public TextParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.text = parcelReader.createString(2, null);
        this.confidence = parcelReader.readFloat(3, 0.0f);
        this.textRect = (Rect) parcelReader.readParcelable(4, Rect.CREATOR, null);
        this.cornerPoints = parcelReader.createTypedList(5, Point.CREATOR, null);
        this.language = parcelReader.createString(6, null);
        this.blocks = parcelReader.createTypedList(7, TextBlockParcel.CREATOR, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeString(2, this.text, false);
        parcelWriter.writeFloat(3, this.confidence);
        parcelWriter.writeParcelable(4, this.textRect, i, false);
        parcelWriter.writeTypedList(5, this.cornerPoints, false);
        parcelWriter.writeString(6, this.language, false);
        parcelWriter.writeTypedList(7, this.blocks, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
