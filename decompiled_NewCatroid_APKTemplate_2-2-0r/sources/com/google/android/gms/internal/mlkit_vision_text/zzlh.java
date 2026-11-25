package com.google.android.gms.internal.mlkit_vision_text;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlh extends zza implements zzlj {
    zzlh(IBinder iBinder) {
        super(iBinder, "com.google.mlkit.vision.text.aidls.ITextRecognizerCreator");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzlj
    public final zzlg zzd(IObjectWrapper iObjectWrapper) throws RemoteException {
        zzlg zzlgVar;
        Parcel parcelZza = zza();
        zzc.zzc(parcelZza, iObjectWrapper);
        Parcel parcelZzb = zzb(1, parcelZza);
        IBinder strongBinder = parcelZzb.readStrongBinder();
        if (strongBinder == null) {
            zzlgVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.mlkit.vision.text.aidls.ITextRecognizer");
            zzlgVar = iInterfaceQueryLocalInterface instanceof zzlg ? (zzlg) iInterfaceQueryLocalInterface : new zzlg(strongBinder);
        }
        parcelZzb.recycle();
        return zzlgVar;
    }
}
