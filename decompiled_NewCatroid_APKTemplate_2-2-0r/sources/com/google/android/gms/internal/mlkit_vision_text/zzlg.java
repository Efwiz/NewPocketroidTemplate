package com.google.android.gms.internal.mlkit_vision_text;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlg extends zza implements IInterface {
    zzlg(IBinder iBinder) {
        super(iBinder, "com.google.mlkit.vision.text.aidls.ITextRecognizer");
    }

    public final void zzd() throws RemoteException {
        zzc(1, zza());
    }

    public final void zze() throws RemoteException {
        zzc(2, zza());
    }

    public final zzlq zzf(IObjectWrapper iObjectWrapper, zzle zzleVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zzc(parcelZza, iObjectWrapper);
        zzc.zzb(parcelZza, zzleVar);
        Parcel parcelZzb = zzb(3, parcelZza);
        zzlq zzlqVar = (zzlq) zzc.zza(parcelZzb, zzlq.CREATOR);
        parcelZzb.recycle();
        return zzlqVar;
    }
}
