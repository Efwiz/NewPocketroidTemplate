package com.google.research.xeno.effect;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdb;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class AssetRegistryService implements zzdb<AssetRegistry> {
    public native void nativeInstallServiceObject(long j, long j2);

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdb
    public /* synthetic */ void zza(long j, AssetRegistry assetRegistry) {
        nativeInstallServiceObject(j, assetRegistry.zza());
    }
}
