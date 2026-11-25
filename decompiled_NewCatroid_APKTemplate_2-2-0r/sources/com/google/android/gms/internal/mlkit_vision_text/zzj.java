package com.google.android.gms.internal.mlkit_vision_text;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzj extends zzb implements zzk {
    public static zzk zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.vision.text.internal.client.INativeTextRecognizerCreator");
        return iInterfaceQueryLocalInterface instanceof zzk ? (zzk) iInterfaceQueryLocalInterface : new zzi(iBinder);
    }
}
