package com.huawei.hms.mlkit.tts.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.base.common.AbstractSafeParcelable;
import com.huawei.hms.base.common.ParcelReader;
import com.huawei.hms.base.common.ParcelWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class TtsCancelParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TtsCancelParcel> CREATOR = new Parcelable.Creator<TtsCancelParcel>() { // from class: com.huawei.hms.mlkit.tts.common.TtsCancelParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsCancelParcel createFromParcel(Parcel parcel) {
            return new TtsCancelParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsCancelParcel[] newArray(int i) {
            return new TtsCancelParcel[i];
        }
    };
    public List<String> cancelList;

    public TtsCancelParcel() {
        this.cancelList = new ArrayList();
    }

    public TtsCancelParcel(List<String> list) {
        new ArrayList();
        this.cancelList = list;
    }

    protected TtsCancelParcel(Parcel parcel) {
        this.cancelList = new ArrayList();
        ParcelReader parcelReader = new ParcelReader(parcel);
        this.cancelList = parcelReader.createStringList(2, null);
        parcelReader.finish();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelWriter parcelWriter = new ParcelWriter(parcel);
        int iBeginObjectHeader = parcelWriter.beginObjectHeader();
        parcelWriter.writeStringList(2, this.cancelList, false);
        parcelWriter.finishObjectHeader(iBeginObjectHeader);
    }
}
