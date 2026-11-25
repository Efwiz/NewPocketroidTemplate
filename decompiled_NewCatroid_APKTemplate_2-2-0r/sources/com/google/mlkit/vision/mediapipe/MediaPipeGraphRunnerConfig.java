package com.google.mlkit.vision.mediapipe;

import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class MediaPipeGraphRunnerConfig {

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public enum ResultType {
        PROTO,
        IMAGE_FRAME
    }

    abstract MlKitContext zza();

    abstract String zzb();

    abstract String zzc();

    abstract String zzd();

    abstract Map<String, String> zze();

    abstract ResultType zzf();

    public static MediaPipeGraphRunnerConfig create(MlKitContext mlKitContext, String str, String str2, String str3, Map<String, String> map, ResultType resultType) {
        return new zza(mlKitContext, str, str2, str3, map, resultType);
    }
}
