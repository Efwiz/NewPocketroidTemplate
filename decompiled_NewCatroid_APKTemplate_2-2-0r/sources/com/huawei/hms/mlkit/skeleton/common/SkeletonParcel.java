package com.huawei.hms.mlkit.skeleton.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class SkeletonParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SkeletonParcel> CREATOR = new Parcelable.Creator<SkeletonParcel>() { // from class: com.huawei.hms.mlkit.skeleton.common.SkeletonParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SkeletonParcel createFromParcel(Parcel parcel) {
            return new SkeletonParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SkeletonParcel[] newArray(int i) {
            return new SkeletonParcel[i];
        }
    };
    public final List<JointParcel> joints;

    public SkeletonParcel(List<JointParcel> list) {
        this.joints = list;
    }

    public SkeletonParcel(Parcel parcel) {
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.joints = parcelReader.createTypedList(2, JointParcel.CREATOR, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeTypedList(2, this.joints, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
