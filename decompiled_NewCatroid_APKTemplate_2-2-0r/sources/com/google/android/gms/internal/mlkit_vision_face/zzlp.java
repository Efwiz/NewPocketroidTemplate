package com.google.android.gms.internal.mlkit_vision_face;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlp extends zza implements zzlr {
    zzlp(IBinder iBinder) {
        super(iBinder, "com.google.mlkit.vision.face.aidls.IFaceDetectorCreator");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzlr
    public final zzlo zzd(IObjectWrapper iObjectWrapper, zzlk zzlkVar) throws RemoteException {
        zzlo zzloVar;
        Parcel parcelZza = zza();
        zzc.zzb(parcelZza, iObjectWrapper);
        zzc.zza(parcelZza, zzlkVar);
        Parcel parcelZzb = zzb(1, parcelZza);
        IBinder strongBinder = parcelZzb.readStrongBinder();
        if (strongBinder == null) {
            zzloVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.mlkit.vision.face.aidls.IFaceDetector");
            zzloVar = iInterfaceQueryLocalInterface instanceof zzlo ? (zzlo) iInterfaceQueryLocalInterface : new zzlo(strongBinder);
        }
        parcelZzb.recycle();
        return zzloVar;
    }
}
