package com.google.mlkit.vision.pose.internal;

import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class zza extends LazyInstanceMap<PoseDetectorOptionsBase, zzc> {
    private final MlKitContext zza;

    public zza(MlKitContext mlKitContext) {
        this.zza = mlKitContext;
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected /* synthetic */ zzc create(PoseDetectorOptionsBase poseDetectorOptionsBase) {
        return new zzc(this.zza, poseDetectorOptionsBase);
    }
}
