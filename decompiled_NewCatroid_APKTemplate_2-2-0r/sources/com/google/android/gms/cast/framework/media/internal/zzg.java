package com.google.android.gms.cast.framework.media.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzg extends com.google.android.gms.internal.cast.zza implements zzi {
    zzg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.framework.media.internal.IFetchBitmapTask");
    }

    @Override // com.google.android.gms.cast.framework.media.internal.zzi
    public final Bitmap zze(Uri uri) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.cast.zzc.zzc(parcelZza, uri);
        Parcel parcelZzb = zzb(1, parcelZza);
        Bitmap bitmap = (Bitmap) com.google.android.gms.internal.cast.zzc.zza(parcelZzb, Bitmap.CREATOR);
        parcelZzb.recycle();
        return bitmap;
    }
}
