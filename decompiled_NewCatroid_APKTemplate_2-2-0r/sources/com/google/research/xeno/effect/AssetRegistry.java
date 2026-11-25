package com.google.research.xeno.effect;

import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class AssetRegistry {
    private final long zza = nativeCreateAssetRegistry();

    public AssetRegistry(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            nativeRegisterAsset(this.zza, entry.getKey(), entry.getValue());
        }
    }

    private native long nativeCreateAssetRegistry();

    private native void nativeRegisterAsset(long j, String str, String str2);

    public final long zza() {
        return this.zza;
    }
}
