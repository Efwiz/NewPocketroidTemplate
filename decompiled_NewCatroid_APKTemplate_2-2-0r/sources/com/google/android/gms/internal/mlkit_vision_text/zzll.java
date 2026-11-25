package com.google.android.gms.internal.mlkit_vision_text;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzll implements Parcelable.Creator<zzlk> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzlk createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        Rect rect = null;
        ArrayList arrayListCreateTypedList = null;
        String strCreateString2 = null;
        ArrayList arrayListCreateTypedList2 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 2) {
                rect = (Rect) SafeParcelReader.createParcelable(parcel, header, Rect.CREATOR);
            } else if (fieldId == 3) {
                arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, Point.CREATOR);
            } else if (fieldId == 4) {
                strCreateString2 = SafeParcelReader.createString(parcel, header);
            } else if (fieldId != 5) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                arrayListCreateTypedList2 = SafeParcelReader.createTypedList(parcel, header, zzlo.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzlk(strCreateString, rect, arrayListCreateTypedList, strCreateString2, arrayListCreateTypedList2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzlk[] newArray(int i) {
        return new zzlk[i];
    }
}
