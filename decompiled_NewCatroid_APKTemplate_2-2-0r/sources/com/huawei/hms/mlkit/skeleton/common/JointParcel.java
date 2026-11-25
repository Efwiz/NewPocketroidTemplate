package com.huawei.hms.mlkit.skeleton.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;

/* JADX INFO: loaded from: classes10.dex */
public class JointParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<JointParcel> CREATOR = new Parcelable.Creator<JointParcel>() { // from class: com.huawei.hms.mlkit.skeleton.common.JointParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JointParcel createFromParcel(Parcel parcel) {
            return new JointParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JointParcel[] newArray(int i) {
            return new JointParcel[i];
        }
    };
    public final Float score;
    public final int type;
    public final Float x;
    public final Float y;

    public JointParcel(Float f, Float f2, int i, Float f3) {
        this.x = f;
        this.y = f2;
        this.type = i;
        this.score = f3;
    }

    public JointParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.x = parcelReader.readFloatObject(2, null);
        this.y = parcelReader.readFloatObject(3, null);
        this.type = parcelReader.readInt(4, 0);
        this.score = parcelReader.readFloatObject(5, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeFloatObject(2, this.x, false);
        parcelWriter.writeFloatObject(3, this.y, false);
        parcelWriter.writeInt(4, this.type);
        parcelWriter.writeFloatObject(5, this.score, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
