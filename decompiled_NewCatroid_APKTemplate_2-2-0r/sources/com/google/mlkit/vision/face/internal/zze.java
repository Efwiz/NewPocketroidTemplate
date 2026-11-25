package com.google.mlkit.vision.face.internal;

import android.content.Context;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.internal.mlkit_vision_face.zzlc;
import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.face.FaceDetectorOptions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
public final class zze extends LazyInstanceMap<FaceDetectorOptions, zzh> {
    private final MlKitContext zza;

    public zze(MlKitContext mlKitContext) {
        this.zza = mlKitContext;
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected final /* bridge */ /* synthetic */ zzh create(FaceDetectorOptions faceDetectorOptions) {
        FaceDetectorOptions faceDetectorOptions2 = faceDetectorOptions;
        Context applicationContext = this.zza.getApplicationContext();
        return new zzh(zzlc.zza(zzi.zzb()), faceDetectorOptions2, (zza.zzd(applicationContext) || GoogleApiAvailabilityLight.getInstance().getApkVersion(applicationContext) >= 204490000) ? new zza(applicationContext, faceDetectorOptions2) : new zzl(applicationContext, faceDetectorOptions2));
    }
}
