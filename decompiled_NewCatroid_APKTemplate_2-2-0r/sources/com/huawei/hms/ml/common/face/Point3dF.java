package com.huawei.hms.ml.common.face;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes10.dex */
public class Point3dF implements Parcelable {
    public static final Parcelable.Creator<Point3dF> CREATOR = new Parcelable.Creator<Point3dF>() { // from class: com.huawei.hms.ml.common.face.Point3dF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Point3dF createFromParcel(Parcel parcel) {
            Point3dF point3dF = new Point3dF();
            point3dF.readFromParcel(parcel);
            return point3dF;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Point3dF[] newArray(int i) {
            return new Point3dF[i];
        }
    };
    public float x;
    public float y;
    public float z;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Point3dF() {
    }

    public Point3dF(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public Point3dF(Point point) {
        this.x = point.x;
        this.y = point.y;
        this.z = 0.0f;
    }

    public final void set(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public final void set(Point3dF point3dF) {
        this.x = point3dF.x;
        this.y = point3dF.y;
        this.z = point3dF.z;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public final void offset(float f, float f2, float f3) {
        this.x += f;
        this.y += f2;
        this.z += f3;
    }

    public final boolean equals(float f, float f2, float f3) {
        return Float.compare(this.x, f) == 0 && Float.compare(this.y, f2) == 0 && Float.compare(this.z, f3) == 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point3dF point3dF = (Point3dF) obj;
        return Float.compare(point3dF.x, this.x) == 0 && Float.compare(point3dF.y, this.y) == 0 && Float.compare(point3dF.z, this.z) == 0;
    }

    public int hashCode() {
        float f = this.x;
        int iFloatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
        float f2 = this.y;
        return iFloatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0);
    }

    public String toString() {
        return "Point3dF(" + this.x + ", " + this.y + ", " + this.z + Constant.AFTER_QUTO;
    }

    public final float length() {
        return length(this.x, this.y, this.z);
    }

    public static float length(float f, float f2, float f3) {
        return (float) Math.hypot(f, f2);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.y);
        parcel.writeFloat(this.z);
    }

    public void readFromParcel(Parcel parcel) {
        this.x = parcel.readFloat();
        this.y = parcel.readFloat();
        this.z = parcel.readFloat();
    }
}
