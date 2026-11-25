package com.google.android.gms.internal.mlkit_vision_face;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlo extends zza implements IInterface {
    zzlo(IBinder iBinder) {
        super(iBinder, "com.google.mlkit.vision.face.aidls.IFaceDetector");
    }

    public final void zzd() throws RemoteException {
        zzc(1, zza());
    }

    public final void zze() throws RemoteException {
        zzc(2, zza());
    }

    public final List<zzlm> zzf(IObjectWrapper iObjectWrapper, zzlg zzlgVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zzb(parcelZza, iObjectWrapper);
        zzc.zza(parcelZza, zzlgVar);
        Parcel parcelZzb = zzb(3, parcelZza);
        ArrayList arrayListCreateTypedArrayList = parcelZzb.createTypedArrayList(zzlm.CREATOR);
        parcelZzb.recycle();
        return arrayListCreateTypedArrayList;
    }
}
