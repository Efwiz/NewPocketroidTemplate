package com.google.mlkit.vision.objects.defaults.internal;

import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes10.dex */
public final class zzb extends LazyInstanceMap<ObjectDetectorOptions, zze> {
    private final MlKitContext zza;

    zzb(MlKitContext mlKitContext) {
        this.zza = mlKitContext;
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected final /* bridge */ /* synthetic */ zze create(ObjectDetectorOptions objectDetectorOptions) {
        return new zze(this.zza, objectDetectorOptions);
    }
}
