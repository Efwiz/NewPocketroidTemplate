package com.google.android.gms.internal.mlkit_vision_text;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzln implements Parcelable.Creator<zzlm> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzlm createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        Rect rect = null;
        ArrayList arrayListCreateTypedList = null;
        String strCreateString2 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 2) {
                rect = (Rect) SafeParcelReader.createParcelable(parcel, header, Rect.CREATOR);
            } else if (fieldId == 3) {
                arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, Point.CREATOR);
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                strCreateString2 = SafeParcelReader.createString(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzlm(strCreateString, rect, arrayListCreateTypedList, strCreateString2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzlm[] newArray(int i) {
        return new zzlm[i];
    }
}
