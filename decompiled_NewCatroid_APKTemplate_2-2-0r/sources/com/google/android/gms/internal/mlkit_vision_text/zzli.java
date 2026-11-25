package com.google.android.gms.internal.mlkit_vision_text;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzli extends zzb implements zzlj {
    public static zzlj zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.mlkit.vision.text.aidls.ITextRecognizerCreator");
        return iInterfaceQueryLocalInterface instanceof zzlj ? (zzlj) iInterfaceQueryLocalInterface : new zzlh(iBinder);
    }
}
