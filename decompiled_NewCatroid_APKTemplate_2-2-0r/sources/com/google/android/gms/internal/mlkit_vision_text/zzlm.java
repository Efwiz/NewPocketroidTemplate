package com.google.android.gms.internal.mlkit_vision_text;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlm extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzlm> CREATOR = new zzln();
    private final String zza;
    private final Rect zzb;
    private final List<Point> zzc;
    private final String zzd;

    public zzlm(String str, Rect rect, List<Point> list, String str2) {
        this.zza = str;
        this.zzb = rect;
        this.zzc = list;
        this.zzd = str2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzc, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzd, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zza() {
        return this.zza;
    }

    public final Rect zzb() {
        return this.zzb;
    }

    public final List<Point> zzc() {
        return this.zzc;
    }

    public final String zzd() {
        return this.zzd;
    }
}
